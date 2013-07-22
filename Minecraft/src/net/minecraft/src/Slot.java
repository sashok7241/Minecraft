package net.minecraft.src;

public class Slot
{
	private final int slotIndex;
	public final IInventory inventory;
	public int slotNumber;
	public int xDisplayPosition;
	public int yDisplayPosition;
	
	public Slot(IInventory p_i3616_1_, int p_i3616_2_, int p_i3616_3_, int p_i3616_4_)
	{
		inventory = p_i3616_1_;
		slotIndex = p_i3616_2_;
		xDisplayPosition = p_i3616_3_;
		yDisplayPosition = p_i3616_4_;
	}
	
	public boolean canTakeStack(EntityPlayer p_82869_1_)
	{
		return true;
	}
	
	public ItemStack decrStackSize(int p_75209_1_)
	{
		return inventory.decrStackSize(slotIndex, p_75209_1_);
	}
	
	public Icon getBackgroundIconIndex()
	{
		return null;
	}
	
	public boolean getHasStack()
	{
		return getStack() != null;
	}
	
	public int getSlotStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}
	
	public ItemStack getStack()
	{
		return inventory.getStackInSlot(slotIndex);
	}
	
	public boolean isItemValid(ItemStack p_75214_1_)
	{
		return true;
	}
	
	public boolean isSlotInInventory(IInventory p_75217_1_, int p_75217_2_)
	{
		return p_75217_1_ == inventory && p_75217_2_ == slotIndex;
	}
	
	protected void onCrafting(ItemStack p_75208_1_)
	{
	}
	
	protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
	{
	}
	
	public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		onSlotChanged();
	}
	
	public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_)
	{
		if(p_75220_1_ != null && p_75220_2_ != null)
		{
			if(p_75220_1_.itemID == p_75220_2_.itemID)
			{
				int var3 = p_75220_2_.stackSize - p_75220_1_.stackSize;
				if(var3 > 0)
				{
					this.onCrafting(p_75220_1_, var3);
				}
			}
		}
	}
	
	public void onSlotChanged()
	{
		inventory.onInventoryChanged();
	}
	
	public void putStack(ItemStack p_75215_1_)
	{
		inventory.setInventorySlotContents(slotIndex, p_75215_1_);
		onSlotChanged();
	}
}
