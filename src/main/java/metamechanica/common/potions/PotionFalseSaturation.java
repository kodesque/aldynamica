package metamechanica.common.potions;

import metamechanica.init.PotionInit;
import metamechanica.root.Main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;

public class PotionFalseSaturation extends Potion{

    public String initialFoodLevelKey = "initialFoodLevel";
    public String lastFoodLevelKey = "lastFoodLevel";
    public String damageToDealKey = "accumulatedDamage";


    public PotionFalseSaturation(String name) {
        super(false, 0);
        this.setRegistryName(Main.MODID, name);
        this.setPotionName("effect." + name);

        PotionInit.POTIONS.add(this);
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {

        if (entityLivingBaseIn instanceof EntityPlayer) {

            EntityPlayer playerIn = (EntityPlayer)entityLivingBaseIn;
            NBTTagCompound playerData = playerIn.getEntityData();

            if (playerData != null) {

                int remainingTime = playerIn.getActivePotionEffect(PotionInit.FALSE_SATURATION).getDuration();
                int damageToDeal = playerData.getInteger(this.damageToDealKey);

                int currentFoodLevel = playerIn.getFoodStats().getFoodLevel();
                int initialFoodLevel = playerData.getInteger(this.initialFoodLevelKey);
                int finalFoodLevel = initialFoodLevel - damageToDeal;

                if (!playerData.hasKey(this.initialFoodLevelKey) &&
                        !playerData.hasKey(this.lastFoodLevelKey)) {

                    playerData.setInteger(this.initialFoodLevelKey, currentFoodLevel);
                    playerData.setInteger(this.lastFoodLevelKey, currentFoodLevel);
                }

                //all good, plays once

                if (playerData.getInteger(this.lastFoodLevelKey) != currentFoodLevel) {
                    damageToDeal++;
                    playerData.setInteger(this.damageToDealKey, damageToDeal);
                    playerData.setInteger(this.lastFoodLevelKey, currentFoodLevel);
                    playerIn.getFoodStats().setFoodLevel(20);
                }

                //if there is a difference between current and old food level, increase damage

                if (remainingTime <= 1) {
                    entityLivingBaseIn.attackEntityFrom(DamageSource.causeIndirectDamage(playerIn, entityLivingBaseIn), damageToDeal);

                    if (finalFoodLevel < 0) {
                        finalFoodLevel = 0;
                    }

                    playerIn.getFoodStats().setFoodLevel(finalFoodLevel);

                    playerData.removeTag(this.damageToDealKey);
                    playerData.removeTag(this.initialFoodLevelKey);
                    playerData.removeTag(this.lastFoodLevelKey);
                }

                //when effect ends, deal the damage and nullify the tags
            }
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}



