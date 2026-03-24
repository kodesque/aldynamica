package metamechanica.events.back;

import metamechanica.api.IHasModel;
import metamechanica.common.entities.EntityBillet;
import metamechanica.init.BlockInit;
import metamechanica.init.ItemInit;
import metamechanica.init.PotionInit;
import metamechanica.init.SoundInit;
import metamechanica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@EventBusSubscriber
public class RegistryEvents {

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
    }

    @SubscribeEvent
    public static void onPotionRegister(RegistryEvent.Register<Potion> event) {
        event.getRegistry().registerAll(PotionInit.POTIONS.toArray(new Potion[0]));
    }

    @SubscribeEvent
    public static void onSoundRegister(RegistryEvent.Register<SoundEvent> event) {

        event.getRegistry().registerAll(SoundInit.SOUNDS.toArray(new SoundEvent[0]));
    }

    public static void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(Main.MODID, EntityBillet.name),
                EntityBillet.class,
                EntityBillet.name,
                1,
                Main.instance,
                64, 1, true,
                0x736D6D,
                0x403B3B
                );
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {

        for (Item item : ItemInit.ITEMS) {
            if(item instanceof IHasModel) {
                ((IHasModel)item).registerModels();
            }
        }
    }

}