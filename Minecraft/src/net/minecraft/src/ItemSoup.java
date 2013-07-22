package net.minecraft.src;

public class ItemSoup extends ItemFood
{
	public ItemSoup(int p_i3624_1_, int p_i3624_2_)
	{
		super(p_i3624_1_, p_i3624_2_, false);
		setMaxStackSize(1);
	}
	
	@Override public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		super.onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
		return new ItemStack(Item.bowlEmpty);
	}
}
