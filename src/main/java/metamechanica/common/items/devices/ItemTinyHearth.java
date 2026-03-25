package metamechanica.common.items.devices;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.common.templates.ItemBase;
import metamechanica.root.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTinyHearth extends ItemBase {

    public static String name = "hearth";

    TextComponentTranslation resource = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".resource");
    TextComponentTranslation fuel = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".fuel");

    public ItemTinyHearth(String name) {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(this.resource.getFormattedText());
    }



}
