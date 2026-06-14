package aldynamica.common.templates;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaItem;
import aldynamica.common.init.ItemInit;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ALItemBlockBase extends ItemBlock implements IAldynamicaItem {

    private EnumSortGroup type;

    public ALItemBlockBase(Block block, EnumSortGroup group) {
        super(block);

        this.setRegistryName(block.getRegistryName());
        this.setTranslationKey(block.getTranslationKey());

        this.setCreativeTab(Main.tabMod);

        this.type = group;

        ItemInit.ITEMS.add(this);
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

    @Override
    public boolean isTool() {
        return false;
    }
}
