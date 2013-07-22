package net.minecraft.src;

import java.util.Random;

public class BlockGravel extends BlockSand
{
	public BlockGravel(int p_i9058_1_)
	{
		super(p_i9058_1_);
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		if(p_71885_3_ > 3)
		{
			p_71885_3_ = 3;
		}
		return p_71885_2_.nextInt(10 - p_71885_3_ * 3) == 0 ? Item.flint.itemID : blockID;
	}
}
