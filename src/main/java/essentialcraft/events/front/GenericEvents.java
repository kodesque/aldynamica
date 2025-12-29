package essentialcraft.events.front;

import java.util.Map;

import essentialcraft.capabilities.register.CapabilityAttributeImprint;
import essentialcraft.capabilities.register.CapabilityMRULattice;
import essentialcraft.capabilities.register.CapabilityMRUStorage;
import essentialcraft.common.items.ItemAttributeMold;
import essentialcraft.common.items.ItemCrowbar;
import essentialcraft.init.ItemInit;
import essentialcraft.root.Main;
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
    public static void debug (PlayerInteractEvent.RightClickItem event) {
        Item item = event.getItemStack().getItem();
        if (!event.getWorld().isRemote) {
            if (item.equals(Items.APPLE)) {
                if(event.getEntityPlayer().getCapability(CapabilityMRULattice.CAP, null) != null && event.getEntityPlayer().getCapability(CapabilityMRUStorage.CAP, null) != null) {

                    System.out.println(event.getEntityPlayer().getCapability(CapabilityMRUStorage.CAP, null).getAmount());
                    System.out.println(event.getEntityPlayer().getCapability(CapabilityMRULattice.CAP, null).getAmount());
                }
            }

            if (event.getItemStack().hasCapability(CapabilityAttributeImprint.CAP, null)) {
                System.out.println("yes");
            }

            if (event.getItemStack().getItem().equals(ItemInit.MOLD)) {
                if (event.getItemStack().hasCapability(CapabilityAttributeImprint.CAP, null)) {
                    System.out.println(event.getItemStack().getCapability(CapabilityAttributeImprint.CAP, null).getAmount());
                    if (event.getItemStack().getSubCompound(Main.MODID) != null) {
                        System.out.println(event.getItemStack());
                    }
                }
            }
        }
    }

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
