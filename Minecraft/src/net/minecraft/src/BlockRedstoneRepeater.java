package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneRepeater extends BlockRedstoneLogic
{
	public static final double[] repeaterTorchOffset = new double[] { -0.0625D, 0.0625D, 0.1875D, 0.3125D };
	private static final int[] repeaterState = new int[] { 1, 2, 3, 4 };
	
	protected BlockRedstoneRepeater(int par1, boolean par2)
	{
		super(par1, par2);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		func_94483_i_(par1World, par2, par3, par4);
	}
	
	@Override public boolean func_94476_e(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return func_94482_f(par1IBlockAccess, par2, par3, par4, par5) > 0;
	}
	
	@Override protected boolean func_94477_d(int par1)
	{
		return isRedstoneRepeaterBlockID(par1);
	}
	
	@Override protected int func_94481_j_(int par1)
	{
		return repeaterState[(par1 & 12) >> 2] * 2;
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
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Item.redstoneRepeater.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.redstoneRepeater.itemID;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		int var10 = par1World.getBlockMetadata(par2, par3, par4);
		int var11 = (var10 & 12) >> 2;
		var11 = var11 + 1 << 2 & 12;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var11 | var10 & 3, 3);
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
