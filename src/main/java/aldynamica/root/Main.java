package aldynamica.root;

import aldynamica.events.back.RegistryEvents;
import aldynamica.network.Network;
import aldynamica.network.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Main.MODID, version = Main.VERSION, name = Main.NAME)
public class Main {
    public static final String MODID = "aldynamica";
    public static final String NAME = "Aldynamica";
    public static final String VERSION = "0.0.1-ALPHA";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "aldynamica.network.proxy.ClientProxy", serverSide = "aldynamica.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        //        CapabilityDARStorage.register();

        Network.registerPackets();
        proxy.preInit(event);

    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    }

    public static CreativeTabs tabMod = new CreativeTabs("tabAldynamica") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(Items.DIAMOND);
        }
    };
}
