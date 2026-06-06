package aldynamica.common.templates;

import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import aldynamica.common.init.BlockInit;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ALBlockBase extends Block implements IAldynamicaNative {

    private EnumSortGroup type;

    public ALBlockBase(String name, Material materialIn) {
        super(materialIn);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        this.setCreativeTab(Main.tabMod);

        BlockInit.BLOCKS.add(this);

        //        this.setHardness(this.blockHardness);
        //fuck is this?
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

    public void setGroup(EnumSortGroup type) {
        this.type = type;
    }

}
