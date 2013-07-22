package net.minecraft.src;

public class ItemSeedFood extends ItemFood
{
	private int cropId;
	private int soilId;
	
	public ItemSeedFood(int par1, int par2, float par3, int par4, int par5)
	{
		super(par1, par2, par3, false);
		cropId = par4;
		soilId = par5;
	}
	
	@Override public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(par7 != 1) return false;
		else if(par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6, par7, par1ItemStack))
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			if(var11 == soilId && par3World.isAirBlock(par4, par5 + 1, par6))
			{
				par3World.setBlock(par4, par5 + 1, par6, cropId);
				--par1ItemStack.stackSize;
				return true;
			} else return false;
		} else return false;
	}
}
