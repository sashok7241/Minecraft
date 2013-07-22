package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerMP
{
	private final Minecraft mc;
	private final NetClientHandler netClientHandler;
	private int currentBlockX = -1;
	private int currentBlockY = -1;
	private int currentblockZ = -1;
	private ItemStack field_85183_f = null;
	private float curBlockDamageMP = 0.0F;
	private float stepSoundTickCounter = 0.0F;
	private int blockHitDelay = 0;
	private boolean isHittingBlock = false;
	private EnumGameType currentGameType;
	private int currentPlayerItem;
	
	public PlayerControllerMP(Minecraft par1Minecraft, NetClientHandler par2NetClientHandler)
	{
		currentGameType = EnumGameType.SURVIVAL;
		currentPlayerItem = 0;
		mc = par1Minecraft;
		netClientHandler = par2NetClientHandler;
	}
	
	public void attackEntity(EntityPlayer par1EntityPlayer, Entity par2Entity)
	{
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet7UseEntity(par1EntityPlayer.entityId, par2Entity.entityId, 1));
		par1EntityPlayer.attackTargetEntityWithCurrentItem(par2Entity);
	}
	
	public void clickBlock(int par1, int par2, int par3, int par4)
	{
		if(!currentGameType.isAdventure() || mc.thePlayer.isCurrentToolAdventureModeExempt(par1, par2, par3))
		{
			if(currentGameType.isCreative())
			{
				netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
				clickBlockCreative(mc, this, par1, par2, par3, par4);
				blockHitDelay = 5;
			} else if(!isHittingBlock || !sameToolAndBlock(par1, par2, par3))
			{
				if(isHittingBlock)
				{
					netClientHandler.addToSendQueue(new Packet14BlockDig(1, currentBlockX, currentBlockY, currentblockZ, par4));
				}
				netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
				int var5 = mc.theWorld.getBlockId(par1, par2, par3);
				if(var5 > 0 && curBlockDamageMP == 0.0F)
				{
					Block.blocksList[var5].onBlockClicked(mc.theWorld, par1, par2, par3, mc.thePlayer);
				}
				if(var5 > 0 && Block.blocksList[var5].getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, par1, par2, par3) >= 1.0F)
				{
					onPlayerDestroyBlock(par1, par2, par3, par4);
				} else
				{
					isHittingBlock = true;
					currentBlockX = par1;
					currentBlockY = par2;
					currentblockZ = par3;
					field_85183_f = mc.thePlayer.getHeldItem();
					curBlockDamageMP = 0.0F;
					stepSoundTickCounter = 0.0F;
					mc.theWorld.destroyBlockInWorldPartially(mc.thePlayer.entityId, currentBlockX, currentBlockY, currentblockZ, (int) (curBlockDamageMP * 10.0F) - 1);
				}
			}
		}
	}
	
	public boolean enableEverythingIsScrewedUpMode()
	{
		return false;
	}
	
	public boolean extendedReach()
	{
		return currentGameType.isCreative();
	}
	
	public void flipPlayer(EntityPlayer par1EntityPlayer)
	{
		par1EntityPlayer.rotationYaw = -180.0F;
	}
	
	public void func_78752_a(ItemStack par1ItemStack)
	{
		if(currentGameType.isCreative() && par1ItemStack != null)
		{
			netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(-1, par1ItemStack));
		}
	}
	
	public EntityClientPlayerMP func_78754_a(World par1World)
	{
		return new EntityClientPlayerMP(mc, par1World, mc.session, netClientHandler);
	}
	
	public boolean func_78763_f()
	{
		return true;
	}
	
	public boolean func_78768_b(EntityPlayer par1EntityPlayer, Entity par2Entity)
	{
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet7UseEntity(par1EntityPlayer.entityId, par2Entity.entityId, 0));
		return par1EntityPlayer.interactWith(par2Entity);
	}
	
	public float getBlockReachDistance()
	{
		return currentGameType.isCreative() ? 5.0F : 4.5F;
	}
	
	public boolean isInCreativeMode()
	{
		return currentGameType.isCreative();
	}
	
	public boolean isNotCreative()
	{
		return !currentGameType.isCreative();
	}
	
	public void onPlayerDamageBlock(int par1, int par2, int par3, int par4)
	{
		syncCurrentPlayItem();
		if(blockHitDelay > 0)
		{
			--blockHitDelay;
		} else if(currentGameType.isCreative())
		{
			blockHitDelay = 5;
			netClientHandler.addToSendQueue(new Packet14BlockDig(0, par1, par2, par3, par4));
			clickBlockCreative(mc, this, par1, par2, par3, par4);
		} else
		{
			if(sameToolAndBlock(par1, par2, par3))
			{
				int var5 = mc.theWorld.getBlockId(par1, par2, par3);
				if(var5 == 0)
				{
					isHittingBlock = false;
					return;
				}
				Block var6 = Block.blocksList[var5];
				curBlockDamageMP += var6.getPlayerRelativeBlockHardness(mc.thePlayer, mc.thePlayer.worldObj, par1, par2, par3);
				if(stepSoundTickCounter % 4.0F == 0.0F && var6 != null)
				{
					mc.sndManager.playSound(var6.stepSound.getStepSound(), par1 + 0.5F, par2 + 0.5F, par3 + 0.5F, (var6.stepSound.getVolume() + 1.0F) / 8.0F, var6.stepSound.getPitch() * 0.5F);
				}
				++stepSoundTickCounter;
				if(curBlockDamageMP >= 1.0F)
				{
					isHittingBlock = false;
					netClientHandler.addToSendQueue(new Packet14BlockDig(2, par1, par2, par3, par4));
					onPlayerDestroyBlock(par1, par2, par3, par4);
					curBlockDamageMP = 0.0F;
					stepSoundTickCounter = 0.0F;
					blockHitDelay = 5;
				}
				mc.theWorld.destroyBlockInWorldPartially(mc.thePlayer.entityId, currentBlockX, currentBlockY, currentblockZ, (int) (curBlockDamageMP * 10.0F) - 1);
			} else
			{
				clickBlock(par1, par2, par3, par4);
			}
		}
	}
	
	public boolean onPlayerDestroyBlock(int par1, int par2, int par3, int par4)
	{
		if(currentGameType.isAdventure() && !mc.thePlayer.isCurrentToolAdventureModeExempt(par1, par2, par3)) return false;
		else
		{
			WorldClient var5 = mc.theWorld;
			Block var6 = Block.blocksList[var5.getBlockId(par1, par2, par3)];
			if(var6 == null) return false;
			else
			{
				var5.playAuxSFX(2001, par1, par2, par3, var6.blockID + (var5.getBlockMetadata(par1, par2, par3) << 12));
				int var7 = var5.getBlockMetadata(par1, par2, par3);
				boolean var8 = var5.setBlockToAir(par1, par2, par3);
				if(var8)
				{
					var6.onBlockDestroyedByPlayer(var5, par1, par2, par3, var7);
				}
				currentBlockY = -1;
				if(!currentGameType.isCreative())
				{
					ItemStack var9 = mc.thePlayer.getCurrentEquippedItem();
					if(var9 != null)
					{
						var9.onBlockDestroyed(var5, var6.blockID, par1, par2, par3, mc.thePlayer);
						if(var9.stackSize == 0)
						{
							mc.thePlayer.destroyCurrentEquippedItem();
						}
					}
				}
				return var8;
			}
		}
	}
	
	public boolean onPlayerRightClick(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, Vec3 par8Vec3)
	{
		syncCurrentPlayItem();
		float var9 = (float) par8Vec3.xCoord - par4;
		float var10 = (float) par8Vec3.yCoord - par5;
		float var11 = (float) par8Vec3.zCoord - par6;
		boolean var12 = false;
		int var13;
		if(!par1EntityPlayer.isSneaking() || par1EntityPlayer.getHeldItem() == null)
		{
			var13 = par2World.getBlockId(par4, par5, par6);
			if(var13 > 0 && Block.blocksList[var13].onBlockActivated(par2World, par4, par5, par6, par1EntityPlayer, par7, var9, var10, var11))
			{
				var12 = true;
			}
		}
		if(!var12 && par3ItemStack != null && par3ItemStack.getItem() instanceof ItemBlock)
		{
			ItemBlock var16 = (ItemBlock) par3ItemStack.getItem();
			if(!var16.canPlaceItemBlockOnSide(par2World, par4, par5, par6, par7, par1EntityPlayer, par3ItemStack)) return false;
		}
		netClientHandler.addToSendQueue(new Packet15Place(par4, par5, par6, par7, par1EntityPlayer.inventory.getCurrentItem(), var9, var10, var11));
		if(var12) return true;
		else if(par3ItemStack == null) return false;
		else if(currentGameType.isCreative())
		{
			var13 = par3ItemStack.getItemDamage();
			int var14 = par3ItemStack.stackSize;
			boolean var15 = par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
			par3ItemStack.setItemDamage(var13);
			par3ItemStack.stackSize = var14;
			return var15;
		} else return par3ItemStack.tryPlaceItemIntoWorld(par1EntityPlayer, par2World, par4, par5, par6, par7, var9, var10, var11);
	}
	
	public void onStoppedUsingItem(EntityPlayer par1EntityPlayer)
	{
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet14BlockDig(5, 0, 0, 0, 255));
		par1EntityPlayer.stopUsingItem();
	}
	
	public void resetBlockRemoving()
	{
		if(isHittingBlock)
		{
			netClientHandler.addToSendQueue(new Packet14BlockDig(1, currentBlockX, currentBlockY, currentblockZ, -1));
		}
		isHittingBlock = false;
		curBlockDamageMP = 0.0F;
		mc.theWorld.destroyBlockInWorldPartially(mc.thePlayer.entityId, currentBlockX, currentBlockY, currentblockZ, -1);
	}
	
	private boolean sameToolAndBlock(int par1, int par2, int par3)
	{
		ItemStack var4 = mc.thePlayer.getHeldItem();
		boolean var5 = field_85183_f == null && var4 == null;
		if(field_85183_f != null && var4 != null)
		{
			var5 = var4.itemID == field_85183_f.itemID && ItemStack.areItemStackTagsEqual(var4, field_85183_f) && (var4.isItemStackDamageable() || var4.getItemDamage() == field_85183_f.getItemDamage());
		}
		return par1 == currentBlockX && par2 == currentBlockY && par3 == currentblockZ && var5;
	}
	
	public void sendEnchantPacket(int par1, int par2)
	{
		netClientHandler.addToSendQueue(new Packet108EnchantItem(par1, par2));
	}
	
	public void sendSlotPacket(ItemStack par1ItemStack, int par2)
	{
		if(currentGameType.isCreative())
		{
			netClientHandler.addToSendQueue(new Packet107CreativeSetSlot(par2, par1ItemStack));
		}
	}
	
	public boolean sendUseItem(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
	{
		syncCurrentPlayItem();
		netClientHandler.addToSendQueue(new Packet15Place(-1, -1, -1, 255, par1EntityPlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
		int var4 = par3ItemStack.stackSize;
		ItemStack var5 = par3ItemStack.useItemRightClick(par2World, par1EntityPlayer);
		if(var5 == par3ItemStack && (var5 == null || var5.stackSize == var4)) return false;
		else
		{
			par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = var5;
			if(var5.stackSize == 0)
			{
				par1EntityPlayer.inventory.mainInventory[par1EntityPlayer.inventory.currentItem] = null;
			}
			return true;
		}
	}
	
	public void setGameType(EnumGameType par1EnumGameType)
	{
		currentGameType = par1EnumGameType;
		currentGameType.configurePlayerCapabilities(mc.thePlayer.capabilities);
	}
	
	public void setPlayerCapabilities(EntityPlayer par1EntityPlayer)
	{
		currentGameType.configurePlayerCapabilities(par1EntityPlayer.capabilities);
	}
	
	public boolean shouldDrawHUD()
	{
		return currentGameType.isSurvivalOrAdventure();
	}
	
	private void syncCurrentPlayItem()
	{
		int var1 = mc.thePlayer.inventory.currentItem;
		if(var1 != currentPlayerItem)
		{
			currentPlayerItem = var1;
			netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(currentPlayerItem));
		}
	}
	
	public void updateController()
	{
		syncCurrentPlayItem();
		mc.sndManager.playRandomMusicIfReady();
	}
	
	public ItemStack windowClick(int par1, int par2, int par3, int par4, EntityPlayer par5EntityPlayer)
	{
		short var6 = par5EntityPlayer.openContainer.getNextTransactionID(par5EntityPlayer.inventory);
		ItemStack var7 = par5EntityPlayer.openContainer.slotClick(par2, par3, par4, par5EntityPlayer);
		netClientHandler.addToSendQueue(new Packet102WindowClick(par1, par2, par3, par4, var7, var6));
		return var7;
	}
	
	public static void clickBlockCreative(Minecraft par0Minecraft, PlayerControllerMP par1PlayerControllerMP, int par2, int par3, int par4, int par5)
	{
		if(!par0Minecraft.theWorld.extinguishFire(par0Minecraft.thePlayer, par2, par3, par4, par5))
		{
			par1PlayerControllerMP.onPlayerDestroyBlock(par2, par3, par4, par5);
		}
	}
}
