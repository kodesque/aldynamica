package metamechanica.common.templates;

import metamechanica.api.IHasModel;
import metamechanica.init.ItemInit;
import metamechanica.network.proxy.ClientProxy;
import metamechanica.root.Main;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel{

    public ItemBase(String name) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
