package net.minecraft.src;

public class RecipesCrafting
{
	public void addRecipes(CraftingManager p_77589_1_)
	{
		p_77589_1_.addRecipe(new ItemStack(Block.chest), new Object[] { "###", "# #", "###", '#', Block.planks });
		p_77589_1_.addRecipe(new ItemStack(Block.chestTrapped), new Object[] { "#-", '#', Block.chest, '-', Block.tripWireSource });
		p_77589_1_.addRecipe(new ItemStack(Block.enderChest), new Object[] { "###", "#E#", "###", '#', Block.obsidian, 'E', Item.eyeOfEnder });
		p_77589_1_.addRecipe(new ItemStack(Block.furnaceIdle), new Object[] { "###", "# #", "###", '#', Block.cobblestone });
		p_77589_1_.addRecipe(new ItemStack(Block.workbench), new Object[] { "##", "##", '#', Block.planks });
		p_77589_1_.addRecipe(new ItemStack(Block.sandStone), new Object[] { "##", "##", '#', Block.sand });
		p_77589_1_.addRecipe(new ItemStack(Block.sandStone, 4, 2), new Object[] { "##", "##", '#', Block.sandStone });
		p_77589_1_.addRecipe(new ItemStack(Block.sandStone, 1, 1), new Object[] { "#", "#", '#', new ItemStack(Block.stoneSingleSlab, 1, 1) });
		p_77589_1_.addRecipe(new ItemStack(Block.blockNetherQuartz, 1, 1), new Object[] { "#", "#", '#', new ItemStack(Block.stoneSingleSlab, 1, 7) });
		p_77589_1_.addRecipe(new ItemStack(Block.blockNetherQuartz, 2, 2), new Object[] { "#", "#", '#', new ItemStack(Block.blockNetherQuartz, 1, 0) });
		p_77589_1_.addRecipe(new ItemStack(Block.stoneBrick, 4), new Object[] { "##", "##", '#', Block.stone });
		p_77589_1_.addRecipe(new ItemStack(Block.fenceIron, 16), new Object[] { "###", "###", '#', Item.ingotIron });
		p_77589_1_.addRecipe(new ItemStack(Block.thinGlass, 16), new Object[] { "###", "###", '#', Block.glass });
		p_77589_1_.addRecipe(new ItemStack(Block.redstoneLampIdle, 1), new Object[] { " R ", "RGR", " R ", 'R', Item.redstone, 'G', Block.glowStone });
		p_77589_1_.addRecipe(new ItemStack(Block.beacon, 1), new Object[] { "GGG", "GSG", "OOO", 'G', Block.glass, 'S', Item.netherStar, 'O', Block.obsidian });
		p_77589_1_.addRecipe(new ItemStack(Block.netherBrick, 1), new Object[] { "NN", "NN", 'N', Item.netherrackBrick });
	}
}
