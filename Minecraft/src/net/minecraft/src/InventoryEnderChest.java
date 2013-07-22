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
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return associatedChest != null && !associatedChest.isUseableByPlayer(par1EntityPlayer) ? false : super.isUseableByPlayer(par1EntityPlayer);
	}
	
	public void loadInventoryFromNBT(NBTTagList par1NBTTagList)
	{
		int var2;
		for(var2 = 0; var2 < getSizeInventory(); ++var2)
		{
			setInventorySlotContents(var2, (ItemStack) null);
		}
		for(var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) par1NBTTagList.tagAt(var2);
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
	
	public void setAssociatedChest(TileEntityEnderChest par1TileEntityEnderChest)
	{
		associatedChest = par1TileEntityEnderChest;
	}
}
