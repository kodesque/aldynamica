package metamechanica.common.items;

import metamechanica.api.IHasMeta;
import metamechanica.common.templates.ItemBase;
import metamechanica.root.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemRecordFound extends ItemBase implements IHasMeta {

    public static String name = "record_found";
    public static String meta_name = "record_found_spinning";

    public ItemRecordFound(String name) {
        super(name);
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }

    @Override
    public void registerItemVariants() {
        Main.proxy.registerItemVariants(this, 0, name);
        Main.proxy.registerItemVariants(this, 1, meta_name);
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
        Main.proxy.registerMetaRenderer(this, Main.MODID + ":" + meta_name, 1, "inventory");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack hand = playerIn.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offhand = playerIn.getHeldItem(EnumHand.OFF_HAND);

        if (offhand.getItem().equals(Items.FLINT)) {
            //launch animation

            hand.setItemDamage(1);

            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }


}
