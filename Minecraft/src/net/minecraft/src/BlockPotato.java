package net.minecraft.src;

public class BlockPotato extends BlockCrops
{
	private Icon[] iconArray;
	
	public BlockPotato(int p_i5105_1_)
	{
		super(p_i5105_1_);
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
		if(!p_71914_1_.isRemote)
		{
			if(p_71914_5_ >= 7 && p_71914_1_.rand.nextInt(50) == 0)
			{
				dropBlockAsItem_do(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, new ItemStack(Item.poisonousPotato));
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
