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
	
	public Packet5PlayerInventory(int par1, int par2, ItemStack par3ItemStack)
	{
		entityID = par1;
		slot = par2;
		itemSlot = par3ItemStack == null ? null : par3ItemStack.copy();
	}
	
	@Override public boolean containsSameEntityIDAs(Packet par1Packet)
	{
		Packet5PlayerInventory var2 = (Packet5PlayerInventory) par1Packet;
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handlePlayerInventory(this);
	}
	
	@Override public void readPacketData(DataInputStream par1DataInputStream) throws IOException
	{
		entityID = par1DataInputStream.readInt();
		slot = par1DataInputStream.readShort();
		itemSlot = readItemStack(par1DataInputStream);
	}
	
	@Override public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
	{
		par1DataOutputStream.writeInt(entityID);
		par1DataOutputStream.writeShort(slot);
		writeItemStack(itemSlot, par1DataOutputStream);
	}
}
