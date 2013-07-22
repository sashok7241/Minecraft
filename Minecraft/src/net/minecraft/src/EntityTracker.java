package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EntityTracker
{
	private final WorldServer theWorld;
	private Set trackedEntities = new HashSet();
	private IntHashMap trackedEntityIDs = new IntHashMap();
	private int entityViewDistance;
	
	public EntityTracker(WorldServer p_i3389_1_)
	{
		theWorld = p_i3389_1_;
		entityViewDistance = p_i3389_1_.getMinecraftServer().getConfigurationManager().getEntityViewDistance();
	}
	
	public void addEntityToTracker(Entity p_72786_1_)
	{
		if(p_72786_1_ instanceof EntityPlayerMP)
		{
			this.addEntityToTracker(p_72786_1_, 512, 2);
			EntityPlayerMP var2 = (EntityPlayerMP) p_72786_1_;
			Iterator var3 = trackedEntities.iterator();
			while(var3.hasNext())
			{
				EntityTrackerEntry var4 = (EntityTrackerEntry) var3.next();
				if(var4.myEntity != var2)
				{
					var4.tryStartWachingThis(var2);
				}
			}
		} else if(p_72786_1_ instanceof EntityFishHook)
		{
			this.addEntityToTracker(p_72786_1_, 64, 5, true);
		} else if(p_72786_1_ instanceof EntityArrow)
		{
			this.addEntityToTracker(p_72786_1_, 64, 20, false);
		} else if(p_72786_1_ instanceof EntitySmallFireball)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, false);
		} else if(p_72786_1_ instanceof EntityFireball)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, false);
		} else if(p_72786_1_ instanceof EntitySnowball)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if(p_72786_1_ instanceof EntityEnderPearl)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if(p_72786_1_ instanceof EntityEnderEye)
		{
			this.addEntityToTracker(p_72786_1_, 64, 4, true);
		} else if(p_72786_1_ instanceof EntityEgg)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if(p_72786_1_ instanceof EntityPotion)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if(p_72786_1_ instanceof EntityExpBottle)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if(p_72786_1_ instanceof EntityFireworkRocket)
		{
			this.addEntityToTracker(p_72786_1_, 64, 10, true);
		} else if(p_72786_1_ instanceof EntityItem)
		{
			this.addEntityToTracker(p_72786_1_, 64, 20, true);
		} else if(p_72786_1_ instanceof EntityMinecart)
		{
			this.addEntityToTracker(p_72786_1_, 80, 3, true);
		} else if(p_72786_1_ instanceof EntityBoat)
		{
			this.addEntityToTracker(p_72786_1_, 80, 3, true);
		} else if(p_72786_1_ instanceof EntitySquid)
		{
			this.addEntityToTracker(p_72786_1_, 64, 3, true);
		} else if(p_72786_1_ instanceof EntityWither)
		{
			this.addEntityToTracker(p_72786_1_, 80, 3, false);
		} else if(p_72786_1_ instanceof EntityBat)
		{
			this.addEntityToTracker(p_72786_1_, 80, 3, false);
		} else if(p_72786_1_ instanceof IAnimals)
		{
			this.addEntityToTracker(p_72786_1_, 80, 3, true);
		} else if(p_72786_1_ instanceof EntityDragon)
		{
			this.addEntityToTracker(p_72786_1_, 160, 3, true);
		} else if(p_72786_1_ instanceof EntityTNTPrimed)
		{
			this.addEntityToTracker(p_72786_1_, 160, 10, true);
		} else if(p_72786_1_ instanceof EntityFallingSand)
		{
			this.addEntityToTracker(p_72786_1_, 160, 20, true);
		} else if(p_72786_1_ instanceof EntityPainting)
		{
			this.addEntityToTracker(p_72786_1_, 160, Integer.MAX_VALUE, false);
		} else if(p_72786_1_ instanceof EntityXPOrb)
		{
			this.addEntityToTracker(p_72786_1_, 160, 20, true);
		} else if(p_72786_1_ instanceof EntityEnderCrystal)
		{
			this.addEntityToTracker(p_72786_1_, 256, Integer.MAX_VALUE, false);
		} else if(p_72786_1_ instanceof EntityItemFrame)
		{
			this.addEntityToTracker(p_72786_1_, 160, Integer.MAX_VALUE, false);
		}
	}
	
	public void addEntityToTracker(Entity p_72791_1_, int p_72791_2_, int p_72791_3_)
	{
		this.addEntityToTracker(p_72791_1_, p_72791_2_, p_72791_3_, false);
	}
	
	public void addEntityToTracker(Entity p_72785_1_, int p_72785_2_, int p_72785_3_, boolean p_72785_4_)
	{
		if(p_72785_2_ > entityViewDistance)
		{
			p_72785_2_ = entityViewDistance;
		}
		try
		{
			if(trackedEntityIDs.containsItem(p_72785_1_.entityId)) throw new IllegalStateException("Entity is already tracked!");
			EntityTrackerEntry var5 = new EntityTrackerEntry(p_72785_1_, p_72785_2_, p_72785_3_, p_72785_4_);
			trackedEntities.add(var5);
			trackedEntityIDs.addKey(p_72785_1_.entityId, var5);
			var5.sendEventsToPlayers(theWorld.playerEntities);
		} catch(Throwable var11)
		{
			CrashReport var6 = CrashReport.makeCrashReport(var11, "Adding entity to track");
			CrashReportCategory var7 = var6.makeCategory("Entity To Track");
			var7.addCrashSection("Tracking range", p_72785_2_ + " blocks");
			var7.addCrashSectionCallable("Update interval", new CallableEntityTracker(this, p_72785_3_));
			p_72785_1_.func_85029_a(var7);
			CrashReportCategory var8 = var6.makeCategory("Entity That Is Already Tracked");
			((EntityTrackerEntry) trackedEntityIDs.lookup(p_72785_1_.entityId)).myEntity.func_85029_a(var8);
			try
			{
				throw new ReportedException(var6);
			} catch(ReportedException var10)
			{
				System.err.println("\"Silently\" catching entity tracking error.");
				var10.printStackTrace();
			}
		}
	}
	
	public void func_85172_a(EntityPlayerMP p_85172_1_, Chunk p_85172_2_)
	{
		Iterator var3 = trackedEntities.iterator();
		while(var3.hasNext())
		{
			EntityTrackerEntry var4 = (EntityTrackerEntry) var3.next();
			if(var4.myEntity != p_85172_1_ && var4.myEntity.chunkCoordX == p_85172_2_.xPosition && var4.myEntity.chunkCoordZ == p_85172_2_.zPosition)
			{
				var4.tryStartWachingThis(p_85172_1_);
			}
		}
	}
	
	public void removeEntityFromAllTrackingPlayers(Entity p_72790_1_)
	{
		if(p_72790_1_ instanceof EntityPlayerMP)
		{
			EntityPlayerMP var2 = (EntityPlayerMP) p_72790_1_;
			Iterator var3 = trackedEntities.iterator();
			while(var3.hasNext())
			{
				EntityTrackerEntry var4 = (EntityTrackerEntry) var3.next();
				var4.removeFromWatchingList(var2);
			}
		}
		EntityTrackerEntry var5 = (EntityTrackerEntry) trackedEntityIDs.removeObject(p_72790_1_.entityId);
		if(var5 != null)
		{
			trackedEntities.remove(var5);
			var5.informAllAssociatedPlayersOfItemDestruction();
		}
	}
	
	public void removePlayerFromTrackers(EntityPlayerMP p_72787_1_)
	{
		Iterator var2 = trackedEntities.iterator();
		while(var2.hasNext())
		{
			EntityTrackerEntry var3 = (EntityTrackerEntry) var2.next();
			var3.removePlayerFromTracker(p_72787_1_);
		}
	}
	
	public void sendPacketToAllAssociatedPlayers(Entity p_72789_1_, Packet p_72789_2_)
	{
		EntityTrackerEntry var3 = (EntityTrackerEntry) trackedEntityIDs.lookup(p_72789_1_.entityId);
		if(var3 != null)
		{
			var3.sendPacketToAllAssociatedPlayers(p_72789_2_);
		}
	}
	
	public void sendPacketToAllPlayersTrackingEntity(Entity p_72784_1_, Packet p_72784_2_)
	{
		EntityTrackerEntry var3 = (EntityTrackerEntry) trackedEntityIDs.lookup(p_72784_1_.entityId);
		if(var3 != null)
		{
			var3.sendPacketToAllTrackingPlayers(p_72784_2_);
		}
	}
	
	public void updateTrackedEntities()
	{
		ArrayList var1 = new ArrayList();
		Iterator var2 = trackedEntities.iterator();
		while(var2.hasNext())
		{
			EntityTrackerEntry var3 = (EntityTrackerEntry) var2.next();
			var3.sendLocationToAllClients(theWorld.playerEntities);
			if(var3.playerEntitiesUpdated && var3.myEntity instanceof EntityPlayerMP)
			{
				var1.add(var3.myEntity);
			}
		}
		for(int var6 = 0; var6 < var1.size(); ++var6)
		{
			EntityPlayerMP var7 = (EntityPlayerMP) var1.get(var6);
			Iterator var4 = trackedEntities.iterator();
			while(var4.hasNext())
			{
				EntityTrackerEntry var5 = (EntityTrackerEntry) var4.next();
				if(var5.myEntity != var7)
				{
					var5.tryStartWachingThis(var7);
				}
			}
		}
	}
}
