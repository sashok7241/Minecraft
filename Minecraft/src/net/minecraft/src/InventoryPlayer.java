package net.minecraft.src;

public class InventoryPlayer implements IInventory
{
	public ItemStack[] mainInventory = new ItemStack[36];
	public ItemStack[] armorInventory = new ItemStack[4];
	public int currentItem = 0;
	private ItemStack currentItemStack;
	public EntityPlayer player;
	private ItemStack itemStack;
	public boolean inventoryChanged = false;
	
	public InventoryPlayer(EntityPlayer par1EntityPlayer)
	{
		player = par1EntityPlayer;
	}
	
	public boolean addItemStackToInventory(ItemStack par1ItemStack)
	{
		if(par1ItemStack == null) return false;
		else
		{
			try
			{
				int var2;
				if(par1ItemStack.isItemDamaged())
				{
					var2 = getFirstEmptyStack();
					if(var2 >= 0)
					{
						mainInventory[var2] = ItemStack.copyItemStack(par1ItemStack);
						mainInventory[var2].animationsToGo = 5;
						par1ItemStack.stackSize = 0;
						return true;
					} else if(player.capabilities.isCreativeMode)
					{
						par1ItemStack.stackSize = 0;
						return true;
					} else return false;
				} else
				{
					do
					{
						var2 = par1ItemStack.stackSize;
						par1ItemStack.stackSize = storePartialItemStack(par1ItemStack);
					} while(par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < var2);
					if(par1ItemStack.stackSize == var2 && player.capabilities.isCreativeMode)
					{
						par1ItemStack.stackSize = 0;
						return true;
					} else return par1ItemStack.stackSize < var2;
				}
			} catch(Throwable var5)
			{
				CrashReport var3 = CrashReport.makeCrashReport(var5, "Adding item to inventory");
				CrashReportCategory var4 = var3.makeCategory("Item being added");
				var4.addCrashSection("Item ID", Integer.valueOf(par1ItemStack.itemID));
				var4.addCrashSection("Item data", Integer.valueOf(par1ItemStack.getItemDamage()));
				var4.addCrashSectionCallable("Item name", new CallableItemName(this, par1ItemStack));
				throw new ReportedException(var3);
			}
		}
	}
	
	public ItemStack armorItemInSlot(int par1)
	{
		return armorInventory[par1];
	}
	
	public boolean canHarvestBlock(Block par1Block)
	{
		if(par1Block.blockMaterial.isToolNotRequired()) return true;
		else
		{
			ItemStack var2 = getStackInSlot(currentItem);
			return var2 != null ? var2.canHarvestBlock(par1Block) : false;
		}
	}
	
	public void changeCurrentItem(int par1)
	{
		if(par1 > 0)
		{
			par1 = 1;
		}
		if(par1 < 0)
		{
			par1 = -1;
		}
		for(currentItem -= par1; currentItem < 0; currentItem += 9)
		{
			;
		}
		while(currentItem >= 9)
		{
			currentItem -= 9;
		}
	}
	
	public int clearInventory(int par1, int par2)
	{
		int var3 = 0;
		int var4;
		ItemStack var5;
		for(var4 = 0; var4 < mainInventory.length; ++var4)
		{
			var5 = mainInventory[var4];
			if(var5 != null && (par1 <= -1 || var5.itemID == par1) && (par2 <= -1 || var5.getItemDamage() == par2))
			{
				var3 += var5.stackSize;
				mainInventory[var4] = null;
			}
		}
		for(var4 = 0; var4 < armorInventory.length; ++var4)
		{
			var5 = armorInventory[var4];
			if(var5 != null && (par1 <= -1 || var5.itemID == par1) && (par2 <= -1 || var5.getItemDamage() == par2))
			{
				var3 += var5.stackSize;
				armorInventory[var4] = null;
			}
		}
		return var3;
	}
	
	@Override public void closeChest()
	{
	}
	
	public boolean consumeInventoryItem(int par1)
	{
		int var2 = getInventorySlotContainItem(par1);
		if(var2 < 0) return false;
		else
		{
			if(--mainInventory[var2].stackSize <= 0)
			{
				mainInventory[var2] = null;
			}
			return true;
		}
	}
	
	public void copyInventory(InventoryPlayer par1InventoryPlayer)
	{
		int var2;
		for(var2 = 0; var2 < mainInventory.length; ++var2)
		{
			mainInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[var2]);
		}
		for(var2 = 0; var2 < armorInventory.length; ++var2)
		{
			armorInventory[var2] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[var2]);
		}
		currentItem = par1InventoryPlayer.currentItem;
	}
	
	public void damageArmor(int par1)
	{
		par1 /= 4;
		if(par1 < 1)
		{
			par1 = 1;
		}
		for(int var2 = 0; var2 < armorInventory.length; ++var2)
		{
			if(armorInventory[var2] != null && armorInventory[var2].getItem() instanceof ItemArmor)
			{
				armorInventory[var2].damageItem(par1, player);
				if(armorInventory[var2].stackSize == 0)
				{
					armorInventory[var2] = null;
				}
			}
		}
	}
	
	public void decrementAnimations()
	{
		for(int var1 = 0; var1 < mainInventory.length; ++var1)
		{
			if(mainInventory[var1] != null)
			{
				mainInventory[var1].updateAnimation(player.worldObj, player, var1, currentItem == var1);
			}
		}
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		ItemStack[] var3 = mainInventory;
		if(par1 >= mainInventory.length)
		{
			var3 = armorInventory;
			par1 -= mainInventory.length;
		}
		if(var3[par1] != null)
		{
			ItemStack var4;
			if(var3[par1].stackSize <= par2)
			{
				var4 = var3[par1];
				var3[par1] = null;
				return var4;
			} else
			{
				var4 = var3[par1].splitStack(par2);
				if(var3[par1].stackSize == 0)
				{
					var3[par1] = null;
				}
				return var4;
			}
		} else return null;
	}
	
	public void dropAllItems()
	{
		int var1;
		for(var1 = 0; var1 < mainInventory.length; ++var1)
		{
			if(mainInventory[var1] != null)
			{
				player.dropPlayerItemWithRandomChoice(mainInventory[var1], true);
				mainInventory[var1] = null;
			}
		}
		for(var1 = 0; var1 < armorInventory.length; ++var1)
		{
			if(armorInventory[var1] != null)
			{
				player.dropPlayerItemWithRandomChoice(armorInventory[var1], true);
				armorInventory[var1] = null;
			}
		}
	}
	
	public void func_70439_a(Item par1Item, int par2)
	{
		if(par1Item != null)
		{
			int var3 = getInventorySlotContainItemAndDamage(par1Item.itemID, par2);
			if(var3 >= 0)
			{
				mainInventory[var3] = mainInventory[currentItem];
			}
			if(currentItemStack != null && currentItemStack.isItemEnchantable() && getInventorySlotContainItemAndDamage(currentItemStack.itemID, currentItemStack.getItemDamageForDisplay()) == currentItem) return;
			mainInventory[currentItem] = new ItemStack(Item.itemsList[par1Item.itemID], 1, par2);
		}
	}
	
	public ItemStack getCurrentItem()
	{
		return currentItem < 9 && currentItem >= 0 ? mainInventory[currentItem] : null;
	}
	
	public int getDamageVsEntity(Entity par1Entity)
	{
		ItemStack var2 = getStackInSlot(currentItem);
		return var2 != null ? var2.getDamageVsEntity(par1Entity) : 1;
	}
	
	public int getFirstEmptyStack()
	{
		for(int var1 = 0; var1 < mainInventory.length; ++var1)
		{
			if(mainInventory[var1] == null) return var1;
		}
		return -1;
	}
	
	private int getInventorySlotContainItem(int par1)
	{
		for(int var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null && mainInventory[var2].itemID == par1) return var2;
		}
		return -1;
	}
	
	private int getInventorySlotContainItemAndDamage(int par1, int par2)
	{
		for(int var3 = 0; var3 < mainInventory.length; ++var3)
		{
			if(mainInventory[var3] != null && mainInventory[var3].itemID == par1 && mainInventory[var3].getItemDamage() == par2) return var3;
		}
		return -1;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return "container.inventory";
	}
	
	public ItemStack getItemStack()
	{
		return itemStack;
	}
	
	@Override public int getSizeInventory()
	{
		return mainInventory.length + 4;
	}
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		ItemStack[] var2 = mainInventory;
		if(par1 >= var2.length)
		{
			par1 -= var2.length;
			var2 = armorInventory;
		}
		return var2[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		ItemStack[] var2 = mainInventory;
		if(par1 >= mainInventory.length)
		{
			var2 = armorInventory;
			par1 -= mainInventory.length;
		}
		if(var2[par1] != null)
		{
			ItemStack var3 = var2[par1];
			var2[par1] = null;
			return var3;
		} else return null;
	}
	
	public float getStrVsBlock(Block par1Block)
	{
		float var2 = 1.0F;
		if(mainInventory[currentItem] != null)
		{
			var2 *= mainInventory[currentItem].getStrVsBlock(par1Block);
		}
		return var2;
	}
	
	public int getTotalArmorValue()
	{
		int var1 = 0;
		for(ItemStack element : armorInventory)
		{
			if(element != null && element.getItem() instanceof ItemArmor)
			{
				int var3 = ((ItemArmor) element.getItem()).damageReduceAmount;
				var1 += var3;
			}
		}
		return var1;
	}
	
	public boolean hasItem(int par1)
	{
		int var2 = getInventorySlotContainItem(par1);
		return var2 >= 0;
	}
	
	public boolean hasItemStack(ItemStack par1ItemStack)
	{
		int var2;
		for(var2 = 0; var2 < armorInventory.length; ++var2)
		{
			if(armorInventory[var2] != null && armorInventory[var2].isItemEqual(par1ItemStack)) return true;
		}
		for(var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null && mainInventory[var2].isItemEqual(par1ItemStack)) return true;
		}
		return false;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return false;
	}
	
	@Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
	{
		return player.isDead ? false : par1EntityPlayer.getDistanceSqToEntity(player) <= 64.0D;
	}
	
	@Override public void onInventoryChanged()
	{
		inventoryChanged = true;
	}
	
	@Override public void openChest()
	{
	}
	
	public void readFromNBT(NBTTagList par1NBTTagList)
	{
		mainInventory = new ItemStack[36];
		armorInventory = new ItemStack[4];
		for(int var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) par1NBTTagList.tagAt(var2);
			int var4 = var3.getByte("Slot") & 255;
			ItemStack var5 = ItemStack.loadItemStackFromNBT(var3);
			if(var5 != null)
			{
				if(var4 >= 0 && var4 < mainInventory.length)
				{
					mainInventory[var4] = var5;
				}
				if(var4 >= 100 && var4 < armorInventory.length + 100)
				{
					armorInventory[var4 - 100] = var5;
				}
			}
		}
	}
	
	public void setCurrentItem(int par1, int par2, boolean par3, boolean par4)
	{
		boolean var5 = true;
		currentItemStack = getCurrentItem();
		int var7;
		if(par3)
		{
			var7 = getInventorySlotContainItemAndDamage(par1, par2);
		} else
		{
			var7 = getInventorySlotContainItem(par1);
		}
		if(var7 >= 0 && var7 < 9)
		{
			currentItem = var7;
		} else
		{
			if(par4 && par1 > 0)
			{
				int var6 = getFirstEmptyStack();
				if(var6 >= 0 && var6 < 9)
				{
					currentItem = var6;
				}
				func_70439_a(Item.itemsList[par1], par2);
			}
		}
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		ItemStack[] var3 = mainInventory;
		if(par1 >= var3.length)
		{
			par1 -= var3.length;
			var3 = armorInventory;
		}
		var3[par1] = par2ItemStack;
	}
	
	public void setItemStack(ItemStack par1ItemStack)
	{
		itemStack = par1ItemStack;
	}
	
	private int storeItemStack(ItemStack par1ItemStack)
	{
		for(int var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null && mainInventory[var2].itemID == par1ItemStack.itemID && mainInventory[var2].isStackable() && mainInventory[var2].stackSize < mainInventory[var2].getMaxStackSize() && mainInventory[var2].stackSize < getInventoryStackLimit() && (!mainInventory[var2].getHasSubtypes() || mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(mainInventory[var2], par1ItemStack)) return var2;
		}
		return -1;
	}
	
	private int storePartialItemStack(ItemStack par1ItemStack)
	{
		int var2 = par1ItemStack.itemID;
		int var3 = par1ItemStack.stackSize;
		int var4;
		if(par1ItemStack.getMaxStackSize() == 1)
		{
			var4 = getFirstEmptyStack();
			if(var4 < 0) return var3;
			else
			{
				if(mainInventory[var4] == null)
				{
					mainInventory[var4] = ItemStack.copyItemStack(par1ItemStack);
				}
				return 0;
			}
		} else
		{
			var4 = storeItemStack(par1ItemStack);
			if(var4 < 0)
			{
				var4 = getFirstEmptyStack();
			}
			if(var4 < 0) return var3;
			else
			{
				if(mainInventory[var4] == null)
				{
					mainInventory[var4] = new ItemStack(var2, 0, par1ItemStack.getItemDamage());
					if(par1ItemStack.hasTagCompound())
					{
						mainInventory[var4].setTagCompound((NBTTagCompound) par1ItemStack.getTagCompound().copy());
					}
				}
				int var5 = var3;
				if(var3 > mainInventory[var4].getMaxStackSize() - mainInventory[var4].stackSize)
				{
					var5 = mainInventory[var4].getMaxStackSize() - mainInventory[var4].stackSize;
				}
				if(var5 > getInventoryStackLimit() - mainInventory[var4].stackSize)
				{
					var5 = getInventoryStackLimit() - mainInventory[var4].stackSize;
				}
				if(var5 == 0) return var3;
				else
				{
					var3 -= var5;
					mainInventory[var4].stackSize += var5;
					mainInventory[var4].animationsToGo = 5;
					return var3;
				}
			}
		}
	}
	
	public NBTTagList writeToNBT(NBTTagList par1NBTTagList)
	{
		int var2;
		NBTTagCompound var3;
		for(var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null)
			{
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte) var2);
				mainInventory[var2].writeToNBT(var3);
				par1NBTTagList.appendTag(var3);
			}
		}
		for(var2 = 0; var2 < armorInventory.length; ++var2)
		{
			if(armorInventory[var2] != null)
			{
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte) (var2 + 100));
				armorInventory[var2].writeToNBT(var3);
				par1NBTTagList.appendTag(var3);
			}
		}
		return par1NBTTagList;
	}
	
	public static int getHotbarSize()
	{
		return 9;
	}
}
