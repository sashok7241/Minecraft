package net.minecraft.src;

public class MaterialLogic extends Material
{
	public MaterialLogic(MapColor p_i3878_1_)
	{
		super(p_i3878_1_);
		setAdventureModeExempt();
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
