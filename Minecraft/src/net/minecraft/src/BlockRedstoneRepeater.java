package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneLogic
{
	public static final double[] repeaterTorchOffset = new double[] { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
	private static final int[] repeaterState = new int[] { 1, 2, 3, 4 };
	
	protected BlockRedstoneRepeater(int p_i3934_1_, boolean p_i3934_2_)
	{
		super(p_i3934_1_, p_i3934_2_);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		func_94483_i_(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_);
	}
	
	@Override public boolean func_94476_e(IBlockAccess p_94476_1_, int p_94476_2_, int p_94476_3_, int p_94476_4_, int p_94476_5_)
	{
		return func_94482_f(p_94476_1_, p_94476_2_, p_94476_3_, p_94476_4_, p_94476_5_) > 0;
	}
	
	@Override protected boolean func_94477_d(int p_94477_1_)
	{
		return isRedstoneRepeaterBlockID(p_94477_1_);
	}
	
	@Override protected int func_94481_j_(int p_94481_1_)
	{
		return repeaterState[(p_94481_1_ & 12) >> 2] * 2;
	}
	
	@Override protected BlockRedstoneLogic func_94484_i()
	{
		return Block.redstoneRepeaterIdle;
	}
	
	@Override protected BlockRedstoneLogic func_94485_e()
	{
		return Block.redstoneRepeaterActive;
	}
	
	@Override public int getRenderType()
	{
		return 15;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.redstoneRepeater.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.redstoneRepeater.itemID;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		int var10 = p_71903_1_.getBlockMetadata(p_71903_2_, p_71903_3_, p_71903_4_);
		int var11 = (var10 & 12) >> 2;
		var11 = var11 + 1 << 2 & 12;
		p_71903_1_.setBlockMetadataWithNotify(p_71903_2_, p_71903_3_, p_71903_4_, var11 | var10 & 3, 3);
		return true;
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(isRepeaterPowered)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			int var7 = getDirection(var6);
			double var8 = par2 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.2D;
			double var10 = par3 + 0.4F + (par5Random.nextFloat() - 0.5F) * 0.2D;
			double var12 = par4 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.2D;
			double var14 = 0.0D;
			double var16 = 0.0D;
			if(par5Random.nextInt(2) == 0)
			{
				switch(var7)
				{
					case 0:
						var16 = -0.3125D;
						break;
					case 1:
						var14 = 0.3125D;
						break;
					case 2:
						var16 = 0.3125D;
						break;
					case 3:
						var14 = -0.3125D;
				}
			} else
			{
				int var18 = (var6 & 12) >> 2;
				switch(var7)
				{
					case 0:
						var16 = repeaterTorchOffset[var18];
						break;
					case 1:
						var14 = -repeaterTorchOffset[var18];
						break;
					case 2:
						var16 = -repeaterTorchOffset[var18];
						break;
					case 3:
						var14 = repeaterTorchOffset[var18];
				}
			}
			par1World.spawnParticle("reddust", var8 + var14, var10, var12 + var16, 0.0D, 0.0D, 0.0D);
		}
	}
}
