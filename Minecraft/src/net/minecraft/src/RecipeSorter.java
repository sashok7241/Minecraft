package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator
{
	final CraftingManager craftingManager;
	
	RecipeSorter(CraftingManager p_i3699_1_)
	{
		craftingManager = p_i3699_1_;
	}
	
	@Override public int compare(Object p_compare_1_, Object p_compare_2_)
	{
		return compareRecipes((IRecipe) p_compare_1_, (IRecipe) p_compare_2_);
	}
	
	public int compareRecipes(IRecipe p_77581_1_, IRecipe p_77581_2_)
	{
		return p_77581_1_ instanceof ShapelessRecipes && p_77581_2_ instanceof ShapedRecipes ? 1 : p_77581_2_ instanceof ShapelessRecipes && p_77581_1_ instanceof ShapedRecipes ? -1 : p_77581_2_.getRecipeSize() < p_77581_1_.getRecipeSize() ? -1 : p_77581_2_.getRecipeSize() > p_77581_1_.getRecipeSize() ? 1 : 0;
	}
}
