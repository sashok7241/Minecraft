package net.minecraft.src;

public class ItemAxe extends ItemTool
{
	private static Block[] blocksEffectiveAgainst = new Block[] { Block.planks, Block.bookShelf, Block.wood, Block.chest, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.pumpkin, Block.pumpkinLantern };
	
	protected ItemAxe(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, 3.0F, par2EnumToolMaterial, blocksEffectiveAgainst);
	}
	
	@Override public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block != null && (par2Block.blockMaterial == Material.wood || par2Block.blockMaterial == Material.plants || par2Block.blockMaterial == Material.vine) ? efficiencyOnProperMaterial : super.getStrVsBlock(par1ItemStack, par2Block);
	}
}
