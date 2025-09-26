package essentialcraft.api;


import essentialcraft.util.IAttributeImprint;
import essentialcraft.util.IMRUStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityAttributeImprint{
    @CapabilityInject(IAttributeImprint.class)
    public static final Capability<IAttributeImprint> CAP = null;

    public static final ResourceLocation KEY = new ResourceLocation(Main.MODID, "attribute_imprint");

    public static void register() {
        CapabilityManager.INSTANCE.register(
                IAttributeImprint.class,
                new Capability.IStorage<IAttributeImprint>() {
                    @Override
                    public NBTBase writeNBT(Capability<IAttributeImprint> capability, IAttributeImprint instance, EnumFacing side) {
                        if (instance instanceof AttributeImprint)
                            return ((AttributeImprint) instance).serializeNBT();
                        return new NBTTagCompound();
                    }

                    @Override
                    public void readNBT(Capability<IAttributeImprint> capability, IAttributeImprint instance, EnumFacing side, NBTBase nbt) {
                        if (instance instanceof AttributeImprint) {
                            ((AttributeImprint) instance).deserializeNBT((NBTTagCompound) nbt);
                        }
                    }
                },

                AttributeImprint::new
                );
    }
}


