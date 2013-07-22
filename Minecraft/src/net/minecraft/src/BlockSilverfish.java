package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockSilverfish extends Block
{
	public static final String[] silverfishStoneTypes = new String[] { "stone", "cobble", "brick" };
	
	public BlockSilverfish(int par1)
	{
		super(par1, Material.clay);
		setHardness(0.0F);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override protected ItemStack createStackedBlock(int par1)
	{
		Block var2 = Block.stone;
		if(par1 == 1)
		{
			var2 = Block.cobblestone;
		}
		if(par1 == 2)
		{
			var2 = Block.stoneBrick;
		}
		return new ItemStack(var2);
	}
	
	@Override public int getDamageValue(World par1World, int par2, int par3, int par4)
	{
		return par1World.getBlockMetadata(par2, par3, par4);
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
	
	@Override public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			EntitySilverfish var6 = new EntitySilverfish(par1World);
			var6.setLocationAndAngles(par2 + 0.5D, par3, par4 + 0.5D, 0.0F, 0.0F);
			par1World.spawnEntityInWorld(var6);
			var6.spawnExplosionParticle();
		}
		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	public static int getMetadataForBlockType(int par0)
	{
		return par0 == Block.cobblestone.blockID ? 1 : par0 == Block.stoneBrick.blockID ? 2 : 0;
	}
	
	public static boolean getPosingIdByMetadata(int par0)
	{
		return par0 == Block.stone.blockID || par0 == Block.cobblestone.blockID || par0 == Block.stoneBrick.blockID;
	}
}
