package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet133TileEditorOpen extends Packet
{
	public int field_142037_a;
	public int field_142035_b;
	public int field_142036_c;
	public int field_142034_d;
	
	public Packet133TileEditorOpen()
	{
	}
	
	public Packet133TileEditorOpen(int par1, int par2, int par3, int par4)
	{
		field_142037_a = par1;
		field_142035_b = par2;
		field_142036_c = par3;
		field_142034_d = par4;
	}
	
	@Override public int getPacketSize()
	{
		return 13;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.func_142031_a(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		field_142037_a = par1DataInput.readByte();
		field_142035_b = par1DataInput.readInt();
		field_142036_c = par1DataInput.readInt();
		field_142034_d = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(field_142037_a);
		par1DataOutput.writeInt(field_142035_b);
		par1DataOutput.writeInt(field_142036_c);
		par1DataOutput.writeInt(field_142034_d);
	}
}
