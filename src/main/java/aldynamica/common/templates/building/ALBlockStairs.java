package aldynamica.common.templates.building;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;

public class ALBlockStairs extends BlockStairs implements IAldynamicaNative {

    private EnumSortGroup type;

    protected ALBlockStairs(IBlockState modelState, EnumSortGroup group) {
        super(modelState);

        this.type = group;
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

}
