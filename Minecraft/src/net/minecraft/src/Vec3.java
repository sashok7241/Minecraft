package net.minecraft.src;

public class Vec3
{
	public static final Vec3Pool fakePool = new Vec3Pool(-1, -1);
	public final Vec3Pool myVec3LocalPool;
	public double xCoord;
	public double yCoord;
	public double zCoord;
	
	protected Vec3(Vec3Pool p_i5109_1_, double p_i5109_2_, double p_i5109_4_, double p_i5109_6_)
	{
		if(p_i5109_2_ == -0.0D)
		{
			p_i5109_2_ = 0.0D;
		}
		if(p_i5109_4_ == -0.0D)
		{
			p_i5109_4_ = 0.0D;
		}
		if(p_i5109_6_ == -0.0D)
		{
			p_i5109_6_ = 0.0D;
		}
		xCoord = p_i5109_2_;
		yCoord = p_i5109_4_;
		zCoord = p_i5109_6_;
		myVec3LocalPool = p_i5109_1_;
	}
	
	public Vec3 addVector(double p_72441_1_, double p_72441_3_, double p_72441_5_)
	{
		return myVec3LocalPool.getVecFromPool(xCoord + p_72441_1_, yCoord + p_72441_3_, zCoord + p_72441_5_);
	}
	
	public Vec3 crossProduct(Vec3 par1Vec3)
	{
		return myVec3LocalPool.getVecFromPool(yCoord * par1Vec3.zCoord - zCoord * par1Vec3.yCoord, zCoord * par1Vec3.xCoord - xCoord * par1Vec3.zCoord, xCoord * par1Vec3.yCoord - yCoord * par1Vec3.xCoord);
	}
	
	public double distanceTo(Vec3 p_72438_1_)
	{
		double var2 = p_72438_1_.xCoord - xCoord;
		double var4 = p_72438_1_.yCoord - yCoord;
		double var6 = p_72438_1_.zCoord - zCoord;
		return MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
	}
	
	public double dotProduct(Vec3 p_72430_1_)
	{
		return xCoord * p_72430_1_.xCoord + yCoord * p_72430_1_.yCoord + zCoord * p_72430_1_.zCoord;
	}
	
	public Vec3 getIntermediateWithXValue(Vec3 p_72429_1_, double p_72429_2_)
	{
		double var4 = p_72429_1_.xCoord - xCoord;
		double var6 = p_72429_1_.yCoord - yCoord;
		double var8 = p_72429_1_.zCoord - zCoord;
		if(var4 * var4 < 1.0000000116860974E-7D) return null;
		else
		{
			double var10 = (p_72429_2_ - xCoord) / var4;
			return var10 >= 0.0D && var10 <= 1.0D ? myVec3LocalPool.getVecFromPool(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}
	
	public Vec3 getIntermediateWithYValue(Vec3 p_72435_1_, double p_72435_2_)
	{
		double var4 = p_72435_1_.xCoord - xCoord;
		double var6 = p_72435_1_.yCoord - yCoord;
		double var8 = p_72435_1_.zCoord - zCoord;
		if(var6 * var6 < 1.0000000116860974E-7D) return null;
		else
		{
			double var10 = (p_72435_2_ - yCoord) / var6;
			return var10 >= 0.0D && var10 <= 1.0D ? myVec3LocalPool.getVecFromPool(xCoord + var4 * var10, yCoord + var6 * var10, zCoord + var8 * var10) : null;
		}
	}
	
	public Vec3 getIntermediateWithZValue(Vec3 p_72434_1_, double p_72434_2_)
	{
		double var4 = p_72434_1_.xCoord - xCoord;
		double var6 = p_72434_1_.yCoord - yCoord;
		double var8 = p_72434_1_.zCoord - zCoord;
		if(var8 * var8 < 1.0000000116860974E-7D) return null;
		else
		{
			double var10 = (p_72434_2_ - zCoord) / var8;
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
	
	public void rotateAroundX(float p_72440_1_)
	{
		float var2 = MathHelper.cos(p_72440_1_);
		float var3 = MathHelper.sin(p_72440_1_);
		double var4 = xCoord;
		double var6 = yCoord * var2 + zCoord * var3;
		double var8 = zCoord * var2 - yCoord * var3;
		xCoord = var4;
		yCoord = var6;
		zCoord = var8;
	}
	
	public void rotateAroundY(float p_72442_1_)
	{
		float var2 = MathHelper.cos(p_72442_1_);
		float var3 = MathHelper.sin(p_72442_1_);
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
	
	protected Vec3 setComponents(double p_72439_1_, double p_72439_3_, double p_72439_5_)
	{
		xCoord = p_72439_1_;
		yCoord = p_72439_3_;
		zCoord = p_72439_5_;
		return this;
	}
	
	public double squareDistanceTo(double p_72445_1_, double p_72445_3_, double p_72445_5_)
	{
		double var7 = p_72445_1_ - xCoord;
		double var9 = p_72445_3_ - yCoord;
		double var11 = p_72445_5_ - zCoord;
		return var7 * var7 + var9 * var9 + var11 * var11;
	}
	
	public double squareDistanceTo(Vec3 p_72436_1_)
	{
		double var2 = p_72436_1_.xCoord - xCoord;
		double var4 = p_72436_1_.yCoord - yCoord;
		double var6 = p_72436_1_.zCoord - zCoord;
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
	
	public static Vec3 createVectorHelper(double p_72443_0_, double p_72443_2_, double p_72443_4_)
	{
		return new Vec3(fakePool, p_72443_0_, p_72443_2_, p_72443_4_);
	}
}
