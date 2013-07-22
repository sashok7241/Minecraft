package net.minecraft.src;

public class ItemSoup extends ItemFood
{
	public ItemSoup(int par1, int par2)
	{
		super(par1, par2, false);
		setMaxStackSize(1);
	}
	
	@Override public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
		return new ItemStack(Item.bowlEmpty);
	}
}
