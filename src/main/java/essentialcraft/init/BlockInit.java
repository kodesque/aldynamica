package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.common.blocks.BlockMineral;
import essentialcraft.common.blocks.BlockMineralHalf;
import essentialcraft.common.blocks.structures.BlockApparatus;
import essentialcraft.common.blocks.structures.BlockGemcuttingTable;
import essentialcraft.common.blocks.structures.BlockWheelBase;
import essentialcraft.common.blocks.structures.BlockWheelFiller;
import essentialcraft.common.templates.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block STONE_METALLIC = new BlockBase("metallic_stone", Material.ROCK)
            .setHardness(2);

    public static final Block WHEEL_BASE = new BlockWheelBase("wheel_base");

    public static final Block WHEEL_FILLER = new BlockWheelFiller("wheel_filler");

    public static final Block DEPOSIT_CORUNDUM = new BlockMineral(BlockMineral.EnumOreTypes.CORUNDUM);
    public static final Block CORUNDUM_HALF = new BlockMineralHalf(DEPOSIT_CORUNDUM);

    public static final Block DEPOSIT_RHINESTONE = new BlockMineral(BlockMineral.EnumOreTypes.RHINESTONE);
    public static final Block RHINESTONE_HALF = new BlockMineralHalf(DEPOSIT_RHINESTONE);

    public static final Block DEPOSIT_GARNET = new BlockMineral(BlockMineral.EnumOreTypes.GARNET);
    public static final Block GARNET_HALF = new BlockMineralHalf(DEPOSIT_GARNET);

    public static final Block GEMCUTTER = new BlockGemcuttingTable(BlockGemcuttingTable.name);
    public static final Block APPARATUS = new BlockApparatus(BlockApparatus.name);

    //    public static BlockBrutefire BRUTEFIRE_PRE = new BlockBrutefire("brutefire");
    //
    //    public static final Block BRUTEFIRE = BRUTEFIRE_PRE;
}
