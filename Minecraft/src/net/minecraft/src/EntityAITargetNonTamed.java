package net.minecraft.src;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
	private EntityTameable theTameable;
	
	public EntityAITargetNonTamed(EntityTameable par1EntityTameable, Class par2Class, int par3, boolean par4)
	{
		super(par1EntityTameable, par2Class, par3, par4);
		theTameable = par1EntityTameable;
	}
	
	@Override public boolean shouldExecute()
	{
		return !theTameable.isTamed() && super.shouldExecute();
	}
}
