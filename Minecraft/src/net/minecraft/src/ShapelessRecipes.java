package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipes implements IRecipe
{
	private final ItemStack recipeOutput;
	private final List recipeItems;
	
	public ShapelessRecipes(ItemStack par1ItemStack, List par2List)
	{
		recipeOutput = par1ItemStack;
		recipeItems = par2List;
	}
	
	@Override public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
	{
		return recipeOutput.copy();
	}
	
	@Override public ItemStack getRecipeOutput()
	{
		return recipeOutput;
	}
	
	@Override public int getRecipeSize()
	{
		return recipeItems.size();
	}
	
	@Override public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
	{
		ArrayList var3 = new ArrayList(recipeItems);
		for(int var4 = 0; var4 < 3; ++var4)
		{
			for(int var5 = 0; var5 < 3; ++var5)
			{
				ItemStack var6 = par1InventoryCrafting.getStackInRowAndColumn(var5, var4);
				if(var6 != null)
				{
					boolean var7 = false;
					Iterator var8 = var3.iterator();
					while(var8.hasNext())
					{
						ItemStack var9 = (ItemStack) var8.next();
						if(var6.itemID == var9.itemID && (var9.getItemDamage() == 32767 || var6.getItemDamage() == var9.getItemDamage()))
						{
							var7 = true;
							var3.remove(var9);
							break;
						}
					}
					if(!var7) return false;
				}
			}
		}
		return var3.isEmpty();
	}
}
