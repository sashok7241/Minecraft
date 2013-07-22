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
	
	public ItemInWorldManager(World par1World)
	{
		gameType = EnumGameType.NOT_SET;
		durabilityRemainingOnBlock = -1;
		theWorld = par1World;
	}
	
	public boolean activateBlockOrUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		int var11;
		if(!par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null)
		{
			var11 = par2World.getBlockId(par4, par5, par6);
			if(var11 > 0 && Block.blocksList[var11].onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, par8, par9, par10)) return true;
		}
		if(par3ItemStack == null) return false;
		else if(isCreative())
		{
			var11 = par3ItemStack.getItemDamage();
			int var12 = par3ItemStack.stackSize;
			boolean var13 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
			par3ItemStack.setItemDamage(var11);
			par3ItemStack.stackSize = var12;
			return var13;
		} else return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, par8, par9, par10);
	}
	
	public void cancelDestroyingBlock(int par1, int par2, int par3)
	{
		isDestroyingBlock = false;
		theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, partiallyDestroyedBlockX, partiallyDestroyedBlockY, partiallyDestroyedBlockZ, -1);
	}
	
	public EnumGameType getGameType()
	{
		return gameType;
	}
	
	public void initializeGameType(EnumGameType par1EnumGameType)
	{
		if(gameType == EnumGameType.NOT_SET)
		{
			gameType = par1EnumGameType;
		}
		setGameType(gameType);
	}
	
	public boolean isCreative()
	{
		return gameType.isCreative();
	}
	
	public void onBlockClicked(int par1, int par2, int par3, int par4)
	{
		if(!gameType.isAdventure() || thisPlayerMP.isCurrentToolAdventureModeExempt(par1, par2, par3))
		{
			if(isCreative())
			{
				if(!theWorld.extinguishFire((EntityPlayer) null, par1, par2, par3, par4))
				{
					tryHarvestBlock(par1, par2, par3);
				}
			} else
			{
				theWorld.extinguishFire((EntityPlayer) null, par1, par2, par3, par4);
				initialDamage = curblockDamage;
				float var5 = 1.0F;
				int var6 = theWorld.getBlockId(par1, par2, par3);
				if(var6 > 0)
				{
					Block.blocksList[var6].onBlockClicked(theWorld, par1, par2, par3, thisPlayerMP);
					var5 = Block.blocksList[var6].getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, par1, par2, par3);
				}
				if(var6 > 0 && var5 >= 1.0F)
				{
					tryHarvestBlock(par1, par2, par3);
				} else
				{
					isDestroyingBlock = true;
					partiallyDestroyedBlockX = par1;
					partiallyDestroyedBlockY = par2;
					partiallyDestroyedBlockZ = par3;
					int var7 = (int) (var5 * 10.0F);
					theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, par1, par2, par3, var7);
					durabilityRemainingOnBlock = var7;
				}
			}
		}
	}
	
	private boolean removeBlock(int par1, int par2, int par3)
	{
		Block var4 = Block.blocksList[theWorld.getBlockId(par1, par2, par3)];
		int var5 = theWorld.getBlockMetadata(par1, par2, par3);
		if(var4 != null)
		{
			var4.onBlockHarvested(theWorld, par1, par2, par3, var5, thisPlayerMP);
		}
		boolean var6 = theWorld.setBlockToAir(par1, par2, par3);
		if(var4 != null && var6)
		{
			var4.onBlockDestroyedByPlayer(theWorld, par1, par2, par3, var5);
		}
		return var6;
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
		gameType = par1EnumGameType;
		par1EnumGameType.configurePlayerCapabilities(thisPlayerMP.capabilities);
		thisPlayerMP.sendPlayerAbilities();
	}
	
	public void setWorld(WorldServer par1WorldServer)
	{
		theWorld = par1WorldServer;
	}
	
	public boolean tryHarvestBlock(int par1, int par2, int par3)
	{
		if(gameType.isAdventure() && !thisPlayerMP.isCurrentToolAdventureModeExempt(par1, par2, par3)) return false;
		else
		{
			int var4 = theWorld.getBlockId(par1, par2, par3);
			int var5 = theWorld.getBlockMetadata(par1, par2, par3);
			theWorld.playAuxSFXAtEntity(thisPlayerMP, 2001, par1, par2, par3, var4 + (theWorld.getBlockMetadata(par1, par2, par3) << 12));
			boolean var6 = removeBlock(par1, par2, par3);
			if(isCreative())
			{
				thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet53BlockChange(par1, par2, par3, theWorld));
			} else
			{
				ItemStack var7 = thisPlayerMP.getCurrentEquippedItem();
				boolean var8 = thisPlayerMP.canHarvestBlock(Block.blocksList[var4]);
				if(var7 != null)
				{
					var7.onBlockDestroyed(theWorld, var4, par1, par2, par3, thisPlayerMP);
					if(var7.stackSize == 0)
					{
						thisPlayerMP.destroyCurrentEquippedItem();
					}
				}
				if(var6 && var8)
				{
					Block.blocksList[var4].harvestBlock(theWorld, thisPlayerMP, par1, par2, par3, var5);
				}
			}
			return var6;
		}
	}
	
	public boolean tryUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
	{
		int var4 = par3ItemStack.stackSize;
		int var5 = par3ItemStack.getItemDamage();
		ItemStack var6 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
		if(var6 == par3ItemStack && (var6 == null || var6.stackSize == var4 && var6.getMaxItemUseDuration() <= 0 && var6.getItemDamage() == var5)) return false;
		else
		{
			par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var6;
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
				par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
			}
			if(!par1EntityPlayer.isUsingItem())
			{
				((EntityPlayerMP) par1EntityPlayer).sendContainerToPlayer(par1EntityPlayer.inventoryContainer);
			}
			return true;
		}
	}
	
	public void uncheckedTryHarvestBlock(int par1, int par2, int par3)
	{
		if(par1 == partiallyDestroyedBlockX && par2 == partiallyDestroyedBlockY && par3 == partiallyDestroyedBlockZ)
		{
			int var4 = curblockDamage - initialDamage;
			int var5 = theWorld.getBlockId(par1, par2, par3);
			if(var5 != 0)
			{
				Block var6 = Block.blocksList[var5];
				float var7 = var6.getPlayerRelativeBlockHardness(thisPlayerMP, thisPlayerMP.worldObj, par1, par2, par3) * (var4 + 1);
				if(var7 >= 0.7F)
				{
					isDestroyingBlock = false;
					theWorld.destroyBlockInWorldPartially(thisPlayerMP.entityId, par1, par2, par3, -1);
					tryHarvestBlock(par1, par2, par3);
				} else if(!receivedFinishDiggingPacket)
				{
					isDestroyingBlock = false;
					receivedFinishDiggingPacket = true;
					posX = par1;
					posY = par2;
					posZ = par3;
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
