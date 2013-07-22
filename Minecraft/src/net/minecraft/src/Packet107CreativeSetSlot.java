package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet107CreativeSetSlot extends Packet
{
	public int slot;
	public ItemStack itemStack;
	
	public Packet107CreativeSetSlot()
	{
	}
	
	public Packet107CreativeSetSlot(int par1, ItemStack par2ItemStack)
	{
		slot = par1;
		itemStack = par2ItemStack != null ? par2ItemStack.copy() : null;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleCreativeSetSlot(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		slot = par1DataInputStream.readShort();
		itemStack = readItemStack(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeShort(slot);
		writeItemStack(itemStack, par1DataOutputStream);
	}
}
