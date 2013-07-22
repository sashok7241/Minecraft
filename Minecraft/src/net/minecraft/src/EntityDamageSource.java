package net.minecraft.src;

public class EntityDamageSource extends DamageSource
{
	protected Entity damageSourceEntity;
	
	public EntityDamageSource(String par1Str, Entity par2Entity)
	{
		super(par1Str);
		damageSourceEntity = par2Entity;
	}
	
	@Override public String getDeathMessage(EntityLiving par1EntityLiving)
	{
		ItemStack var2 = damageSourceEntity instanceof EntityLiving ? ((EntityLiving) damageSourceEntity).getHeldItem() : null;
		String var3 = "death.attack." + damageType;
		String var4 = var3 + ".item";
		return var2 != null && var2.hasDisplayName() && StatCollector.func_94522_b(var4) ? StatCollector.translateToLocalFormatted(var4, new Object[] { par1EntityLiving.getTranslatedEntityName(), damageSourceEntity.getTranslatedEntityName(), var2.getDisplayName() }) : StatCollector.translateToLocalFormatted(var3, new Object[] { par1EntityLiving.getTranslatedEntityName(), damageSourceEntity.getTranslatedEntityName() });
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
