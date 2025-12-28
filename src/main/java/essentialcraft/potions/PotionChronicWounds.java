package essentialcraft.potions;

import essentialcraft.api.Main;
import essentialcraft.init.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionChronicWounds extends Potion{

    public PotionChronicWounds(String name) {
        super(false, 0);
        this.setRegistryName(Main.MODID, name);
        this.setPotionName("effect." + name);

        PotionInit.POTIONS.add(this);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof EntityPlayer) {
            EntityPlayer playerIn = (EntityPlayer)entityLivingBaseIn;
        }
    }


}
