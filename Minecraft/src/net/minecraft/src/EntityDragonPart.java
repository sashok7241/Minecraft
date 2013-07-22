package net.minecraft.src;

public class EntityDragonPart extends Entity
{
	public final IEntityMultiPart entityDragonObj;
	public final String name;
	
	public EntityDragonPart(IEntityMultiPart par1IEntityMultiPart, String par2Str, float par3, float par4)
	{
		super(par1IEntityMultiPart.func_82194_d());
		setSize(par3, par4);
		entityDragonObj = par1IEntityMultiPart;
		name = par2Str;
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		return isEntityInvulnerable() ? false : entityDragonObj.attackEntityFromPart(this, par1DamageSource, par2);
	}
	
	@Override public boolean canBeCollidedWith()
	{
		return true;
	}
	
	@Override protected void entityInit()
	{
	}
	
	@Override public boolean isEntityEqual(Entity par1Entity)
	{
		return this == par1Entity || entityDragonObj == par1Entity;
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
}
