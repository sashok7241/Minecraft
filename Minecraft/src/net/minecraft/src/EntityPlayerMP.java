package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
	private String translator = "en_US";
	public NetServerHandler playerNetServerHandler;
	public MinecraftServer mcServer;
	public ItemInWorldManager theItemInWorldManager;
	public double managedPosX;
	public double managedPosZ;
	public final List loadedChunks = new LinkedList();
	public final List destroyedItemsNetCache = new LinkedList();
	private float field_130068_bO = Float.MIN_VALUE;
	private float lastHealth = -1.0E8F;
	private int lastFoodLevel = -99999999;
	private boolean wasHungry = true;
	private int lastExperience = -99999999;
	private int initialInvulnerability = 60;
	private int renderDistance;
	private int chatVisibility;
	private boolean chatColours = true;
	private int currentWindowId;
	public boolean playerInventoryBeingManipulated;
	public int ping;
	public boolean playerConqueredTheEnd;
	
	public EntityPlayerMP(MinecraftServer par1MinecraftServer, World par2World, String par3Str, ItemInWorldManager par4ItemInWorldManager)
	{
		super(par2World, par3Str);
		par4ItemInWorldManager.thisPlayerMP = this;
		theItemInWorldManager = par4ItemInWorldManager;
		renderDistance = par1MinecraftServer.getConfigurationManager().getViewDistance();
		ChunkCoordinates var5 = par2World.getSpawnPoint();
		int var6 = var5.posX;
		int var7 = var5.posZ;
		int var8 = var5.posY;
		if(!par2World.provider.hasNoSky && par2World.getWorldInfo().getGameType() != EnumGameType.ADVENTURE)
		{
			int var9 = Math.max(5, par1MinecraftServer.getSpawnProtectionSize() - 6);
			var6 += rand.nextInt(var9 * 2) - var9;
			var7 += rand.nextInt(var9 * 2) - var9;
			var8 = par2World.getTopSolidOrLiquidBlock(var6, var7);
		}
		mcServer = par1MinecraftServer;
		stepHeight = 0.0F;
		yOffset = 0.0F;
		setLocationAndAngles(var6 + 0.5D, var8, var7 + 0.5D, 0.0F, 0.0F);
		while(!par2World.getCollidingBoundingBoxes(this, boundingBox).isEmpty())
		{
			setPosition(posX, posY + 1.0D, posZ);
		}
	}
	
	@Override public void addChatMessage(String par1Str)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(ChatMessageComponent.func_111077_e(par1Str)));
	}
	
	@Override public void addExperienceLevel(int par1)
	{
		super.addExperienceLevel(par1);
		lastExperience = -1;
	}
	
	public void addSelfToInternalCraftingInventory()
	{
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void addStat(StatBase par1StatBase, int par2)
	{
		if(par1StatBase != null)
		{
			if(!par1StatBase.isIndependent)
			{
				playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, par2));
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			boolean var3 = mcServer.isDedicatedServer() && mcServer.isPVPEnabled() && "fall".equals(par1DamageSource.damageType);
			if(!var3 && initialInvulnerability > 0 && par1DamageSource != DamageSource.outOfWorld) return false;
			else
			{
				if(par1DamageSource instanceof EntityDamageSource)
				{
					Entity var4 = par1DamageSource.getEntity();
					if(var4 instanceof EntityPlayer && !func_96122_a((EntityPlayer) var4)) return false;
					if(var4 instanceof EntityArrow)
					{
						EntityArrow var5 = (EntityArrow) var4;
						if(var5.shootingEntity instanceof EntityPlayer && !func_96122_a((EntityPlayer) var5.shootingEntity)) return false;
					}
				}
				return super.attackEntityFrom(par1DamageSource, par2);
			}
		}
	}
	
	@Override public boolean canCommandSenderUseCommand(int par1, String par2Str)
	{
		return "seed".equals(par2Str) && !mcServer.isDedicatedServer() ? true : !"tell".equals(par2Str) && !"help".equals(par2Str) && !"me".equals(par2Str) ? mcServer.getConfigurationManager().areCommandsAllowed(username) ? mcServer.func_110455_j() >= par1 : false : true;
	}
	
	@Override public void clonePlayer(EntityPlayer par1EntityPlayer, boolean par2)
	{
		super.clonePlayer(par1EntityPlayer, par2);
		lastExperience = -1;
		lastHealth = -1.0F;
		lastFoodLevel = -1;
		destroyedItemsNetCache.addAll(((EntityPlayerMP) par1EntityPlayer).destroyedItemsNetCache);
	}
	
	public void closeContainer()
	{
		openContainer.onContainerClosed(this);
		openContainer = inventoryContainer;
	}
	
	@Override public void closeScreen()
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet101CloseWindow(openContainer.windowId));
		closeContainer();
	}
	
	@Override public void displayGUIAnvil(int par1, int par2, int par3)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 8, "Repairing", 9, true));
		openContainer = new ContainerRepair(inventory, worldObj, par1, par2, par3, this);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIBeacon(TileEntityBeacon par1TileEntityBeacon)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 7, par1TileEntityBeacon.getInvName(), par1TileEntityBeacon.getSizeInventory(), par1TileEntityBeacon.isInvNameLocalized()));
		openContainer = new ContainerBeacon(inventory, par1TileEntityBeacon);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIBrewingStand(TileEntityBrewingStand par1TileEntityBrewingStand)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory(), par1TileEntityBrewingStand.isInvNameLocalized()));
		openContainer = new ContainerBrewingStand(inventory, par1TileEntityBrewingStand);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIChest(IInventory par1IInventory)
	{
		if(openContainer != inventoryContainer)
		{
			closeScreen();
		}
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory(), par1IInventory.isInvNameLocalized()));
		openContainer = new ContainerChest(inventory, par1IInventory);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIDispenser(TileEntityDispenser par1TileEntityDispenser)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, par1TileEntityDispenser instanceof TileEntityDropper ? 10 : 3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory(), par1TileEntityDispenser.isInvNameLocalized()));
		openContainer = new ContainerDispenser(inventory, par1TileEntityDispenser);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIEditSign(TileEntity par1TileEntity)
	{
		if(par1TileEntity instanceof TileEntitySign)
		{
			((TileEntitySign) par1TileEntity).func_142010_a(this);
			playerNetServerHandler.sendPacketToPlayer(new Packet133TileEditorOpen(0, par1TileEntity.xCoord, par1TileEntity.yCoord, par1TileEntity.zCoord));
		}
	}
	
	@Override public void displayGUIEnchantment(int par1, int par2, int par3, String par4Str)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 4, par4Str == null ? "" : par4Str, 9, par4Str != null));
		openContainer = new ContainerEnchantment(inventory, worldObj, par1, par2, par3);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIFurnace(TileEntityFurnace par1TileEntityFurnace)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory(), par1TileEntityFurnace.isInvNameLocalized()));
		openContainer = new ContainerFurnace(inventory, par1TileEntityFurnace);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIHopper(TileEntityHopper par1TileEntityHopper)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 9, par1TileEntityHopper.getInvName(), par1TileEntityHopper.getSizeInventory(), par1TileEntityHopper.isInvNameLocalized()));
		openContainer = new ContainerHopper(inventory, par1TileEntityHopper);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIHopperMinecart(EntityMinecartHopper par1EntityMinecartHopper)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 9, par1EntityMinecartHopper.getInvName(), par1EntityMinecartHopper.getSizeInventory(), par1EntityMinecartHopper.isInvNameLocalized()));
		openContainer = new ContainerHopper(inventory, par1EntityMinecartHopper);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIMerchant(IMerchant par1IMerchant, String par2Str)
	{
		incrementWindowID();
		openContainer = new ContainerMerchant(inventory, par1IMerchant, worldObj);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
		InventoryMerchant var3 = ((ContainerMerchant) openContainer).getMerchantInventory();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 6, par2Str == null ? "" : par2Str, var3.getSizeInventory(), par2Str != null));
		MerchantRecipeList var4 = par1IMerchant.getRecipes(this);
		if(var4 != null)
		{
			try
			{
				ByteArrayOutputStream var5 = new ByteArrayOutputStream();
				DataOutputStream var6 = new DataOutputStream(var5);
				var6.writeInt(currentWindowId);
				var4.writeRecipiesToStream(var6);
				playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TrList", var5.toByteArray()));
			} catch(IOException var7)
			{
				var7.printStackTrace();
			}
		}
	}
	
	@Override public void displayGUIWorkbench(int par1, int par2, int par3)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 1, "Crafting", 9, true));
		openContainer = new ContainerWorkbench(inventory, worldObj, par1, par2, par3);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void func_110298_a(EntityHorse par1EntityHorse, IInventory par2IInventory)
	{
		if(openContainer != inventoryContainer)
		{
			closeScreen();
		}
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 11, par2IInventory.getInvName(), par2IInventory.getSizeInventory(), par2IInventory.isInvNameLocalized(), par1EntityHorse.entityId));
		openContainer = new ContainerHorseInventory(inventory, par2IInventory, par1EntityHorse);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	public void func_110430_a(float par1, float par2, boolean par3, boolean par4)
	{
		if(ridingEntity != null)
		{
			if(par1 >= -1.0F && par1 <= 1.0F)
			{
				moveStrafing = par1;
			}
			if(par2 >= -1.0F && par2 <= 1.0F)
			{
				moveForward = par2;
			}
			isJumping = par3;
			setSneaking(par4);
		}
	}
	
	@Override public boolean func_96122_a(EntityPlayer par1EntityPlayer)
	{
		return !mcServer.isPVPEnabled() ? false : super.func_96122_a(par1EntityPlayer);
	}
	
	public int getChatVisibility()
	{
		return chatVisibility;
	}
	
	@Override public float getEyeHeight()
	{
		return 1.62F;
	}
	
	@Override public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(MathHelper.floor_double(posX), MathHelper.floor_double(posY + 0.5D), MathHelper.floor_double(posZ));
	}
	
	public String getPlayerIP()
	{
		String var1 = playerNetServerHandler.netManager.getSocketAddress().toString();
		var1 = var1.substring(var1.indexOf("/") + 1);
		var1 = var1.substring(0, var1.indexOf(":"));
		return var1;
	}
	
	public WorldServer getServerForPlayer()
	{
		return (WorldServer) worldObj;
	}
	
	private void incrementWindowID()
	{
		currentWindowId = currentWindowId % 100 + 1;
	}
	
	@Override public void mountEntity(Entity par1Entity)
	{
		super.mountEntity(par1Entity);
		playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(0, this, ridingEntity));
		playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
	}
	
	public void mountEntityAndWakeUp()
	{
		if(riddenByEntity != null)
		{
			riddenByEntity.mountEntity(this);
		}
		if(sleeping)
		{
			wakeUpPlayer(true, false, false);
		}
	}
	
	@Override protected void onChangedPotionEffect(PotionEffect par1PotionEffect, boolean par2)
	{
		super.onChangedPotionEffect(par1PotionEffect, par2);
		playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(entityId, par1PotionEffect));
	}
	
	@Override public void onCriticalHit(Entity par1Entity)
	{
		getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 6));
	}
	
	@Override public void onDeath(DamageSource par1DamageSource)
	{
		mcServer.getConfigurationManager().sendChatMsg(func_110142_aN().func_94546_b());
		if(!worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
		{
			inventory.dropAllItems();
		}
		Collection var2 = worldObj.getScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96642_c);
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			ScoreObjective var4 = (ScoreObjective) var3.next();
			Score var5 = getWorldScoreboard().func_96529_a(getEntityName(), var4);
			var5.func_96648_a();
		}
		EntityLivingBase var6 = func_94060_bK();
		if(var6 != null)
		{
			var6.addToPlayerScore(this, scoreValue);
		}
		addStat(StatList.deathsStat, 1);
	}
	
	@Override public void onEnchantmentCritical(Entity par1Entity)
	{
		getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 7));
	}
	
	@Override protected void onFinishedPotionEffect(PotionEffect par1PotionEffect)
	{
		super.onFinishedPotionEffect(par1PotionEffect);
		playerNetServerHandler.sendPacketToPlayer(new Packet42RemoveEntityEffect(entityId, par1PotionEffect));
	}
	
	@Override public void onItemPickup(Entity par1Entity, int par2)
	{
		super.onItemPickup(par1Entity, par2);
		openContainer.detectAndSendChanges();
	}
	
	@Override protected void onItemUseFinish()
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet38EntityStatus(entityId, (byte) 9));
		super.onItemUseFinish();
	}
	
	@Override protected void onNewPotionEffect(PotionEffect par1PotionEffect)
	{
		super.onNewPotionEffect(par1PotionEffect);
		playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(entityId, par1PotionEffect));
	}
	
	@Override public void onUpdate()
	{
		theItemInWorldManager.updateBlockRemoving();
		--initialInvulnerability;
		openContainer.detectAndSendChanges();
		if(!worldObj.isRemote && !openContainer.canInteractWith(this))
		{
			closeScreen();
			openContainer = inventoryContainer;
		}
		while(!destroyedItemsNetCache.isEmpty())
		{
			int var1 = Math.min(destroyedItemsNetCache.size(), 127);
			int[] var2 = new int[var1];
			Iterator var3 = destroyedItemsNetCache.iterator();
			int var4 = 0;
			while(var3.hasNext() && var4 < var1)
			{
				var2[var4++] = ((Integer) var3.next()).intValue();
				var3.remove();
			}
			playerNetServerHandler.sendPacketToPlayer(new Packet29DestroyEntity(var2));
		}
		if(!loadedChunks.isEmpty())
		{
			ArrayList var6 = new ArrayList();
			Iterator var7 = loadedChunks.iterator();
			ArrayList var8 = new ArrayList();
			while(var7.hasNext() && var6.size() < 5)
			{
				ChunkCoordIntPair var9 = (ChunkCoordIntPair) var7.next();
				var7.remove();
				if(var9 != null && worldObj.blockExists(var9.chunkXPos << 4, 0, var9.chunkZPos << 4))
				{
					var6.add(worldObj.getChunkFromChunkCoords(var9.chunkXPos, var9.chunkZPos));
					var8.addAll(((WorldServer) worldObj).getAllTileEntityInBox(var9.chunkXPos * 16, 0, var9.chunkZPos * 16, var9.chunkXPos * 16 + 16, 256, var9.chunkZPos * 16 + 16));
				}
			}
			if(!var6.isEmpty())
			{
				playerNetServerHandler.sendPacketToPlayer(new Packet56MapChunks(var6));
				Iterator var11 = var8.iterator();
				while(var11.hasNext())
				{
					TileEntity var5 = (TileEntity) var11.next();
					sendTileEntityToPlayer(var5);
				}
				var11 = var6.iterator();
				while(var11.hasNext())
				{
					Chunk var10 = (Chunk) var11.next();
					getServerForPlayer().getEntityTracker().func_85172_a(this, var10);
				}
			}
		}
	}
	
	public void onUpdateEntity()
	{
		try
		{
			super.onUpdate();
			for(int var1 = 0; var1 < inventory.getSizeInventory(); ++var1)
			{
				ItemStack var6 = inventory.getStackInSlot(var1);
				if(var6 != null && Item.itemsList[var6.itemID].isMap() && playerNetServerHandler.packetSize() <= 5)
				{
					Packet var8 = ((ItemMapBase) Item.itemsList[var6.itemID]).createMapDataPacket(var6, worldObj, this);
					if(var8 != null)
					{
						playerNetServerHandler.sendPacketToPlayer(var8);
					}
				}
			}
			if(func_110143_aJ() != lastHealth || lastFoodLevel != foodStats.getFoodLevel() || foodStats.getSaturationLevel() == 0.0F != wasHungry)
			{
				playerNetServerHandler.sendPacketToPlayer(new Packet8UpdateHealth(func_110143_aJ(), foodStats.getFoodLevel(), foodStats.getSaturationLevel()));
				lastHealth = func_110143_aJ();
				lastFoodLevel = foodStats.getFoodLevel();
				wasHungry = foodStats.getSaturationLevel() == 0.0F;
			}
			if(func_110143_aJ() + func_110139_bj() != field_130068_bO)
			{
				field_130068_bO = func_110143_aJ() + func_110139_bj();
				Collection var5 = getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96638_f);
				Iterator var7 = var5.iterator();
				while(var7.hasNext())
				{
					ScoreObjective var9 = (ScoreObjective) var7.next();
					getWorldScoreboard().func_96529_a(getEntityName(), var9).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
				}
			}
			if(experienceTotal != lastExperience)
			{
				lastExperience = experienceTotal;
				playerNetServerHandler.sendPacketToPlayer(new Packet43Experience(experience, experienceTotal, experienceLevel));
			}
		} catch(Throwable var4)
		{
			CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
			CrashReportCategory var3 = var2.makeCategory("Player being ticked");
			func_85029_a(var3);
			throw new ReportedException(var2);
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("playerGameType"))
		{
			if(MinecraftServer.getServer().func_104056_am())
			{
				theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
			} else
			{
				theItemInWorldManager.setGameType(EnumGameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
			}
		}
	}
	
	public void requestTexturePackLoad(String par1Str, int par2)
	{
		String var3 = par1Str + "\u0000" + par2;
		playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TPack", var3.getBytes()));
	}
	
	@Override protected void resetHeight()
	{
		yOffset = 0.0F;
	}
	
	@Override public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(par1ChatMessageComponent));
	}
	
	@Override public void sendContainerAndContentsToPlayer(Container par1Container, List par2List)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet104WindowItems(par1Container.windowId, par2List));
		playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
	}
	
	public void sendContainerToPlayer(Container par1Container)
	{
		sendContainerAndContentsToPlayer(par1Container, par1Container.getInventory());
	}
	
	@Override public void sendPlayerAbilities()
	{
		if(playerNetServerHandler != null)
		{
			playerNetServerHandler.sendPacketToPlayer(new Packet202PlayerAbilities(capabilities));
		}
	}
	
	@Override public void sendProgressBarUpdate(Container par1Container, int par2, int par3)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
	}
	
	@Override public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
	{
		if(!(par1Container.getSlot(par2) instanceof SlotCrafting))
		{
			if(!playerInventoryBeingManipulated)
			{
				playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
			}
		}
	}
	
	private void sendTileEntityToPlayer(TileEntity par1TileEntity)
	{
		if(par1TileEntity != null)
		{
			Packet var2 = par1TileEntity.getDescriptionPacket();
			if(var2 != null)
			{
				playerNetServerHandler.sendPacketToPlayer(var2);
			}
		}
	}
	
	@Override public void setGameType(EnumGameType par1EnumGameType)
	{
		theItemInWorldManager.setGameType(par1EnumGameType);
		playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(3, par1EnumGameType.getID()));
	}
	
	@Override public void setItemInUse(ItemStack par1ItemStack, int par2)
	{
		super.setItemInUse(par1ItemStack, par2);
		if(par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat)
		{
			getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 5));
		}
	}
	
	public void setPlayerHealthUpdated()
	{
		lastHealth = -1.0E8F;
	}
	
	@Override public void setPositionAndUpdate(double par1, double par3, double par5)
	{
		playerNetServerHandler.setPlayerLocation(par1, par3, par5, rotationYaw, rotationPitch);
	}
	
	@Override public EnumStatus sleepInBedAt(int par1, int par2, int par3)
	{
		EnumStatus var4 = super.sleepInBedAt(par1, par2, par3);
		if(var4 == EnumStatus.OK)
		{
			Packet17Sleep var5 = new Packet17Sleep(this, 0, par1, par2, par3);
			getServerForPlayer().getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, var5);
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
			playerNetServerHandler.sendPacketToPlayer(var5);
		}
		return var4;
	}
	
	@Override public void travelToDimension(int par1)
	{
		if(dimension == 1 && par1 == 1)
		{
			triggerAchievement(AchievementList.theEnd2);
			worldObj.removeEntity(this);
			playerConqueredTheEnd = true;
			playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(4, 0));
		} else
		{
			if(dimension == 0 && par1 == 1)
			{
				triggerAchievement(AchievementList.theEnd);
				ChunkCoordinates var2 = mcServer.worldServerForDimension(par1).getEntrancePortalLocation();
				if(var2 != null)
				{
					playerNetServerHandler.setPlayerLocation(var2.posX, var2.posY, var2.posZ, 0.0F, 0.0F);
				}
				par1 = 1;
			} else
			{
				triggerAchievement(AchievementList.portal);
			}
			mcServer.getConfigurationManager().transferPlayerToDimension(this, par1);
			lastExperience = -1;
			lastHealth = -1.0F;
			lastFoodLevel = -1;
		}
	}
	
	public void updateClientInfo(Packet204ClientInfo par1Packet204ClientInfo)
	{
		translator = par1Packet204ClientInfo.getLanguage();
		int var2 = 256 >> par1Packet204ClientInfo.getRenderDistance();
		if(var2 > 3 && var2 < 15)
		{
			renderDistance = var2;
		}
		chatVisibility = par1Packet204ClientInfo.getChatVisibility();
		chatColours = par1Packet204ClientInfo.getChatColours();
		if(mcServer.isSinglePlayer() && mcServer.getServerOwner().equals(username))
		{
			mcServer.setDifficultyForAllWorlds(par1Packet204ClientInfo.getDifficulty());
		}
		setHideCape(1, !par1Packet204ClientInfo.getShowCape());
	}
	
	@Override protected void updateFallState(double par1, boolean par3)
	{
	}
	
	public void updateFlyingState(double par1, boolean par3)
	{
		super.updateFallState(par1, par3);
	}
	
	public void updateHeldItem()
	{
		if(!playerInventoryBeingManipulated)
		{
			playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
		}
	}
	
	@Override public void wakeUpPlayer(boolean par1, boolean par2, boolean par3)
	{
		if(isPlayerSleeping())
		{
			getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 3));
		}
		super.wakeUpPlayer(par1, par2, par3);
		if(playerNetServerHandler != null)
		{
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("playerGameType", theItemInWorldManager.getGameType().getID());
	}
}
