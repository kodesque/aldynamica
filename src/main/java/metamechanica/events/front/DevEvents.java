package metamechanica.events.front;

import metamechanica.capabilities.register.CapabilityAttributeImprint;
import metamechanica.init.ItemInit;
import metamechanica.root.Main;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DevEvents {

    @SubscribeEvent
    public static void debug (PlayerInteractEvent.RightClickItem event) {

        if (event.getItemStack().getItem().equals(ItemInit.MOLD)) {
            if (event.getItemStack().hasCapability(CapabilityAttributeImprint.CAP, null)) {
                System.out.println(event.getItemStack().getCapability(CapabilityAttributeImprint.CAP, null).getAmount());
                if (event.getItemStack().getSubCompound(Main.MODID) != null) {
                    System.out.println(event.getItemStack());
                }
            }
        }
    }

}
