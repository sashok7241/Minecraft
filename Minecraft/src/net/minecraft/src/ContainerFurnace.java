package net.minecraft.src;

public class ContainerFurnace extends Container
{
	private TileEntityFurnace furnace;
	private int lastCookTime = 0;
	private int lastBurnTime = 0;
	private int lastItemBurnTime = 0;
	
	public ContainerFurnace(InventoryPlayer p_i3607_1_, TileEntityFurnace p_i3607_2_)
	{
		furnace = p_i3607_2_;
		addSlotToContainer(new Slot(p_i3607_2_, 0, 56, 17));
		addSlotToContainer(new Slot(p_i3607_2_, 1, 56, 53));
		addSlotToContainer(new SlotFurnace(p_i3607_1_.player, p_i3607_2_, 2, 116, 35));
		int var3;
		for(var3 = 0; var3 < 3; ++var3)
		{
			for(int var4 = 0; var4 < 9; ++var4)
			{
				addSlotToContainer(new Slot(p_i3607_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		for(var3 = 0; var3 < 9; ++var3)
		{
			addSlotToContainer(new Slot(p_i3607_1_, var3, 8 + var3 * 18, 142));
		}
	}
	
	@Override public void addCraftingToCrafters(ICrafting p_75132_1_)
	{
		super.addCraftingToCrafters(p_75132_1_);
		p_75132_1_.sendProgressBarUpdate(this, 0, furnace.furnaceCookTime);
		p_75132_1_.sendProgressBarUpdate(this, 1, furnace.furnaceBurnTime);
		p_75132_1_.sendProgressBarUpdate(this, 2, furnace.currentItemBurnTime);
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return furnace.isUseableByPlayer(p_75145_1_);
	}
	
	@Override public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int var1 = 0; var1 < crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting) crafters.get(var1);
			if(lastCookTime != furnace.furnaceCookTime)
			{
				var2.sendProgressBarUpdate(this, 0, furnace.furnaceCookTime);
			}
			if(lastBurnTime != furnace.furnaceBurnTime)
			{
				var2.sendProgressBarUpdate(this, 1, furnace.furnaceBurnTime);
			}
			if(lastItemBurnTime != furnace.currentItemBurnTime)
			{
				var2.sendProgressBarUpdate(this, 2, furnace.currentItemBurnTime);
			}
		}
		lastCookTime = furnace.furnaceCookTime;
		lastBurnTime = furnace.furnaceBurnTime;
		lastItemBurnTime = furnace.currentItemBurnTime;
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(p_82846_2_);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(p_82846_2_ == 2)
			{
				if(!mergeItemStack(var5, 3, 39, true)) return null;
				var4.onSlotChange(var5, var3);
			} else if(p_82846_2_ != 1 && p_82846_2_ != 0)
			{
				if(FurnaceRecipes.smelting().getSmeltingResult(var5.getItem().itemID) != null)
				{
					if(!mergeItemStack(var5, 0, 1, false)) return null;
				} else if(TileEntityFurnace.isItemFuel(var5))
				{
					if(!mergeItemStack(var5, 1, 2, false)) return null;
				} else if(p_82846_2_ >= 3 && p_82846_2_ < 30)
				{
					if(!mergeItemStack(var5, 30, 39, false)) return null;
				} else if(p_82846_2_ >= 30 && p_82846_2_ < 39 && !mergeItemStack(var5, 3, 30, false)) return null;
			} else if(!mergeItemStack(var5, 3, 39, false)) return null;
			if(var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			} else
			{
				var4.onSlotChanged();
			}
			if(var5.stackSize == var3.stackSize) return null;
			var4.onPickupFromSlot(p_82846_1_, var5);
		}
		return var3;
	}
	
	@Override public void updateProgressBar(int par1, int par2)
	{
		if(par1 == 0)
		{
			furnace.furnaceCookTime = par2;
		}
		if(par1 == 1)
		{
			furnace.furnaceBurnTime = par2;
		}
		if(par1 == 2)
		{
			furnace.currentItemBurnTime = par2;
		}
	}
}
