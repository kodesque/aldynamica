package metamechanica.common.items;

import metamechanica.api.IHasModel;
import metamechanica.api.ILeavesImprint;
import metamechanica.common.templates.ItemBase;
import metamechanica.root.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

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

}
