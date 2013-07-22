package net.minecraft.src;

public class GuiPlayerInfo
{
	public final String name;
	private final String nameinLowerCase;
	public int responseTime;
	
	public GuiPlayerInfo(String p_i3109_1_)
	{
		name = p_i3109_1_;
		nameinLowerCase = p_i3109_1_.toLowerCase();
	}
}
