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
	
	public InventoryPlayer(EntityPlayer p_i3562_1_)
	{
		player = p_i3562_1_;
	}
	
	public boolean addItemStackToInventory(ItemStack p_70441_1_)
	{
		if(p_70441_1_ == null) return false;
		else
		{
			try
			{
				int var2;
				if(p_70441_1_.isItemDamaged())
				{
					var2 = getFirstEmptyStack();
					if(var2 >= 0)
					{
						mainInventory[var2] = ItemStack.copyItemStack(p_70441_1_);
						mainInventory[var2].animationsToGo = 5;
						p_70441_1_.stackSize = 0;
						return true;
					} else if(player.capabilities.isCreativeMode)
					{
						p_70441_1_.stackSize = 0;
						return true;
					} else return false;
				} else
				{
					do
					{
						var2 = p_70441_1_.stackSize;
						p_70441_1_.stackSize = storePartialItemStack(p_70441_1_);
					} while(p_70441_1_.stackSize > 0 && p_70441_1_.stackSize < var2);
					if(p_70441_1_.stackSize == var2 && player.capabilities.isCreativeMode)
					{
						p_70441_1_.stackSize = 0;
						return true;
					} else return p_70441_1_.stackSize < var2;
				}
			} catch(Throwable var5)
			{
				CrashReport var3 = CrashReport.makeCrashReport(var5, "Adding item to inventory");
				CrashReportCategory var4 = var3.makeCategory("Item being added");
				var4.addCrashSection("Item ID", Integer.valueOf(p_70441_1_.itemID));
				var4.addCrashSection("Item data", Integer.valueOf(p_70441_1_.getItemDamage()));
				var4.addCrashSectionCallable("Item name", new CallableItemName(this, p_70441_1_));
				throw new ReportedException(var3);
			}
		}
	}
	
	public ItemStack armorItemInSlot(int p_70440_1_)
	{
		return armorInventory[p_70440_1_];
	}
	
	public boolean canHarvestBlock(Block p_70454_1_)
	{
		if(p_70454_1_.blockMaterial.isToolNotRequired()) return true;
		else
		{
			ItemStack var2 = getStackInSlot(currentItem);
			return var2 != null ? var2.canHarvestBlock(p_70454_1_) : false;
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
	
	public int clearInventory(int p_82347_1_, int p_82347_2_)
	{
		int var3 = 0;
		int var4;
		ItemStack var5;
		for(var4 = 0; var4 < mainInventory.length; ++var4)
		{
			var5 = mainInventory[var4];
			if(var5 != null && (p_82347_1_ <= -1 || var5.itemID == p_82347_1_) && (p_82347_2_ <= -1 || var5.getItemDamage() == p_82347_2_))
			{
				var3 += var5.stackSize;
				mainInventory[var4] = null;
			}
		}
		for(var4 = 0; var4 < armorInventory.length; ++var4)
		{
			var5 = armorInventory[var4];
			if(var5 != null && (p_82347_1_ <= -1 || var5.itemID == p_82347_1_) && (p_82347_2_ <= -1 || var5.getItemDamage() == p_82347_2_))
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
	
	public boolean consumeInventoryItem(int p_70435_1_)
	{
		int var2 = getInventorySlotContainItem(p_70435_1_);
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
	
	public void copyInventory(InventoryPlayer p_70455_1_)
	{
		int var2;
		for(var2 = 0; var2 < mainInventory.length; ++var2)
		{
			mainInventory[var2] = ItemStack.copyItemStack(p_70455_1_.mainInventory[var2]);
		}
		for(var2 = 0; var2 < armorInventory.length; ++var2)
		{
			armorInventory[var2] = ItemStack.copyItemStack(p_70455_1_.armorInventory[var2]);
		}
		currentItem = p_70455_1_.currentItem;
	}
	
	public void damageArmor(int p_70449_1_)
	{
		p_70449_1_ /= 4;
		if(p_70449_1_ < 1)
		{
			p_70449_1_ = 1;
		}
		for(int var2 = 0; var2 < armorInventory.length; ++var2)
		{
			if(armorInventory[var2] != null && armorInventory[var2].getItem() instanceof ItemArmor)
			{
				armorInventory[var2].damageItem(p_70449_1_, player);
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
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		ItemStack[] var3 = mainInventory;
		if(p_70298_1_ >= mainInventory.length)
		{
			var3 = armorInventory;
			p_70298_1_ -= mainInventory.length;
		}
		if(var3[p_70298_1_] != null)
		{
			ItemStack var4;
			if(var3[p_70298_1_].stackSize <= p_70298_2_)
			{
				var4 = var3[p_70298_1_];
				var3[p_70298_1_] = null;
				return var4;
			} else
			{
				var4 = var3[p_70298_1_].splitStack(p_70298_2_);
				if(var3[p_70298_1_].stackSize == 0)
				{
					var3[p_70298_1_] = null;
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
	
	public int getDamageVsEntity(Entity p_70444_1_)
	{
		ItemStack var2 = getStackInSlot(currentItem);
		return var2 != null ? var2.getDamageVsEntity(p_70444_1_) : 1;
	}
	
	public int getFirstEmptyStack()
	{
		for(int var1 = 0; var1 < mainInventory.length; ++var1)
		{
			if(mainInventory[var1] == null) return var1;
		}
		return -1;
	}
	
	private int getInventorySlotContainItem(int p_70446_1_)
	{
		for(int var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null && mainInventory[var2].itemID == p_70446_1_) return var2;
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
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		ItemStack[] var2 = mainInventory;
		if(p_70301_1_ >= var2.length)
		{
			p_70301_1_ -= var2.length;
			var2 = armorInventory;
		}
		return var2[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		ItemStack[] var2 = mainInventory;
		if(p_70304_1_ >= mainInventory.length)
		{
			var2 = armorInventory;
			p_70304_1_ -= mainInventory.length;
		}
		if(var2[p_70304_1_] != null)
		{
			ItemStack var3 = var2[p_70304_1_];
			var2[p_70304_1_] = null;
			return var3;
		} else return null;
	}
	
	public float getStrVsBlock(Block p_70438_1_)
	{
		float var2 = 1.0F;
		if(mainInventory[currentItem] != null)
		{
			var2 *= mainInventory[currentItem].getStrVsBlock(p_70438_1_);
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
	
	public boolean hasItem(int p_70450_1_)
	{
		int var2 = getInventorySlotContainItem(p_70450_1_);
		return var2 >= 0;
	}
	
	public boolean hasItemStack(ItemStack p_70431_1_)
	{
		int var2;
		for(var2 = 0; var2 < armorInventory.length; ++var2)
		{
			if(armorInventory[var2] != null && armorInventory[var2].isItemEqual(p_70431_1_)) return true;
		}
		for(var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null && mainInventory[var2].isItemEqual(p_70431_1_)) return true;
		}
		return false;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return false;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return player.isDead ? false : p_70300_1_.getDistanceSqToEntity(player) <= 64.0D;
	}
	
	@Override public void onInventoryChanged()
	{
		inventoryChanged = true;
	}
	
	@Override public void openChest()
	{
	}
	
	public void readFromNBT(NBTTagList p_70443_1_)
	{
		mainInventory = new ItemStack[36];
		armorInventory = new ItemStack[4];
		for(int var2 = 0; var2 < p_70443_1_.tagCount(); ++var2)
		{
			NBTTagCompound var3 = (NBTTagCompound) p_70443_1_.tagAt(var2);
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
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		ItemStack[] var3 = mainInventory;
		if(p_70299_1_ >= var3.length)
		{
			p_70299_1_ -= var3.length;
			var3 = armorInventory;
		}
		var3[p_70299_1_] = p_70299_2_;
	}
	
	public void setItemStack(ItemStack p_70437_1_)
	{
		itemStack = p_70437_1_;
	}
	
	private int storeItemStack(ItemStack p_70432_1_)
	{
		for(int var2 = 0; var2 < mainInventory.length; ++var2)
		{
			if(mainInventory[var2] != null && mainInventory[var2].itemID == p_70432_1_.itemID && mainInventory[var2].isStackable() && mainInventory[var2].stackSize < mainInventory[var2].getMaxStackSize() && mainInventory[var2].stackSize < getInventoryStackLimit() && (!mainInventory[var2].getHasSubtypes() || mainInventory[var2].getItemDamage() == p_70432_1_.getItemDamage()) && ItemStack.areItemStackTagsEqual(mainInventory[var2], p_70432_1_)) return var2;
		}
		return -1;
	}
	
	private int storePartialItemStack(ItemStack p_70452_1_)
	{
		int var2 = p_70452_1_.itemID;
		int var3 = p_70452_1_.stackSize;
		int var4;
		if(p_70452_1_.getMaxStackSize() == 1)
		{
			var4 = getFirstEmptyStack();
			if(var4 < 0) return var3;
			else
			{
				if(mainInventory[var4] == null)
				{
					mainInventory[var4] = ItemStack.copyItemStack(p_70452_1_);
				}
				return 0;
			}
		} else
		{
			var4 = storeItemStack(p_70452_1_);
			if(var4 < 0)
			{
				var4 = getFirstEmptyStack();
			}
			if(var4 < 0) return var3;
			else
			{
				if(mainInventory[var4] == null)
				{
					mainInventory[var4] = new ItemStack(var2, 0, p_70452_1_.getItemDamage());
					if(p_70452_1_.hasTagCompound())
					{
						mainInventory[var4].setTagCompound((NBTTagCompound) p_70452_1_.getTagCompound().copy());
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
	
	public NBTTagList writeToNBT(NBTTagList p_70442_1_)
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
				p_70442_1_.appendTag(var3);
			}
		}
		for(var2 = 0; var2 < armorInventory.length; ++var2)
		{
			if(armorInventory[var2] != null)
			{
				var3 = new NBTTagCompound();
				var3.setByte("Slot", (byte) (var2 + 100));
				armorInventory[var2].writeToNBT(var3);
				p_70442_1_.appendTag(var3);
			}
		}
		return p_70442_1_;
	}
	
	public static int getHotbarSize()
	{
		return 9;
	}
}
