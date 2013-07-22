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
	
	public StringTranslate(String p_i3260_1_)
	{
		loadLanguageList();
		setLanguage(p_i3260_1_, false);
	}
	
	public synchronized boolean containsTranslateKey(String p_94520_1_)
	{
		return translateTable.containsKey(p_94520_1_);
	}
	
	public synchronized void func_94519_a(String p_94519_1_, File p_94519_2_)
	{
		int var3 = p_94519_1_.indexOf(46);
		if(var3 > 0)
		{
			p_94519_1_ = p_94519_1_.substring(0, var3);
		}
		field_94521_d.put(p_94519_1_, p_94519_2_);
		if(p_94519_1_.contains(currentLanguage))
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
	
	private void loadLanguage(Properties p_74812_1_, String p_74812_2_) throws IOException
	{
		BufferedReader var3 = null;
		if(field_94521_d.containsKey(p_74812_2_))
		{
			var3 = new BufferedReader(new FileReader((File) field_94521_d.get(p_74812_2_)));
		} else
		{
			var3 = new BufferedReader(new InputStreamReader(StringTranslate.class.getResourceAsStream("/lang/" + p_74812_2_ + ".lang"), "UTF-8"));
		}
		for(String var4 = var3.readLine(); var4 != null; var4 = var3.readLine())
		{
			var4 = var4.trim();
			if(!var4.startsWith("#"))
			{
				String[] var5 = var4.split("=");
				if(var5 != null && var5.length == 2)
				{
					p_74812_1_.setProperty(var5[0], var5[1]);
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
	
	public synchronized void setLanguage(String p_74810_1_, boolean p_74810_2_)
	{
		if(p_74810_2_ || !p_74810_1_.equals(currentLanguage))
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
			if(!"en_US".equals(p_74810_1_))
			{
				try
				{
					loadLanguage(var3, p_74810_1_);
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
			currentLanguage = p_74810_1_;
			translateTable = var3;
		}
	}
	
	public synchronized String translateKey(String p_74805_1_)
	{
		return translateTable.getProperty(p_74805_1_, p_74805_1_);
	}
	
	public synchronized String translateKeyFormat(String p_74803_1_, Object ... p_74803_2_)
	{
		String var3 = translateTable.getProperty(p_74803_1_, p_74803_1_);
		try
		{
			return String.format(var3, p_74803_2_);
		} catch(IllegalFormatException var5)
		{
			return "Format error: " + var3;
		}
	}
	
	public synchronized String translateNamedKey(String p_74809_1_)
	{
		return translateTable.getProperty(p_74809_1_ + ".name", "");
	}
	
	public static StringTranslate getInstance()
	{
		return instance;
	}
	
	public static boolean isBidirectional(String p_74802_0_)
	{
		return "ar_SA".equals(p_74802_0_) || "he_IL".equals(p_74802_0_);
	}
}
