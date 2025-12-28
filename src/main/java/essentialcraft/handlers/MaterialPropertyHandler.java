package essentialcraft.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import essentialcraft.api.Main;
import essentialcraft.util.MaterialPropertyUtil;
import essentialcraft.util.MaterialPropertyUtil.PropertyBundle;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class MaterialPropertyHandler {

    public static String type_key = "type";

    public static String induced_durability_key = "induced_durability";
    public static String induced_damage_key = "induced_damage";
    public static String induced_attack_speed_key = "induced_attack_speed";
    public static String induced_movement_speed_key = "induced_movement_speed";
    public static String induced_protection_key = "induced_protection";

    public static enum types {
        BREAK,
        ATTACK,
        DEFEND,
        OTHER
    }

    @SubscribeEvent
    public static void onItemCrafted(ItemCraftedEvent event) {
        IInventory inv = event.craftMatrix;
        ItemStack result = event.crafting;

        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        types found = getToolOrArmor(result);

        if (found == null) return;

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (isIngotOrGem(stack)) {
                list.add(stack);
            }
        }

        if (list.isEmpty()) return;

        NBTTagCompound nbt = result.getOrCreateSubCompound(Main.MODID);

        for (types type : types.values()) {
            if (found == type) {
                nbt.setString(type_key, type.toString());
                break;
            }
        }

        PropertyBundle toAdd = MaterialPropertyUtil.aggregateProperties(list);

        //general logic, processing in *useSpareDurability*
        nbt.setInteger(induced_durability_key, (int)toAdd.durability * 100);
        MaterialPropertyUtil.setProperties(result, toAdd);

        switch(found) {
            case BREAK:
                //processing additional logic in *useInducedMiningSpeed*
                break;
            case DEFEND:

                EntityEquipmentSlot applicable = ((ItemArmor)result.getItem()).getEquipmentSlot();

                UUID id_moveSpeed = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0001");
                AttributeModifier attrMoveSpeed = new AttributeModifier(id_moveSpeed, induced_movement_speed_key, 0.5 * toAdd.lightness, 0);
                result.addAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), attrMoveSpeed, applicable);

                UUID id_protect = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0002");
                AttributeModifier attrProtection = new AttributeModifier(id_protect, induced_protection_key, 0.5 * toAdd.hardness, 0);
                result.addAttributeModifier(SharedMonsterAttributes.ARMOR.getName(), attrProtection, applicable);

                break;
            case ATTACK:

                UUID id_attackSpeed = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0003");
                AttributeModifier attrAttackSpeed = new AttributeModifier(id_attackSpeed, induced_damage_key, 0.5 * toAdd.lightness, 0);
                result.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), attrAttackSpeed, EntityEquipmentSlot.MAINHAND);

                UUID id_attack = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0004");
                AttributeModifier attrAttack = new AttributeModifier(id_attack, induced_damage_key, 5 * toAdd.hardness, 0);
                result.addAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), attrAttack, EntityEquipmentSlot.MAINHAND);

                break;
            case OTHER:
                //no additional logic provided, skipping
                break;
        }

        //TODO: add some kind of sub-sub compound, because now anything that adds Main.MODID is counted as fine
    }

    @SubscribeEvent
    public static void onPlayerHitEvent(LivingDamageEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
    }

    @SubscribeEvent
    public static void useSpareDurability(PlayerDestroyItemEvent event) {

        ItemStack original = event.getOriginal();
        EntityPlayer player = event.getEntityPlayer();
        NBTTagCompound nbt = original.getSubCompound(Main.MODID);

        if ((nbt == null) || (nbt.getInteger(induced_durability_key) <= 0)) return;

        original.setItemDamage(original.getItemDamage() - 1);
        nbt.setInteger(induced_durability_key, nbt.getInteger(induced_durability_key) - 1);
    }

    @SubscribeEvent
    public static void useInducedMiningSpeed(PlayerEvent.BreakSpeed event) {

        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        PropertyBundle props = MaterialPropertyUtil.getProperties(stack);
        if (props.isEmpty()) return;

        float toModify = event.getOriginalSpeed();

        event.setNewSpeed((float)(toModify + props.hardness));
    }

    @SubscribeEvent
    public static boolean onTooltipDraw(ItemTooltipEvent event) {
        List<String> tips = event.getToolTip();
        ItemStack stack = event.getItemStack();
        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (nbt == null) return true;

        tips.add(MaterialPropertyUtil.induced_durability.getFormattedText() + " " + nbt.getInteger(induced_durability_key));

        tips.add(MaterialPropertyUtil.material_lightness.getFormattedText() + " " + nbt.getDouble(PropertyBundle.material_lightness));
        tips.add(MaterialPropertyUtil.material_durability.getFormattedText() + " " + nbt.getDouble(PropertyBundle.material_durability));
        tips.add(MaterialPropertyUtil.material_hardness.getFormattedText() + " " + nbt.getDouble(PropertyBundle.material_hardness));
        return true;
    }

    public static boolean isIngotOrGem(ItemStack stack) {
        if (stack.isEmpty()) return false;

        for (int id : OreDictionary.getOreIDs(stack)) {
            String name = OreDictionary.getOreName(id);
            if (name.startsWith("ingot") || name.startsWith("gem"))
                return true;
        }
        return false;
    }

    public static types getToolOrArmor(ItemStack stack) {

        Set<String> classes = stack.getItem().getToolClasses(stack);

        if (classes.contains("pickaxe") || classes.contains("axe") || classes.contains("shovel"))
            return types.BREAK;
        else if ((classes.contains("sword")))
            return types.ATTACK;
        else if (stack.getItem() instanceof ItemArmor)
            return types.DEFEND;
        else if (stack.getItem() instanceof ItemTool)
            return types.OTHER;
        return null;
    }

    @SubscribeEvent
    public static void debug(PlayerInteractEvent.RightClickItem event) {
        ItemStack item = event.getItemStack();
        if (!event.getWorld().isRemote) {
            if (isIngotOrGem(item)) {
                MaterialPropertyUtil.rollAndSetProperties(item);
            }
        }
    }

}
