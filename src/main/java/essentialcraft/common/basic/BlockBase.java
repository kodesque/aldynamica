package essentialcraft.common.basic;

import essentialcraft.api.Main;
import essentialcraft.init.BlockInit;
import essentialcraft.init.ItemInit;
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
