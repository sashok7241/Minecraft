package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block
{
	private Icon headTexture = null;
	
	public BlockPistonExtension(int p_i9106_1_)
	{
		super(p_i9106_1_, Material.piston);
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		int var8 = p_71871_1_.getBlockMetadata(p_71871_2_, p_71871_3_, p_71871_4_);
		switch(getDirectionMeta(var8))
		{
			case 0:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				break;
			case 1:
				setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				break;
			case 2:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				break;
			case 3:
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				break;
			case 4:
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				break;
			case 5:
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
				setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
				super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		}
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		int var7 = Facing.oppositeSide[getDirectionMeta(p_71852_6_)];
		p_71852_2_ += Facing.offsetsXForSide[var7];
		p_71852_3_ += Facing.offsetsYForSide[var7];
		p_71852_4_ += Facing.offsetsZForSide[var7];
		int var8 = p_71852_1_.getBlockId(p_71852_2_, p_71852_3_, p_71852_4_);
		if(var8 == Block.pistonBase.blockID || var8 == Block.pistonStickyBase.blockID)
		{
			p_71852_6_ = p_71852_1_.getBlockMetadata(p_71852_2_, p_71852_3_, p_71852_4_);
			if(BlockPistonBase.isExtended(p_71852_6_))
			{
				Block.blocksList[var8].dropBlockAsItem(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_6_, 0);
				p_71852_1_.setBlockToAir(p_71852_2_, p_71852_3_, p_71852_4_);
			}
		}
	}
	
	@Override public boolean canPlaceBlockAt(World p_71930_1_, int p_71930_2_, int p_71930_3_, int p_71930_4_)
	{
		return false;
	}
	
	@Override public boolean canPlaceBlockOnSide(World p_71850_1_, int p_71850_2_, int p_71850_3_, int p_71850_4_, int p_71850_5_)
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
		return par1 == var3 ? headTexture != null ? headTexture : (par2 & 8) != 0 ? BlockPistonBase.func_94496_b("piston_top_sticky") : BlockPistonBase.func_94496_b("piston_top") : var3 < 6 && par1 == Facing.oppositeSide[var3] ? BlockPistonBase.func_94496_b("piston_top") : BlockPistonBase.func_94496_b("piston_side");
	}
	
	@Override public int getRenderType()
	{
		return 17;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return 0;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public void onNeighborBlockChange(World p_71863_1_, int p_71863_2_, int p_71863_3_, int p_71863_4_, int p_71863_5_)
	{
		int var6 = getDirectionMeta(p_71863_1_.getBlockMetadata(p_71863_2_, p_71863_3_, p_71863_4_));
		int var7 = p_71863_1_.getBlockId(p_71863_2_ - Facing.offsetsXForSide[var6], p_71863_3_ - Facing.offsetsYForSide[var6], p_71863_4_ - Facing.offsetsZForSide[var6]);
		if(var7 != Block.pistonBase.blockID && var7 != Block.pistonStickyBase.blockID)
		{
			p_71863_1_.setBlockToAir(p_71863_2_, p_71863_3_, p_71863_4_);
		} else
		{
			Block.blocksList[var7].onNeighborBlockChange(p_71863_1_, p_71863_2_ - Facing.offsetsXForSide[var6], p_71863_3_ - Facing.offsetsYForSide[var6], p_71863_4_ - Facing.offsetsZForSide[var6], p_71863_5_);
		}
	}
	
	@Override public int quantityDropped(Random p_71925_1_)
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_);
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
	
	public static int getDirectionMeta(int p_72121_0_)
	{
		return p_72121_0_ & 7;
	}
}
