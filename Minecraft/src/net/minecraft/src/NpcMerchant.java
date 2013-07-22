package net.minecraft.src;

public class NpcMerchant implements IMerchant
{
	private InventoryMerchant theMerchantInventory;
	private EntityPlayer customer;
	private MerchantRecipeList recipeList;
	
	public NpcMerchant(EntityPlayer p_i3559_1_)
	{
		customer = p_i3559_1_;
		theMerchantInventory = new InventoryMerchant(p_i3559_1_, this);
	}
	
	@Override public EntityPlayer getCustomer()
	{
		return customer;
	}
	
	@Override public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_)
	{
		return recipeList;
	}
	
	@Override public void setCustomer(EntityPlayer p_70932_1_)
	{
	}
	
	@Override public void setRecipes(MerchantRecipeList par1MerchantRecipeList)
	{
		recipeList = par1MerchantRecipeList;
	}
	
	@Override public void useRecipe(MerchantRecipe p_70933_1_)
	{
	}
}
