package aldynamica.common.init;

import java.util.ArrayList;
import java.util.List;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import aldynamica.api.IBlockSpecial;
import aldynamica.common.templates.ALBlockBase;
import aldynamica.common.templates.ALItemBlockBase;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockInit {

    public static Block TURBID_TILE;
    public static Block TURBID_TILE_DARK;
    public static Block TURBID_TILE_MOSSY;

    public static Block TURBID_BRICKS;
    public static Block TURBID_GLOWTILE;

    public static Block TURBID_SLAB;
    public static Block TURBID_WALL;
    public static Block TURBID_STAIRS;

    public static Block OCHER_BONE;

    public static Block PEAT_RAW;
    public static Block PEAT_MOSSY;

    public static Block SILT;
    public static Block SWAMP_REED;

    public static List<Block> BLOCKS = new ArrayList<Block>();

    public static void initBlocks(IForgeRegistry<Block> iForgeRegistry) {

        iForgeRegistry.register(BlockInit.TURBID_TILE = new ALBlockBase("turbid_tile", Material.ROCK, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.TURBID_TILE_DARK = new ALBlockBase("turbid_tile_dark", Material.ROCK, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.TURBID_TILE_MOSSY = new ALBlockBase("turbid_tile_mossy", Material.ROCK, EnumSortGroup.WORLDGEN));

        iForgeRegistry.register(BlockInit.TURBID_BRICKS = new ALBlockBase("turbid_bricks", Material.ROCK, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.TURBID_GLOWTILE = new ALBlockBase("turbid_glowtile", Material.GLASS, EnumSortGroup.WORLDGEN));

        iForgeRegistry.register(BlockInit.TURBID_SLAB = new ALBlockBase("turbid_slab", Material.ROCK, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.TURBID_WALL = new ALBlockBase("turbid_wall", Material.ROCK, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.TURBID_STAIRS = new ALBlockBase("turbid_stairs", Material.ROCK, EnumSortGroup.WORLDGEN));

        iForgeRegistry.register(BlockInit.OCHER_BONE = new ALBlockBase("ocher_bone", Material.SAND, EnumSortGroup.WORLDGEN));

        iForgeRegistry.register(BlockInit.PEAT_RAW = new ALBlockBase("peat_raw", Material.CLAY, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.PEAT_MOSSY = new ALBlockBase("peat_mossy", Material.CLAY, EnumSortGroup.WORLDGEN));

        iForgeRegistry.register(BlockInit.SILT = new ALBlockBase("silt", Material.CLAY, EnumSortGroup.WORLDGEN));
        iForgeRegistry.register(BlockInit.SWAMP_REED = new ALBlockBase("swamp_reed", Material.PLANTS, EnumSortGroup.WORLDGEN));

    }

    public static void initBlocksRemap() {
        //        RemappingManager.BLOCK_REMAPS.put();
    }

    public static void initItemBlocks(IForgeRegistry<Item> iForgeRegistry) {
        for (Block element : BlockInit.BLOCKS) {

            if (element instanceof IBlockSpecial) {
                iForgeRegistry.register(((IBlockSpecial)element).getItemBlockSpecial());
            } else {
                ALItemBlockBase itemblock = new ALItemBlockBase(element, ((IAldynamicaNative)element).getGroup());
                iForgeRegistry.register(itemblock);
                Main.proxy.registerItemBlockRenderer(itemblock);
            }

        }
    }


}
