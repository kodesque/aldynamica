package metamechanica.common.items;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.api.IHasMeta;
import metamechanica.common.templates.ItemBase;
import metamechanica.init.BlockInit;
import metamechanica.init.SoundInit;
import metamechanica.root.Main;
import metamechanica.util.RecordEventUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRecordFound extends ItemBase implements IHasMeta {

    public static String name = "record_found";
    public static String meta_name = "record_found_spinning";

    TextComponentTranslation main = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".description" + ".main");
    TextComponentTranslation sub = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".description" + ".sub");

    TextComponentTranslation fail = new TextComponentTranslation("event." + Main.MODID + "." + name + ".fail");
    TextComponentTranslation use = new TextComponentTranslation("event." + Main.MODID + "." + name + ".use");

    public ItemRecordFound(String name) {
        super(name);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }

    @Override
    public void registerItemVariants() {
        Main.proxy.registerItemVariants(this, 0, name);
        Main.proxy.registerItemVariants(this, 1, meta_name);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
        Main.proxy.registerMetaRenderer(this, Main.MODID + ":" + meta_name, 1, "inventory");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(this.main.getFormattedText());
        tooltip.add(this.sub.getFormattedText());

        tooltip.add("-" + " " + new ItemStack (Blocks.IRON_ORE).getDisplayName());
        tooltip.add("-" + " " + new ItemStack (Blocks.CLAY).getDisplayName());
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack hand = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offhand = playerIn.getHeldItem(EnumHand.OFF_HAND);

        if (offhand.getItem().equals(Items.FLINT)) {

            worldIn.playSound(
                    null,
                    playerIn.getPosition(),
                    SoundInit.RECORD_STRIKE,
                    SoundCategory.PLAYERS,
                    2.0F,
                    1.0F
                    );

            if (RecordEventUtil.findValidBlocks(worldIn, playerIn.getPosition()) != null) {
                RecordEventUtil.startEvent(playerIn, null, worldIn);
                hand.setItemDamage(1);
            } else {
                playerIn.sendStatusMessage(new TextComponentString(I18n.format(this.fail.getFormattedText())), true);
            }

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        } else {
            playerIn.sendStatusMessage(new TextComponentString(I18n.format(this.use.getFormattedText())), true);
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }


}
