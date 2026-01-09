package essentialcraft.events.back;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AssetInitEvents {

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event)
    {
        event.getMap().registerSprite(
                new ResourceLocation("essentialcraft:blocks/wheel_base")
                );
    }

    //Will be used in the future, not required at this point

}
