package net.minecraft.src;

public class NextTickListEntry implements Comparable
{
	private static long nextTickEntryID = 0L;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	public int blockID;
	public long scheduledTime;
	public int priority;
	private long tickEntryID;
	
	public NextTickListEntry(int par1, int par2, int par3, int par4)
	{
		tickEntryID = nextTickEntryID++;
		xCoord = par1;
		yCoord = par2;
		zCoord = par3;
		blockID = par4;
	}
	
	public int comparer(NextTickListEntry par1NextTickListEntry)
	{
		return scheduledTime < par1NextTickListEntry.scheduledTime ? -1 : scheduledTime > par1NextTickListEntry.scheduledTime ? 1 : priority != par1NextTickListEntry.priority ? priority - par1NextTickListEntry.priority : tickEntryID < par1NextTickListEntry.tickEntryID ? -1 : tickEntryID > par1NextTickListEntry.tickEntryID ? 1 : 0;
	}
	
	@Override public int compareTo(Object par1Obj)
	{
		return comparer((NextTickListEntry) par1Obj);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof NextTickListEntry)) return false;
		else
		{
			NextTickListEntry var2 = (NextTickListEntry) par1Obj;
			return xCoord == var2.xCoord && yCoord == var2.yCoord && zCoord == var2.zCoord && Block.isAssociatedBlockID(blockID, var2.blockID);
		}
	}
	
	@Override public int hashCode()
	{
		return (xCoord * 1024 * 1024 + zCoord * 1024 + yCoord) * 256;
	}
	
	public void setPriority(int par1)
	{
		priority = par1;
	}
	
	public NextTickListEntry setScheduledTime(long par1)
	{
		scheduledTime = par1;
		return this;
	}
	
	@Override public String toString()
	{
		return blockID + ": (" + xCoord + ", " + yCoord + ", " + zCoord + "), " + scheduledTime + ", " + priority + ", " + tickEntryID;
	}
}
