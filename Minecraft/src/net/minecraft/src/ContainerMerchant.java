package net.minecraft.src;

public class ContainerMerchant extends Container
{
	private IMerchant theMerchant;
	private InventoryMerchant merchantInventory;
	private final World theWorld;
	
	public ContainerMerchant(InventoryPlayer p_i3613_1_, IMerchant p_i3613_2_, World p_i3613_3_)
	{
		theMerchant = p_i3613_2_;
		theWorld = p_i3613_3_;
		merchantInventory = new InventoryMerchant(p_i3613_1_.player, p_i3613_2_);
		addSlotToContainer(new Slot(merchantInventory, 0, 36, 53));
		addSlotToContainer(new Slot(merchantInventory, 1, 62, 53));
		addSlotToContainer(new SlotMerchantResult(p_i3613_1_.player, p_i3613_2_, merchantInventory, 2, 120, 53));
		int var4;
		for(var4 = 0; var4 < 3; ++var4)
		{
			for(int var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(p_i3613_1_, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
			}
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			addSlotToContainer(new Slot(p_i3613_1_, var4, 8 + var4 * 18, 142));
		}
	}
	
	@Override public void addCraftingToCrafters(ICrafting p_75132_1_)
	{
		super.addCraftingToCrafters(p_75132_1_);
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return theMerchant.getCustomer() == p_75145_1_;
	}
	
	@Override public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}
	
	public InventoryMerchant getMerchantInventory()
	{
		return merchantInventory;
	}
	
	@Override public void onContainerClosed(EntityPlayer p_75134_1_)
	{
		super.onContainerClosed(p_75134_1_);
		theMerchant.setCustomer((EntityPlayer) null);
		super.onContainerClosed(p_75134_1_);
		if(!theWorld.isRemote)
		{
			ItemStack var2 = merchantInventory.getStackInSlotOnClosing(0);
			if(var2 != null)
			{
				p_75134_1_.dropPlayerItem(var2);
			}
			var2 = merchantInventory.getStackInSlotOnClosing(1);
			if(var2 != null)
			{
				p_75134_1_.dropPlayerItem(var2);
			}
		}
	}
	
	@Override public void onCraftMatrixChanged(IInventory p_75130_1_)
	{
		merchantInventory.resetRecipeAndSlots();
		super.onCraftMatrixChanged(p_75130_1_);
	}
	
	public void setCurrentRecipeIndex(int p_75175_1_)
	{
		merchantInventory.setCurrentRecipeIndex(p_75175_1_);
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
			} else if(p_82846_2_ != 0 && p_82846_2_ != 1)
			{
				if(p_82846_2_ >= 3 && p_82846_2_ < 30)
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
	}
}
