package net.minecraft.src;

public interface IMerchant
{
	void func_110297_a_(ItemStack var1);
	
	EntityPlayer getCustomer();
	
	MerchantRecipeList getRecipes(EntityPlayer var1);
	
	void setCustomer(EntityPlayer var1);
	
	void setRecipes(MerchantRecipeList var1);
	
	void useRecipe(MerchantRecipe var1);
}
