package net.minecraft.src;

public class ChatLine
{
	private final int updateCounterCreated;
	private final String lineString;
	private final int chatLineID;
	
	public ChatLine(int p_i3021_1_, String p_i3021_2_, int p_i3021_3_)
	{
		lineString = p_i3021_2_;
		updateCounterCreated = p_i3021_1_;
		chatLineID = p_i3021_3_;
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
