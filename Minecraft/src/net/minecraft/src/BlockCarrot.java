package net.minecraft.src;

public class BlockCarrot extends BlockCrops
{
	private Icon[] iconArray;
	
	public BlockCarrot(int p_i5101_1_)
	{
		super(p_i5101_1_);
	}
	
	@Override protected int getCropItem()
	{
		return Item.carrot.itemID;
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
		return Item.carrot.itemID;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[4];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon("carrots_" + var2);
		}
	}
}
