package net.minecraft.src;

public class MaterialTransparent extends Material
{
	public MaterialTransparent(MapColor p_i3879_1_)
	{
		super(p_i3879_1_);
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
