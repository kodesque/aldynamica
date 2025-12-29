package essentialcraft.common.items;

import essentialcraft.api.IHasModel;
import essentialcraft.api.ILeavesImprint;
import essentialcraft.common.templates.ItemBase;
import essentialcraft.root.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ItemShowcase extends ItemBase implements ILeavesImprint{

    public ItemShowcase(String name) {
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
