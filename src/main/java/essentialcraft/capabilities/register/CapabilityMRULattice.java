package essentialcraft.capabilities.register;

import essentialcraft.api.IMRULattice;
import essentialcraft.capabilities.logic.MRULattice;
import essentialcraft.root.Main;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMRULattice {
    @CapabilityInject(IMRULattice.class)
    public static final Capability<IMRULattice> CAP = null;

    public static final ResourceLocation KEY = new ResourceLocation(Main.MODID, "MRU_lattice");

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IMRULattice.class,
                new Capability.IStorage<IMRULattice>() {
                    @Override
                    public NBTBase writeNBT(Capability<IMRULattice> capability, IMRULattice instance, EnumFacing side) {
                        if (instance instanceof MRULattice)
                            return ((MRULattice) instance).serializeNBT();
                        return new NBTTagCompound();
                    }

                    @Override
                    public void readNBT(Capability<IMRULattice> capability, IMRULattice instance, EnumFacing side, NBTBase nbt) {
                        if (instance instanceof MRULattice) {
                            ((MRULattice) instance).deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },

                MRULattice::new
                );
    }
}
