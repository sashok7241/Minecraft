package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet102WindowClick extends Packet
{
	public int window_Id;
	public int inventorySlot;
	public int mouseClick;
	public short action;
	public ItemStack itemStack;
	public int holdingShift;
	
	public Packet102WindowClick()
	{
	}
	
	public Packet102WindowClick(int par1, int par2, int par3, int par4, ItemStack par5ItemStack, short par6)
	{
		window_Id = par1;
		inventorySlot = par2;
		mouseClick = par3;
		itemStack = par5ItemStack != null ? par5ItemStack.copy() : null;
		action = par6;
		holdingShift = par4;
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleWindowClick(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		window_Id = par1DataInputStream.readByte();
		inventorySlot = par1DataInputStream.readShort();
		mouseClick = par1DataInputStream.readByte();
		action = par1DataInputStream.readShort();
		holdingShift = par1DataInputStream.readByte();
		itemStack = readItemStack(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(window_Id);
		par1DataOutputStream.writeShort(inventorySlot);
		par1DataOutputStream.writeByte(mouseClick);
		par1DataOutputStream.writeShort(action);
		par1DataOutputStream.writeByte(holdingShift);
		writeItemStack(itemStack, par1DataOutputStream);
	}
}
