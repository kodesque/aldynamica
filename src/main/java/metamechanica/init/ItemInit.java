package metamechanica.init;

import java.util.ArrayList;
import java.util.List;

import metamechanica.common.items.ItemAttributeMold;
import metamechanica.common.items.ItemCaviar;
import metamechanica.common.items.ItemConductor;
import metamechanica.common.items.ItemCrowbar;
import metamechanica.common.items.ItemDebug;
import metamechanica.common.items.ItemRecordFound;
import metamechanica.common.items.ItemWedge;
import metamechanica.common.templates.ItemBase;
import metamechanica.common.templates.ItemBlockBase;
import net.minecraft.item.Item;

public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item DUMMY = new ItemConductor(ItemConductor.name);
    public static final Item CAVIAR = new ItemCaviar(ItemCaviar.name);
    public static final Item CROWBAR = new ItemCrowbar(ItemCrowbar.name);
    public static final Item MOLD = new ItemAttributeMold(ItemAttributeMold.name);

    public static final Item DEBUG = new ItemDebug("debug");
    public static final Item RECORD_FOUND = new ItemRecordFound(ItemRecordFound.name);

    public static final Item DEPOSIT_CORUNDUM = new ItemBlockBase(BlockInit.DEPOSIT_CORUNDUM);
    public static final Item DEPOSIT_RHINESTONE = new ItemBlockBase(BlockInit.DEPOSIT_RHINESTONE);
    public static final Item DEPOSIT_GARNET = new ItemBlockBase(BlockInit.DEPOSIT_GARNET);

    public static final Item WEDGE = new ItemWedge(ItemWedge.name);

    public static final Item GEM_CORUNDUM = new ItemBase("corundum");
    public static final Item GEM_RHINESTONE = new ItemBase("rhinestone");
    public static final Item GEM_GARNET = new ItemBase("garnet");


}
