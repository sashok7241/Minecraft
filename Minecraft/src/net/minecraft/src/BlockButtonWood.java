package net.minecraft.src;

public class BlockButtonWood extends BlockButton
{
	protected BlockButtonWood(int p_i9017_1_)
	{
		super(p_i9017_1_, true);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.planks.getBlockTextureFromSide(1);
	}
}
