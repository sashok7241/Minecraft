package net.minecraft.src;

public class BlockButtonStone extends BlockButton
{
	protected BlockButtonStone(int p_i9015_1_)
	{
		super(p_i9015_1_, false);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.stone.getBlockTextureFromSide(1);
	}
}
