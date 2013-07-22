package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet20NamedEntitySpawn extends Packet
{
	public int entityId;
	public String name;
	public int xPosition;
	public int yPosition;
	public int zPosition;
	public byte rotation;
	public byte pitch;
	public int currentItem;
	private DataWatcher metadata;
	private List metadataWatchableObjects;
	
	public Packet20NamedEntitySpawn()
	{
	}
	
	public Packet20NamedEntitySpawn(EntityPlayer p_i3296_1_)
	{
		entityId = p_i3296_1_.entityId;
		name = p_i3296_1_.username;
		xPosition = MathHelper.floor_double(p_i3296_1_.posX * 32.0D);
		yPosition = MathHelper.floor_double(p_i3296_1_.posY * 32.0D);
		zPosition = MathHelper.floor_double(p_i3296_1_.posZ * 32.0D);
		rotation = (byte) (int) (p_i3296_1_.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (p_i3296_1_.rotationPitch * 256.0F / 360.0F);
		ItemStack var2 = p_i3296_1_.inventory.getCurrentItem();
		currentItem = var2 == null ? 0 : var2.itemID;
		metadata = p_i3296_1_.getDataWatcher();
	}
	
	@Override public int getPacketSize()
	{
		return 28;
	}
	
	public List getWatchedMetadata()
	{
		if(metadataWatchableObjects == null)
		{
			metadataWatchableObjects = metadata.getAllWatched();
		}
		return metadataWatchableObjects;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleNamedEntitySpawn(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityId = p_73267_1_.readInt();
		name = readString(p_73267_1_, 16);
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
		rotation = p_73267_1_.readByte();
		pitch = p_73267_1_.readByte();
		currentItem = p_73267_1_.readShort();
		metadataWatchableObjects = DataWatcher.readWatchableObjects(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityId);
		writeString(name, p_73273_1_);
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeByte(rotation);
		p_73273_1_.writeByte(pitch);
		p_73273_1_.writeShort(currentItem);
		metadata.writeWatchableObjects(p_73273_1_);
	}
}
