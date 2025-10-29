package essentialcraft.init;

import java.util.ArrayList;
import java.util.List;

import essentialcraft.potions.PotionFalseSaturation;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class PotionInit {

    public static final List<Potion> POTIONS = new ArrayList<Potion>();

    public static final Potion FALSE_SATURATION = new PotionFalseSaturation("false_saturation");



}
