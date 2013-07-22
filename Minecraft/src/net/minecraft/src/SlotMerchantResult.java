package net.minecraft.src;

public class SlotMerchantResult extends Slot
{
	private final InventoryMerchant theMerchantInventory;
	private EntityPlayer thePlayer;
	private int field_75231_g;
	private final IMerchant theMerchant;
	
	public SlotMerchantResult(EntityPlayer p_i3614_1_, IMerchant p_i3614_2_, InventoryMerchant p_i3614_3_, int p_i3614_4_, int p_i3614_5_, int p_i3614_6_)
	{
		super(p_i3614_3_, p_i3614_4_, p_i3614_5_, p_i3614_6_);
		thePlayer = p_i3614_1_;
		theMerchant = p_i3614_2_;
		theMerchantInventory = p_i3614_3_;
	}
	
	@Override public ItemStack decrStackSize(int p_75209_1_)
	{
		if(getHasStack())
		{
			field_75231_g += Math.min(p_75209_1_, getStack().stackSize);
		}
		return super.decrStackSize(p_75209_1_);
	}
	
	private boolean func_75230_a(MerchantRecipe p_75230_1_, ItemStack p_75230_2_, ItemStack p_75230_3_)
	{
		ItemStack var4 = p_75230_1_.getItemToBuy();
		ItemStack var5 = p_75230_1_.getSecondItemToBuy();
		if(p_75230_2_ != null && p_75230_2_.itemID == var4.itemID)
		{
			if(var5 != null && p_75230_3_ != null && var5.itemID == p_75230_3_.itemID)
			{
				p_75230_2_.stackSize -= var4.stackSize;
				p_75230_3_.stackSize -= var5.stackSize;
				return true;
			}
			if(var5 == null && p_75230_3_ == null)
			{
				p_75230_2_.stackSize -= var4.stackSize;
				return true;
			}
		}
		return false;
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return false;
	}
	
	@Override protected void onCrafting(ItemStack p_75208_1_)
	{
		p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, field_75231_g);
		field_75231_g = 0;
	}
	
	@Override protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
	{
		field_75231_g += p_75210_2_;
		this.onCrafting(p_75210_1_);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		this.onCrafting(p_82870_2_);
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
