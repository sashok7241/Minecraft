package net.minecraft.src;

import java.util.List;

public class BlockAnvil extends BlockSand
{
	public static final String[] statuses = new String[] { "intact", "slightlyDamaged", "veryDamaged" };
	private static final String[] anvilIconNames = new String[] { "anvil_top", "anvil_top_damaged_1", "anvil_top_damaged_2" };
	public int field_82521_b = 0;
	private Icon[] iconArray;
	
	protected BlockAnvil(int p_i5098_1_)
	{
		super(p_i5098_1_, Material.anvil);
		setLightOpacity(0);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_ >> 2;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(field_82521_b == 3 && par1 == 1)
		{
			int var3 = (par2 >> 2) % iconArray.length;
			return iconArray[var3];
		} else return blockIcon;
	}
	
	@Override public int getRenderType()
	{
		return 35;
	}
	
	@Override public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			p_71903_5_.displayGUIAnvil(p_71903_2_, p_71903_3_, p_71903_4_);
			return true;
		}
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int var8 = p_71860_1_.getBlockMetadata(p_71860_2_, p_71860_3_, p_71860_4_) >> 2;
		++var7;
		var7 %= 4;
		if(var7 == 0)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 2 | var8 << 2, 2);
		}
		if(var7 == 1)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 3 | var8 << 2, 2);
		}
		if(var7 == 2)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 0 | var8 << 2, 2);
		}
		if(var7 == 3)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 1 | var8 << 2, 2);
		}
	}
	
	@Override public void onFinishFalling(World p_82519_1_, int p_82519_2_, int p_82519_3_, int p_82519_4_, int p_82519_5_)
	{
		p_82519_1_.playAuxSFX(1022, p_82519_2_, p_82519_3_, p_82519_4_, 0);
	}
	
	@Override protected void onStartFalling(EntityFallingSand p_82520_1_)
	{
		p_82520_1_.setIsAnvil(true);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("anvil_base");
		iconArray = new Icon[anvilIconNames.length];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon(anvilIconNames[var2]);
		}
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess p_71902_1_, int p_71902_2_, int p_71902_3_, int p_71902_4_)
	{
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_) & 3;
		if(var5 != 3 && var5 != 1)
		{
			setBlockBounds(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
		} else
		{
			setBlockBounds(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
		}
	}
	
	@Override public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}
}
