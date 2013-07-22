package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class BlockBrewingStand extends BlockContainer
{
	private Random rand = new Random();
	private Icon theIcon;
	
	public BlockBrewingStand(int p_i3921_1_)
	{
		super(p_i3921_1_, Material.iron);
	}
	
	@Override public void addCollisionBoxesToList(World p_71871_1_, int p_71871_2_, int p_71871_3_, int p_71871_4_, AxisAlignedBB p_71871_5_, List p_71871_6_, Entity p_71871_7_)
	{
		setBlockBounds(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
		setBlockBoundsForItemRender();
		super.addCollisionBoxesToList(p_71871_1_, p_71871_2_, p_71871_3_, p_71871_4_, p_71871_5_, p_71871_6_, p_71871_7_);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		TileEntity var7 = p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
		if(var7 instanceof TileEntityBrewingStand)
		{
			TileEntityBrewingStand var8 = (TileEntityBrewingStand) var7;
			for(int var9 = 0; var9 < var8.getSizeInventory(); ++var9)
			{
				ItemStack var10 = var8.getStackInSlot(var9);
				if(var10 != null)
				{
					float var11 = rand.nextFloat() * 0.8F + 0.1F;
					float var12 = rand.nextFloat() * 0.8F + 0.1F;
					float var13 = rand.nextFloat() * 0.8F + 0.1F;
					while(var10.stackSize > 0)
					{
						int var14 = rand.nextInt(21) + 10;
						if(var14 > var10.stackSize)
						{
							var14 = var10.stackSize;
						}
						var10.stackSize -= var14;
						EntityItem var15 = new EntityItem(p_71852_1_, p_71852_2_ + var11, p_71852_3_ + var12, p_71852_4_ + var13, new ItemStack(var10.itemID, var14, var10.getItemDamage()));
						float var16 = 0.05F;
						var15.motionX = (float) rand.nextGaussian() * var16;
						var15.motionY = (float) rand.nextGaussian() * var16 + 0.2F;
						var15.motionZ = (float) rand.nextGaussian() * var16;
						p_71852_1_.spawnEntityInWorld(var15);
					}
				}
			}
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityBrewingStand();
	}
	
	public Icon getBrewingStandIcon()
	{
		return theIcon;
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		return Container.calcRedstoneFromInventory((IInventory) p_94328_1_.getBlockTileEntity(p_94328_2_, p_94328_3_, p_94328_4_));
	}
	
	@Override public int getRenderType()
	{
		return 25;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.brewingStand.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.brewingStand.itemID;
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
			TileEntityBrewingStand var10 = (TileEntityBrewingStand) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				p_71903_5_.displayGUIBrewingStand(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		if(p_71860_6_.hasDisplayName())
		{
			((TileEntityBrewingStand) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_)).func_94131_a(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		double var6 = par2 + 0.4F + par5Random.nextFloat() * 0.2F;
		double var8 = par3 + 0.7F + par5Random.nextFloat() * 0.3F;
		double var10 = par4 + 0.4F + par5Random.nextFloat() * 0.2F;
		par1World.spawnParticle("smoke", var6, var8, var10, 0.0D, 0.0D, 0.0D);
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
		theIcon = par1IconRegister.registerIcon("brewingStand_base");
	}
	
	@Override public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override public void setBlockBoundsForItemRender()
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}
}
