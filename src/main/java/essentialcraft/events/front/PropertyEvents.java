package essentialcraft.events.front;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import essentialcraft.root.Main;
import essentialcraft.util.PropertyUtil;
import essentialcraft.util.PropertyUtil.PropertyBundle;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.oredict.OreDictionary;

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
        EntityPlayer player = event.getEntityPlayer();
        NBTTagCompound nbt = original.getSubCompound(PropertyUtil.PROPID);

        if ((nbt == null) || (nbt.getInteger(PropertyUtil.durability_key) <= 0)) return;

        original.setItemDamage(original.getItemDamage() - 1);
        nbt.setInteger(PropertyUtil.durability_key, Math.max((nbt.getInteger(PropertyUtil.durability_key) - 1), 0));

        if (!player.addItemStackToInventory(original.copy())) {
            player.dropItem(original.copy(), false);
        }
    }

    @SubscribeEvent
    public static void addMiningSpeed(PlayerEvent.BreakSpeed event) {

        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        PropertyBundle props = PropertyUtil.readProperties(stack);
        if (props.isEmpty()) return;
        if (PropertyUtil.isToolOrArmor(stack) != PropertyUtil.UseType.BREAK) return;

        float toModify = event.getOriginalSpeed();

        event.setNewSpeed((float)(toModify + (props.hardness * 2)));
    }

    @SubscribeEvent
    public static void renderPropertiesTooltip(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();
        NBTTagCompound nbt = stack.getSubCompound(PropertyUtil.PROPID);

        if (nbt == null) return;

        if (nbt.getInteger(PropertyUtil.durability_key) != 0) {
            tips.add(PropertyUtil.spare_durability_lang.getFormattedText() + " " + nbt.getInteger(PropertyUtil.durability_key));
        }

        tips.add(PropertyUtil.lightness_lang.getFormattedText() + " " + nbt.getDouble(PropertyBundle.lightness_key));
        tips.add(PropertyUtil.stiffness_lang.getFormattedText() + " " + nbt.getDouble(PropertyBundle.stiffness_key));
        tips.add(PropertyUtil.hardness_lang.getFormattedText() + " " + nbt.getDouble(PropertyBundle.hardness_key));
    }

    //TODO: don't forget to remove on release
    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {
        ItemStack item = event.getItemStack();
        if (!event.getWorld().isRemote) {
            if (PropertyUtil.isIngotOrGem(item)) {
                PropertyUtil.rollAndWriteProperties(item, event.getWorld().rand);
            }
        }
    }

}
