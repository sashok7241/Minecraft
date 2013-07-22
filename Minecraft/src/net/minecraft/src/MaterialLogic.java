package net.minecraft.src;

public class MaterialLogic extends Material
{
	public MaterialLogic(MapColor par1MapColor)
	{
		super(par1MapColor);
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
