package aldynamica.common.templates;

import aldynamica.common.registry.ItemRegistry;
import aldynamica.network.proxy.ClientProxy;
import aldynamica.root.Main;
import net.minecraft.item.Item;

public class ALItemBase extends Item{

    public ALItemBase(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        ItemRegistry.ITEMS.add(this);
    }

    //    @Override
    //    public void registerModels() {
    //        Main.proxy.registerItemRenderer(this, 0, "inventory");
    //    }

}
