package net.minecraft.src;

public class ItemBook extends Item
{
	public ItemBook(int par1)
	{
		super(par1);
	}
	
	@Override public int getItemEnchantability()
	{
		return 1;
	}
	
	@Override public boolean isItemTool(ItemStack par1ItemStack)
	{
		return par1ItemStack.stackSize == 1;
	}
}
