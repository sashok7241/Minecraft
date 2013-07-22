package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
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
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		entityID = par1DataInput.readInt();
		slot = par1DataInput.readShort();
		itemSlot = readItemStack(par1DataInput);
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(entityID);
		par1DataOutput.writeShort(slot);
		writeItemStack(itemSlot, par1DataOutput);
	}
}
