package net.minecraft.src;

public class ContainerWorkbench extends Container
{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerWorkbench(InventoryPlayer p_i3603_1_, World p_i3603_2_, int p_i3603_3_, int p_i3603_4_, int p_i3603_5_)
	{
		worldObj = p_i3603_2_;
		posX = p_i3603_3_;
		posY = p_i3603_4_;
		posZ = p_i3603_5_;
		addSlotToContainer(new SlotCrafting(p_i3603_1_.player, craftMatrix, craftResult, 0, 124, 35));
		int var6;
		int var7;
		for(var6 = 0; var6 < 3; ++var6)
		{
			for(var7 = 0; var7 < 3; ++var7)
			{
				addSlotToContainer(new Slot(craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
			}
		}
		for(var6 = 0; var6 < 3; ++var6)
		{
			for(var7 = 0; var7 < 9; ++var7)
			{
				addSlotToContainer(new Slot(p_i3603_1_, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}
		for(var6 = 0; var6 < 9; ++var6)
		{
			addSlotToContainer(new Slot(p_i3603_1_, var6, 8 + var6 * 18, 142));
		}
		onCraftMatrixChanged(craftMatrix);
	}
	
	@Override public boolean canInteractWith(EntityPlayer p_75145_1_)
	{
		return worldObj.getBlockId(posX, posY, posZ) != Block.workbench.blockID ? false : p_75145_1_.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}
	
	@Override public boolean func_94530_a(ItemStack p_94530_1_, Slot p_94530_2_)
	{
		return p_94530_2_.inventory != craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
	}
	
	@Override public void onContainerClosed(EntityPlayer p_75134_1_)
	{
		super.onContainerClosed(p_75134_1_);
		if(!worldObj.isRemote)
		{
			for(int var2 = 0; var2 < 9; ++var2)
			{
				ItemStack var3 = craftMatrix.getStackInSlotOnClosing(var2);
				if(var3 != null)
				{
					p_75134_1_.dropPlayerItem(var3);
				}
			}
		}
	}
	
	@Override public void onCraftMatrixChanged(IInventory p_75130_1_)
	{
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
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
				if(!mergeItemStack(var5, 10, 46, true)) return null;
				var4.onSlotChange(var5, var3);
			} else if(p_82846_2_ >= 10 && p_82846_2_ < 37)
			{
				if(!mergeItemStack(var5, 37, 46, false)) return null;
			} else if(p_82846_2_ >= 37 && p_82846_2_ < 46)
			{
				if(!mergeItemStack(var5, 10, 37, false)) return null;
			} else if(!mergeItemStack(var5, 10, 46, false)) return null;
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
