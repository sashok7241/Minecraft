package net.minecraft.src;

import java.util.Random;

public class BlockCommandBlock extends BlockContainer
{
	public BlockCommandBlock(int par1)
	{
		super(par1, Material.iron);
	}
	
	@Override public TileEntity createNewTileEntity(World par1World)
	{
		return new TileEntityCommandBlock();
	}
	
	@Override public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
		return var6 != null && var6 instanceof TileEntityCommandBlock ? ((TileEntityCommandBlock) var6).func_96103_d() : 0;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TileEntityCommandBlock var10 = (TileEntityCommandBlock) par1World.getBlockTileEntity(par2, par3, par4);
		if(var10 != null)
		{
			par5EntityPlayer.displayGUIEditSign(var10);
		}
		return true;
	}
	
	@Override public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		TileEntityCommandBlock var7 = (TileEntityCommandBlock) par1World.getBlockTileEntity(par2, par3, par4);
		if(par6ItemStack.hasDisplayName())
		{
			var7.setCommandSenderName(par6ItemStack.getDisplayName());
		}
	}
	
	@Override public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(!par1World.isRemote)
		{
			boolean var6 = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);
			int var7 = par1World.getBlockMetadata(par2, par3, par4);
			boolean var8 = (var7 & 1) != 0;
			if(var6 && !var8)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 | 1, 4);
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			} else if(!var6 && var8)
			{
				par1World.setBlockMetadataWithNotify(par2, par3, par4, var7 & -2, 4);
			}
		}
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 0;
	}
	
	@Override public int tickRate(World par1World)
	{
		return 1;
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		TileEntity var6 = par1World.getBlockTileEntity(par2, par3, par4);
		if(var6 != null && var6 instanceof TileEntityCommandBlock)
		{
			TileEntityCommandBlock var7 = (TileEntityCommandBlock) var6;
			var7.func_96102_a(var7.executeCommandOnPowered(par1World));
			par1World.func_96440_m(par2, par3, par4, blockID);
		}
	}
}
