package essentialcraft.common.blocks.structures;

import essentialcraft.common.templates.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGemcuttingTable extends BlockBase {

    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final PropertyBool PART = PropertyBool.create("part");

    public static String name = "gemcutting_table";

    public BlockGemcuttingTable(String name) {
        super(name, Material.WOOD);

        this.translucent = true;

        this.setHardness(5);
        this.setHarvestLevel("pickaxe", 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, PART});
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing playerFace = EnumFacing.getDirectionFromEntityLiving(pos, placer);

        if (playerFace == EnumFacing.UP || playerFace == EnumFacing.DOWN) {
            playerFace = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, playerFace).withProperty(PART, true);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue(IBlockState state)
    {
        return 0.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(FACING).getHorizontalIndex();

        if (state.getValue(PART)) {
            meta |= 4;
        }

        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);

        boolean active = (meta & 4) != 0;

        return this.getDefaultState()
                .withProperty(FACING, facing)
                .withProperty(PART, active);
    }

}
