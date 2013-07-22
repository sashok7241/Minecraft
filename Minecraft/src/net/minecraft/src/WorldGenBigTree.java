package net.minecraft.src;

import java.util.Random;

public class WorldGenBigTree extends WorldGenerator
{
	static final byte[] otherCoordPairs = new byte[] { (byte) 2, (byte) 0, (byte) 0, (byte) 1, (byte) 2, (byte) 1 };
	Random rand = new Random();
	World worldObj;
	int[] basePos = new int[] { 0, 0, 0 };
	int heightLimit = 0;
	int height;
	double heightAttenuation = 0.618D;
	double branchDensity = 1.0D;
	double branchSlope = 0.381D;
	double scaleWidth = 1.0D;
	double leafDensity = 1.0D;
	int trunkSize = 1;
	int heightLimitLimit = 12;
	int leafDistanceLimit = 4;
	int[][] leafNodes;
	
	public WorldGenBigTree(boolean p_i3784_1_)
	{
		super(p_i3784_1_);
	}
	
	int checkBlockLine(int[] p_76496_1_, int[] p_76496_2_)
	{
		int[] var3 = new int[] { 0, 0, 0 };
		byte var4 = 0;
		byte var5;
		for(var5 = 0; var4 < 3; ++var4)
		{
			var3[var4] = p_76496_2_[var4] - p_76496_1_[var4];
			if(Math.abs(var3[var4]) > Math.abs(var3[var5]))
			{
				var5 = var4;
			}
		}
		if(var3[var5] == 0) return -1;
		else
		{
			byte var6 = otherCoordPairs[var5];
			byte var7 = otherCoordPairs[var5 + 3];
			byte var8;
			if(var3[var5] > 0)
			{
				var8 = 1;
			} else
			{
				var8 = -1;
			}
			double var9 = (double) var3[var6] / (double) var3[var5];
			double var11 = (double) var3[var7] / (double) var3[var5];
			int[] var13 = new int[] { 0, 0, 0 };
			int var14 = 0;
			int var15;
			for(var15 = var3[var5] + var8; var14 != var15; var14 += var8)
			{
				var13[var5] = p_76496_1_[var5] + var14;
				var13[var6] = MathHelper.floor_double(p_76496_1_[var6] + var14 * var9);
				var13[var7] = MathHelper.floor_double(p_76496_1_[var7] + var14 * var11);
				int var16 = worldObj.getBlockId(var13[0], var13[1], var13[2]);
				if(var16 != 0 && var16 != Block.leaves.blockID)
				{
					break;
				}
			}
			return var14 == var15 ? -1 : Math.abs(var14);
		}
	}
	
	@Override public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		worldObj = p_76484_1_;
		long var6 = p_76484_2_.nextLong();
		rand.setSeed(var6);
		basePos[0] = p_76484_3_;
		basePos[1] = p_76484_4_;
		basePos[2] = p_76484_5_;
		if(heightLimit == 0)
		{
			heightLimit = 5 + rand.nextInt(heightLimitLimit);
		}
		if(!validTreeLocation()) return false;
		else
		{
			generateLeafNodeList();
			generateLeaves();
			generateTrunk();
			generateLeafNodeBases();
			return true;
		}
	}
	
	void generateLeafNode(int p_76491_1_, int p_76491_2_, int p_76491_3_)
	{
		int var4 = p_76491_2_;
		for(int var5 = p_76491_2_ + leafDistanceLimit; var4 < var5; ++var4)
		{
			float var6 = leafSize(var4 - p_76491_2_);
			genTreeLayer(p_76491_1_, var4, p_76491_3_, var6, (byte) 1, Block.leaves.blockID);
		}
	}
	
	void generateLeafNodeBases()
	{
		int var1 = 0;
		int var2 = leafNodes.length;
		for(int[] var3 = new int[] { basePos[0], basePos[1], basePos[2] }; var1 < var2; ++var1)
		{
			int[] var4 = leafNodes[var1];
			int[] var5 = new int[] { var4[0], var4[1], var4[2] };
			var3[1] = var4[3];
			int var6 = var3[1] - basePos[1];
			if(leafNodeNeedsBase(var6))
			{
				placeBlockLine(var3, var5, (byte) Block.wood.blockID);
			}
		}
	}
	
	void generateLeafNodeList()
	{
		height = (int) (heightLimit * heightAttenuation);
		if(height >= heightLimit)
		{
			height = heightLimit - 1;
		}
		int var1 = (int) (1.382D + Math.pow(leafDensity * heightLimit / 13.0D, 2.0D));
		if(var1 < 1)
		{
			var1 = 1;
		}
		int[][] var2 = new int[var1 * heightLimit][4];
		int var3 = basePos[1] + heightLimit - leafDistanceLimit;
		int var4 = 1;
		int var5 = basePos[1] + height;
		int var6 = var3 - basePos[1];
		var2[0][0] = basePos[0];
		var2[0][1] = var3;
		var2[0][2] = basePos[2];
		var2[0][3] = var5;
		--var3;
		while(var6 >= 0)
		{
			int var7 = 0;
			float var8 = layerSize(var6);
			if(var8 < 0.0F)
			{
				--var3;
				--var6;
			} else
			{
				for(double var9 = 0.5D; var7 < var1; ++var7)
				{
					double var11 = scaleWidth * var8 * (rand.nextFloat() + 0.328D);
					double var13 = rand.nextFloat() * 2.0D * Math.PI;
					int var15 = MathHelper.floor_double(var11 * Math.sin(var13) + basePos[0] + var9);
					int var16 = MathHelper.floor_double(var11 * Math.cos(var13) + basePos[2] + var9);
					int[] var17 = new int[] { var15, var3, var16 };
					int[] var18 = new int[] { var15, var3 + leafDistanceLimit, var16 };
					if(checkBlockLine(var17, var18) == -1)
					{
						int[] var19 = new int[] { basePos[0], basePos[1], basePos[2] };
						double var20 = Math.sqrt(Math.pow(Math.abs(basePos[0] - var17[0]), 2.0D) + Math.pow(Math.abs(basePos[2] - var17[2]), 2.0D));
						double var22 = var20 * branchSlope;
						if(var17[1] - var22 > var5)
						{
							var19[1] = var5;
						} else
						{
							var19[1] = (int) (var17[1] - var22);
						}
						if(checkBlockLine(var19, var17) == -1)
						{
							var2[var4][0] = var15;
							var2[var4][1] = var3;
							var2[var4][2] = var16;
							var2[var4][3] = var19[1];
							++var4;
						}
					}
				}
				--var3;
				--var6;
			}
		}
		leafNodes = new int[var4][4];
		System.arraycopy(var2, 0, leafNodes, 0, var4);
	}
	
	void generateLeaves()
	{
		int var1 = 0;
		for(int var2 = leafNodes.length; var1 < var2; ++var1)
		{
			int var3 = leafNodes[var1][0];
			int var4 = leafNodes[var1][1];
			int var5 = leafNodes[var1][2];
			generateLeafNode(var3, var4, var5);
		}
	}
	
	void generateTrunk()
	{
		int var1 = basePos[0];
		int var2 = basePos[1];
		int var3 = basePos[1] + height;
		int var4 = basePos[2];
		int[] var5 = new int[] { var1, var2, var4 };
		int[] var6 = new int[] { var1, var3, var4 };
		placeBlockLine(var5, var6, Block.wood.blockID);
		if(trunkSize == 2)
		{
			++var5[0];
			++var6[0];
			placeBlockLine(var5, var6, Block.wood.blockID);
			++var5[2];
			++var6[2];
			placeBlockLine(var5, var6, Block.wood.blockID);
			var5[0] += -1;
			var6[0] += -1;
			placeBlockLine(var5, var6, Block.wood.blockID);
		}
	}
	
	void genTreeLayer(int p_76492_1_, int p_76492_2_, int p_76492_3_, float p_76492_4_, byte p_76492_5_, int p_76492_6_)
	{
		int var7 = (int) (p_76492_4_ + 0.618D);
		byte var8 = otherCoordPairs[p_76492_5_];
		byte var9 = otherCoordPairs[p_76492_5_ + 3];
		int[] var10 = new int[] { p_76492_1_, p_76492_2_, p_76492_3_ };
		int[] var11 = new int[] { 0, 0, 0 };
		int var12 = -var7;
		int var13 = -var7;
		for(var11[p_76492_5_] = var10[p_76492_5_]; var12 <= var7; ++var12)
		{
			var11[var8] = var10[var8] + var12;
			var13 = -var7;
			while(var13 <= var7)
			{
				double var15 = Math.pow(Math.abs(var12) + 0.5D, 2.0D) + Math.pow(Math.abs(var13) + 0.5D, 2.0D);
				if(var15 > p_76492_4_ * p_76492_4_)
				{
					++var13;
				} else
				{
					var11[var9] = var10[var9] + var13;
					int var14 = worldObj.getBlockId(var11[0], var11[1], var11[2]);
					if(var14 != 0 && var14 != Block.leaves.blockID)
					{
						++var13;
					} else
					{
						setBlockAndMetadata(worldObj, var11[0], var11[1], var11[2], p_76492_6_, 0);
						++var13;
					}
				}
			}
		}
	}
	
	float layerSize(int p_76490_1_)
	{
		if(p_76490_1_ < heightLimit * 0.3D) return -1.618F;
		else
		{
			float var2 = heightLimit / 2.0F;
			float var3 = heightLimit / 2.0F - p_76490_1_;
			float var4;
			if(var3 == 0.0F)
			{
				var4 = var2;
			} else if(Math.abs(var3) >= var2)
			{
				var4 = 0.0F;
			} else
			{
				var4 = (float) Math.sqrt(Math.pow(Math.abs(var2), 2.0D) - Math.pow(Math.abs(var3), 2.0D));
			}
			var4 *= 0.5F;
			return var4;
		}
	}
	
	boolean leafNodeNeedsBase(int p_76493_1_)
	{
		return p_76493_1_ >= heightLimit * 0.2D;
	}
	
	float leafSize(int p_76495_1_)
	{
		return p_76495_1_ >= 0 && p_76495_1_ < leafDistanceLimit ? p_76495_1_ != 0 && p_76495_1_ != leafDistanceLimit - 1 ? 3.0F : 2.0F : -1.0F;
	}
	
	void placeBlockLine(int[] p_76500_1_, int[] p_76500_2_, int p_76500_3_)
	{
		int[] var4 = new int[] { 0, 0, 0 };
		byte var5 = 0;
		byte var6;
		for(var6 = 0; var5 < 3; ++var5)
		{
			var4[var5] = p_76500_2_[var5] - p_76500_1_[var5];
			if(Math.abs(var4[var5]) > Math.abs(var4[var6]))
			{
				var6 = var5;
			}
		}
		if(var4[var6] != 0)
		{
			byte var7 = otherCoordPairs[var6];
			byte var8 = otherCoordPairs[var6 + 3];
			byte var9;
			if(var4[var6] > 0)
			{
				var9 = 1;
			} else
			{
				var9 = -1;
			}
			double var10 = (double) var4[var7] / (double) var4[var6];
			double var12 = (double) var4[var8] / (double) var4[var6];
			int[] var14 = new int[] { 0, 0, 0 };
			int var15 = 0;
			for(int var16 = var4[var6] + var9; var15 != var16; var15 += var9)
			{
				var14[var6] = MathHelper.floor_double(p_76500_1_[var6] + var15 + 0.5D);
				var14[var7] = MathHelper.floor_double(p_76500_1_[var7] + var15 * var10 + 0.5D);
				var14[var8] = MathHelper.floor_double(p_76500_1_[var8] + var15 * var12 + 0.5D);
				byte var17 = 0;
				int var18 = Math.abs(var14[0] - p_76500_1_[0]);
				int var19 = Math.abs(var14[2] - p_76500_1_[2]);
				int var20 = Math.max(var18, var19);
				if(var20 > 0)
				{
					if(var18 == var20)
					{
						var17 = 4;
					} else if(var19 == var20)
					{
						var17 = 8;
					}
				}
				setBlockAndMetadata(worldObj, var14[0], var14[1], var14[2], p_76500_3_, var17);
			}
		}
	}
	
	@Override public void setScale(double p_76487_1_, double p_76487_3_, double p_76487_5_)
	{
		heightLimitLimit = (int) (p_76487_1_ * 12.0D);
		if(p_76487_1_ > 0.5D)
		{
			leafDistanceLimit = 5;
		}
		scaleWidth = p_76487_3_;
		leafDensity = p_76487_5_;
	}
	
	boolean validTreeLocation()
	{
		int[] var1 = new int[] { basePos[0], basePos[1], basePos[2] };
		int[] var2 = new int[] { basePos[0], basePos[1] + heightLimit - 1, basePos[2] };
		int var3 = worldObj.getBlockId(basePos[0], basePos[1] - 1, basePos[2]);
		if(var3 != 2 && var3 != 3) return false;
		else
		{
			int var4 = checkBlockLine(var1, var2);
			if(var4 == -1) return true;
			else if(var4 < 6) return false;
			else
			{
				heightLimit = var4;
				return true;
			}
		}
	}
}
