package aldynamica.events.back;

import aldynamica.api.IHasModel;
import aldynamica.common.register.ModBlocks;
import aldynamica.common.register.ModItems;
import aldynamica.common.register.ModSounds;
import aldynamica.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryEvents {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {

        ModItems.initItems(event.getRegistry());
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {

        ModBlocks.initBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {

        ModSounds.initSounds(event.getRegistry());
    }

}