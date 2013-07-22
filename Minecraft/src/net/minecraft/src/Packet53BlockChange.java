package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet53BlockChange extends Packet
{
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int type;
	public int metadata;
	
	public Packet53BlockChange()
	{
		isChunkDataPacket = true;
	}
	
	public Packet53BlockChange(int p_i3364_1_, int p_i3364_2_, int p_i3364_3_, World p_i3364_4_)
	{
		isChunkDataPacket = true;
		xPosition = p_i3364_1_;
		yPosition = p_i3364_2_;
		zPosition = p_i3364_3_;
		type = p_i3364_4_.getBlockId(p_i3364_1_, p_i3364_2_, p_i3364_3_);
		metadata = p_i3364_4_.getBlockMetadata(p_i3364_1_, p_i3364_2_, p_i3364_3_);
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleBlockChange(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.read();
		zPosition = p_73267_1_.readInt();
		type = p_73267_1_.readShort();
		metadata = p_73267_1_.read();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.write(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeShort(type);
		p_73273_1_.write(metadata);
	}
}
