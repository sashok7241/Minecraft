package net.minecraft.src;

import java.util.List;

public class TileEntityBrewingStand extends TileEntity implements ISidedInventory
{
	private static final int[] field_102017_a = new int[] { 3 };
	private static final int[] field_102016_b = new int[] { 0, 1, 2 };
	private ItemStack[] brewingItemStacks = new ItemStack[4];
	private int brewTime;
	private int filledSlots;
	private int ingredientID;
	private String field_94132_e;
	
	private void brewPotions()
	{
		if(canBrew())
		{
			ItemStack var1 = brewingItemStacks[3];
			for(int var2 = 0; var2 < 3; ++var2)
			{
				if(brewingItemStacks[var2] != null && brewingItemStacks[var2].itemID == Item.potion.itemID)
				{
					int var3 = brewingItemStacks[var2].getItemDamage();
					int var4 = getPotionResult(var3, var1);
					List var5 = Item.potion.getEffects(var3);
					List var6 = Item.potion.getEffects(var4);
					if((var3 <= 0 || var5 != var6) && (var5 == null || !var5.equals(var6) && var6 != null))
					{
						if(var3 != var4)
						{
							brewingItemStacks[var2].setItemDamage(var4);
						}
					} else if(!ItemPotion.isSplash(var3) && ItemPotion.isSplash(var4))
					{
						brewingItemStacks[var2].setItemDamage(var4);
					}
				}
			}
			if(Item.itemsList[var1.itemID].hasContainerItem())
			{
				brewingItemStacks[3] = new ItemStack(Item.itemsList[var1.itemID].getContainerItem());
			} else
			{
				--brewingItemStacks[3].stackSize;
				if(brewingItemStacks[3].stackSize <= 0)
				{
					brewingItemStacks[3] = null;
				}
			}
		}
	}
	
	private boolean canBrew()
	{
		if(brewingItemStacks[3] != null && brewingItemStacks[3].stackSize > 0)
		{
			ItemStack var1 = brewingItemStacks[3];
			if(!Item.itemsList[var1.itemID].isPotionIngredient()) return false;
			else
			{
				boolean var2 = false;
				for(int var3 = 0; var3 < 3; ++var3)
				{
					if(brewingItemStacks[var3] != null && brewingItemStacks[var3].itemID == Item.potion.itemID)
					{
						int var4 = brewingItemStacks[var3].getItemDamage();
						int var5 = getPotionResult(var4, var1);
						if(!ItemPotion.isSplash(var4) && ItemPotion.isSplash(var5))
						{
							var2 = true;
							break;
						}
						List var6 = Item.potion.getEffects(var4);
						List var7 = Item.potion.getEffects(var5);
						if((var4 <= 0 || var6 != var7) && (var6 == null || !var6.equals(var7) && var7 != null) && var4 != var5)
						{
							var2 = true;
							break;
						}
					}
				}
				return var2;
			}
		} else return false;
	}
	
	@Override public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_)
	{
		return true;
	}
	
	@Override public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_)
	{
		return isItemValidForSlot(p_102007_1_, p_102007_2_);
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(p_70298_1_ >= 0 && p_70298_1_ < brewingItemStacks.length)
		{
			ItemStack var3 = brewingItemStacks[p_70298_1_];
			brewingItemStacks[p_70298_1_] = null;
			return var3;
		} else return null;
	}
	
	public void func_94131_a(String p_94131_1_)
	{
		field_94132_e = p_94131_1_;
	}
	
	@Override public int[] getAccessibleSlotsFromSide(int p_94128_1_)
	{
		return p_94128_1_ == 1 ? field_102017_a : field_102016_b;
	}
	
	public int getBrewTime()
	{
		return brewTime;
	}
	
	public int getFilledSlots()
	{
		int var1 = 0;
		for(int var2 = 0; var2 < 3; ++var2)
		{
			if(brewingItemStacks[var2] != null)
			{
				var1 |= 1 << var2;
			}
		}
		return var1;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return isInvNameLocalized() ? field_94132_e : "container.brewing";
	}
	
	private int getPotionResult(int p_70352_1_, ItemStack p_70352_2_)
	{
		return p_70352_2_ == null ? p_70352_1_ : Item.itemsList[p_70352_2_.itemID].isPotionIngredient() ? PotionHelper.applyIngredient(p_70352_1_, Item.itemsList[p_70352_2_.itemID].getPotionEffect()) : p_70352_1_;
	}
	
	@Override public int getSizeInventory()
	{
		return brewingItemStacks.length;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return p_70301_1_ >= 0 && p_70301_1_ < brewingItemStacks.length ? brewingItemStacks[p_70301_1_] : null;
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(p_70304_1_ >= 0 && p_70304_1_ < brewingItemStacks.length)
		{
			ItemStack var2 = brewingItemStacks[p_70304_1_];
			brewingItemStacks[p_70304_1_] = null;
			return var2;
		} else return null;
	}
	
	@Override public boolean isInvNameLocalized()
	{
		return field_94132_e != null && field_94132_e.length() > 0;
	}
	
	@Override public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return p_94041_1_ == 3 ? Item.itemsList[p_94041_2_.itemID].isPotionIngredient() : p_94041_2_.itemID == Item.potion.itemID || p_94041_2_.itemID == Item.glassBottle.itemID;
	}
	
	@Override public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this ? false : p_70300_1_.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}
	
	@Override public void openChest()
	{
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		NBTTagList var2 = p_70307_1_.getTagList("Items");
		brewingItemStacks = new ItemStack[getSizeInventory()];
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			if(var5 >= 0 && var5 < brewingItemStacks.length)
			{
				brewingItemStacks[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
		brewTime = p_70307_1_.getShort("BrewTime");
		if(p_70307_1_.hasKey("CustomName"))
		{
			field_94132_e = p_70307_1_.getString("CustomName");
		}
	}
	
	public void setBrewTime(int par1)
	{
		brewTime = par1;
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		if(p_70299_1_ >= 0 && p_70299_1_ < brewingItemStacks.length)
		{
			brewingItemStacks[p_70299_1_] = p_70299_2_;
		}
	}
	
	@Override public void updateEntity()
	{
		if(brewTime > 0)
		{
			--brewTime;
			if(brewTime == 0)
			{
				brewPotions();
				onInventoryChanged();
			} else if(!canBrew())
			{
				brewTime = 0;
				onInventoryChanged();
			} else if(ingredientID != brewingItemStacks[3].itemID)
			{
				brewTime = 0;
				onInventoryChanged();
			}
		} else if(canBrew())
		{
			brewTime = 400;
			ingredientID = brewingItemStacks[3].itemID;
		}
		int var1 = getFilledSlots();
		if(var1 != filledSlots)
		{
			filledSlots = var1;
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, var1, 2);
		}
		super.updateEntity();
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setShort("BrewTime", (short) brewTime);
		NBTTagList var2 = new NBTTagList();
		for(int var3 = 0; var3 < brewingItemStacks.length; ++var3)
		{
			if(brewingItemStacks[var3] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte) var3);
				brewingItemStacks[var3].writeToNBT(var4);
				var2.appendTag(var4);
			}
		}
		p_70310_1_.setTag("Items", var2);
		if(isInvNameLocalized())
		{
			p_70310_1_.setString("CustomName", field_94132_e);
		}
	}
}
