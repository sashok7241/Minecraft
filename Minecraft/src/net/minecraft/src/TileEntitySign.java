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
	
	@Override public void readFromNBT(NBTTagCompound p_70307_1_)
	{
		isEditable = false;
		super.readFromNBT(p_70307_1_);
		for(int var2 = 0; var2 < 4; ++var2)
		{
			signText[var2] = p_70307_1_.getString("Text" + (var2 + 1));
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
	
	@Override public void writeToNBT(NBTTagCompound p_70310_1_)
	{
		super.writeToNBT(p_70310_1_);
		p_70310_1_.setString("Text1", signText[0]);
		p_70310_1_.setString("Text2", signText[1]);
		p_70310_1_.setString("Text3", signText[2]);
		p_70310_1_.setString("Text4", signText[3]);
	}
}
