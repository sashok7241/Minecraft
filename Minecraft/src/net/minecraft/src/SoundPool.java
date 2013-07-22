package net.minecraft.src;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SoundPool
{
	private final Random rand = new Random();
	private final Map nameToSoundPoolEntriesMapping = Maps.newHashMap();
	private final ResourceManager field_110657_c;
	private final String field_110656_d;
	private final boolean isGetRandomSound;
	
	public SoundPool(ResourceManager par1ResourceManager, String par2Str, boolean par3)
	{
		field_110657_c = par1ResourceManager;
		field_110656_d = par2Str;
		isGetRandomSound = par3;
	}
	
	public void addSound(String par1Str)
	{
		try
		{
			String var2 = par1Str;
			par1Str = par1Str.substring(0, par1Str.indexOf("."));
			if(isGetRandomSound)
			{
				while(Character.isDigit(par1Str.charAt(par1Str.length() - 1)))
				{
					par1Str = par1Str.substring(0, par1Str.length() - 1);
				}
			}
			par1Str = par1Str.replaceAll("/", ".");
			Object var3 = nameToSoundPoolEntriesMapping.get(par1Str);
			if(var3 == null)
			{
				var3 = Lists.newArrayList();
				nameToSoundPoolEntriesMapping.put(par1Str, var3);
			}
			((List) var3).add(new SoundPoolEntry(var2, func_110654_c(var2)));
		} catch(MalformedURLException var4)
		{
			var4.printStackTrace();
			throw new RuntimeException(var4);
		}
	}
	
	private URL func_110654_c(String par1Str) throws MalformedURLException
	{
		ResourceLocation var2 = new ResourceLocation(par1Str);
		String var3 = String.format("%s:%s:%s/%s", new Object[] { "mcsounddomain", var2.func_110624_b(), field_110656_d, var2.func_110623_a() });
		SoundPoolProtocolHandler var4 = new SoundPoolProtocolHandler(this);
		return new URL((URL) null, var3, var4);
	}
	
	public SoundPoolEntry getRandomSound()
	{
		if(nameToSoundPoolEntriesMapping.isEmpty()) return null;
		else
		{
			ArrayList var1 = Lists.newArrayList(nameToSoundPoolEntriesMapping.keySet());
			return getRandomSoundFromSoundPool((String) var1.get(rand.nextInt(var1.size())));
		}
	}
	
	public SoundPoolEntry getRandomSoundFromSoundPool(String par1Str)
	{
		List var2 = (List) nameToSoundPoolEntriesMapping.get(par1Str);
		return var2 == null ? null : (SoundPoolEntry) var2.get(rand.nextInt(var2.size()));
	}
	
	static ResourceManager func_110655_a(SoundPool par0SoundPool)
	{
		return par0SoundPool.field_110657_c;
	}
}
