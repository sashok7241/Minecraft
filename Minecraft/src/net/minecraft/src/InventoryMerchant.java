package net.minecraft.src;

public class InventoryMerchant implements IInventory
{
	private final IMerchant theMerchant;
	private ItemStack[] theInventory = new ItemStack[3];
	private final EntityPlayer thePlayer;
	private MerchantRecipe currentRecipe;
	private int currentRecipeIndex;
	
	public InventoryMerchant(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant)
	{
		thePlayer = par1EntityPlayer;
		theMerchant = par2IMerchant;
	}
	
	@Override public void closeChest()
	{
	}
	
	@Override public ItemStack decrStackSize(int par1, int par2)
	{
		if(theInventory[par1] != null)
		{
			ItemStack var3;
			if(par1 == 2)
			{
				var3 = theInventory[par1];
				theInventory[par1] = null;
				return var3;
			} else if(theInventory[par1].stackSize <= par2)
			{
				var3 = theInventory[par1];
				theInventory[par1] = null;
				if(inventoryResetNeededOnSlotChange(par1))
				{
					resetRecipeAndSlots();
				}
				return var3;
			} else
			{
				var3 = theInventory[par1].splitStack(par2);
				if(theInventory[par1].stackSize == 0)
				{
					theInventory[par1] = null;
				}
				if(inventoryResetNeededOnSlotChange(par1))
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
	
	@Override public ItemStack getStackInSlot(int par1)
	{
		return theInventory[par1];
	}
	
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(theInventory[par1] != null)
		{
			ItemStack var2 = theInventory[par1];
			theInventory[par1] = null;
			return var2;
		} else return null;
	}
	
	private boolean inventoryResetNeededOnSlotChange(int par1)
	{
		return par1 == 0 || par1 == 1;
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
		return theMerchant.getCustomer() == par1EntityPlayer;
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
		theMerchant.func_110297_a_(getStackInSlot(2));
	}
	
	public void setCurrentRecipeIndex(int par1)
	{
		currentRecipeIndex = par1;
		resetRecipeAndSlots();
	}
	
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		theInventory[par1] = par2ItemStack;
		if(par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit())
		{
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
		if(inventoryResetNeededOnSlotChange(par1))
		{
			resetRecipeAndSlots();
		}
	}
}
