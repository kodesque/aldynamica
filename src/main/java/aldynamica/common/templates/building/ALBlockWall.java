package aldynamica.common.templates.building;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;

public class ALBlockWall extends BlockWall implements IAldynamicaNative{

    private EnumSortGroup type;

    public ALBlockWall(Block modelBlock, EnumSortGroup group) {
        super(modelBlock);

        this.type = group;
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

}
