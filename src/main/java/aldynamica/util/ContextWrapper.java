package aldynamica.util;

import java.util.function.Consumer;

import com.google.common.base.Supplier;

import aldynamica.util.ExceptionManager.ExceptionContext;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ContextWrapper {

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

    public static <R> R runMethod(
            ExceptionContext ctx,
            Supplier<R> action,
            R fallback)
    {
        try {
            return action.get();
        }
        catch (RuntimeException e) {
            ExceptionManager.handle(e, ctx);
            return fallback;
        }
    }


}
