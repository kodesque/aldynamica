package essentialcraft.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import essentialcraft.root.Main;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;

public class PropertyUtil {

    public static String PROPID = Main.MODID + "." + "properties";

    public static TextComponentTranslation lightness_lang = new TextComponentTranslation("tooltip." + PROPID + "." + PropertyBundle.lightness_key);
    public static TextComponentTranslation stiffness_lang = new TextComponentTranslation("tooltip." + PROPID + "." + PropertyBundle.stiffness_key);
    public static TextComponentTranslation hardness_lang = new TextComponentTranslation("tooltip." + PROPID + "." + PropertyBundle.hardness_key);

    public static TextComponentTranslation spare_durability_lang = new TextComponentTranslation("tooltip." + PROPID + "." + "spare_durability");

    public static String durability_key = "durability";
    public static String damage_key = "damage";
    public static String attack_speed_key = "attack_speed";
    public static String movement_speed_key = "movement_speed";
    public static String protection_key = "protection";

    public static enum UseType {
        BREAK,
        ATTACK,
        DEFEND,
        OTHER
    }

    public static ArrayList<ItemStack> scanCraftingMatrix(IInventory inv) {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();

        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (isIngotOrGem(stack)) {
                list.add(stack);
            }
        }

        return list;
    }

    public static void applyRelatedAttributes(ItemStack stack, UseType found, PropertyBundle props) {

        NBTTagCompound nbt = stack.getSubCompound(PROPID);

        //general logic, processing in *PropertyHandler#useSpareDurability*
        nbt.setInteger(PropertyUtil.durability_key, (int)(props.stiffness * 100));

        switch(found) {
            case BREAK:

                //processing additional logic in *PropertyHandler#addMiningSpeed*
                break;
            case DEFEND:

                EntityEquipmentSlot applicable = ((ItemArmor)stack.getItem()).getEquipmentSlot();

                UUID id_moveSpeed = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0001");
                AttributeModifier attrMoveSpeed = new AttributeModifier(id_moveSpeed, movement_speed_key, 0.01 * props.lightness, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), attrMoveSpeed, applicable);

                UUID id_protect = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0002");
                AttributeModifier attrProtection = new AttributeModifier(id_protect, protection_key, 0.5 * props.hardness, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ARMOR.getName(), attrProtection, applicable);

                break;
            case ATTACK:

                UUID id_attackSpeed = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0003");
                AttributeModifier attrAttackSpeed = new AttributeModifier(id_attackSpeed, attack_speed_key, 0.1 * props.lightness, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED.getName(), attrAttackSpeed, EntityEquipmentSlot.MAINHAND);

                UUID id_attack = UUID.fromString("d3c6e3a2-9c6f-4d8a-bc89-8bde0b1f0004");
                AttributeModifier attrAttack = new AttributeModifier(id_attack, damage_key, 5 * props.hardness, 0);
                stack.addAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), attrAttack, EntityEquipmentSlot.MAINHAND);

                break;
            case OTHER:
                //no additional logic provided, skipping
                break;
        }
    }

    public static UseType isToolOrArmor(ItemStack stack) {

        Set<String> classes = stack.getItem().getToolClasses(stack);

        if (classes.contains("pickaxe") || classes.contains("axe") || classes.contains("shovel"))
            return UseType.BREAK;
        else if (stack.getItem() instanceof ItemSword)
            return UseType.ATTACK;
        else if (stack.getItem() instanceof ItemArmor)
            return UseType.DEFEND;
        else if (stack.getItem() instanceof ItemTool)
            return UseType.OTHER;
        return null;
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

    public static void rollAndWriteProperties(ItemStack stack, Random rand) {

        PropertyBundle toAdd = new PropertyBundle((rand.nextInt(11) / 10.0), (rand.nextInt(11) / 10.0), (rand.nextInt(11) / 10.0));

        writeProperties(stack, toAdd);
    }

    public static PropertyBundle aggregateProperties(ArrayList<ItemStack> stacks) {

        double lightness = 0;
        double durability = 0;
        double hardness = 0;

        for (ItemStack stack : stacks) {
            NBTTagCompound nbt = stack.getSubCompound(PROPID);
            if (nbt == null) {
                continue;
            }

            lightness = cutDouble(lightness + nbt.getDouble(PropertyBundle.lightness_key));
            durability = cutDouble(durability + nbt.getDouble(PropertyBundle.stiffness_key));
            hardness = cutDouble(hardness + nbt.getDouble(PropertyBundle.hardness_key));
        }

        return new PropertyBundle(lightness, durability, hardness);
    }

    public static PropertyBundle readProperties(ItemStack stack) {

        NBTTagCompound nbt = stack.getSubCompound(PROPID);
        if (nbt == null) return returnEmptyBundle();

        double lightness = nbt.getDouble(PropertyBundle.lightness_key);
        double durability = nbt.getDouble(PropertyBundle.stiffness_key);
        double hardness = nbt.getDouble(PropertyBundle.hardness_key);

        return new PropertyBundle(lightness, durability, hardness);
    }

    public static void writeProperties(ItemStack stack, PropertyBundle properties) {
        NBTTagCompound nbt = stack.getOrCreateSubCompound(PROPID);

        nbt.setDouble(PropertyBundle.lightness_key, properties.lightness);
        nbt.setDouble(PropertyBundle.stiffness_key, properties.stiffness);
        nbt.setDouble(PropertyBundle.hardness_key, properties.hardness);
    }

    public static double cutDouble(double value) {

        double newValue = BigDecimal.valueOf(value).setScale(1, RoundingMode.FLOOR).doubleValue();

        return newValue;
    }

    public static PropertyBundle returnEmptyBundle() {
        return new PropertyBundle(0, 0, 0);
    }

    public static class PropertyBundle {

        public static String lightness_key = "lightness";
        public static String stiffness_key = "stiffness";
        public static String hardness_key = "hardness";

        public double lightness = 0;
        public double stiffness = 0;
        public double hardness = 0;

        public PropertyBundle(double lightness, double stiffness, double hardness) {
            this.lightness = lightness;
            this.stiffness = stiffness;
            this.hardness = hardness;
        }

        public boolean isEmpty() {
            return this.lightness == 0 && this.stiffness == 0 && this.hardness == 0;
        }

    }

}
