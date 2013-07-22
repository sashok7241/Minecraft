package net.minecraft.src;

public class EntityWitherSkull extends EntityFireball
{
	public EntityWitherSkull(World p_i5072_1_)
	{
		super(p_i5072_1_);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntityWitherSkull(World p_i5074_1_, double p_i5074_2_, double p_i5074_4_, double p_i5074_6_, double p_i5074_8_, double p_i5074_10_, double p_i5074_12_)
	{
		super(p_i5074_1_, p_i5074_2_, p_i5074_4_, p_i5074_6_, p_i5074_8_, p_i5074_10_, p_i5074_12_);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntityWitherSkull(World p_i5073_1_, EntityLiving p_i5073_2_, double p_i5073_3_, double p_i5073_5_, double p_i5073_7_)
	{
		super(p_i5073_1_, p_i5073_2_, p_i5073_3_, p_i5073_5_, p_i5073_7_);
		setSize(0.3125F, 0.3125F);
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		return false;
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
		dataWatcher.addObject(10, Byte.valueOf((byte) 0));
	}
	
	@Override public float func_82146_a(Explosion p_82146_1_, World p_82146_2_, int p_82146_3_, int p_82146_4_, int p_82146_5_, Block p_82146_6_)
	{
		float var7 = super.func_82146_a(p_82146_1_, p_82146_2_, p_82146_3_, p_82146_4_, p_82146_5_, p_82146_6_);
		if(isInvulnerable() && p_82146_6_ != Block.bedrock && p_82146_6_ != Block.endPortal && p_82146_6_ != Block.endPortalFrame)
		{
			var7 = Math.min(0.8F, var7);
		}
		return var7;
	}
	
	@Override protected float getMotionFactor()
	{
		return isInvulnerable() ? 0.73F : super.getMotionFactor();
	}
	
	@Override public boolean isBurning()
	{
		return false;
	}
	
	public boolean isInvulnerable()
	{
		return dataWatcher.getWatchableObjectByte(10) == 1;
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70227_1_)
	{
		if(!worldObj.isRemote)
		{
			if(p_70227_1_.entityHit != null)
			{
				if(shootingEntity != null)
				{
					if(p_70227_1_.entityHit.attackEntityFrom(DamageSource.causeMobDamage(shootingEntity), 8) && !p_70227_1_.entityHit.isEntityAlive())
					{
						shootingEntity.heal(5);
					}
				} else
				{
					p_70227_1_.entityHit.attackEntityFrom(DamageSource.magic, 5);
				}
				if(p_70227_1_.entityHit instanceof EntityLiving)
				{
					byte var2 = 0;
					if(worldObj.difficultySetting > 1)
					{
						if(worldObj.difficultySetting == 2)
						{
							var2 = 10;
						} else if(worldObj.difficultySetting == 3)
						{
							var2 = 40;
						}
					}
					if(var2 > 0)
					{
						((EntityLiving) p_70227_1_.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
					}
				}
			}
			worldObj.newExplosion(this, posX, posY, posZ, 1.0F, false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}
	}
	
	public void setInvulnerable(boolean p_82343_1_)
	{
		dataWatcher.updateObject(10, Byte.valueOf((byte) (p_82343_1_ ? 1 : 0)));
	}
}
