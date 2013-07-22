package net.minecraft.src;

import java.util.List;

public class BlockStoneBrick extends Block
{
	public static final String[] STONE_BRICK_TYPES = new String[] { "default", "mossy", "cracked", "chiseled" };
	public static final String[] field_94407_b = new String[] { null, "mossy", "cracked", "carved" };
	private Icon[] field_94408_c;
	
	public BlockStoneBrick(int par1)
	{
		super(par1, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 < 0 || par2 >= field_94407_b.length)
		{
			par2 = 0;
		}
		return field_94408_c[par2];
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 0; var4 < 4; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94408_c = new Icon[field_94407_b.length];
		for(int var2 = 0; var2 < field_94408_c.length; ++var2)
		{
			String var3 = func_111023_E();
			if(field_94407_b[var2] != null)
			{
				var3 = var3 + "_" + field_94407_b[var2];
			}
			field_94408_c[var2] = par1IconRegister.registerIcon(var3);
		}
	}
}
