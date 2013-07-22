package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPane extends Block
{
	private final String sideTextureIndex;
	private final boolean canDropItself;
	private final String field_94402_c;
	private Icon theIcon;
	
	protected BlockPane(int p_i9094_1_, String p_i9094_2_, String p_i9094_3_, Material p_i9094_4_, boolean p_i9094_5_)
	{
		super(p_i9094_1_, p_i9094_4_);
		sideTextureIndex = p_i9094_3_;
		canDropItself = p_i9094_5_;
		field_94402_c = p_i9094_2_;
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		boolean var8 = canThisPaneConnectToThisBlockID(p_71871_1_.getBlockId(p_71871_2_, p_71871_3_, p_71871_4_ - 1));
		boolean var9 = canThisPaneConnectToThisBlockID(p_71871_1_.getBlockId(p_71871_2_, p_71871_3_, p_71871_4_ + 1));
		boolean var10 = canThisPaneConnectToThisBlockID(p_71871_1_.getBlockId(p_71871_2_ - 1, p_71871_3_, p_71871_4_));
		boolean var11 = canThisPaneConnectToThisBlockID(p_71871_1_.getBlockId(p_71871_2_ + 1, p_71871_3_, p_71871_4_));
		if((!var10 || !var11) && (var10 || var11 || var8 || var9))
		{
			if(var10 && !var11)
			{
				setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
			} else if(!var10 && var11)
			{
				setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
			}
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
		if((!var8 || !var9) && (var10 || var11 || var8 || var9))
		{
			if(var8 && !var9)
			{
				setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
			} else if(!var8 && var9)
			{
				setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
			}
		} else
		{
			setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
	}
	
	@Override protected boolean canSilkHarvest()
	{
		return true;
	}
	
	public final boolean canThisPaneConnectToThisBlockID(int p_72161_1_)
	{
		return Block.opaqueCubeLookup[p_72161_1_] || p_72161_1_ == blockID || p_72161_1_ == Block.glass.blockID;
	}
	
	@Override protected ItemStack createStackedBlock(int p_71880_1_)
	{
		return new ItemStack(blockID, 1, p_71880_1_);
	}
	
	@Override public int getRenderType()
	{
		return 18;
	}
	
	public Icon getSideTextureIndex()
	{
		return theIcon;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return !canDropItself ? 0 : super.idDropped(p_71885_1_, p_71885_2_, p_71885_3_);
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(field_94402_c);
		theIcon = par1IconRegister.registerIcon(sideTextureIndex);
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		float var5 = 0.4375F;
		float var6 = 0.5625F;
		float var7 = 0.4375F;
		float var8 = 0.5625F;
		boolean var9 = canThisPaneConnectToThisBlockID(p_71902_1_.getBlockId(p_71902_2_, p_71902_3_, p_71902_4_ - 1));
		boolean var10 = canThisPaneConnectToThisBlockID(p_71902_1_.getBlockId(p_71902_2_, p_71902_3_, p_71902_4_ + 1));
		boolean var11 = canThisPaneConnectToThisBlockID(p_71902_1_.getBlockId(p_71902_2_ - 1, p_71902_3_, p_71902_4_));
		boolean var12 = canThisPaneConnectToThisBlockID(p_71902_1_.getBlockId(p_71902_2_ + 1, p_71902_3_, p_71902_4_));
		if((!var11 || !var12) && (var11 || var12 || var9 || var10))
		{
			if(var11 && !var12)
			{
				var5 = 0.0F;
			} else if(!var11 && var12)
			{
				var6 = 1.0F;
			}
		} else
		{
			var5 = 0.0F;
			var6 = 1.0F;
		}
		if((!var9 || !var10) && (var11 || var12 || var9 || var10))
		{
			if(var9 && !var10)
			{
				var7 = 0.0F;
			} else if(!var9 && var10)
			{
				var8 = 1.0F;
			}
		} else
		{
			var7 = 0.0F;
			var8 = 1.0F;
		}
		setBlockBounds(var5, 0.0F, var7, var6, 1.0F, var8);
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int var6 = par1IBlockAccess.getBlockId(par2, par3, par4);
		return var6 == blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
