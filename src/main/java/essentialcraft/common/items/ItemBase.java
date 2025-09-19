package essentialcraft.common.items;

import essentialcraft.api.Main;
import essentialcraft.init.ItemInit;
import essentialcraft.network.proxy.ClientProxy;
import essentialcraft.util.IHasModel;
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
