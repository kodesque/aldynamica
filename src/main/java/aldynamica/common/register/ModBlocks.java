package aldynamica.common.register;

import java.util.List;

import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {

    public static List<Block> BLOCKS;

    public static Block EXAMPLE;

    public static void initBlocks(IForgeRegistry<Block> iForgeRegistry) {

        //        iForgeRegistry.register();

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
