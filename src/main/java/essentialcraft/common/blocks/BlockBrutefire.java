package essentialcraft.common.blocks;

import java.util.ArrayList;
import java.util.Random;

import essentialcraft.init.BlockInit;
import essentialcraft.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBrutefire extends BlockFire {

    public BlockBrutefire(String name) {

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        BlockInit.BLOCKS.add(this);
    }

    @Override
    public boolean canCatchFire(IBlockAccess world, BlockPos pos, EnumFacing face)
    {

        return world.getBlockState(pos).getBlock().isFlammable(world, pos, face);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getGameRules().getBoolean("doFireTick"))
        {
            if (!worldIn.isAreaLoaded(pos, 2)) return;
            if (!this.canPlaceBlockAt(worldIn, pos))
            {
                worldIn.setBlockToAir(pos);
            }

            Block block = worldIn.getBlockState(pos.down()).getBlock();
            boolean flag = block.isFireSource(worldIn, pos.down(), EnumFacing.UP);

            int i = state.getValue(AGE).intValue();

            if (i < 15)
            {
                state = state.withProperty(AGE, Integer.valueOf(i + rand.nextInt(3) / 2));
                worldIn.setBlockState(pos, state, 4);
            }

            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + rand.nextInt(10));

            if (!flag)
            {
                if (!this.canNeighborCatchFire(worldIn, pos))
                {
                    if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) || i > 3)
                    {
                        worldIn.setBlockToAir(pos);
                    }

                    return;
                }

                if (!this.canCatchFire(worldIn, pos.down(), EnumFacing.UP) && i == 15 && rand.nextInt(4) == 0)
                {
                    worldIn.setBlockToAir(pos);
                    return;
                }
            }

            boolean flag1 = worldIn.isBlockinHighHumidity(pos);
            int j = 0;

            if (flag1)
            {
                j = -50;
            }

            this.tryCatchFire(worldIn, pos.east(), 300 + j, rand, i, EnumFacing.WEST);
            this.tryCatchFire(worldIn, pos.west(), 300 + j, rand, i, EnumFacing.EAST);
            this.tryCatchFire(worldIn, pos.down(), 250 + j, rand, i, EnumFacing.UP);
            this.tryCatchFire(worldIn, pos.up(), 250 + j, rand, i, EnumFacing.DOWN);
            this.tryCatchFire(worldIn, pos.north(), 300 + j, rand, i, EnumFacing.SOUTH);
            this.tryCatchFire(worldIn, pos.south(), 300 + j, rand, i, EnumFacing.NORTH);

            for (int k = -1; k <= 1; ++k)
            {
                for (int l = -1; l <= 1; ++l)
                {
                    for (int i1 = -1; i1 <= 4; ++i1)
                    {
                        if (k != 0 || i1 != 0 || l != 0)
                        {
                            int j1 = 100;

                            if (i1 > 1)
                            {
                                j1 += (i1 - 1) * 100;
                            }

                            BlockPos blockpos = pos.add(k, i1, l);
                            int k1 = this.getNeighborEncouragement(worldIn, blockpos);

                            if (k1 > 0)
                            {
                                int l1 = (k1 + 40 + worldIn.getDifficulty().getId() * 7) / (i + 30);

                                if (flag1)
                                {
                                    l1 /= 2;
                                }

                                if (l1 > 0 && rand.nextInt(j1) <= l1)
                                {
                                    int i2 = i + rand.nextInt(5) / 4;

                                    if (i2 > 15)
                                    {
                                        i2 = 15;
                                    }

                                    worldIn.setBlockState(blockpos, state.withProperty(AGE, Integer.valueOf(i2)), 3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canNeighborCatchFire(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (this.canCatchFire(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()))
                return true;
        }

        return false;
    }

    private int getNeighborEncouragement(World worldIn, BlockPos pos)
    {
        if (!worldIn.isAirBlock(pos))
            return 0;
        else
        {
            int i = 0;

            for (EnumFacing enumfacing : EnumFacing.values())
            {
                i = Math.max(worldIn.getBlockState(pos.offset(enumfacing)).getBlock().getFireSpreadSpeed(worldIn, pos.offset(enumfacing), enumfacing.getOpposite()), i);
            }

            return i;
        }
    }

    private void tryCatchFire(World worldIn, BlockPos pos, int chance, Random random, int age, EnumFacing face)
    {
        int i = worldIn.getBlockState(pos).getBlock().getFlammability(worldIn, pos, face);

        if (random.nextInt(chance) < i)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (random.nextInt(age + 10) < 5)
            {
                int j = age + random.nextInt(5) / 4;

                if (j > 15)
                {
                    j = 15;
                }

                worldIn.setBlockState(pos, this.getDefaultState().withProperty(AGE, Integer.valueOf(j)), 3);
            }
            else
            {
                worldIn.setBlockToAir(pos);
            }

            if (iblockstate.getBlock() == Blocks.TNT)
            {
                Blocks.TNT.onPlayerDestroy(worldIn, pos, iblockstate.withProperty(BlockTNT.EXPLODE, Boolean.valueOf(true)));
            }
        }
    }


    //    public static void init() {
    //
    //        //vanilla start
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.PLANKS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DOUBLE_WOODEN_SLAB, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.WOODEN_SLAB, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.OAK_FENCE_GATE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.SPRUCE_FENCE_GATE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.BIRCH_FENCE_GATE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.JUNGLE_FENCE_GATE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DARK_OAK_FENCE_GATE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.ACACIA_FENCE_GATE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.OAK_FENCE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.SPRUCE_FENCE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.BIRCH_FENCE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.JUNGLE_FENCE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DARK_OAK_FENCE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.ACACIA_FENCE, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.OAK_STAIRS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.BIRCH_STAIRS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.SPRUCE_STAIRS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.JUNGLE_STAIRS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.ACACIA_STAIRS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DARK_OAK_STAIRS, 10, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.LOG, 10, 10);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.LOG2, 10, 10);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.LEAVES, 60, 80);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.LEAVES2, 60, 80);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.BOOKSHELF, 40, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.TNT, 15, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.TALLGRASS, 80, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DOUBLE_PLANT, 80, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.YELLOW_FLOWER, 80, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.RED_FLOWER, 80, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DEADBUSH, 80, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.WOOL, 60, 60);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.VINE, 30, 100);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.COAL_BLOCK, 20, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.HAY_BLOCK, 60, 60);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.CARPET, 60, 60);
    //        //vanilla end
    //
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.COBBLESTONE, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.MOSSY_COBBLESTONE, 5, 5);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONEBRICK, 5, 5);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.BRICK_BLOCK, 5, 30);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.BRICK_STAIRS, 5, 30);
    //
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_SLAB, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DOUBLE_STONE_SLAB, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_SLAB2, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DOUBLE_STONE_SLAB2, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_STAIRS, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_BRICK_STAIRS, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.COBBLESTONE_WALL, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_BRICK_STAIRS, 5, 5);
    //
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.MONSTER_EGG, 5, 60);
    //
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.FURNACE, 5, 10);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DROPPER, 5, 10);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.DISPENSER, 5, 10);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_BUTTON, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STONE_PRESSURE_PLATE, 5, 20);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.PISTON, 5, 40);
    //        BlockInit.BRUTEFIRE_PRE.setFireInfo(Blocks.STICKY_PISTON, 5, 40);
    //
    //    }


}
