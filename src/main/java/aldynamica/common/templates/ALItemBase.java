package aldynamica.common.templates;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import aldynamica.common.init.ItemInit;
import aldynamica.root.Main;
import aldynamica.util.EventWrapper;
import aldynamica.util.ExceptionManager.ContextBuilder;
import aldynamica.util.ExceptionManager.ExceptionContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ALItemBase extends Item implements IAldynamicaNative {

    private EnumSortGroup type;

    public ALItemBase(String name, EnumSortGroup type) {
        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setCreativeTab(Main.tabMod);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

    public void setGroup(EnumSortGroup type) {
        this.type = type;
    }

    //wrap start

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        return this.useActual(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    public EnumActionResult useActual(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        return this.onItemRightClickActual(worldIn, playerIn, handIn);
    }

    public ActionResult<ItemStack> onItemRightClickActual(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

}
