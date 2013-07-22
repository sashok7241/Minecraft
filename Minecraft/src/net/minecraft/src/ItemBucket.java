package net.minecraft.src;

public class ItemBucket extends Item
{
	private int isFull;
	
	public ItemBucket(int p_i3625_1_, int p_i3625_2_)
	{
		super(p_i3625_1_);
		maxStackSize = 1;
		isFull = p_i3625_2_;
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		float var4 = 1.0F;
		double var5 = p_77659_3_.prevPosX + (p_77659_3_.posX - p_77659_3_.prevPosX) * var4;
		double var7 = p_77659_3_.prevPosY + (p_77659_3_.posY - p_77659_3_.prevPosY) * var4 + 1.62D - p_77659_3_.yOffset;
		double var9 = p_77659_3_.prevPosZ + (p_77659_3_.posZ - p_77659_3_.prevPosZ) * var4;
		boolean var11 = isFull == 0;
		MovingObjectPosition var12 = getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, var11);
		if(var12 == null) return p_77659_1_;
		else
		{
			if(var12.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var13 = var12.blockX;
				int var14 = var12.blockY;
				int var15 = var12.blockZ;
				if(!p_77659_2_.canMineBlock(p_77659_3_, var13, var14, var15)) return p_77659_1_;
				if(isFull == 0)
				{
					if(!p_77659_3_.canPlayerEdit(var13, var14, var15, var12.sideHit, p_77659_1_)) return p_77659_1_;
					if(p_77659_2_.getBlockMaterial(var13, var14, var15) == Material.water && p_77659_2_.getBlockMetadata(var13, var14, var15) == 0)
					{
						p_77659_2_.setBlockToAir(var13, var14, var15);
						if(p_77659_3_.capabilities.isCreativeMode) return p_77659_1_;
						if(--p_77659_1_.stackSize <= 0) return new ItemStack(Item.bucketWater);
						if(!p_77659_3_.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater)))
						{
							p_77659_3_.dropPlayerItem(new ItemStack(Item.bucketWater.itemID, 1, 0));
						}
						return p_77659_1_;
					}
					if(p_77659_2_.getBlockMaterial(var13, var14, var15) == Material.lava && p_77659_2_.getBlockMetadata(var13, var14, var15) == 0)
					{
						p_77659_2_.setBlockToAir(var13, var14, var15);
						if(p_77659_3_.capabilities.isCreativeMode) return p_77659_1_;
						if(--p_77659_1_.stackSize <= 0) return new ItemStack(Item.bucketLava);
						if(!p_77659_3_.inventory.addItemStackToInventory(new ItemStack(Item.bucketLava)))
						{
							p_77659_3_.dropPlayerItem(new ItemStack(Item.bucketLava.itemID, 1, 0));
						}
						return p_77659_1_;
					}
				} else
				{
					if(isFull < 0) return new ItemStack(Item.bucketEmpty);
					if(var12.sideHit == 0)
					{
						--var14;
					}
					if(var12.sideHit == 1)
					{
						++var14;
					}
					if(var12.sideHit == 2)
					{
						--var15;
					}
					if(var12.sideHit == 3)
					{
						++var15;
					}
					if(var12.sideHit == 4)
					{
						--var13;
					}
					if(var12.sideHit == 5)
					{
						++var13;
					}
					if(!p_77659_3_.canPlayerEdit(var13, var14, var15, var12.sideHit, p_77659_1_)) return p_77659_1_;
					if(tryPlaceContainedLiquid(p_77659_2_, var5, var7, var9, var13, var14, var15) && !p_77659_3_.capabilities.isCreativeMode) return new ItemStack(Item.bucketEmpty);
				}
			} else if(isFull == 0 && var12.entityHit instanceof EntityCow) return new ItemStack(Item.bucketMilk);
			return p_77659_1_;
		}
	}
	
	public boolean tryPlaceContainedLiquid(World p_77875_1_, double p_77875_2_, double p_77875_4_, double p_77875_6_, int p_77875_8_, int p_77875_9_, int p_77875_10_)
	{
		if(isFull <= 0) return false;
		else if(!p_77875_1_.isAirBlock(p_77875_8_, p_77875_9_, p_77875_10_) && p_77875_1_.getBlockMaterial(p_77875_8_, p_77875_9_, p_77875_10_).isSolid()) return false;
		else
		{
			if(p_77875_1_.provider.isHellWorld && isFull == Block.waterMoving.blockID)
			{
				p_77875_1_.playSoundEffect(p_77875_2_ + 0.5D, p_77875_4_ + 0.5D, p_77875_6_ + 0.5D, "random.fizz", 0.5F, 2.6F + (p_77875_1_.rand.nextFloat() - p_77875_1_.rand.nextFloat()) * 0.8F);
				for(int var11 = 0; var11 < 8; ++var11)
				{
					p_77875_1_.spawnParticle("largesmoke", p_77875_8_ + Math.random(), p_77875_9_ + Math.random(), p_77875_10_ + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			} else
			{
				p_77875_1_.setBlock(p_77875_8_, p_77875_9_, p_77875_10_, isFull, 0, 3);
			}
			return true;
		}
	}
}
