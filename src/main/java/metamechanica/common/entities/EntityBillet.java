package metamechanica.common.entities;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityBillet extends EntitySlime {

    public static String name = "raw_billet";

    public EntityBillet(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
        this.startRiding(entityIn);
    }

    @Override
    protected EnumParticleTypes getParticleType()
    {
        return EnumParticleTypes.WATER_SPLASH;
    }


    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        this.setSlimeSize(1, true);
        return super.onInitialSpawn(difficulty, livingdata);
    }

}
