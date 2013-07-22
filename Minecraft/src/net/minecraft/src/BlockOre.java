package net.minecraft.src;

import java.util.Random;

public class BlockOre extends Block
{
	public BlockOre(int p_i9076_1_)
	{
		super(p_i9076_1_, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return blockID == Block.oreLapis.blockID ? 4 : 0;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
		super.dropBlockAsItemWithChance(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, p_71914_5_, p_71914_6_, p_71914_7_);
		if(idDropped(p_71914_5_, p_71914_1_.rand, p_71914_7_) != blockID)
		{
			int var8 = 0;
			if(blockID == Block.oreCoal.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(p_71914_1_.rand, 0, 2);
			} else if(blockID == Block.oreDiamond.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(p_71914_1_.rand, 3, 7);
			} else if(blockID == Block.oreEmerald.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(p_71914_1_.rand, 3, 7);
			} else if(blockID == Block.oreLapis.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(p_71914_1_.rand, 2, 5);
			} else if(blockID == Block.oreNetherQuartz.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(p_71914_1_.rand, 2, 5);
			}
			dropXpOnBlockBreak(p_71914_1_, p_71914_2_, p_71914_3_, p_71914_4_, var8);
		}
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return blockID == Block.oreCoal.blockID ? Item.coal.itemID : blockID == Block.oreDiamond.blockID ? Item.diamond.itemID : blockID == Block.oreLapis.blockID ? Item.dyePowder.itemID : blockID == Block.oreEmerald.blockID ? Item.emerald.itemID : blockID == Block.oreNetherQuartz.blockID ? Item.netherQuartz.itemID : blockID;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return blockID == Block.oreLapis.blockID ? 4 + p_71925_1_.nextInt(5) : 1;
	}
	
	@Override public int quantityDroppedWithBonus(int p_71910_1_, Random p_71910_2_)
	{
		if(p_71910_1_ > 0 && blockID != idDropped(0, p_71910_2_, p_71910_1_))
		{
			int var3 = p_71910_2_.nextInt(p_71910_1_ + 2) - 1;
			if(var3 < 0)
			{
				var3 = 0;
			}
			return quantityDropped(p_71910_2_) * (var3 + 1);
		} else return quantityDropped(p_71910_2_);
	}
}
