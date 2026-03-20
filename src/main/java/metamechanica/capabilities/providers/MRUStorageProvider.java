package metamechanica.capabilities.providers;

import metamechanica.capabilities.logic.MRUStorage;
import metamechanica.capabilities.register.CapabilityMRUStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MRUStorageProvider implements ICapabilitySerializable<NBTTagCompound>{

    private final MRUStorage instance = new MRUStorage();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityMRUStorage.CAP;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (capability == CapabilityMRUStorage.CAP) ? (T) this.instance : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return this.instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        this.instance.deserializeNBT(nbt);
    }
}
