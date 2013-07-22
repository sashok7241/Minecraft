package net.minecraft.src;

import java.util.Comparator;

class RecipeSorter implements Comparator
{
	final CraftingManager craftingManager;
	
	RecipeSorter(CraftingManager par1CraftingManager)
	{
		craftingManager = par1CraftingManager;
	}
	
	@Override public int compare(Object par1Obj, Object par2Obj)
	{
		return compareRecipes((IRecipe) par1Obj, (IRecipe) par2Obj);
	}
	
	public int compareRecipes(IRecipe par1IRecipe, IRecipe par2IRecipe)
	{
		return par1IRecipe instanceof ShapelessRecipes && par2IRecipe instanceof ShapedRecipes ? 1 : par2IRecipe instanceof ShapelessRecipes && par1IRecipe instanceof ShapedRecipes ? -1 : par2IRecipe.getRecipeSize() < par1IRecipe.getRecipeSize() ? -1 : par2IRecipe.getRecipeSize() > par1IRecipe.getRecipeSize() ? 1 : 0;
	}
}
