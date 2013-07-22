package net.minecraft.src;

public class TileEntitySign extends TileEntity
{
	public String[] signText = new String[] { "", "", "", "" };
	public int lineBeingEdited = -1;
	private boolean isEditable = true;
	
	@Override public Packet getDescriptionPacket()
	{
		String[] var1 = new String[4];
		System.arraycopy(signText, 0, var1, 0, 4);
		return new Packet130UpdateSign(xCoord, yCoord, zCoord, var1);
	}
	
	public boolean isEditable()
	{
		return isEditable;
	}
	
	@Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		isEditable = false;
		super.readFromNBT(par1NBTTagCompound);
		for(int var2 = 0; var2 < 4; ++var2)
		{
			signText[var2] = par1NBTTagCompound.getString("Text" + (var2 + 1));
			if(signText[var2].length() > 15)
			{
				signText[var2] = signText[var2].substring(0, 15);
			}
		}
	}
	
	public void setEditable(boolean par1)
	{
		isEditable = par1;
	}
	
	@Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setString("Text1", signText[0]);
		par1NBTTagCompound.setString("Text2", signText[1]);
		par1NBTTagCompound.setString("Text3", signText[2]);
		par1NBTTagCompound.setString("Text4", signText[3]);
	}
}
