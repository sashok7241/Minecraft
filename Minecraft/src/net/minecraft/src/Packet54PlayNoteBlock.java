package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet54PlayNoteBlock extends Packet
{
	public int xLocation;
	public int yLocation;
	public int zLocation;
	public int instrumentType;
	public int pitch;
	public int blockId;
	
	public Packet54PlayNoteBlock()
	{
	}
	
	public Packet54PlayNoteBlock(int p_i3363_1_, int p_i3363_2_, int p_i3363_3_, int p_i3363_4_, int p_i3363_5_, int p_i3363_6_)
	{
		xLocation = p_i3363_1_;
		yLocation = p_i3363_2_;
		zLocation = p_i3363_3_;
		instrumentType = p_i3363_5_;
		pitch = p_i3363_6_;
		blockId = p_i3363_4_;
	}
	
	@Override public int getPacketSize()
	{
		return 14;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleBlockEvent(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xLocation = p_73267_1_.readInt();
		yLocation = p_73267_1_.readShort();
		zLocation = p_73267_1_.readInt();
		instrumentType = p_73267_1_.read();
		pitch = p_73267_1_.read();
		blockId = p_73267_1_.readShort() & 4095;
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xLocation);
		p_73273_1_.writeShort(yLocation);
		p_73273_1_.writeInt(zLocation);
		p_73273_1_.write(instrumentType);
		p_73273_1_.write(pitch);
		p_73273_1_.writeShort(blockId & 4095);
	}
}
