package net.minecraft.src;

import net.minecraft.client.Minecraft;

class StatStringFormatKeyInv implements IStatStringFormat
{
	final Minecraft mc;
	
	StatStringFormatKeyInv(Minecraft par1Minecraft)
	{
		mc = par1Minecraft;
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
