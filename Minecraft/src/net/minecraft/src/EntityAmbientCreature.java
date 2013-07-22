package net.minecraft.src;

public abstract class EntityAmbientCreature extends EntityLiving implements IAnimals
{
	public EntityAmbientCreature(World par1World)
	{
		super(par1World);
	}
	
	@Override public boolean func_110164_bC()
	{
		return false;
	}
	
	@Override protected boolean interact(EntityPlayer par1EntityPlayer)
	{
		return false;
	}
}
