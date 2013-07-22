package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class MobSpawnerBaseLogic
{
	public int spawnDelay = 20;
	private String mobID = "Pig";
	private List minecartToSpawn = null;
	private WeightedRandomMinecart randomMinecart = null;
	public double field_98287_c;
	public double field_98284_d = 0.0D;
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
	
	public Entity func_98265_a(Entity p_98265_1_)
	{
		if(getRandomMinecart() != null)
		{
			NBTTagCompound var2 = new NBTTagCompound();
			p_98265_1_.addEntityID(var2);
			Iterator var3 = getRandomMinecart().field_98222_b.getTags().iterator();
			while(var3.hasNext())
			{
				NBTBase var4 = (NBTBase) var3.next();
				var2.setTag(var4.getName(), var4.copy());
			}
			p_98265_1_.readFromNBT(var2);
			if(p_98265_1_.worldObj != null)
			{
				p_98265_1_.worldObj.spawnEntityInWorld(p_98265_1_);
			}
			NBTTagCompound var10;
			for(Entity var9 = p_98265_1_; var2.hasKey("Riding"); var2 = var10)
			{
				var10 = var2.getCompoundTag("Riding");
				Entity var5 = EntityList.createEntityByName(var10.getString("id"), getSpawnerWorld());
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
					getSpawnerWorld().spawnEntityInWorld(var5);
					var9.mountEntity(var5);
				}
				var9 = var5;
			}
		} else if(p_98265_1_ instanceof EntityLiving && p_98265_1_.worldObj != null)
		{
			((EntityLiving) p_98265_1_).initCreature();
			getSpawnerWorld().spawnEntityInWorld(p_98265_1_);
		}
		return p_98265_1_;
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
	
	public void readFromNBT(NBTTagCompound p_98270_1_)
	{
		mobID = p_98270_1_.getString("EntityId");
		spawnDelay = p_98270_1_.getShort("Delay");
		if(p_98270_1_.hasKey("SpawnPotentials"))
		{
			minecartToSpawn = new ArrayList();
			NBTTagList var2 = p_98270_1_.getTagList("SpawnPotentials");
			for(int var3 = 0; var3 < var2.tagCount(); ++var3)
			{
				minecartToSpawn.add(new WeightedRandomMinecart(this, (NBTTagCompound) var2.tagAt(var3)));
			}
		} else
		{
			minecartToSpawn = null;
		}
		if(p_98270_1_.hasKey("SpawnData"))
		{
			setRandomMinecart(new WeightedRandomMinecart(this, p_98270_1_.getCompoundTag("SpawnData"), mobID));
		} else
		{
			setRandomMinecart((WeightedRandomMinecart) null);
		}
		if(p_98270_1_.hasKey("MinSpawnDelay"))
		{
			minSpawnDelay = p_98270_1_.getShort("MinSpawnDelay");
			maxSpawnDelay = p_98270_1_.getShort("MaxSpawnDelay");
			spawnCount = p_98270_1_.getShort("SpawnCount");
		}
		if(p_98270_1_.hasKey("MaxNearbyEntities"))
		{
			maxNearbyEntities = p_98270_1_.getShort("MaxNearbyEntities");
			activatingRangeFromPlayer = p_98270_1_.getShort("RequiredPlayerRange");
		}
		if(p_98270_1_.hasKey("SpawnRange"))
		{
			spawnRange = p_98270_1_.getShort("SpawnRange");
		}
		if(getSpawnerWorld() != null && getSpawnerWorld().isRemote)
		{
			field_98291_j = null;
		}
	}
	
	public boolean setDelayToMin(int p_98268_1_)
	{
		if(p_98268_1_ == 1 && getSpawnerWorld().isRemote)
		{
			spawnDelay = minSpawnDelay;
			return true;
		} else return false;
	}
	
	public void setMobID(String p_98272_1_)
	{
		mobID = p_98272_1_;
	}
	
	public void setRandomMinecart(WeightedRandomMinecart p_98277_1_)
	{
		randomMinecart = p_98277_1_;
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
	
	public void writeToNBT(NBTTagCompound p_98280_1_)
	{
		p_98280_1_.setString("EntityId", getEntityNameToSpawn());
		p_98280_1_.setShort("Delay", (short) spawnDelay);
		p_98280_1_.setShort("MinSpawnDelay", (short) minSpawnDelay);
		p_98280_1_.setShort("MaxSpawnDelay", (short) maxSpawnDelay);
		p_98280_1_.setShort("SpawnCount", (short) spawnCount);
		p_98280_1_.setShort("MaxNearbyEntities", (short) maxNearbyEntities);
		p_98280_1_.setShort("RequiredPlayerRange", (short) activatingRangeFromPlayer);
		p_98280_1_.setShort("SpawnRange", (short) spawnRange);
		if(getRandomMinecart() != null)
		{
			p_98280_1_.setCompoundTag("SpawnData", (NBTTagCompound) getRandomMinecart().field_98222_b.copy());
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
			p_98280_1_.setTag("SpawnPotentials", var2);
		}
	}
}
