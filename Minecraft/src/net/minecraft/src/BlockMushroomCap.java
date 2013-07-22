package net.minecraft.src;

import java.util.Random;

public class BlockMushroomCap extends Block
{
	private static final String[] field_94429_a = new String[] { "mushroom_skin_brown", "mushroom_skin_red" };
	private final int mushroomType;
	private Icon[] iconArray;
	private Icon field_94426_cO;
	private Icon field_94427_cP;
	
	public BlockMushroomCap(int p_i9065_1_, Material p_i9065_2_, int p_i9065_3_)
	{
		super(p_i9065_1_, p_i9065_2_);
		mushroomType = p_i9065_3_;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par2 == 10 && par1 > 1 ? field_94426_cO : par2 >= 1 && par2 <= 9 && par1 == 1 ? iconArray[mushroomType] : par2 >= 1 && par2 <= 3 && par1 == 2 ? iconArray[mushroomType] : par2 >= 7 && par2 <= 9 && par1 == 3 ? iconArray[mushroomType] : (par2 == 1 || par2 == 4 || par2 == 7) && par1 == 4 ? iconArray[mushroomType] : (par2 == 3 || par2 == 6 || par2 == 9) && par1 == 5 ? iconArray[mushroomType] : par2 == 14 ? iconArray[mushroomType] : par2 == 15 ? field_94426_cO : field_94427_cP;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.mushroomBrown.blockID + mushroomType;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.mushroomBrown.blockID + mushroomType;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		int var2 = p_71925_1_.nextInt(10) - 7;
		if(var2 < 0)
		{
			var2 = 0;
		}
		return var2;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[field_94429_a.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(field_94429_a[var2]);
		}
		field_94427_cP = par1IconRegister.registerIcon("mushroom_inside");
		field_94426_cO = par1IconRegister.registerIcon("mushroom_skin_stem");
	}
}
