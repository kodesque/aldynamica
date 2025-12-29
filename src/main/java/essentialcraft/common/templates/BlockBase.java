package essentialcraft.common.templates;

import essentialcraft.init.BlockInit;
import essentialcraft.init.ItemInit;
import essentialcraft.root.Main;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block{

    public BlockBase(String name, Material materialIn) {
        super(materialIn);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        this.setHardness(this.blockHardness);

        BlockInit.BLOCKS.add(this);
    }

}
