package net.minecraft.src;

public class InventoryMerchant implements IInventory
{
	private final IMerchant theMerchant;
	private ItemStack[] theInventory = new ItemStack[3];
	private final EntityPlayer thePlayer;
	private MerchantRecipe currentRecipe;
	private int currentRecipeIndex;
	
	public InventoryMerchant(EntityPlayer p_i3612_1_, IMerchant p_i3612_2_)
	{
		thePlayer = p_i3612_1_;
		theMerchant = p_i3612_2_;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if(theInventory[p_70298_1_] != null)
		{
			ItemStack var3;
			if(p_70298_1_ == 2)
			{
				var3 = theInventory[p_70298_1_];
				theInventory[p_70298_1_] = null;
				return var3;
			} else if(theInventory[p_70298_1_].stackSize <= p_70298_2_)
			{
				var3 = theInventory[p_70298_1_];
				theInventory[p_70298_1_] = null;
				if(inventoryResetNeededOnSlotChange(p_70298_1_))
				{
					resetRecipeAndSlots();
				}
				return var3;
			} else
			{
				var3 = theInventory[p_70298_1_].splitStack(p_70298_2_);
				if(theInventory[p_70298_1_].stackSize == 0)
				{
					theInventory[p_70298_1_] = null;
				}
				if(inventoryResetNeededOnSlotChange(p_70298_1_))
				{
					resetRecipeAndSlots();
				}
				return var3;
			}
		} else return null;
	}
	
	public MerchantRecipe getCurrentRecipe()
	{
		return currentRecipe;
	}
	
	@Override public int getInventoryStackLimit()
	{
		return 64;
	}
	
	@Override public String getInvName()
	{
		return "mob.villager";
	}
	
	@Override public int getSizeInventory()
	{
		return theInventory.length;
	}
	
	@Override public ItemStack getStackInSlot(int p_70301_1_)
	{
		return theInventory[p_70301_1_];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if(theInventory[p_70304_1_] != null)
		{
			ItemStack var2 = theInventory[p_70304_1_];
			theInventory[p_70304_1_] = null;
			return var2;
		} else return null;
	}
	
	private boolean inventoryResetNeededOnSlotChange(int p_70469_1_)
	{
		return p_70469_1_ == 0 || p_70469_1_ == 1;
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
		return theMerchant.getCustomer() == p_70300_1_;
	}
	
	@Override public void onInventoryChanged()
	{
		resetRecipeAndSlots();
	}
	
	@Override public void openChest()
	{
	}
	
	public void resetRecipeAndSlots()
	{
		currentRecipe = null;
		ItemStack var1 = theInventory[0];
		ItemStack var2 = theInventory[1];
		if(var1 == null)
		{
			var1 = var2;
			var2 = null;
		}
		if(var1 == null)
		{
			setInventorySlotContents(2, (ItemStack) null);
		} else
		{
			MerchantRecipeList var3 = theMerchant.getRecipes(thePlayer);
			if(var3 != null)
			{
				MerchantRecipe var4 = var3.canRecipeBeUsed(var1, var2, currentRecipeIndex);
				if(var4 != null && !var4.func_82784_g())
				{
					currentRecipe = var4;
					setInventorySlotContents(2, var4.getItemToSell().copy());
				} else if(var2 != null)
				{
					var4 = var3.canRecipeBeUsed(var2, var1, currentRecipeIndex);
					if(var4 != null && !var4.func_82784_g())
					{
						currentRecipe = var4;
						setInventorySlotContents(2, var4.getItemToSell().copy());
					} else
					{
						setInventorySlotContents(2, (ItemStack) null);
					}
				} else
				{
					setInventorySlotContents(2, (ItemStack) null);
				}
			}
		}
	}
	
	public void setCurrentRecipeIndex(int p_70471_1_)
	{
		currentRecipeIndex = p_70471_1_;
		resetRecipeAndSlots();
	}
	
	@Override public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		theInventory[p_70299_1_] = p_70299_2_;
		if(p_70299_2_ != null && p_70299_2_.stackSize > getInventoryStackLimit())
		{
			p_70299_2_.stackSize = getInventoryStackLimit();
		}
		if(inventoryResetNeededOnSlotChange(p_70299_1_))
		{
			resetRecipeAndSlots();
		}
	}
}
