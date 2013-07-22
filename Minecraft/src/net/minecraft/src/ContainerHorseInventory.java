package net.minecraft.src;

public class ContainerHorseInventory extends Container
{
	private IInventory field_111243_a;
	private EntityHorse field_111242_f;
	
	public ContainerHorseInventory(IInventory par1IInventory, IInventory par2IInventory, EntityHorse par3EntityHorse)
	{
		field_111243_a = par2IInventory;
		field_111242_f = par3EntityHorse;
		byte var4 = 3;
		par2IInventory.openChest();
		int var5 = (var4 - 4) * 18;
		addSlotToContainer(new ContainerHorseInventorySlotSaddle(this, par2IInventory, 0, 8, 18));
		addSlotToContainer(new ContainerHorseInventorySlotArmor(this, par2IInventory, 1, 8, 36, par3EntityHorse));
		int var6;
		int var7;
		if(par3EntityHorse.func_110261_ca())
		{
			for(var6 = 0; var6 < var4; ++var6)
			{
				for(var7 = 0; var7 < 5; ++var7)
				{
					addSlotToContainer(new Slot(par2IInventory, 2 + var7 + var6 * 5, 80 + var7 * 18, 18 + var6 * 18));
				}
			}
		}
		for(var6 = 0; var6 < 3; ++var6)
		{
			for(var7 = 0; var7 < 9; ++var7)
			{
				addSlotToContainer(new Slot(par1IInventory, var7 + var6 * 9 + 9, 8 + var7 * 18, 102 + var6 * 18 + var5));
			}
		}
		for(var6 = 0; var6 < 9; ++var6)
		{
			addSlotToContainer(new Slot(par1IInventory, var6, 8 + var6 * 18, 160 + var5));
		}
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return field_111243_a.isUseableByPlayer(par1EntityPlayer) && field_111242_f.isEntityAlive() && field_111242_f.getDistanceToEntity(par1EntityPlayer) < 8.0F;
	}
	
	@Override public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		field_111243_a.closeChest();
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(par2 < field_111243_a.getSizeInventory())
			{
				if(!mergeItemStack(var5, field_111243_a.getSizeInventory(), inventorySlots.size(), true)) return null;
			} else if(getSlot(1).isItemValid(var5) && !getSlot(1).getHasStack())
			{
				if(!mergeItemStack(var5, 1, 2, false)) return null;
			} else if(getSlot(0).isItemValid(var5))
			{
				if(!mergeItemStack(var5, 0, 1, false)) return null;
			} else if(field_111243_a.getSizeInventory() <= 2 || !mergeItemStack(var5, 2, field_111243_a.getSizeInventory(), false)) return null;
			if(var5.stackSize == 0)
			{
				var4.putStack((ItemStack) null);
			} else
			{
				var4.onSlotChanged();
			}
		}
		return var3;
	}
}
