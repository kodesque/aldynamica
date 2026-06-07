package aldynamica.events.back;

import aldynamica.common.init.BlockInit;
import aldynamica.common.init.EnchantmentInit;
import aldynamica.common.init.ItemInit;
import aldynamica.common.init.PotionInit;
import aldynamica.common.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryEvents {

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {

        BlockInit.initBlocks(event.getRegistry());
        BlockInit.initBlocksRemap();

    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {

        ItemInit.initItems(event.getRegistry());
        ItemInit.initItemsRemap();

        BlockInit.initItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event)
    {

        SoundInit.initSounds(event.getRegistry());
    }

    @SubscribeEvent
    public static void onPotionRegister(RegistryEvent.Register<Potion> event)
    {

        PotionInit.initPotions(event.getRegistry());
        PotionInit.initPotionsRemap();

    }

    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
    {

        EnchantmentInit.initEnchantments(event.getRegistry());
        EnchantmentInit.initEnchantmentsRemap();

    }

}