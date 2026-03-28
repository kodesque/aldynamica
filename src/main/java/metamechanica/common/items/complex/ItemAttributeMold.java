package metamechanica.common.items.complex;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.capabilities.register.CapabilityAttributeImprint;
import metamechanica.capabilities.register.CapabilityDARLattice;
import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.common.templates.ItemBase;
import metamechanica.root.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemAttributeMold extends ItemBase {

    public static String name = "attribute_mold";
    public static String bound_item = "bound_item";
    public static String imprint = "imprint";
    public static String key = "key";
    public static String last_location = "last_location";

    TextComponentTranslation boundItem = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".bound_item");
    TextComponentTranslation imprintValue = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".imprint");
    TextComponentTranslation inactive = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".inactive");

    public ItemAttributeMold(String name) {
        super(name);
        this.setMaxStackSize(1);

        this.addPropertyOverride(new ResourceLocation(Main.MODID, "bound"),
                new IItemPropertyGetter() {
            @Override
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                return ItemAttributeMold.getProperty(stack, entityIn);
            }
        });
    }

    public static float getProperty (ItemStack stack, @Nullable EntityLivingBase entityIn) {

        if (!stack.isEmpty() && stack.getItem() instanceof ItemAttributeMold) {
            if (stack.getSubCompound(Main.MODID) != null)
                return stack.getSubCompound(Main.MODID).getDouble(ItemAttributeMold.key) != 0 ? 1 : 0;
        }

        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(Minecraft.getMinecraft().player != null) {
            if (stack.getSubCompound(Main.MODID) != null) {
                if (stack.hasCapability(CapabilityAttributeImprint.CAP, null)) {
                    tooltip.add(this.boundItem.getFormattedText() + " " + stack.getSubCompound(Main.MODID).getString(bound_item));
                    tooltip.add(this.imprintValue.getFormattedText() + " " + stack.getCapability(CapabilityAttributeImprint.CAP, null).getAmount());
                }
            } else {

                tooltip.add(this.inactive.getFormattedText());
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }



}
