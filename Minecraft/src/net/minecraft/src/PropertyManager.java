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
	
	public PropertyManager(File p_i11028_1_, ILogAgent p_i11028_2_)
	{
		associatedFile = p_i11028_1_;
		logger = p_i11028_2_;
		if(p_i11028_1_.exists())
		{
			FileInputStream var3 = null;
			try
			{
				var3 = new FileInputStream(p_i11028_1_);
				properties.load(var3);
			} catch(Exception var13)
			{
				p_i11028_2_.logWarningException("Failed to load " + p_i11028_1_, var13);
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
			p_i11028_2_.logWarning(p_i11028_1_ + " does not exist");
			logMessageAndSave();
		}
	}
	
	public boolean getBooleanProperty(String p_73670_1_, boolean p_73670_2_)
	{
		try
		{
			return Boolean.parseBoolean(getProperty(p_73670_1_, "" + p_73670_2_));
		} catch(Exception var4)
		{
			properties.setProperty(p_73670_1_, "" + p_73670_2_);
			return p_73670_2_;
		}
	}
	
	public int getIntProperty(String p_73669_1_, int p_73669_2_)
	{
		try
		{
			return Integer.parseInt(getProperty(p_73669_1_, "" + p_73669_2_));
		} catch(Exception var4)
		{
			properties.setProperty(p_73669_1_, "" + p_73669_2_);
			return p_73669_2_;
		}
	}
	
	public File getPropertiesFile()
	{
		return associatedFile;
	}
	
	public String getProperty(String p_73671_1_, String p_73671_2_)
	{
		if(!properties.containsKey(p_73671_1_))
		{
			properties.setProperty(p_73671_1_, p_73671_2_);
			saveProperties();
		}
		return properties.getProperty(p_73671_1_, p_73671_2_);
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
	
	public void setProperty(String p_73667_1_, Object p_73667_2_)
	{
		properties.setProperty(p_73667_1_, "" + p_73667_2_);
	}
}
