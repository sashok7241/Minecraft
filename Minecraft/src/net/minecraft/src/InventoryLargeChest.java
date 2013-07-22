package net.minecraft.src;

public class InventoryLargeChest implements IInventory
{
	private String name;
	private IInventory upperChest;
	private IInventory lowerChest;
	
	public InventoryLargeChest(String p_i3425_1_, IInventory p_i3425_2_, IInventory p_i3425_3_)
	{
		name = p_i3425_1_;
		if(p_i3425_2_ == null)
		{
			p_i3425_2_ = p_i3425_3_;
		}
		if(p_i3425_3_ == null)
		{
			p_i3425_3_ = p_i3425_2_;
		}
		upperChest = p_i3425_2_;
		lowerChest = p_i3425_3_;
	}
	
	@Override public void closeChest()
	{
		upperChest.closeChest();
		lowerChest.closeChest();
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		return p_70298_1_ >= upperChest.getSizeInventory() ? lowerChest.decrStackSize(p_70298_1_ - upperChest.getSizeInventory(), p_70298_2_) : upperChest.decrStackSize(p_70298_1_, p_70298_2_);
	}
	
	@Override public int getInventoryStackLimit()
	{
		return upperChest.getInventoryStackLimit();
	}
	
	@Override public String getInvName()
	{
		return upperChest.isInvNameLocalized() ? upperChest.getInvName() : lowerChest.isInvNameLocalized() ? lowerChest.getInvName() : name;
	}
	
	@Override public int getSizeInventory()
	{
		return upperChest.getSizeInventory() + lowerChest.getSizeInventory();
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return p_70301_1_ >= upperChest.getSizeInventory() ? lowerChest.getStackInSlot(p_70301_1_ - upperChest.getSizeInventory()) : upperChest.getStackInSlot(p_70301_1_);
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		return p_70304_1_ >= upperChest.getSizeInventory() ? lowerChest.getStackInSlotOnClosing(p_70304_1_ - upperChest.getSizeInventory()) : upperChest.getStackInSlotOnClosing(p_70304_1_);
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return upperChest.isInvNameLocalized() || lowerChest.isInvNameLocalized();
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	public boolean isPartOfLargeChest(IInventory p_90010_1_)
	{
		return upperChest == p_90010_1_ || lowerChest == p_90010_1_;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return upperChest.isUseableByPlayer(p_70300_1_) && lowerChest.isUseableByPlayer(p_70300_1_);
	}
	
	@Override public void onInventoryChanged()
	{
		upperChest.onInventoryChanged();
		lowerChest.onInventoryChanged();
	}
	
	@Override public void openChest()
	{
		upperChest.openChest();
		lowerChest.openChest();
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		if(p_70299_1_ >= upperChest.getSizeInventory())
		{
			lowerChest.setInventorySlotContents(p_70299_1_ - upperChest.getSizeInventory(), p_70299_2_);
		} else
		{
			upperChest.setInventorySlotContents(p_70299_1_, p_70299_2_);
		}
	}
}
