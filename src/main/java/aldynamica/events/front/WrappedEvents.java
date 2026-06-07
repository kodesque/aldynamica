package aldynamica.events.front;

import aldynamica.util.ExceptionManager.ContextBuilder;
import aldynamica.util.ExceptionManager.ExceptionContext;
import aldynamica.util.ContextWrapper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class WrappedEvents {

    @SubscribeEvent
    public void onInteractDud(PlayerInteractEvent.RightClickItem event) {

        ExceptionContext context = new ContextBuilder()
                .addPlayer(event.getEntityPlayer())
                .addStack(event.getEntityPlayer().getHeldItemMainhand())
                .build();

        ContextWrapper.runEvent(event, this::onInteractActual, context);
    }

    private void onInteractActual(PlayerInteractEvent.RightClickItem event) {

        //logic goes here
    }

}
