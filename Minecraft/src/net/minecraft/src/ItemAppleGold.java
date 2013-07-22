package net.minecraft.src;

import java.util.List;

public class ItemAppleGold extends ItemFood
{
	public ItemAppleGold(int p_i3655_1_, int p_i3655_2_, float p_i3655_3_, boolean p_i3655_4_)
	{
		super(p_i3655_1_, p_i3655_2_, p_i3655_3_, p_i3655_4_);
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
	
	@Override protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
	{
		if(p_77849_1_.getItemDamage() > 0)
		{
			if(!p_77849_2_.isRemote)
			{
				p_77849_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 3));
				p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
				p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
			}
		} else
		{
			super.onFoodEaten(p_77849_1_, p_77849_2_, p_77849_3_);
		}
	}
}
