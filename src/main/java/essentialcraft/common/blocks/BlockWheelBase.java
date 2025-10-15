package essentialcraft.common.blocks;

import java.util.ArrayList;

import essentialcraft.api.Main;
import essentialcraft.common.tiles.TileEntityWheelBase;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
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

        boolean checkSides = false;
        boolean checkCorners = false;

        ArrayList<EnumFacing> sides = new ArrayList<EnumFacing>();
        ArrayList<EnumFacing> corners = new ArrayList<EnumFacing>();

        BlockPos anchorPos = pos;
        int sidesPass = 0;
        boolean allPass = false;

        sidesCheck:
            for (EnumFacing facing : EnumFacing.VALUES) {
                if (side != facing && side != facing.getOpposite()) {
                    sides.add(facing);
                } else {
                    continue;
                }

                if (worldIn.getBlockState(pos.offset(facing)).getBlock().isReplaceable(worldIn, pos)) {
                    sidesPass++;
                } else {
                    break;
                }

                if (sidesPass == 4) {

                    for (int x = 0; x < sides.size(); x++) {
                        for (int y = sides.size() - 1; y > 0; y--) {
                            anchorPos = pos.offset(sides.get(x));
                            if (worldIn.getBlockState(anchorPos.offset(sides.get(y))).getBlock().isReplaceable(worldIn, anchorPos)) {
                                sidesPass++;
                            } else {
                                break sidesCheck;
                            }
                        }
                    }

                    if (sidesPass == 8) {
                        allPass = true;
                    }
                }
            }

        //Old version


        for (EnumFacing facing : EnumFacing.VALUES) {
            if (facing == side || facing.getOpposite() == side) {
                continue;
            } else {
                sides.add(side);
            }

            if (worldIn.getBlockState(pos.offset(side)).getBlock().isReplaceable(worldIn, pos)) {
                checkSides = true;
            } else {
                checkSides = false;
                break;
            }
        }

        outer:
            for (EnumFacing anchor : sides) {
                for (EnumFacing direction : corners) {
                    anchorPos = pos.offset(anchor);
                    if (worldIn.getBlockState(anchorPos.offset(direction)).getBlock().isReplaceable(worldIn, anchorPos)) {
                        checkCorners = true;
                    } else {
                        checkCorners = false;
                        break outer;
                    }
                }
            }

        if (checkSides) {
            corners.add(sides.get(0));
            corners.add(sides.get(1));
            sides.remove(0);
            sides.remove(1);

            outer:
                for (EnumFacing anchor : sides) {
                    for (EnumFacing direction : corners) {
                        anchorPos = pos.offset(anchor);
                        if (worldIn.getBlockState(anchorPos.offset(direction)).getBlock().isReplaceable(worldIn, anchorPos)) {
                            checkCorners = true;
                        } else {
                            checkCorners = false;
                            break outer;
                        }
                    }
                }
        }

        return checkSides && checkCorners;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {

    }


}
