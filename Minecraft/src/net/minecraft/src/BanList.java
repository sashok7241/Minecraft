package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import net.minecraft.server.MinecraftServer;

public class BanList
{
	private final LowerStringMap theBanList = new LowerStringMap();
	private final File fileName;
	private boolean listActive = true;
	
	public BanList(File par1File)
	{
		fileName = par1File;
	}
	
	public Map getBannedList()
	{
		removeExpiredBans();
		return theBanList;
	}
	
	public boolean isBanned(String par1Str)
	{
		if(!isListActive()) return false;
		else
		{
			removeExpiredBans();
			return theBanList.containsKey(par1Str);
		}
	}
	
	public boolean isListActive()
	{
		return listActive;
	}
	
	public void loadBanList()
	{
		if(fileName.isFile())
		{
			BufferedReader var1;
			try
			{
				var1 = new BufferedReader(new FileReader(fileName));
			} catch(FileNotFoundException var4)
			{
				throw new Error();
			}
			String var2;
			try
			{
				while((var2 = var1.readLine()) != null)
				{
					if(!var2.startsWith("#"))
					{
						BanEntry var3 = BanEntry.parse(var2);
						if(var3 != null)
						{
							theBanList.putLower(var3.getBannedUsername(), var3);
						}
					}
				}
			} catch(IOException var5)
			{
				MinecraftServer.getServer().getLogAgent().logSevereException("Could not load ban list", var5);
			}
		}
	}
	
	public void put(BanEntry par1BanEntry)
	{
		theBanList.putLower(par1BanEntry.getBannedUsername(), par1BanEntry);
		saveToFileWithHeader();
	}
	
	public void remove(String par1Str)
	{
		theBanList.remove(par1Str);
		saveToFileWithHeader();
	}
	
	public void removeExpiredBans()
	{
		Iterator var1 = theBanList.values().iterator();
		while(var1.hasNext())
		{
			BanEntry var2 = (BanEntry) var1.next();
			if(var2.hasBanExpired())
			{
				var1.remove();
			}
		}
	}
	
	public void saveToFile(boolean par1)
	{
		removeExpiredBans();
		try
		{
			PrintWriter var2 = new PrintWriter(new FileWriter(fileName, false));
			if(par1)
			{
				var2.println("# Updated " + new SimpleDateFormat().format(new Date()) + " by Minecraft " + "1.6.2");
				var2.println("# victim name | ban date | banned by | banned until | reason");
				var2.println();
			}
			Iterator var3 = theBanList.values().iterator();
			while(var3.hasNext())
			{
				BanEntry var4 = (BanEntry) var3.next();
				var2.println(var4.buildBanString());
			}
			var2.close();
		} catch(IOException var5)
		{
			MinecraftServer.getServer().getLogAgent().logSevereException("Could not save ban list", var5);
		}
	}
	
	public void saveToFileWithHeader()
	{
		saveToFile(true);
	}
	
	public void setListActive(boolean par1)
	{
		listActive = par1;
	}
}
