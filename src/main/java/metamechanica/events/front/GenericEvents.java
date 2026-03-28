package metamechanica.events.front;

import java.util.Map;

import metamechanica.capabilities.register.CapabilityAttributeImprint;
import metamechanica.capabilities.register.CapabilityDARLattice;
import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.common.items.complex.ItemAttributeMold;
import metamechanica.common.items.tools.ItemCrowbar;
import metamechanica.init.ItemInit;
import metamechanica.root.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@Mod.EventBusSubscriber
public class GenericEvents {

    @SubscribeEvent
    public static void crushBones(LivingDeathEvent event) {

        EntityLivingBase entity = event.getEntityLiving();
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            ItemStack stack  = player.getHeldItem(player.getActiveHand());
            if (stack.getItem() instanceof ItemCrowbar) {
                if (entity.getEntityWorld().rand.nextInt(4) == 1) {
                    entity.entityDropItem(new ItemStack(Items.DYE, 2 + entity.getEntityWorld().rand.nextInt(4), 15), 0.0F);
                }
            }
        }
    }


}
