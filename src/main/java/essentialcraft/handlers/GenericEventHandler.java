package essentialcraft.handlers;

import java.awt.List;

import javax.annotation.Nullable;

import org.jline.terminal.MouseEvent.Button;
import org.lwjgl.input.Mouse;

import essentialcraft.api.AttributeImprintProvider;
import essentialcraft.api.CapabilityAttributeImprint;
import essentialcraft.api.CapabilityMRULattice;
import essentialcraft.api.CapabilityMRUStorage;
import essentialcraft.api.MRULattice;
import essentialcraft.api.MRULatticeProvider;
import essentialcraft.api.MRUStorage;
import essentialcraft.api.MRUStorageProvider;
import essentialcraft.api.Main;
import essentialcraft.common.items.ItemAttributeMold;
import essentialcraft.common.items.ItemConductor;
import essentialcraft.common.items.ItemCrowbar;
import essentialcraft.init.ItemInit;
import essentialcraft.network.packets.Network;
import essentialcraft.network.packets.PacketUpdateLattice;
import essentialcraft.network.packets.PacketUpdateStorage;
import essentialcraft.util.IMRUStorage;
import essentialcraft.util.ILeavesImprint;
import essentialcraft.util.IMRULattice;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
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
    public static void attachCapabilityItemStack(AttachCapabilitiesEvent<ItemStack> event) {

        if (event.getObject().getItem() instanceof ILeavesImprint) {
            event.addCapability(CapabilityAttributeImprint.KEY, new AttributeImprintProvider());
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
            if (event.getItemStack().hasCapability(CapabilityAttributeImprint.CAP, null)) {
                System.out.println("yes");
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
                    entity.entityDropItem(new ItemStack(Items.DYE, 2 + entity.getEntityWorld().rand.nextInt(4), 15), 0.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void leaveImprint(AttackEntityEvent event) {
        ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
        InventoryPlayer inventory = event.getEntityPlayer().inventory;

        if (heldItem.hasCapability(CapabilityAttributeImprint.CAP, null)) {

            if (inventory.hasItemStack(ItemInit.MOLD.getDefaultInstance())) {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack stackInSlot = inventory.getStackInSlot(i);
                    if (stackInSlot.getItem().equals(ItemInit.MOLD)) {
                        if (stackInSlot.getSubCompound(Main.MODID) != null) {
                            if (stackInSlot.getSubCompound(Main.MODID).getDouble("key") == heldItem.getSubCompound(Main.MODID).getDouble("key")) {

                                ItemStack moldItem = inventory.getStackInSlot(i);
                                //This should be redone
                                moldItem.getCapability(CapabilityAttributeImprint.CAP, null).addAmount(1);
                                System.out.println("Imprint (finally) received by inventory");
                                break;
                            }
                        }
                    }
                }
            } else {

                heldItem.getCapability(CapabilityAttributeImprint.CAP, null).addAmount(1);
                System.out.println("Imprint received by the main item");
            }
        }
    }


    @SubscribeEvent
    public static void bindAttributeMold(MouseInputEvent event) {
        if (event.getGui() instanceof GuiInventory) {
            if (Mouse.getEventButton() == 1) {
                //Now this is only possible in (survival!) player inventory, which makes it useless in others
                GuiInventory inventory = ((GuiInventory)event.getGui());
                InventoryPlayer player = Minecraft.getMinecraft().player.inventory;
                ItemStack lyingStack = inventory.getSlotUnderMouse().getStack();
                ItemStack carriedStack = player.getItemStack();

                if (lyingStack.hasCapability(CapabilityAttributeImprint.CAP, null)) {
                    //                    if (!carriedStack.isEmpty() && carriedStack.getSubCompound(Main.MODID) == null) {
                    if (lyingStack.getCapability(CapabilityAttributeImprint.CAP, null).getAmount() == 0) {
                        //I think the whole reason why this doesn't work is yet again synchronization
                        System.out.println("1");

                        if (carriedStack.getItem().equals(ItemInit.MOLD)) {
                            System.out.println("2");

                            NBTTagCompound tagCarried = carriedStack.getOrCreateSubCompound(Main.MODID);
                            NBTTagCompound tagLying = lyingStack.getOrCreateSubCompound(Main.MODID);
                            Double key = Minecraft.getMinecraft().player.world.rand.nextGaussian();

                            tagCarried.setString("item_type", lyingStack.getClass().toString());
                            tagCarried.setDouble("key", key);
                            tagLying.setDouble("key", key);

                            lyingStack.getCapability(CapabilityAttributeImprint.CAP, null).setRequiredAmount(ItemCrowbar.requiredImprint);

                            event.setCanceled(true);
                        }
                    }
                }
            }
        }
    }

}
