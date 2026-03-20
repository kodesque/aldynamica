package metamechanica.network;

import io.netty.buffer.ByteBuf;
import metamechanica.api.ILeavesImprint;
import metamechanica.api.IMRULattice;
import metamechanica.capabilities.register.CapabilityAttributeImprint;
import metamechanica.capabilities.register.CapabilityMRULattice;
import metamechanica.common.items.ItemAttributeMold;
import metamechanica.root.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketBindMold implements IMessage {

    private static int stackLyingIn;
    private static double key;

    public PacketBindMold() {};

    public PacketBindMold (int lyingStackSlotId, double key) {
        PacketBindMold.stackLyingIn = lyingStackSlotId;
        PacketBindMold.key = key;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBindMold.stackLyingIn = buf.readInt();
        PacketBindMold.key = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(PacketBindMold.stackLyingIn);
        buf.writeDouble(PacketBindMold.key);
    }

    public int getStackLyingIn() {
        return PacketBindMold.stackLyingIn;
    }

    public double getKey() {
        return PacketBindMold.key;
    }

    public static class Handler implements IMessageHandler<PacketBindMold, IMessage> {

        @Override
        public IMessage onMessage(PacketBindMold message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;

            ItemStack stackLying = player.inventory.getStackInSlot(stackLyingIn);
            ItemStack stackCarried = player.inventory.getItemStack();

            NBTTagCompound tagCarried = stackCarried.getOrCreateSubCompound(Main.MODID);
            NBTTagCompound tagLying = stackLying.getOrCreateSubCompound(Main.MODID);

            tagCarried.setString(ItemAttributeMold.bound_item, stackLying.getDisplayName());
            tagCarried.setDouble(ItemAttributeMold.key, key);
            tagLying.setDouble(ItemAttributeMold.key, key);

            if (stackLying.getItem() instanceof ILeavesImprint && stackCarried.getItem() instanceof ILeavesImprint) {
                stackLying.getCapability(CapabilityAttributeImprint.CAP, null).setStoringTypeAndRequiredAmount(
                        false, 0);
                stackCarried.getCapability(CapabilityAttributeImprint.CAP, null).setStoringTypeAndRequiredAmount(
                        false, ((ILeavesImprint)stackLying.getItem()).getRequiredImprint());
            }

            return null;
        }
    }

}
