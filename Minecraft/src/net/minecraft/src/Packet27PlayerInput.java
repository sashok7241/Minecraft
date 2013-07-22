package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet27PlayerInput extends Packet
{
	private float field_111017_a;
	private float field_111015_b;
	private boolean field_111016_c;
	private boolean field_111014_d;
	
	public Packet27PlayerInput()
	{
	}
	
	public Packet27PlayerInput(float par1, float par2, boolean par3, boolean par4)
	{
		field_111017_a = par1;
		field_111015_b = par2;
		field_111016_c = par3;
		field_111014_d = par4;
	}
	
	public float func_111010_d()
	{
		return field_111017_a;
	}
	
	public boolean func_111011_h()
	{
		return field_111014_d;
	}
	
	public float func_111012_f()
	{
		return field_111015_b;
	}
	
	public boolean func_111013_g()
	{
		return field_111016_c;
	}
	
	@Override public int getPacketSize()
	{
		return 10;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.func_110774_a(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		field_111017_a = par1DataInput.readFloat();
		field_111015_b = par1DataInput.readFloat();
		field_111016_c = par1DataInput.readBoolean();
		field_111014_d = par1DataInput.readBoolean();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeFloat(field_111017_a);
		par1DataOutput.writeFloat(field_111015_b);
		par1DataOutput.writeBoolean(field_111016_c);
		par1DataOutput.writeBoolean(field_111014_d);
	}
}
