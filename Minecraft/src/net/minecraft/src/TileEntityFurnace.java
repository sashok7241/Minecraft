package net.minecraft.src;

public class TileEntityFurnace extends TileEntity implements ISidedInventory
{
	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 2, 1 };
	private static final int[] slots_sides = new int[] { 1 };
	private ItemStack[] furnaceItemStacks = new ItemStack[3];
	public int furnaceBurnTime = 0;
	public int currentItemBurnTime = 0;
	public int furnaceCookTime = 0;
	private String field_94130_e;
	
	@Override public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
	{
		return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
	}
	
	@Override public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
	{
		return isItemValidForSlot(par1, par2ItemStack);
	}
	
	private boolean canSmelt()
	{
		if(furnaceItemStacks[0] == null) return false;
		else
		{
			ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().itemID);
			return var1 == null ? false : furnaceItemStacks[2] == null ? true : !furnaceItemStacks[2].isItemEqual(var1) ? false : furnaceItemStacks[2].stackSize < getInventoryStackLimit() && furnaceItemStacks[2].stackSize < furnaceItemStacks[2].getMaxStackSize() ? true : furnaceItemStacks[2].stackSize < var1.getMaxStackSize();
		}
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(furnaceItemStacks[par1] != null)
		{
			ItemStack var3;
			if(furnaceItemStacks[par1].stackSize <= par2)
			{
				var3 = furnaceItemStacks[par1];
				furnaceItemStacks[par1] = null;
				return var3;
			} else
			{
				var3 = furnaceItemStacks[par1].splitStack(par2);
				if(furnaceItemStacks[par1].stackSize == 0)
				{
					furnaceItemStacks[par1] = null;
				}
				return var3;
			}
		} else return null;
	}
	
	@Override public int[] getAccessibleSlotsFromSide(int par1)
	{
		return par1 == 0 ? slots_bottom : par1 == 1 ? slots_top : slots_sides;
	}
	
	public int getBurnTimeRemainingScaled(int par1)
	{
		if(currentItemBurnTime == 0)
		{
			currentItemBurnTime = 200;
		}
		return furnaceBurnTime * par1 / currentItemBurnTime;
	}
	
	public int getCookProgressScaled(int par1)
	{
		return furnaceCookTime * par1 / 200;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? field_94130_e : "container.furnace";
	}
	
	@Override public int getSizeInventory()
	{
		return furnaceItemStacks.length;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return furnaceItemStacks[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(furnaceItemStacks[par1] != null)
		{
			ItemStack var2 = furnaceItemStacks[par1];
			furnaceItemStacks[par1] = null;
			return var2;
		} else return null;
	}
	
	public boolean isBurning()
	{
		return furnaceBurnTime > 0;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return field_94130_e != null && field_94130_e.length() > 0;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return par1 == 2 ? false : par1 == 1 ? isItemFuel(par2ItemStack) : true;
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
		furnaceItemStacks = new ItemStack[getSizeInventory()];
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if(var5 >= 0 && var5 < furnaceItemStacks.length)
			{
				furnaceItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
		furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
		if(par1NBTTagCompound.hasKey("CustomName"))
		{
			field_94130_e = par1NBTTagCompound.getString("CustomName");
		}
	}
	
	public void setGuiDisplayName(String par1Str)
	{
		field_94130_e = par1Str;
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		furnaceItemStacks[par1] = par2ItemStack;
		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
		{
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
	}
	
	public void smeltItem()
	{
		if(canSmelt())
		{
			ItemStack var1 = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().itemID);
			if(furnaceItemStacks[2] == null)
			{
				furnaceItemStacks[2] = var1.copy();
			} else if(furnaceItemStacks[2].itemID == var1.itemID)
			{
				++furnaceItemStacks[2].stackSize;
			}
			--furnaceItemStacks[0].stackSize;
			if(furnaceItemStacks[0].stackSize <= 0)
			{
				furnaceItemStacks[0] = null;
			}
		}
	}
	
	@Override public void updateEntity()
	{
		boolean var1 = furnaceBurnTime > 0;
		boolean var2 = false;
		if(furnaceBurnTime > 0)
		{
			--furnaceBurnTime;
		}
		if(!worldObj.isRemote)
		{
			if(furnaceBurnTime == 0 && canSmelt())
			{
				currentItemBurnTime = furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]);
				if(furnaceBurnTime > 0)
				{
					var2 = true;
					if(furnaceItemStacks[1] != null)
					{
						--furnaceItemStacks[1].stackSize;
						if(furnaceItemStacks[1].stackSize == 0)
						{
							Item var3 = furnaceItemStacks[1].getItem().getContainerItem();
							furnaceItemStacks[1] = var3 != null ? new ItemStack(var3) : null;
						}
					}
				}
			}
			if(isBurning() && canSmelt())
			{
				++furnaceCookTime;
				if(furnaceCookTime == 200)
				{
					furnaceCookTime = 0;
					smeltItem();
					var2 = true;
				}
			} else
			{
				furnaceCookTime = 0;
			}
			if(var1 != furnaceBurnTime > 0)
			{
				var2 = true;
				BlockFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}
		if(var2)
		{
			onInventoryChanged();
		}
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short) furnaceBurnTime);
		par1NBTTagCompound.setShort("CookTime", (short) furnaceCookTime);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < furnaceItemStacks.length; ++var3)
		{
			if(furnaceItemStacks[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				furnaceItemStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		par1NBTTagCompound.setTag("Items", var2);
		if(isInvNameLocalized())
		{
			par1NBTTagCompound.setString("CustomName", field_94130_e);
		}
	}
	
	public static int getItemBurnTime(ItemStack par0ItemStack)
	{
		if(par0ItemStack == null) return 0;
		else
		{
			int var1 = par0ItemStack.getItem().itemID;
			Item var2 = par0ItemStack.getItem();
			if(var1 < 256 && Block.blocksList[var1] != null)
			{
				Block var3 = Block.blocksList[var1];
				if(var3 == Block.woodSingleSlab) return 150;
				if(var3.blockMaterial == Material.wood) return 300;
			}
			return var2 instanceof ItemTool && ((ItemTool) var2).getToolMaterialName().equals("WOOD") ? 200 : var2 instanceof ItemSword && ((ItemSword) var2).getToolMaterialName().equals("WOOD") ? 200 : var2 instanceof ItemHoe && ((ItemHoe) var2).getMaterialName().equals("WOOD") ? 200 : var1 == Item.stick.itemID ? 100 : var1 == Item.coal.itemID ? 1600 : var1 == Item.bucketLava.itemID ? 20000 : var1 == Block.sapling.blockID ? 100 : var1 == Item.blazeRod.itemID ? 2400 : 0;
		}
	}
	
	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemBurnTime(par0ItemStack) > 0;
	}
}
