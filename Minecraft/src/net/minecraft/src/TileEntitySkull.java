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
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		skullType = par1NBTTagCompound.getByte("SkullType");
		skullRotation = par1NBTTagCompound.getByte("Rot");
		if(par1NBTTagCompound.hasKey("ExtraType"))
		{
			extraType = par1NBTTagCompound.getString("ExtraType");
		}
	}
	
	public void setSkullRotation(int par1)
	{
		skullRotation = par1;
	}
	
	public void setSkullType(int par1, String par2Str)
	{
		skullType = par1;
		extraType = par2Str;
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("SkullType", (byte) (skullType & 255));
		par1NBTTagCompound.setByte("Rot", (byte) (skullRotation & 255));
		par1NBTTagCompound.setString("ExtraType", extraType);
	}
}
