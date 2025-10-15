package essentialcraft.handlers;

import essentialcraft.api.AttributeImprintProvider;
import essentialcraft.api.CapabilityAttributeImprint;
import essentialcraft.api.CapabilityMRULattice;
import essentialcraft.api.CapabilityMRUStorage;
import essentialcraft.api.MRULatticeProvider;
import essentialcraft.api.MRUStorageProvider;
import essentialcraft.common.items.ItemAttributeMold;
import essentialcraft.util.ILeavesImprint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {

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
