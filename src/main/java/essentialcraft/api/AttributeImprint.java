package essentialcraft.api;

import essentialcraft.util.IAttributeImprint;
import essentialcraft.util.IMRUStorage;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeImprint implements IAttributeImprint{

    private int amount = 0;
    public int requiredAmount = 0;
    public static final String name = "attribute_imprint";

    public void throwException() {
        throw new IllegalArgumentException("Received an illegal value while working with imprint!");
    }

    @Override
    public int getRequiredAmount() {
        return this.requiredAmount;
    }

    @Override
    public void setRequiredAmount(int requiredAmount) {
        if (requiredAmount < 0) {
            this.throwException();
        } else {
            this.requiredAmount = requiredAmount;
        }
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
        if (amount < 0 || amount > this.requiredAmount || this.amount+amount > this.requiredAmount) {
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
