package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockSilverfish extends Block
{
	public static final String[] silverfishStoneTypes = new String[] { "stone", "cobble", "brick" };
	
	public BlockSilverfish(int p_i3999_1_)
	{
		super(p_i3999_1_, Material.clay);
		setHardness(0.0F);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		Block var2 = Block.stone;
		if(p_71880_1_ == 1)
		{
			var2 = Block.cobblestone;
		}
		if(p_71880_1_ == 2)
		{
			var2 = Block.stoneBrick;
		}
		return new ItemStack(var2);
	}
	
	@Override public int getDamageValue(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
	{
		return p_71873_1_.getBlockMetadata(p_71873_2_, p_71873_3_, p_71873_4_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par2 == 1 ? Block.cobblestone.getBlockTextureFromSide(par1) : par2 == 2 ? Block.stoneBrick.getBlockTextureFromSide(par1) : Block.stone.getBlockTextureFromSide(par1);
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int var4 = 0; var4 < 3; ++var4)
		{
			par3List.add(new ItemStack(par1, 1, var4));
		}
	}
	
	@Override public void onBlockDestroyedByPlayer(World p_71898_1_, int p_71898_2_, int p_71898_3_, int p_71898_4_, int p_71898_5_)
	{
		if(!p_71898_1_.isRemote)
		{
			EntitySilverfish var6 = new EntitySilverfish(p_71898_1_);
			var6.setLocationAndAngles(p_71898_2_ + 0.5D, p_71898_3_, p_71898_4_ + 0.5D, 0.0F, 0.0F);
			p_71898_1_.spawnEntityInWorld(var6);
			var6.spawnExplosionParticle();
		}
		super.onBlockDestroyedByPlayer(p_71898_1_, p_71898_2_, p_71898_3_, p_71898_4_, p_71898_5_);
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
	{
		return 0;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	public static int getMetadataForBlockType(int p_72153_0_)
	{
		return p_72153_0_ == Block.cobblestone.blockID ? 1 : p_72153_0_ == Block.stoneBrick.blockID ? 2 : 0;
	}
	
	public static boolean getPosingIdByMetadata(int p_72154_0_)
	{
		return p_72154_0_ == Block.stone.blockID || p_72154_0_ == Block.cobblestone.blockID || p_72154_0_ == Block.stoneBrick.blockID;
	}
}
