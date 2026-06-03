package aldynamica.common.register;

import java.util.List;

import aldynamica.api.IHasMeta;
import aldynamica.common.items.misc.ItemDebug;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {

    public static List<Item> ITEMS;

    public static Item DEBUG;

    public static void initItems(IForgeRegistry<Item> iForgeRegistry) {

        iForgeRegistry.register(ModItems.DEBUG = new ItemDebug("debug"));

    }

    @SideOnly(Side.CLIENT)
    public static void initModelsAndVariants() {
        for (Item item : ModItems.ITEMS) {
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
