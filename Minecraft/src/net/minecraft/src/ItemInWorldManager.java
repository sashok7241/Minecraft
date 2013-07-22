package net.minecraft.src;

public class ItemInWorldManager
{
	public World theWorld;
	public EntityPlayerMP thisPlayerMP;
	private EnumGameType gameType;
	private boolean isDestroyingBlock;
	private int initialDamage;
	private int partiallyDestroyedBlockX;
	private int partiallyDestroyedBlockY;
	private int partiallyDestroyedBlockZ;
	private int curblockDamage;
	private boolean receivedFinishDiggingPacket;
	private int posX;
	private int posY;
	private int posZ;
	private int field_73093_n;
	private int durabilityRemainingOnBlock;
	
	public ItemInWorldManager(World p_i3397_1_)
	{
		gameType = EnumGameType.NOT_SET;
		durabilityRemainingOnBlock = -1;
		theWorld = p_i3397_1_;
	}
	
	public boolean activateBlockOrUseItem(EntityPlayer p_73078_1_, World p_73078_2_, ItemStack p_73078_3_, int p_73078_4_, int p_73078_5_, int p_73078_6_, int p_73078_7_, float p_73078_8_, float p_73078_9_, float p_73078_10_)
	{
		int var11;
		if(!p_73078_1_.isSneaking() || p_73078_1_.getHeldItem() == null)
		{
			var11 = p_73078_2_.getBlockId(p_73078_4_, p_73078_5_, p_73078_6_);
			if(var11 > 0 && Block.blocksList[var11].onBlockActivated(p_73078_2_, p_73078_4_, p_73078_5_, p_73078_6_, p_73078_1_, p_73078_7_, p_73078_8_, p_73078_9_, p_73078_10_)) return true;
		}
		if(p_73078_3_ == null) return false;
		else if(isCreative())
		{
			var11 = p_73078_3_.getItemDamage();
			int var12 = p_73078_3_.stackSize;
			boolean var13 = p_73078_3_.tryPlaceItemIntoWorld(p_73078_1_, p_73078_2_, p_73078_4_, p_73078_5_, p_73078_6_, p_73078_7_, p_73078_8_, p_73078_9_, p_73078_10_);
			p_73078_3_.setItemDamage(var11);
			p_73078_3_.stackSize = var12;
			return var13;
		} else return p_73078_3_.tryPlaceItemIntoWorld(p_73078_1_, p_73078_2_, p_73078_4_, p_73078_5_, p_73078_6_, p_73078_7_, p_73078_8_, p_73078_9_, p_73078_10_);
	}
	
	public void cancelDestroyingBlock(int p_73073_1_, int p_73073_2_, int p_73073_3_)
	{
		isDestroyingBlock = false;
		theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, partiallyDestroyedBlockX, partiallyDestroyedBlockY, partiallyDestroyedBlockZ, -1);
	}
	
	public EnumGameType getGameType()
	{
		return gameType;
	}
	
	public void initializeGameType(EnumGameType p_73077_1_)
	{
		if(gameType == EnumGameType.NOT_SET)
		{
			gameType = p_73077_1_;
		}
		setGameType(gameType);
	}
	
	public boolean isCreative()
	{
		return gameType.isCreative();
	}
	
	public void onBlockClicked(int p_73074_1_, int p_73074_2_, int p_73074_3_, int p_73074_4_)
	{
		if(!gameType.isAdventure() || thisPlayerMP.isCurrentToolAdventureModeExempt(p_73074_1_, p_73074_2_, p_73074_3_))
		{
			if(isCreative())
			{
				if(!theWorld.extinguishFire((EntityPlayer) null, p_73074_1_, p_73074_2_, p_73074_3_, p_73074_4_))
				{
					tryHarvestBlock(p_73074_1_, p_73074_2_, p_73074_3_);
				}
			} else
			{
				theWorld.extinguishFire((EntityPlayer) null, p_73074_1_, p_73074_2_, p_73074_3_, p_73074_4_);
				initialDamage = curblockDamage;
				float var5 = 1.0F;
				int var6 = theWorld.getBlockId(p_73074_1_, p_73074_2_, p_73074_3_);
				if(var6 > 0)
				{
					Block.blocksList[var6].onBlockClicked(theWorld, p_73074_1_, p_73074_2_, p_73074_3_, thisPlayerMP);
					var5 = Block.blocksList[var6].getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, p_73074_1_, p_73074_2_, p_73074_3_);
				}
				if(var6 > 0 && var5 >= 1.0F)
				{
					tryHarvestBlock(p_73074_1_, p_73074_2_, p_73074_3_);
				} else
				{
					isDestroyingBlock = true;
					partiallyDestroyedBlockX = p_73074_1_;
					partiallyDestroyedBlockY = p_73074_2_;
					partiallyDestroyedBlockZ = p_73074_3_;
					int var7 = (int) (var5 * 10.0F);
					theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, p_73074_1_, p_73074_2_, p_73074_3_, var7);
					durabilityRemainingOnBlock = var7;
				}
			}
		}
	}
	
	private boolean removeBlock(int p_73079_1_, int p_73079_2_, int p_73079_3_)
	{
		Block var4 = Block.blocksList[theWorld.getBlockId(p_73079_1_, p_73079_2_, p_73079_3_)];
		int var5 = theWorld.getBlockMetadata(p_73079_1_, p_73079_2_, p_73079_3_);
		if(var4 != null)
		{
			var4.onBlockHarvested(theWorld, p_73079_1_, p_73079_2_, p_73079_3_, var5, thisPlayerMP);
		}
		boolean var6 = theWorld.setBlockToAir(p_73079_1_, p_73079_2_, p_73079_3_);
		if(var4 != null && var6)
		{
			var4.onBlockDestroyedByPlayer(theWorld, p_73079_1_, p_73079_2_, p_73079_3_, var5);
		}
		return var6;
	}
	
	public void setGameType(EnumGameType p_73076_1_)
	{
		gameType = p_73076_1_;
		p_73076_1_.configurePlayerCapabilities(thisPlayerMP.capabilities);
		thisPlayerMP.sendPlayerAbilities();
	}
	
	public void setWorld(WorldServer p_73080_1_)
	{
		theWorld = p_73080_1_;
	}
	
	public boolean tryHarvestBlock(int p_73084_1_, int p_73084_2_, int p_73084_3_)
	{
		if(gameType.isAdventure() && !thisPlayerMP.isCurrentToolAdventureModeExempt(p_73084_1_, p_73084_2_, p_73084_3_)) return false;
		else
		{
			int var4 = theWorld.getBlockId(p_73084_1_, p_73084_2_, p_73084_3_);
			int var5 = theWorld.getBlockMetadata(p_73084_1_, p_73084_2_, p_73084_3_);
			theWorld.playAuxSFXAtEntity(thisPlayerMP, 2001, p_73084_1_, p_73084_2_, p_73084_3_, var4 + (theWorld.getBlockMetadata(p_73084_1_, p_73084_2_, p_73084_3_) << 12));
			boolean var6 = removeBlock(p_73084_1_, p_73084_2_, p_73084_3_);
			if(isCreative())
			{
				thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(p_73084_1_, p_73084_2_, p_73084_3_, theWorld));
			} else
			{
				ItemStack var7 = thisPlayerMP.getCurrentEquippedItem();
				boolean var8 = thisPlayerMP.canHarvestBlock(Block.blocksList[var4]);
				if(var7 != null)
				{
					var7.onBlockDestroyed(theWorld, var4, p_73084_1_, p_73084_2_, p_73084_3_, thisPlayerMP);
					if(var7.stackSize == 0)
					{
						thisPlayerMP.destroyCurrentEquippedItem();
					}
				}
				if(var6 && var8)
				{
					Block.blocksList[var4].harvestBlock(theWorld, thisPlayerMP, p_73084_1_, p_73084_2_, p_73084_3_, var5);
				}
			}
			return var6;
		}
	}
	
	public boolean tryUseItem(EntityPlayer p_73085_1_, World p_73085_2_, ItemStack p_73085_3_)
	{
		int var4 = p_73085_3_.stackSize;
		int var5 = p_73085_3_.getItemDamage();
		ItemStack var6 = p_73085_3_.useItemRightClick(p_73085_2_, p_73085_1_);
		if(var6 == p_73085_3_ && (var6 == null || var6.stackSize == var4 && var6.getMaxItemUseDuration() <= 0 && var6.getItemDamage() == var5)) return false;
		else
		{
			p_73085_1_.inventory.mainInventory[p_73085_1_.inventory.currentItem] = var6;
			if(isCreative())
			{
				var6.stackSize = var4;
				if(var6.isItemStackDamageable())
				{
					var6.setItemDamage(var5);
				}
			}
			if(var6.stackSize == 0)
			{
				p_73085_1_.inventory.mainInventory[p_73085_1_.inventory.currentItem] = null;
			}
			if(!p_73085_1_.isUsingItem())
			{
				((EntityPlayerMP) p_73085_1_).sendContainerToPlayer(p_73085_1_.inventoryContainer);
			}
			return true;
		}
	}
	
	public void uncheckedTryHarvestBlock(int p_73082_1_, int p_73082_2_, int p_73082_3_)
	{
		if(p_73082_1_ == partiallyDestroyedBlockX && p_73082_2_ == partiallyDestroyedBlockY && p_73082_3_ == partiallyDestroyedBlockZ)
		{
			int var4 = curblockDamage - initialDamage;
			int var5 = theWorld.getBlockId(p_73082_1_, p_73082_2_, p_73082_3_);
			if(var5 != 0)
			{
				Block var6 = Block.blocksList[var5];
				float var7 = var6.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, p_73082_1_, p_73082_2_, p_73082_3_) * (var4 + 1);
				if(var7 >= 0.7F)
				{
					isDestroyingBlock = false;
					theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, p_73082_1_, p_73082_2_, p_73082_3_, -1);
					tryHarvestBlock(p_73082_1_, p_73082_2_, p_73082_3_);
				} else if(!receivedFinishDiggingPacket)
				{
					isDestroyingBlock = false;
					receivedFinishDiggingPacket = true;
					posX = p_73082_1_;
					posY = p_73082_2_;
					posZ = p_73082_3_;
					field_73093_n = initialDamage;
				}
			}
		}
	}
	
	public void updateBlockRemoving()
	{
		++curblockDamage;
		int var1;
		float var4;
		int var5;
		if(receivedFinishDiggingPacket)
		{
			var1 = curblockDamage - field_73093_n;
			int var2 = theWorld.getBlockId(posX, posY, posZ);
			if(var2 == 0)
			{
				receivedFinishDiggingPacket = false;
			} else
			{
				Block var3 = Block.blocksList[var2];
				var4 = var3.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, posX, posY, posZ) * (var1 + 1);
				var5 = (int) (var4 * 10.0F);
				if(var5 != durabilityRemainingOnBlock)
				{
					theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, posX, posY, posZ, var5);
					durabilityRemainingOnBlock = var5;
				}
				if(var4 >= 1.0F)
				{
					receivedFinishDiggingPacket = false;
					tryHarvestBlock(posX, posY, posZ);
				}
			}
		} else if(isDestroyingBlock)
		{
			var1 = theWorld.getBlockId(partiallyDestroyedBlockX, partiallyDestroyedBlockY, partiallyDestroyedBlockZ);
			Block var6 = Block.blocksList[var1];
			if(var6 == null)
			{
				theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, partiallyDestroyedBlockX, partiallyDestroyedBlockY, partiallyDestroyedBlockZ, -1);
				durabilityRemainingOnBlock = -1;
				isDestroyingBlock = false;
			} else
			{
				int var7 = curblockDamage - initialDamage;
				var4 = var6.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, partiallyDestroyedBlockX, partiallyDestroyedBlockY, partiallyDestroyedBlockZ) * (var7 + 1);
				var5 = (int) (var4 * 10.0F);
				if(var5 != durabilityRemainingOnBlock)
				{
					theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, partiallyDestroyedBlockX, partiallyDestroyedBlockY, partiallyDestroyedBlockZ, var5);
					durabilityRemainingOnBlock = var5;
				}
			}
		}
	}
}
