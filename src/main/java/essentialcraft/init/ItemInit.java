package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.common.blocks.structures.BlockApparatus;
import essentialcraft.common.blocks.structures.BlockGemcuttingTable;
import essentialcraft.common.items.ItemAttributeMold;
import essentialcraft.common.items.ItemCaviar;
import essentialcraft.common.items.ItemConductor;
import essentialcraft.common.items.ItemCrowbar;
import essentialcraft.common.items.ItemPill;
import essentialcraft.common.items.ItemShowcase;
import essentialcraft.common.items.ItemWedge;
import essentialcraft.common.templates.ItemBase;
import essentialcraft.common.templates.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item DUMMY = new ItemConductor(ItemConductor.name);
    public static final Item CAVIAR = new ItemCaviar(ItemCaviar.name);
    public static final Item CROWBAR = new ItemCrowbar(ItemCrowbar.name);
    public static final Item MOLD = new ItemAttributeMold(ItemAttributeMold.name);

    public static final Item SHOWCASE = new ItemShowcase("apple");

    public static final Item DULL_PILL = new ItemPill("dull_pill");

    public static final Item POLYMER_SHELL = new ItemBase("digestable_polymer");
    public static final Item POLYMER_CLAY = new ItemBase("polymer_clay");

    public static final Item STONE_METALLIC = new ItemBlockBase(BlockInit.STONE_METALLIC);
    public static final Item WHEEL_BASE = new ItemBlockBase(BlockInit.WHEEL_BASE);

    public static final Item DEPOSIT_CORUNDUM = new ItemBlockBase(BlockInit.DEPOSIT_CORUNDUM);
    public static final Item DEPOSIT_RHINESTONE = new ItemBlockBase(BlockInit.DEPOSIT_RHINESTONE);
    public static final Item DEPOSIT_GARNET = new ItemBlockBase(BlockInit.DEPOSIT_GARNET);

    public static final Item WEDGE = new ItemWedge(ItemWedge.name);

    public static final Item GEM_CORUNDUM = new ItemBase("corundum");
    public static final Item GEM_RHINESTONE = new ItemBase("rhinestone");
    public static final Item GEM_GARNET = new ItemBase("garnet");

    public static final Item GEMCUTTER = new ItemBlockBase(BlockInit.GEMCUTTER);
    public static final Item APPARATUS = new ItemBlockBase(BlockInit.APPARATUS);

    //    public static final Item GEM_TOURMALINE = new ItemBase("tourmaline");
    //    public static final Item GEM_TOPAZ = new ItemBase("topaz");
    //    public static final Item GEM_OPAL = new ItemBase("opal");


}
