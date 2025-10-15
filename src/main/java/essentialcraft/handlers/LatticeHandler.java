package essentialcraft.handlers;

import essentialcraft.api.CapabilityMRUStorage;
import essentialcraft.network.packets.Network;
import essentialcraft.network.packets.PacketUpdateStorage;
import essentialcraft.util.IMRUStorage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class LatticeHandler {

    @SubscribeEvent
    public static void receiveLatticeCharge(LivingDeathEvent event) {

        EntityLivingBase entity = event.getEntityLiving();
        if (event.getSource().getTrueSource() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
            IMRUStorage storage = player.getCapability(CapabilityMRUStorage.CAP, null);
            storage.addAmount((int) entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue());
            Network.sendToPlayerMP(new PacketUpdateStorage(storage.getAmount()), (EntityPlayerMP) player);

        }
    }

}
