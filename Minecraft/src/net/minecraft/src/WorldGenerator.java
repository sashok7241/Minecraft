package net.minecraft.src;

import java.util.Random;

public abstract class WorldGenerator
{
	private final boolean doBlockNotify;
	
	public WorldGenerator()
	{
		doBlockNotify = false;
	}
	
	public WorldGenerator(boolean par1)
	{
		doBlockNotify = par1;
	}
	
	public abstract boolean generate(World var1, Random var2, int var3, int var4, int var5);
	
	protected void setBlock(World par1World, int par2, int par3, int par4, int par5)
	{
		setBlockAndMetadata(par1World, par2, par3, par4, par5, 0);
	}
	
	protected void setBlockAndMetadata(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if(doBlockNotify)
		{
			par1World.setBlock(par2, par3, par4, par5, par6, 3);
		} else
		{
			par1World.setBlock(par2, par3, par4, par5, par6, 2);
		}
	}
	
	public void setScale(double par1, double par3, double par5)
	{
	}
}
