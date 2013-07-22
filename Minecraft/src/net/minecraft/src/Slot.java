package net.minecraft.src;

public class Slot
{
	private final int slotIndex;
	public final IInventory inventory;
	public int slotNumber;
	public int xDisplayPosition;
	public int yDisplayPosition;
	
	public Slot(IInventory par1IInventory, int par2, int par3, int par4)
	{
		inventory = par1IInventory;
		slotIndex = par2;
		xDisplayPosition = par3;
		yDisplayPosition = par4;
	}
	
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return true;
	}
	
	public ItemStack decrStackSize(int par1)
	{
		return inventory.decrStackSize(slotIndex, par1);
	}
	
	public boolean func_111238_b()
	{
		return true;
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
	
	public boolean isItemValid(ItemStack par1ItemStack)
	{
		return true;
	}
	
	public boolean isSlotInInventory(IInventory par1IInventory, int par2)
	{
		return par1IInventory == inventory && par2 == slotIndex;
	}
	
	protected void onCrafting(ItemStack par1ItemStack)
	{
	}
	
	protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
	}
	
	public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		onSlotChanged();
	}
	
	public void onSlotChange(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		if(par1ItemStack != null && par2ItemStack != null)
		{
			if(par1ItemStack.itemID == par2ItemStack.itemID)
			{
				int var3 = par2ItemStack.stackSize - par1ItemStack.stackSize;
				if(var3 > 0)
				{
					this.onCrafting(par1ItemStack, var3);
				}
			}
		}
	}
	
	public void onSlotChanged()
	{
		inventory.onInventoryChanged();
	}
	
	public void putStack(ItemStack par1ItemStack)
	{
		inventory.setInventorySlotContents(slotIndex, par1ItemStack);
		onSlotChanged();
	}
}
