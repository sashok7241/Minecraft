package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet103SetSlot extends Packet
{
	public int windowId;
	public int itemSlot;
	public ItemStack myItemStack;
	
	public Packet103SetSlot()
	{
	}
	
	public Packet103SetSlot(int par1, int par2, ItemStack par3ItemStack)
	{
		windowId = par1;
		itemSlot = par2;
		myItemStack = par3ItemStack == null ? par3ItemStack : par3ItemStack.copy();
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleSetSlot(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		windowId = par1DataInputStream.readByte();
		itemSlot = par1DataInputStream.readShort();
		myItemStack = readItemStack(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(itemSlot);
		writeItemStack(myItemStack, par1DataOutputStream);
	}
}
