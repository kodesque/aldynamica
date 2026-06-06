package aldynamica.util;

import java.util.function.Consumer;

import aldynamica.util.ExceptionManager.ExceptionContext;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EventWrapper {

    public static <T extends Event> void runEvent (
            T actual,
            Consumer<T> handler,
            ExceptionContext ctx
            ) {

        try {
            handler.accept(actual);
        }
        catch (RuntimeException e) {

            ExceptionManager.handle(e, ctx);

        }
    }

}
