package aldynamica.common.templates;

import aldynamica.common.register.ModBlocks;
import aldynamica.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlockBase extends Block{

    public ModBlockBase(String name, Material materialIn) {
        super(materialIn);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);

        ModBlocks.BLOCKS.add(this);

        //        this.setHardness(this.blockHardness);
        //fuck is this?
    }

}
