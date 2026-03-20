package metamechanica.common.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import metamechanica.common.templates.BlockBase;
import metamechanica.init.ItemInit;
import metamechanica.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMineral extends BlockBase {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyInteger MINED = PropertyInteger.create("mined", 0, 3);

    private  String type;
    private Block half;

    public enum EnumOreTypes {
        CORUNDUM(() -> ItemInit.GEM_CORUNDUM, EnumParticleTypes.PORTAL),
        RHINESTONE(() -> ItemInit.GEM_RHINESTONE, EnumParticleTypes.END_ROD),
        GARNET(() -> ItemInit.GEM_GARNET,EnumParticleTypes.REDSTONE);

        private final Supplier<Item> drop;
        private final EnumParticleTypes particles;

        EnumOreTypes(Supplier<Item> drop, EnumParticleTypes particles) {
            this.drop = drop;
            this.particles = particles;
        }

        public Item getDrop() {
            return this.drop.get();
        }

        public EnumParticleTypes getParticles() {
            return this.particles;
        }
    }

    public BlockMineral(EnumOreTypes type) {
        super("deposit" + "_" + type.toString().toLowerCase(), Material.ROCK);

        this.type = type.toString();

        this.translucent = true;

        this.setHardness(5);
        this.setHarvestLevel("pickaxe", 2);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(MINED, 0));
    }

    public static boolean performHit(World worldIn, BlockPos pos, IBlockState state, EnumFacing facing) {
        if (facing == state.getValue(BlockMineral.FACING)) {

            List<EnumFacing> asList = new ArrayList<EnumFacing>(Arrays.asList(EnumFacing.HORIZONTALS));
            asList.remove(asList.indexOf(facing));
            float rollSides = worldIn.rand.nextFloat();

            if (rollSides > 0.7) {
                asList.remove(asList.indexOf(facing.getOpposite()));
            } else {
                asList.remove(asList.indexOf(facing.rotateY()));
                asList.remove(asList.indexOf(facing.rotateY().rotateY()));
            }

            EnumFacing newFace = asList.get(worldIn.rand.nextInt(asList.size()));

            int oldValue = state.getValue(BlockMineral.MINED);
            int newValue = Math.min(oldValue + 1, 3);

            spawnAABBParticles(worldIn, pos, EnumParticleTypes.CRIT);

            if (oldValue == 3) {
                worldIn.destroyBlock(pos, false);
                worldIn.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.0F, false, false);

                worldIn.setBlockState(pos, ((BlockMineral)state.getBlock()).half.getDefaultState()
                        .withProperty(BlockMineralHalf.FACING, facing));

                worldIn.playSound(
                        null,
                        pos,
                        SoundInit.ORE_CRACK,
                        SoundCategory.BLOCKS,
                        2.0F,
                        1.0F
                        );
            } else {
                worldIn.setBlockState(pos, state
                        .withProperty(BlockMineral.FACING, newFace)
                        .withProperty(BlockMineral.MINED, newValue));

                worldIn.playSound(
                        null,
                        pos,
                        SoundInit.WEDGE_USE,
                        SoundCategory.BLOCKS,
                        2.0F,
                        1.0F
                        );
            }

            return true;
        }
        return false;
    }

    public static void spawnAABBParticles(World worldIn, BlockPos pos, EnumParticleTypes type)
    {
        int amount = 15;

        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getMaterial() != Material.AIR)
        {
            for (int i = 0; i < amount; ++i)
            {
                double d0 = worldIn.rand.nextGaussian() * 0.02D;
                double d1 = worldIn.rand.nextGaussian() * 0.02D;
                double d2 = worldIn.rand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(type, pos.getX() + worldIn.rand.nextFloat(), pos.getY() + worldIn.rand.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, pos.getZ() + worldIn.rand.nextFloat(), d0, d1, d2);
            }
        }
        else
        {
            for (int i1 = 0; i1 < amount; ++i1)
            {
                double d0 = worldIn.rand.nextGaussian() * 0.02D;
                double d1 = worldIn.rand.nextGaussian() * 0.02D;
                double d2 = worldIn.rand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(type, pos.getX() + worldIn.rand.nextFloat(), pos.getY() + (double)worldIn.rand.nextFloat() * 1.0f, pos.getZ() + worldIn.rand.nextFloat(), d0, d1, d2, new int[0]);
            }
        }
    }

    public void assignHalf(Block block) {
        if (this.half == null) {
            this.half = block;
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
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 0.0F;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, MINED});
    }


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Items.AIR;
    }

    //here should be different stone types, not air

    //    @Override
    //    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    //    {
    //        if (side != EnumFacing.UP && side != EnumFacing.DOWN)
    //            return true;
    //
    //        return false;
    //    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing playerFace = EnumFacing.getDirectionFromEntityLiving(pos, placer);

        if (playerFace == EnumFacing.UP || playerFace == EnumFacing.DOWN) {
            playerFace = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, playerFace).withProperty(MINED, 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int mined = state.getValue(MINED); // 0-3

        EnumFacing facing = state.getValue(FACING);
        int facingIndex;

        switch (facing) {
            case NORTH: facingIndex = 0; break;
            case EAST:  facingIndex = 1; break;
            case SOUTH: facingIndex = 2; break;
            case WEST:  facingIndex = 3; break;
            default:    facingIndex = 0;
        }

        return (mined << 2) | facingIndex;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        int facingIndex = meta & 3;
        int mined = (meta >> 2) & 3;

        EnumFacing facing;
        switch (facingIndex) {
            case 0:  facing = EnumFacing.NORTH; break;
            case 1:  facing = EnumFacing.EAST;  break;
            case 2:  facing = EnumFacing.SOUTH; break;
            case 3:  facing = EnumFacing.WEST;  break;
            default: facing = EnumFacing.NORTH;
        }

        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(MINED, mined);
    }

}
