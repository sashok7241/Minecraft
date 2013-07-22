package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager
{
	private final Properties properties = new Properties();
	private final ILogAgent logger;
	private final File associatedFile;
	
	public PropertyManager(File par1File, ILogAgent par2ILogAgent)
	{
		associatedFile = par1File;
		logger = par2ILogAgent;
		if(par1File.exists())
		{
			FileInputStream var3 = null;
			try
			{
				var3 = new FileInputStream(par1File);
				properties.load(var3);
			} catch(Exception var13)
			{
				par2ILogAgent.logWarningException("Failed to load " + par1File, var13);
				logMessageAndSave();
			} finally
			{
				if(var3 != null)
				{
					try
					{
						var3.close();
					} catch(IOException var12)
					{
						;
					}
				}
			}
		} else
		{
			par2ILogAgent.logWarning(par1File + " does not exist");
			logMessageAndSave();
		}
	}
	
	public boolean getBooleanProperty(String par1Str, boolean par2)
	{
		try
		{
			return Boolean.parseBoolean(getProperty(par1Str, "" + par2));
		} catch(Exception var4)
		{
			properties.setProperty(par1Str, "" + par2);
			return par2;
		}
	}
	
	public int getIntProperty(String par1Str, int par2)
	{
		try
		{
			return Integer.parseInt(getProperty(par1Str, "" + par2));
		} catch(Exception var4)
		{
			properties.setProperty(par1Str, "" + par2);
			return par2;
		}
	}
	
	public File getPropertiesFile()
	{
		return associatedFile;
	}
	
	public String getProperty(String par1Str, String par2Str)
	{
		if(!properties.containsKey(par1Str))
		{
			properties.setProperty(par1Str, par2Str);
			saveProperties();
		}
		return properties.getProperty(par1Str, par2Str);
	}
	
	public void logMessageAndSave()
	{
		logger.logInfo("Generating new properties file");
		saveProperties();
	}
	
	public void saveProperties()
	{
		FileOutputStream var1 = null;
		try
		{
			var1 = new FileOutputStream(associatedFile);
			properties.store(var1, "Minecraft server properties");
		} catch(Exception var11)
		{
			logger.logWarningException("Failed to save " + associatedFile, var11);
			logMessageAndSave();
		} finally
		{
			if(var1 != null)
			{
				try
				{
					var1.close();
				} catch(IOException var10)
				{
					;
				}
			}
		}
	}
	
	public void setProperty(String par1Str, Object par2Obj)
	{
		properties.setProperty(par1Str, "" + par2Obj);
	}
}
