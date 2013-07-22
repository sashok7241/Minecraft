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
	private StringTranslate translator = new StringTranslate("en_US");
	public NetServerHandler playerNetServerHandler;
	public MinecraftServer mcServer;
	public ItemInWorldManager theItemInWorldManager;
	public double managedPosX;
	public double managedPosZ;
	public final List loadedChunks = new LinkedList();
	public final List destroyedItemsNetCache = new LinkedList();
	private int lastHealth = -99999999;
	private int lastFoodLevel = -99999999;
	private boolean wasHungry = true;
	private int lastExperience = -99999999;
	private int initialInvulnerability = 60;
	private int renderDistance = 0;
	private int chatVisibility = 0;
	private boolean chatColours = true;
	private int currentWindowId = 0;
	public boolean playerInventoryBeingManipulated;
	public int ping;
	public boolean playerConqueredTheEnd = false;
	
	public EntityPlayerMP(MinecraftServer p_i3396_1_, World p_i3396_2_, String p_i3396_3_, ItemInWorldManager p_i3396_4_)
	{
		super(p_i3396_2_);
		p_i3396_4_.thisPlayerMP = this;
		theItemInWorldManager = p_i3396_4_;
		renderDistance = p_i3396_1_.getConfigurationManager().getViewDistance();
		ChunkCoordinates var5 = p_i3396_2_.getSpawnPoint();
		int var6 = var5.posX;
		int var7 = var5.posZ;
		int var8 = var5.posY;
		if(!p_i3396_2_.provider.hasNoSky && p_i3396_2_.getWorldInfo().getGameType() != EnumGameType.ADVENTURE)
		{
			int var9 = Math.max(5, p_i3396_1_.getSpawnProtectionSize() - 6);
			var6 += rand.nextInt(var9 * 2) - var9;
			var7 += rand.nextInt(var9 * 2) - var9;
			var8 = p_i3396_2_.getTopSolidOrLiquidBlock(var6, var7);
		}
		mcServer = p_i3396_1_;
		stepHeight = 0.0F;
		username = p_i3396_3_;
		yOffset = 0.0F;
		setLocationAndAngles(var6 + 0.5D, var8, var7 + 0.5D, 0.0F, 0.0F);
		while(!p_i3396_2_.getCollidingBoundingBoxes(this, boundingBox).isEmpty())
		{
			setPosition(posX, posY + 1.0D, posZ);
		}
	}
	
	@Override public void addChatMessage(String p_71035_1_)
	{
		StringTranslate var2 = StringTranslate.getInstance();
		String var3 = var2.translateKey(p_71035_1_);
		playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(var3));
	}
	
	@Override public void addExperienceLevel(int p_82242_1_)
	{
		super.addExperienceLevel(p_82242_1_);
		lastExperience = -1;
	}
	
	public void addSelfToInternalCraftingInventory()
	{
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void addStat(StatBase p_71064_1_, int p_71064_2_)
	{
		if(p_71064_1_ != null)
		{
			if(!p_71064_1_.isIndependent)
			{
				while(p_71064_2_ > 100)
				{
					playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(p_71064_1_.statId, 100));
					p_71064_2_ -= 100;
				}
				playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(p_71064_1_.statId, p_71064_2_));
			}
		}
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else
		{
			boolean var3 = mcServer.isDedicatedServer() && mcServer.isPVPEnabled() && "fall".equals(p_70097_1_.damageType);
			if(!var3 && initialInvulnerability > 0 && p_70097_1_ != DamageSource.outOfWorld) return false;
			else
			{
				if(p_70097_1_ instanceof EntityDamageSource)
				{
					Entity var4 = p_70097_1_.getEntity();
					if(var4 instanceof EntityPlayer && !func_96122_a((EntityPlayer) var4)) return false;
					if(var4 instanceof EntityArrow)
					{
						EntityArrow var5 = (EntityArrow) var4;
						if(var5.shootingEntity instanceof EntityPlayer && !func_96122_a((EntityPlayer) var5.shootingEntity)) return false;
					}
				}
				return super.attackEntityFrom(p_70097_1_, p_70097_2_);
			}
		}
	}
	
	@Override public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
	{
		return "seed".equals(p_70003_2_) && !mcServer.isDedicatedServer() ? true : !"tell".equals(p_70003_2_) && !"help".equals(p_70003_2_) && !"me".equals(p_70003_2_) ? mcServer.getConfigurationManager().areCommandsAllowed(username) : true;
	}
	
	@Override public void clonePlayer(EntityPlayer p_71049_1_, boolean p_71049_2_)
	{
		super.clonePlayer(p_71049_1_, p_71049_2_);
		lastExperience = -1;
		lastHealth = -1;
		lastFoodLevel = -1;
		destroyedItemsNetCache.addAll(((EntityPlayerMP) p_71049_1_).destroyedItemsNetCache);
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
	
	@Override public void displayGUIAnvil(int p_82244_1_, int p_82244_2_, int p_82244_3_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 8, "Repairing", 9, true));
		openContainer = new ContainerRepair(inventory, worldObj, p_82244_1_, p_82244_2_, p_82244_3_, this);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIBeacon(TileEntityBeacon p_82240_1_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 7, p_82240_1_.getInvName(), p_82240_1_.getSizeInventory(), p_82240_1_.isInvNameLocalized()));
		openContainer = new ContainerBeacon(inventory, p_82240_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIBrewingStand(TileEntityBrewingStand p_71017_1_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 5, p_71017_1_.getInvName(), p_71017_1_.getSizeInventory(), p_71017_1_.isInvNameLocalized()));
		openContainer = new ContainerBrewingStand(inventory, p_71017_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIChest(IInventory p_71007_1_)
	{
		if(openContainer != inventoryContainer)
		{
			closeScreen();
		}
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 0, p_71007_1_.getInvName(), p_71007_1_.getSizeInventory(), p_71007_1_.isInvNameLocalized()));
		openContainer = new ContainerChest(inventory, p_71007_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIDispenser(TileEntityDispenser p_71006_1_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, p_71006_1_ instanceof TileEntityDropper ? 10 : 3, p_71006_1_.getInvName(), p_71006_1_.getSizeInventory(), p_71006_1_.isInvNameLocalized()));
		openContainer = new ContainerDispenser(inventory, p_71006_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIEnchantment(int p_71002_1_, int p_71002_2_, int p_71002_3_, String p_71002_4_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 4, p_71002_4_ == null ? "" : p_71002_4_, 9, p_71002_4_ != null));
		openContainer = new ContainerEnchantment(inventory, worldObj, p_71002_1_, p_71002_2_, p_71002_3_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIFurnace(TileEntityFurnace p_71042_1_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 2, p_71042_1_.getInvName(), p_71042_1_.getSizeInventory(), p_71042_1_.isInvNameLocalized()));
		openContainer = new ContainerFurnace(inventory, p_71042_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIHopper(TileEntityHopper p_94064_1_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 9, p_94064_1_.getInvName(), p_94064_1_.getSizeInventory(), p_94064_1_.isInvNameLocalized()));
		openContainer = new ContainerHopper(inventory, p_94064_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIHopperMinecart(EntityMinecartHopper p_96125_1_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 9, p_96125_1_.getInvName(), p_96125_1_.getSizeInventory(), p_96125_1_.isInvNameLocalized()));
		openContainer = new ContainerHopper(inventory, p_96125_1_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public void displayGUIMerchant(IMerchant p_71030_1_, String p_71030_2_)
	{
		incrementWindowID();
		openContainer = new ContainerMerchant(inventory, p_71030_1_, worldObj);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
		InventoryMerchant var3 = ((ContainerMerchant) openContainer).getMerchantInventory();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 6, p_71030_2_ == null ? "" : p_71030_2_, var3.getSizeInventory(), p_71030_2_ != null));
		MerchantRecipeList var4 = p_71030_1_.getRecipes(this);
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
	
	@Override public void displayGUIWorkbench(int p_71058_1_, int p_71058_2_, int p_71058_3_)
	{
		incrementWindowID();
		playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(currentWindowId, 1, "Crafting", 9, true));
		openContainer = new ContainerWorkbench(inventory, worldObj, p_71058_1_, p_71058_2_, p_71058_3_);
		openContainer.windowId = currentWindowId;
		openContainer.addCraftingToCrafters(this);
	}
	
	@Override public boolean func_96122_a(EntityPlayer p_96122_1_)
	{
		return !mcServer.isPVPEnabled() ? false : super.func_96122_a(p_96122_1_);
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
	
	@Override public StringTranslate getTranslator()
	{
		return translator;
	}
	
	private void incrementWindowID()
	{
		currentWindowId = currentWindowId % 100 + 1;
	}
	
	@Override public void mountEntity(Entity p_70078_1_)
	{
		super.mountEntity(p_70078_1_);
		playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(this, ridingEntity));
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
	
	@Override protected void onChangedPotionEffect(PotionEffect p_70695_1_)
	{
		super.onChangedPotionEffect(p_70695_1_);
		playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(entityId, p_70695_1_));
	}
	
	@Override public void onCriticalHit(Entity p_71009_1_)
	{
		getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(p_71009_1_, 6));
	}
	
	@Override public void onDeath(DamageSource p_70645_1_)
	{
		mcServer.getConfigurationManager().sendChatMsg(_combatTracker.func_94546_b());
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
		EntityLiving var6 = func_94060_bK();
		if(var6 != null)
		{
			var6.addToPlayerScore(this, scoreValue);
		}
	}
	
	@Override public void onEnchantmentCritical(Entity p_71047_1_)
	{
		getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(p_71047_1_, 7));
	}
	
	@Override protected void onFinishedPotionEffect(PotionEffect p_70688_1_)
	{
		super.onFinishedPotionEffect(p_70688_1_);
		playerNetServerHandler.sendPacketToPlayer(new Packet42RemoveEntityEffect(entityId, p_70688_1_));
	}
	
	@Override public void onItemPickup(Entity p_71001_1_, int p_71001_2_)
	{
		super.onItemPickup(p_71001_1_, p_71001_2_);
		openContainer.detectAndSendChanges();
	}
	
	@Override protected void onItemUseFinish()
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet38EntityStatus(entityId, (byte) 9));
		super.onItemUseFinish();
	}
	
	@Override protected void onNewPotionEffect(PotionEffect p_70670_1_)
	{
		super.onNewPotionEffect(p_70670_1_);
		playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(entityId, p_70670_1_));
	}
	
	@Override public void onUpdate()
	{
		theItemInWorldManager.updateBlockRemoving();
		--initialInvulnerability;
		openContainer.detectAndSendChanges();
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
				ItemStack var5 = inventory.getStackInSlot(var1);
				if(var5 != null && Item.itemsList[var5.itemID].isMap() && playerNetServerHandler.packetSize() <= 5)
				{
					Packet var6 = ((ItemMapBase) Item.itemsList[var5.itemID]).createMapDataPacket(var5, worldObj, this);
					if(var6 != null)
					{
						playerNetServerHandler.sendPacketToPlayer(var6);
					}
				}
			}
			if(getHealth() != lastHealth || lastFoodLevel != foodStats.getFoodLevel() || foodStats.getSaturationLevel() == 0.0F != wasHungry)
			{
				playerNetServerHandler.sendPacketToPlayer(new Packet8UpdateHealth(getHealth(), foodStats.getFoodLevel(), foodStats.getSaturationLevel()));
				lastHealth = getHealth();
				lastFoodLevel = foodStats.getFoodLevel();
				wasHungry = foodStats.getSaturationLevel() == 0.0F;
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
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.hasKey("playerGameType"))
		{
			if(MinecraftServer.getServer().func_104056_am())
			{
				theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
			} else
			{
				theItemInWorldManager.setGameType(EnumGameType.getByID(p_70037_1_.getInteger("playerGameType")));
			}
		}
	}
	
	public void requestTexturePackLoad(String p_71115_1_, int p_71115_2_)
	{
		String var3 = p_71115_1_ + "\u0000" + p_71115_2_;
		playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TPack", var3.getBytes()));
	}
	
	@Override protected void resetHeight()
	{
		yOffset = 0.0F;
	}
	
	@Override public void sendChatToPlayer(String p_70006_1_)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(p_70006_1_));
	}
	
	@Override public void sendContainerAndContentsToPlayer(Container p_71110_1_, List p_71110_2_)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet104WindowItems(p_71110_1_.windowId, p_71110_2_));
		playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
	}
	
	public void sendContainerToPlayer(Container p_71120_1_)
	{
		sendContainerAndContentsToPlayer(p_71120_1_, p_71120_1_.getInventory());
	}
	
	@Override public void sendPlayerAbilities()
	{
		if(playerNetServerHandler != null)
		{
			playerNetServerHandler.sendPacketToPlayer(new Packet202PlayerAbilities(capabilities));
		}
	}
	
	@Override public void sendProgressBarUpdate(Container p_71112_1_, int p_71112_2_, int p_71112_3_)
	{
		playerNetServerHandler.sendPacketToPlayer(new Packet105UpdateProgressbar(p_71112_1_.windowId, p_71112_2_, p_71112_3_));
	}
	
	@Override public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_)
	{
		if(!(p_71111_1_.getSlot(p_71111_2_) instanceof SlotCrafting))
		{
			if(!playerInventoryBeingManipulated)
			{
				playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(p_71111_1_.windowId, p_71111_2_, p_71111_3_));
			}
		}
	}
	
	private void sendTileEntityToPlayer(TileEntity p_71119_1_)
	{
		if(p_71119_1_ != null)
		{
			Packet var2 = p_71119_1_.getDescriptionPacket();
			if(var2 != null)
			{
				playerNetServerHandler.sendPacketToPlayer(var2);
			}
		}
	}
	
	@Override public void setEntityHealth(int p_70606_1_)
	{
		super.setEntityHealth(p_70606_1_);
		Collection var2 = getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96638_f);
		Iterator var3 = var2.iterator();
		while(var3.hasNext())
		{
			ScoreObjective var4 = (ScoreObjective) var3.next();
			getWorldScoreboard().func_96529_a(getEntityName(), var4).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
		}
	}
	
	@Override public void setGameType(EnumGameType p_71033_1_)
	{
		theItemInWorldManager.setGameType(p_71033_1_);
		playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(3, p_71033_1_.getID()));
	}
	
	@Override public void setItemInUse(ItemStack p_71008_1_, int p_71008_2_)
	{
		super.setItemInUse(p_71008_1_, p_71008_2_);
		if(p_71008_1_ != null && p_71008_1_.getItem() != null && p_71008_1_.getItem().getItemUseAction(p_71008_1_) == EnumAction.eat)
		{
			getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 5));
		}
	}
	
	public void setPlayerHealthUpdated()
	{
		lastHealth = -99999999;
	}
	
	@Override public void setPositionAndUpdate(double p_70634_1_, double p_70634_3_, double p_70634_5_)
	{
		playerNetServerHandler.setPlayerLocation(p_70634_1_, p_70634_3_, p_70634_5_, rotationYaw, rotationPitch);
	}
	
	@Override public EnumStatus sleepInBedAt(int p_71018_1_, int p_71018_2_, int p_71018_3_)
	{
		EnumStatus var4 = super.sleepInBedAt(p_71018_1_, p_71018_2_, p_71018_3_);
		if(var4 == EnumStatus.OK)
		{
			Packet17Sleep var5 = new Packet17Sleep(this, 0, p_71018_1_, p_71018_2_, p_71018_3_);
			getServerForPlayer().getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, var5);
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
			playerNetServerHandler.sendPacketToPlayer(var5);
		}
		return var4;
	}
	
	@Override public void travelToDimension(int p_71027_1_)
	{
		if(dimension == 1 && p_71027_1_ == 1)
		{
			triggerAchievement(AchievementList.theEnd2);
			worldObj.removeEntity(this);
			playerConqueredTheEnd = true;
			playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(4, 0));
		} else
		{
			if(dimension == 1 && p_71027_1_ == 0)
			{
				triggerAchievement(AchievementList.theEnd);
				ChunkCoordinates var2 = mcServer.worldServerForDimension(p_71027_1_).getEntrancePortalLocation();
				if(var2 != null)
				{
					playerNetServerHandler.setPlayerLocation(var2.posX, var2.posY, var2.posZ, 0.0F, 0.0F);
				}
				p_71027_1_ = 1;
			} else
			{
				triggerAchievement(AchievementList.portal);
			}
			mcServer.getConfigurationManager().transferPlayerToDimension(this, p_71027_1_);
			lastExperience = -1;
			lastHealth = -1;
			lastFoodLevel = -1;
		}
	}
	
	public void updateClientInfo(Packet204ClientInfo p_71125_1_)
	{
		if(translator.getLanguageList().containsKey(p_71125_1_.getLanguage()))
		{
			translator.setLanguage(p_71125_1_.getLanguage(), false);
		}
		int var2 = 256 >> p_71125_1_.getRenderDistance();
		if(var2 > 3 && var2 < 15)
		{
			renderDistance = var2;
		}
		chatVisibility = p_71125_1_.getChatVisibility();
		chatColours = p_71125_1_.getChatColours();
		if(mcServer.isSinglePlayer() && mcServer.getServerOwner().equals(username))
		{
			mcServer.setDifficultyForAllWorlds(p_71125_1_.getDifficulty());
		}
		setHideCape(1, !p_71125_1_.getShowCape());
	}
	
	@Override protected void updateFallState(double p_70064_1_, boolean p_70064_3_)
	{
	}
	
	public void updateFlyingState(double p_71122_1_, boolean p_71122_3_)
	{
		super.updateFallState(p_71122_1_, p_71122_3_);
	}
	
	public void updateHeldItem()
	{
		if(!playerInventoryBeingManipulated)
		{
			playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, inventory.getItemStack()));
		}
	}
	
	@Override public void wakeUpPlayer(boolean p_70999_1_, boolean p_70999_2_, boolean p_70999_3_)
	{
		if(isPlayerSleeping())
		{
			getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 3));
		}
		super.wakeUpPlayer(p_70999_1_, p_70999_2_, p_70999_3_);
		if(playerNetServerHandler != null)
		{
			playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("playerGameType", theItemInWorldManager.getGameType().getID());
	}
}
