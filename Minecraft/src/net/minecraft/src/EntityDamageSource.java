package net.minecraft.src;

public class EntityDamageSource extends DamageSource
{
	protected Entity damageSourceEntity;
	
	public EntityDamageSource(String p_i3430_1_, Entity p_i3430_2_)
	{
		super(p_i3430_1_);
		damageSourceEntity = p_i3430_2_;
	}
	
	@Override public String getDeathMessage(EntityLiving p_76360_1_)
	{
		ItemStack var2 = damageSourceEntity instanceof EntityLiving ? ((EntityLiving) damageSourceEntity).getHeldItem() : null;
		String var3 = "death.attack." + damageType;
		String var4 = var3 + ".item";
		return var2 != null && var2.hasDisplayName() && StatCollector.func_94522_b(var4) ? StatCollector.translateToLocalFormatted(var4, new Object[] { p_76360_1_.getTranslatedEntityName(), damageSourceEntity.getTranslatedEntityName(), var2.getDisplayName() }) : StatCollector.translateToLocalFormatted(var3, new Object[] { p_76360_1_.getTranslatedEntityName(), damageSourceEntity.getTranslatedEntityName() });
	}
	
	@Override public Entity getEntity()
	{
		return damageSourceEntity;
	}
	
	@Override public boolean isDifficultyScaled()
	{
		return damageSourceEntity != null && damageSourceEntity instanceof EntityLiving && !(damageSourceEntity instanceof EntityPlayer);
	}
}
