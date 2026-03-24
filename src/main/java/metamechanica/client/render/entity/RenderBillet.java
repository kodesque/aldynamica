package metamechanica.client.render.entity;

import metamechanica.common.entities.EntityBillet;
import metamechanica.root.Main;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderBillet extends RenderSlime {

    public RenderBillet(RenderManager p_i47193_1_) {
        super(p_i47193_1_);
    }

    private static final ResourceLocation PATH = new ResourceLocation(Main.MODID, "textures/entity/" + EntityBillet.name + ".png");

    @Override
    protected ResourceLocation getEntityTexture(EntitySlime entity)
    {
        return PATH;
    }

}
