package essentialcraft.handlers;

import essentialcraft.api.Main;
import essentialcraft.init.BlockInit;
import essentialcraft.init.ItemInit;
import essentialcraft.init.PotionInit;
import essentialcraft.init.TileEntityInit;
import essentialcraft.util.IHasIrregularModel;
import essentialcraft.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class RegistryHandler {

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
    public static void onModelRegister(ModelRegistryEvent event) {

        //        for (Block block : BlockInit.BLOCKS) {
        //            if (block instanceof IHasIrregularModel) {
        //                OBJLoader.INSTANCE.loadModel(new ResourceLocation(Main.MODID, block.getLocalizedName()));
        //            }
        //        }

        for (Item item : ItemInit.ITEMS) {
            if(item instanceof IHasModel) {
                ((IHasModel)item).registerModels();
            }
        }
    }

}