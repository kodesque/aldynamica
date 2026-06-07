package aldynamica.util;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class RemappingHashes {

    public static final Map<ResourceLocation, Block> BLOCK_REMAPS = new HashMap<>();
    public static final Map<ResourceLocation, Item> ITEM_REMAPS = new HashMap<>();
    public static final Map<ResourceLocation, Potion> POTION_REMAPS = new HashMap<>();
    public static final Map<ResourceLocation, Enchantment> ENCHANTMENT_REMAPS = new HashMap<>();

}
