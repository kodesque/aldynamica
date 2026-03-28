package metamechanica.common.items.scene;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.api.IHasMeta;
import metamechanica.common.templates.ItemBase;
import metamechanica.root.Main;
import metamechanica.util.RecordEventUtil;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRecordFound extends ItemBase {

    public static String name = "record_found";

    TextComponentTranslation main = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".description" + ".main");
    TextComponentTranslation sub = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".description" + ".sub");

    TextComponentTranslation fail = new TextComponentTranslation("event." + Main.MODID + "." + name + ".fail");

    public ItemRecordFound(String name) {
        super(name);
        this.setMaxStackSize(1);
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
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        ItemStack stack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);

        if (state.getBlock().equals(Blocks.JUKEBOX) && !state.getValue(BlockJukebox.HAS_RECORD) ) {

            if (RecordEventUtil.findValidBlocks(worldIn, pos) != null) {
                RecordEventUtil.startEvent(player, null, worldIn);
                stack.shrink(1);

                return EnumActionResult.SUCCESS;
            } else {
                player.sendStatusMessage(new TextComponentString(I18n.format(this.fail.getFormattedText())), true);
            }
        }
        return EnumActionResult.PASS;
    }


}
