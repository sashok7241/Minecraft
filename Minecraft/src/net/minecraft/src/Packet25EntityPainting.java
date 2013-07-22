package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet25EntityPainting extends Packet
{
	public int entityId;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public int direction;
	public String title;
	
	public Packet25EntityPainting()
	{
	}
	
	public Packet25EntityPainting(EntityPainting p_i3295_1_)
	{
		entityId = p_i3295_1_.entityId;
		xPosition = p_i3295_1_.xPosition;
		yPosition = p_i3295_1_.yPosition;
		zPosition = p_i3295_1_.zPosition;
		direction = p_i3295_1_.hangingDirection;
		title = p_i3295_1_.art.title;
	}
	
	@Override public int getPacketSize()
	{
		return 24;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityPainting(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		title = readString(p_73267_1_, EnumArt.maxArtTitleLength);
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
		direction = p_73267_1_.readInt();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		writeString(title, p_73273_1_);
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeInt(direction);
	}
}
