package essentialcraft.network.packets;

import essentialcraft.api.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Network {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Main.MODID);
    private static int packetId = 0;

    public static void registerPackets() {

        INSTANCE.registerMessage(
                PacketUpdateLattice.Handler.class,
                PacketUpdateLattice.class,
                packetId++,
                Side.CLIENT
                );

        INSTANCE.registerMessage(
                PacketUpdateStorage.Handler.class,
                PacketUpdateStorage.class,
                packetId++,
                Side.CLIENT
                );
    }

    public static void sendToPlayer(IMessage msg, EntityPlayerMP player) {
        INSTANCE.sendTo(msg, player);
    }

}
