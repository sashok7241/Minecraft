package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockEndPortalFrame extends Block
{
	private Icon field_94400_a;
	private Icon field_94399_b;
	
	public BlockEndPortalFrame(int par1)
	{
		super(par1, Material.rock);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
		super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		int var8 = par1World.getBlockMetadata(par2, par3, par4);
		if(isEnderEyeInserted(var8))
		{
			setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		setBlockBoundsForItemRender();
	}
	
	public Icon func_94398_p()
	{
		return field_94399_b;
	}
	
	@Override public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		return isEnderEyeInserted(var6) ? 15 : 0;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? field_94400_a : par1 == 0 ? Block.whiteStone.getBlockTextureFromSide(par1) : blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 26;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int var7 = ((MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) + 2) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(func_111023_E() + "_side");
		field_94400_a = par1IconRegister.registerIcon(func_111023_E() + "_top");
		field_94399_b = par1IconRegister.registerIcon(func_111023_E() + "_eye");
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	}
	
	public static boolean isEnderEyeInserted(int par0)
	{
		return (par0 & 4) != 0;
	}
}
