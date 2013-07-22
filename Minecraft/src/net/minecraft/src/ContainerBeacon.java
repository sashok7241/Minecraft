package net.minecraft.src;

public class ContainerBeacon extends Container
{
	private TileEntityBeacon theBeacon;
	private final SlotBeacon beaconSlot;
	private int field_82865_g;
	private int field_82867_h;
	private int field_82868_i;
	
	public ContainerBeacon(InventoryPlayer par1InventoryPlayer, TileEntityBeacon par2TileEntityBeacon)
	{
		theBeacon = par2TileEntityBeacon;
		addSlotToContainer(beaconSlot = new SlotBeacon(this, par2TileEntityBeacon, 0, 136, 110));
		byte var3 = 36;
		short var4 = 137;
		int var5;
		for(var5 = 0; var5 < 3; ++var5)
		{
			for(int var6 = 0; var6 < 9; ++var6)
			{
				addSlotToContainer(new Slot(par1InventoryPlayer, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
			}
		}
		for(var5 = 0; var5 < 9; ++var5)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, var5, var3 + var5 * 18, 58 + var4));
		}
		field_82865_g = par2TileEntityBeacon.getLevels();
		field_82867_h = par2TileEntityBeacon.getPrimaryEffect();
		field_82868_i = par2TileEntityBeacon.getSecondaryEffect();
	}
	
	@Override public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0, field_82865_g);
		par1ICrafting.sendProgressBarUpdate(this, 1, field_82867_h);
		par1ICrafting.sendProgressBarUpdate(this, 2, field_82868_i);
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return theBeacon.isUseableByPlayer(par1EntityPlayer);
	}
	
	public TileEntityBeacon getBeacon()
	{
		return theBeacon;
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(par2 == 0)
			{
				if(!mergeItemStack(var5, 1, 37, true)) return null;
				var4.onSlotChange(var5, var3);
			} else if(!beaconSlot.getHasStack() && beaconSlot.isItemValid(var5) && var5.stackSize == 1)
			{
				if(!mergeItemStack(var5, 0, 1, false)) return null;
			} else if(par2 >= 1 && par2 < 28)
			{
				if(!mergeItemStack(var5, 28, 37, false)) return null;
			} else if(par2 >= 28 && par2 < 37)
			{
				if(!mergeItemStack(var5, 1, 28, false)) return null;
			} else if(!mergeItemStack(var5, 1, 37, false)) return null;
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
			theBeacon.setLevels(par2);
		}
		if(par1 == 1)
		{
			theBeacon.setPrimaryEffect(par2);
		}
		if(par1 == 2)
		{
			theBeacon.setSecondaryEffect(par2);
		}
	}
}
