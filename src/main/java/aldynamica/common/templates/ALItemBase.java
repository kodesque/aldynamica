package aldynamica.common.templates;

import aldynamica.api.EnumLangSection;
import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaItem;
import aldynamica.common.init.ItemInit;
import aldynamica.root.Main;
import aldynamica.util.CommonBlockStates;
import aldynamica.util.ContextWrapper;
import aldynamica.util.ExceptionManager.ContextBuilder;
import aldynamica.util.ExceptionManager.ExceptionContext;
import aldynamica.util.T9n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ALItemBase extends Item implements IAldynamicaItem {

    private EnumSortGroup type;
    private boolean isTool;

    public ALItemBase(String name, EnumSortGroup group, boolean isTool) {
        this.setRegistryName(name);
        this.setTranslationKey(T9n.simpleKey(name, EnumLangSection.ITEMS));
        this.type = group;

        this.setCreativeTab(Main.tabMod);

        ItemInit.ITEMS.add(this);
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

    //wrap start

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        ExceptionContext context = new ContextBuilder()
                .addPlayer(player)
                .addWorld(world)
                .addPos(pos)
                .build();

        return ContextWrapper.runMethod(
                context,
                () -> this.onItemUseActual(
                        player,
                        world,
                        pos,
                        hand,
                        facing,
                        hitX,
                        hitY,
                        hitZ
                        ),
                EnumActionResult.FAIL
                );
    }

    public EnumActionResult onItemUseActual(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        return EnumActionResult.PASS;

    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {

        ExceptionContext context = new ContextBuilder()
                .addPlayer(playerIn)
                .addWorld(worldIn)
                .build();

        return ContextWrapper.runMethod(
                context,
                () -> this.onItemRightClickActual(
                        worldIn,
                        playerIn,
                        handIn
                        ),
                new ActionResult<ItemStack>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn))
                );
    }

    public ActionResult<ItemStack> onItemRightClickActual(World worldIn, EntityPlayer playerIn, EnumHand handIn) {


        return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean isTool() {
        return this.isTool;
    }

}
