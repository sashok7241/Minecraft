package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet15Place extends Packet
{
	private int xPosition;
	private int yPosition;
	private int zPosition;
	private int direction;
	private ItemStack itemStack;
	private float xOffset;
	private float yOffset;
	private float zOffset;
	
	public Packet15Place()
	{
	}
	
	public Packet15Place(int p_i3366_1_, int p_i3366_2_, int p_i3366_3_, int p_i3366_4_, ItemStack p_i3366_5_, float p_i3366_6_, float p_i3366_7_, float p_i3366_8_)
	{
		xPosition = p_i3366_1_;
		yPosition = p_i3366_2_;
		zPosition = p_i3366_3_;
		direction = p_i3366_4_;
		itemStack = p_i3366_5_ != null ? p_i3366_5_.copy() : null;
		xOffset = p_i3366_6_;
		yOffset = p_i3366_7_;
		zOffset = p_i3366_8_;
	}
	
	public int getDirection()
	{
		return direction;
	}
	
	public ItemStack getItemStack()
	{
		return itemStack;
	}
	
	@Override public int getPacketSize()
	{
		return 19;
	}
	
	public float getXOffset()
	{
		return xOffset;
	}
	
	public int getXPosition()
	{
		return xPosition;
	}
	
	public float getYOffset()
	{
		return yOffset;
	}
	
	public int getYPosition()
	{
		return yPosition;
	}
	
	public float getZOffset()
	{
		return zOffset;
	}
	
	public int getZPosition()
	{
		return zPosition;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handlePlace(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readInt();
		yPosition = p_73267_1_.read();
		zPosition = p_73267_1_.readInt();
		direction = p_73267_1_.read();
		itemStack = readItemStack(p_73267_1_);
		xOffset = p_73267_1_.read() / 16.0F;
		yOffset = p_73267_1_.read() / 16.0F;
		zOffset = p_73267_1_.read() / 16.0F;
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.write(yPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.write(direction);
		writeItemStack(itemStack, p_73273_1_);
		p_73273_1_.write((int) (xOffset * 16.0F));
		p_73273_1_.write((int) (yOffset * 16.0F));
		p_73273_1_.write((int) (zOffset * 16.0F));
	}
}
