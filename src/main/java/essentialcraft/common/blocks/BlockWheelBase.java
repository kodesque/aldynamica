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
        return this.handleStructure(worldIn, pos, side, funcType.ASSEMBLE);
    }

    public enum funcType {
        ASSEMBLE,
        DISASSEMBLE,
        CHECK
    }

    public boolean handleStructure(World worldIn, BlockPos corePos, EnumFacing side, funcType type) {

        ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();

        BlockPos anchorPos = corePos;
        BlockPos targetPos = corePos;
        int sidesPass = 0;

        boolean valueToCheck = false;

        sidesHandle:
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (side != facing && side != facing.getOpposite()) {
                    sides.add(facing);
                } else {
                    continue;
                }

                targetPos = corePos.offset(facing);
                valueToCheck = type == funcType.ASSEMBLE ? worldIn.getBlockState(targetPos).getBlock().isReplaceable(worldIn, targetPos)
                        : worldIn.getBlockState(targetPos).getBlock().equals(BlockInit.WHEEL_FILLER);

                if (valueToCheck) {
                    this.proceed(type, targetPos, corePos, worldIn);
                    sidesPass++;
                } else {
                    break;
                }

                if (sidesPass == 4) {

                    for (int x = 0; x <= 1; x++) {
                        for (int y = 2; y <= 3; y++) {
                            anchorPos = corePos.offset(sides.get(x));
                            targetPos = anchorPos.offset(sides.get(y));

                            valueToCheck = type == funcType.ASSEMBLE ? worldIn.getBlockState(targetPos).getBlock().isReplaceable(worldIn, targetPos)
                                    : worldIn.getBlockState(targetPos).getBlock().equals(BlockInit.WHEEL_FILLER);

                            if (valueToCheck) {

                                this.proceed(type, targetPos, corePos, worldIn);

                                if (sidesPass == 7)
                                    return true;
                                else {
                                    sidesPass++;
                                }

                            } else {
                                break sidesHandle;
                            }
                        }
                    }
                }
            }

        return false;
    }

    public void proceed(funcType type, BlockPos targetPos, @Nullable BlockPos corePos, World worldIn) {
        switch (type) {
            case ASSEMBLE:
                worldIn.setBlockState(targetPos, BlockInit.WHEEL_FILLER.getDefaultState());

                NBTTagCompound compound = new NBTTagCompound();
                compound.setLong(TileEntityWheelFiller.corePosKey, corePos.toLong());

                ((TileEntityWheelFiller)worldIn.getTileEntity(targetPos)).setCorePos(corePos);

                break;
            case DISASSEMBLE:
                worldIn.destroyBlock(targetPos, false);
                break;
            case CHECK:
                break;
        }
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        this.handleStructure(worldIn, pos, state.getValue(FACING), funcType.DISASSEMBLE);
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
