package essentialcraft.client.renderer.tiles;

import java.util.function.Function;

import org.lwjgl.opengl.GL11;

import essentialcraft.common.tiles.TileEntityWheelBase;
import essentialcraft.init.BlockInit;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class RendererWheelBase extends TileEntitySpecialRenderer<TileEntityWheelBase> {

    // trying to get a baked model in a non-direct way

    public static ResourceLocation rl = new ResourceLocation("essentialcraft:block/wheel_small.obj");

    private IModel model;
    private IBakedModel baked;

    private IBakedModel getBakedModel() {
        if (this.baked == null) {

            try {
                this.model = ModelLoaderRegistry.getModel(rl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Function<ResourceLocation, TextureAtlasSprite> spriteGetter =
                    sl -> Minecraft.getMinecraft()
                    .getTextureMapBlocks()
                    .getAtlasSprite(sl.toString());

                    this.baked = this.model.bake(

                            this.model.getDefaultState(),
                            DefaultVertexFormats.BLOCK,
                            spriteGetter
                            );
        }
        return this.baked;
    }



    @Override
    public void render(TileEntityWheelBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        GlStateManager.pushMatrix();
        //        GlStateManager.pushAttrib();

        GlStateManager.translate(x + 0.5, y, z + 0.5);
        GlStateManager.scale(3f, 1f, 3f);
        Minecraft.getMinecraft().getTextureManager()
        .bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        RenderHelper.disableStandardItemLighting();
        //        if (Minecraft.isAmbientOcclusionEnabled()) {
        //            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        //        } else {
        //            GlStateManager.shadeModel(GL11.GL_FLAT);
        //        }

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        //Hard-drawing a model over a vanilla-initialized one

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

        IBlockState state = BlockInit.WHEEL_BASE.getDefaultState();
        IBakedModel model = dispatcher.getModelForState(state);

        dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), model, te.getBlockType().getDefaultState(), te.getPos(), buffer, true);


        tess.draw();

        GlStateManager.popMatrix();
        //        GlStateManager.popAttrib();//
    }

}
