package net.minecraft.src;

import java.util.Random;

public class BlockSkull extends BlockContainer
{
	protected BlockSkull(int p_i5106_1_)
	{
		super(p_i5106_1_, Material.circuits);
		setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
	}
	
	@Override public void breakBlock(World p_71852_1_, int p_71852_2_, int p_71852_3_, int p_71852_4_, int p_71852_5_, int p_71852_6_)
	{
		if(!p_71852_1_.isRemote)
		{
			if((p_71852_6_ & 8) == 0)
			{
				ItemStack var7 = new ItemStack(Item.skull.itemID, 1, getDamageValue(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_));
				TileEntitySkull var8 = (TileEntitySkull) p_71852_1_.getBlockTileEntity(p_71852_2_, p_71852_3_, p_71852_4_);
				if(var8.getSkullType() == 3 && var8.getExtraType() != null && var8.getExtraType().length() > 0)
				{
					var7.setTagCompound(new NBTTagCompound());
					var7.getTagCompound().setString("SkullOwner", var8.getExtraType());
				}
				dropBlockAsItem_do(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, var7);
			}
			super.breakBlock(p_71852_1_, p_71852_2_, p_71852_3_, p_71852_4_, p_71852_5_, p_71852_6_);
		}
	}
	
	@Override public TileEntity createNewTileEntity(World p_72274_1_)
	{
		return new TileEntitySkull();
	}
	
	@Override public int damageDropped(int p_71899_1_)
	{
		return p_71899_1_;
	}
	
	@Override public void dropBlockAsItemWithChance(World p_71914_1_, int p_71914_2_, int p_71914_3_, int p_71914_4_, int p_71914_5_, float p_71914_6_, int p_71914_7_)
	{
	}
	
	private boolean func_82528_d(World p_82528_1_, int p_82528_2_, int p_82528_3_, int p_82528_4_, int p_82528_5_)
	{
		if(p_82528_1_.getBlockId(p_82528_2_, p_82528_3_, p_82528_4_) != blockID) return false;
		else
		{
			TileEntity var6 = p_82528_1_.getBlockTileEntity(p_82528_2_, p_82528_3_, p_82528_4_);
			return var6 != null && var6 instanceof TileEntitySkull ? ((TileEntitySkull) var6).getSkullType() == p_82528_5_ : false;
		}
	}
	
	@Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_71872_1_, int p_71872_2_, int p_71872_3_, int p_71872_4_)
	{
		setBlockBoundsBasedOnState(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
		return super.getCollisionBoundingBoxFromPool(p_71872_1_, p_71872_2_, p_71872_3_, p_71872_4_);
	}
	
	@Override public int getDamageValue(World p_71873_1_, int p_71873_2_, int p_71873_3_, int p_71873_4_)
	{
		TileEntity var5 = p_71873_1_.getBlockTileEntity(p_71873_2_, p_71873_3_, p_71873_4_);
		return var5 != null && var5 instanceof TileEntitySkull ? ((TileEntitySkull) var5).getSkullType() : super.getDamageValue(p_71873_1_, p_71873_2_, p_71873_3_, p_71873_4_);
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		return Block.slowSand.getBlockTextureFromSide(par1);
	}
	
	@Override public String getItemIconName()
	{
		return ItemSkull.field_94587_a[0];
	}
	
	@Override public int getRenderType()
	{
		return -1;
	}
	
	@Override public int idDropped(int p_71885_1_, Random p_71885_2_, int p_71885_3_)
	{
		return Item.skull.itemID;
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return Item.skull.itemID;
	}
	
	@Override public boolean isOpaqueCube()
	{
		return false;
	}
	
	public void makeWither(World p_82529_1_, int p_82529_2_, int p_82529_3_, int p_82529_4_, TileEntitySkull p_82529_5_)
	{
		if(p_82529_5_.getSkullType() == 1 && p_82529_3_ >= 2 && p_82529_1_.difficultySetting > 0 && !p_82529_1_.isRemote)
		{
			int var6 = Block.slowSand.blockID;
			int var7;
			EntityWither var8;
			int var9;
			for(var7 = -2; var7 <= 0; ++var7)
			{
				if(p_82529_1_.getBlockId(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7) == var6 && p_82529_1_.getBlockId(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7 + 1) == var6 && p_82529_1_.getBlockId(p_82529_2_, p_82529_3_ - 2, p_82529_4_ + var7 + 1) == var6 && p_82529_1_.getBlockId(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7 + 2) == var6 && func_82528_d(p_82529_1_, p_82529_2_, p_82529_3_, p_82529_4_ + var7, 1) && func_82528_d(p_82529_1_, p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 1, 1) && func_82528_d(p_82529_1_, p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 2, 1))
				{
					p_82529_1_.setBlockMetadataWithNotify(p_82529_2_, p_82529_3_, p_82529_4_ + var7, 8, 2);
					p_82529_1_.setBlockMetadataWithNotify(p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 1, 8, 2);
					p_82529_1_.setBlockMetadataWithNotify(p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 2, 8, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_, p_82529_4_ + var7, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 1, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 2, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7 + 1, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7 + 2, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_, p_82529_3_ - 2, p_82529_4_ + var7 + 1, 0, 0, 2);
					if(!p_82529_1_.isRemote)
					{
						var8 = new EntityWither(p_82529_1_);
						var8.setLocationAndAngles(p_82529_2_ + 0.5D, p_82529_3_ - 1.45D, p_82529_4_ + var7 + 1.5D, 90.0F, 0.0F);
						var8.renderYawOffset = 90.0F;
						var8.func_82206_m();
						p_82529_1_.spawnEntityInWorld(var8);
					}
					for(var9 = 0; var9 < 120; ++var9)
					{
						p_82529_1_.spawnParticle("snowballpoof", p_82529_2_ + p_82529_1_.rand.nextDouble(), p_82529_3_ - 2 + p_82529_1_.rand.nextDouble() * 3.9D, p_82529_4_ + var7 + 1 + p_82529_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					}
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_, p_82529_4_ + var7, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 1, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_, p_82529_4_ + var7 + 2, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7 + 1, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_ - 1, p_82529_4_ + var7 + 2, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_, p_82529_3_ - 2, p_82529_4_ + var7 + 1, 0);
					return;
				}
			}
			for(var7 = -2; var7 <= 0; ++var7)
			{
				if(p_82529_1_.getBlockId(p_82529_2_ + var7, p_82529_3_ - 1, p_82529_4_) == var6 && p_82529_1_.getBlockId(p_82529_2_ + var7 + 1, p_82529_3_ - 1, p_82529_4_) == var6 && p_82529_1_.getBlockId(p_82529_2_ + var7 + 1, p_82529_3_ - 2, p_82529_4_) == var6 && p_82529_1_.getBlockId(p_82529_2_ + var7 + 2, p_82529_3_ - 1, p_82529_4_) == var6 && func_82528_d(p_82529_1_, p_82529_2_ + var7, p_82529_3_, p_82529_4_, 1) && func_82528_d(p_82529_1_, p_82529_2_ + var7 + 1, p_82529_3_, p_82529_4_, 1) && func_82528_d(p_82529_1_, p_82529_2_ + var7 + 2, p_82529_3_, p_82529_4_, 1))
				{
					p_82529_1_.setBlockMetadataWithNotify(p_82529_2_ + var7, p_82529_3_, p_82529_4_, 8, 2);
					p_82529_1_.setBlockMetadataWithNotify(p_82529_2_ + var7 + 1, p_82529_3_, p_82529_4_, 8, 2);
					p_82529_1_.setBlockMetadataWithNotify(p_82529_2_ + var7 + 2, p_82529_3_, p_82529_4_, 8, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7, p_82529_3_, p_82529_4_, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7 + 1, p_82529_3_, p_82529_4_, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7 + 2, p_82529_3_, p_82529_4_, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7, p_82529_3_ - 1, p_82529_4_, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7 + 1, p_82529_3_ - 1, p_82529_4_, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7 + 2, p_82529_3_ - 1, p_82529_4_, 0, 0, 2);
					p_82529_1_.setBlock(p_82529_2_ + var7 + 1, p_82529_3_ - 2, p_82529_4_, 0, 0, 2);
					if(!p_82529_1_.isRemote)
					{
						var8 = new EntityWither(p_82529_1_);
						var8.setLocationAndAngles(p_82529_2_ + var7 + 1.5D, p_82529_3_ - 1.45D, p_82529_4_ + 0.5D, 0.0F, 0.0F);
						var8.func_82206_m();
						p_82529_1_.spawnEntityInWorld(var8);
					}
					for(var9 = 0; var9 < 120; ++var9)
					{
						p_82529_1_.spawnParticle("snowballpoof", p_82529_2_ + var7 + 1 + p_82529_1_.rand.nextDouble(), p_82529_3_ - 2 + p_82529_1_.rand.nextDouble() * 3.9D, p_82529_4_ + p_82529_1_.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
					}
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7, p_82529_3_, p_82529_4_, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7 + 1, p_82529_3_, p_82529_4_, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7 + 2, p_82529_3_, p_82529_4_, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7, p_82529_3_ - 1, p_82529_4_, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7 + 1, p_82529_3_ - 1, p_82529_4_, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7 + 2, p_82529_3_ - 1, p_82529_4_, 0);
					p_82529_1_.notifyBlockChange(p_82529_2_ + var7 + 1, p_82529_3_ - 2, p_82529_4_, 0);
					return;
				}
			}
		}
	}
	
	@Override public void onBlockHarvested(World p_71846_1_, int p_71846_2_, int p_71846_3_, int p_71846_4_, int p_71846_5_, EntityPlayer p_71846_6_)
	{
		if(p_71846_6_.capabilities.isCreativeMode)
		{
			p_71846_5_ |= 8;
			p_71846_1_.setBlockMetadataWithNotify(p_71846_2_, p_71846_3_, p_71846_4_, p_71846_5_, 4);
		}
		super.onBlockHarvested(p_71846_1_, p_71846_2_, p_71846_3_, p_71846_4_, p_71846_5_, p_71846_6_);
	}
	
	@Override public void onBlockPlacedBy(World p_71860_1_, int p_71860_2_, int p_71860_3_, int p_71860_4_, EntityLiving p_71860_5_, ItemStack p_71860_6_)
	{
		int var7 = MathHelper.floor_double(p_71860_5_.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		p_71860_1_.setBlockMetadataWithNotify(p_71860_2_, p_71860_3_, p_71860_4_, var7, 2);
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
		int var5 = p_71902_1_.getBlockMetadata(p_71902_2_, p_71902_3_, p_71902_4_) & 7;
		switch(var5)
		{
			case 1:
			default:
				setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
				break;
			case 2:
				setBlockBounds(0.25F, 0.25F, 0.5F, 0.75F, 0.75F, 1.0F);
				break;
			case 3:
				setBlockBounds(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.5F);
				break;
			case 4:
				setBlockBounds(0.5F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
				break;
			case 5:
				setBlockBounds(0.0F, 0.25F, 0.25F, 0.5F, 0.75F, 0.75F);
		}
	}
}
