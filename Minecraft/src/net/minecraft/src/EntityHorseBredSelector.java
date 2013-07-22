package net.minecraft.src;

final class EntityHorseBredSelector implements IEntitySelector
{
	@Override public boolean isEntityApplicable(Entity par1Entity)
	{
		return par1Entity instanceof EntityHorse && ((EntityHorse) par1Entity).func_110205_ce();
	}
}
