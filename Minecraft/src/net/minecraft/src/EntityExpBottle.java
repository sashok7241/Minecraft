package net.minecraft.src;

public class EntityExpBottle extends EntityThrowable
{
	public EntityExpBottle(World p_i3592_1_)
	{
		super(p_i3592_1_);
	}
	
	public EntityExpBottle(World p_i3594_1_, double p_i3594_2_, double p_i3594_4_, double p_i3594_6_)
	{
		super(p_i3594_1_, p_i3594_2_, p_i3594_4_, p_i3594_6_);
	}
	
	public EntityExpBottle(World p_i3593_1_, EntityLiving p_i3593_2_)
	{
		super(p_i3593_1_, p_i3593_2_);
	}
	
	@Override protected float func_70182_d()
	{
		return 0.7F;
	}
	
	@Override protected float func_70183_g()
	{
		return -20.0F;
	}
	
	@Override protected float getGravityVelocity()
	{
		return 0.07F;
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if(!worldObj.isRemote)
		{
			worldObj.playAuxSFX(2002, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), 0);
			int var2 = 3 + worldObj.rand.nextInt(5) + worldObj.rand.nextInt(5);
			while(var2 > 0)
			{
				int var3 = EntityXPOrb.getXPSplit(var2);
				var2 -= var3;
				worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var3));
			}
			setDead();
		}
	}
}
