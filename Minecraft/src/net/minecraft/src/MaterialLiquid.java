package net.minecraft.src;

public class MaterialLiquid extends Material
{
	public MaterialLiquid(MapColor par1MapColor)
	{
		super(par1MapColor);
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
