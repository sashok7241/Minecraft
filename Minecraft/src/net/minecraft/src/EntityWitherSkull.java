package net.minecraft.src;

public class EntityWitherSkull extends EntityFireball
{
	public EntityWitherSkull(World par1World)
	{
		super(par1World);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntityWitherSkull(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		setSize(0.3125F, 0.3125F);
	}
	
	public EntityWitherSkull(World par1World, EntityLivingBase par2EntityLivingBase, double par3, double par5, double par7)
	{
		super(par1World, par2EntityLivingBase, par3, par5, par7);
		setSize(0.3125F, 0.3125F);
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
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
	
	@Override public float func_82146_a(Explosion par1Explosion, World par2World, int par3, int par4, int par5, Block par6Block)
	{
		float var7 = super.func_82146_a(par1Explosion, par2World, par3, par4, par5, par6Block);
		if(isInvulnerable() && par6Block != Block.bedrock && par6Block != Block.endPortal && par6Block != Block.endPortalFrame)
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
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	{
		if(!worldObj.isRemote)
		{
			if(par1MovingObjectPosition.entityHit != null)
			{
				if(shootingEntity != null)
				{
					if(par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeMobDamage(shootingEntity), 8.0F) && !par1MovingObjectPosition.entityHit.isEntityAlive())
					{
						shootingEntity.heal(5.0F);
					}
				} else
				{
					par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
				}
				if(par1MovingObjectPosition.entityHit instanceof EntityLivingBase)
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
						((EntityLivingBase) par1MovingObjectPosition.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * var2, 1));
					}
				}
			}
			worldObj.newExplosion(this, posX, posY, posZ, 1.0F, false, worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
			setDead();
		}
	}
	
	public void setInvulnerable(boolean par1)
	{
		dataWatcher.updateObject(10, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
}
