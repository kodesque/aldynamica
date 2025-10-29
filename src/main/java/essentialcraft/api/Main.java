package essentialcraft.api;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import essentialcraft.common.tiles.TileEntityWheelBase;
import essentialcraft.common.tiles.TileEntityWheelFiller;
import essentialcraft.handlers.ExampleConfig;
import essentialcraft.handlers.GenericEventHandler;
import essentialcraft.init.ItemInit;
import essentialcraft.network.packets.Network;
import essentialcraft.network.proxy.CommonProxy;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "essentialcraft";
    public static final String NAME = "Essential Craft";
    public static final String VERSION = "1.0.0";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "essentialcraft.network.proxy.ClientProxy", serverSide = "essentialcraft.network.proxy.CommonProxy")
    public static CommonProxy proxy;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityMRUStorage.register();
        CapabilityMRULattice.register();
        Network.registerPackets();


        GameRegistry.registerTileEntity(TileEntityWheelBase.class, new ResourceLocation(MODID, "wheel"));
        GameRegistry.registerTileEntity(TileEntityWheelFiller.class, new ResourceLocation(MODID, "filler"));
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    }

    public static CreativeTabs tabEssentialCraft = new CreativeTabs("tabEssentialCraft") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.DUMMY);
        }
    };
}
