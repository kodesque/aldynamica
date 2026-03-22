package metamechanica.common.blocks;

import metamechanica.common.templates.BlockBase;
import net.minecraft.block.material.Material;

public class BlockMetalPile extends BlockBase {

    public static String name = "pile_metal";

    public BlockMetalPile(String name) {
        super(name, Material.IRON);
    }

}
