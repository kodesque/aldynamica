package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.common.templates.SoundEventBase;
import net.minecraft.util.SoundEvent;

public class SoundInit {

    public static final List<SoundEvent> SOUNDS = new ArrayList<SoundEvent>();

    public static SoundEvent WEDGE_USE = new SoundEventBase("wedge_use");
    public static SoundEvent ORE_CRACK = new SoundEventBase("ore_crack");
    public static SoundEvent CROWBAR_HIT = new SoundEventBase("crowbar_hit");


}
