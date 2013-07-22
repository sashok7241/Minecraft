package net.minecraft.src;

public class ItemSpade extends ItemTool
{
	private static Block[] blocksEffectiveAgainst = new Block[] { Block.grass, Block.dirt, Block.sand, Block.gravel, Block.snow, Block.blockSnow, Block.blockClay, Block.tilledField, Block.slowSand, Block.mycelium };
	
	public ItemSpade(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, 1, par2EnumToolMaterial, blocksEffectiveAgainst);
	}
	
	@Override public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block == Block.snow ? true : par1Block == Block.blockSnow;
	}
}
