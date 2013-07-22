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
	
	public Packet108EnchantItem(int p_i3308_1_, int p_i3308_2_)
	{
		windowId = p_i3308_1_;
		enchantment = p_i3308_2_;
	}
	
	@Override public int getPacketSize()
	{
		return 2;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleEnchantItem(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		windowId = p_73267_1_.readByte();
		enchantment = p_73267_1_.readByte();
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(windowId);
		p_73273_1_.writeByte(enchantment);
	}
}
