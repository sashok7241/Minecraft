package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockLog extends Block
{
	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };
	public static final String[] treeTextureTypes = new String[] { "tree_side", "tree_spruce", "tree_birch", "tree_jungle" };
	private Icon[] iconArray;
	private Icon tree_top;
	
	protected BlockLog(int p_i4016_1_)
	{
		super(p_i4016_1_, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		byte var7 = 4;
		int var8 = var7 + 1;
		if(p_71852_1_.checkChunksExist(p_71852_2_ - var8, p_71852_3_ - var8, p_71852_4_ - var8, p_71852_2_ + var8, p_71852_3_ + var8, p_71852_4_ + var8))
		{
			for(int var9 = -var7; var9 <= var7; ++var9)
			{
				for(int var10 = -var7; var10 <= var7; ++var10)
				{
					for(int var11 = -var7; var11 <= var7; ++var11)
					{
						int var12 = p_71852_1_.getBlockId(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11);
						if(var12 == Block.leaves.blockID)
						{
							int var13 = p_71852_1_.getBlockMetadata(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11);
							if((var13 & 8) == 0)
							{
								p_71852_1_.setBlockMetadataWithNotify(p_71852_2_ + var9, p_71852_3_ + var10, p_71852_4_ + var11, var13 | 8, 4);
							}
						}
					}
				}
			}
		}
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return new ItemStack(blockID, 1, limitToValidMetadata(p_71880_1_));
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_ & 3;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = par2 & 12;
		int var4 = par2 & 3;
		return var3 == 0 && (par1 == 1 || par1 == 0) ? tree_top : var3 == 4 && (par1 == 5 || par1 == 4) ? tree_top : var3 == 8 && (par1 == 2 || par1 == 3) ? tree_top : iconArray[var4];
	}
	
	@Override public int getRenderType()
	{
		return 31;
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.wood.blockID;
	}
	
	@Override public int onBlockPlaced(World p_85104_1_, int p_85104_2_, int p_85104_3_, int p_85104_4_, int p_85104_5_, float p_85104_6_, float p_85104_7_, float p_85104_8_, int p_85104_9_)
	{
		int var10 = p_85104_9_ & 3;
		byte var11 = 0;
		switch(p_85104_5_)
		{
			case 0:
			case 1:
				var11 = 0;
				break;
			case 2:
			case 3:
				var11 = 8;
				break;
			case 4:
			case 5:
				var11 = 4;
		}
		return var10 | var11;
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 1;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		tree_top = par1IconRegister.registerIcon("tree_top");
		iconArray = new Icon[treeTextureTypes.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(treeTextureTypes[var2]);
		}
	}
	
	public static int limitToValidMetadata(int p_72141_0_)
	{
		return p_72141_0_ & 3;
	}
}
