package net.minecraft.src;

public abstract class BlockDirectional extends Block
{
	protected BlockDirectional(int p_i3936_1_, Material p_i3936_2_)
	{
		super(p_i3936_1_, p_i3936_2_);
	}
	
	public static int getDirection(int p_72217_0_)
	{
		return p_72217_0_ & 3;
	}
}
