package essentialcraft.handlers;

import javax.annotation.Nullable;

import essentialcraft.api.CapabilityMRULattice;
import essentialcraft.api.CapabilityMRUStorage;
import essentialcraft.api.MRULattice;
import essentialcraft.api.MRULatticeProvider;
import essentialcraft.api.MRUStorage;
import essentialcraft.api.MRUStorageProvider;
import essentialcraft.api.Main;
import essentialcraft.common.items.ItemConductor;
import essentialcraft.common.items.ItemCrowbar;
import essentialcraft.network.packets.Network;
import essentialcraft.network.packets.PacketUpdateLattice;
import essentialcraft.network.packets.PacketUpdateStorage;
import essentialcraft.util.IMRUStorage;
import essentialcraft.util.IMRULattice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class GenericEventHandler {

    @SubscribeEvent
    public static void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof EntityPlayer) {

            event.addCapability(CapabilityMRUStorage.KEY, new MRUStorageProvider());
            event.addCapability(CapabilityMRULattice.KEY, new MRULatticeProvider());
        }
    }

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
        }
    }

    @SubscribeEvent
    public static void receiveLatticeCharge(LivingDeathEvent event) {

        EntityLivingBase entity = event.getEntityLiving();
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            IMRUStorage storage = player.getCapability(CapabilityMRUStorage.CAP, null);
            storage.addAmount((int) entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
            Network.sendToPlayer(new PacketUpdateStorage(storage.getAmount()), (EntityPlayerMP) player);

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
                    entity.entityDropItem(new ItemStack(Items.DYE, 1 + entity.getEntityWorld().rand.nextInt(3), 15), 0.0F);
                }
            }
        }
    }
}
