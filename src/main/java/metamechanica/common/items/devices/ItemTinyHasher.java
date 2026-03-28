package metamechanica.common.items.devices;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.root.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTinyHasher extends ItemTinyBase {

    public static String name = "hasher";

    public static String key_resource = "resource";
    public static String key_progress = "progress";

    public static int resource_max = 100;
    public static int progress_max = 2400;

    TextComponentTranslation resource = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".resource");
    TextComponentTranslation charge = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".charge");
    TextComponentTranslation progress = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".progress");

    public static Item[] variant = {
            Items.PORKCHOP,
            Items.BEEF,
            Items.MUTTON,
            Items.CHICKEN,
            Items.RABBIT,
            Items.FISH,

            Items.ROTTEN_FLESH,
            Items.SPIDER_EYE,
            Items.BONE,
            Items.STRING,

            Item.getItemFromBlock(Blocks.WOOL),
            Items.LEATHER,
            Items.RABBIT_HIDE,
            Items.RABBIT_FOOT
    };

    //charge from capability
    //items of various "quality" should give various amount of resource

    //progress should be a capability and not an nbt tag
    //charge capability should have a max value

    public ItemTinyHasher(String name) {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (stack.hasCapability(CapabilityDARStorage.CAP, null) && nbt != null) {
            tooltip.add(this.resource.getFormattedText() + " " + nbt.getInteger(key_resource) + "/" + resource_max);
            tooltip.add(this.progress.getFormattedText() + " " + nbt.getInteger(key_progress) + "/" + progress_max);
            tooltip.add(this.charge.getFormattedText() + " " + stack.getCapability(CapabilityDARStorage.CAP, null).getAmount() + " DAR");
        }

        //TODO: holds shift, components, holds alt, upgrades

    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        NBTTagCompound nbt = stack.getSubCompound(Main.MODID);

        if (entityIn instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer)entityIn;

            if (nbt != null) {
                if (nbt.getInteger(key_resource) == resource_max && nbt.getInteger(key_progress) != progress_max) {
                    nbt.setInteger(key_progress, Math.max(nbt.getInteger(key_progress) + 1, progress_max));
                } else if (nbt.getInteger(key_progress) == progress_max) {
                    nbt.setInteger(key_resource, 0);
                    nbt.setInteger(key_progress, 0);

                    ItemStack result = new ItemStack(Items.EXPERIENCE_BOTTLE);

                    if (!player.addItemStackToInventory(result)) {
                        player.dropItem(result, false);
                    }
                }
            }
        }
    }


}
