package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class StatStringFormatKeyInv implements IStatStringFormat
{
	final Minecraft mc;
	
	public StatStringFormatKeyInv(Minecraft p_i3018_1_)
	{
		mc = p_i3018_1_;
	}
	
	@Override public String formatString(String par1Str)
	{
		try
		{
			return String.format(par1Str, new Object[] { GameSettings.getKeyDisplayString(mc.gameSettings.keyBindInventory.keyCode) });
		} catch(Exception var3)
		{
			return "Error: " + var3.getLocalizedMessage();
		}
	}
}
