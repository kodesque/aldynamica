package metamechanica.common.items.misc;

import metamechanica.api.IHasModel;
import metamechanica.api.ILeavesImprint;
import metamechanica.common.templates.ItemBase;
import metamechanica.init.ItemInit;
import metamechanica.root.Main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDebug extends ItemBase implements ILeavesImprint{

    public ItemDebug(String name) {
        super(name);
    }

    @Override
    public int getRequiredImprint() {
        // TODO Auto-generated method stub
        return 5;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        target.setFire(5);
        return true;
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

        items.add(new ItemStack (ItemInit.DUMMY));
        items.add(new ItemStack (ItemInit.MOLD));
        items.add(new ItemStack (ItemInit.CROWBAR));
        items.add(new ItemStack (ItemInit.WEDGE));

        items.add(new ItemStack (ItemInit.RECORD_FOUND));
        items.add(new ItemStack (ItemInit.RECORD_WART));

        items.add(new ItemStack (ItemInit.DEPOSIT_CORUNDUM));
        items.add(new ItemStack (ItemInit.DEPOSIT_GARNET));
        items.add(new ItemStack (ItemInit.DEPOSIT_RHINESTONE));

        items.add(new ItemStack (ItemInit.BEDROCK_BRACE));

        items.add(new ItemStack (ItemInit.GEM_CORUNDUM));
        items.add(new ItemStack (ItemInit.GEM_GARNET));
        items.add(new ItemStack (ItemInit.GEM_RHINESTONE));

        items.add(new ItemStack (ItemInit.CAVIAR));
    }

}
