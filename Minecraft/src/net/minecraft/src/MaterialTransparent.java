package net.minecraft.src;

public class MaterialTransparent extends Material
{
	public MaterialTransparent(MapColor par1MapColor)
	{
		super(par1MapColor);
		setReplaceable();
	}
	
	@Override public boolean blocksMovement()
	{
		return false;
	}
	
	@Override public boolean getCanBlockGrass()
	{
		return false;
	}
	
	@Override public boolean isSolid()
	{
		return false;
	}
}
