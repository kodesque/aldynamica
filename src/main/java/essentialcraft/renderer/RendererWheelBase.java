package essentialcraft.renderer;

import org.lwjgl.opengl.GL11;

import essentialcraft.api.Main;
import essentialcraft.common.tiles.TileEntityWheelBase;
import essentialcraft.init.BlockInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RendererWheelBase extends TileEntitySpecialRenderer<TileEntityWheelBase> {

    @Override
    public void render(TileEntityWheelBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

        System.out.println("loading");

        IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(BlockInit.WHEEL_BASE.getDefaultState());

        GlStateManager.pushMatrix();
        {

            GlStateManager.translate(x + 0.5, y, z + 0.5);

            GlStateManager.scale(1f, 1f, 1f);

            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Main.MODID, "textures/blocks/wheel_base.png"));
            if (Minecraft.isAmbientOcclusionEnabled()) {
                GlStateManager.shadeModel(GL11.GL_SMOOTH);
            } else {
                GlStateManager.shadeModel(GL11.GL_FLAT);
            }
        }
        GlStateManager.popMatrix();
    }

}
