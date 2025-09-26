package essentialcraft.common.items;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class ItemAttributeMold extends ItemBase {

    public static String name = "attribute_mold";

    public ItemAttributeMold(String name) {
        super(name);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }



}
