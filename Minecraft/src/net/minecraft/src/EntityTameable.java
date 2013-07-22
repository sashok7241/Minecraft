package net.minecraft.src;

public abstract class EntityTameable extends EntityAnimal
{
	protected EntityAISit aiSit = new EntityAISit(this);
	
	public EntityTameable(World p_i3452_1_)
	{
		super(p_i3452_1_);
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, "");
	}
	
	public EntityAISit func_70907_r()
	{
		return aiSit;
	}
	
	public EntityLiving getOwner()
	{
		return worldObj.getPlayerEntityByName(getOwnerName());
	}
	
	public String getOwnerName()
	{
		return dataWatcher.getWatchableObjectString(17);
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 7)
		{
			playTameEffect(true);
		} else if(par1 == 6)
		{
			playTameEffect(false);
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	public boolean isSitting()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}
	
	public boolean isTamed()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 4) != 0;
	}
	
	protected void playTameEffect(boolean p_70908_1_)
	{
		String var2 = "heart";
		if(!p_70908_1_)
		{
			var2 = "smoke";
		}
		for(int var3 = 0; var3 < 7; ++var3)
		{
			double var4 = rand.nextGaussian() * 0.02D;
			double var6 = rand.nextGaussian() * 0.02D;
			double var8 = rand.nextGaussian() * 0.02D;
			worldObj.spawnParticle(var2, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var4, var6, var8);
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		String var2 = p_70037_1_.getString("Owner");
		if(var2.length() > 0)
		{
			setOwner(var2);
			setTamed(true);
		}
		aiSit.setSitting(p_70037_1_.getBoolean("Sitting"));
		setSitting(p_70037_1_.getBoolean("Sitting"));
	}
	
	public void setOwner(String p_70910_1_)
	{
		dataWatcher.updateObject(17, p_70910_1_);
	}
	
	public void setSitting(boolean p_70904_1_)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(p_70904_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}
	
	public void setTamed(boolean p_70903_1_)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(p_70903_1_)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 4)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -5)));
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		if(getOwnerName() == null)
		{
			p_70014_1_.setString("Owner", "");
		} else
		{
			p_70014_1_.setString("Owner", getOwnerName());
		}
		p_70014_1_.setBoolean("Sitting", isSitting());
	}
}
