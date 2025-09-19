package essentialcraft.common.items;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemCaviar extends ItemBase {

    public ItemCaviar(String name) {
        super(name);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            if (!((EntityPlayer)entityIn).isCreative()) {
                if (worldIn.rand.nextInt(100) == 0) {
                    stack.shrink(1);
                    for (int i = 0; i < 4; i += 1 + worldIn.rand.nextInt(2)) {
                        Entity spawn = new EntitySilverfish(worldIn);
                        ((EntityLivingBase)spawn).addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
                        spawn.setGlowing(true);
                        //                        spawn.setInvisible(true);
                        //This doesn't work for sr
                        //Enchanted item + "energetic entities"
                        ((EntityLivingBase)spawn).setHealth(4);
                        spawn.setPosition(entityIn.posX + worldIn.rand.nextDouble(), entityIn.posY, entityIn.posZ + worldIn.rand.nextDouble());

                        //                        Scoreboard scoreboard = worldIn.getScoreboard();
                        //                        ScorePlayerTeam team = scoreboard.getTeam("darkPurpleTeam");
                        //
                        //                        if (team == null) {
                        //                            team = scoreboard.createTeam("darkPurpleTeam");
                        //                            team.setColor(TextFormatting.DARK_PURPLE);
                        //                        }
                        //
                        //                        scoreboard.addPlayerToTeam(spawn.getCachedUniqueIdString(), "darkPurpleTeam");


                        worldIn.spawnEntity(spawn);

                        //How to do this without any crutches?
                    }
                    for (int k = 0; k < 20; ++k)
                    {
                        double d2 = worldIn.rand.nextGaussian() * 0.02D;
                        double d0 = worldIn.rand.nextGaussian() * 0.02D;
                        double d1 = worldIn.rand.nextGaussian() * 0.02D;
                        worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, entityIn.posX + worldIn.rand.nextFloat() * entityIn.width * 2.0F - entityIn.width, entityIn.posY + worldIn.rand.nextFloat() * entityIn.height, entityIn.posZ + worldIn.rand.nextFloat() * entityIn.width * 2.0F - entityIn.width, d2, d0, d1);
                    }
                }
            }
        }
    }

}
