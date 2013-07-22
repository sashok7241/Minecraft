package net.minecraft.src;

public class EntitySnowball extends EntityThrowable
{
	public EntitySnowball(World p_i3580_1_)
	{
		super(p_i3580_1_);
	}
	
	public EntitySnowball(World p_i3582_1_, double p_i3582_2_, double p_i3582_4_, double p_i3582_6_)
	{
		super(p_i3582_1_, p_i3582_2_, p_i3582_4_, p_i3582_6_);
	}
	
	public EntitySnowball(World p_i3581_1_, EntityLiving p_i3581_2_)
	{
		super(p_i3581_1_, p_i3581_2_);
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if(p_70184_1_.entityHit != null)
		{
			byte var2 = 0;
			if(p_70184_1_.entityHit instanceof EntityBlaze)
			{
				var2 = 3;
			}
			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), var2);
		}
		for(int var3 = 0; var3 < 8; ++var3)
		{
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}
		if(!worldObj.isRemote)
		{
			setDead();
		}
	}
}
