package net.minecraft.src;

public class ItemSpade extends ItemTool
{
	private static Block[] blocksEffectiveAgainst = new Block[] { Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium };
	
	public ItemSpade(int p_i3684_1_, EnumToolMaterial p_i3684_2_)
	{
		super(p_i3684_1_, 1, p_i3684_2_, blocksEffectiveAgainst);
	}
	
	@Override public boolean canHarvestBlock(Block p_77641_1_)
	{
		return p_77641_1_ == Block.snow ? true : p_77641_1_ == Block.blockSnow;
	}
}
