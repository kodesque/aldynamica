package essentialcraft.common.blocks;

import java.util.ArrayList;

import javax.annotation.Nullable;

import essentialcraft.api.Main;
import essentialcraft.common.tiles.TileEntityWheelBase;
import essentialcraft.common.tiles.TileEntityWheelFiller;
import essentialcraft.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWheelBase extends BlockContainer {

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockWheelBase(String name) {
        super(Material.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        BlockInit.BLOCKS.add(this);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)

    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getIndex();

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWheelBase();
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        if (this.handleStructure(worldIn, pos, side, null, checkType.PREPARE))
            return this.handleStructure(worldIn, pos, side, funcType.ASSEMBLE, null);
        else
            return false;
    }

    public enum funcType {
        ASSEMBLE,
        DISASSEMBLE
    }

    public enum checkType {
        PREPARE,
        MAKE_SURE
    }


    public boolean handleStructure(World worldIn, BlockPos corePos, EnumFacing side, @Nullable funcType funcType, @Nullable checkType checkType) {

        ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();

        BlockPos anchorPos = corePos;
        BlockPos targetPos = null;
        int sidesPass = 0;

        handleSides:
            for (EnumFacing facing : EnumFacing.VALUES) {

                if (side != facing && side != facing.getOpposite()) {
                    sides.add(facing);
                } else {
                    continue;
                }

                targetPos = corePos.offset(facing);

                if (sidesPass < 4) {
                    System.out.println("handling side " + side);
                    if (checkType != null || funcType != null) {
                        if (checkType != null && this.proceedWithCheck(checkType, targetPos, corePos, worldIn)) {
                            System.out.println("checking side " + side);
                            sidesPass++;
                        }

                        if (funcType != null && this.proceedWithFunc(funcType, targetPos, corePos, worldIn)) {
                            System.out.println("placing at side " + side);
                            sidesPass++;
                        }
                    }
                } else {

                    for (int x = 0; x < 2; x++) {
                        for (int y = 2; y < 4; y++) {
                            anchorPos = corePos.offset(sides.get(x));
                            targetPos = anchorPos.offset(sides.get(y));

                            System.out.println("handling corner " + targetPos);

                            if (checkType != null || funcType != null) {
                                if (checkType != null && this.proceedWithCheck(checkType, targetPos, corePos, worldIn)) {
                                    System.out.println("checking corner " + targetPos);
                                    sidesPass++;
                                    continue;
                                }

                                if (funcType != null && this.proceedWithFunc(funcType, targetPos, corePos, worldIn)) {
                                    System.out.println("checking corner " + targetPos);
                                    sidesPass++;
                                    continue;
                                }
                            } else {
                                break handleSides;
                            }

                            if (sidesPass == 8)
                                //7 or 8
                                return true;
                        }
                    }
                }
            }

        return false;

    }

    public boolean proceedWithCheck(checkType type, BlockPos targetPos, @Nullable BlockPos corePos, World worldIn) {
        switch (type) {
            case PREPARE: {
                if (worldIn.getBlockState(targetPos).getBlock().isReplaceable(worldIn, targetPos))
                    return true;
                break;
            }
            case MAKE_SURE: {
                if (worldIn.getBlockState(targetPos).getBlock().equals(BlockInit.WHEEL_FILLER))
                    return true;
                break;
            }
        }
        return false;
    }

    public boolean proceedWithFunc(funcType type, BlockPos targetPos, @Nullable BlockPos corePos, World worldIn) {
        switch (type) {
            case ASSEMBLE: {
                worldIn.setBlockState(targetPos, BlockInit.WHEEL_FILLER.getDefaultState());

                TileEntity tile = worldIn.getTileEntity(targetPos);
                if (tile != null) {
                    ((TileEntityWheelFiller)tile).setCorePos(corePos);
                }
                //idk if this is actually needed

                return true;
            }
            case DISASSEMBLE: {
                worldIn.destroyBlock(targetPos, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        this.handleStructure(worldIn, pos, state.getValue(FACING), funcType.DISASSEMBLE, null);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {

    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }


}
