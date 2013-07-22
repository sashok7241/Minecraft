package net.minecraft.src;

public abstract class EntityWaterMob extends EntityCreature implements IAnimals
{
	public EntityWaterMob(World par1World)
	{
		super(par1World);
	}
	
	@Override public boolean canBreatheUnderwater()
	{
		return true;
	}
	
	@Override protected boolean canDespawn()
	{
		return true;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return worldObj.checkNoEntityCollision(boundingBox);
	}
	
	@Override protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		return 1 + worldObj.rand.nextInt(3);
	}
	
	@Override public int getTalkInterval()
	{
		return 120;
	}
	
	@Override public void onEntityUpdate()
	{
		int var1 = getAir();
		super.onEntityUpdate();
		if(isEntityAlive() && !isInsideOfMaterial(Material.water))
		{
			--var1;
			setAir(var1);
			if(getAir() == -20)
			{
				setAir(0);
				attackEntityFrom(DamageSource.drown, 2);
			}
		} else
		{
			setAir(300);
		}
	}
}
