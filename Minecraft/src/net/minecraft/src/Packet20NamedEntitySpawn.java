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
	
	public Packet20NamedEntitySpawn(EntityPlayer par1EntityPlayer)
	{
		entityId = par1EntityPlayer.entityId;
		name = par1EntityPlayer.username;
		xPosition = MathHelper.floor_double(par1EntityPlayer.posX * 32.0D);
		yPosition = MathHelper.floor_double(par1EntityPlayer.posY * 32.0D);
		zPosition = MathHelper.floor_double(par1EntityPlayer.posZ * 32.0D);
		rotation = (byte) (int) (par1EntityPlayer.rotationYaw * 256.0F / 360.0F);
		pitch = (byte) (int) (par1EntityPlayer.rotationPitch * 256.0F / 360.0F);
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		currentItem = var2 == null ? 0 : var2.itemID;
		metadata = par1EntityPlayer.getDataWatcher();
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleNamedEntitySpawn(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityId = par1DataInputStream.readInt();
		name = readString(par1DataInputStream, 16);
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.readInt();
		zPosition = par1DataInputStream.readInt();
		rotation = par1DataInputStream.readByte();
		pitch = par1DataInputStream.readByte();
		currentItem = par1DataInputStream.readShort();
		metadataWatchableObjects = DataWatcher.readWatchableObjects(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityId);
		writeString(name, par1DataOutputStream);
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.writeInt(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.writeByte(rotation);
		par1DataOutputStream.writeByte(pitch);
		par1DataOutputStream.writeShort(currentItem);
		metadata.writeWatchableObjects(par1DataOutputStream);
	}
}
