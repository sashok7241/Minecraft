package net.minecraft.src;

public class EntityEgg extends EntityThrowable
{
	public EntityEgg(World p_i3586_1_)
	{
		super(p_i3586_1_);
	}
	
	public EntityEgg(World p_i3588_1_, double p_i3588_2_, double p_i3588_4_, double p_i3588_6_)
	{
		super(p_i3588_1_, p_i3588_2_, p_i3588_4_, p_i3588_6_);
	}
	
	public EntityEgg(World p_i3587_1_, EntityLiving p_i3587_2_)
	{
		super(p_i3587_1_, p_i3587_2_);
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if(p_70184_1_.entityHit != null)
		{
			p_70184_1_.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), 0);
		}
		if(!worldObj.isRemote && rand.nextInt(8) == 0)
		{
			byte var2 = 1;
			if(rand.nextInt(32) == 0)
			{
				var2 = 4;
			}
			for(int var3 = 0; var3 < var2; ++var3)
			{
				EntityChicken var4 = new EntityChicken(worldObj);
				var4.setGrowingAge(-24000);
				var4.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
				worldObj.spawnEntityInWorld(var4);
			}
		}
		for(int var5 = 0; var5 < 8; ++var5)
		{
			worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
		}
		if(!worldObj.isRemote)
		{
			setDead();
		}
	}
}
