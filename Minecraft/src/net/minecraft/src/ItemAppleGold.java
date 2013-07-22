package net.minecraft.src;

import java.util.List;

public class ItemAppleGold extends ItemFood
{
	public ItemAppleGold(int par1, int par2, float par3, boolean par4)
	{
		super(par1, par2, par3, par4);
		setHasSubtypes(true);
	}
	
	@Override public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItemDamage() == 0 ? EnumRarity.rare : EnumRarity.epic;
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}
	
	@Override public boolean hasEffect(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItemDamage() > 0;
	}
	
	@Override protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(!par2World.isRemote)
		{
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2400, 0));
		}
		if(par1ItemStack.getItemDamage() > 0)
		{
			if(!par2World.isRemote)
			{
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
				par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
			}
		} else
		{
			super.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		}
	}
}
