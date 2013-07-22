package net.minecraft.src;

import java.util.List;

public class BlockAnvil extends BlockSand
{
	public static final String[] statuses = new String[] { "intact", "slightlyDamaged", "veryDamaged" };
	private static final String[] anvilIconNames = new String[] { "anvil_top_damaged_0", "anvil_top_damaged_1", "anvil_top_damaged_2" };
	public int field_82521_b;
	private Icon[] iconArray;
	
	protected BlockAnvil(int par1)
	{
		super(par1, Material.anvil);
		setLightOpacity(0);
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1 >> 2;
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
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote) return true;
		else
		{
			par5EntityPlayer.displayGUIAnvil(par2, par3, par4);
			return true;
		}
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int var7 = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int var8 = par1World.getBlockMetadata(par2, par3, par4) >> 2;
		++var7;
		var7 %= 4;
		if(var7 == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | var8 << 2, 2);
		}
		if(var7 == 1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | var8 << 2, 2);
		}
		if(var7 == 2)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | var8 << 2, 2);
		}
		if(var7 == 3)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | var8 << 2, 2);
		}
	}
	
	@Override public void onFinishFalling(World par1World, int par2, int par3, int par4, int par5)
	{
		par1World.playAuxSFX(1022, par2, par3, par4, 0);
	}
	
	@Override protected void onStartFalling(EntityFallingSand par1EntityFallingSand)
	{
		par1EntityFallingSand.setIsAnvil(true);
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
	
	@Override public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;
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
