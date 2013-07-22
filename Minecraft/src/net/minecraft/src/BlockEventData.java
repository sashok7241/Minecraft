package net.minecraft.src;

public class BlockEventData
{
	private int coordX;
	private int coordY;
	private int coordZ;
	private int blockID;
	private int eventID;
	private int eventParameter;
	
	public BlockEventData(int par1, int par2, int par3, int par4, int par5, int par6)
	{
		coordX = par1;
		coordY = par2;
		coordZ = par3;
		eventID = par5;
		eventParameter = par6;
		blockID = par4;
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof BlockEventData)) return false;
		else
		{
			BlockEventData var2 = (BlockEventData) par1Obj;
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
