package net.minecraft.src;

class TileEntityMobSpawnerLogic extends MobSpawnerBaseLogic
{
	final TileEntityMobSpawner mobSpawnerEntity;
	
	TileEntityMobSpawnerLogic(TileEntityMobSpawner p_i11046_1_)
	{
		mobSpawnerEntity = p_i11046_1_;
	}
	
	@Override public void func_98267_a(int p_98267_1_)
	{
		mobSpawnerEntity.worldObj.addBlockEvent(mobSpawnerEntity.xCoord, mobSpawnerEntity.yCoord, mobSpawnerEntity.zCoord, Block.mobSpawner.blockID, p_98267_1_, 0);
	}
	
	@Override public World getSpawnerWorld()
	{
		return mobSpawnerEntity.worldObj;
	}
	
	@Override public int getSpawnerX()
	{
		return mobSpawnerEntity.xCoord;
	}
	
	@Override public int getSpawnerY()
	{
		return mobSpawnerEntity.yCoord;
	}
	
	@Override public int getSpawnerZ()
	{
		return mobSpawnerEntity.zCoord;
	}
	
	@Override public void setRandomMinecart(WeightedRandomMinecart p_98277_1_)
	{
		super.setRandomMinecart(p_98277_1_);
		if(getSpawnerWorld() != null)
		{
			getSpawnerWorld().markBlockForUpdate(mobSpawnerEntity.xCoord, mobSpawnerEntity.yCoord, mobSpawnerEntity.zCoord);
		}
	}
}
