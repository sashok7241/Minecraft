package net.minecraft.src;

public abstract class EntityMob extends EntityCreature implements IMob
{
	public EntityMob(World par1World)
	{
		super(par1World);
		experienceValue = 5;
	}
	
	@Override protected void attackEntity(Entity par1Entity, float par2)
	{
		if(attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > boundingBox.minY && par1Entity.boundingBox.minY < boundingBox.maxY)
		{
			attackTime = 20;
			attackEntityAsMob(par1Entity);
		}
	}
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		float var2 = (float) func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
		int var3 = 0;
		if(par1Entity instanceof EntityLivingBase)
		{
			var2 += EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) par1Entity);
			var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) par1Entity);
		}
		boolean var4 = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), var2);
		if(var4)
		{
			if(var3 > 0)
			{
				par1Entity.addVelocity(-MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F, 0.1D, MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * var3 * 0.5F);
				motionX *= 0.6D;
				motionZ *= 0.6D;
			}
			int var5 = EnchantmentHelper.getFireAspectModifier(this);
			if(var5 > 0)
			{
				par1Entity.setFire(var5 * 4);
			}
			if(par1Entity instanceof EntityLivingBase)
			{
				EnchantmentThorns.func_92096_a(this, (EntityLivingBase) par1Entity, rand);
			}
		}
		return var4;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(isEntityInvulnerable()) return false;
		else if(super.attackEntityFrom(par1DamageSource, par2))
		{
			Entity var3 = par1DamageSource.getEntity();
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
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
	}
	
	@Override public float getBlockPathWeight(int par1, int par2, int par3)
	{
		return 0.5F - worldObj.getLightBrightness(par1, par2, par3);
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return worldObj.difficultySetting > 0 && isValidLightLevel() && super.getCanSpawnHere();
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
