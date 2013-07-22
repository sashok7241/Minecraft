package net.minecraft.src;

import java.util.Random;

public class WorldGenTrees extends WorldGenerator
{
	private final int minTreeHeight;
	private final boolean vinesGrow;
	private final int metaWood;
	private final int metaLeaves;
	
	public WorldGenTrees(boolean par1)
	{
		this(par1, 4, 0, 0, false);
	}
	
	public WorldGenTrees(boolean par1, int par2, int par3, int par4, boolean par5)
	{
		super(par1);
		minTreeHeight = par2;
		metaWood = par3;
		metaLeaves = par4;
		vinesGrow = par5;
	}
	
	@Override public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
	{
		int var6 = par2Random.nextInt(3) + minTreeHeight;
		boolean var7 = true;
		if(par4 >= 1 && par4 + var6 + 1 <= 256)
		{
			int var8;
			byte var9;
			int var11;
			int var12;
			for(var8 = par4; var8 <= par4 + 1 + var6; ++var8)
			{
				var9 = 1;
				if(var8 == par4)
				{
					var9 = 0;
				}
				if(var8 >= par4 + 1 + var6 - 2)
				{
					var9 = 2;
				}
				for(int var10 = par3 - var9; var10 <= par3 + var9 && var7; ++var10)
				{
					for(var11 = par5 - var9; var11 <= par5 + var9 && var7; ++var11)
					{
						if(var8 >= 0 && var8 < 256)
						{
							var12 = par1World.getBlockId(var10, var8, var11);
							if(var12 != 0 && var12 != Block.leaves.blockID && var12 != Block.grass.blockID && var12 != Block.dirt.blockID && var12 != Block.wood.blockID)
							{
								var7 = false;
							}
						} else
						{
							var7 = false;
						}
					}
				}
			}
			if(!var7) return false;
			else
			{
				var8 = par1World.getBlockId(par3, par4 - 1, par5);
				if((var8 == Block.grass.blockID || var8 == Block.dirt.blockID) && par4 < 256 - var6 - 1)
				{
					setBlock(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
					var9 = 3;
					byte var19 = 0;
					int var13;
					int var14;
					int var15;
					for(var11 = par4 - var9 + var6; var11 <= par4 + var6; ++var11)
					{
						var12 = var11 - (par4 + var6);
						var13 = var19 + 1 - var12 / 2;
						for(var14 = par3 - var13; var14 <= par3 + var13; ++var14)
						{
							var15 = var14 - par3;
							for(int var16 = par5 - var13; var16 <= par5 + var13; ++var16)
							{
								int var17 = var16 - par5;
								if(Math.abs(var15) != var13 || Math.abs(var17) != var13 || par2Random.nextInt(2) != 0 && var12 != 0)
								{
									int var18 = par1World.getBlockId(var14, var11, var16);
									if(var18 == 0 || var18 == Block.leaves.blockID)
									{
										setBlockAndMetadata(par1World, var14, var11, var16, Block.leaves.blockID, metaLeaves);
									}
								}
							}
						}
					}
					for(var11 = 0; var11 < var6; ++var11)
					{
						var12 = par1World.getBlockId(par3, par4 + var11, par5);
						if(var12 == 0 || var12 == Block.leaves.blockID)
						{
							setBlockAndMetadata(par1World, par3, par4 + var11, par5, Block.wood.blockID, metaWood);
							if(vinesGrow && var11 > 0)
							{
								if(par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + var11, par5))
								{
									setBlockAndMetadata(par1World, par3 - 1, par4 + var11, par5, Block.vine.blockID, 8);
								}
								if(par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + var11, par5))
								{
									setBlockAndMetadata(par1World, par3 + 1, par4 + var11, par5, Block.vine.blockID, 2);
								}
								if(par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 - 1))
								{
									setBlockAndMetadata(par1World, par3, par4 + var11, par5 - 1, Block.vine.blockID, 1);
								}
								if(par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + var11, par5 + 1))
								{
									setBlockAndMetadata(par1World, par3, par4 + var11, par5 + 1, Block.vine.blockID, 4);
								}
							}
						}
					}
					if(vinesGrow)
					{
						for(var11 = par4 - 3 + var6; var11 <= par4 + var6; ++var11)
						{
							var12 = var11 - (par4 + var6);
							var13 = 2 - var12 / 2;
							for(var14 = par3 - var13; var14 <= par3 + var13; ++var14)
							{
								for(var15 = par5 - var13; var15 <= par5 + var13; ++var15)
								{
									if(par1World.getBlockId(var14, var11, var15) == Block.leaves.blockID)
									{
										if(par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 - 1, var11, var15) == 0)
										{
											growVines(par1World, var14 - 1, var11, var15, 8);
										}
										if(par2Random.nextInt(4) == 0 && par1World.getBlockId(var14 + 1, var11, var15) == 0)
										{
											growVines(par1World, var14 + 1, var11, var15, 2);
										}
										if(par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 - 1) == 0)
										{
											growVines(par1World, var14, var11, var15 - 1, 1);
										}
										if(par2Random.nextInt(4) == 0 && par1World.getBlockId(var14, var11, var15 + 1) == 0)
										{
											growVines(par1World, var14, var11, var15 + 1, 4);
										}
									}
								}
							}
						}
						if(par2Random.nextInt(5) == 0 && var6 > 5)
						{
							for(var11 = 0; var11 < 2; ++var11)
							{
								for(var12 = 0; var12 < 4; ++var12)
								{
									if(par2Random.nextInt(4 - var11) == 0)
									{
										var13 = par2Random.nextInt(3);
										setBlockAndMetadata(par1World, par3 + Direction.offsetX[Direction.rotateOpposite[var12]], par4 + var6 - 5 + var11, par5 + Direction.offsetZ[Direction.rotateOpposite[var12]], Block.cocoaPlant.blockID, var13 << 2 | var12);
									}
								}
							}
						}
					}
					return true;
				} else return false;
			}
		} else return false;
	}
	
	private void growVines(World par1World, int par2, int par3, int par4, int par5)
	{
		setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
		int var6 = 4;
		while(true)
		{
			--par3;
			if(par1World.getBlockId(par2, par3, par4) != 0 || var6 <= 0) return;
			setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
			--var6;
		}
	}
}
