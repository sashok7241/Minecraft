package net.minecraft.src;

public abstract class EntityGolem extends EntityCreature implements IAnimals
{
	public EntityGolem(World p_i3517_1_)
	{
		super(p_i3517_1_);
	}
	
	@Override protected boolean canDespawn()
	{
		return false;
	}
	
	@Override protected void fall(float p_70069_1_)
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
