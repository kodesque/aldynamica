package aldynamica.common.templates;

import aldynamica.init.SoundInit;
import aldynamica.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ModSoundEventBase extends SoundEvent{

    public ModSoundEventBase(String name) {
        super(new ResourceLocation(Main.MODID, name));
        this.setRegistryName(Main.MODID, name);
    }

}
