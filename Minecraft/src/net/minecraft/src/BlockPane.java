package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPane extends Block
{
	private final String sideTextureIndex;
	private final boolean canDropItself;
	private final String field_94402_c;
	private Icon theIcon;
	
	protected BlockPane(int par1, String par2Str, String par3Str, Material par4Material, boolean par5)
	{
		super(par1, par4Material);
		sideTextureIndex = par3Str;
		canDropItself = par5;
		field_94402_c = par2Str;
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		boolean var8 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 - 1));
		boolean var9 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2, par3, par4 + 1));
		boolean var10 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 - 1, par3, par4));
		boolean var11 = canThisPaneConnectToThisBlockID(par1World.getBlockId(par2 + 1, par3, par4));
		if((!var10 || !var11) && (var10 || var11 || var8 || var9))
		{
			if(var10 && !var11)
			{
				setBlockBounds(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			} else if(!var10 && var11)
			{
				setBlockBounds(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			}
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		if((!var8 || !var9) && (var10 || var11 || var8 || var9))
		{
			if(var8 && !var9)
			{
				setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			} else if(!var8 && var9)
			{
				setBlockBounds(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
			}
		} else
		{
			setBlockBounds(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
	}
	
	@Override protected boolean canSilkHarvest()
	{
		return true;
	}
	
	public final boolean canThisPaneConnectToThisBlockID(int par1)
	{
		return Block.opaqueCubeLookup[par1] || par1 == blockID || par1 == Block.glass.blockID;
	}
	
	@Override protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(blockID, 1, par1);
	}
	
	@Override public int getRenderType()
	{
		return 18;
	}
	
	public Icon getSideTextureIndex()
	{
		return theIcon;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return !canDropItself ? 0 : super.idDropped(par1, par2Random, par3);
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		float var5 = 0.4375F;
		float var6 = 0.5625F;
		float var7 = 0.4375F;
		float var8 = 0.5625F;
		boolean var9 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 - 1));
		boolean var10 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2, par3, par4 + 1));
		boolean var11 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 - 1, par3, par4));
		boolean var12 = canThisPaneConnectToThisBlockID(par1IBlockAccess.getBlockId(par2 + 1, par3, par4));
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
