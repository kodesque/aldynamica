package metamechanica.common.items.scene;

import metamechanica.common.blocks.scene.BlockBedrockBrace;
import metamechanica.common.templates.ItemBase;
import metamechanica.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRecordWart extends ItemBase {

    public static String name = "record_wart";

    public ItemRecordWart(String name) {
        super(name);

        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        IBlockState state = worldIn.getBlockState(pos);
        ItemStack stack = player.getHeldItem(hand);

        if (state.getBlock().equals(BlockInit.BEDROCK_BRACE) && state.getValue(BlockBedrockBrace.STAGE) == 1 ) {

            worldIn.setBlockState(pos, BlockInit.BEDROCK_BRACE.getDefaultState().withProperty(BlockBedrockBrace.STAGE, 2));
            stack.shrink(1);

            return EnumActionResult.SUCCESS;
        }

        //TODO: put the wart bulb interaction logic into the dummy itself, not the wart bulb class
        //TODO: the dummy should have a needle interface extension to interact with the wart knot

        return EnumActionResult.PASS;
    }



}
