package aldynamica.common.registry;

import java.util.ArrayList;
import java.util.List;

import aldynamica.common.templates.ALBlockBase;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {

    public static List<Block> BLOCKS = new ArrayList<Block>();

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

    public static void initBlocks(IForgeRegistry<Block> iForgeRegistry) {

        BlockRegistry.TURBID_TILE = registerBlock(new ALBlockBase("turbid_tile", Material.ROCK));
        BlockRegistry.TURBID_TILE_DARK = registerBlock(new ALBlockBase("turbid_tile_dark", Material.ROCK));
        BlockRegistry.TURBID_TILE_MOSSY = registerBlock(new ALBlockBase("turbid_tile_mossy", Material.ROCK));

        BlockRegistry.TURBID_BRICKS = registerBlock(new ALBlockBase("turbid_bricks", Material.ROCK));
        BlockRegistry.TURBID_GLOWTILE = registerBlock(new ALBlockBase("turbid_glowtile", Material.GLASS));

        BlockRegistry.TURBID_SLAB = registerBlock(new ALBlockBase("turbid_slab", Material.ROCK));
        BlockRegistry.TURBID_WALL = registerBlock(new ALBlockBase("turbid_wall", Material.ROCK));
        BlockRegistry.TURBID_STAIRS = registerBlock(new ALBlockBase("turbid_stairs", Material.ROCK));

        BlockRegistry.OCHER_BONE = registerBlock(new ALBlockBase("ocher_bone", Material.SAND));

        BlockRegistry.PEAT_RAW = registerBlock(new ALBlockBase("peat_raw", Material.CLAY));
        BlockRegistry.PEAT_MOSSY = registerBlock(new ALBlockBase("peat_mossy", Material.CLAY));

        BlockRegistry.SILT = registerBlock(new ALBlockBase("silt", Material.CLAY));
        BlockRegistry.SWAMP_REED = registerBlock(new ALBlockBase("swamp_reed", Material.PLANTS));

    }

    private static Block registerBlock(Block block, ItemBlock itemBlock) {
        ForgeRegistries.BLOCKS.register(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(itemBlock);
        Main.proxy.registerItemBlockRenderer(itemBlock);
        return block;
    }

    private static Block registerBlock(Block block) {
        return registerBlock(block, new ItemBlock(block));
    }

    private static Block registerBlockSpecial(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }

}
