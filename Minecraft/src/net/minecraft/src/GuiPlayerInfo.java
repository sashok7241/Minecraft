package net.minecraft.src;

public class GuiPlayerInfo
{
	public final String name;
	private final String nameinLowerCase;
	public int responseTime;
	
	public GuiPlayerInfo(String par1Str)
	{
		name = par1Str;
		nameinLowerCase = par1Str.toLowerCase();
	}
}
