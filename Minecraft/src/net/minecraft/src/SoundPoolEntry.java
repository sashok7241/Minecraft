package net.minecraft.src;

import java.net.URL;

public class SoundPoolEntry
{
	private final String soundName;
	private final URL soundUrl;
	
	public SoundPoolEntry(String par1Str, URL par2URL)
	{
		soundName = par1Str;
		soundUrl = par2URL;
	}
	
	public URL func_110457_b()
	{
		return soundUrl;
	}
	
	public String func_110458_a()
	{
		return soundName;
	}
}
