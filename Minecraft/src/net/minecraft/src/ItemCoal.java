package net.minecraft.src;

import java.util.List;

public class ItemCoal extends Item
{
	public ItemCoal(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
	}
	
	@Override public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return par1ItemStack.getItemDamage() == 1 ? "item.charcoal" : "item.coal";
	}
}
