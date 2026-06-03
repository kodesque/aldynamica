package aldynamica.common.templates;

import aldynamica.api.IHasModel;
import aldynamica.common.register.ModItems;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ModItemBlockBase extends ItemBlock implements IHasModel {

    public ModItemBlockBase(Block block) {
        super(block);

        this.setRegistryName(block.getRegistryName());
        this.setTranslationKey(block.getTranslationKey());

        ModItems.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
