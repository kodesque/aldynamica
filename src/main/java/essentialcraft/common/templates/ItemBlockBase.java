package essentialcraft.common.templates;

import essentialcraft.api.IHasModel;
import essentialcraft.init.ItemInit;
import essentialcraft.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockBase extends ItemBlock implements IHasModel {

    public ItemBlockBase(Block block) {
        super(block);

        this.setRegistryName(block.getRegistryName());
        this.setTranslationKey(block.getTranslationKey());
        this.setCreativeTab(Main.tabEssentialCraft);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
