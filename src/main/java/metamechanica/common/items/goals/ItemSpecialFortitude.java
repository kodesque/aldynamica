package metamechanica.common.items.goals;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.root.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpecialFortitude extends ItemSpecialBase {

    public static String name = "decree_fortitude";

    TextComponentTranslation inactive = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".inactive");

    public ItemSpecialFortitude(String name) {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {

    }

}
