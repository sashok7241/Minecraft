package net.minecraft.src;

public class TileEntitySkull extends TileEntity
{
	private int skullType;
	private int skullRotation;
	private String extraType = "";
	
	public int func_82119_b()
	{
		return skullRotation;
	}
	
	@Override public Packet getDescriptionPacket()
	{
		NBTTagCompound var1 = new NBTTagCompound();
		writeToNBT(var1);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 4, var1);
	}
	
	public String getExtraType()
	{
		return extraType;
	}
	
	public int getSkullType()
	{
		return skullType;
	}
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		super.readFromNBT(p_70307_1_);
		skullType = p_70307_1_.getByte("SkullType");
		skullRotation = p_70307_1_.getByte("Rot");
		if(p_70307_1_.hasKey("ExtraType"))
		{
			extraType = p_70307_1_.getString("ExtraType");
		}
	}
	
	public void setSkullRotation(int p_82116_1_)
	{
		skullRotation = p_82116_1_;
	}
	
	public void setSkullType(int p_82118_1_, String p_82118_2_)
	{
		skullType = p_82118_1_;
		extraType = p_82118_2_;
	}
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setByte("SkullType", (byte) (skullType & 255));
		p_70310_1_.setByte("Rot", (byte) (skullRotation & 255));
		p_70310_1_.setString("ExtraType", extraType);
	}
}
