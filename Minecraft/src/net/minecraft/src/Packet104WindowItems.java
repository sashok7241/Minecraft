package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class Packet104WindowItems extends Packet
{
	public int windowId;
	public ItemStack[] itemStack;
	
	public Packet104WindowItems()
	{
	}
	
	public Packet104WindowItems(int par1, List par2List)
	{
		windowId = par1;
		itemStack = new ItemStack[par2List.size()];
		for(int var3 = 0; var3 < itemStack.length; ++var3)
		{
			ItemStack var4 = (ItemStack) par2List.get(var3);
			itemStack[var3] = var4 == null ? null : var4.copy();
		}
	}
	
	@Override public int getPacketSize()
	{
		return 3 + itemStack.length * 5;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleWindowItems(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		windowId = par1DataInputStream.readByte();
		short var2 = par1DataInputStream.readShort();
		itemStack = new ItemStack[var2];
		for(int var3 = 0; var3 < var2; ++var3)
		{
			itemStack[var3] = readItemStack(par1DataInputStream);
		}
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeShort(itemStack.length);
		for(ItemStack element : itemStack)
		{
			writeItemStack(element, par1DataOutputStream);
		}
	}
}
