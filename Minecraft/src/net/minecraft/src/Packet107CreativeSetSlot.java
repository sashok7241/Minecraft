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
	
	public Packet107CreativeSetSlot(int p_i3346_1_, ItemStack p_i3346_2_)
	{
		slot = p_i3346_1_;
		itemStack = p_i3346_2_ != null ? p_i3346_2_.copy() : null;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleCreativeSetSlot(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		slot = p_73267_1_.readShort();
		itemStack = readItemStack(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeShort(slot);
		writeItemStack(itemStack, p_73273_1_);
	}
}
