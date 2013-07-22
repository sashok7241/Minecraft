package net.minecraft.src;

public class SlotMerchantResult extends Slot
{
	private final InventoryMerchant theMerchantInventory;
	private EntityPlayer thePlayer;
	private int field_75231_g;
	private final IMerchant theMerchant;
	
	public SlotMerchantResult(EntityPlayer par1EntityPlayer, IMerchant par2IMerchant, InventoryMerchant par3InventoryMerchant, int par4, int par5, int par6)
	{
		super(par3InventoryMerchant, par4, par5, par6);
		thePlayer = par1EntityPlayer;
		theMerchant = par2IMerchant;
		theMerchantInventory = par3InventoryMerchant;
	}
	
	@Override public ItemStack decrStackSize(int par1)
	{
		if(getHasStack())
		{
			field_75231_g += Math.min(par1, getStack().stackSize);
		}
		return super.decrStackSize(par1);
	}
	
	private boolean func_75230_a(MerchantRecipe par1MerchantRecipe, ItemStack par2ItemStack, ItemStack par3ItemStack)
	{
		ItemStack var4 = par1MerchantRecipe.getItemToBuy();
		ItemStack var5 = par1MerchantRecipe.getSecondItemToBuy();
		if(par2ItemStack != null && par2ItemStack.itemID == var4.itemID)
		{
			if(var5 != null && par3ItemStack != null && var5.itemID == par3ItemStack.itemID)
			{
				par2ItemStack.stackSize -= var4.stackSize;
				par3ItemStack.stackSize -= var5.stackSize;
				return true;
			}
			if(var5 == null && par3ItemStack == null)
			{
				par2ItemStack.stackSize -= var4.stackSize;
				return true;
			}
		}
		return false;
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}
	
	@Override protected void onCrafting(ItemStack par1ItemStack)
	{
		par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, field_75231_g);
		field_75231_g = 0;
	}
	
	@Override protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
		field_75231_g += par2;
		this.onCrafting(par1ItemStack);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		this.onCrafting(par2ItemStack);
		MerchantRecipe var3 = theMerchantInventory.getCurrentRecipe();
		if(var3 != null)
		{
			ItemStack var4 = theMerchantInventory.getStackInSlot(0);
			ItemStack var5 = theMerchantInventory.getStackInSlot(1);
			if(func_75230_a(var3, var4, var5) || func_75230_a(var3, var5, var4))
			{
				if(var4 != null && var4.stackSize <= 0)
				{
					var4 = null;
				}
				if(var5 != null && var5.stackSize <= 0)
				{
					var5 = null;
				}
				theMerchantInventory.setInventorySlotContents(0, var4);
				theMerchantInventory.setInventorySlotContents(1, var5);
				theMerchant.useRecipe(var3);
			}
		}
	}
}
