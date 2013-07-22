package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet5PlayerInventory extends Packet
{
	public int entityID;
	public int slot;
	private ItemStack itemSlot;
	
	public Packet5PlayerInventory()
	{
	}
	
	public Packet5PlayerInventory(int p_i3350_1_, int p_i3350_2_, ItemStack p_i3350_3_)
	{
		entityID = p_i3350_1_;
		slot = p_i3350_2_;
		itemSlot = p_i3350_3_ == null ? null : p_i3350_3_.copy();
	}
	
	@Override public boolean containsSameEntityIDAs(Packet p_73268_1_)
	{
		Packet5PlayerInventory var2 = (Packet5PlayerInventory) p_73268_1_;
		return var2.entityID == entityID && var2.slot == slot;
	}
	
	public ItemStack getItemSlot()
	{
		return itemSlot;
	}
	
	@Override public int getPacketSize()
	{
		return 8;
	}
	
	@Override public boolean isRealPacket()
	{
		return true;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handlePlayerInventory(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		entityID = p_73267_1_.readInt();
		slot = p_73267_1_.readShort();
		itemSlot = readItemStack(p_73267_1_);
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(entityID);
		p_73273_1_.writeShort(slot);
		writeItemStack(itemSlot, p_73273_1_);
	}
}
