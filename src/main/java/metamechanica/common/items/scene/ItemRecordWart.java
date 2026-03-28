package metamechanica.common.items.scene;

import metamechanica.common.templates.ItemBase;
import metamechanica.init.BlockInit;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

        if (state.getBlock().equals(Blocks.JUKEBOX) && !state.getValue(BlockJukebox.HAS_RECORD) ) {

            worldIn.setBlockState(pos, BlockInit.NERVE_BULB.getDefaultState());

            for (int i = 3; i < pos.getY(); i++) {

                BlockPos lower = new BlockPos(pos.getX() + worldIn.rand.nextInt(1), pos.getY() - i, pos.getZ() + worldIn.rand.nextInt(1));

            }

            return EnumActionResult.SUCCESS;
        }

        //TODO: put the wart bulb interaction logic into the dummy itself, not the wart bulb class
        //TODO: the dummy should have a needle interface extension to interact with the wart knot

        return EnumActionResult.PASS;
    }



}
