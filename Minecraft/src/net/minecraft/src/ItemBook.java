package net.minecraft.src;

public class ItemBook extends Item
{
	public ItemBook(int p_i8010_1_)
	{
		super(p_i8010_1_);
	}
	
	@Override public int getItemEnchantability()
	{
		return 1;
	}
	
	@Override public boolean isItemTool(ItemStack p_77616_1_)
	{
		return p_77616_1_.stackSize == 1;
	}
}
