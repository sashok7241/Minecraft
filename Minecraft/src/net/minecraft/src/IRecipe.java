package net.minecraft.src;

public interface IRecipe
{
	ItemStack getCraftingResult(InventoryCrafting var1);
	
	ItemStack getRecipeOutput();
	
	int getRecipeSize();
	
	boolean matches(InventoryCrafting var1, World var2);
}
