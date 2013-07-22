package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		slot = par1DataInput.readShort();
		itemStack = readItemStack(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeShort(slot);
		writeItemStack(itemStack, par1DataOutput);
	}
}
