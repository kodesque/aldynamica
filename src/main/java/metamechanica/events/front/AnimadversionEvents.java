package metamechanica.events.front;

import metamechanica.capabilities.register.CapabilityDARStorage;
import metamechanica.root.Main;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class AnimadversionEvents {

    @SubscribeEvent
    public static void inscrease(AdvancementEvent event) {

        if (event.getAdvancement().getDisplay() == null) return;

        FrameType type = event.getAdvancement().getDisplay().getFrame();
        int amount = 0;

        if (type.equals(FrameType.TASK)) {
            amount = 1;
        } else if (type.equals(FrameType.GOAL)) {
            amount = 5;

            if (event.getEntityPlayer().world.rand.nextInt(10) < 3) {
                TextComponentTranslation regular = new TextComponentTranslation("scene." + Main.MODID + "." + "animadversion" + ".receive" + "." + "goal" + event.getEntityPlayer().world.rand.nextInt(3));

                event.getEntityPlayer().sendMessage(regular);
            }

        } else if (type.equals(FrameType.CHALLENGE)) {
            amount = 10;

            if (event.getEntityPlayer().world.rand.nextInt(10) < 3) {
                TextComponentTranslation regular = new TextComponentTranslation("scene." + Main.MODID + "." + "animadversion" + ".receive" + "." + "challenge" + event.getEntityPlayer().world.rand.nextInt(3));

                event.getEntityPlayer().sendMessage(regular);
            }
        }

        event.getEntityPlayer().getCapability(CapabilityDARStorage.CAP, null).addAmount(amount);

    }

}
