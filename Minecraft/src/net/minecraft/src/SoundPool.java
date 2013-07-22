package net.minecraft.src;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SoundPool
{
	private Random rand = new Random();
	private Map nameToSoundPoolEntriesMapping = new HashMap();
	private List allSoundPoolEntries = new ArrayList();
	public int numberOfSoundPoolEntries = 0;
	public boolean isGetRandomSound = true;
	
	public SoundPoolEntry addSound(String par1Str, File p_77459_2_)
	{
		try
		{
			String var3 = par1Str;
			par1Str = par1Str.substring(0, par1Str.indexOf("."));
			if(isGetRandomSound)
			{
				while(Character.isDigit(par1Str.charAt(par1Str.length() - 1)))
				{
					par1Str = par1Str.substring(0, par1Str.length() - 1);
				}
			}
			par1Str = par1Str.replaceAll("/", ".");
			if(!nameToSoundPoolEntriesMapping.containsKey(par1Str))
			{
				nameToSoundPoolEntriesMapping.put(par1Str, new ArrayList());
			}
			SoundPoolEntry var4 = new SoundPoolEntry(var3, p_77459_2_.toURI().toURL());
			((List) nameToSoundPoolEntriesMapping.get(par1Str)).add(var4);
			allSoundPoolEntries.add(var4);
			++numberOfSoundPoolEntries;
			return var4;
		} catch(MalformedURLException var5)
		{
			var5.printStackTrace();
			throw new RuntimeException(var5);
		}
	}
	
	public SoundPoolEntry getRandomSound()
	{
		return allSoundPoolEntries.isEmpty() ? null : (SoundPoolEntry) allSoundPoolEntries.get(rand.nextInt(allSoundPoolEntries.size()));
	}
	
	public SoundPoolEntry getRandomSoundFromSoundPool(String par1Str)
	{
		List var2 = (List) nameToSoundPoolEntriesMapping.get(par1Str);
		return var2 == null ? null : (SoundPoolEntry) var2.get(rand.nextInt(var2.size()));
	}
}
