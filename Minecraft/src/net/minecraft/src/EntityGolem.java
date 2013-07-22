package net.minecraft.src;

public abstract class EntityGolem extends EntityCreature implements IAnimals
{
	public EntityGolem(World par1World)
	{
		super(par1World);
	}
	
	@Override protected boolean canDespawn()
	{
		return false;
	}
	
	@Override protected void fall(float par1)
	{
	}
	
	@Override protected String getDeathSound()
	{
		return "none";
	}
	
	@Override protected String getHurtSound()
	{
		return "none";
	}
	
	@Override protected String getLivingSound()
	{
		return "none";
	}
	
	@Override public int getTalkInterval()
	{
		return 120;
	}
}
