package net.minecraft.src;

class EntityZombieGroupData implements EntityLivingData
{
	public boolean field_142048_a;
	public boolean field_142046_b;
	final EntityZombie field_142047_c;
	
	private EntityZombieGroupData(EntityZombie par1EntityZombie, boolean par2, boolean par3)
	{
		field_142047_c = par1EntityZombie;
		field_142048_a = false;
		field_142046_b = false;
		field_142048_a = par2;
		field_142046_b = par3;
	}
	
	EntityZombieGroupData(EntityZombie par1EntityZombie, boolean par2, boolean par3, EntityZombieINNER1 par4EntityZombieINNER1)
	{
		this(par1EntityZombie, par2, par3);
	}
}
