package essentialcraft.util;

import java.util.ArrayList;
import java.util.Random;

import essentialcraft.api.Main;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialPropertyUtil {

    public static TextComponentTranslation material_lightness = new TextComponentTranslation("tooltip." + Main.MODID + "." + PropertyBundle.material_lightness);
    public static TextComponentTranslation material_durability = new TextComponentTranslation("tooltip." + Main.MODID + "." + PropertyBundle.material_durability);
    public static TextComponentTranslation material_hardness = new TextComponentTranslation("tooltip." + Main.MODID + "." + PropertyBundle.material_hardness);

    public static TextComponentTranslation induced_durability = new TextComponentTranslation("tooltip." + Main.MODID + "." + "spare_durability");


    public static void rollAndSetProperties(ItemStack stack) {

        Random rand = new Random();

        PropertyBundle toAdd = new PropertyBundle(rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

        setProperties(stack, toAdd);
    }

    public static PropertyBundle aggregateProperties(ArrayList<ItemStack> stacks) {

        double lightness = 0;
        double durability = 0;
        double hardness = 0;

        for (ItemStack stack : stacks) {
            NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
            if (nbt == null) return emptyBundle();

            lightness += Math.min(nbt.getDouble(PropertyBundle.material_lightness), 1);
            durability += Math.min(nbt.getDouble(PropertyBundle.material_durability), 1);
            hardness += Math.min(nbt.getDouble(PropertyBundle.material_hardness), 1);
        }

        return new PropertyBundle(lightness, durability, hardness);
    }

    public static PropertyBundle getProperties(ItemStack stack) {

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);
        if (nbt == null) return emptyBundle();

        double lightness = nbt.getDouble(PropertyBundle.material_lightness);
        double durability = nbt.getDouble(PropertyBundle.material_durability);
        double hardness = nbt.getDouble(PropertyBundle.material_hardness);

        return new PropertyBundle(lightness, durability, hardness);
    }

    public static PropertyBundle emptyBundle() {
        return new PropertyBundle(0, 0, 0);
    }

    public static void setProperties(ItemStack stack, PropertyBundle properties) {
        NBTTagCompound nbt = stack.getOrCreateSubCompound(Main.MODID);

        nbt.setDouble(PropertyBundle.material_lightness, properties.lightness);
        nbt.setDouble(PropertyBundle.material_durability, properties.durability);
        nbt.setDouble(PropertyBundle.material_hardness, properties.hardness);
    }

    public static class PropertyBundle {

        public static String material_lightness = "lightness";
        public static String material_durability = "durability";
        public static String material_hardness = "hardness";

        public double lightness = 0;
        public double durability = 0;
        public double hardness = 0;

        public PropertyBundle(double lightness, double durability, double hardness) {
            this.lightness = lightness;
            this.durability = durability;
            this.hardness = hardness;
        }

        public boolean isEmpty() {
            return this.lightness == 0 && this.durability == 0 && this.hardness == 0;
        }

    }

}
