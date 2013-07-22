package net.minecraft.src;

public class EntityDragonPart extends Entity
{
	public final IEntityMultiPart entityDragonObj;
	public final String name;
	
	public EntityDragonPart(IEntityMultiPart p_i5012_1_, String p_i5012_2_, float p_i5012_3_, float p_i5012_4_)
	{
		super(p_i5012_1_.func_82194_d());
		setSize(p_i5012_3_, p_i5012_4_);
		entityDragonObj = p_i5012_1_;
		name = p_i5012_2_;
	}
	
	@Override public boolean attackEntityFrom(DamageSource p_70097_1_, int p_70097_2_)
	{
		return isEntityInvulnerable() ? false : entityDragonObj.attackEntityFromPart(this, p_70097_1_, p_70097_2_);
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public boolean isEntityEqual(Entity p_70028_1_)
	{
		return this == p_70028_1_ || entityDragonObj == p_70028_1_;
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
	}
}
