package net.minecraft.src;

import java.util.List;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
	private float[] field_82220_d = new float[2];
	private float[] field_82221_e = new float[2];
	private float[] field_82217_f = new float[2];
	private float[] field_82218_g = new float[2];
	private int[] field_82223_h = new int[2];
	private int[] field_82224_i = new int[2];
	private int field_82222_j;
	private static final IEntitySelector attackEntitySelector = new EntityWitherAttackFilter();
	
	public EntityWither(World p_i5065_1_)
	{
		super(p_i5065_1_);
		setEntityHealth(getMaxHealth());
		texture = "/mob/wither.png";
		setSize(0.9F, 4.0F);
		isImmuneToFire = true;
		moveSpeed = 0.6F;
		getNavigator().setCanSwim(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIArrowAttack(this, moveSpeed, 40, 20.0F));
		tasks.addTask(5, new EntityAIWander(this, moveSpeed));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 30.0F, 0, false, false, attackEntitySelector));
		experienceValue = 50;
	}
	
	@Override public void addPotionEffect(PotionEffect p_70690_1_)
	{
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else if(p_70097_1_ == DamageSource.drown) return false;
		else if(func_82212_n() > 0) return false;
		else
		{
			Entity var3;
			if(isArmored())
			{
				var3 = p_70097_1_.getSourceOfDamage();
				if(var3 instanceof EntityArrow) return false;
			}
			var3 = p_70097_1_.getEntity();
			if(var3 != null && !(var3 instanceof EntityPlayer) && var3 instanceof EntityLiving && ((EntityLiving) var3).getCreatureAttribute() == getCreatureAttribute()) return false;
			else
			{
				if(field_82222_j <= 0)
				{
					field_82222_j = 20;
				}
				for(int var4 = 0; var4 < field_82224_i.length; ++var4)
				{
					field_82224_i[var4] += 3;
				}
				return super.attackEntityFrom(p_70097_1_, p_70097_2_);
			}
		}
	}
	
	@Override public void attackEntityWithRangedAttack(EntityLiving p_82196_1_, float p_82196_2_)
	{
		func_82216_a(0, p_82196_1_);
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return !isDead;
	}
	
	@Override protected void despawnEntity()
	{
		entityAge = 0;
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		dropItem(Item.netherStar.itemID, 1);
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Integer(100));
		dataWatcher.addObject(17, new Integer(0));
		dataWatcher.addObject(18, new Integer(0));
		dataWatcher.addObject(19, new Integer(0));
		dataWatcher.addObject(20, new Integer(0));
	}
	
	@Override protected void fall(float p_70069_1_)
	{
	}
	
	private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_)
	{
		float var4 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
		if(var4 > p_82204_3_)
		{
			var4 = p_82204_3_;
		}
		if(var4 < -p_82204_3_)
		{
			var4 = -p_82204_3_;
		}
		return p_82204_1_ + var4;
	}
	
	public void func_82206_m()
	{
		func_82215_s(220);
		setEntityHealth(getMaxHealth() / 3);
	}
	
	public float func_82207_a(int par1)
	{
		return field_82221_e[par1];
	}
	
	private double func_82208_v(int p_82208_1_)
	{
		return p_82208_1_ <= 0 ? posY + 3.0D : posY + 2.2D;
	}
	
	private void func_82209_a(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
	{
		worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1014, (int) posX, (int) posY, (int) posZ, 0);
		double var9 = func_82214_u(p_82209_1_);
		double var11 = func_82208_v(p_82209_1_);
		double var13 = func_82213_w(p_82209_1_);
		double var15 = p_82209_2_ - var9;
		double var17 = p_82209_4_ - var11;
		double var19 = p_82209_6_ - var13;
		EntityWitherSkull var21 = new EntityWitherSkull(worldObj, this, var15, var17, var19);
		if(p_82209_8_)
		{
			var21.setInvulnerable(true);
		}
		var21.posY = var11;
		var21.posX = var9;
		var21.posZ = var13;
		worldObj.spawnEntityInWorld(var21);
	}
	
	public float func_82210_r(int par1)
	{
		return field_82220_d[par1];
	}
	
	public void func_82211_c(int p_82211_1_, int p_82211_2_)
	{
		dataWatcher.updateObject(17 + p_82211_1_, Integer.valueOf(p_82211_2_));
	}
	
	public int func_82212_n()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}
	
	private double func_82213_w(int p_82213_1_)
	{
		if(p_82213_1_ <= 0) return posZ;
		else
		{
			float var2 = (renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * (float) Math.PI;
			float var3 = MathHelper.sin(var2);
			return posZ + var3 * 1.3D;
		}
	}
	
	private double func_82214_u(int p_82214_1_)
	{
		if(p_82214_1_ <= 0) return posX;
		else
		{
			float var2 = (renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * (float) Math.PI;
			float var3 = MathHelper.cos(var2);
			return posX + var3 * 1.3D;
		}
	}
	
	public void func_82215_s(int p_82215_1_)
	{
		dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
	}
	
	private void func_82216_a(int p_82216_1_, EntityLiving p_82216_2_)
	{
		func_82209_a(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, p_82216_1_ == 0 && rand.nextFloat() < 0.001F);
	}
	
	@Override public int getBossHealth()
	{
		return dataWatcher.getWatchableObjectInt(16);
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}
	
	@Override public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.wither.death";
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.wither.hurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.wither.idle";
	}
	
	@Override public int getMaxHealth()
	{
		return 300;
	}
	
	@Override public float getShadowSize()
	{
		return height / 8.0F;
	}
	
	@Override public String getTexture()
	{
		int var1 = func_82212_n();
		return var1 > 0 && (var1 > 80 || var1 / 5 % 2 != 1) ? "/mob/wither_invul.png" : "/mob/wither.png";
	}
	
	@Override public int getTotalArmorValue()
	{
		return 4;
	}
	
	public int getWatchedTargetId(int p_82203_1_)
	{
		return dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
	}
	
	@Override protected boolean isAIEnabled()
	{
		return true;
	}
	
	public boolean isArmored()
	{
		return getBossHealth() <= getMaxHealth() / 2;
	}
	
	@Override public void mountEntity(Entity p_70078_1_)
	{
		ridingEntity = null;
	}
	
	@Override public void onLivingUpdate()
	{
		if(!worldObj.isRemote)
		{
			dataWatcher.updateObject(16, Integer.valueOf(health));
		}
		motionY *= 0.6000000238418579D;
		double var4;
		double var6;
		double var8;
		if(!worldObj.isRemote && getWatchedTargetId(0) > 0)
		{
			Entity var1 = worldObj.getEntityByID(getWatchedTargetId(0));
			if(var1 != null)
			{
				if(posY < var1.posY || !isArmored() && posY < var1.posY + 5.0D)
				{
					if(motionY < 0.0D)
					{
						motionY = 0.0D;
					}
					motionY += (0.5D - motionY) * 0.6000000238418579D;
				}
				double var2 = var1.posX - posX;
				var4 = var1.posZ - posZ;
				var6 = var2 * var2 + var4 * var4;
				if(var6 > 9.0D)
				{
					var8 = MathHelper.sqrt_double(var6);
					motionX += (var2 / var8 * 0.5D - motionX) * 0.6000000238418579D;
					motionZ += (var4 / var8 * 0.5D - motionZ) * 0.6000000238418579D;
				}
			}
		}
		if(motionX * motionX + motionZ * motionZ > 0.05000000074505806D)
		{
			rotationYaw = (float) Math.atan2(motionZ, motionX) * (180F / (float) Math.PI) - 90.0F;
		}
		super.onLivingUpdate();
		int var20;
		for(var20 = 0; var20 < 2; ++var20)
		{
			field_82218_g[var20] = field_82221_e[var20];
			field_82217_f[var20] = field_82220_d[var20];
		}
		int var21;
		for(var20 = 0; var20 < 2; ++var20)
		{
			var21 = getWatchedTargetId(var20 + 1);
			Entity var3 = null;
			if(var21 > 0)
			{
				var3 = worldObj.getEntityByID(var21);
			}
			if(var3 != null)
			{
				var4 = func_82214_u(var20 + 1);
				var6 = func_82208_v(var20 + 1);
				var8 = func_82213_w(var20 + 1);
				double var10 = var3.posX - var4;
				double var12 = var3.posY + var3.getEyeHeight() - var6;
				double var14 = var3.posZ - var8;
				double var16 = MathHelper.sqrt_double(var10 * var10 + var14 * var14);
				float var18 = (float) (Math.atan2(var14, var10) * 180.0D / Math.PI) - 90.0F;
				float var19 = (float) -(Math.atan2(var12, var16) * 180.0D / Math.PI);
				field_82220_d[var20] = func_82204_b(field_82220_d[var20], var19, 40.0F);
				field_82221_e[var20] = func_82204_b(field_82221_e[var20], var18, 10.0F);
			} else
			{
				field_82221_e[var20] = func_82204_b(field_82221_e[var20], renderYawOffset, 10.0F);
			}
		}
		boolean var22 = isArmored();
		for(var21 = 0; var21 < 3; ++var21)
		{
			double var23 = func_82214_u(var21);
			double var5 = func_82208_v(var21);
			double var7 = func_82213_w(var21);
			worldObj.spawnParticle("smoke", var23 + rand.nextGaussian() * 0.30000001192092896D, var5 + rand.nextGaussian() * 0.30000001192092896D, var7 + rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
			if(var22 && worldObj.rand.nextInt(4) == 0)
			{
				worldObj.spawnParticle("mobSpell", var23 + rand.nextGaussian() * 0.30000001192092896D, var5 + rand.nextGaussian() * 0.30000001192092896D, var7 + rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
			}
		}
		if(func_82212_n() > 0)
		{
			for(var21 = 0; var21 < 3; ++var21)
			{
				worldObj.spawnParticle("mobSpell", posX + rand.nextGaussian() * 1.0D, posY + rand.nextFloat() * 3.3F, posZ + rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
			}
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		func_82215_s(p_70037_1_.getInteger("Invul"));
		dataWatcher.updateObject(16, Integer.valueOf(health));
	}
	
	@Override public void setInWeb()
	{
	}
	
	@Override protected void updateAITasks()
	{
		int var1;
		if(func_82212_n() > 0)
		{
			var1 = func_82212_n() - 1;
			if(var1 <= 0)
			{
				worldObj.newExplosion(this, posX, posY + getEyeHeight(), posZ, 7.0F, false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
				worldObj.func_82739_e(1013, (int) posX, (int) posY, (int) posZ, 0);
			}
			func_82215_s(var1);
			if(ticksExisted % 10 == 0)
			{
				heal(10);
			}
		} else
		{
			super.updateAITasks();
			int var12;
			for(var1 = 1; var1 < 3; ++var1)
			{
				if(ticksExisted >= field_82223_h[var1 - 1])
				{
					field_82223_h[var1 - 1] = ticksExisted + 10 + rand.nextInt(10);
					if(worldObj.difficultySetting >= 2)
					{
						int var10001 = var1 - 1;
						int var10003 = field_82224_i[var1 - 1];
						field_82224_i[var10001] = field_82224_i[var1 - 1] + 1;
						if(var10003 > 15)
						{
							float var2 = 10.0F;
							float var3 = 5.0F;
							double var4 = MathHelper.getRandomDoubleInRange(rand, posX - var2, posX + var2);
							double var6 = MathHelper.getRandomDoubleInRange(rand, posY - var3, posY + var3);
							double var8 = MathHelper.getRandomDoubleInRange(rand, posZ - var2, posZ + var2);
							func_82209_a(var1 + 1, var4, var6, var8, true);
							field_82224_i[var1 - 1] = 0;
						}
					}
					var12 = getWatchedTargetId(var1);
					if(var12 > 0)
					{
						Entity var14 = worldObj.getEntityByID(var12);
						if(var14 != null && var14.isEntityAlive() && getDistanceSqToEntity(var14) <= 900.0D && canEntityBeSeen(var14))
						{
							func_82216_a(var1 + 1, (EntityLiving) var14);
							field_82223_h[var1 - 1] = ticksExisted + 40 + rand.nextInt(20);
							field_82224_i[var1 - 1] = 0;
						} else
						{
							func_82211_c(var1, 0);
						}
					} else
					{
						List var13 = worldObj.selectEntitiesWithinAABB(EntityLiving.class, boundingBox.expand(20.0D, 8.0D, 20.0D), attackEntitySelector);
						for(int var16 = 0; var16 < 10 && !var13.isEmpty(); ++var16)
						{
							EntityLiving var5 = (EntityLiving) var13.get(rand.nextInt(var13.size()));
							if(var5 != this && var5.isEntityAlive() && canEntityBeSeen(var5))
							{
								if(var5 instanceof EntityPlayer)
								{
									if(!((EntityPlayer) var5).capabilities.disableDamage)
									{
										func_82211_c(var1, var5.entityId);
									}
								} else
								{
									func_82211_c(var1, var5.entityId);
								}
								break;
							}
							var13.remove(var5);
						}
					}
				}
			}
			if(getAttackTarget() != null)
			{
				func_82211_c(0, getAttackTarget().entityId);
			} else
			{
				func_82211_c(0, 0);
			}
			if(field_82222_j > 0)
			{
				--field_82222_j;
				if(field_82222_j == 0 && worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
				{
					var1 = MathHelper.floor_double(posY);
					var12 = MathHelper.floor_double(posX);
					int var15 = MathHelper.floor_double(posZ);
					boolean var18 = false;
					for(int var17 = -1; var17 <= 1; ++var17)
					{
						for(int var19 = -1; var19 <= 1; ++var19)
						{
							for(int var7 = 0; var7 <= 3; ++var7)
							{
								int var20 = var12 + var17;
								int var9 = var1 + var7;
								int var10 = var15 + var19;
								int var11 = worldObj.getBlockId(var20, var9, var10);
								if(var11 > 0 && var11 != Block.bedrock.blockID && var11 != Block.endPortal.blockID && var11 != Block.endPortalFrame.blockID)
								{
									var18 = worldObj.destroyBlock(var20, var9, var10, true) || var18;
								}
							}
						}
					}
					if(var18)
					{
						worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1012, (int) posX, (int) posY, (int) posZ, 0);
					}
				}
			}
			if(ticksExisted % 20 == 0)
			{
				heal(1);
			}
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		p_70014_1_.setInteger("Invul", func_82212_n());
	}
}
