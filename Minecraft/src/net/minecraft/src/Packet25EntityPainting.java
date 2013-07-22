package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet25EntityPainting(EntityPainting par1EntityPainting)
	{
		entityId = par1EntityPainting.entityId;
		xPosition = par1EntityPainting.xPosition;
		yPosition = par1EntityPainting.yPosition;
		zPosition = par1EntityPainting.zPosition;
		direction = par1EntityPainting.hangingDirection;
		title = par1EntityPainting.art.title;
	}
	
	@Override public int getPacketSize()
	{
		return 24;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityPainting(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		title = readString(par1DataInput, EnumArt.maxArtTitleLength);
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
		direction = par1DataInput.readInt();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		writeString(title, par1DataOutput);
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeInt(direction);
	}
}
