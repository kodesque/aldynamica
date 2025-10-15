package essentialcraft.util;

public interface IAttributeImprint extends IBaseArithmetic {

    void setStoringTypeAndRequiredAmount(boolean isKeeping, int requiredAmount);

    int getRequiredAmount();

}
