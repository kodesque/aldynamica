package metamechanica.common.blocks.world;

import java.util.Random;

import metamechanica.common.templates.BlockBase;
import metamechanica.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMineralHalf extends BlockBase{

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.0, 0.0, 0.5, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 0.5);
    private static final AxisAlignedBB AABB_WEST  = new AxisAlignedBB(0.5, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB AABB_EAST  = new AxisAlignedBB(0.0, 0.0, 0.0, 0.5, 1.0, 1.0);

    private String type;

    public BlockMineralHalf(Block block) {
        super(((BlockMineral)block).getType().toString().toLowerCase() + "_" + "half", Material.ROCK);

        this.type = ((BlockMineral)block).getType().toString();
        ((BlockMineral)block).assignHalf(this);

        this.setTickRandomly(true);
        this.setHardness(5);
        this.setHarvestLevel("pickaxe", 2);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        switch (facing) {
            case NORTH: return AABB_NORTH;
            case SOUTH: return AABB_SOUTH;
            case WEST:  return AABB_WEST;
            case EAST:  return AABB_EAST;
            default:    return AABB_NORTH;
        }
    }

    public BlockMineral.EnumOreTypes getType() {
        return BlockMineral.EnumOreTypes.valueOf(this.type);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (rand.nextInt(5) == 0) {

            //        if (worldIn.getWorldTime() % 20 == 0) {
            worldIn.spawnParticle(
                    this.getType().getParticles(),
                    pos.getX() + 0.5 + rand.nextFloat() * 0.1F,
                    pos.getY() + 0.5 + rand.nextFloat() * 0.1F,
                    pos.getZ() + 0.5 + rand.nextFloat() * 0.1F,
                    0.0F,
                    0.0F,
                    0.0F);
            //        }
        }
    }



    //    @Override
    //    @SideOnly(Side.CLIENT)
    //    public BlockRenderLayer getRenderLayer() {
    //        return BlockRenderLayer.TRANSLUCENT;
    //    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
        return 55;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.getType().getDrop();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = state.getValue(FACING);

        switch (facing) {
            case NORTH: return 0;
            case EAST:  return 1;
            case SOUTH: return 2;
            case WEST:  return 3;
            default:    return 0;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing;

        switch (meta & 3) {
            case 0:  facing = EnumFacing.NORTH; break;
            case 1:  facing = EnumFacing.EAST;  break;
            case 2:  facing = EnumFacing.SOUTH; break;
            case 3:  facing = EnumFacing.WEST;  break;
            default: facing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, facing);
    }

}
