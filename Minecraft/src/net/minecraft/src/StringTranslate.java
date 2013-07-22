package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.IllegalFormatException;
import java.util.Properties;
import java.util.TreeMap;

public class StringTranslate
{
	private static StringTranslate instance = new StringTranslate("en_US");
	private Properties translateTable = new Properties();
	private TreeMap languageList;
	private TreeMap field_94521_d = new TreeMap();
	private String currentLanguage;
	private boolean isUnicode;
	
	public StringTranslate(String par1Str)
	{
		loadLanguageList();
		setLanguage(par1Str, false);
	}
	
	public synchronized boolean containsTranslateKey(String par1Str)
	{
		return translateTable.containsKey(par1Str);
	}
	
	public synchronized void func_94519_a(String par1Str, File par2File)
	{
		int var3 = par1Str.indexOf(46);
		if(var3 > 0)
		{
			par1Str = par1Str.substring(0, var3);
		}
		field_94521_d.put(par1Str, par2File);
		if(par1Str.contains(currentLanguage))
		{
			setLanguage(currentLanguage, true);
		}
	}
	
	public String getCurrentLanguage()
	{
		return currentLanguage;
	}
	
	public TreeMap getLanguageList()
	{
		return languageList;
	}
	
	public boolean isUnicode()
	{
		return isUnicode;
	}
	
	private void loadLanguage(Properties par1Properties, String par2Str) throws IOException
	{
		BufferedReader var3 = null;
		if(field_94521_d.containsKey(par2Str))
		{
			var3 = new BufferedReader(new FileReader((File) field_94521_d.get(par2Str)));
		} else
		{
			var3 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/" + par2Str + ".lang"), "UTF-8"));
		}
		for(String var4 = var3.readLine(); var4 != null; var4 = var3.readLine())
		{
			var4 = var4.trim();
			if(!var4.startsWith("#"))
			{
				String[] var5 = var4.split("=");
				if(var5 != null && var5.length == 2)
				{
					par1Properties.setProperty(var5[0], var5[1]);
				}
			}
		}
	}
	
	private void loadLanguageList()
	{
		TreeMap var1 = new TreeMap();
		try
		{
			BufferedReader var2 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/languages.txt"), "UTF-8"));
			for(String var3 = var2.readLine(); var3 != null; var3 = var2.readLine())
			{
				String[] var4 = var3.trim().split("=");
				if(var4 != null && var4.length == 2)
				{
					var1.put(var4[0], var4[1]);
				}
			}
		} catch(IOException var5)
		{
			var5.printStackTrace();
			return;
		}
		languageList = var1;
		languageList.put("en_US", "English (US)");
	}
	
	public synchronized void setLanguage(String par1Str, boolean par2)
	{
		if(par2 || !par1Str.equals(currentLanguage))
		{
			Properties var3 = new Properties();
			try
			{
				loadLanguage(var3, "en_US");
			} catch(IOException var9)
			{
				;
			}
			isUnicode = false;
			if(!"en_US".equals(par1Str))
			{
				try
				{
					loadLanguage(var3, par1Str);
					Enumeration var4 = var3.propertyNames();
					while(var4.hasMoreElements() && !isUnicode)
					{
						Object var5 = var4.nextElement();
						Object var6 = var3.get(var5);
						if(var6 != null)
						{
							String var7 = var6.toString();
							for(int var8 = 0; var8 < var7.length(); ++var8)
							{
								if(var7.charAt(var8) >= 256)
								{
									isUnicode = true;
									break;
								}
							}
						}
					}
				} catch(IOException var10)
				{
					var10.printStackTrace();
					return;
				}
			}
			currentLanguage = par1Str;
			translateTable = var3;
		}
	}
	
	public synchronized String translateKey(String par1Str)
	{
		return translateTable.getProperty(par1Str, par1Str);
	}
	
	public synchronized String translateKeyFormat(String par1Str, Object ... par2ArrayOfObj)
	{
		String var3 = translateTable.getProperty(par1Str, par1Str);
		try
		{
			return String.format(var3, par2ArrayOfObj);
		} catch(IllegalFormatException var5)
		{
			return "Format error: " + var3;
		}
	}
	
	public synchronized String translateNamedKey(String par1Str)
	{
		return translateTable.getProperty(par1Str + ".name", "");
	}
	
	public static StringTranslate getInstance()
	{
		return instance;
	}
	
	public static boolean isBidirectional(String par0Str)
	{
		return "ar_SA".equals(par0Str) || "he_IL".equals(par0Str);
	}
}
