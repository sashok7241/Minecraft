package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessRecipes implements IRecipe
{
	private final ItemStack recipeOutput;
	private final List recipeItems;
	
	public ShapelessRecipes(ItemStack p_i3701_1_, List p_i3701_2_)
	{
		recipeOutput = p_i3701_1_;
		recipeItems = p_i3701_2_;
	}
	
	@Override public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
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
	
	@Override public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_)
	{
		ArrayList var3 = new ArrayList(recipeItems);
		for(int var4 = 0; var4 < 3; ++var4)
		{
			for(int var5 = 0; var5 < 3; ++var5)
			{
				ItemStack var6 = p_77569_1_.getStackInRowAndColumn(var5, var4);
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
