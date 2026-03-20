package metamechanica.capabilities.logic;

import metamechanica.api.IAttributeImprint;
import metamechanica.api.IMRUStorage;
import net.minecraft.nbt.NBTTagCompound;

public class AttributeImprint implements IAttributeImprint{

    private int amount = 0;
    private int requiredAmount = 0;
    private boolean isKeeping = false;

    private static final String maxImprint = "required_amount";
    private static final String storageType = "is_keeping";

    public static final String name = "attribute_imprint";

    public void throwException() {
        throw new IllegalArgumentException("Received an illegal value while working with imprint!");
    }

    @Override
    public void setStoringTypeAndRequiredAmount(boolean isKeeping, int requiredAmount) {
        this.isKeeping = isKeeping;

        if (requiredAmount < 0) {
            this.throwException();
        } else {
            this.requiredAmount = requiredAmount;
        }
    }

    @Override
    public int getRequiredAmount() {
        return this.requiredAmount;
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
        }

        if (!this.isKeeping) {
            if (amount > this.requiredAmount || this.amount+amount > this.requiredAmount) {
                this.amount = this.requiredAmount;
            } else {
                this.amount += amount;
            }
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
        tag.setInteger(maxImprint, this.requiredAmount);
        tag.setBoolean(storageType, this.isKeeping);
        return tag;
    }

    public NBTTagCompound deserializeNBT(NBTTagCompound nbt) {
        this.amount = nbt.getInteger(name);
        this.requiredAmount = nbt.getInteger(maxImprint);
        this.isKeeping = nbt.getBoolean(storageType);

        return nbt;
    }

}
