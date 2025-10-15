package essentialcraft.handlers;

import org.lwjgl.input.Mouse;

import essentialcraft.api.CapabilityAttributeImprint;
import essentialcraft.api.Main;
import essentialcraft.common.items.ItemAttributeMold;
import essentialcraft.network.packets.Network;
import essentialcraft.network.packets.PacketBindMold;
import essentialcraft.util.IAttributeImprint;
import essentialcraft.util.ILeavesImprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class AttributeHandler {

    @SubscribeEvent
    public static void leaveImprint(AttackEntityEvent event) {

        InventoryPlayer inventory = event.getEntityPlayer().inventory;

        ItemStack heldItem = event.getEntityPlayer().getHeldItemMainhand();
        NBTTagCompound nbt_held = heldItem.getSubCompound(Main.MODID);
        IAttributeImprint cap_held = heldItem.getCapability(CapabilityAttributeImprint.CAP, null);

        boolean hasUpdated = false;
        boolean wasFound = false;

        if (heldItem.getItem() instanceof ILeavesImprint && nbt_held != null) {

            if (cap_held.getRequiredAmount() != 0) {
                ItemStack recordedItem = inventory.getStackInSlot(nbt_held.getInteger(ItemAttributeMold.last_location));
                hasUpdated = tryAddImprint(recordedItem, nbt_held, heldItem);
                wasFound = true;
            }

            if (!wasFound) {
                for (int i = 0; i < inventory.getSizeInventory(); i++) {

                    ItemStack storedItem = inventory.getStackInSlot(i);
                    if (tryAddImprint(storedItem, nbt_held, heldItem)) {
                        nbt_held.setInteger(ItemAttributeMold.last_location, i);
                        hasUpdated = true;
                        break;
                    }
                }
            }
            if (!hasUpdated) {
                cap_held.setStoringTypeAndRequiredAmount(true, ((ILeavesImprint)heldItem.getItem()).getRequiredImprint());
                cap_held.addAmount(1);
            }
        }
    }

    public static boolean tryAddImprint(ItemStack stack_inv, NBTTagCompound nbt_hand, ItemStack stack_hand) {

        NBTTagCompound tag_inv = stack_inv.getSubCompound(Main.MODID);
        if (stack_inv.getItem() instanceof ItemAttributeMold) {
            if (tag_inv != null) {
                if (nbt_hand.getDouble(ItemAttributeMold.key) == tag_inv.getDouble(ItemAttributeMold.key)) {

                    IAttributeImprint cap = stack_inv.getCapability(CapabilityAttributeImprint.CAP, null);
                    if (((ILeavesImprint)stack_hand.getItem()).getRequiredImprint() == cap.getAmount() + 1 &&
                            !stack_inv.isItemEnchanted()) {
                        stack_inv.addEnchantment(Enchantments.FIRE_ASPECT, 1);
                    }
                    cap.addAmount(1);

                    return true;
                }
            }
        }
        return false;
    }


    @SubscribeEvent
    public static void bindAttributeMold(MouseInputEvent event) {
        if (Mouse.getEventButton() == 1) {

            Slot slot = ((GuiContainer)event.getGui()).getSlotUnderMouse();
            if (slot != null) {
                ItemStack stackLying = slot.getStack();

                ItemStack stackCarried = Minecraft.getMinecraft().player.inventory.getItemStack();

                if (stackCarried.getItem() instanceof ItemAttributeMold) {

                    if (stackLying.getCapability(CapabilityAttributeImprint.CAP, null).getAmount() == 0) {

                        Double key = Minecraft.getMinecraft().player.world.rand.nextGaussian();

                        NBTTagCompound tagCarried = stackCarried.getOrCreateSubCompound(Main.MODID);
                        tagCarried.setDouble(ItemAttributeMold.key, key);
                        tagCarried.setString(ItemAttributeMold.bound_item, stackLying.getDisplayName());

                        stackCarried.getCapability(CapabilityAttributeImprint.CAP, null).setStoringTypeAndRequiredAmount(
                                false, ((ILeavesImprint)stackLying.getItem()).getRequiredImprint());

                        Network.sendToPlayerSP(new PacketBindMold(slot.getSlotIndex(), key));

                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}
