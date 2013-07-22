package net.minecraft.src;

public class ContainerWorkbench extends Container
{
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	
	public ContainerWorkbench(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5)
	{
		worldObj = par2World;
		posX = par3;
		posY = par4;
		posZ = par5;
		addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, craftMatrix, craftResult, 0, 124, 35));
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
				addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}
		for(var6 = 0; var6 < 9; ++var6)
		{
			addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
		}
		onCraftMatrixChanged(craftMatrix);
	}
	
	@Override public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return worldObj.getBlockId(posX, posY, posZ) != Block.workbench.blockID ? false : par1EntityPlayer.getDistanceSq(posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;
	}
	
	@Override public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot)
	{
		return par2Slot.inventory != craftResult && super.func_94530_a(par1ItemStack, par2Slot);
	}
	
	@Override public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		if(!worldObj.isRemote)
		{
			for(int var2 = 0; var2 < 9; ++var2)
			{
				ItemStack var3 = craftMatrix.getStackInSlotOnClosing(var2);
				if(var3 != null)
				{
					par1EntityPlayer.dropPlayerItem(var3);
				}
			}
		}
	}
	
	@Override public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix, worldObj));
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
				if(!mergeItemStack(var5, 10, 46, true)) return null;
				var4.onSlotChange(var5, var3);
			} else if(par2 >= 10 && par2 < 37)
			{
				if(!mergeItemStack(var5, 37, 46, false)) return null;
			} else if(par2 >= 37 && par2 < 46)
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
			var4.onPickupFromSlot(par1EntityPlayer, var5);
		}
		return var3;
	}
}
