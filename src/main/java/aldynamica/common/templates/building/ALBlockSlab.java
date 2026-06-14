package aldynamica.common.templates.building;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.ItemStack;

public class ALBlockSlab extends BlockSlab implements IAldynamicaNative {

    private EnumSortGroup type;
    private Block parent;

    public ALBlockSlab(Block parent, EnumSortGroup group) {
        super(parent.getDefaultState().getMaterial());

        this.type = group;
        this.parent = parent;
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

    @Override
    public String getTranslationKey(int meta) {
        return super.getTranslationKey();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return null;
    }

}
