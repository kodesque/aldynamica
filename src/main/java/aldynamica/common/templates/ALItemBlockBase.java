package aldynamica.common.templates;

import aldynamica.common.init.ItemInit;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ALItemBlockBase extends ItemBlock {

    public ALItemBlockBase(Block block) {
        super(block);

        this.setRegistryName(block.getRegistryName());
        this.setTranslationKey(block.getTranslationKey());

        this.setCreativeTab(Main.tabMod);

        ItemInit.ITEMS.add(this);
    }
}
