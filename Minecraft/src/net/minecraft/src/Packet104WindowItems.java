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
	
	public Packet104WindowItems(int p_i3312_1_, List p_i3312_2_)
	{
		windowId = p_i3312_1_;
		itemStack = new ItemStack[p_i3312_2_.size()];
		for(int var3 = 0; var3 < itemStack.length; ++var3)
		{
			ItemStack var4 = (ItemStack) p_i3312_2_.get(var3);
			itemStack[var3] = var4 == null ? null : var4.copy();
		}
	}
	
	@Override public int getPacketSize()
	{
		return 3 + itemStack.length * 5;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleWindowItems(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		windowId = p_73267_1_.readByte();
		short var2 = p_73267_1_.readShort();
		itemStack = new ItemStack[var2];
		for(int var3 = 0; var3 < var2; ++var3)
		{
			itemStack[var3] = readItemStack(p_73267_1_);
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeByte(windowId);
		p_73273_1_.writeShort(itemStack.length);
		for(ItemStack element : itemStack)
		{
			writeItemStack(element, p_73273_1_);
		}
	}
}
