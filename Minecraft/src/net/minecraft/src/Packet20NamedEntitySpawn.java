package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
		name = par1EntityPlayer.getCommandSenderName();
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityId = par1DataInput.readInt();
		name = readString(par1DataInput, 16);
		xPosition = par1DataInput.readInt();
		yPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
		rotation = par1DataInput.readByte();
		pitch = par1DataInput.readByte();
		currentItem = par1DataInput.readShort();
		metadataWatchableObjects = DataWatcher.readWatchableObjects(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityId);
		writeString(name, par1DataOutput);
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(yPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeByte(rotation);
		par1DataOutput.writeByte(pitch);
		par1DataOutput.writeShort(currentItem);
		metadata.writeWatchableObjects(par1DataOutput);
	}
}
