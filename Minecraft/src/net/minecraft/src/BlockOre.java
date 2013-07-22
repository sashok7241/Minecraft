package net.minecraft.src;

import java.util.Random;

public class BlockOre extends Block
{
	public BlockOre(int par1)
	{
		super(par1, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int par1)
	{
		return blockID == Block.oreLapis.blockID ? 4 : 0;
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
		if(idDropped(par5, par1World.rand, par7) != blockID)
		{
			int var8 = 0;
			if(blockID == Block.oreCoal.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 0, 2);
			} else if(blockID == Block.oreDiamond.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
			} else if(blockID == Block.oreEmerald.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 3, 7);
			} else if(blockID == Block.oreLapis.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
			} else if(blockID == Block.oreNetherQuartz.blockID)
			{
				var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
			}
			dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
		}
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return blockID == Block.oreCoal.blockID ? Item.coal.itemID : blockID == Block.oreDiamond.blockID ? Item.diamond.itemID : blockID == Block.oreLapis.blockID ? Item.dyePowder.itemID : blockID == Block.oreEmerald.blockID ? Item.emerald.itemID : blockID == Block.oreNetherQuartz.blockID ? Item.netherQuartz.itemID : blockID;
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return blockID == Block.oreLapis.blockID ? 4 + par1Random.nextInt(5) : 1;
	}
	
	@Override public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		if(par1 > 0 && blockID != idDropped(0, par2Random, par1))
		{
			int var3 = par2Random.nextInt(par1 + 2) - 1;
			if(var3 < 0)
			{
				var3 = 0;
			}
			return quantityDropped(par2Random) * (var3 + 1);
		} else return quantityDropped(par2Random);
	}
}
