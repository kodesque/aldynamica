package aldynamica.init;

import java.util.ArrayList;
import java.util.List;

import aldynamica.common.templates.ModSoundEventBase;
import net.minecraft.util.SoundEvent;

public class SoundInit {

    public static final List<SoundEvent> SOUNDS = new ArrayList<SoundEvent>();

    public static SoundEvent WEDGE_USE = new ModSoundEventBase("wedge_use");
    public static SoundEvent ORE_CRACK = new ModSoundEventBase("ore_crack");
    public static SoundEvent CROWBAR_HIT = new ModSoundEventBase("crowbar_hit");

    public static SoundEvent RECORD_STRIKE = new ModSoundEventBase("record_strike");


}
