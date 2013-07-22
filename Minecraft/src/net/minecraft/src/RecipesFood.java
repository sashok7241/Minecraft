package net.minecraft.src;

public class RecipesFood
{
	public void addRecipes(CraftingManager p_77608_1_)
	{
		p_77608_1_.addShapelessRecipe(new ItemStack(Item.bowlSoup), new Object[] { Block.mushroomBrown, Block.mushroomRed, Item.bowlEmpty });
		p_77608_1_.addRecipe(new ItemStack(Item.cookie, 8), new Object[] { "#X#", 'X', new ItemStack(Item.dyePowder, 1, 3), '#', Item.wheat });
		p_77608_1_.addRecipe(new ItemStack(Block.melon), new Object[] { "MMM", "MMM", "MMM", 'M', Item.melon });
		p_77608_1_.addRecipe(new ItemStack(Item.melonSeeds), new Object[] { "M", 'M', Item.melon });
		p_77608_1_.addRecipe(new ItemStack(Item.pumpkinSeeds, 4), new Object[] { "M", 'M', Block.pumpkin });
		p_77608_1_.addShapelessRecipe(new ItemStack(Item.pumpkinPie), new Object[] { Block.pumpkin, Item.sugar, Item.egg });
		p_77608_1_.addShapelessRecipe(new ItemStack(Item.fermentedSpiderEye), new Object[] { Item.spiderEye, Block.mushroomBrown, Item.sugar });
		p_77608_1_.addShapelessRecipe(new ItemStack(Item.speckledMelon), new Object[] { Item.melon, Item.goldNugget });
		p_77608_1_.addShapelessRecipe(new ItemStack(Item.blazePowder, 2), new Object[] { Item.blazeRod });
		p_77608_1_.addShapelessRecipe(new ItemStack(Item.magmaCream), new Object[] { Item.blazePowder, Item.slimeBall });
	}
}
