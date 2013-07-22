package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet254ServerPing extends Packet
{
	private static final int field_140051_d = new Packet250CustomPayload().getPacketId();
	public int readSuccessfully;
	public String field_140052_b;
	public int field_140053_c;
	
	public Packet254ServerPing()
	{
	}
	
	public Packet254ServerPing(int par1, String par2Str, int par3)
	{
		readSuccessfully = par1;
		field_140052_b = par2Str;
		field_140053_c = par3;
	}
	
	public boolean func_140050_d()
	{
		return readSuccessfully == 0;
	}
	
	@Override public int getPacketSize()
	{
		return 3 + field_140052_b.length() * 2 + 4;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleServerPing(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		try
		{
			readSuccessfully = par1DataInput.readByte();
			try
			{
				par1DataInput.readByte();
				readString(par1DataInput, 255);
				par1DataInput.readShort();
				readSuccessfully = par1DataInput.readByte();
				if(readSuccessfully >= 73)
				{
					field_140052_b = readString(par1DataInput, 255);
					field_140053_c = par1DataInput.readInt();
				}
			} catch(Throwable var3)
			{
				field_140052_b = "";
			}
		} catch(Throwable var4)
		{
			readSuccessfully = 0;
			field_140052_b = "";
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(1);
		par1DataOutput.writeByte(field_140051_d);
		Packet.writeString("MC|PingHost", par1DataOutput);
		par1DataOutput.writeShort(3 + 2 * field_140052_b.length() + 4);
		par1DataOutput.writeByte(readSuccessfully);
		Packet.writeString(field_140052_b, par1DataOutput);
		par1DataOutput.writeInt(field_140053_c);
	}
}
