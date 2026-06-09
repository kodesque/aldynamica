package aldynamica.common.templates;

import aldynamica.api.EnumLangSection;
import aldynamica.api.EnumSortGroup;
import aldynamica.api.IAldynamicaNative;
import aldynamica.common.init.BlockInit;
import aldynamica.root.Main;
import aldynamica.util.T9n;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ALBlockBase extends Block implements IAldynamicaNative {

    private EnumSortGroup type;

    public ALBlockBase(String name, Material materialIn, EnumSortGroup group) {
        super(materialIn);

        this.setRegistryName(name);
        this.setTranslationKey(T9n.simpleKey(name, EnumLangSection.BLOCKS));

        this.setCreativeTab(Main.tabMod);

        this.type = group;

        BlockInit.BLOCKS.add(this);

        //        this.setHardness(this.blockHardness);
        //fuck is this?
    }

    @Override
    public EnumSortGroup getGroup() {
        return this.type;
    }

}
