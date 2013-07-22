package net.minecraft.src;

public class PositionImpl implements IPosition
{
	protected final double x;
	protected final double y;
	protected final double z;
	
	public PositionImpl(double p_i5028_1_, double p_i5028_3_, double p_i5028_5_)
	{
		x = p_i5028_1_;
		y = p_i5028_3_;
		z = p_i5028_5_;
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
