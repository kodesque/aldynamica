package metamechanica.common.items;

import java.util.List;

import javax.annotation.Nullable;

import metamechanica.api.IAttributeImprint;
import metamechanica.api.IHasModel;
import metamechanica.api.ILeavesImprint;
import metamechanica.api.IMRUStorage;
import metamechanica.init.ItemInit;
import metamechanica.init.SoundInit;
import metamechanica.root.Main;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrowbar extends ItemPickaxe implements IHasModel, ILeavesImprint{

    public ItemCrowbar(String name) {
        super(ToolMaterial.WOOD);
        this.attackSpeed = 1.0F;
        this.setMaxStackSize(1);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        ItemInit.ITEMS.add(this);
    }

    public static String name = "soldering_crowbar";
    public static int requiredImprint = 100;

    TextComponentTranslation description = new TextComponentTranslation("tooltip." + Main.MODID + "." + name + ".description");

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        return true;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

        worldIn.playSound(null, player.getPosition(), SoundInit.CROWBAR_HIT, SoundCategory.PLAYERS, 1.0F, 1.0F);

        if (player.isSneaking()) {
            if(worldIn.getBlockState(pos).getBlock().equals(Blocks.ANVIL)) {

                IBlockState anvil = worldIn.getBlockState(pos);
                ItemStack stack = player.getHeldItemOffhand();

                int amount = anvil.getValue(BlockAnvil.DAMAGE);
                boolean pass = false;

                if (!player.isCreative()) {
                    if (stack.getItem().equals(Item.getItemFromBlock(Blocks.IRON_BLOCK))
                            && stack.getCount() >= amount) {
                        stack.shrink(amount);
                        pass = true;
                    }
                } else {
                    pass = true;
                }

                if (pass) {
                    worldIn.setBlockState(pos, anvil.withProperty(BlockAnvil.DAMAGE, amount - amount));
                    return EnumActionResult.SUCCESS;
                }
            } else if (worldIn.getBlockState(pos).getBlock().equals(Blocks.GRASS) && !worldIn.isDaytime()) {
                //                worldIn.setSkylightSubtracted(0);
                worldIn.provider.getLightBrightnessTable();
                worldIn.setSkylightSubtracted(requiredImprint);
            }
        }

        return EnumActionResult.SUCCESS;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(this.description.getFormattedText());
    }

    @Override
    public void registerModels() {
        Main.proxy.registerItemRenderer(this, 0, "inventory");
    }

    @Override
    public int getRequiredImprint() {
        return ItemCrowbar.requiredImprint;
    }

}
