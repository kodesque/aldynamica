package essentialcraft.common.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityWheelFiller extends TileEntity implements ITickable, ISidedInventory{

    public static String corePosKey = "corePos";
    BlockPos corePos = null;

    public BlockPos getCorePos() {
        return this.corePos;
    }

    public void setCorePos(BlockPos corePos) {
        this.corePos = corePos;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getInventoryStackLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        // TODO Auto-generated method stub
        return false;
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
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        return null;
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

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.corePos = BlockPos.fromLong(compound.getLong(corePosKey));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setLong(corePosKey, this.corePos.toLong());
        return compound;
    }

}
