package essentialcraft.common.items;

import java.util.List;

import javax.annotation.Nullable;

import essentialcraft.api.Main;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrowbar extends ItemBase {

    public static String name = "soldering_crowbar";

    TextComponentTranslation description = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".description");

    public ItemCrowbar(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        SoundEvent sound = new SoundEvent(new ResourceLocation(Main.MODID, "crowbar_hit"));
        sound.setRegistryName("crowbar_hit");

        worldIn.playSound(null, player.getPosition(), sound, SoundCategory.PLAYERS, 1.0F, 1.0F);

        if (player.isSneaking()) {
            if(worldIn.getBlockState(pos).getBlock().equals(Blocks.ANVIL)) {
                IBlockState anvil = worldIn.getBlockState(pos);
                ItemStack stack = player.getHeldItemOffhand();
                int amount = anvil.getValue(BlockAnvil.DAMAGE);

                if (amount != 0) {
                    if (stack.getItem().equals(Item.getItemFromBlock(Blocks.IRON_BLOCK))) {
                        worldIn.setBlockState(pos, anvil.withProperty(BlockAnvil.DAMAGE, amount - 1));
                        stack.shrink(amount);

                        //TODO: finish
                    }
                }
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.description.getFormattedText());
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BLOCK;
    }

}
