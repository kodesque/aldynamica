package metamechanica.capabilities.register;


import metamechanica.api.IDARStorage;
import metamechanica.capabilities.logic.DARStorage;
import metamechanica.root.Main;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityDARStorage{
    @CapabilityInject(IDARStorage.class)
    public static final Capability<IDARStorage> CAP = null;

    public static final ResourceLocation KEY = new ResourceLocation(Main.MODID, "DAR_storage");

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IDARStorage.class,
                new Capability.IStorage<IDARStorage>() {
                    @Override
                    public NBTBase writeNBT(Capability<IDARStorage> capability, IDARStorage instance, EnumFacing side) {
                        if (instance instanceof DARStorage)
                            return ((DARStorage) instance).serializeNBT();
                        return new NBTTagCompound();
                    }

                    @Override
                    public void readNBT(Capability<IDARStorage> capability, IDARStorage instance, EnumFacing side, NBTBase nbt) {
                        if (instance instanceof DARStorage) {
                            ((DARStorage) instance).deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },

                DARStorage::new
                );
    }
}


