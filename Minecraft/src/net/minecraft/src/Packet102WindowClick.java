package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		window_Id = par1DataInput.readByte();
		inventorySlot = par1DataInput.readShort();
		mouseClick = par1DataInput.readByte();
		action = par1DataInput.readShort();
		holdingShift = par1DataInput.readByte();
		itemStack = readItemStack(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeByte(window_Id);
		par1DataOutput.writeShort(inventorySlot);
		par1DataOutput.writeByte(mouseClick);
		par1DataOutput.writeShort(action);
		par1DataOutput.writeByte(holdingShift);
		writeItemStack(itemStack, par1DataOutput);
	}
}
