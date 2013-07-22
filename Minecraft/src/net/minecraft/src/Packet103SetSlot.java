package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		windowId = par1DataInput.readByte();
		itemSlot = par1DataInput.readShort();
		myItemStack = readItemStack(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(windowId);
		par1DataOutput.writeShort(itemSlot);
		writeItemStack(myItemStack, par1DataOutput);
	}
}
