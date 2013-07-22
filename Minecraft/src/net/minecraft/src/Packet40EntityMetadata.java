package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet40EntityMetadata extends Packet
{
	public int entityId;
	private List metadata;
	
	public Packet40EntityMetadata()
	{
	}
	
	public Packet40EntityMetadata(int p_i5034_1_, DataWatcher p_i5034_2_, boolean p_i5034_3_)
	{
		entityId = p_i5034_1_;
		if(p_i5034_3_)
		{
			metadata = p_i5034_2_.getAllWatched();
		} else
		{
			metadata = p_i5034_2_.unwatchAndReturnAllWatched();
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
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEntityMetadata(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		metadata = DataWatcher.readWatchableObjects(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		DataWatcher.writeObjectsInListToStream(metadata, p_73273_1_);
	}
}
