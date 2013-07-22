package net.minecraft.src;

public class ContainerBrewingStand extends Container
{
	private TileEntityBrewingStand tileBrewingStand;
	private final Slot theSlot;
	private int brewTime;
	
	public ContainerBrewingStand(InventoryPlayer par1InventoryPlayer, TileEntityBrewingStand par2TileEntityBrewingStand)
	{
		tileBrewingStand = par2TileEntityBrewingStand;
		addSlotToContainer(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 0, 56, 46));
		addSlotToContainer(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 1, 79, 53));
		addSlotToContainer(new SlotBrewingStandPotion(par1InventoryPlayer.player, par2TileEntityBrewingStand, 2, 102, 46));
		theSlot = addSlotToContainer(new SlotBrewingStandIngredient(this, par2TileEntityBrewingStand, 3, 79, 17));
		int var3;
		for(var3 = 0; var3 < 3; ++var3)
		{
			for(int var4 = 0; var4 < 9; ++var4)
			{
				addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
			}
		}
		for(var3 = 0; var3 < 9; ++var3)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
		}
	}
	
	@Override public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, tileBrewingStand.getBrewTime());
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return tileBrewingStand.isUseableByPlayer(par1EntityPlayer);
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
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if((par2 < 0 || par2 > 2) && par2 != 3)
			{
				if(!theSlot.getHasStack() && theSlot.isItemValid(var5))
				{
					if(!mergeItemStack(var5, 3, 4, false)) return null;
				} else if(SlotBrewingStandPotion.canHoldPotion(var3))
				{
					if(!mergeItemStack(var5, 0, 3, false)) return null;
				} else if(par2 >= 4 && par2 < 31)
				{
					if(!mergeItemStack(var5, 31, 40, false)) return null;
				} else if(par2 >= 31 && par2 < 40)
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
			var4.onPickupFromSlot(par1EntityPlayer, var5);
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
