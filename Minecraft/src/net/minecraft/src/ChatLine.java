package net.minecraft.src;

public class ChatLine
{
	private final int updateCounterCreated;
	private final String lineString;
	private final int chatLineID;
	
	public ChatLine(int par1, String par2Str, int par3)
	{
		lineString = par2Str;
		updateCounterCreated = par1;
		chatLineID = par3;
	}
	
	public int getChatLineID()
	{
		return chatLineID;
	}
	
	public String getChatLineString()
	{
		return lineString;
	}
	
	public int getUpdatedCounter()
	{
		return updateCounterCreated;
	}
}
