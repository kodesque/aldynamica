package essentialcraft.common.blocks;

import essentialcraft.api.Main;
import essentialcraft.common.tiles.TileEntityWheelFiller;
import essentialcraft.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockWheelFiller extends BlockContainer{

    public BlockWheelFiller(String name) {
        super(Material.ANVIL);

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        BlockInit.BLOCKS.add(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWheelFiller();
    }



}
