package net.minecraft.src;

public class EntitySlime extends EntityLiving implements IMob
{
	public float field_70813_a;
	public float field_70811_b;
	public float field_70812_c;
	private int slimeJumpDelay;
	
	public EntitySlime(World par1World)
	{
		super(par1World);
		int var2 = 1 << rand.nextInt(3);
		yOffset = 0.0F;
		slimeJumpDelay = rand.nextInt(20) + 10;
		setSlimeSize(var2);
	}
	
	protected boolean canDamagePlayer()
	{
		return getSlimeSize() > 1;
	}
	
	protected EntitySlime createInstance()
	{
		return new EntitySlime(worldObj);
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 1));
	}
	
	protected void func_70808_l()
	{
		field_70813_a *= 0.6F;
	}
	
	protected int getAttackStrength()
	{
		return getSlimeSize();
	}
	
	@Override public boolean getCanSpawnHere()
	{
		Chunk var1 = worldObj.getChunkFromBlockCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
		if(worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && rand.nextInt(4) != 1) return false;
		else
		{
			if(getSlimeSize() == 1 || worldObj.difficultySetting > 0)
			{
				BiomeGenBase var2 = worldObj.getBiomeGenForCoords(MathHelper.floor_double(posX), MathHelper.floor_double(posZ));
				if(var2 == BiomeGenBase.swampland && posY > 50.0D && posY < 70.0D && rand.nextFloat() < 0.5F && rand.nextFloat() < worldObj.func_130001_d() && worldObj.getBlockLightValue(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) <= rand.nextInt(8)) return super.getCanSpawnHere();
				if(rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911L).nextInt(10) == 0 && posY < 40.0D) return super.getCanSpawnHere();
			}
			return false;
		}
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}
	
	@Override protected int getDropItemId()
	{
		return getSlimeSize() == 1 ? Item.slimeBall.itemID : 0;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}
	
	protected int getJumpDelay()
	{
		return rand.nextInt(20) + 10;
	}
	
	protected String getJumpSound()
	{
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}
	
	protected String getSlimeParticle()
	{
		return "slime";
	}
	
	public int getSlimeSize()
	{
		return dataWatcher.getWatchableObjectByte(16);
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F * getSlimeSize();
	}
	
	@Override public int getVerticalFaceSpeed()
	{
		return 0;
	}
	
	protected boolean makesSoundOnJump()
	{
		return getSlimeSize() > 0;
	}
	
	protected boolean makesSoundOnLand()
	{
		return getSlimeSize() > 2;
	}
	
	@Override public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
	{
		if(canDamagePlayer())
		{
			int var2 = getSlimeSize();
			if(canEntityBeSeen(par1EntityPlayer) && getDistanceSqToEntity(par1EntityPlayer) < 0.6D * var2 * 0.6D * var2 && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength()))
			{
				playSound("mob.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}
		}
	}
	
	@Override public void onUpdate()
	{
		if(!worldObj.isRemote && worldObj.difficultySetting == 0 && getSlimeSize() > 0)
		{
			isDead = true;
		}
		field_70811_b += (field_70813_a - field_70811_b) * 0.5F;
		field_70812_c = field_70811_b;
		boolean var1 = onGround;
		super.onUpdate();
		int var2;
		if(onGround && !var1)
		{
			var2 = getSlimeSize();
			for(int var3 = 0; var3 < var2 * 8; ++var3)
			{
				float var4 = rand.nextFloat() * (float) Math.PI * 2.0F;
				float var5 = rand.nextFloat() * 0.5F + 0.5F;
				float var6 = MathHelper.sin(var4) * var2 * 0.5F * var5;
				float var7 = MathHelper.cos(var4) * var2 * 0.5F * var5;
				worldObj.spawnParticle(getSlimeParticle(), posX + var6, boundingBox.minY, posZ + var7, 0.0D, 0.0D, 0.0D);
			}
			if(makesSoundOnLand())
			{
				playSound(getJumpSound(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			}
			field_70813_a = -0.5F;
		} else if(!onGround && var1)
		{
			field_70813_a = 1.0F;
		}
		func_70808_l();
		if(worldObj.isRemote)
		{
			var2 = getSlimeSize();
			setSize(0.6F * var2, 0.6F * var2);
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setSlimeSize(par1NBTTagCompound.getInteger("Size") + 1);
	}
	
	@Override public void setDead()
	{
		int var1 = getSlimeSize();
		if(!worldObj.isRemote && var1 > 1 && func_110143_aJ() <= 0.0F)
		{
			int var2 = 2 + rand.nextInt(3);
			for(int var3 = 0; var3 < var2; ++var3)
			{
				float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
				float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
				EntitySlime var6 = createInstance();
				var6.setSlimeSize(var1 / 2);
				var6.setLocationAndAngles(posX + var4, posY + 0.5D, posZ + var5, rand.nextFloat() * 360.0F, 0.0F);
				worldObj.spawnEntityInWorld(var6);
			}
		}
		super.setDead();
	}
	
	protected void setSlimeSize(int par1)
	{
		dataWatcher.updateObject(16, new Byte((byte) par1));
		setSize(0.6F * par1, 0.6F * par1);
		setPosition(posX, posY, posZ);
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(par1 * par1);
		setEntityHealth(func_110138_aP());
		experienceValue = par1;
	}
	
	@Override protected void updateEntityActionState()
	{
		despawnEntity();
		EntityPlayer var1 = worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
		if(var1 != null)
		{
			faceEntity(var1, 10.0F, 20.0F);
		}
		if(onGround && slimeJumpDelay-- <= 0)
		{
			slimeJumpDelay = getJumpDelay();
			if(var1 != null)
			{
				slimeJumpDelay /= 3;
			}
			isJumping = true;
			if(makesSoundOnJump())
			{
				playSound(getJumpSound(), getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
			}
			moveStrafing = 1.0F - rand.nextFloat() * 2.0F;
			moveForward = 1 * getSlimeSize();
		} else
		{
			isJumping = false;
			if(onGround)
			{
				moveStrafing = moveForward = 0.0F;
			}
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Size", getSlimeSize() - 1);
	}
}
