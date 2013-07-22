package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class InventoryBasic implements IInventory
{
	private String inventoryTitle;
	private int slotsCount;
	private ItemStack[] inventoryContents;
	private List field_70480_d;
	private boolean field_94051_e;
	
	public InventoryBasic(String par1Str, boolean par2, int par3)
	{
		inventoryTitle = par1Str;
		field_94051_e = par2;
		slotsCount = par3;
		inventoryContents = new ItemStack[par3];
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(inventoryContents[par1] != null)
		{
			ItemStack var3;
			if(inventoryContents[par1].stackSize <= par2)
			{
				var3 = inventoryContents[par1];
				inventoryContents[par1] = null;
				onInventoryChanged();
				return var3;
			} else
			{
				var3 = inventoryContents[par1].splitStack(par2);
				if(inventoryContents[par1].stackSize == 0)
				{
					inventoryContents[par1] = null;
				}
				onInventoryChanged();
				return var3;
			}
		} else return null;
	}
	
	public void func_110132_b(IInvBasic par1IInvBasic)
	{
		field_70480_d.remove(par1IInvBasic);
	}
	
	public void func_110133_a(String par1Str)
	{
		field_94051_e = true;
		inventoryTitle = par1Str;
	}
	
	public void func_110134_a(IInvBasic par1IInvBasic)
	{
		if(field_70480_d == null)
		{
			field_70480_d = new ArrayList();
		}
		field_70480_d.add(par1IInvBasic);
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return inventoryTitle;
	}
	
	@Override public int getSizeInventory()
	{
		return slotsCount;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return inventoryContents[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(inventoryContents[par1] != null)
		{
			ItemStack var2 = inventoryContents[par1];
			inventoryContents[par1] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return field_94051_e;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return true;
	}
	
	@Override public void onInventoryChanged()
	{
		if(field_70480_d != null)
		{
			for(int var1 = 0; var1 < field_70480_d.size(); ++var1)
			{
				((IInvBasic) field_70480_d.get(var1)).onInventoryChanged(this);
			}
		}
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		inventoryContents[par1] = par2ItemStack;
		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
		{
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
}
