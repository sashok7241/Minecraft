package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class MobSpawnerBaseLogic
{
	public int spawnDelay = 20;
	private String mobID = "Pig";
	private List minecartToSpawn;
	private WeightedRandomMinecart randomMinecart;
	public double field_98287_c;
	public double field_98284_d;
	private int minSpawnDelay = 200;
	private int maxSpawnDelay = 800;
	private int spawnCount = 4;
	private Entity field_98291_j;
	private int maxNearbyEntities = 6;
	private int activatingRangeFromPlayer = 16;
	private int spawnRange = 4;
	
	public boolean canRun()
	{
		return getSpawnerWorld().getClosestPlayer(getSpawnerX() + 0.5D, getSpawnerY() + 0.5D, getSpawnerZ() + 0.5D, activatingRangeFromPlayer) != null;
	}
	
	public Entity func_98265_a(Entity par1Entity)
	{
		if(getRandomMinecart() != null)
		{
			NBTTagCompound var2 = new NBTTagCompound();
			par1Entity.addEntityID(var2);
			Iterator var3 = getRandomMinecart().field_98222_b.getTags().iterator();
			while(var3.hasNext())
			{
				NBTBase var4 = (NBTBase) var3.next();
				var2.setTag(var4.getName(), var4.copy());
			}
			par1Entity.readFromNBT(var2);
			if(par1Entity.worldObj != null)
			{
				par1Entity.worldObj.spawnEntityInWorld(par1Entity);
			}
			NBTTagCompound var10;
			for(Entity var9 = par1Entity; var2.hasKey("Riding"); var2 = var10)
			{
				var10 = var2.getCompoundTag("Riding");
				Entity var5 = EntityList.createEntityByName(var10.getString("id"), par1Entity.worldObj);
				if(var5 != null)
				{
					NBTTagCompound var6 = new NBTTagCompound();
					var5.addEntityID(var6);
					Iterator var7 = var10.getTags().iterator();
					while(var7.hasNext())
					{
						NBTBase var8 = (NBTBase) var7.next();
						var6.setTag(var8.getName(), var8.copy());
					}
					var5.readFromNBT(var6);
					var5.setLocationAndAngles(var9.posX, var9.posY, var9.posZ, var9.rotationYaw, var9.rotationPitch);
					if(par1Entity.worldObj != null)
					{
						par1Entity.worldObj.spawnEntityInWorld(var5);
					}
					var9.mountEntity(var5);
				}
				var9 = var5;
			}
		} else if(par1Entity instanceof EntityLivingBase && par1Entity.worldObj != null)
		{
			((EntityLiving) par1Entity).func_110161_a((EntityLivingData) null);
			getSpawnerWorld().spawnEntityInWorld(par1Entity);
		}
		return par1Entity;
	}
	
	public abstract void func_98267_a(int var1);
	
	private void func_98273_j()
	{
		if(maxSpawnDelay <= minSpawnDelay)
		{
			spawnDelay = minSpawnDelay;
		} else
		{
			int var10003 = maxSpawnDelay - minSpawnDelay;
			spawnDelay = minSpawnDelay + getSpawnerWorld().rand.nextInt(var10003);
		}
		if(minecartToSpawn != null && minecartToSpawn.size() > 0)
		{
			setRandomMinecart((WeightedRandomMinecart) WeightedRandom.getRandomItem(getSpawnerWorld().rand, minecartToSpawn));
		}
		func_98267_a(1);
	}
	
	public Entity func_98281_h()
	{
		if(field_98291_j == null)
		{
			Entity var1 = EntityList.createEntityByName(getEntityNameToSpawn(), (World) null);
			var1 = func_98265_a(var1);
			field_98291_j = var1;
		}
		return field_98291_j;
	}
	
	public String getEntityNameToSpawn()
	{
		if(getRandomMinecart() == null)
		{
			if(mobID.equals("Minecart"))
			{
				mobID = "MinecartRideable";
			}
			return mobID;
		} else return getRandomMinecart().minecartName;
	}
	
	public WeightedRandomMinecart getRandomMinecart()
	{
		return randomMinecart;
	}
	
	public abstract World getSpawnerWorld();
	
	public abstract int getSpawnerX();
	
	public abstract int getSpawnerY();
	
	public abstract int getSpawnerZ();
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		mobID = par1NBTTagCompound.getString("EntityId");
		spawnDelay = par1NBTTagCompound.getShort("Delay");
		if(par1NBTTagCompound.hasKey("SpawnPotentials"))
		{
			minecartToSpawn = new ArrayList();
			NBTTagList var2 = par1NBTTagCompound.getTagList("SpawnPotentials");
			for(int var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				minecartToSpawn.add(new WeightedRandomMinecart(this, (NBTTagCompound) var2.tagAt(var3)));
			}
		} else
		{
			minecartToSpawn = null;
		}
		if(par1NBTTagCompound.hasKey("SpawnData"))
		{
			setRandomMinecart(new WeightedRandomMinecart(this, par1NBTTagCompound.getCompoundTag("SpawnData"), mobID));
		} else
		{
			setRandomMinecart((WeightedRandomMinecart) null);
		}
		if(par1NBTTagCompound.hasKey("MinSpawnDelay"))
		{
			minSpawnDelay = par1NBTTagCompound.getShort("MinSpawnDelay");
			maxSpawnDelay = par1NBTTagCompound.getShort("MaxSpawnDelay");
			spawnCount = par1NBTTagCompound.getShort("SpawnCount");
		}
		if(par1NBTTagCompound.hasKey("MaxNearbyEntities"))
		{
			maxNearbyEntities = par1NBTTagCompound.getShort("MaxNearbyEntities");
			activatingRangeFromPlayer = par1NBTTagCompound.getShort("RequiredPlayerRange");
		}
		if(par1NBTTagCompound.hasKey("SpawnRange"))
		{
			spawnRange = par1NBTTagCompound.getShort("SpawnRange");
		}
		if(getSpawnerWorld() != null && getSpawnerWorld().isRemote)
		{
			field_98291_j = null;
		}
	}
	
	public boolean setDelayToMin(int par1)
	{
		if(par1 == 1 && getSpawnerWorld().isRemote)
		{
			spawnDelay = minSpawnDelay;
			return true;
		} else return false;
	}
	
	public void setMobID(String par1Str)
	{
		mobID = par1Str;
	}
	
	public void setRandomMinecart(WeightedRandomMinecart par1WeightedRandomMinecart)
	{
		randomMinecart = par1WeightedRandomMinecart;
	}
	
	public void updateSpawner()
	{
		if(canRun())
		{
			double var5;
			if(getSpawnerWorld().isRemote)
			{
				double var1 = getSpawnerX() + getSpawnerWorld().rand.nextFloat();
				double var3 = getSpawnerY() + getSpawnerWorld().rand.nextFloat();
				var5 = getSpawnerZ() + getSpawnerWorld().rand.nextFloat();
				getSpawnerWorld().spawnParticle("smoke", var1, var3, var5, 0.0D, 0.0D, 0.0D);
				getSpawnerWorld().spawnParticle("flame", var1, var3, var5, 0.0D, 0.0D, 0.0D);
				if(spawnDelay > 0)
				{
					--spawnDelay;
				}
				field_98284_d = field_98287_c;
				field_98287_c = (field_98287_c + 1000.0F / (spawnDelay + 200.0F)) % 360.0D;
			} else
			{
				if(spawnDelay == -1)
				{
					func_98273_j();
				}
				if(spawnDelay > 0)
				{
					--spawnDelay;
					return;
				}
				boolean var12 = false;
				for(int var2 = 0; var2 < spawnCount; ++var2)
				{
					Entity var13 = EntityList.createEntityByName(getEntityNameToSpawn(), getSpawnerWorld());
					if(var13 == null) return;
					int var4 = getSpawnerWorld().getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().getAABB(getSpawnerX(), getSpawnerY(), getSpawnerZ(), getSpawnerX() + 1, getSpawnerY() + 1, getSpawnerZ() + 1).expand(spawnRange * 2, 4.0D, spawnRange * 2)).size();
					if(var4 >= maxNearbyEntities)
					{
						func_98273_j();
						return;
					}
					var5 = getSpawnerX() + (getSpawnerWorld().rand.nextDouble() - getSpawnerWorld().rand.nextDouble()) * spawnRange;
					double var7 = getSpawnerY() + getSpawnerWorld().rand.nextInt(3) - 1;
					double var9 = getSpawnerZ() + (getSpawnerWorld().rand.nextDouble() - getSpawnerWorld().rand.nextDouble()) * spawnRange;
					EntityLiving var11 = var13 instanceof EntityLiving ? (EntityLiving) var13 : null;
					var13.setLocationAndAngles(var5, var7, var9, getSpawnerWorld().rand.nextFloat() * 360.0F, 0.0F);
					if(var11 == null || var11.getCanSpawnHere())
					{
						func_98265_a(var13);
						getSpawnerWorld().playAuxSFX(2004, getSpawnerX(), getSpawnerY(), getSpawnerZ(), 0);
						if(var11 != null)
						{
							var11.spawnExplosionParticle();
						}
						var12 = true;
					}
				}
				if(var12)
				{
					func_98273_j();
				}
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setString("EntityId", getEntityNameToSpawn());
		par1NBTTagCompound.setShort("Delay", (short) spawnDelay);
		par1NBTTagCompound.setShort("MinSpawnDelay", (short) minSpawnDelay);
		par1NBTTagCompound.setShort("MaxSpawnDelay", (short) maxSpawnDelay);
		par1NBTTagCompound.setShort("SpawnCount", (short) spawnCount);
		par1NBTTagCompound.setShort("MaxNearbyEntities", (short) maxNearbyEntities);
		par1NBTTagCompound.setShort("RequiredPlayerRange", (short) activatingRangeFromPlayer);
		par1NBTTagCompound.setShort("SpawnRange", (short) spawnRange);
		if(getRandomMinecart() != null)
		{
			par1NBTTagCompound.setCompoundTag("SpawnData", (NBTTagCompound) getRandomMinecart().field_98222_b.copy());
		}
		if(getRandomMinecart() != null || minecartToSpawn != null && minecartToSpawn.size() > 0)
		{
			NBTTagList var2 = new NBTTagList();
			if(minecartToSpawn != null && minecartToSpawn.size() > 0)
			{
				Iterator var3 = minecartToSpawn.iterator();
				while(var3.hasNext())
				{
					WeightedRandomMinecart var4 = (WeightedRandomMinecart) var3.next();
					var2.appendTag(var4.func_98220_a());
				}
			} else
			{
				var2.appendTag(getRandomMinecart().func_98220_a());
			}
			par1NBTTagCompound.setTag("SpawnPotentials", var2);
		}
	}
}
