package aldynamica.common.templates;

import aldynamica.common.registry.SoundInit;
import aldynamica.root.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class ALSoundBase extends SoundEvent{

    public ALSoundBase(String name) {
        super(new ResourceLocation(Main.MODID, name));
        this.setRegistryName(Main.MODID, name);
    }

}
