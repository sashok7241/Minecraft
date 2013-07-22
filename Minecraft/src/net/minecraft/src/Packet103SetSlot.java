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
	
	public Packet103SetSlot(int p_i3314_1_, int p_i3314_2_, ItemStack p_i3314_3_)
	{
		windowId = p_i3314_1_;
		itemSlot = p_i3314_2_;
		myItemStack = p_i3314_3_ == null ? p_i3314_3_ : p_i3314_3_.copy();
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleSetSlot(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		windowId = p_73267_1_.readByte();
		itemSlot = p_73267_1_.readShort();
		myItemStack = readItemStack(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(windowId);
		p_73273_1_.writeShort(itemSlot);
		writeItemStack(myItemStack, p_73273_1_);
	}
}
