package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockLog extends Block
{
	public static final String[] woodType = new String[] { "oak", "spruce", "birch", "jungle" };
	public static final String[] treeTextureTypes = new String[] { "tree_side", "tree_spruce", "tree_birch", "tree_jungle" };
	private Icon[] iconArray;
	private Icon tree_top;
	
	protected BlockLog(int par1)
	{
		super(par1, Material.wood);
		setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		byte var7 = 4;
		int var8 = var7 + 1;
		if(par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
		{
			for(int var9 = -var7; var9 <= var7; ++var9)
			{
				for(int var10 = -var7; var10 <= var7; ++var10)
				{
					for(int var11 = -var7; var11 <= var7; ++var11)
					{
						int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);
						if(var12 == Block.leaves.blockID)
						{
							int var13 = par1World.getBlockMetadata(par2 + var9, par3 + var10, par4 + var11);
							if((var13 & 8) == 0)
							{
								par1World.setBlockMetadataWithNotify(par2 + var9, par3 + var10, par4 + var11, var13 | 8, 4);
							}
						}
					}
				}
			}
		}
	}
	
	@Override protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(blockID, 1, limitToValidMetadata(par1));
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1 & 3;
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
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return Block.wood.blockID;
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		int var10 = par9 & 3;
		byte var11 = 0;
		switch(par5)
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
	
	@Override public int quantityDropped(Random par1Random)
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
	
	public static int limitToValidMetadata(int par0)
	{
		return par0 & 3;
	}
}
