package net.minecraft.src;

public class AxisAlignedBB
{
	private static final ThreadLocal theAABBLocalPool = new AABBLocalPool();
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	
	protected AxisAlignedBB(double p_i4029_1_, double p_i4029_3_, double p_i4029_5_, double p_i4029_7_, double p_i4029_9_, double p_i4029_11_)
	{
		minX = p_i4029_1_;
		minY = p_i4029_3_;
		minZ = p_i4029_5_;
		maxX = p_i4029_7_;
		maxY = p_i4029_9_;
		maxZ = p_i4029_11_;
	}
	
	public AxisAlignedBB addCoord(double p_72321_1_, double p_72321_3_, double p_72321_5_)
	{
		double var7 = minX;
		double var9 = minY;
		double var11 = minZ;
		double var13 = maxX;
		double var15 = maxY;
		double var17 = maxZ;
		if(p_72321_1_ < 0.0D)
		{
			var7 += p_72321_1_;
		}
		if(p_72321_1_ > 0.0D)
		{
			var13 += p_72321_1_;
		}
		if(p_72321_3_ < 0.0D)
		{
			var9 += p_72321_3_;
		}
		if(p_72321_3_ > 0.0D)
		{
			var15 += p_72321_3_;
		}
		if(p_72321_5_ < 0.0D)
		{
			var11 += p_72321_5_;
		}
		if(p_72321_5_ > 0.0D)
		{
			var17 += p_72321_5_;
		}
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}
	
	public MovingObjectPosition calculateIntercept(Vec3 p_72327_1_, Vec3 p_72327_2_)
	{
		Vec3 var3 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, minX);
		Vec3 var4 = p_72327_1_.getIntermediateWithXValue(p_72327_2_, maxX);
		Vec3 var5 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, minY);
		Vec3 var6 = p_72327_1_.getIntermediateWithYValue(p_72327_2_, maxY);
		Vec3 var7 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, minZ);
		Vec3 var8 = p_72327_1_.getIntermediateWithZValue(p_72327_2_, maxZ);
		if(!isVecInYZ(var3))
		{
			var3 = null;
		}
		if(!isVecInYZ(var4))
		{
			var4 = null;
		}
		if(!isVecInXZ(var5))
		{
			var5 = null;
		}
		if(!isVecInXZ(var6))
		{
			var6 = null;
		}
		if(!isVecInXY(var7))
		{
			var7 = null;
		}
		if(!isVecInXY(var8))
		{
			var8 = null;
		}
		Vec3 var9 = null;
		if(var3 != null && (var9 == null || p_72327_1_.squareDistanceTo(var3) < p_72327_1_.squareDistanceTo(var9)))
		{
			var9 = var3;
		}
		if(var4 != null && (var9 == null || p_72327_1_.squareDistanceTo(var4) < p_72327_1_.squareDistanceTo(var9)))
		{
			var9 = var4;
		}
		if(var5 != null && (var9 == null || p_72327_1_.squareDistanceTo(var5) < p_72327_1_.squareDistanceTo(var9)))
		{
			var9 = var5;
		}
		if(var6 != null && (var9 == null || p_72327_1_.squareDistanceTo(var6) < p_72327_1_.squareDistanceTo(var9)))
		{
			var9 = var6;
		}
		if(var7 != null && (var9 == null || p_72327_1_.squareDistanceTo(var7) < p_72327_1_.squareDistanceTo(var9)))
		{
			var9 = var7;
		}
		if(var8 != null && (var9 == null || p_72327_1_.squareDistanceTo(var8) < p_72327_1_.squareDistanceTo(var9)))
		{
			var9 = var8;
		}
		if(var9 == null) return null;
		else
		{
			byte var10 = -1;
			if(var9 == var3)
			{
				var10 = 4;
			}
			if(var9 == var4)
			{
				var10 = 5;
			}
			if(var9 == var5)
			{
				var10 = 0;
			}
			if(var9 == var6)
			{
				var10 = 1;
			}
			if(var9 == var7)
			{
				var10 = 2;
			}
			if(var9 == var8)
			{
				var10 = 3;
			}
			return new MovingObjectPosition(0, 0, 0, var10, var9);
		}
	}
	
	public double calculateXOffset(AxisAlignedBB p_72316_1_, double p_72316_2_)
	{
		if(p_72316_1_.maxY > minY && p_72316_1_.minY < maxY)
		{
			if(p_72316_1_.maxZ > minZ && p_72316_1_.minZ < maxZ)
			{
				double var4;
				if(p_72316_2_ > 0.0D && p_72316_1_.maxX <= minX)
				{
					var4 = minX - p_72316_1_.maxX;
					if(var4 < p_72316_2_)
					{
						p_72316_2_ = var4;
					}
				}
				if(p_72316_2_ < 0.0D && p_72316_1_.minX >= maxX)
				{
					var4 = maxX - p_72316_1_.minX;
					if(var4 > p_72316_2_)
					{
						p_72316_2_ = var4;
					}
				}
				return p_72316_2_;
			} else return p_72316_2_;
		} else return p_72316_2_;
	}
	
	public double calculateYOffset(AxisAlignedBB p_72323_1_, double p_72323_2_)
	{
		if(p_72323_1_.maxX > minX && p_72323_1_.minX < maxX)
		{
			if(p_72323_1_.maxZ > minZ && p_72323_1_.minZ < maxZ)
			{
				double var4;
				if(p_72323_2_ > 0.0D && p_72323_1_.maxY <= minY)
				{
					var4 = minY - p_72323_1_.maxY;
					if(var4 < p_72323_2_)
					{
						p_72323_2_ = var4;
					}
				}
				if(p_72323_2_ < 0.0D && p_72323_1_.minY >= maxY)
				{
					var4 = maxY - p_72323_1_.minY;
					if(var4 > p_72323_2_)
					{
						p_72323_2_ = var4;
					}
				}
				return p_72323_2_;
			} else return p_72323_2_;
		} else return p_72323_2_;
	}
	
	public double calculateZOffset(AxisAlignedBB p_72322_1_, double p_72322_2_)
	{
		if(p_72322_1_.maxX > minX && p_72322_1_.minX < maxX)
		{
			if(p_72322_1_.maxY > minY && p_72322_1_.minY < maxY)
			{
				double var4;
				if(p_72322_2_ > 0.0D && p_72322_1_.maxZ <= minZ)
				{
					var4 = minZ - p_72322_1_.maxZ;
					if(var4 < p_72322_2_)
					{
						p_72322_2_ = var4;
					}
				}
				if(p_72322_2_ < 0.0D && p_72322_1_.minZ >= maxZ)
				{
					var4 = maxZ - p_72322_1_.minZ;
					if(var4 > p_72322_2_)
					{
						p_72322_2_ = var4;
					}
				}
				return p_72322_2_;
			} else return p_72322_2_;
		} else return p_72322_2_;
	}
	
	public AxisAlignedBB contract(double p_72331_1_, double p_72331_3_, double p_72331_5_)
	{
		double var7 = minX + p_72331_1_;
		double var9 = minY + p_72331_3_;
		double var11 = minZ + p_72331_5_;
		double var13 = maxX - p_72331_1_;
		double var15 = maxY - p_72331_3_;
		double var17 = maxZ - p_72331_5_;
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}
	
	public AxisAlignedBB copy()
	{
		return getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public AxisAlignedBB expand(double p_72314_1_, double p_72314_3_, double p_72314_5_)
	{
		double var7 = minX - p_72314_1_;
		double var9 = minY - p_72314_3_;
		double var11 = minZ - p_72314_5_;
		double var13 = maxX + p_72314_1_;
		double var15 = maxY + p_72314_3_;
		double var17 = maxZ + p_72314_5_;
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}
	
	public double getAverageEdgeLength()
	{
		double var1 = maxX - minX;
		double var3 = maxY - minY;
		double var5 = maxZ - minZ;
		return (var1 + var3 + var5) / 3.0D;
	}
	
	public AxisAlignedBB getOffsetBoundingBox(double p_72325_1_, double p_72325_3_, double p_72325_5_)
	{
		return getAABBPool().getAABB(minX + p_72325_1_, minY + p_72325_3_, minZ + p_72325_5_, maxX + p_72325_1_, maxY + p_72325_3_, maxZ + p_72325_5_);
	}
	
	public boolean intersectsWith(AxisAlignedBB p_72326_1_)
	{
		return p_72326_1_.maxX > minX && p_72326_1_.minX < maxX ? p_72326_1_.maxY > minY && p_72326_1_.minY < maxY ? p_72326_1_.maxZ > minZ && p_72326_1_.minZ < maxZ : false : false;
	}
	
	public boolean isVecInside(Vec3 p_72318_1_)
	{
		return p_72318_1_.xCoord > minX && p_72318_1_.xCoord < maxX ? p_72318_1_.yCoord > minY && p_72318_1_.yCoord < maxY ? p_72318_1_.zCoord > minZ && p_72318_1_.zCoord < maxZ : false : false;
	}
	
	private boolean isVecInXY(Vec3 p_72319_1_)
	{
		return p_72319_1_ == null ? false : p_72319_1_.xCoord >= minX && p_72319_1_.xCoord <= maxX && p_72319_1_.yCoord >= minY && p_72319_1_.yCoord <= maxY;
	}
	
	private boolean isVecInXZ(Vec3 p_72315_1_)
	{
		return p_72315_1_ == null ? false : p_72315_1_.xCoord >= minX && p_72315_1_.xCoord <= maxX && p_72315_1_.zCoord >= minZ && p_72315_1_.zCoord <= maxZ;
	}
	
	private boolean isVecInYZ(Vec3 p_72333_1_)
	{
		return p_72333_1_ == null ? false : p_72333_1_.yCoord >= minY && p_72333_1_.yCoord <= maxY && p_72333_1_.zCoord >= minZ && p_72333_1_.zCoord <= maxZ;
	}
	
	public AxisAlignedBB offset(double p_72317_1_, double p_72317_3_, double p_72317_5_)
	{
		minX += p_72317_1_;
		minY += p_72317_3_;
		minZ += p_72317_5_;
		maxX += p_72317_1_;
		maxY += p_72317_3_;
		maxZ += p_72317_5_;
		return this;
	}
	
	public void setBB(AxisAlignedBB p_72328_1_)
	{
		minX = p_72328_1_.minX;
		minY = p_72328_1_.minY;
		minZ = p_72328_1_.minZ;
		maxX = p_72328_1_.maxX;
		maxY = p_72328_1_.maxY;
		maxZ = p_72328_1_.maxZ;
	}
	
	public AxisAlignedBB setBounds(double p_72324_1_, double p_72324_3_, double p_72324_5_, double p_72324_7_, double p_72324_9_, double p_72324_11_)
	{
		minX = p_72324_1_;
		minY = p_72324_3_;
		minZ = p_72324_5_;
		maxX = p_72324_7_;
		maxY = p_72324_9_;
		maxZ = p_72324_11_;
		return this;
	}
	
	@Override public String toString()
	{
		return "box[" + minX + ", " + minY + ", " + minZ + " -> " + maxX + ", " + maxY + ", " + maxZ + "]";
	}
	
	public static AABBPool getAABBPool()
	{
		return (AABBPool) theAABBLocalPool.get();
	}
	
	public static AxisAlignedBB getBoundingBox(double p_72330_0_, double p_72330_2_, double p_72330_4_, double p_72330_6_, double p_72330_8_, double p_72330_10_)
	{
		return new AxisAlignedBB(p_72330_0_, p_72330_2_, p_72330_4_, p_72330_6_, p_72330_8_, p_72330_10_);
	}
}
