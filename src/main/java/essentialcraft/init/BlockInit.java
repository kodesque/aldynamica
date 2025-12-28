package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.common.basic.BlockBase;
import essentialcraft.common.blocks.BlockBrutefire;
import essentialcraft.common.blocks.BlockWheelBase;
import essentialcraft.common.blocks.BlockWheelFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockInit {

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block STONE_METALLIC = new BlockBase("metallic_stone", Material.ROCK)
            .setHardness(2);

    public static final Block WHEEL_BASE = new BlockWheelBase("wheel_base")
            .setHardness(5);

    public static final Block WHEEL_FILLER = new BlockWheelFiller("wheel_filler")
            .setHardness(5);

    //    public static BlockBrutefire BRUTEFIRE_PRE = new BlockBrutefire("brutefire");
    //
    //    public static final Block BRUTEFIRE = BRUTEFIRE_PRE;
}
