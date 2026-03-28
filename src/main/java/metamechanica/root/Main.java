package metamechanica.root;

import metamechanica.capabilities.register.CapabilityAnimadversion;
import metamechanica.capabilities.register.CapabilityAttributeImprint;
import metamechanica.capabilities.register.CapabilityDARLattice;
import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.client.render.entity.RenderBillet;
import metamechanica.common.entities.EntityBillet;
import metamechanica.init.ItemInit;
import metamechanica.network.Network;
import metamechanica.network.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
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
    public static final String MODID = "metamechanica";
    public static final String NAME = "Metamechanica";
    public static final String VERSION = "1.0.0";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "metamechanica.network.proxy.ClientProxy", serverSide = "metamechanica.network.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static Main instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CapabilityDARStorage.register();
        CapabilityDARLattice.register();
        CapabilityAttributeImprint.register();
        CapabilityAnimadversion.register();

        Network.registerPackets();
        proxy.preInit(event);

        RenderingRegistry.registerEntityRenderingHandler(
                EntityBillet.class,
                renderManager -> new RenderBillet(renderManager)
                );

    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
    }

    public static CreativeTabs tabMod = new CreativeTabs("tabMetamechanica") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(ItemInit.DUMMY);
        }
    };
}
