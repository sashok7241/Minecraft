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
	
	protected AxisAlignedBB(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		minX = par1;
		minY = par3;
		minZ = par5;
		maxX = par7;
		maxY = par9;
		maxZ = par11;
	}
	
	public AxisAlignedBB addCoord(double par1, double par3, double par5)
	{
		double var7 = minX;
		double var9 = minY;
		double var11 = minZ;
		double var13 = maxX;
		double var15 = maxY;
		double var17 = maxZ;
		if(par1 < 0.0D)
		{
			var7 += par1;
		}
		if(par1 > 0.0D)
		{
			var13 += par1;
		}
		if(par3 < 0.0D)
		{
			var9 += par3;
		}
		if(par3 > 0.0D)
		{
			var15 += par3;
		}
		if(par5 < 0.0D)
		{
			var11 += par5;
		}
		if(par5 > 0.0D)
		{
			var17 += par5;
		}
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}
	
	public MovingObjectPosition calculateIntercept(Vec3 par1Vec3, Vec3 par2Vec3)
	{
		Vec3 var3 = par1Vec3.getIntermediateWithXValue(par2Vec3, minX);
		Vec3 var4 = par1Vec3.getIntermediateWithXValue(par2Vec3, maxX);
		Vec3 var5 = par1Vec3.getIntermediateWithYValue(par2Vec3, minY);
		Vec3 var6 = par1Vec3.getIntermediateWithYValue(par2Vec3, maxY);
		Vec3 var7 = par1Vec3.getIntermediateWithZValue(par2Vec3, minZ);
		Vec3 var8 = par1Vec3.getIntermediateWithZValue(par2Vec3, maxZ);
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
		if(var3 != null && (var9 == null || par1Vec3.squareDistanceTo(var3) < par1Vec3.squareDistanceTo(var9)))
		{
			var9 = var3;
		}
		if(var4 != null && (var9 == null || par1Vec3.squareDistanceTo(var4) < par1Vec3.squareDistanceTo(var9)))
		{
			var9 = var4;
		}
		if(var5 != null && (var9 == null || par1Vec3.squareDistanceTo(var5) < par1Vec3.squareDistanceTo(var9)))
		{
			var9 = var5;
		}
		if(var6 != null && (var9 == null || par1Vec3.squareDistanceTo(var6) < par1Vec3.squareDistanceTo(var9)))
		{
			var9 = var6;
		}
		if(var7 != null && (var9 == null || par1Vec3.squareDistanceTo(var7) < par1Vec3.squareDistanceTo(var9)))
		{
			var9 = var7;
		}
		if(var8 != null && (var9 == null || par1Vec3.squareDistanceTo(var8) < par1Vec3.squareDistanceTo(var9)))
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
	
	public double calculateXOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
	{
		if(par1AxisAlignedBB.maxY > minY && par1AxisAlignedBB.minY < maxY)
		{
			if(par1AxisAlignedBB.maxZ > minZ && par1AxisAlignedBB.minZ < maxZ)
			{
				double var4;
				if(par2 > 0.0D && par1AxisAlignedBB.maxX <= minX)
				{
					var4 = minX - par1AxisAlignedBB.maxX;
					if(var4 < par2)
					{
						par2 = var4;
					}
				}
				if(par2 < 0.0D && par1AxisAlignedBB.minX >= maxX)
				{
					var4 = maxX - par1AxisAlignedBB.minX;
					if(var4 > par2)
					{
						par2 = var4;
					}
				}
				return par2;
			} else return par2;
		} else return par2;
	}
	
	public double calculateYOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
	{
		if(par1AxisAlignedBB.maxX > minX && par1AxisAlignedBB.minX < maxX)
		{
			if(par1AxisAlignedBB.maxZ > minZ && par1AxisAlignedBB.minZ < maxZ)
			{
				double var4;
				if(par2 > 0.0D && par1AxisAlignedBB.maxY <= minY)
				{
					var4 = minY - par1AxisAlignedBB.maxY;
					if(var4 < par2)
					{
						par2 = var4;
					}
				}
				if(par2 < 0.0D && par1AxisAlignedBB.minY >= maxY)
				{
					var4 = maxY - par1AxisAlignedBB.minY;
					if(var4 > par2)
					{
						par2 = var4;
					}
				}
				return par2;
			} else return par2;
		} else return par2;
	}
	
	public double calculateZOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
	{
		if(par1AxisAlignedBB.maxX > minX && par1AxisAlignedBB.minX < maxX)
		{
			if(par1AxisAlignedBB.maxY > minY && par1AxisAlignedBB.minY < maxY)
			{
				double var4;
				if(par2 > 0.0D && par1AxisAlignedBB.maxZ <= minZ)
				{
					var4 = minZ - par1AxisAlignedBB.maxZ;
					if(var4 < par2)
					{
						par2 = var4;
					}
				}
				if(par2 < 0.0D && par1AxisAlignedBB.minZ >= maxZ)
				{
					var4 = maxZ - par1AxisAlignedBB.minZ;
					if(var4 > par2)
					{
						par2 = var4;
					}
				}
				return par2;
			} else return par2;
		} else return par2;
	}
	
	public AxisAlignedBB contract(double par1, double par3, double par5)
	{
		double var7 = minX + par1;
		double var9 = minY + par3;
		double var11 = minZ + par5;
		double var13 = maxX - par1;
		double var15 = maxY - par3;
		double var17 = maxZ - par5;
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}
	
	public AxisAlignedBB copy()
	{
		return getAABBPool().getAABB(minX, minY, minZ, maxX, maxY, maxZ);
	}
	
	public AxisAlignedBB expand(double par1, double par3, double par5)
	{
		double var7 = minX - par1;
		double var9 = minY - par3;
		double var11 = minZ - par5;
		double var13 = maxX + par1;
		double var15 = maxY + par3;
		double var17 = maxZ + par5;
		return getAABBPool().getAABB(var7, var9, var11, var13, var15, var17);
	}
	
	public AxisAlignedBB func_111270_a(AxisAlignedBB par1AxisAlignedBB)
	{
		double var2 = Math.min(minX, par1AxisAlignedBB.minX);
		double var4 = Math.min(minY, par1AxisAlignedBB.minY);
		double var6 = Math.min(minZ, par1AxisAlignedBB.minZ);
		double var8 = Math.max(maxX, par1AxisAlignedBB.maxX);
		double var10 = Math.max(maxY, par1AxisAlignedBB.maxY);
		double var12 = Math.max(maxZ, par1AxisAlignedBB.maxZ);
		return getAABBPool().getAABB(var2, var4, var6, var8, var10, var12);
	}
	
	public double getAverageEdgeLength()
	{
		double var1 = maxX - minX;
		double var3 = maxY - minY;
		double var5 = maxZ - minZ;
		return (var1 + var3 + var5) / 3.0D;
	}
	
	public AxisAlignedBB getOffsetBoundingBox(double par1, double par3, double par5)
	{
		return getAABBPool().getAABB(minX + par1, minY + par3, minZ + par5, maxX + par1, maxY + par3, maxZ + par5);
	}
	
	public boolean intersectsWith(AxisAlignedBB par1AxisAlignedBB)
	{
		return par1AxisAlignedBB.maxX > minX && par1AxisAlignedBB.minX < maxX ? par1AxisAlignedBB.maxY > minY && par1AxisAlignedBB.minY < maxY ? par1AxisAlignedBB.maxZ > minZ && par1AxisAlignedBB.minZ < maxZ : false : false;
	}
	
	public boolean isVecInside(Vec3 par1Vec3)
	{
		return par1Vec3.xCoord > minX && par1Vec3.xCoord < maxX ? par1Vec3.yCoord > minY && par1Vec3.yCoord < maxY ? par1Vec3.zCoord > minZ && par1Vec3.zCoord < maxZ : false : false;
	}
	
	private boolean isVecInXY(Vec3 par1Vec3)
	{
		return par1Vec3 == null ? false : par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY;
	}
	
	private boolean isVecInXZ(Vec3 par1Vec3)
	{
		return par1Vec3 == null ? false : par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
	}
	
	private boolean isVecInYZ(Vec3 par1Vec3)
	{
		return par1Vec3 == null ? false : par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
	}
	
	public AxisAlignedBB offset(double par1, double par3, double par5)
	{
		minX += par1;
		minY += par3;
		minZ += par5;
		maxX += par1;
		maxY += par3;
		maxZ += par5;
		return this;
	}
	
	public void setBB(AxisAlignedBB par1AxisAlignedBB)
	{
		minX = par1AxisAlignedBB.minX;
		minY = par1AxisAlignedBB.minY;
		minZ = par1AxisAlignedBB.minZ;
		maxX = par1AxisAlignedBB.maxX;
		maxY = par1AxisAlignedBB.maxY;
		maxZ = par1AxisAlignedBB.maxZ;
	}
	
	public AxisAlignedBB setBounds(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		minX = par1;
		minY = par3;
		minZ = par5;
		maxX = par7;
		maxY = par9;
		maxZ = par11;
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
	
	public static AxisAlignedBB getBoundingBox(double par0, double par2, double par4, double par6, double par8, double par10)
	{
		return new AxisAlignedBB(par0, par2, par4, par6, par8, par10);
	}
}
