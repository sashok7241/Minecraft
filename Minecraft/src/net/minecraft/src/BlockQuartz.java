package net.minecraft.src;

import java.util.List;

public class BlockQuartz extends Block
{
	public static final String[] quartzBlockTypes = new String[] { "default", "chiseled", "lines" };
	private static final String[] quartzBlockTextureTypes = new String[] { "quartzblock_side", "quartzblock_chiseled", "quartzblock_lines", null, null };
	private Icon[] quartzblockIcons;
	private Icon quartzblock_chiseled_top;
	private Icon quartzblock_lines_top;
	private Icon quartzblock_top;
	private Icon quartzblock_bottom;
	
	public BlockQuartz(int p_i9082_1_)
	{
		super(p_i9082_1_, Material.rock);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return p_71880_1_ != 3 && p_71880_1_ != 4 ? super.createStackedBlock(p_71880_1_) : new ItemStack(blockID, 1, 2);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_ != 3 && p_71899_1_ != 4 ? p_71899_1_ : 2;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 != 2 && par2 != 3 && par2 != 4)
		{
			if(par1 != 1 && (par1 != 0 || par2 != 1))
			{
				if(par1 == 0) return quartzblock_bottom;
				else
				{
					if(par2 < 0 || par2 >= quartzblockIcons.length)
					{
						par2 = 0;
					}
					return quartzblockIcons[par2];
				}
			} else return par2 == 1 ? quartzblock_chiseled_top : quartzblock_top;
		} else return par2 == 2 && (par1 == 1 || par1 == 0) ? quartzblock_lines_top : par2 == 3 && (par1 == 5 || par1 == 4) ? quartzblock_lines_top : par2 == 4 && (par1 == 2 || par1 == 3) ? quartzblock_lines_top : quartzblockIcons[par2];
	}
	
	@Override public int getRenderType()
	{
		return 39;
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		if(p_85104_9_ == 2)
		{
			switch(p_85104_5_)
			{
				case 0:
				case 1:
					p_85104_9_ = 2;
					break;
				case 2:
				case 3:
					p_85104_9_ = 4;
					break;
				case 4:
				case 5:
					p_85104_9_ = 3;
			}
		}
		return p_85104_9_;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		quartzblockIcons = new Icon[quartzBlockTextureTypes.length];
		for(int var2 = 0; var2 < quartzblockIcons.length; ++var2)
		{
			if(quartzBlockTextureTypes[var2] == null)
			{
				quartzblockIcons[var2] = quartzblockIcons[var2 - 1];
			} else
			{
				quartzblockIcons[var2] = par1IconRegister.registerIcon(quartzBlockTextureTypes[var2]);
			}
		}
		quartzblock_top = par1IconRegister.registerIcon("quartzblock_top");
		quartzblock_chiseled_top = par1IconRegister.registerIcon("quartzblock_chiseled_top");
		quartzblock_lines_top = par1IconRegister.registerIcon("quartzblock_lines_top");
		quartzblock_bottom = par1IconRegister.registerIcon("quartzblock_bottom");
	}
}
