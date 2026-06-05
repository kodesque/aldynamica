package aldynamica.common.templates;

import aldynamica.common.registry.BlockRegistry;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ALBlockBase extends Block {

    public ALBlockBase(String name, Material materialIn) {
        super(materialIn);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        BlockRegistry.BLOCKS.add(this);

        //        this.setHardness(this.blockHardness);
        //fuck is this?
    }

}
