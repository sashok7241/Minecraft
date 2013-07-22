package net.minecraft.src;

public class MaterialLiquid extends Material
{
	public MaterialLiquid(MapColor p_i3880_1_)
	{
		super(p_i3880_1_);
		setReplaceable();
		setNoPushMobility();
	}
	
	@Override public boolean blocksMovement()
	{
		return false;
	}
	
	@Override public boolean isLiquid()
	{
		return true;
	}
	
	@Override public boolean isSolid()
	{
		return false;
	}
}
