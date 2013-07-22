package net.minecraft.src;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class EntityTrackerEntry
{
	public Entity myEntity;
	public int blocksDistanceThreshold;
	public int updateFrequency;
	public int lastScaledXPosition;
	public int lastScaledYPosition;
	public int lastScaledZPosition;
	public int lastYaw;
	public int lastPitch;
	public int lastHeadMotion;
	public double motionX;
	public double motionY;
	public double motionZ;
	public int ticks;
	private double posX;
	private double posY;
	private double posZ;
	private boolean isDataInitialized;
	private boolean sendVelocityUpdates;
	private int ticksSinceLastForcedTeleport;
	private Entity field_85178_v;
	private boolean ridingEntity;
	public boolean playerEntitiesUpdated;
	public Set trackingPlayers = new HashSet();
	
	public EntityTrackerEntry(Entity par1Entity, int par2, int par3, boolean par4)
	{
		myEntity = par1Entity;
		blocksDistanceThreshold = par2;
		updateFrequency = par3;
		sendVelocityUpdates = par4;
		lastScaledXPosition = MathHelper.floor_double(par1Entity.posX * 32.0D);
		lastScaledYPosition = MathHelper.floor_double(par1Entity.posY * 32.0D);
		lastScaledZPosition = MathHelper.floor_double(par1Entity.posZ * 32.0D);
		lastYaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0F / 360.0F);
		lastPitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0F / 360.0F);
		lastHeadMotion = MathHelper.floor_float(par1Entity.getRotationYawHead() * 256.0F / 360.0F);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		return par1Obj instanceof EntityTrackerEntry ? ((EntityTrackerEntry) par1Obj).myEntity.entityId == myEntity.entityId : false;
	}
	
	private void func_111190_b()
	{
		DataWatcher var1 = myEntity.getDataWatcher();
		if(var1.hasChanges())
		{
			sendPacketToAllAssociatedPlayers(new Packet40EntityMetadata(myEntity.entityId, var1, false));
		}
		if(myEntity instanceof EntityLivingBase)
		{
			ServersideAttributeMap var2 = (ServersideAttributeMap) ((EntityLivingBase) myEntity).func_110140_aT();
			Set var3 = var2.func_111161_b();
			if(!var3.isEmpty())
			{
				sendPacketToAllAssociatedPlayers(new Packet44UpdateAttributes(myEntity.entityId, var3));
			}
			var3.clear();
		}
	}
	
	private Packet getPacketForThisEntity()
	{
		if(myEntity.isDead)
		{
			myEntity.worldObj.getWorldLogAgent().logWarning("Fetching addPacket for removed entity");
		}
		if(myEntity instanceof EntityItem) return new Packet23VehicleSpawn(myEntity, 2, 1);
		else if(myEntity instanceof EntityPlayerMP) return new Packet20NamedEntitySpawn((EntityPlayer) myEntity);
		else if(myEntity instanceof EntityMinecart)
		{
			EntityMinecart var9 = (EntityMinecart) myEntity;
			return new Packet23VehicleSpawn(myEntity, 10, var9.getMinecartType());
		} else if(myEntity instanceof EntityBoat) return new Packet23VehicleSpawn(myEntity, 1);
		else if(!(myEntity instanceof IAnimals) && !(myEntity instanceof EntityDragon))
		{
			if(myEntity instanceof EntityFishHook)
			{
				EntityPlayer var8 = ((EntityFishHook) myEntity).angler;
				return new Packet23VehicleSpawn(myEntity, 90, var8 != null ? var8.entityId : myEntity.entityId);
			} else if(myEntity instanceof EntityArrow)
			{
				Entity var7 = ((EntityArrow) myEntity).shootingEntity;
				return new Packet23VehicleSpawn(myEntity, 60, var7 != null ? var7.entityId : myEntity.entityId);
			} else if(myEntity instanceof EntitySnowball) return new Packet23VehicleSpawn(myEntity, 61);
			else if(myEntity instanceof EntityPotion) return new Packet23VehicleSpawn(myEntity, 73, ((EntityPotion) myEntity).getPotionDamage());
			else if(myEntity instanceof EntityExpBottle) return new Packet23VehicleSpawn(myEntity, 75);
			else if(myEntity instanceof EntityEnderPearl) return new Packet23VehicleSpawn(myEntity, 65);
			else if(myEntity instanceof EntityEnderEye) return new Packet23VehicleSpawn(myEntity, 72);
			else if(myEntity instanceof EntityFireworkRocket) return new Packet23VehicleSpawn(myEntity, 76);
			else
			{
				Packet23VehicleSpawn var2;
				if(myEntity instanceof EntityFireball)
				{
					EntityFireball var6 = (EntityFireball) myEntity;
					var2 = null;
					byte var3 = 63;
					if(myEntity instanceof EntitySmallFireball)
					{
						var3 = 64;
					} else if(myEntity instanceof EntityWitherSkull)
					{
						var3 = 66;
					}
					if(var6.shootingEntity != null)
					{
						var2 = new Packet23VehicleSpawn(myEntity, var3, ((EntityFireball) myEntity).shootingEntity.entityId);
					} else
					{
						var2 = new Packet23VehicleSpawn(myEntity, var3, 0);
					}
					var2.speedX = (int) (var6.accelerationX * 8000.0D);
					var2.speedY = (int) (var6.accelerationY * 8000.0D);
					var2.speedZ = (int) (var6.accelerationZ * 8000.0D);
					return var2;
				} else if(myEntity instanceof EntityEgg) return new Packet23VehicleSpawn(myEntity, 62);
				else if(myEntity instanceof EntityTNTPrimed) return new Packet23VehicleSpawn(myEntity, 50);
				else if(myEntity instanceof EntityEnderCrystal) return new Packet23VehicleSpawn(myEntity, 51);
				else if(myEntity instanceof EntityFallingSand)
				{
					EntityFallingSand var5 = (EntityFallingSand) myEntity;
					return new Packet23VehicleSpawn(myEntity, 70, var5.blockID | var5.metadata << 16);
				} else if(myEntity instanceof EntityPainting) return new Packet25EntityPainting((EntityPainting) myEntity);
				else if(myEntity instanceof EntityItemFrame)
				{
					EntityItemFrame var4 = (EntityItemFrame) myEntity;
					var2 = new Packet23VehicleSpawn(myEntity, 71, var4.hangingDirection);
					var2.xPosition = MathHelper.floor_float(var4.xPosition * 32);
					var2.yPosition = MathHelper.floor_float(var4.yPosition * 32);
					var2.zPosition = MathHelper.floor_float(var4.zPosition * 32);
					return var2;
				} else if(myEntity instanceof EntityLeashKnot)
				{
					EntityLeashKnot var1 = (EntityLeashKnot) myEntity;
					var2 = new Packet23VehicleSpawn(myEntity, 77);
					var2.xPosition = MathHelper.floor_float(var1.xPosition * 32);
					var2.yPosition = MathHelper.floor_float(var1.yPosition * 32);
					var2.zPosition = MathHelper.floor_float(var1.zPosition * 32);
					return var2;
				} else if(myEntity instanceof EntityXPOrb) return new Packet26EntityExpOrb((EntityXPOrb) myEntity);
				else throw new IllegalArgumentException("Don\'t know how to add " + myEntity.getClass() + "!");
			}
		} else
		{
			lastHeadMotion = MathHelper.floor_float(myEntity.getRotationYawHead() * 256.0F / 360.0F);
			return new Packet24MobSpawn((EntityLivingBase) myEntity);
		}
	}
	
	@Override public int hashCode()
	{
		return myEntity.entityId;
	}
	
	public void informAllAssociatedPlayersOfItemDestruction()
	{
		Iterator var1 = trackingPlayers.iterator();
		while(var1.hasNext())
		{
			EntityPlayerMP var2 = (EntityPlayerMP) var1.next();
			var2.destroyedItemsNetCache.add(Integer.valueOf(myEntity.entityId));
		}
	}
	
	private boolean isPlayerWatchingThisChunk(EntityPlayerMP par1EntityPlayerMP)
	{
		return par1EntityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(par1EntityPlayerMP, myEntity.chunkCoordX, myEntity.chunkCoordZ);
	}
	
	public void removeFromWatchingList(EntityPlayerMP par1EntityPlayerMP)
	{
		if(trackingPlayers.contains(par1EntityPlayerMP))
		{
			par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(myEntity.entityId));
			trackingPlayers.remove(par1EntityPlayerMP);
		}
	}
	
	public void removePlayerFromTracker(EntityPlayerMP par1EntityPlayerMP)
	{
		if(trackingPlayers.contains(par1EntityPlayerMP))
		{
			trackingPlayers.remove(par1EntityPlayerMP);
			par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(myEntity.entityId));
		}
	}
	
	public void sendEventsToPlayers(List par1List)
	{
		for(int var2 = 0; var2 < par1List.size(); ++var2)
		{
			tryStartWachingThis((EntityPlayerMP) par1List.get(var2));
		}
	}
	
	public void sendLocationToAllClients(List par1List)
	{
		playerEntitiesUpdated = false;
		if(!isDataInitialized || myEntity.getDistanceSq(posX, posY, posZ) > 16.0D)
		{
			posX = myEntity.posX;
			posY = myEntity.posY;
			posZ = myEntity.posZ;
			isDataInitialized = true;
			playerEntitiesUpdated = true;
			sendEventsToPlayers(par1List);
		}
		if(field_85178_v != myEntity.ridingEntity || myEntity.ridingEntity != null && ticks % 60 == 0)
		{
			field_85178_v = myEntity.ridingEntity;
			sendPacketToAllTrackingPlayers(new Packet39AttachEntity(0, myEntity, myEntity.ridingEntity));
		}
		if(myEntity instanceof EntityItemFrame && ticks % 10 == 0)
		{
			EntityItemFrame var23 = (EntityItemFrame) myEntity;
			ItemStack var24 = var23.getDisplayedItem();
			if(var24 != null && var24.getItem() instanceof ItemMap)
			{
				MapData var26 = Item.map.getMapData(var24, myEntity.worldObj);
				Iterator var27 = par1List.iterator();
				while(var27.hasNext())
				{
					EntityPlayer var28 = (EntityPlayer) var27.next();
					EntityPlayerMP var29 = (EntityPlayerMP) var28;
					var26.updateVisiblePlayers(var29, var24);
					if(var29.playerNetServerHandler.packetSize() <= 5)
					{
						Packet var30 = Item.map.createMapDataPacket(var24, myEntity.worldObj, var29);
						if(var30 != null)
						{
							var29.playerNetServerHandler.sendPacketToPlayer(var30);
						}
					}
				}
			}
			func_111190_b();
		} else if(ticks % updateFrequency == 0 || myEntity.isAirBorne || myEntity.getDataWatcher().hasChanges())
		{
			int var2;
			int var3;
			if(myEntity.ridingEntity == null)
			{
				++ticksSinceLastForcedTeleport;
				var2 = myEntity.myEntitySize.multiplyBy32AndRound(myEntity.posX);
				var3 = MathHelper.floor_double(myEntity.posY * 32.0D);
				int var4 = myEntity.myEntitySize.multiplyBy32AndRound(myEntity.posZ);
				int var5 = MathHelper.floor_float(myEntity.rotationYaw * 256.0F / 360.0F);
				int var6 = MathHelper.floor_float(myEntity.rotationPitch * 256.0F / 360.0F);
				int var7 = var2 - lastScaledXPosition;
				int var8 = var3 - lastScaledYPosition;
				int var9 = var4 - lastScaledZPosition;
				Object var10 = null;
				boolean var11 = Math.abs(var7) >= 4 || Math.abs(var8) >= 4 || Math.abs(var9) >= 4 || ticks % 60 == 0;
				boolean var12 = Math.abs(var5 - lastYaw) >= 4 || Math.abs(var6 - lastPitch) >= 4;
				if(ticks > 0 || myEntity instanceof EntityArrow)
				{
					if(var7 >= -128 && var7 < 128 && var8 >= -128 && var8 < 128 && var9 >= -128 && var9 < 128 && ticksSinceLastForcedTeleport <= 400 && !ridingEntity)
					{
						if(var11 && var12)
						{
							var10 = new Packet33RelEntityMoveLook(myEntity.entityId, (byte) var7, (byte) var8, (byte) var9, (byte) var5, (byte) var6);
						} else if(var11)
						{
							var10 = new Packet31RelEntityMove(myEntity.entityId, (byte) var7, (byte) var8, (byte) var9);
						} else if(var12)
						{
							var10 = new Packet32EntityLook(myEntity.entityId, (byte) var5, (byte) var6);
						}
					} else
					{
						ticksSinceLastForcedTeleport = 0;
						var10 = new Packet34EntityTeleport(myEntity.entityId, var2, var3, var4, (byte) var5, (byte) var6);
					}
				}
				if(sendVelocityUpdates)
				{
					double var13 = myEntity.motionX - motionX;
					double var15 = myEntity.motionY - motionY;
					double var17 = myEntity.motionZ - motionZ;
					double var19 = 0.02D;
					double var21 = var13 * var13 + var15 * var15 + var17 * var17;
					if(var21 > var19 * var19 || var21 > 0.0D && myEntity.motionX == 0.0D && myEntity.motionY == 0.0D && myEntity.motionZ == 0.0D)
					{
						motionX = myEntity.motionX;
						motionY = myEntity.motionY;
						motionZ = myEntity.motionZ;
						sendPacketToAllTrackingPlayers(new Packet28EntityVelocity(myEntity.entityId, motionX, motionY, motionZ));
					}
				}
				if(var10 != null)
				{
					sendPacketToAllTrackingPlayers((Packet) var10);
				}
				func_111190_b();
				if(var11)
				{
					lastScaledXPosition = var2;
					lastScaledYPosition = var3;
					lastScaledZPosition = var4;
				}
				if(var12)
				{
					lastYaw = var5;
					lastPitch = var6;
				}
				ridingEntity = false;
			} else
			{
				var2 = MathHelper.floor_float(myEntity.rotationYaw * 256.0F / 360.0F);
				var3 = MathHelper.floor_float(myEntity.rotationPitch * 256.0F / 360.0F);
				boolean var25 = Math.abs(var2 - lastYaw) >= 4 || Math.abs(var3 - lastPitch) >= 4;
				if(var25)
				{
					sendPacketToAllTrackingPlayers(new Packet32EntityLook(myEntity.entityId, (byte) var2, (byte) var3));
					lastYaw = var2;
					lastPitch = var3;
				}
				lastScaledXPosition = myEntity.myEntitySize.multiplyBy32AndRound(myEntity.posX);
				lastScaledYPosition = MathHelper.floor_double(myEntity.posY * 32.0D);
				lastScaledZPosition = myEntity.myEntitySize.multiplyBy32AndRound(myEntity.posZ);
				func_111190_b();
				ridingEntity = true;
			}
			var2 = MathHelper.floor_float(myEntity.getRotationYawHead() * 256.0F / 360.0F);
			if(Math.abs(var2 - lastHeadMotion) >= 4)
			{
				sendPacketToAllTrackingPlayers(new Packet35EntityHeadRotation(myEntity.entityId, (byte) var2));
				lastHeadMotion = var2;
			}
			myEntity.isAirBorne = false;
		}
		++ticks;
		if(myEntity.velocityChanged)
		{
			sendPacketToAllAssociatedPlayers(new Packet28EntityVelocity(myEntity));
			myEntity.velocityChanged = false;
		}
	}
	
	public void sendPacketToAllAssociatedPlayers(Packet par1Packet)
	{
		sendPacketToAllTrackingPlayers(par1Packet);
		if(myEntity instanceof EntityPlayerMP)
		{
			((EntityPlayerMP) myEntity).playerNetServerHandler.sendPacketToPlayer(par1Packet);
		}
	}
	
	public void sendPacketToAllTrackingPlayers(Packet par1Packet)
	{
		Iterator var2 = trackingPlayers.iterator();
		while(var2.hasNext())
		{
			EntityPlayerMP var3 = (EntityPlayerMP) var2.next();
			var3.playerNetServerHandler.sendPacketToPlayer(par1Packet);
		}
	}
	
	public void tryStartWachingThis(EntityPlayerMP par1EntityPlayerMP)
	{
		if(par1EntityPlayerMP != myEntity)
		{
			double var2 = par1EntityPlayerMP.posX - lastScaledXPosition / 32;
			double var4 = par1EntityPlayerMP.posZ - lastScaledZPosition / 32;
			if(var2 >= -blocksDistanceThreshold && var2 <= blocksDistanceThreshold && var4 >= -blocksDistanceThreshold && var4 <= blocksDistanceThreshold)
			{
				if(!trackingPlayers.contains(par1EntityPlayerMP) && (isPlayerWatchingThisChunk(par1EntityPlayerMP) || myEntity.field_98038_p))
				{
					trackingPlayers.add(par1EntityPlayerMP);
					Packet var6 = getPacketForThisEntity();
					par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(var6);
					if(!myEntity.getDataWatcher().getIsBlank())
					{
						par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet40EntityMetadata(myEntity.entityId, myEntity.getDataWatcher(), true));
					}
					if(myEntity instanceof EntityLivingBase)
					{
						ServersideAttributeMap var7 = (ServersideAttributeMap) ((EntityLivingBase) myEntity).func_110140_aT();
						Collection var8 = var7.func_111160_c();
						if(!var8.isEmpty())
						{
							par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet44UpdateAttributes(myEntity.entityId, var8));
						}
					}
					motionX = myEntity.motionX;
					motionY = myEntity.motionY;
					motionZ = myEntity.motionZ;
					if(sendVelocityUpdates && !(var6 instanceof Packet24MobSpawn))
					{
						par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet28EntityVelocity(myEntity.entityId, myEntity.motionX, myEntity.motionY, myEntity.motionZ));
					}
					if(myEntity.ridingEntity != null)
					{
						par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(0, myEntity, myEntity.ridingEntity));
					}
					if(myEntity instanceof EntityLiving && ((EntityLiving) myEntity).func_110166_bE() != null)
					{
						par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(1, myEntity, ((EntityLiving) myEntity).func_110166_bE()));
					}
					if(myEntity instanceof EntityLivingBase)
					{
						for(int var10 = 0; var10 < 5; ++var10)
						{
							ItemStack var13 = ((EntityLivingBase) myEntity).getCurrentItemOrArmor(var10);
							if(var13 != null)
							{
								par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet5PlayerInventory(myEntity.entityId, var10, var13));
							}
						}
					}
					if(myEntity instanceof EntityPlayer)
					{
						EntityPlayer var11 = (EntityPlayer) myEntity;
						if(var11.isPlayerSleeping())
						{
							par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet17Sleep(myEntity, 0, MathHelper.floor_double(myEntity.posX), MathHelper.floor_double(myEntity.posY), MathHelper.floor_double(myEntity.posZ)));
						}
					}
					if(myEntity instanceof EntityLivingBase)
					{
						EntityLivingBase var14 = (EntityLivingBase) myEntity;
						Iterator var12 = var14.getActivePotionEffects().iterator();
						while(var12.hasNext())
						{
							PotionEffect var9 = (PotionEffect) var12.next();
							par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(myEntity.entityId, var9));
						}
					}
				}
			} else if(trackingPlayers.contains(par1EntityPlayerMP))
			{
				trackingPlayers.remove(par1EntityPlayerMP);
				par1EntityPlayerMP.destroyedItemsNetCache.add(Integer.valueOf(myEntity.entityId));
			}
		}
	}
}
