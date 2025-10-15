package essentialcraft.handlers;

import java.awt.List;
import java.util.Map;

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
import essentialcraft.network.packets.PacketBindMold;
import essentialcraft.network.packets.PacketUpdateLattice;
import essentialcraft.network.packets.PacketUpdateStorage;
import essentialcraft.util.IMRUStorage;
import essentialcraft.util.IAttributeImprint;
import essentialcraft.util.ILeavesImprint;
import essentialcraft.util.IMRULattice;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AnvilUpdateEvent;
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

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (!right.isEmpty() && right.getItem() instanceof ItemAttributeMold) {
            if (right.isItemEnchanted()) {
                ItemStack output = left.copy();

                Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(right);

                enchants.forEach((ench, lvl) -> output.addEnchantment(ench, lvl));

                event.setOutput(output);
                event.setCost(10);
            }
        }
    }
}
