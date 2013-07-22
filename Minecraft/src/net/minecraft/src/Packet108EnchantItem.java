package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		windowId = par1DataInput.readByte();
		enchantment = par1DataInput.readByte();
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(windowId);
		par1DataOutput.writeByte(enchantment);
	}
}
