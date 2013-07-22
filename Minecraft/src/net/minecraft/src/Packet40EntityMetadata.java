package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;

public class Packet40EntityMetadata extends Packet
{
	public int entityId;
	private List metadata;
	
	public Packet40EntityMetadata()
	{
	}
	
	public Packet40EntityMetadata(int par1, DataWatcher par2DataWatcher, boolean par3)
	{
		entityId = par1;
		if(par3)
		{
			metadata = par2DataWatcher.getAllWatched();
		} else
		{
			metadata = par2DataWatcher.unwatchAndReturnAllWatched();
		}
	}
	
	public List getMetadata()
	{
		return metadata;
	}
	
	@Override public int getPacketSize()
	{
		return 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEntityMetadata(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		metadata = DataWatcher.readWatchableObjects(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		DataWatcher.writeObjectsInListToStream(metadata, par1DataOutput);
	}
}
