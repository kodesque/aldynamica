package aldynamica.common.templates;

import aldynamica.api.IHasModel;
import aldynamica.common.registry.ItemRegistry;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ALItemBlockBase extends ItemBlock implements IHasModel {

    public ALItemBlockBase(Block block) {
        super(block);

        this.setRegistryName(block.getRegistryName());
        this.setTranslationKey(block.getTranslationKey());

        ItemRegistry.ITEMS.add(this);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
