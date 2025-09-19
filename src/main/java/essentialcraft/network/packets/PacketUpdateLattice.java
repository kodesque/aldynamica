package essentialcraft.network.packets;

import essentialcraft.api.CapabilityMRULattice;
import essentialcraft.util.IMRULattice;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketUpdateLattice implements IMessage {

    private int amount;

    public PacketUpdateLattice() {};

    public PacketUpdateLattice (int amount) {
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

    public static class Handler implements IMessageHandler<PacketUpdateLattice, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketUpdateLattice message, MessageContext ctx) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            IMRULattice lattice = player.getCapability(CapabilityMRULattice.CAP, null);

            if (lattice != null) {
                lattice.setAmount(message.getAmount());
            }

            return null;
        }
    }

}
