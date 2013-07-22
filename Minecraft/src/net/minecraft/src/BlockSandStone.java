package net.minecraft.src;

import java.util.List;

public class BlockSandStone extends Block
{
	public static final String[] SAND_STONE_TYPES = new String[] { "default", "chiseled", "smooth" };
	private static final String[] field_94405_b = new String[] { "sandstone_side", "sandstone_carved", "sandstone_smooth" };
	private Icon[] field_94406_c;
	private Icon field_94403_cO;
	private Icon field_94404_cP;
	
	public BlockSandStone(int p_i3990_1_)
	{
		super(p_i3990_1_, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par1 != 1 && (par1 != 0 || par2 != 1 && par2 != 2))
		{
			if(par1 == 0) return field_94404_cP;
			else
			{
				if(par2 < 0 || par2 >= field_94406_c.length)
				{
					par2 = 0;
				}
				return field_94406_c[par2];
			}
		} else return field_94403_cO;
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		field_94406_c = new Icon[field_94405_b.length];
		for(int var2 = 0; var2 < field_94406_c.length; ++var2)
		{
			field_94406_c[var2] = par1IconRegister.registerIcon(field_94405_b[var2]);
		}
		field_94403_cO = par1IconRegister.registerIcon("sandstone_top");
		field_94404_cP = par1IconRegister.registerIcon("sandstone_bottom");
	}
}
