package metamechanica.init;

import java.util.ArrayList;
import java.util.List;

import metamechanica.common.templates.SoundEventBase;
import net.minecraft.util.SoundEvent;

public class SoundInit {

    public static final List<SoundEvent> SOUNDS = new ArrayList<SoundEvent>();

    public static SoundEvent WEDGE_USE = new SoundEventBase("wedge_use");
    public static SoundEvent ORE_CRACK = new SoundEventBase("ore_crack");
    public static SoundEvent CROWBAR_HIT = new SoundEventBase("crowbar_hit");

    public static SoundEvent RECORD_STRIKE = new SoundEventBase("record_strike");


}
