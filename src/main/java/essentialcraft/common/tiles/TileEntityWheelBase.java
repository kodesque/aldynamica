package essentialcraft.common.tiles;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityWheelBase extends TileEntity implements ITickable, ISidedInventory{

    private NonNullList<ItemStack> containerContents = NonNullList.<ItemStack>withSize(3, ItemStack.EMPTY);
    private AxisAlignedBB cachedBB;

    public TileEntityWheelBase()
    {
    }

    @Override
    public int getSizeInventory() {
        return 3;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.containerContents)
        {
            if (!itemstack.isEmpty())
                return false;
        }

        return true;
    }

    public void updateBoundingBox() {
        this.cachedBB = new AxisAlignedBB(this.pos.add(-2,0,0), this.pos.add(3,2,1));
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return this.cachedBB != null ? this.cachedBB : super.getRenderBoundingBox();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.containerContents.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.containerContents, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.containerContents, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getInventoryStackLimit() {
        // TODO Auto-generated method stub
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void closeInventory(EntityPlayer player) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack.getItem() instanceof ItemSword)
            return true;
        return false;
    }

    @Override
    public int getField(int id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getFieldCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        return "container.wheel";
    }

    @Override
    public boolean hasCustomName() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
}
