package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.api.Main;
import essentialcraft.common.items.ItemBase;
import essentialcraft.common.items.ItemCaviar;
import essentialcraft.common.items.ItemConductor;
import essentialcraft.common.items.ItemCrowbar;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class ItemInit {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    public static final Item DUMMY = new ItemConductor(ItemConductor.name).setMaxStackSize(1);
    public static final Item CAVIAR = new ItemCaviar("silverfish_caviar");
    //    public static final Item DOG = new ItemCrowbar("assembling_crowbar");

    //Crowbar should extend ItemTool and not ItemBase

}
