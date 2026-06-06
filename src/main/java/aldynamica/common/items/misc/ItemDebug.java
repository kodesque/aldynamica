package aldynamica.common.items.misc;

import aldynamica.api.EnumSortGroup;
import aldynamica.common.templates.ALItemBase;
import aldynamica.root.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDebug extends ALItemBase{

    public ItemDebug(String name, EnumSortGroup group) {
        super(name, group);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != Main.tabMod) return;

        items.clear();

        //add
    }

}
