package essentialcraft.common.blocks.structures;

import essentialcraft.api.IHasModel;
import essentialcraft.common.tiles.TileEntityWheelBase;
import essentialcraft.init.BlockInit;
import essentialcraft.root.Main;
import essentialcraft.util.StructureUtil;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockWheelBase extends BlockContainer{

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockWheelBase(String name) {
        super(Material.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));

        this.setRegistryName(name);
        this.setTranslationKey(Main.MODID + "." + name);
        this.setCreativeTab(Main.tabEssentialCraft);

        this.setHardness(5);

        BlockInit.BLOCKS.add(this);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)

    {
        return this.getDefaultState().withProperty(FACING, facing.getOpposite());
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | state.getValue(FACING).getIndex();

        return i;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityWheelBase();
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return StructureUtil.handleStructure(worldIn, pos, side, null, StructureUtil.checkType.PREPARE);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        StructureUtil.handleStructure(worldIn, pos, state.getValue(FACING), StructureUtil.funcType.ASSEMBLE, null);
    }

    @Override
    public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
    {
        StructureUtil.handleStructure(worldIn, pos, state.getValue(FACING), StructureUtil.funcType.DISASSEMBLE, null);
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.SOLID;
    }

    //    @Override
    //    public void registerModels() {
    //
    //        //        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    //        //        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWheelBase.class, new TimeMachineTESR());
    //
    //    }
    //
    //
    //
    //    @Override
    //    public void registerItemModels() {
    //
    //        //        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Main.MODID, "wheel"));
    //        //        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(this.getRegistryName(), "inventory");
    //        //        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, 0, itemModelResourceLocation);
    //
    //    }
}


