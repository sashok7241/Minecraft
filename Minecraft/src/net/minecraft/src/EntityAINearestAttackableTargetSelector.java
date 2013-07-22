package net.minecraft.src;

class EntityAINearestAttackableTargetSelector implements IEntitySelector
{
	final IEntitySelector field_111103_c;
	final EntityAINearestAttackableTarget field_111102_d;
	
	EntityAINearestAttackableTargetSelector(EntityAINearestAttackableTarget par1EntityAINearestAttackableTarget, IEntitySelector par2IEntitySelector)
	{
		field_111102_d = par1EntityAINearestAttackableTarget;
		field_111103_c = par2IEntitySelector;
	}
	
	@Override public boolean isEntityApplicable(Entity par1Entity)
	{
		return !(par1Entity instanceof EntityLivingBase) ? false : field_111103_c != null && !field_111103_c.isEntityApplicable(par1Entity) ? false : field_111102_d.isSuitableTarget((EntityLivingBase) par1Entity, false);
	}
}
