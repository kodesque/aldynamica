package metamechanica.common.items;

import java.awt.Event;
import java.lang.reflect.Array;
import java.rmi.server.ServerCloneException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Supplier;

import ibxm.Player;
import metamechanica.api.IDARLattice;
import metamechanica.api.IDARStorage;
import metamechanica.capabilities.logic.DARLattice;
import metamechanica.capabilities.logic.DARStorage;
import metamechanica.capabilities.providers.DARStorageProvider;
import metamechanica.capabilities.register.CapabilityDARLattice;
import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.common.templates.ItemBase;
import metamechanica.events.front.GenericEvents;
import metamechanica.init.ItemInit;
import metamechanica.network.Network;
import metamechanica.network.packets.PacketUpdateLattice;
import metamechanica.network.packets.PacketUpdateStorage;
import metamechanica.root.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemConductor extends ItemBase{

    public static String name = "conductor_dummy";

    public static String owner_name = "owner_name";

    TextComponentTranslation owner = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".owner");
    TextComponentTranslation charge = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".charge");
    TextComponentTranslation coherence = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".coherence");
    TextComponentTranslation inactive = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".inactive");


    public ItemConductor(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        if (playerIn.isSneaking()) {

            if (!worldIn.isRemote) {
                IDARLattice lattice = playerIn.getCapability(CapabilityDARLattice.CAP, null);
                IDARStorage charge = playerIn.getCapability(CapabilityDARStorage.CAP, null);
                Network.sendToPlayerMP(new PacketUpdateLattice(lattice.getAmount()), (EntityPlayerMP) playerIn);
                Network.sendToPlayerMP(new PacketUpdateStorage(charge.getAmount()), (EntityPlayerMP) playerIn);
            }

            NBTTagCompound nbt = playerIn.getHeldItem(handIn).getOrCreateSubCompound(Main.MODID);

            nbt.setString(owner_name, playerIn.getName());

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(Minecraft.getMinecraft().player != null) {
            if (stack.getSubCompound(Main.MODID) != null) {

                tooltip.add(this.owner.getFormattedText() + " " + stack.getSubCompound(Main.MODID).getString(owner_name));

                String name = stack.getSubCompound(Main.MODID).getString(owner_name);
                EntityPlayer player = Minecraft.getMinecraft().world.getPlayerEntityByName(name);

                if(Minecraft.getMinecraft().player.equals(player)) {

                    tooltip.add(this.charge.getFormattedText() + " " + player.getCapability(CapabilityDARStorage.CAP, null).getAmount() + " DAR");
                    tooltip.add(this.coherence.getFormattedText() + " " + player.getCapability(CapabilityDARLattice.CAP, null).getAmount() + "%");
                }
            } else {

                tooltip.add(this.inactive.getFormattedText());
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.UNCOMMON;
    }
}
