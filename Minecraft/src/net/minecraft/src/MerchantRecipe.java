package net.minecraft.src;

public class MerchantRecipe
{
	private ItemStack itemToBuy;
	private ItemStack secondItemToBuy;
	private ItemStack itemToSell;
	private int toolUses;
	private int maxTradeUses;
	
	public MerchantRecipe(ItemStack par1ItemStack, Item par2Item)
	{
		this(par1ItemStack, new ItemStack(par2Item));
	}
	
	public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		this(par1ItemStack, (ItemStack) null, par2ItemStack);
	}
	
	public MerchantRecipe(ItemStack par1ItemStack, ItemStack par2ItemStack, ItemStack par3ItemStack)
	{
		itemToBuy = par1ItemStack;
		secondItemToBuy = par2ItemStack;
		itemToSell = par3ItemStack;
		maxTradeUses = 7;
	}
	
	public MerchantRecipe(NBTTagCompound par1NBTTagCompound)
	{
		readFromTags(par1NBTTagCompound);
	}
	
	public void func_82783_a(int par1)
	{
		maxTradeUses += par1;
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
	
	public boolean hasSameIDsAs(MerchantRecipe par1MerchantRecipe)
	{
		return itemToBuy.itemID == par1MerchantRecipe.itemToBuy.itemID && itemToSell.itemID == par1MerchantRecipe.itemToSell.itemID ? secondItemToBuy == null && par1MerchantRecipe.secondItemToBuy == null || secondItemToBuy != null && par1MerchantRecipe.secondItemToBuy != null && secondItemToBuy.itemID == par1MerchantRecipe.secondItemToBuy.itemID : false;
	}
	
	public boolean hasSameItemsAs(MerchantRecipe par1MerchantRecipe)
	{
		return hasSameIDsAs(par1MerchantRecipe) && (itemToBuy.stackSize < par1MerchantRecipe.itemToBuy.stackSize || secondItemToBuy != null && secondItemToBuy.stackSize < par1MerchantRecipe.secondItemToBuy.stackSize);
	}
	
	public boolean hasSecondItemToBuy()
	{
		return secondItemToBuy != null;
	}
	
	public void incrementToolUses()
	{
		++toolUses;
	}
	
	public void readFromTags(NBTTagCompound par1NBTTagCompound)
	{
		NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("buy");
		itemToBuy = ItemStack.loadItemStackFromNBT(var2);
		NBTTagCompound var3 = par1NBTTagCompound.getCompoundTag("sell");
		itemToSell = ItemStack.loadItemStackFromNBT(var3);
		if(par1NBTTagCompound.hasKey("buyB"))
		{
			secondItemToBuy = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("buyB"));
		}
		if(par1NBTTagCompound.hasKey("uses"))
		{
			toolUses = par1NBTTagCompound.getInteger("uses");
		}
		if(par1NBTTagCompound.hasKey("maxUses"))
		{
			maxTradeUses = par1NBTTagCompound.getInteger("maxUses");
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
