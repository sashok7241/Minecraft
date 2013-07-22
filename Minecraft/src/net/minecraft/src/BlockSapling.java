package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockSapling extends BlockFlower
{
	public static final String[] WOOD_TYPES = new String[] { "oak", "spruce", "birch", "jungle" };
	private static final String[] field_94370_b = new String[] { "sapling", "sapling_spruce", "sapling_birch", "sapling_jungle" };
	private Icon[] saplingIcon;
	
	protected BlockSapling(int p_i9089_1_)
	{
		super(p_i9089_1_);
		float var2 = 0.4F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var2 * 2.0F, 0.5F + var2);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_ & 3;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		par2 &= 3;
		return saplingIcon[par2];
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	public void growTree(World p_72269_1_, int p_72269_2_, int p_72269_3_, int p_72269_4_, Random p_72269_5_)
	{
		int var6 = p_72269_1_.getBlockMetadata(p_72269_2_, p_72269_3_, p_72269_4_) & 3;
		Object var7 = null;
		int var8 = 0;
		int var9 = 0;
		boolean var10 = false;
		if(var6 == 1)
		{
			var7 = new WorldGenTaiga2(true);
		} else if(var6 == 2)
		{
			var7 = new WorldGenForest(true);
		} else if(var6 == 3)
		{
			for(var8 = 0; var8 >= -1; --var8)
			{
				for(var9 = 0; var9 >= -1; --var9)
				{
					if(isSameSapling(p_72269_1_, p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9, 3) && isSameSapling(p_72269_1_, p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9, 3) && isSameSapling(p_72269_1_, p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9 + 1, 3) && isSameSapling(p_72269_1_, p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9 + 1, 3))
					{
						var7 = new WorldGenHugeTrees(true, 10 + p_72269_5_.nextInt(20), 3, 3);
						var10 = true;
						break;
					}
				}
				if(var7 != null)
				{
					break;
				}
			}
			if(var7 == null)
			{
				var9 = 0;
				var8 = 0;
				var7 = new WorldGenTrees(true, 4 + p_72269_5_.nextInt(7), 3, 3, false);
			}
		} else
		{
			var7 = new WorldGenTrees(true);
			if(p_72269_5_.nextInt(10) == 0)
			{
				var7 = new WorldGenBigTree(true);
			}
		}
		if(var10)
		{
			p_72269_1_.setBlock(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9, 0, 0, 4);
			p_72269_1_.setBlock(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9, 0, 0, 4);
			p_72269_1_.setBlock(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9 + 1, 0, 0, 4);
			p_72269_1_.setBlock(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9 + 1, 0, 0, 4);
		} else
		{
			p_72269_1_.setBlock(p_72269_2_, p_72269_3_, p_72269_4_, 0, 0, 4);
		}
		if(!((WorldGenerator) var7).generate(p_72269_1_, p_72269_5_, p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9))
		{
			if(var10)
			{
				p_72269_1_.setBlock(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9, blockID, var6, 4);
				p_72269_1_.setBlock(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9, blockID, var6, 4);
				p_72269_1_.setBlock(p_72269_2_ + var8, p_72269_3_, p_72269_4_ + var9 + 1, blockID, var6, 4);
				p_72269_1_.setBlock(p_72269_2_ + var8 + 1, p_72269_3_, p_72269_4_ + var9 + 1, blockID, var6, 4);
			} else
			{
				p_72269_1_.setBlock(p_72269_2_, p_72269_3_, p_72269_4_, blockID, var6, 4);
			}
		}
	}
	
	public boolean isSameSapling(World p_72268_1_, int p_72268_2_, int p_72268_3_, int p_72268_4_, int p_72268_5_)
	{
		return p_72268_1_.getBlockId(p_72268_2_, p_72268_3_, p_72268_4_) == blockID && (p_72268_1_.getBlockMetadata(p_72268_2_, p_72268_3_, p_72268_4_) & 3) == p_72268_5_;
	}
	
	public void markOrGrowMarked(World p_96477_1_, int p_96477_2_, int p_96477_3_, int p_96477_4_, Random p_96477_5_)
	{
		int var6 = p_96477_1_.getBlockMetadata(p_96477_2_, p_96477_3_, p_96477_4_);
		if((var6 & 8) == 0)
		{
			p_96477_1_.setBlockMetadataWithNotify(p_96477_2_, p_96477_3_, p_96477_4_, var6 | 8, 4);
		} else
		{
			growTree(p_96477_1_, p_96477_2_, p_96477_3_, p_96477_4_, p_96477_5_);
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		saplingIcon = new Icon[field_94370_b.length];
		for(int var2 = 0; var2 < saplingIcon.length; ++var2)
		{
			saplingIcon[var2] = par1IconRegister.registerIcon(field_94370_b[var2]);
		}
	}
	
	@Override public void updateTick(World p_71847_1_, int p_71847_2_, int p_71847_3_, int p_71847_4_, Random p_71847_5_)
	{
		if(!p_71847_1_.isRemote)
		{
			super.updateTick(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
			if(p_71847_1_.getBlockLightValue(p_71847_2_, p_71847_3_ + 1, p_71847_4_) >= 9 && p_71847_5_.nextInt(7) == 0)
			{
				markOrGrowMarked(p_71847_1_, p_71847_2_, p_71847_3_, p_71847_4_, p_71847_5_);
			}
		}
	}
}
