package net.minecraft.src;

public class EntityDamageSourceIndirect extends EntityDamageSource
{
	private Entity indirectEntity;
	
	public EntityDamageSourceIndirect(String p_i3431_1_, Entity p_i3431_2_, Entity p_i3431_3_)
	{
		super(p_i3431_1_, p_i3431_2_);
		indirectEntity = p_i3431_3_;
	}
	
	@Override public String getDeathMessage(EntityLiving p_76360_1_)
	{
		String var2 = indirectEntity == null ? damageSourceEntity.getTranslatedEntityName() : indirectEntity.getTranslatedEntityName();
		ItemStack var3 = indirectEntity instanceof EntityLiving ? ((EntityLiving) indirectEntity).getHeldItem() : null;
		String var4 = "death.attack." + damageType;
		String var5 = var4 + ".item";
		return var3 != null && var3.hasDisplayName() && StatCollector.func_94522_b(var5) ? StatCollector.translateToLocalFormatted(var5, new Object[] { p_76360_1_.getTranslatedEntityName(), var2, var3.getDisplayName() }) : StatCollector.translateToLocalFormatted(var4, new Object[] { p_76360_1_.getTranslatedEntityName(), var2 });
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
