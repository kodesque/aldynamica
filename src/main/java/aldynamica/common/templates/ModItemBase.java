package aldynamica.common.templates;

import aldynamica.api.IHasModel;
import aldynamica.common.register.ModItems;
import aldynamica.network.proxy.ClientProxy;
import aldynamica.root.Main;
import net.minecraft.item.Item;

public class ModItemBase extends Item implements IHasModel{

    public ModItemBase(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
