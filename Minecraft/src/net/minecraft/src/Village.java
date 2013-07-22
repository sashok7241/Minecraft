package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class Village
{
	private World worldObj;
	private final List villageDoorInfoList = new ArrayList();
	private final ChunkCoordinates centerHelper = new ChunkCoordinates(0, 0, 0);
	private final ChunkCoordinates center = new ChunkCoordinates(0, 0, 0);
	private int villageRadius = 0;
	private int lastAddDoorTimestamp = 0;
	private int tickCounter = 0;
	private int numVillagers = 0;
	private int noBreedTicks;
	private TreeMap playerReputation = new TreeMap();
	private List villageAgressors = new ArrayList();
	private int numIronGolems = 0;
	
	public Village()
	{
	}
	
	public Village(World p_i3511_1_)
	{
		worldObj = p_i3511_1_;
	}
	
	public void addOrRenewAgressor(EntityLiving p_75575_1_)
	{
		Iterator var2 = villageAgressors.iterator();
		VillageAgressor var3;
		do
		{
			if(!var2.hasNext())
			{
				villageAgressors.add(new VillageAgressor(this, p_75575_1_, tickCounter));
				return;
			}
			var3 = (VillageAgressor) var2.next();
		} while(var3.agressor != p_75575_1_);
		var3.agressionTime = tickCounter;
	}
	
	public void addVillageDoorInfo(VillageDoorInfo p_75576_1_)
	{
		villageDoorInfoList.add(p_75576_1_);
		centerHelper.posX += p_75576_1_.posX;
		centerHelper.posY += p_75576_1_.posY;
		centerHelper.posZ += p_75576_1_.posZ;
		updateVillageRadiusAndCenter();
		lastAddDoorTimestamp = p_75576_1_.lastActivityTimestamp;
	}
	
	public void endMatingSeason()
	{
		noBreedTicks = tickCounter;
	}
	
	public VillageDoorInfo findNearestDoor(int p_75564_1_, int p_75564_2_, int p_75564_3_)
	{
		VillageDoorInfo var4 = null;
		int var5 = Integer.MAX_VALUE;
		Iterator var6 = villageDoorInfoList.iterator();
		while(var6.hasNext())
		{
			VillageDoorInfo var7 = (VillageDoorInfo) var6.next();
			int var8 = var7.getDistanceSquared(p_75564_1_, p_75564_2_, p_75564_3_);
			if(var8 < var5)
			{
				var4 = var7;
				var5 = var8;
			}
		}
		return var4;
	}
	
	public VillageDoorInfo findNearestDoorUnrestricted(int p_75569_1_, int p_75569_2_, int p_75569_3_)
	{
		VillageDoorInfo var4 = null;
		int var5 = Integer.MAX_VALUE;
		Iterator var6 = villageDoorInfoList.iterator();
		while(var6.hasNext())
		{
			VillageDoorInfo var7 = (VillageDoorInfo) var6.next();
			int var8 = var7.getDistanceSquared(p_75569_1_, p_75569_2_, p_75569_3_);
			if(var8 > 256)
			{
				var8 *= 1000;
			} else
			{
				var8 = var7.getDoorOpeningRestrictionCounter();
			}
			if(var8 < var5)
			{
				var4 = var7;
				var5 = var8;
			}
		}
		return var4;
	}
	
	public EntityLiving findNearestVillageAggressor(EntityLiving p_75571_1_)
	{
		double var2 = Double.MAX_VALUE;
		VillageAgressor var4 = null;
		for(int var5 = 0; var5 < villageAgressors.size(); ++var5)
		{
			VillageAgressor var6 = (VillageAgressor) villageAgressors.get(var5);
			double var7 = var6.agressor.getDistanceSqToEntity(p_75571_1_);
			if(var7 <= var2)
			{
				var4 = var6;
				var2 = var7;
			}
		}
		return var4 != null ? var4.agressor : null;
	}
	
	public void func_82683_b(int p_82683_1_)
	{
		Iterator var2 = playerReputation.keySet().iterator();
		while(var2.hasNext())
		{
			String var3 = (String) var2.next();
			setReputationForPlayer(var3, p_82683_1_);
		}
	}
	
	public EntityPlayer func_82685_c(EntityLiving p_82685_1_)
	{
		double var2 = Double.MAX_VALUE;
		EntityPlayer var4 = null;
		Iterator var5 = playerReputation.keySet().iterator();
		while(var5.hasNext())
		{
			String var6 = (String) var5.next();
			if(isPlayerReputationTooLow(var6))
			{
				EntityPlayer var7 = worldObj.getPlayerEntityByName(var6);
				if(var7 != null)
				{
					double var8 = var7.getDistanceSqToEntity(p_82685_1_);
					if(var8 <= var2)
					{
						var4 = var7;
						var2 = var8;
					}
				}
			}
		}
		return var4;
	}
	
	public void func_82691_a(World p_82691_1_)
	{
		worldObj = p_82691_1_;
	}
	
	public ChunkCoordinates getCenter()
	{
		return center;
	}
	
	public int getNumVillageDoors()
	{
		return villageDoorInfoList.size();
	}
	
	public int getNumVillagers()
	{
		return numVillagers;
	}
	
	public int getReputationForPlayer(String p_82684_1_)
	{
		Integer var2 = (Integer) playerReputation.get(p_82684_1_);
		return var2 != null ? var2.intValue() : 0;
	}
	
	public int getTicksSinceLastDoorAdding()
	{
		return tickCounter - lastAddDoorTimestamp;
	}
	
	public VillageDoorInfo getVillageDoorAt(int p_75578_1_, int p_75578_2_, int p_75578_3_)
	{
		if(center.getDistanceSquared(p_75578_1_, p_75578_2_, p_75578_3_) > villageRadius * villageRadius) return null;
		else
		{
			Iterator var4 = villageDoorInfoList.iterator();
			VillageDoorInfo var5;
			do
			{
				if(!var4.hasNext()) return null;
				var5 = (VillageDoorInfo) var4.next();
			} while(var5.posX != p_75578_1_ || var5.posZ != p_75578_3_ || Math.abs(var5.posY - p_75578_2_) > 1);
			return var5;
		}
	}
	
	public List getVillageDoorInfoList()
	{
		return villageDoorInfoList;
	}
	
	public int getVillageRadius()
	{
		return villageRadius;
	}
	
	public boolean isAnnihilated()
	{
		return villageDoorInfoList.isEmpty();
	}
	
	private boolean isBlockDoor(int p_75574_1_, int p_75574_2_, int p_75574_3_)
	{
		int var4 = worldObj.getBlockId(p_75574_1_, p_75574_2_, p_75574_3_);
		return var4 <= 0 ? false : var4 == Block.doorWood.blockID;
	}
	
	public boolean isInRange(int p_75570_1_, int p_75570_2_, int p_75570_3_)
	{
		return center.getDistanceSquared(p_75570_1_, p_75570_2_, p_75570_3_) < villageRadius * villageRadius;
	}
	
	public boolean isMatingSeason()
	{
		return noBreedTicks == 0 || tickCounter - noBreedTicks >= 3600;
	}
	
	public boolean isPlayerReputationTooLow(String p_82687_1_)
	{
		return getReputationForPlayer(p_82687_1_) <= -15;
	}
	
	private boolean isValidIronGolemSpawningLocation(int p_75563_1_, int p_75563_2_, int p_75563_3_, int p_75563_4_, int p_75563_5_, int p_75563_6_)
	{
		if(!worldObj.doesBlockHaveSolidTopSurface(p_75563_1_, p_75563_2_ - 1, p_75563_3_)) return false;
		else
		{
			int var7 = p_75563_1_ - p_75563_4_ / 2;
			int var8 = p_75563_3_ - p_75563_6_ / 2;
			for(int var9 = var7; var9 < var7 + p_75563_4_; ++var9)
			{
				for(int var10 = p_75563_2_; var10 < p_75563_2_ + p_75563_5_; ++var10)
				{
					for(int var11 = var8; var11 < var8 + p_75563_6_; ++var11)
					{
						if(worldObj.isBlockNormalCube(var9, var10, var11)) return false;
					}
				}
			}
			return true;
		}
	}
	
	public void readVillageDataFromNBT(NBTTagCompound p_82690_1_)
	{
		numVillagers = p_82690_1_.getInteger("PopSize");
		villageRadius = p_82690_1_.getInteger("Radius");
		numIronGolems = p_82690_1_.getInteger("Golems");
		lastAddDoorTimestamp = p_82690_1_.getInteger("Stable");
		tickCounter = p_82690_1_.getInteger("Tick");
		noBreedTicks = p_82690_1_.getInteger("MTick");
		center.posX = p_82690_1_.getInteger("CX");
		center.posY = p_82690_1_.getInteger("CY");
		center.posZ = p_82690_1_.getInteger("CZ");
		centerHelper.posX = p_82690_1_.getInteger("ACX");
		centerHelper.posY = p_82690_1_.getInteger("ACY");
		centerHelper.posZ = p_82690_1_.getInteger("ACZ");
		NBTTagList var2 = p_82690_1_.getTagList("Doors");
		for(int var3 = 0; var3 < var2.tagCount(); ++var3)
		{
			NBTTagCompound var4 = (NBTTagCompound) var2.tagAt(var3);
			VillageDoorInfo var5 = new VillageDoorInfo(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z"), var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
			villageDoorInfoList.add(var5);
		}
		NBTTagList var6 = p_82690_1_.getTagList("Players");
		for(int var7 = 0; var7 < var6.tagCount(); ++var7)
		{
			NBTTagCompound var8 = (NBTTagCompound) var6.tagAt(var7);
			playerReputation.put(var8.getString("Name"), Integer.valueOf(var8.getInteger("S")));
		}
	}
	
	private void removeDeadAndOldAgressors()
	{
		Iterator var1 = villageAgressors.iterator();
		while(var1.hasNext())
		{
			VillageAgressor var2 = (VillageAgressor) var1.next();
			if(!var2.agressor.isEntityAlive() || Math.abs(tickCounter - var2.agressionTime) > 300)
			{
				var1.remove();
			}
		}
	}
	
	private void removeDeadAndOutOfRangeDoors()
	{
		boolean var1 = false;
		boolean var2 = worldObj.rand.nextInt(50) == 0;
		Iterator var3 = villageDoorInfoList.iterator();
		while(var3.hasNext())
		{
			VillageDoorInfo var4 = (VillageDoorInfo) var3.next();
			if(var2)
			{
				var4.resetDoorOpeningRestrictionCounter();
			}
			if(!isBlockDoor(var4.posX, var4.posY, var4.posZ) || Math.abs(tickCounter - var4.lastActivityTimestamp) > 1200)
			{
				centerHelper.posX -= var4.posX;
				centerHelper.posY -= var4.posY;
				centerHelper.posZ -= var4.posZ;
				var1 = true;
				var4.isDetachedFromVillageFlag = true;
				var3.remove();
			}
		}
		if(var1)
		{
			updateVillageRadiusAndCenter();
		}
	}
	
	public int setReputationForPlayer(String p_82688_1_, int p_82688_2_)
	{
		int var3 = getReputationForPlayer(p_82688_1_);
		int var4 = MathHelper.clamp_int(var3 + p_82688_2_, -30, 10);
		playerReputation.put(p_82688_1_, Integer.valueOf(var4));
		return var4;
	}
	
	public void tick(int p_75560_1_)
	{
		tickCounter = p_75560_1_;
		removeDeadAndOutOfRangeDoors();
		removeDeadAndOldAgressors();
		if(p_75560_1_ % 20 == 0)
		{
			updateNumVillagers();
		}
		if(p_75560_1_ % 30 == 0)
		{
			updateNumIronGolems();
		}
		int var2 = numVillagers / 10;
		if(numIronGolems < var2 && villageDoorInfoList.size() > 20 && worldObj.rand.nextInt(7000) == 0)
		{
			Vec3 var3 = tryGetIronGolemSpawningLocation(MathHelper.floor_float(center.posX), MathHelper.floor_float(center.posY), MathHelper.floor_float(center.posZ), 2, 4, 2);
			if(var3 != null)
			{
				EntityIronGolem var4 = new EntityIronGolem(worldObj);
				var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
				worldObj.spawnEntityInWorld(var4);
				++numIronGolems;
			}
		}
	}
	
	private Vec3 tryGetIronGolemSpawningLocation(int p_75559_1_, int p_75559_2_, int p_75559_3_, int p_75559_4_, int p_75559_5_, int p_75559_6_)
	{
		for(int var7 = 0; var7 < 10; ++var7)
		{
			int var8 = p_75559_1_ + worldObj.rand.nextInt(16) - 8;
			int var9 = p_75559_2_ + worldObj.rand.nextInt(6) - 3;
			int var10 = p_75559_3_ + worldObj.rand.nextInt(16) - 8;
			if(isInRange(var8, var9, var10) && isValidIronGolemSpawningLocation(var8, var9, var10, p_75559_4_, p_75559_5_, p_75559_6_)) return worldObj.getWorldVec3Pool().getVecFromPool(var8, var9, var10);
		}
		return null;
	}
	
	private void updateNumIronGolems()
	{
		List var1 = worldObj.getEntitiesWithinAABB(EntityIronGolem.class, AxisAlignedBB.getAABBPool().getAABB(center.posX - villageRadius, center.posY - 4, center.posZ - villageRadius, center.posX + villageRadius, center.posY + 4, center.posZ + villageRadius));
		numIronGolems = var1.size();
	}
	
	private void updateNumVillagers()
	{
		List var1 = worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getAABBPool().getAABB(center.posX - villageRadius, center.posY - 4, center.posZ - villageRadius, center.posX + villageRadius, center.posY + 4, center.posZ + villageRadius));
		numVillagers = var1.size();
		if(numVillagers == 0)
		{
			playerReputation.clear();
		}
	}
	
	private void updateVillageRadiusAndCenter()
	{
		int var1 = villageDoorInfoList.size();
		if(var1 == 0)
		{
			center.set(0, 0, 0);
			villageRadius = 0;
		} else
		{
			center.set(centerHelper.posX / var1, centerHelper.posY / var1, centerHelper.posZ / var1);
			int var2 = 0;
			VillageDoorInfo var4;
			for(Iterator var3 = villageDoorInfoList.iterator(); var3.hasNext(); var2 = Math.max(var4.getDistanceSquared(center.posX, center.posY, center.posZ), var2))
			{
				var4 = (VillageDoorInfo) var3.next();
			}
			villageRadius = Math.max(32, (int) Math.sqrt(var2) + 1);
		}
	}
	
	public void writeVillageDataToNBT(NBTTagCompound p_82689_1_)
	{
		p_82689_1_.setInteger("PopSize", numVillagers);
		p_82689_1_.setInteger("Radius", villageRadius);
		p_82689_1_.setInteger("Golems", numIronGolems);
		p_82689_1_.setInteger("Stable", lastAddDoorTimestamp);
		p_82689_1_.setInteger("Tick", tickCounter);
		p_82689_1_.setInteger("MTick", noBreedTicks);
		p_82689_1_.setInteger("CX", center.posX);
		p_82689_1_.setInteger("CY", center.posY);
		p_82689_1_.setInteger("CZ", center.posZ);
		p_82689_1_.setInteger("ACX", centerHelper.posX);
		p_82689_1_.setInteger("ACY", centerHelper.posY);
		p_82689_1_.setInteger("ACZ", centerHelper.posZ);
		NBTTagList var2 = new NBTTagList("Doors");
		Iterator var3 = villageDoorInfoList.iterator();
		while(var3.hasNext())
		{
			VillageDoorInfo var4 = (VillageDoorInfo) var3.next();
			NBTTagCompound var5 = new NBTTagCompound("Door");
			var5.setInteger("X", var4.posX);
			var5.setInteger("Y", var4.posY);
			var5.setInteger("Z", var4.posZ);
			var5.setInteger("IDX", var4.insideDirectionX);
			var5.setInteger("IDZ", var4.insideDirectionZ);
			var5.setInteger("TS", var4.lastActivityTimestamp);
			var2.appendTag(var5);
		}
		p_82689_1_.setTag("Doors", var2);
		NBTTagList var7 = new NBTTagList("Players");
		Iterator var8 = playerReputation.keySet().iterator();
		while(var8.hasNext())
		{
			String var9 = (String) var8.next();
			NBTTagCompound var6 = new NBTTagCompound(var9);
			var6.setString("Name", var9);
			var6.setInteger("S", ((Integer) playerReputation.get(var9)).intValue());
			var7.appendTag(var6);
		}
		p_82689_1_.setTag("Players", var7);
	}
}
