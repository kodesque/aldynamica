package aldynamica.events.back;

import aldynamica.util.ExceptionManager;
import aldynamica.util.ExceptionManager.EnumSpecial;
import aldynamica.util.RemappingHashes;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RemappingEvents {

    @SubscribeEvent
    public static void onMissingBlocks(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getMappings()) {

            if (RemappingHashes.BLOCK_REMAPS.containsKey(mapping.key)) {
                mapping.remap(RemappingHashes.BLOCK_REMAPS.get(mapping.key));
            } else {
                System.out.println(ExceptionManager.getSpecial(EnumSpecial.REMAPPING));
            }

        }
    }

    @SubscribeEvent
    public static void onMissingItems(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getMappings()) {

            if (RemappingHashes.ITEM_REMAPS.containsKey(mapping.key)) {
                mapping.remap(RemappingHashes.ITEM_REMAPS.get(mapping.key));
            } else {
                System.out.println(ExceptionManager.getSpecial(EnumSpecial.REMAPPING));
            }

        }
    }

    @SubscribeEvent
    public static void onMissingPotions(RegistryEvent.MissingMappings<Potion> event) {
        for (RegistryEvent.MissingMappings.Mapping<Potion> mapping : event.getMappings()) {

            if (RemappingHashes.POTION_REMAPS.containsKey(mapping.key)) {
                mapping.remap(RemappingHashes.POTION_REMAPS.get(mapping.key));
            } else {
                System.out.println(ExceptionManager.getSpecial(EnumSpecial.REMAPPING));
            }

        }
    }

    @SubscribeEvent
    public static void onMissingEnchantments(RegistryEvent.MissingMappings<Enchantment> event) {
        for (RegistryEvent.MissingMappings.Mapping<Enchantment> mapping : event.getMappings()) {

            if (RemappingHashes.ENCHANTMENT_REMAPS.containsKey(mapping.key)) {
                mapping.remap(RemappingHashes.ENCHANTMENT_REMAPS.get(mapping.key));
            } else {
                System.out.println(ExceptionManager.getSpecial(EnumSpecial.REMAPPING));
            }

        }
    }

}
