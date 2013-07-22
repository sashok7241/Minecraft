package net.minecraft.src;

public class RecipesIngots
{
	private Object[][] recipeItems;
	
	public RecipesIngots()
	{
		recipeItems = new Object[][] { { Block.blockGold, new ItemStack(Item.ingotGold, 9) }, { Block.blockIron, new ItemStack(Item.ingotIron, 9) }, { Block.blockDiamond, new ItemStack(Item.diamond, 9) }, { Block.blockEmerald, new ItemStack(Item.emerald, 9) }, { Block.blockLapis, new ItemStack(Item.dyePowder, 9, 4) }, { Block.blockRedstone, new ItemStack(Item.redstone, 9) } };
	}
	
	public void addRecipes(CraftingManager p_77590_1_)
	{
		for(Object[] recipeItem : recipeItems)
		{
			Block var3 = (Block) recipeItem[0];
			ItemStack var4 = (ItemStack) recipeItem[1];
			p_77590_1_.addRecipe(new ItemStack(var3), new Object[] { "###", "###", "###", '#', var4 });
			p_77590_1_.addRecipe(var4, new Object[] { "#", '#', var3 });
		}
		p_77590_1_.addRecipe(new ItemStack(Item.ingotGold), new Object[] { "###", "###", "###", '#', Item.goldNugget });
		p_77590_1_.addRecipe(new ItemStack(Item.goldNugget, 9), new Object[] { "#", '#', Item.ingotGold });
	}
}
