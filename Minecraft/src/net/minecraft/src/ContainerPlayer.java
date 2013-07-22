package net.minecraft.src;

public class ContainerPlayer extends Container
{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
	public IInventory craftResult = new InventoryCraftResult();
	public boolean isLocalWorld = false;
	private final EntityPlayer thePlayer;
	
	public ContainerPlayer(InventoryPlayer p_i5077_1_, boolean p_i5077_2_, EntityPlayer p_i5077_3_)
	{
		isLocalWorld = p_i5077_2_;
		thePlayer = p_i5077_3_;
		addSlotToContainer(new SlotCrafting(p_i5077_1_.player, craftMatrix, craftResult, 0, 144, 36));
		int var4;
		int var5;
		for(var4 = 0; var4 < 2; ++var4)
		{
			for(var5 = 0; var5 < 2; ++var5)
			{
				addSlotToContainer(new Slot(craftMatrix, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
			}
		}
		for(var4 = 0; var4 < 4; ++var4)
		{
			addSlotToContainer(new SlotArmor(this, p_i5077_1_, p_i5077_1_.getSizeInventory() - 1 - var4, 8, 8 + var4 * 18, var4));
		}
		for(var4 = 0; var4 < 3; ++var4)
		{
			for(var5 = 0; var5 < 9; ++var5)
			{
				addSlotToContainer(new Slot(p_i5077_1_, var5 + (var4 + 1) * 9, 8 + var5 * 18, 84 + var4 * 18));
			}
		}
		for(var4 = 0; var4 < 9; ++var4)
		{
			addSlotToContainer(new Slot(p_i5077_1_, var4, 8 + var4 * 18, 142));
		}
		onCraftMatrixChanged(craftMatrix);
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return true;
	}
	
	@Override public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
	{
		return p_94530_2_.inventory != craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
	}
	
	@Override public void onContainerClosed(EntityPlayer p_75134_1_)
	{
		super.onContainerClosed(p_75134_1_);
		for(int var2 = 0; var2 < 4; ++var2)
		{
			ItemStack var3 = craftMatrix.getStackInSlotOnClosing(var2);
			if(var3 != null)
			{
				p_75134_1_.dropPlayerItem(var3);
			}
		}
		craftResult.setInventorySlotContents(0, (ItemStack) null);
	}
	
	@Override public void onCraftMatrixChanged(IInventory p_75130_1_)
	{
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, thePlayer.worldObj));
	}
	
	@Override public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
	{
		ItemStack var3 = null;
		Slot var4 = (Slot) inventorySlots.get(p_82846_2_);
		if(var4 != null && var4.getHasStack())
		{
			ItemStack var5 = var4.getStack();
			var3 = var5.copy();
			if(p_82846_2_ == 0)
			{
				if(!mergeItemStack(var5, 9, 45, true)) return null;
				var4.onSlotChange(var5, var3);
			} else if(p_82846_2_ >= 1 && p_82846_2_ < 5)
			{
				if(!mergeItemStack(var5, 9, 45, false)) return null;
			} else if(p_82846_2_ >= 5 && p_82846_2_ < 9)
			{
				if(!mergeItemStack(var5, 9, 45, false)) return null;
			} else if(var3.getItem() instanceof ItemArmor && !((Slot) inventorySlots.get(5 + ((ItemArmor) var3.getItem()).armorType)).getHasStack())
			{
				int var6 = 5 + ((ItemArmor) var3.getItem()).armorType;
				if(!mergeItemStack(var5, var6, var6 + 1, false)) return null;
			} else if(p_82846_2_ >= 9 && p_82846_2_ < 36)
			{
				if(!mergeItemStack(var5, 36, 45, false)) return null;
			} else if(p_82846_2_ >= 36 && p_82846_2_ < 45)
			{
				if(!mergeItemStack(var5, 9, 36, false)) return null;
			} else if(!mergeItemStack(var5, 9, 45, false)) return null;
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
}
