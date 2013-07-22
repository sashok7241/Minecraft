package net.minecraft.src;

public enum EnumCreatureType
{
	monster(IMob.class, 70, Material.air, false, false), creature(EntityAnimal.class, 10, Material.air, true, true), ambient(EntityAmbientCreature.class, 15, Material.air, true, false), waterCreature(EntityWaterMob.class, 5, Material.water, true, false);
	private final Class creatureClass;
	private final int maxNumberOfCreature;
	private final Material creatureMaterial;
	private final boolean isPeacefulCreature;
	private final boolean isAnimal;
	
	private EnumCreatureType(Class p_i5057_3_, int p_i5057_4_, Material p_i5057_5_, boolean p_i5057_6_, boolean p_i5057_7_)
	{
		creatureClass = p_i5057_3_;
		maxNumberOfCreature = p_i5057_4_;
		creatureMaterial = p_i5057_5_;
		isPeacefulCreature = p_i5057_6_;
		isAnimal = p_i5057_7_;
	}
	
	public boolean getAnimal()
	{
		return isAnimal;
	}
	
	public Class getCreatureClass()
	{
		return creatureClass;
	}
	
	public Material getCreatureMaterial()
	{
		return creatureMaterial;
	}
	
	public int getMaxNumberOfCreature()
	{
		return maxNumberOfCreature;
	}
	
	public boolean getPeacefulCreature()
	{
		return isPeacefulCreature;
	}
}
