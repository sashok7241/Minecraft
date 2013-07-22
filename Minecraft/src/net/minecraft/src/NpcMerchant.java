package net.minecraft.src;

public class NpcMerchant implements IMerchant
{
	private InventoryMerchant theMerchantInventory;
	private EntityPlayer customer;
	private MerchantRecipeList recipeList;
	
	public NpcMerchant(EntityPlayer par1EntityPlayer)
	{
		customer = par1EntityPlayer;
		theMerchantInventory = new InventoryMerchant(par1EntityPlayer, this);
	}
	
	@Override public void func_110297_a_(ItemStack par1ItemStack)
	{
	}
	
	@Override public EntityPlayer getCustomer()
	{
		return customer;
	}
	
	@Override public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
	{
		return recipeList;
	}
	
	@Override public void setCustomer(EntityPlayer par1EntityPlayer)
	{
	}
	
	@Override public void setRecipes(MerchantRecipeList par1MerchantRecipeList)
	{
		recipeList = par1MerchantRecipeList;
	}
	
	@Override public void useRecipe(MerchantRecipe par1MerchantRecipe)
	{
	}
}
