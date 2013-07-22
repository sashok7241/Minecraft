package net.minecraft.src;

public class ContainerBrewingStand extends Container
{
	private TileEntityBrewingStand tileBrewingStand;
	private final Slot theSlot;
	private int brewTime = 0;
	
	public ContainerBrewingStand(InventoryPlayer p_i3600_1_, TileEntityBrewingStand p_i3600_2_)
	{
		tileBrewingStand = p_i3600_2_;
		addSlotToContainer(new SlotBrewingStandPotion(p_i3600_1_.player, p_i3600_2_, 0, 56, 46));
		addSlotToContainer(new SlotBrewingStandPotion(p_i3600_1_.player, p_i3600_2_, 1, 79, 53));
		addSlotToContainer(new SlotBrewingStandPotion(p_i3600_1_.player, p_i3600_2_, 2, 102, 46));
		theSlot = addSlotToContainer(new SlotBrewingStandIngredient(this, p_i3600_2_, 3, 79, 17));
		int var3;
		for(var3 = 0; var3 < 3; ++var3)
		{
			for(int var4 = 0; var4 < 9; ++var4)
			{
				addSlotToContainer(new Slot(p_i3600_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		for(var3 = 0; var3 < 9; ++var3)
		{
			addSlotToContainer(new Slot(p_i3600_1_, var3, 8 + var3 * 18, 142));
		}
	}
	
	@Override public void addCraftingToCrafters(ICrafting p_75132_1_)
	{
		super.addCraftingToCrafters(p_75132_1_);
		p_75132_1_.sendProgressBarUpdate(this, 0, tileBrewingStand.getBrewTime());
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return tileBrewingStand.isUseableByPlayer(p_75145_1_);
	}
	
	@Override public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int var1 = 0; var1 < crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting) crafters.get(var1);
			if(brewTime != tileBrewingStand.getBrewTime())
			{
				var2.sendProgressBarUpdate(this, 0, tileBrewingStand.getBrewTime());
			}
		}
		brewTime = tileBrewingStand.getBrewTime();
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(p_82846_2_);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if((p_82846_2_ < 0 || p_82846_2_ > 2) && p_82846_2_ != 3)
			{
				if(!theSlot.getHasStack() && theSlot.isItemValid(var5))
				{
					if(!mergeItemStack(var5, 3, 4, false)) return null;
				} else if(SlotBrewingStandPotion.canHoldPotion(var3))
				{
					if(!mergeItemStack(var5, 0, 3, false)) return null;
				} else if(p_82846_2_ >= 4 && p_82846_2_ < 31)
				{
					if(!mergeItemStack(var5, 31, 40, false)) return null;
				} else if(p_82846_2_ >= 31 && p_82846_2_ < 40)
				{
					if(!mergeItemStack(var5, 4, 31, false)) return null;
				} else if(!mergeItemStack(var5, 4, 40, false)) return null;
			} else
			{
				if(!mergeItemStack(var5, 4, 40, true)) return null;
				var4.onSlotChange(var5, var3);
			}
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
			tileBrewingStand.setBrewTime(par2);
		}
	}
}
