package essentialcraft.events.back;

import essentialcraft.api.ILeavesImprint;
import essentialcraft.capabilities.providers.AttributeImprintProvider;
import essentialcraft.capabilities.providers.MRULatticeProvider;
import essentialcraft.capabilities.providers.MRUStorageProvider;
import essentialcraft.capabilities.register.CapabilityAttributeImprint;
import essentialcraft.capabilities.register.CapabilityMRULattice;
import essentialcraft.capabilities.register.CapabilityMRUStorage;
import essentialcraft.common.items.ItemAttributeMold;
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

            event.addCapability(CapabilityMRUStorage.KEY, new MRUStorageProvider());
            event.addCapability(CapabilityMRULattice.KEY, new MRULatticeProvider());
        }
    }

    @SubscribeEvent
    public static void attachCapabilityItemStack(AttachCapabilitiesEvent<ItemStack> event) {

        if (event.getObject().getItem() instanceof ILeavesImprint || event.getObject().getItem() instanceof ItemAttributeMold) {
            event.addCapability(CapabilityAttributeImprint.KEY, new AttributeImprintProvider());
        }
    }

}
