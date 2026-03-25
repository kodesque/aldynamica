package metamechanica.network.packets;

import io.netty.buffer.ByteBuf;
import metamechanica.api.IDARLattice;
import metamechanica.api.IDARStorage;
import metamechanica.capabilities.register.CapabilityDARLattice;
import metamechanica.capabilities.register.CapabilityDARStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateStorage implements IMessage {

    private int amount;

    public PacketUpdateStorage() {};

    public PacketUpdateStorage (int amount) {
        this.amount = amount;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.amount = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.amount);

    }

    public int getAmount() {
        return this.amount;
    }

    public static class Handler implements IMessageHandler<PacketUpdateStorage, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketUpdateStorage message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            IDARStorage lattice = player.getCapability(CapabilityDARStorage.CAP, null);

            if (lattice != null) {
                lattice.setAmount(message.getAmount());
            }

            return null;
        }
    }

}