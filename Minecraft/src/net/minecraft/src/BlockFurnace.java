package net.minecraft.src;

import java.util.Random;

public class BlockFurnace extends BlockContainer
{
	private final Random furnaceRand = new Random();
	private final boolean isActive;
	private static boolean keepFurnaceInventory = false;
	private Icon furnaceIconTop;
	private Icon furnaceIconFront;
	
	protected BlockFurnace(int p_i3950_1_, boolean p_i3950_2_)
	{
		super(p_i3950_1_, Material.rock);
		isActive = p_i3950_2_;
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		if(!keepFurnaceInventory)
		{
			TileEntityFurnace var7 = (TileEntityFurnace) p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
			if(var7 != null)
			{
				for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
				{
					ItemStack var9 = var7.getStackInSlot(var8);
					if(var9 != null)
					{
						float var10 = furnaceRand.nextFloat() * 0.8F + 0.1F;
						float var11 = furnaceRand.nextFloat() * 0.8F + 0.1F;
						float var12 = furnaceRand.nextFloat() * 0.8F + 0.1F;
						while(var9.stackSize > 0)
						{
							int var13 = furnaceRand.nextInt(21) + 10;
							if(var13 > var9.stackSize)
							{
								var13 = var9.stackSize;
							}
							var9.stackSize -= var13;
							EntityItem var14 = new EntityItem(p_71852_1_, p_71852_2_ + var10, p_71852_3_ + var11, p_71852_4_ + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
							if(var9.hasTagCompound())
							{
								var14.getEntityItem().setTagCompound((NBTTagCompound) var9.getTagCompound().copy());
							}
							float var15 = 0.05F;
							var14.motionX = (float) furnaceRand.nextGaussian() * var15;
							var14.motionY = (float) furnaceRand.nextGaussian() * var15 + 0.2F;
							var14.motionZ = (float) furnaceRand.nextGaussian() * var15;
							p_71852_1_.spawnEntityInWorld(var14);
						}
					}
				}
				p_71852_1_.func_96440_m(p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_);
			}
		}
		super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntityFurnace();
	}
	
	@Override public int getComparatorInputOverride(World p_94328_1_, int p_94328_2_, int p_94328_3_, int p_94328_4_, int p_94328_5_)
	{
		return Container.calcRedstoneFromInventory((IInventory) p_94328_1_.getBlockTileEntity(p_94328_2_, p_94328_3_, p_94328_4_));
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return par1 == 1 ? furnaceIconTop : par1 == 0 ? furnaceIconTop : par1 != par2 ? blockIcon : furnaceIconFront;
	}
	
	@Override public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Block.furnaceIdle.blockID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Block.furnaceIdle.blockID;
	}
	
	@Override public boolean onBlockActivated(World p_71903_1_, int p_71903_2_, int p_71903_3_, int p_71903_4_, EntityPlayer p_71903_5_, int p_71903_6_, float p_71903_7_, float p_71903_8_, float p_71903_9_)
	{
		if(p_71903_1_.isRemote) return true;
		else
		{
			TileEntityFurnace var10 = (TileEntityFurnace) p_71903_1_.getBlockTileEntity(p_71903_2_, p_71903_3_, p_71903_4_);
			if(var10 != null)
			{
				p_71903_5_.displayGUIFurnace(var10);
			}
			return true;
		}
	}
	
	@Override public void onBlockAdded(World p_71861_1_, int p_71861_2_, int p_71861_3_, int p_71861_4_)
	{
		super.onBlockAdded(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
		setDefaultDirection(p_71861_1_, p_71861_2_, p_71861_3_, p_71861_4_);
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		if(var7 == 0)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 2, 2);
		}
		if(var7 == 1)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 5, 2);
		}
		if(var7 == 2)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 3, 2);
		}
		if(var7 == 3)
		{
			p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, 4, 2);
		}
		if(p_71860_6_.hasDisplayName())
		{
			((TileEntityFurnace) p_71860_1_.getBlockTileEntity(p_71860_2_, p_71860_3_, p_71860_4_)).setGuiDisplayName(p_71860_6_.getDisplayName());
		}
	}
	
	@Override public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(isActive)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			float var7 = par2 + 0.5F;
			float var8 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float var9 = par4 + 0.5F;
			float var10 = 0.52F;
			float var11 = par5Random.nextFloat() * 0.6F - 0.3F;
			if(var6 == 4)
			{
				par1World.spawnParticle("smoke", var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", var7 - var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
			} else if(var6 == 5)
			{
				par1World.spawnParticle("smoke", var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", var7 + var10, var8, var9 + var11, 0.0D, 0.0D, 0.0D);
			} else if(var6 == 2)
			{
				par1World.spawnParticle("smoke", var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", var7 + var11, var8, var9 - var10, 0.0D, 0.0D, 0.0D);
			} else if(var6 == 3)
			{
				par1World.spawnParticle("smoke", var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", var7 + var11, var8, var9 + var10, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon("furnace_side");
		furnaceIconFront = par1IconRegister.registerIcon(isActive ? "furnace_front_lit" : "furnace_front");
		furnaceIconTop = par1IconRegister.registerIcon("furnace_top");
	}
	
	private void setDefaultDirection(World p_72285_1_, int p_72285_2_, int p_72285_3_, int p_72285_4_)
	{
		if(!p_72285_1_.isRemote)
		{
			int var5 = p_72285_1_.getBlockId(p_72285_2_, p_72285_3_, p_72285_4_ - 1);
			int var6 = p_72285_1_.getBlockId(p_72285_2_, p_72285_3_, p_72285_4_ + 1);
			int var7 = p_72285_1_.getBlockId(p_72285_2_ - 1, p_72285_3_, p_72285_4_);
			int var8 = p_72285_1_.getBlockId(p_72285_2_ + 1, p_72285_3_, p_72285_4_);
			byte var9 = 3;
			if(Block.opaqueCubeLookup[var5] && !Block.opaqueCubeLookup[var6])
			{
				var9 = 3;
			}
			if(Block.opaqueCubeLookup[var6] && !Block.opaqueCubeLookup[var5])
			{
				var9 = 2;
			}
			if(Block.opaqueCubeLookup[var7] && !Block.opaqueCubeLookup[var8])
			{
				var9 = 5;
			}
			if(Block.opaqueCubeLookup[var8] && !Block.opaqueCubeLookup[var7])
			{
				var9 = 4;
			}
			p_72285_1_.setBlockMetadataWithNotify(p_72285_2_, p_72285_3_, p_72285_4_, var9, 2);
		}
	}
	
	public static void updateFurnaceBlockState(boolean p_72286_0_, World p_72286_1_, int p_72286_2_, int p_72286_3_, int p_72286_4_)
	{
		int var5 = p_72286_1_.getBlockMetadata(p_72286_2_, p_72286_3_, p_72286_4_);
		TileEntity var6 = p_72286_1_.getBlockTileEntity(p_72286_2_, p_72286_3_, p_72286_4_);
		keepFurnaceInventory = true;
		if(p_72286_0_)
		{
			p_72286_1_.setBlock(p_72286_2_, p_72286_3_, p_72286_4_, Block.furnaceBurning.blockID);
		} else
		{
			p_72286_1_.setBlock(p_72286_2_, p_72286_3_, p_72286_4_, Block.furnaceIdle.blockID);
		}
		keepFurnaceInventory = false;
		p_72286_1_.setBlockMetadataWithNotify(p_72286_2_, p_72286_3_, p_72286_4_, var5, 2);
		if(var6 != null)
		{
			var6.validate();
			p_72286_1_.setBlockTileEntity(p_72286_2_, p_72286_3_, p_72286_4_, var6);
		}
	}
}
