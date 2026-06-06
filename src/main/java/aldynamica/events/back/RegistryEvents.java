package aldynamica.events.back;

import aldynamica.common.init.BlockInit;
import aldynamica.common.init.ItemInit;
import aldynamica.common.init.SoundInit;
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
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {

        BlockInit.initBlocks(event.getRegistry());

    }

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {

        ItemInit.initItems(event.getRegistry());
        BlockInit.initItemBlocks(event.getRegistry());
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event)
    {

        SoundInit.initSounds(event.getRegistry());
    }

}