package net.minecraft.src;

public class RecipesMapCloning implements IRecipe
{
	@Override public ItemStack getCraftingResult(InventoryCrafting p_77572_1_)
	{
		int var2 = 0;
		ItemStack var3 = null;
		for(int var4 = 0; var4 < p_77572_1_.getSizeInventory(); ++var4)
		{
			ItemStack var5 = p_77572_1_.getStackInSlot(var4);
			if(var5 != null)
			{
				if(var5.itemID == Item.map.itemID)
				{
					if(var3 != null) return null;
					var3 = var5;
				} else
				{
					if(var5.itemID != Item.emptyMap.itemID) return null;
					++var2;
				}
			}
		}
		if(var3 != null && var2 >= 1)
		{
			ItemStack var6 = new ItemStack(Item.map, var2 + 1, var3.getItemDamage());
			if(var3.hasDisplayName())
			{
				var6.setItemName(var3.getDisplayName());
			}
			return var6;
		} else return null;
	}
	
	@Override public ItemStack getRecipeOutput()
	{
		return null;
	}
	
	@Override public int getRecipeSize()
	{
		return 9;
	}
	
	@Override public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_)
	{
		int var3 = 0;
		ItemStack var4 = null;
		for(int var5 = 0; var5 < p_77569_1_.getSizeInventory(); ++var5)
		{
			ItemStack var6 = p_77569_1_.getStackInSlot(var5);
			if(var6 != null)
			{
				if(var6.itemID == Item.map.itemID)
				{
					if(var4 != null) return false;
					var4 = var6;
				} else
				{
					if(var6.itemID != Item.emptyMap.itemID) return false;
					++var3;
				}
			}
		}
		return var4 != null && var3 > 0;
	}
}
