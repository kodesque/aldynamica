package essentialcraft.api;


import essentialcraft.util.IMRUStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMRUStorage{
    @CapabilityInject(IMRUStorage.class)
    public static final Capability<IMRUStorage> CAP = null;

    public static final ResourceLocation KEY = new ResourceLocation(Main.MODID, "MRU_storage");

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IMRUStorage.class,
                new Capability.IStorage<IMRUStorage>() {
                    @Override
                    public NBTBase writeNBT(Capability<IMRUStorage> capability, IMRUStorage instance, EnumFacing side) {
                        if (instance instanceof MRUStorage)
                            return ((MRUStorage) instance).serializeNBT();
                        return new NBTTagCompound();
                    }

                    @Override
                    public void readNBT(Capability<IMRUStorage> capability, IMRUStorage instance, EnumFacing side, NBTBase nbt) {
                        if (instance instanceof MRUStorage) {
                            ((MRUStorage) instance).deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },

                MRUStorage::new
                );
    }
}


