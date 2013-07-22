package net.minecraft.src;

public class TileEntityMobSpawner extends TileEntity
{
	private final MobSpawnerBaseLogic field_98050_a = new TileEntityMobSpawnerLogic(this);
	
	@Override public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		writeToNBT(var1);
		var1.removeTag("SpawnPotentials");
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, var1);
	}
	
	public MobSpawnerBaseLogic getSpawnerLogic()
	{
		return field_98050_a;
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		field_98050_a.readFromNBT(p_70307_1_);
	}
	
	@Override public boolean receiveClientEvent(int p_70315_1_, int p_70315_2_)
	{
		return field_98050_a.setDelayToMin(p_70315_1_) ? true : super.receiveClientEvent(p_70315_1_, p_70315_2_);
	}
	
	@Override public void updateEntity()
	{
		field_98050_a.updateSpawner();
		super.updateEntity();
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		field_98050_a.writeToNBT(p_70310_1_);
	}
}
