package metamechanica.capabilities.register;

import metamechanica.api.IAnimadversion;
import metamechanica.api.IAttributeImprint;
import metamechanica.capabilities.logic.Animadversion;
import metamechanica.root.Main;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityAnimadversion {
    @CapabilityInject(IAnimadversion.class)
    public static final Capability<IAnimadversion> CAP = null;

    public static final ResourceLocation KEY = new ResourceLocation(Main.MODID, "animadversion");

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IAnimadversion.class,
                new Capability.IStorage<IAnimadversion>() {
                    @Override
                    public NBTBase writeNBT(Capability<IAnimadversion> capability, IAnimadversion instance, EnumFacing side) {
                        if (instance instanceof Animadversion)
                            return ((Animadversion) instance).serializeNBT();
                        return new NBTTagCompound();
                    }

                    @Override
                    public void readNBT(Capability<IAnimadversion> capability, IAnimadversion instance, EnumFacing side, NBTBase nbt) {
                        if (instance instanceof Animadversion) {
                            ((Animadversion) instance).deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },

                Animadversion::new
                );
    }

}
