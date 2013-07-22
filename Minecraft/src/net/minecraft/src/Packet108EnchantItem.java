package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet108EnchantItem extends Packet
{
	public int windowId;
	public int enchantment;
	
	public Packet108EnchantItem()
	{
	}
	
	public Packet108EnchantItem(int par1, int par2)
	{
		windowId = par1;
		enchantment = par2;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleEnchantItem(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		windowId = par1DataInputStream.readByte();
		enchantment = par1DataInputStream.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeByte(windowId);
		par1DataOutputStream.writeByte(enchantment);
	}
}
