package net.minecraft.src;

public abstract class EntityMob extends EntityCreature implements IMob
{
	public EntityMob(World p_i3552_1_)
	{
		super(p_i3552_1_);
		experienceValue = 5;
	}
	
	@Override protected void attackEntity(Entity p_70785_1_, float p_70785_2_)
	{
		if(attackTime <= 0 && p_70785_2_ < 2.0F && p_70785_1_.boundingBox.maxY > boundingBox.minY && p_70785_1_.boundingBox.minY < boundingBox.maxY)
		{
			attackTime = 20;
			attackEntityAsMob(p_70785_1_);
		}
	}
	
	@Override public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		int var2 = getAttackStrength(p_70652_1_);
		if(this.isPotionActive(Potion.damageBoost))
		{
			var2 += 3 << getActivePotionEffect(Potion.damageBoost).getAmplifier();
		}
		if(this.isPotionActive(Potion.weakness))
		{
			var2 -= 2 << getActivePotionEffect(Potion.weakness).getAmplifier();
		}
		int var3 = 0;
		if(p_70652_1_ instanceof EntityLiving)
		{
			var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLiving) p_70652_1_);
			var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLiving) p_70652_1_);
		}
		boolean var4 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
		if(var4)
		{
			if(var3 > 0)
			{
				p_70652_1_.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F, 0.1D, MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F);
				motionX *= 0.6D;
				motionZ *= 0.6D;
			}
			int var5 = EnchantmentHelper.getFireAspectModifier(this);
			if(var5 > 0)
			{
				p_70652_1_.setFire(var5 * 4);
			}
			if(p_70652_1_ instanceof EntityLiving)
			{
				EnchantmentThorns.func_92096_a(this, (EntityLiving) p_70652_1_, rand);
			}
		}
		return var4;
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		if(isEntityInvulnerable()) return false;
		else if(super.attackEntityFrom(p_70097_1_, p_70097_2_))
		{
			Entity var3 = p_70097_1_.getEntity();
			if(riddenByEntity != var3 && ridingEntity != var3)
			{
				if(var3 != this)
				{
					entityToAttack = var3;
				}
				return true;
			} else return true;
		} else return false;
	}
	
	@Override protected Entity findPlayerToAttack()
	{
		EntityPlayer var1 = worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
		return var1 != null && canEntityBeSeen(var1) ? var1 : null;
	}
	
	public int getAttackStrength(Entity p_82193_1_)
	{
		return 2;
	}
	
	@Override public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return 0.5F - worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_);
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return isValidLightLevel() && super.getCanSpawnHere();
	}
	
	protected boolean isValidLightLevel()
	{
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(boundingBox.minY);
		int var3 = MathHelper.floor_double(posZ);
		if(worldObj.getSavedLightValue(EnumSkyBlock.Sky, var1, var2, var3) > rand.nextInt(32)) return false;
		else
		{
			int var4 = worldObj.getBlockLightValue(var1, var2, var3);
			if(worldObj.isThundering())
			{
				int var5 = worldObj.skylightSubtracted;
				worldObj.skylightSubtracted = 10;
				var4 = worldObj.getBlockLightValue(var1, var2, var3);
				worldObj.skylightSubtracted = var5;
			}
			return var4 <= rand.nextInt(8);
		}
	}
	
	@Override public void onLivingUpdate()
	{
		updateArmSwingProgress();
		float var1 = getBrightness(1.0F);
		if(var1 > 0.5F)
		{
			entityAge += 2;
		}
		super.onLivingUpdate();
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(!worldObj.isRemote && worldObj.difficultySetting == 0)
		{
			setDead();
		}
	}
}
