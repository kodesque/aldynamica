package aldynamica.common.init;

import java.util.ArrayList;
import java.util.List;

import aldynamica.api.EnumSortGroup;
import aldynamica.common.items.misc.ItemDebug;
import aldynamica.common.templates.ALItemBase;
import aldynamica.util.RemappingHashes;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemInit {

    public static Item DEBUG;

    public static Item CORPUS;

    public static List<Item> ITEMS = new ArrayList<Item>();

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(ItemInit.DEBUG = new ItemDebug("debug"));

        iForgeRegistry.register(ItemInit.CORPUS = new ALItemBase("corpus_aldynamica", EnumSortGroup.ITEMS, false));
    }

    public static void initItemsRemap() {
        //        RemappingManager.ITEM_REMAPS.put();
    }

    @SideOnly(Side.CLIENT)
    public static void initModelsAndVariants() {
        for (Item item : ItemInit.ITEMS) {
            initModelAndVariants(item);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void initModelAndVariants(Item item) {
        if (!item.getHasSubtypes()) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), null));
        }
    }

}
