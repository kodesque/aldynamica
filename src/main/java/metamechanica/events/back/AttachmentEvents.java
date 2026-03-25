package metamechanica.events.back;

import metamechanica.api.ILeavesImprint;
import metamechanica.capabilities.providers.AttributeImprintProvider;
import metamechanica.capabilities.providers.DARLatticeProvider;
import metamechanica.capabilities.providers.DARStorageProvider;
import metamechanica.capabilities.register.CapabilityAttributeImprint;
import metamechanica.capabilities.register.CapabilityDARLattice;
import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.common.items.ItemAttributeMold;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class AttachmentEvents {

    @SubscribeEvent
    public static void attachCapabilityEntity(AttachCapabilitiesEvent<Entity> event) {

        if (event.getObject() instanceof EntityPlayer) {

            event.addCapability(CapabilityDARStorage.KEY, new DARStorageProvider());
            event.addCapability(CapabilityDARLattice.KEY, new DARLatticeProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilityItemStack(AttachCapabilitiesEvent<ItemStack> event) {

        if (event.getObject().getItem() instanceof ILeavesImprint || event.getObject().getItem() instanceof ItemAttributeMold) {
            event.addCapability(CapabilityAttributeImprint.KEY, new AttributeImprintProvider());
        }
    }

}
