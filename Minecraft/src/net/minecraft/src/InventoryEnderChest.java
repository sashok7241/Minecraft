package net.minecraft.src;

public class InventoryEnderChest extends InventoryBasic
{
	private TileEntityEnderChest associatedChest;
	
	public InventoryEnderChest()
	{
		super("container.enderchest", false, 27);
	}
	
	@Override public void closeChest()
	{
		if(associatedChest != null)
		{
			associatedChest.closeChest();
		}
		super.closeChest();
		associatedChest = null;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return associatedChest != null && !associatedChest.isUseableByPlayer(p_70300_1_) ? false : super.isUseableByPlayer(p_70300_1_);
	}
	
	public void loadInventoryFromNBT(NBTTagList p_70486_1_)
	{
		int var2;
		for(var2 = 0; var2 < getSizeInventory(); ++var2)
		{
			setInventorySlotContents(var2, (ItemStack) null);
		}
		for(var2 = 0; var2 < p_70486_1_.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) p_70486_1_.tagAt(var2);
			int var4 = var3.getByte("Slot") & 255;
			if(var4 >= 0 && var4 < getSizeInventory())
			{
				setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
			}
		}
	}
	
	@Override public void openChest()
	{
		if(associatedChest != null)
		{
			associatedChest.openChest();
		}
		super.openChest();
	}
	
	public NBTTagList saveInventoryToNBT()
	{
		NBTTagList var1 = new NBTTagList("EnderItems");
		for(int var2 = 0; var2 < getSizeInventory(); ++var2)
		{
			ItemStack var3 = getStackInSlot(var2);
			if(var3 != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var2);
				var3.writeToNBT(var4);
				var1.appendTag(var4);
			}
		}
		return var1;
	}
	
	public void setAssociatedChest(TileEntityEnderChest p_70485_1_)
	{
		associatedChest = p_70485_1_;
	}
}
