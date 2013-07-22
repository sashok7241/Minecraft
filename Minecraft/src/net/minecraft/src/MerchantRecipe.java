package net.minecraft.src;

public class MerchantRecipe
{
	private ItemStack itemToBuy;
	private ItemStack secondItemToBuy;
	private ItemStack itemToSell;
	private int toolUses;
	private int maxTradeUses;
	
	public MerchantRecipe(ItemStack p_i3724_1_, Item p_i3724_2_)
	{
		this(p_i3724_1_, new ItemStack(p_i3724_2_));
	}
	
	public MerchantRecipe(ItemStack p_i3723_1_, ItemStack p_i3723_2_)
	{
		this(p_i3723_1_, (ItemStack) null, p_i3723_2_);
	}
	
	public MerchantRecipe(ItemStack p_i3722_1_, ItemStack p_i3722_2_, ItemStack p_i3722_3_)
	{
		itemToBuy = p_i3722_1_;
		secondItemToBuy = p_i3722_2_;
		itemToSell = p_i3722_3_;
		maxTradeUses = 7;
	}
	
	public MerchantRecipe(NBTTagCompound p_i3721_1_)
	{
		readFromTags(p_i3721_1_);
	}
	
	public void func_82783_a(int p_82783_1_)
	{
		maxTradeUses += p_82783_1_;
	}
	
	public boolean func_82784_g()
	{
		return toolUses >= maxTradeUses;
	}
	
	public void func_82785_h()
	{
		toolUses = maxTradeUses;
	}
	
	public ItemStack getItemToBuy()
	{
		return itemToBuy;
	}
	
	public ItemStack getItemToSell()
	{
		return itemToSell;
	}
	
	public ItemStack getSecondItemToBuy()
	{
		return secondItemToBuy;
	}
	
	public boolean hasSameIDsAs(MerchantRecipe p_77393_1_)
	{
		return itemToBuy.itemID == p_77393_1_.itemToBuy.itemID && itemToSell.itemID == p_77393_1_.itemToSell.itemID ? secondItemToBuy == null && p_77393_1_.secondItemToBuy == null || secondItemToBuy != null && p_77393_1_.secondItemToBuy != null && secondItemToBuy.itemID == p_77393_1_.secondItemToBuy.itemID : false;
	}
	
	public boolean hasSameItemsAs(MerchantRecipe p_77391_1_)
	{
		return hasSameIDsAs(p_77391_1_) && (itemToBuy.stackSize < p_77391_1_.itemToBuy.stackSize || secondItemToBuy != null && secondItemToBuy.stackSize < p_77391_1_.secondItemToBuy.stackSize);
	}
	
	public boolean hasSecondItemToBuy()
	{
		return secondItemToBuy != null;
	}
	
	public void incrementToolUses()
	{
		++toolUses;
	}
	
	public void readFromTags(NBTTagCompound p_77390_1_)
	{
		NBTTagCompound var2 = p_77390_1_.getCompoundTag("buy");
		itemToBuy = ItemStack.loadItemStackFromNBT(var2);
		NBTTagCompound var3 = p_77390_1_.getCompoundTag("sell");
		itemToSell = ItemStack.loadItemStackFromNBT(var3);
		if(p_77390_1_.hasKey("buyB"))
		{
			secondItemToBuy = ItemStack.loadItemStackFromNBT(p_77390_1_.getCompoundTag("buyB"));
		}
		if(p_77390_1_.hasKey("uses"))
		{
			toolUses = p_77390_1_.getInteger("uses");
		}
		if(p_77390_1_.hasKey("maxUses"))
		{
			maxTradeUses = p_77390_1_.getInteger("maxUses");
		} else
		{
			maxTradeUses = 7;
		}
	}
	
	public NBTTagCompound writeToTags()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		var1.setCompoundTag("buy", itemToBuy.writeToNBT(new NBTTagCompound("buy")));
		var1.setCompoundTag("sell", itemToSell.writeToNBT(new NBTTagCompound("sell")));
		if(secondItemToBuy != null)
		{
			var1.setCompoundTag("buyB", secondItemToBuy.writeToNBT(new NBTTagCompound("buyB")));
		}
		var1.setInteger("uses", toolUses);
		var1.setInteger("maxUses", maxTradeUses);
		return var1;
	}
}
