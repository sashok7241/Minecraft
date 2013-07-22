package net.minecraft.src;

public class EntityExpBottle extends EntityThrowable
{
	public EntityExpBottle(World par1World)
	{
		super(par1World);
	}
	
	public EntityExpBottle(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
	}
	
	public EntityExpBottle(World par1World, EntityLiving par2EntityLiving)
	{
		super(par1World, par2EntityLiving);
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
	
	@Override protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
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
