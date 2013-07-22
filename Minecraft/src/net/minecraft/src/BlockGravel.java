package net.minecraft.src;

import java.util.Random;

public class BlockGravel extends BlockSand
{
	public BlockGravel(int par1)
	{
		super(par1);
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		if(par3 > 3)
		{
			par3 = 3;
		}
		return par2Random.nextInt(10 - par3 * 3) == 0 ? Item.flint.itemID : blockID;
	}
}
