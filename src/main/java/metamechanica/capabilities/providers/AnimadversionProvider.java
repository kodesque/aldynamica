package metamechanica.capabilities.providers;

import metamechanica.capabilities.logic.Animadversion;
import metamechanica.capabilities.register.CapabilityAnimadversion;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AnimadversionProvider implements ICapabilitySerializable<NBTTagCompound> {

    private final Animadversion instance = new Animadversion();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityAnimadversion.CAP;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (capability == CapabilityAnimadversion.CAP) ? (T) this.instance : null;
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
