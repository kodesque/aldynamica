package essentialcraft.capabilities.providers;

import essentialcraft.capabilities.logic.MRULattice;
import essentialcraft.capabilities.register.CapabilityMRULattice;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class MRULatticeProvider implements ICapabilitySerializable<NBTTagCompound> {

    private final MRULattice instance = new MRULattice();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityMRULattice.CAP;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (capability == CapabilityMRULattice.CAP) ? (T) this.instance : null;
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
