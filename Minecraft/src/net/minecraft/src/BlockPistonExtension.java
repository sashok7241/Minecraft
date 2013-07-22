package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block
{
	private Icon headTexture;
	
	public BlockPistonExtension(int par1)
	{
		super(par1, Material.piston);
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
	}
	
	@Override public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		int var8 = par1World.getBlockMetadata(par2, par3, par4);
		float var9 = 0.25F;
		float var10 = 0.375F;
		float var11 = 0.625F;
		float var12 = 0.25F;
		float var13 = 0.75F;
		switch(getDirectionMeta(var8))
		{
			case 0:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				break;
			case 1:
				setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				break;
			case 2:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				break;
			case 3:
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				break;
			case 4:
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				break;
			case 5:
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
				setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
				super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		int var7 = Facing.oppositeSide[getDirectionMeta(par6)];
		par2 += Facing.offsetsXForSide[var7];
		par3 += Facing.offsetsYForSide[var7];
		par4 += Facing.offsetsZForSide[var7];
		int var8 = par1World.getBlockId(par2, par3, par4);
		if(var8 == Block.pistonBase.blockID || var8 == Block.pistonStickyBase.blockID)
		{
			par6 = par1World.getBlockMetadata(par2, par3, par4);
			if(BlockPistonBase.isExtended(par6))
			{
				Block.blocksList[var8].dropBlockAsItem(par1World, par2, par3, par4, par6, 0);
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}
	
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return false;
	}
	
	@Override public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
	{
		return false;
	}
	
	public void clearHeadTexture()
	{
		headTexture = null;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = getDirectionMeta(par2);
		return par1 == var3 ? headTexture != null ? headTexture : (par2 & 8) != 0 ? BlockPistonBase.func_94496_b("piston_top_sticky") : BlockPistonBase.func_94496_b("piston_top_normal") : var3 < 6 && par1 == Facing.oppositeSide[var3] ? BlockPistonBase.func_94496_b("piston_top_normal") : BlockPistonBase.func_94496_b("piston_side");
	}
	
	@Override public int getRenderType()
	{
		return 17;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4);
		return (var5 & 8) != 0 ? Block.pistonStickyBase.blockID : Block.pistonBase.blockID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
		if(par6EntityPlayer.capabilities.isCreativeMode)
		{
			int var7 = getDirectionMeta(par5);
			int var8 = par1World.getBlockId(par2 - Facing.offsetsXForSide[var7], par3 - Facing.offsetsYForSide[var7], par4 - Facing.offsetsZForSide[var7]);
			if(var8 == Block.pistonBase.blockID || var8 == Block.pistonStickyBase.blockID)
			{
				par1World.setBlockToAir(par2 - Facing.offsetsXForSide[var7], par3 - Facing.offsetsYForSide[var7], par4 - Facing.offsetsZForSide[var7]);
			}
		}
		super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		int var6 = getDirectionMeta(par1World.getBlockMetadata(par2, par3, par4));
		int var7 = par1World.getBlockId(par2 - Facing.offsetsXForSide[var6], par3 - Facing.offsetsYForSide[var6], par4 - Facing.offsetsZForSide[var6]);
		if(var7 != Block.pistonBase.blockID && var7 != Block.pistonStickyBase.blockID)
		{
			par1World.setBlockToAir(par2, par3, par4);
		} else
		{
			Block.blocksList[var7].onNeighborBlockChange(par1World, par2 - Facing.offsetsXForSide[var6], par3 - Facing.offsetsYForSide[var6], par4 - Facing.offsetsZForSide[var6], par5);
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		float var6 = 0.25F;
		switch(getDirectionMeta(var5))
		{
			case 0:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
				break;
			case 1:
				setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
			case 2:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
				break;
			case 3:
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
				break;
			case 4:
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
				break;
			case 5:
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
	public void setHeadTexture(Icon par1Icon)
	{
		headTexture = par1Icon;
	}
	
	public static int getDirectionMeta(int par0)
	{
		return par0 & 7;
	}
}
