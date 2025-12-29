package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.common.items.ItemAttributeMold;
import essentialcraft.common.items.ItemCaviar;
import essentialcraft.common.items.ItemConductor;
import essentialcraft.common.items.ItemCrowbar;
import essentialcraft.common.items.ItemPill;
import essentialcraft.common.items.ItemShowcase;
import essentialcraft.common.templates.ItemBase;
import essentialcraft.common.templates.ItemBlockBase;
import essentialcraft.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;

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

}
