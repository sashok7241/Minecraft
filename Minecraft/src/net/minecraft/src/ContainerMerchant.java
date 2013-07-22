package net.minecraft.src;

public class ContainerMerchant extends Container
{
	private IMerchant theMerchant;
	private InventoryMerchant merchantInventory;
	private final World theWorld;
	
	public ContainerMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World)
	{
		theMerchant = par2IMerchant;
		theWorld = par3World;
		merchantInventory = new InventoryMerchant(par1InventoryPlayer.player, par2IMerchant);
		addSlotToContainer(new Slot(merchantInventory, 0, 36, 53));
		addSlotToContainer(new Slot(merchantInventory, 1, 62, 53));
		addSlotToContainer(new SlotMerchantResult(par1InventoryPlayer.player, par2IMerchant, merchantInventory, 2, 120, 53));
		int var4;
		for(var4 = 0; var4 < 3; ++var4)
		{
			for(int var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(par1InventoryPlayer, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
			}
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, var4, 8 + var4 * 18, 142));
		}
	}
	
	@Override public void addCraftingToCrafters(ICrafting par1ICrafting)
	{
		super.addCraftingToCrafters(par1ICrafting);
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return theMerchant.getCustomer() == par1EntityPlayer;
	}
	
	@Override public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}
	
	public InventoryMerchant getMerchantInventory()
	{
		return merchantInventory;
	}
	
	@Override public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		theMerchant.setCustomer((EntityPlayer) null);
		super.onContainerClosed(par1EntityPlayer);
		if(!theWorld.isRemote)
		{
			ItemStack var2 = merchantInventory.getStackInSlotOnClosing(0);
			if(var2 != null)
			{
				par1EntityPlayer.dropPlayerItem(var2);
			}
			var2 = merchantInventory.getStackInSlotOnClosing(1);
			if(var2 != null)
			{
				par1EntityPlayer.dropPlayerItem(var2);
			}
		}
	}
	
	@Override public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		merchantInventory.resetRecipeAndSlots();
		super.onCraftMatrixChanged(par1IInventory);
	}
	
	public void setCurrentRecipeIndex(int par1)
	{
		merchantInventory.setCurrentRecipeIndex(par1);
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(par2);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(par2 == 2)
			{
				if(!mergeItemStack(var5, 3, 39, true)) return null;
				var4.onSlotChange(var5, var3);
			} else if(par2 != 0 && par2 != 1)
			{
				if(par2 >= 3 && par2 < 30)
				{
					if(!mergeItemStack(var5, 30, 39, false)) return null;
				} else if(par2 >= 30 && par2 < 39 && !mergeItemStack(var5, 3, 30, false)) return null;
			} else if(!mergeItemStack(var5, 3, 39, false)) return null;
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
	}
}
