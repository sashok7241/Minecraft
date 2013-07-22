package net.minecraft.src;

class TileEntityMobSpawnerLogic extends MobSpawnerBaseLogic
{
	final TileEntityMobSpawner mobSpawnerEntity;
	
	TileEntityMobSpawnerLogic(TileEntityMobSpawner par1TileEntityMobSpawner)
	{
		mobSpawnerEntity = par1TileEntityMobSpawner;
	}
	
	@Override public void func_98267_a(int par1)
	{
		mobSpawnerEntity.worldObj.addBlockEvent(mobSpawnerEntity.xCoord, mobSpawnerEntity.yCoord, mobSpawnerEntity.zCoord, Block.mobSpawner.blockID, par1, 0);
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
	
	@Override public void setRandomMinecart(WeightedRandomMinecart par1WeightedRandomMinecart)
	{
		super.setRandomMinecart(par1WeightedRandomMinecart);
		if(getSpawnerWorld() != null)
		{
			getSpawnerWorld().markBlockForUpdate(mobSpawnerEntity.xCoord, mobSpawnerEntity.yCoord, mobSpawnerEntity.zCoord);
		}
	}
}
