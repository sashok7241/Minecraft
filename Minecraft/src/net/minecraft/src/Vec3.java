package net.minecraft.src;

public class Vec3
{
	public static final Vec3Pool fakePool = new Vec3Pool(-1, -1);
	public final Vec3Pool myVec3LocalPool;
	public double xCoord;
	public double yCoord;
	public double zCoord;
	
	protected Vec3(Vec3Pool par1Vec3Pool, double par2, double par4, double par6)
	{
		if(par2 == -0.0D)
		{
			par2 = 0.0D;
		}
		if(par4 == -0.0D)
		{
			par4 = 0.0D;
		}
		if(par6 == -0.0D)
		{
			par6 = 0.0D;
		}
		xCoord = par2;
		yCoord = par4;
		zCoord = par6;
		myVec3LocalPool = par1Vec3Pool;
	}
	
	public Vec3 addVector(double par1, double par3, double par5)
	{
		return myVec3LocalPool.getVecFromPool(xCoord + par1, yCoord + par3, zCoord + par5);
	}
	
	public Vec3 crossProduct(Vec3 par1Vec3)
	{
		return myVec3LocalPool.getVecFromPool(yCoord * par1Vec3.zCoord - zCoord * par1Vec3.yCoord, zCoord * par1Vec3.xCoord - xCoord * par1Vec3.zCoord, xCoord * par1Vec3.yCoord - yCoord * par1Vec3.xCoord);
	}
	
	public double distanceTo(Vec3 par1Vec3)
	{
		double var2 = par1Vec3.xCoord - xCoord;
		double var4 = par1Vec3.yCoord - yCoord;
		double var6 = par1Vec3.zCoord - zCoord;
		return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
	}
	
	public double dotProduct(Vec3 par1Vec3)
	{
		return xCoord * par1Vec3.xCoord + yCoord * par1Vec3.yCoord + zCoord * par1Vec3.zCoord;
	}
	
	public Vec3 getIntermediateWithXValue(Vec3 par1Vec3, double par2)
	{
		double var4 = par1Vec3.xCoord - xCoord;
		double var6 = par1Vec3.yCoord - yCoord;
		double var8 = par1Vec3.zCoord - zCoord;
		if(var4 * var4 < 1.0000000116860974E-7D) return null;
		else
		{
			double var10 = (par2 - xCoord) / var4;
			return var10 >= 0.0D && var10 <= 1.0D ? myVec3LocalPool.getVecFromPool(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}
	
	public Vec3 getIntermediateWithYValue(Vec3 par1Vec3, double par2)
	{
		double var4 = par1Vec3.xCoord - xCoord;
		double var6 = par1Vec3.yCoord - yCoord;
		double var8 = par1Vec3.zCoord - zCoord;
		if(var6 * var6 < 1.0000000116860974E-7D) return null;
		else
		{
			double var10 = (par2 - yCoord) / var6;
			return var10 >= 0.0D && var10 <= 1.0D ? myVec3LocalPool.getVecFromPool(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}
	
	public Vec3 getIntermediateWithZValue(Vec3 par1Vec3, double par2)
	{
		double var4 = par1Vec3.xCoord - xCoord;
		double var6 = par1Vec3.yCoord - yCoord;
		double var8 = par1Vec3.zCoord - zCoord;
		if(var8 * var8 < 1.0000000116860974E-7D) return null;
		else
		{
			double var10 = (par2 - zCoord) / var8;
			return var10 >= 0.0D && var10 <= 1.0D ? myVec3LocalPool.getVecFromPool(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}
	
	public double lengthVector()
	{
		return MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
	}
	
	public Vec3 normalize()
	{
		double var1 = MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
		return var1 < 1.0E-4D ? myVec3LocalPool.getVecFromPool(0.0D, 0.0D, 0.0D) : myVec3LocalPool.getVecFromPool(xCoord / var1, yCoord / var1, zCoord / var1);
	}
	
	public void rotateAroundX(float par1)
	{
		float var2 = MathHelper.cos(par1);
		float var3 = MathHelper.sin(par1);
		double var4 = xCoord;
		double var6 = yCoord * var2 + zCoord * var3;
		double var8 = zCoord * var2 - yCoord * var3;
		xCoord = var4;
		yCoord = var6;
		zCoord = var8;
	}
	
	public void rotateAroundY(float par1)
	{
		float var2 = MathHelper.cos(par1);
		float var3 = MathHelper.sin(par1);
		double var4 = xCoord * var2 + zCoord * var3;
		double var6 = yCoord;
		double var8 = zCoord * var2 - xCoord * var3;
		xCoord = var4;
		yCoord = var6;
		zCoord = var8;
	}
	
	public void rotateAroundZ(float par1)
	{
		float var2 = MathHelper.cos(par1);
		float var3 = MathHelper.sin(par1);
		double var4 = xCoord * var2 + yCoord * var3;
		double var6 = yCoord * var2 - xCoord * var3;
		double var8 = zCoord;
		xCoord = var4;
		yCoord = var6;
		zCoord = var8;
	}
	
	protected Vec3 setComponents(double par1, double par3, double par5)
	{
		xCoord = par1;
		yCoord = par3;
		zCoord = par5;
		return this;
	}
	
	public double squareDistanceTo(double par1, double par3, double par5)
	{
		double var7 = par1 - xCoord;
		double var9 = par3 - yCoord;
		double var11 = par5 - zCoord;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}
	
	public double squareDistanceTo(Vec3 par1Vec3)
	{
		double var2 = par1Vec3.xCoord - xCoord;
		double var4 = par1Vec3.yCoord - yCoord;
		double var6 = par1Vec3.zCoord - zCoord;
		return var2 * var2 + var4 * var4 + var6 * var6;
	}
	
	public Vec3 subtract(Vec3 par1Vec3)
	{
		return myVec3LocalPool.getVecFromPool(par1Vec3.xCoord - xCoord, par1Vec3.yCoord - yCoord, par1Vec3.zCoord - zCoord);
	}
	
	@Override public String toString()
	{
		return "(" + xCoord + ", " + yCoord + ", " + zCoord + ")";
	}
	
	public static Vec3 createVectorHelper(double par0, double par2, double par4)
	{
		return new Vec3(fakePool, par0, par2, par4);
	}
}
