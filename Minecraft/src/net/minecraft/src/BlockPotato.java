package net.minecraft.src;

public class BlockPotato extends BlockCrops
{
	private Icon[] iconArray;
	
	public BlockPotato(int par1)
	{
		super(par1);
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		if(!par1World.isRemote)
		{
			if(par5 >= 7 && par1World.rand.nextInt(50) == 0)
			{
				dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(Item.poisonousPotato));
			}
		}
	}
	
	@Override protected int getCropItem()
	{
		return Item.potato.itemID;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 < 7)
		{
			if(par2 == 6)
			{
				par2 = 5;
			}
			return iconArray[par2 >> 1];
		} else return iconArray[3];
	}
	
	@Override protected int getSeedItem()
	{
		return Item.potato.itemID;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[4];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon("potatoes_" + var2);
		}
	}
}
