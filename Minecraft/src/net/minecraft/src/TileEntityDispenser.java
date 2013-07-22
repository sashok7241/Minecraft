package net.minecraft.src;

import java.util.Random;

public class TileEntityDispenser extends TileEntity implements IInventory
{
	private ItemStack[] dispenserContents = new ItemStack[9];
	private Random dispenserRandom = new Random();
	protected String customName;
	
	public int addItem(ItemStack par1ItemStack)
	{
		for(int var2 = 0; var2 < dispenserContents.length; ++var2)
		{
			if(dispenserContents[var2] == null || dispenserContents[var2].itemID == 0)
			{
				setInventorySlotContents(var2, par1ItemStack);
				return var2;
			}
		}
		return -1;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(dispenserContents[par1] != null)
		{
			ItemStack var3;
			if(dispenserContents[par1].stackSize <= par2)
			{
				var3 = dispenserContents[par1];
				dispenserContents[par1] = null;
				onInventoryChanged();
				return var3;
			} else
			{
				var3 = dispenserContents[par1].splitStack(par2);
				if(dispenserContents[par1].stackSize == 0)
				{
					dispenserContents[par1] = null;
				}
				onInventoryChanged();
				return var3;
			}
		} else return null;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? customName : "container.dispenser";
	}
	
	public int getRandomStackFromInventory()
	{
		int var1 = -1;
		int var2 = 1;
		for(int var3 = 0; var3 < dispenserContents.length; ++var3)
		{
			if(dispenserContents[var3] != null && dispenserRandom.nextInt(var2++) == 0)
			{
				var1 = var3;
			}
		}
		return var1;
	}
	
	@Override public int getSizeInventory()
	{
		return 9;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return dispenserContents[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(dispenserContents[par1] != null)
		{
			ItemStack var2 = dispenserContents[par1];
			dispenserContents[par1] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return customName != null;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : par1EntityPlayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		dispenserContents = new ItemStack[getSizeInventory()];
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			int var5 = var4.getByte("Slot") & 255;
			if(var5 >= 0 && var5 < dispenserContents.length)
			{
				dispenserContents[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		if(par1NBTTagCompound.hasKey("CustomName"))
		{
			customName = par1NBTTagCompound.getString("CustomName");
		}
	}
	
	public void setCustomName(String par1Str)
	{
		customName = par1Str;
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		dispenserContents[par1] = par2ItemStack;
		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
		{
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
		onInventoryChanged();
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < dispenserContents.length; ++var3)
		{
			if(dispenserContents[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				dispenserContents[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("Items", var2);
		if(isInvNameLocalized())
		{
			par1NBTTagCompound.setString("CustomName", customName);
		}
	}
}
