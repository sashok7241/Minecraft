package net.minecraft.src;

public class ItemPickaxe extends ItemTool
{
	private static Block[] blocksEffectiveAgainst = new Block[] { Block.cobblestone, Block.stoneDoubleSlab, Block.stoneSingleSlab, Block.stone, Block.sandStone, Block.cobblestoneMossy, Block.oreIron, Block.blockIron, Block.oreCoal, Block.blockGold, Block.oreGold, Block.oreDiamond, Block.blockDiamond, Block.ice, Block.netherrack, Block.oreLapis, Block.blockLapis, Block.oreRedstone, Block.oreRedstoneGlowing, Block.rail, Block.railDetector, Block.railPowered, Block.railActivator };
	
	protected ItemPickaxe(int p_i3673_1_, EnumToolMaterial p_i3673_2_)
	{
		super(p_i3673_1_, 2, p_i3673_2_, blocksEffectiveAgainst);
	}
	
	@Override public boolean canHarvestBlock(Block p_77641_1_)
	{
		return p_77641_1_ == Block.obsidian ? toolMaterial.getHarvestLevel() == 3 : p_77641_1_ != Block.blockDiamond && p_77641_1_ != Block.oreDiamond ? p_77641_1_ != Block.oreEmerald && p_77641_1_ != Block.blockEmerald ? p_77641_1_ != Block.blockGold && p_77641_1_ != Block.oreGold ? p_77641_1_ != Block.blockIron && p_77641_1_ != Block.oreIron ? p_77641_1_ != Block.blockLapis && p_77641_1_ != Block.oreLapis ? p_77641_1_ != Block.oreRedstone && p_77641_1_ != Block.oreRedstoneGlowing ? p_77641_1_.blockMaterial == Material.rock ? true : p_77641_1_.blockMaterial == Material.iron ? true : p_77641_1_.blockMaterial == Material.anvil : toolMaterial.getHarvestLevel() >= 2 : toolMaterial.getHarvestLevel() >= 1 : toolMaterial.getHarvestLevel() >= 1 : toolMaterial.getHarvestLevel() >= 2 : toolMaterial.getHarvestLevel() >= 2 : toolMaterial.getHarvestLevel() >= 2;
	}
	
	@Override public float getStrVsBlock(ItemStack p_77638_1_, Block p_77638_2_)
	{
		return p_77638_2_ != null && (p_77638_2_.blockMaterial == Material.iron || p_77638_2_.blockMaterial == Material.anvil || p_77638_2_.blockMaterial == Material.rock) ? efficiencyOnProperMaterial : super.getStrVsBlock(p_77638_1_, p_77638_2_);
	}
}
