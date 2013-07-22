package net.minecraft.src;

public class AnimationFrame
{
	private final int field_110499_a;
	private final int field_110498_b;
	
	public AnimationFrame(int par1)
	{
		this(par1, -1);
	}
	
	public AnimationFrame(int par1, int par2)
	{
		field_110499_a = par1;
		field_110498_b = par2;
	}
	
	public boolean func_110495_a()
	{
		return field_110498_b == -1;
	}
	
	public int func_110496_c()
	{
		return field_110499_a;
	}
	
	public int func_110497_b()
	{
		return field_110498_b;
	}
}
