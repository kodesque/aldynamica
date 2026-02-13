package essentialcraft.common.items;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.common.blocks.BlockMineral;
import essentialcraft.common.blocks.BlockMineralHalf;
import essentialcraft.common.templates.ItemBase;
import essentialcraft.init.BlockInit;
import essentialcraft.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class ItemWedge extends ItemBase {

    public static String name = "clawlike_wedge";

    public ItemWedge(String name) {
        super(name);

        this.setMaxDamage(16);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState state = worldIn.getBlockState(pos);
        ItemStack stack = player.getHeldItem(hand);

        if (!player.onGround && player.motionY < 0) {
            if (state.getBlock() instanceof BlockMineral) {

                if (BlockMineral.performHit(worldIn, pos, state, facing)) {

                    player.getCooldownTracker().setCooldown(this, 30);

                    if (stack.attemptDamageItem(1, itemRand, null)) {
                        stack.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return EnumActionResult.PASS;
    }





}
