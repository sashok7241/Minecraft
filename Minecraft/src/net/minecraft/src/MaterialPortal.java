package net.minecraft.src;

public class MaterialPortal extends Material
{
	public MaterialPortal(MapColor p_i3884_1_)
	{
		super(p_i3884_1_);
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
