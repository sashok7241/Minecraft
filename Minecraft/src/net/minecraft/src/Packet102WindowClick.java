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
	
	public Packet102WindowClick(int p_i5032_1_, int p_i5032_2_, int p_i5032_3_, int p_i5032_4_, ItemStack p_i5032_5_, short p_i5032_6_)
	{
		window_Id = p_i5032_1_;
		inventorySlot = p_i5032_2_;
		mouseClick = p_i5032_3_;
		itemStack = p_i5032_5_ != null ? p_i5032_5_.copy() : null;
		action = p_i5032_6_;
		holdingShift = p_i5032_4_;
	}
	
	@Override public int getPacketSize()
	{
		return 11;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleWindowClick(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		window_Id = p_73267_1_.readByte();
		inventorySlot = p_73267_1_.readShort();
		mouseClick = p_73267_1_.readByte();
		action = p_73267_1_.readShort();
		holdingShift = p_73267_1_.readByte();
		itemStack = readItemStack(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(window_Id);
		p_73273_1_.writeShort(inventorySlot);
		p_73273_1_.writeByte(mouseClick);
		p_73273_1_.writeShort(action);
		p_73273_1_.writeByte(holdingShift);
		writeItemStack(itemStack, p_73273_1_);
	}
}
