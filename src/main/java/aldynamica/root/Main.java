package aldynamica.root;

import java.util.HashMap;
import java.util.Map;

import aldynamica.api.IAldynamicaNative;
import aldynamica.common.init.ItemInit;
import aldynamica.network.Network;
import aldynamica.network.proxy.CommonProxy;
import aldynamica.util.ExceptionManager;
import aldynamica.util.ExceptionManager.ContextBuilder;
import aldynamica.util.ExceptionManager.EnumSpecial;
import aldynamica.util.ExceptionManager.ExceptionContext;
import aldynamica.util.T9n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

    public final static String GITHUB = "https://github.com/kodesque/aldynamica";

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

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslationKey()
        {
            return T9n.simpleKey("creative_tab" + ":" + "name");
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(NonNullList<ItemStack> list)
        {
            super.displayAllRelevantItems(list);

            try {

                Map<Item, Integer> order = new HashMap<>();

                for (int i = 0; i < ItemInit.ITEMS.size(); i++)
                {
                    order.put(ItemInit.ITEMS.get(i), i);
                }

                list.sort((a, b) ->
                {
                    IAldynamicaNative A = (IAldynamicaNative)a.getItem();
                    IAldynamicaNative B = (IAldynamicaNative)b.getItem();

                    int groupCompare = Integer.compare(
                            A.getGroup().priority(),
                            B.getGroup().priority()
                            );

                    if (groupCompare != 0)
                        return groupCompare;

                    return Integer.compare(
                            order.get(a.getItem()),
                            order.get(b.getItem())
                            );
                });

            } catch (RuntimeException e) {

                ExceptionContext ctx = new ContextBuilder()
                        .addSource(EnumSpecial.SORTING)
                        .build();

                ExceptionManager.handle(e, ctx);

            }
        }
    };
}
