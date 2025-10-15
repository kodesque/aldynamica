package essentialcraft.common.items;

import javax.annotation.Nullable;
import javax.swing.text.html.parser.Entity;

import essentialcraft.api.Main;
import essentialcraft.common.basic.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPill extends ItemBase {

    //    public static String is_full = "is_full";

    public ItemPill(String name) {
        super(name);


        //        this.addPropertyOverride(new ResourceLocation(Main.MODID, "full"),
        //                new IItemPropertyGetter() {
        //            @Override
        //            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
        //                return ItemPill.getPropertyFull(stack, entityIn);
        //            }
        //        });
    }

    //    public static float getPropertyFull (ItemStack stack, @Nullable EntityLivingBase entityIn) {
    //
    //        if (entityIn != null) {
    //            if (!stack.isEmpty() && stack.getItem() instanceof ItemPill) {
    //                //Is this thing here really necessary?
    //                if (stack.getSubCompound(Main.MODID) != null)
    //                    return stack.getSubCompound(Main.MODID).getBoolean(is_full) ? 1 : 0;
    //            }
    //
    //        }
    //        return 0;
    //    }

    //    @Override
    //    @SideOnly(Side.CLIENT)
    //    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    //        if (this.isInCreativeTab(tab)) {
    //            ItemStack stack = new ItemStack(this);
    //            ItemStack stack_2 = new ItemStack(this);
    //            stack.getOrCreateSubCompound(Main.MODID).setBoolean(ItemPill.is_full, true);
    //            items.add(stack);
    //            items.add(stack_2);
    //        }
    //    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
        if (target instanceof EntityLiving) {
            EntityLiving entity = (EntityLiving)target;
            double max_health = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
            double current_health = entity.getHealth();
            if (max_health/current_health == 10/2) {
                entity.setNoAI(true);
                stack.shrink(1);
                return true;
            }
        }

        return false;
    }




}
