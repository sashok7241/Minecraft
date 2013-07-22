package net.minecraft.src;

public enum EnumCreatureType
{
	monster(IMob.class, 70, Material.air, false, false), creature(EntityAnimal.class, 10, Material.air, true, true), ambient(EntityAmbientCreature.class, 15, Material.air, true, false), waterCreature(EntityWaterMob.class, 5, Material.water, true, false);
	private final Class creatureClass;
	private final int maxNumberOfCreature;
	private final Material creatureMaterial;
	private final boolean isPeacefulCreature;
	private final boolean isAnimal;
	
	private EnumCreatureType(Class par3Class, int par4, Material par5Material, boolean par6, boolean par7)
	{
		creatureClass = par3Class;
		maxNumberOfCreature = par4;
		creatureMaterial = par5Material;
		isPeacefulCreature = par6;
		isAnimal = par7;
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
