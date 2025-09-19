package essentialcraft.api;

import essentialcraft.util.IMRUStorage;
import net.minecraft.nbt.NBTTagCompound;

public class MRUStorage implements IMRUStorage{

    private int amount = 0;
    public static final String name = "MRU_storage";

    public void throwException() {
        throw new IllegalArgumentException("Received an illegal value while working with storage!");
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int amount) {
        if (amount < 0) {
            this.throwException();
        } else {
            this.amount = amount;
        }
    }

    @Override
    public void addAmount(int amount) {
        if (amount < 0) {
            this.throwException();
        } else {
            this.amount += amount;
        }
    }

    @Override
    public void subtractAmount(int amount) {
        if (amount < 0 || this.amount-amount < 0) {
            this.throwException();
        } else {
            this.amount -= amount;
        }
    }

    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger(name, this.amount);
        return tag;
    }

    public NBTTagCompound deserializeNBT(NBTTagCompound nbt) {
        this.amount = nbt.getInteger(name);
        return nbt;
    }

}
