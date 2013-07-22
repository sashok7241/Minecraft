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
	
	public Packet15Place(int par1, int par2, int par3, int par4, ItemStack par5ItemStack, float par6, float par7, float par8)
	{
		xPosition = par1;
		yPosition = par2;
		zPosition = par3;
		direction = par4;
		itemStack = par5ItemStack != null ? par5ItemStack.copy() : null;
		xOffset = par6;
		yOffset = par7;
		zOffset = par8;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handlePlace(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		xPosition = par1DataInputStream.readInt();
		yPosition = par1DataInputStream.read();
		zPosition = par1DataInputStream.readInt();
		direction = par1DataInputStream.read();
		itemStack = readItemStack(par1DataInputStream);
		xOffset = par1DataInputStream.read() / 16.0F;
		yOffset = par1DataInputStream.read() / 16.0F;
		zOffset = par1DataInputStream.read() / 16.0F;
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(xPosition);
		par1DataOutputStream.write(yPosition);
		par1DataOutputStream.writeInt(zPosition);
		par1DataOutputStream.write(direction);
		writeItemStack(itemStack, par1DataOutputStream);
		par1DataOutputStream.write((int) (xOffset * 16.0F));
		par1DataOutputStream.write((int) (yOffset * 16.0F));
		par1DataOutputStream.write((int) (zOffset * 16.0F));
	}
}
