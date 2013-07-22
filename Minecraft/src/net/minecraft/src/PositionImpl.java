package net.minecraft.src;

public class PositionImpl implements IPosition
{
	protected final double x;
	protected final double y;
	protected final double z;
	
	public PositionImpl(double par1, double par3, double par5)
	{
		x = par1;
		y = par3;
		z = par5;
	}
	
	@Override public double getX()
	{
		return x;
	}
	
	@Override public double getY()
	{
		return y;
	}
	
	@Override public double getZ()
	{
		return z;
	}
}
