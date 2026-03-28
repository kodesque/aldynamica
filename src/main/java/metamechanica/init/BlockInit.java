package metamechanica.init;

import java.util.ArrayList;
import java.util.List;

import metamechanica.common.blocks.scene.BlockBedrockTight;
import metamechanica.common.blocks.world.BlockMineral;
import metamechanica.common.blocks.world.BlockMineralHalf;
import metamechanica.common.templates.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockInit {

    public static final List<Block> BLOCKS = new ArrayList<Block>();

    public static final Block DEPOSIT_CORUNDUM = new BlockMineral(BlockMineral.EnumOreTypes.CORUNDUM);
    public static final Block CORUNDUM_HALF = new BlockMineralHalf(DEPOSIT_CORUNDUM);

    public static final Block DEPOSIT_RHINESTONE = new BlockMineral(BlockMineral.EnumOreTypes.RHINESTONE);
    public static final Block RHINESTONE_HALF = new BlockMineralHalf(DEPOSIT_RHINESTONE);

    public static final Block DEPOSIT_GARNET = new BlockMineral(BlockMineral.EnumOreTypes.GARNET);
    public static final Block GARNET_HALF = new BlockMineralHalf(DEPOSIT_GARNET);

    public static final Block ORE_TRANSFORM = new BlockBase("ore_transform", Material.IRON).setHardness(0.5F);

    public static final Block NERVE_STEM = new BlockBase("nerve_stem", Material.ROCK).setHardness(0.5F);
    public static final Block NERVE_BULB = new BlockBedrockTight("nerve_bulb");
    public static final Block NERVE_KNOT = new BlockBase("nerve_knot", Material.ROCK).setHardness(0.5F);
}
