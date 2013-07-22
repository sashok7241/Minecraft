package net.minecraft.src;

public abstract class EntityTameable extends EntityAnimal implements EntityOwnable
{
	protected EntityAISit aiSit = new EntityAISit(this);
	
	public EntityTameable(World par1World)
	{
		super(par1World);
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, "");
	}
	
	public EntityLivingBase func_130012_q()
	{
		return worldObj.getPlayerEntityByName(getOwnerName());
	}
	
	@Override public boolean func_142014_c(EntityLivingBase par1EntityLivingBase)
	{
		if(isTamed())
		{
			EntityLivingBase var2 = func_130012_q();
			if(par1EntityLivingBase == var2) return true;
			if(var2 != null) return var2.func_142014_c(par1EntityLivingBase);
		}
		return super.func_142014_c(par1EntityLivingBase);
	}
	
	public boolean func_142018_a(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase)
	{
		return true;
	}
	
	public EntityAISit func_70907_r()
	{
		return aiSit;
	}
	
	@Override public Entity getOwner()
	{
		return func_130012_q();
	}
	
	@Override public String getOwnerName()
	{
		return dataWatcher.getWatchableObjectString(17);
	}
	
	@Override public Team getTeam()
	{
		if(isTamed())
		{
			EntityLivingBase var1 = func_130012_q();
			if(var1 != null) return var1.getTeam();
		}
		return super.getTeam();
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
	
	protected void playTameEffect(boolean par1)
	{
		String var2 = "heart";
		if(!par1)
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
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		String var2 = par1NBTTagCompound.getString("Owner");
		if(var2.length() > 0)
		{
			setOwner(var2);
			setTamed(true);
		}
		aiSit.setSitting(par1NBTTagCompound.getBoolean("Sitting"));
		setSitting(par1NBTTagCompound.getBoolean("Sitting"));
	}
	
	public void setOwner(String par1Str)
	{
		dataWatcher.updateObject(17, par1Str);
	}
	
	public void setSitting(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(par1)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 1)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -2)));
		}
	}
	
	public void setTamed(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(par1)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 4)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -5)));
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		if(getOwnerName() == null)
		{
			par1NBTTagCompound.setString("Owner", "");
		} else
		{
			par1NBTTagCompound.setString("Owner", getOwnerName());
		}
		par1NBTTagCompound.setBoolean("Sitting", isSitting());
	}
}
