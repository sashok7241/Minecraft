package net.minecraft.src;

public class ItemEnderEye extends Item
{
	public ItemEnderEye(int p_i3647_1_)
	{
		super(p_i3647_1_);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		MovingObjectPosition var4 = getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, false);
		if(var4 != null && var4.typeOfHit == EnumMovingObjectType.TILE)
		{
			int var5 = p_77659_2_.getBlockId(var4.blockX, var4.blockY, var4.blockZ);
			if(var5 == Block.endPortalFrame.blockID) return p_77659_1_;
		}
		if(!p_77659_2_.isRemote)
		{
			ChunkPosition var7 = p_77659_2_.findClosestStructure("Stronghold", (int) p_77659_3_.posX, (int) p_77659_3_.posY, (int) p_77659_3_.posZ);
			if(var7 != null)
			{
				EntityEnderEye var6 = new EntityEnderEye(p_77659_2_, p_77659_3_.posX, p_77659_3_.posY + 1.62D - p_77659_3_.yOffset, p_77659_3_.posZ);
				var6.moveTowards(var7.x, var7.y, var7.z);
				p_77659_2_.spawnEntityInWorld(var6);
				p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				p_77659_2_.playAuxSFXAtEntity((EntityPlayer) null, 1002, (int) p_77659_3_.posX, (int) p_77659_3_.posY, (int) p_77659_3_.posZ, 0);
				if(!p_77659_3_.capabilities.isCreativeMode)
				{
					--p_77659_1_.stackSize;
				}
			}
		}
		return p_77659_1_;
	}
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
		int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
		if(p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && var11 == Block.endPortalFrame.blockID && !BlockEndPortalFrame.isEnderEyeInserted(var12))
		{
			if(p_77648_3_.isRemote) return true;
			else
			{
				p_77648_3_.setBlockMetadataWithNotify(p_77648_4_, p_77648_5_, p_77648_6_, var12 + 4, 2);
				--p_77648_1_.stackSize;
				int var13;
				for(var13 = 0; var13 < 16; ++var13)
				{
					double var14 = p_77648_4_ + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F;
					double var16 = p_77648_5_ + 0.8125F;
					double var18 = p_77648_6_ + (5.0F + itemRand.nextFloat() * 6.0F) / 16.0F;
					double var20 = 0.0D;
					double var22 = 0.0D;
					double var24 = 0.0D;
					p_77648_3_.spawnParticle("smoke", var14, var16, var18, var20, var22, var24);
				}
				var13 = var12 & 3;
				int var26 = 0;
				int var15 = 0;
				boolean var27 = false;
				boolean var17 = true;
				int var28 = Direction.rotateRight[var13];
				int var19;
				int var21;
				int var23;
				int var29;
				int var30;
				for(var19 = -2; var19 <= 2; ++var19)
				{
					var29 = p_77648_4_ + Direction.offsetX[var28] * var19;
					var21 = p_77648_6_ + Direction.offsetZ[var28] * var19;
					var30 = p_77648_3_.getBlockId(var29, p_77648_5_, var21);
					if(var30 == Block.endPortalFrame.blockID)
					{
						var23 = p_77648_3_.getBlockMetadata(var29, p_77648_5_, var21);
						if(!BlockEndPortalFrame.isEnderEyeInserted(var23))
						{
							var17 = false;
							break;
						}
						var15 = var19;
						if(!var27)
						{
							var26 = var19;
							var27 = true;
						}
					}
				}
				if(var17 && var15 == var26 + 2)
				{
					for(var19 = var26; var19 <= var15; ++var19)
					{
						var29 = p_77648_4_ + Direction.offsetX[var28] * var19;
						var21 = p_77648_6_ + Direction.offsetZ[var28] * var19;
						var29 += Direction.offsetX[var13] * 4;
						var21 += Direction.offsetZ[var13] * 4;
						var30 = p_77648_3_.getBlockId(var29, p_77648_5_, var21);
						var23 = p_77648_3_.getBlockMetadata(var29, p_77648_5_, var21);
						if(var30 != Block.endPortalFrame.blockID || !BlockEndPortalFrame.isEnderEyeInserted(var23))
						{
							var17 = false;
							break;
						}
					}
					for(var19 = var26 - 1; var19 <= var15 + 1; var19 += 4)
					{
						for(var29 = 1; var29 <= 3; ++var29)
						{
							var21 = p_77648_4_ + Direction.offsetX[var28] * var19;
							var30 = p_77648_6_ + Direction.offsetZ[var28] * var19;
							var21 += Direction.offsetX[var13] * var29;
							var30 += Direction.offsetZ[var13] * var29;
							var23 = p_77648_3_.getBlockId(var21, p_77648_5_, var30);
							int var31 = p_77648_3_.getBlockMetadata(var21, p_77648_5_, var30);
							if(var23 != Block.endPortalFrame.blockID || !BlockEndPortalFrame.isEnderEyeInserted(var31))
							{
								var17 = false;
								break;
							}
						}
					}
					if(var17)
					{
						for(var19 = var26; var19 <= var15; ++var19)
						{
							for(var29 = 1; var29 <= 3; ++var29)
							{
								var21 = p_77648_4_ + Direction.offsetX[var28] * var19;
								var30 = p_77648_6_ + Direction.offsetZ[var28] * var19;
								var21 += Direction.offsetX[var13] * var29;
								var30 += Direction.offsetZ[var13] * var29;
								p_77648_3_.setBlock(var21, p_77648_5_, var30, Block.endPortal.blockID, 0, 2);
							}
						}
					}
				}
				return true;
			}
		} else return false;
	}
}
