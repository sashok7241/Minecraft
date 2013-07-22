package net.minecraft.src;

class EntityMinecartMobSpawnerLogic extends MobSpawnerBaseLogic
{
	final EntityMinecartMobSpawner spawnerMinecart;
	
	EntityMinecartMobSpawnerLogic(EntityMinecartMobSpawner p_i11038_1_)
	{
		spawnerMinecart = p_i11038_1_;
	}
	
	@Override public void func_98267_a(int p_98267_1_)
	{
		spawnerMinecart.worldObj.setEntityState(spawnerMinecart, (byte) p_98267_1_);
	}
	
	@Override public World getSpawnerWorld()
	{
		return spawnerMinecart.worldObj;
	}
	
	@Override public int getSpawnerX()
	{
		return MathHelper.floor_double(spawnerMinecart.posX);
	}
	
	@Override public int getSpawnerY()
	{
		return MathHelper.floor_double(spawnerMinecart.posY);
	}
	
	@Override public int getSpawnerZ()
	{
		return MathHelper.floor_double(spawnerMinecart.posZ);
	}
}
