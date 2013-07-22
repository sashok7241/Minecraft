package net.minecraft.src;

public class ItemAxe extends ItemTool
{
	private static Block[] blocksEffectiveAgainst = new Block[] { Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.pumpkin, Block.pumpkinLantern };
	
	protected ItemAxe(int p_i3656_1_, EnumToolMaterial p_i3656_2_)
	{
		super(p_i3656_1_, 3, p_i3656_2_, blocksEffectiveAgainst);
	}
	
	@Override public float getStrVsBlock(ItemStack p_77638_1_, Block p_77638_2_)
	{
		return p_77638_2_ != null && (p_77638_2_.blockMaterial == Material.wood || p_77638_2_.blockMaterial == Material.plants || p_77638_2_.blockMaterial == Material.vine) ? efficiencyOnProperMaterial : super.getStrVsBlock(p_77638_1_, p_77638_2_);
	}
}
