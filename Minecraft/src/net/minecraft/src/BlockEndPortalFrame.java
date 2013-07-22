package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockEndPortalFrame extends Block
{
	private Icon field_94400_a;
	private Icon field_94399_b;
	
	public BlockEndPortalFrame(int p_i4004_1_)
	{
		super(p_i4004_1_, Material.rock);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		int var8 = p_71871_1_.getBlockMetadata(p_71871_2_, p_71871_3_, p_71871_4_);
		if(isEnderEyeInserted(var8))
		{
			setBlockBounds(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
		setBlockBoundsForItemRender();
	}
	
	public Icon func_94398_p()
	{
		return field_94399_b;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? field_94400_a : par1 == 0 ? Block.whiteStone.getBlockTextureFromSide(par1) : blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 26;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = ((MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) + 2) % 4;
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("endframe_side");
		field_94400_a = par1IconRegister.registerIcon("endframe_top");
		field_94399_b = par1IconRegister.registerIcon("endframe_eye");
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
	}
	
	public static boolean isEnderEyeInserted(int p_72165_0_)
	{
		return (p_72165_0_ & 4) != 0;
	}
}
