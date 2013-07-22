package net.minecraft.src;

public class EntityMinecartMobSpawner extends EntityMinecart
{
	private final MobSpawnerBaseLogic mobSpawnerLogic = new EntityMinecartMobSpawnerLogic(this);
	
	public EntityMinecartMobSpawner(World p_i11039_1_)
	{
		super(p_i11039_1_);
	}
	
	public EntityMinecartMobSpawner(World p_i11040_1_, double p_i11040_2_, double p_i11040_4_, double p_i11040_6_)
	{
		super(p_i11040_1_, p_i11040_2_, p_i11040_4_, p_i11040_6_);
	}
	
	public MobSpawnerBaseLogic func_98039_d()
	{
		return mobSpawnerLogic;
	}
	
	@Override public Block getDefaultDisplayTile()
	{
		return Block.mobSpawner;
	}
	
	@Override public int getMinecartType()
	{
		return 4;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		mobSpawnerLogic.setDelayToMin(par1);
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		mobSpawnerLogic.updateSpawner();
	}
	
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		mobSpawnerLogic.readFromNBT(p_70037_1_);
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		mobSpawnerLogic.writeToNBT(p_70014_1_);
	}
}
