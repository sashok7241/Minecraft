package net.minecraft.src;

public class EntityMinecartMobSpawner extends EntityMinecart
{
	private final MobSpawnerBaseLogic mobSpawnerLogic = new EntityMinecartMobSpawnerLogic(this);
	
	public EntityMinecartMobSpawner(World par1World)
	{
		super(par1World);
	}
	
	public EntityMinecartMobSpawner(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6);
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
	
	@Override protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
	}
}
