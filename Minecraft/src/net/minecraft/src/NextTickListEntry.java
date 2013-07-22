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
	
	public NextTickListEntry(int p_i3741_1_, int p_i3741_2_, int p_i3741_3_, int p_i3741_4_)
	{
		tickEntryID = nextTickEntryID++;
		xCoord = p_i3741_1_;
		yCoord = p_i3741_2_;
		zCoord = p_i3741_3_;
		blockID = p_i3741_4_;
	}
	
	public int comparer(NextTickListEntry p_77175_1_)
	{
		return scheduledTime < p_77175_1_.scheduledTime ? -1 : scheduledTime > p_77175_1_.scheduledTime ? 1 : priority != p_77175_1_.priority ? priority - p_77175_1_.priority : tickEntryID < p_77175_1_.tickEntryID ? -1 : tickEntryID > p_77175_1_.tickEntryID ? 1 : 0;
	}
	
	@Override public int compareTo(Object p_compareTo_1_)
	{
		return comparer((NextTickListEntry) p_compareTo_1_);
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof NextTickListEntry)) return false;
		else
		{
			NextTickListEntry var2 = (NextTickListEntry) p_equals_1_;
			return xCoord == var2.xCoord && yCoord == var2.yCoord && zCoord == var2.zCoord && Block.isAssociatedBlockID(blockID, var2.blockID);
		}
	}
	
	@Override public int hashCode()
	{
		return (xCoord * 1024 * 1024 + zCoord * 1024 + yCoord) * 256;
	}
	
	public void setPriority(int p_82753_1_)
	{
		priority = p_82753_1_;
	}
	
	public NextTickListEntry setScheduledTime(long p_77176_1_)
	{
		scheduledTime = p_77176_1_;
		return this;
	}
	
	@Override public String toString()
	{
		return blockID + ": (" + xCoord + ", " + yCoord + ", " + zCoord + "), " + scheduledTime + ", " + priority + ", " + tickEntryID;
	}
}
