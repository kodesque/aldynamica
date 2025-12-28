package essentialcraft.potions;

import essentialcraft.api.Main;
import essentialcraft.init.PotionInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionMindtrick extends Potion {

    public PotionMindtrick(String name) {
        super(false, 0);
        this.setRegistryName(Main.MODID, name);
        this.setPotionName("effect." + name);

        PotionInit.POTIONS.add(this);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {

        if (entityLivingBaseIn instanceof EntityPlayer) {

            EntityPlayer playerIn = (EntityPlayer)entityLivingBaseIn;



            if (playerIn.experience < playerIn.xpBarCap()) {
                playerIn.addExperienceLevel(-1);
            } else {
                playerIn.addExperience(-1);
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

}
