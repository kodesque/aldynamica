package essentialcraft.capabilities.providers;

import essentialcraft.capabilities.logic.AttributeImprint;
import essentialcraft.capabilities.register.CapabilityAttributeImprint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class AttributeImprintProvider implements ICapabilitySerializable<NBTTagCompound>{

    private final AttributeImprint instance = new AttributeImprint();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityAttributeImprint.CAP;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return (capability == CapabilityAttributeImprint.CAP) ? (T) this.instance : null;
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
