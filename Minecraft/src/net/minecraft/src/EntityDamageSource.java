package net.minecraft.src;

public class EntityDamageSource extends DamageSource
{
	protected Entity damageSourceEntity;
	
	public EntityDamageSource(String par1Str, Entity par2Entity)
	{
		super(par1Str);
		damageSourceEntity = par2Entity;
	}
	
	@Override public ChatMessageComponent getDeathMessage(EntityLivingBase par1EntityLivingBase)
	{
		ItemStack var2 = damageSourceEntity instanceof EntityLivingBase ? ((EntityLivingBase) damageSourceEntity).getHeldItem() : null;
		String var3 = "death.attack." + damageType;
		String var4 = var3 + ".item";
		return var2 != null && var2.hasDisplayName() && StatCollector.func_94522_b(var4) ? ChatMessageComponent.func_111082_b(var4, new Object[] { par1EntityLivingBase.getTranslatedEntityName(), damageSourceEntity.getTranslatedEntityName(), var2.getDisplayName() }) : ChatMessageComponent.func_111082_b(var3, new Object[] { par1EntityLivingBase.getTranslatedEntityName(), damageSourceEntity.getTranslatedEntityName() });
	}
	
	@Override public Entity getEntity()
	{
		return damageSourceEntity;
	}
	
	@Override public boolean isDifficultyScaled()
	{
		return damageSourceEntity != null && damageSourceEntity instanceof EntityLivingBase && !(damageSourceEntity instanceof EntityPlayer);
	}
}
