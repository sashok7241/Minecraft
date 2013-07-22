package net.minecraft.src;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
	private Entity indirectEntity;
	
	public EntityDamageSourceIndirect(String par1Str, Entity par2Entity, Entity par3Entity)
	{
		super(par1Str, par2Entity);
		indirectEntity = par3Entity;
	}
	
	@Override public String getDeathMessage(EntityLiving par1EntityLiving)
	{
		String var2 = indirectEntity == null ? damageSourceEntity.getTranslatedEntityName() : indirectEntity.getTranslatedEntityName();
		ItemStack var3 = indirectEntity instanceof EntityLiving ? ((EntityLiving) indirectEntity).getHeldItem() : null;
		String var4 = "death.attack." + damageType;
		String var5 = var4 + ".item";
		return var3 != null && var3.hasDisplayName() && StatCollector.func_94522_b(var5) ? StatCollector.translateToLocalFormatted(var5, new Object[] { par1EntityLiving.getTranslatedEntityName(), var2, var3.getDisplayName() }) : StatCollector.translateToLocalFormatted(var4, new Object[] { par1EntityLiving.getTranslatedEntityName(), var2 });
	}
	
	@Override public Entity getEntity()
	{
		return indirectEntity;
	}
	
	@Override public Entity getSourceOfDamage()
	{
		return damageSourceEntity;
	}
}
