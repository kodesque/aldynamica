package aldynamica.shelved;

import java.util.ArrayList;
import java.util.List;

import aldynamica.shelved.PropertyUtil.PropertyBundle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

@Mod.EventBusSubscriber
public class PropertyEvents {

    @SubscribeEvent
    public static void modifyCraftingResult(ItemCraftedEvent event) {
        IInventory inv = event.craftMatrix;
        ItemStack stack = event.crafting;

        PropertyUtil.UseType found = PropertyUtil.isToolOrArmor(stack);
        if (found == null) return;

        ArrayList<ItemStack> list = PropertyUtil.scanCraftingMatrix(inv);
        if (list.isEmpty()) return;

        PropertyBundle props = PropertyUtil.aggregateProperties(list);
        PropertyUtil.writeProperties(stack, props);

        PropertyUtil.applyRelatedAttributes(stack, found, props);

    }

    @SubscribeEvent
    public static void useSpareDurability(PlayerDestroyItemEvent event) {

        ItemStack original = event.getOriginal();
        ItemStack safe = original.copy();
        EntityPlayer player = event.getEntityPlayer();
        NBTTagCompound nbt = safe.getSubCompound(PropertyUtil.PROPID);

        if (original.getItem() instanceof ItemArmor) return;

        if ((nbt == null) || (nbt.getInteger(PropertyUtil.durability_key) <= 0)) return;

        safe.setItemDamage(original.getItemDamage() - nbt.getInteger(PropertyUtil.durability_key));
        nbt.setInteger(PropertyUtil.durability_key, Math.max((nbt.getInteger(PropertyUtil.durability_key) - original.getItemDamage()), 0));

        if (!player.addItemStackToInventory(safe)) {
            player.dropItem(safe, false);
        }
    }

    @SubscribeEvent
    public static void addMiningSpeed(PlayerEvent.BreakSpeed event) {

        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        PropertyBundle props = PropertyUtil.readProperties(stack);
        if (props.isEmpty()) return;
        if (PropertyUtil.isToolOrArmor(stack) != PropertyUtil.UseType.BREAK) return;

        float toModify = event.getNewSpeed();

        event.setNewSpeed((float)(toModify + (props.hardness * 4)));
    }

    @SubscribeEvent
    public static void renderPropertiesTooltip(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();
        NBTTagCompound nbt = stack.getSubCompound(PropertyUtil.PROPID);

        if (nbt == null) return;

        double lightness = nbt.getDouble(PropertyBundle.lightness_key);
        double stiffness = nbt.getDouble(PropertyBundle.stiffness_key);
        double hardness = nbt.getDouble(PropertyBundle.hardness_key);

        if (nbt.getInteger(PropertyUtil.durability_key) != 0) {
            tips.add(PropertyUtil.spare_durability_lang.getFormattedText() + " " + TextFormatting.GREEN + nbt.getInteger(PropertyUtil.durability_key));
        }

        tips.add(PropertyUtil.lightness_lang.getFormattedText() + " " + TextFormatting.YELLOW + lightness);
        tips.add(PropertyUtil.stiffness_lang.getFormattedText() + " " + TextFormatting.YELLOW + stiffness);
        tips.add(PropertyUtil.hardness_lang.getFormattedText() + " " + TextFormatting.YELLOW + hardness);
    }

    //TODO: don't forget to remove on release
    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {
        ItemStack item = event.getItemStack();
        if (!event.getWorld().isRemote) {
            if (PropertyUtil.isIngotOrGem(item)) {
                PropertyUtil.rollAndWriteProperties(item, event.getWorld().rand);
                System.out.print(item.getSubCompound(PropertyUtil.PROPID));
            }
        }
    }

}
