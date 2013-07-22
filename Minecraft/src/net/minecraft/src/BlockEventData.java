package net.minecraft.src;

public class BlockEventData
{
	private int coordX;
	private int coordY;
	private int coordZ;
	private int blockID;
	private int eventID;
	private int eventParameter;
	
	public BlockEventData(int p_i3742_1_, int p_i3742_2_, int p_i3742_3_, int p_i3742_4_, int p_i3742_5_, int p_i3742_6_)
	{
		coordX = p_i3742_1_;
		coordY = p_i3742_2_;
		coordZ = p_i3742_3_;
		eventID = p_i3742_5_;
		eventParameter = p_i3742_6_;
		blockID = p_i3742_4_;
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof BlockEventData)) return false;
		else
		{
			BlockEventData var2 = (BlockEventData) p_equals_1_;
			return coordX == var2.coordX && coordY == var2.coordY && coordZ == var2.coordZ && eventID == var2.eventID && eventParameter == var2.eventParameter && blockID == var2.blockID;
		}
	}
	
	public int getBlockID()
	{
		return blockID;
	}
	
	public int getEventID()
	{
		return eventID;
	}
	
	public int getEventParameter()
	{
		return eventParameter;
	}
	
	public int getX()
	{
		return coordX;
	}
	
	public int getY()
	{
		return coordY;
	}
	
	public int getZ()
	{
		return coordZ;
	}
	
	@Override public String toString()
	{
		return "TE(" + coordX + "," + coordY + "," + coordZ + ")," + eventID + "," + eventParameter + "," + blockID;
	}
}
