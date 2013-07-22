package net.minecraft.src;

import java.util.List;

public class ItemCoal extends Item
{
	private Icon field_111220_a;
	
	public ItemCoal(int par1)
	{
		super(par1);
		setHasSubtypes(true);
		setMaxDamage(0);
		setCreativeTab(CreativeTabs.tabMaterials);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return par1 == 1 ? field_111220_a : super.getIconFromDamage(par1);
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
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		field_111220_a = par1IconRegister.registerIcon("charcoal");
	}
}
