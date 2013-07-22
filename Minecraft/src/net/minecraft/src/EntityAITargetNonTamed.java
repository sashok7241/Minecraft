package net.minecraft.src;

public class EntityAITargetNonTamed extends EntityAINearestAttackableTarget
{
	private EntityTameable theTameable;
	
	public EntityAITargetNonTamed(EntityTameable p_i3502_1_, Class p_i3502_2_, float p_i3502_3_, int p_i3502_4_, boolean p_i3502_5_)
	{
		super(p_i3502_1_, p_i3502_2_, p_i3502_3_, p_i3502_4_, p_i3502_5_);
		theTameable = p_i3502_1_;
	}
	
	@Override public boolean shouldExecute()
	{
		return theTameable.isTamed() ? false : super.shouldExecute();
	}
}
