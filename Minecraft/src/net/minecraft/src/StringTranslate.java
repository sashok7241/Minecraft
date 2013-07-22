package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class StringTranslate
{
	private static final Pattern field_111053_a = Pattern.compile("%(\\d+\\$)?[\\d\\.]*[df]");
	private static final Splitter field_135065_b = Splitter.on('=').limit(2);
	private static StringTranslate instance = new StringTranslate();
	private Map languageList = Maps.newHashMap();
	
	public StringTranslate()
	{
		try
		{
			InputStream var1 = StringTranslate.class.getResourceAsStream("/assets/minecraft/lang/en_US.lang");
			Iterator var2 = IOUtils.readLines(var1, Charsets.UTF_8).iterator();
			while(var2.hasNext())
			{
				String var3 = (String) var2.next();
				if(!var3.isEmpty() && var3.charAt(0) != 35)
				{
					String[] var4 = (String[]) Iterables.toArray(field_135065_b.split(var3), String.class);
					if(var4 != null && var4.length == 2)
					{
						String var5 = var4[0];
						String var6 = field_111053_a.matcher(var4[1]).replaceAll("%$1s");
						languageList.put(var5, var6);
					}
				}
			}
		} catch(IOException var7)
		{
			;
		}
	}
	
	public synchronized boolean containsTranslateKey(String par1Str)
	{
		return languageList.containsKey(par1Str);
	}
	
	private String func_135064_c(String par1Str)
	{
		String var2 = (String) languageList.get(par1Str);
		return var2 == null ? par1Str : var2;
	}
	
	public synchronized String translateKey(String par1Str)
	{
		return func_135064_c(par1Str);
	}
	
	public synchronized String translateKeyFormat(String par1Str, Object ... par2ArrayOfObj)
	{
		String var3 = func_135064_c(par1Str);
		try
		{
			return String.format(var3, par2ArrayOfObj);
		} catch(IllegalFormatException var5)
		{
			return "Format error: " + var3;
		}
	}
	
	public static synchronized void func_135063_a(Map par0Map)
	{
		instance.languageList.clear();
		instance.languageList.putAll(par0Map);
	}
	
	static StringTranslate getInstance()
	{
		return instance;
	}
}
