package metamechanica.capabilities.register;

import metamechanica.api.IDARLattice;
import metamechanica.capabilities.logic.DARLattice;
import metamechanica.root.Main;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityDARLattice {
    @CapabilityInject(IDARLattice.class)
    public static final Capability<IDARLattice> CAP = null;

    public static final ResourceLocation KEY = new ResourceLocation(Main.MODID, "DAR_lattice");

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IDARLattice.class,
                new Capability.IStorage<IDARLattice>() {
                    @Override
                    public NBTBase writeNBT(Capability<IDARLattice> capability, IDARLattice instance, EnumFacing side) {
                        if (instance instanceof DARLattice)
                            return ((DARLattice) instance).serializeNBT();
                        return new NBTTagCompound();
                    }

                    @Override
                    public void readNBT(Capability<IDARLattice> capability, IDARLattice instance, EnumFacing side, NBTBase nbt) {
                        if (instance instanceof DARLattice) {
                            ((DARLattice) instance).deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },

                DARLattice::new
                );
    }
}
