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
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		field_98050_a.readFromNBT(par1NBTTagCompound);
	}
	
	@Override public boolean receiveClientEvent(int par1, int par2)
	{
		return field_98050_a.setDelayToMin(par1) ? true : super.receiveClientEvent(par1, par2);
	}
	
	@Override public void updateEntity()
	{
		field_98050_a.updateSpawner();
		super.updateEntity();
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		field_98050_a.writeToNBT(par1NBTTagCompound);
	}
}
