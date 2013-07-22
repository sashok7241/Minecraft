package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;

import net.minecraft.server.MinecraftServer;

public class DedicatedPlayerList extends ServerConfigurationManager
{
	private File opsList;
	private File whiteList;
	
	public DedicatedPlayerList(DedicatedServer par1DedicatedServer)
	{
		super(par1DedicatedServer);
		opsList = par1DedicatedServer.getFile("ops.txt");
		whiteList = par1DedicatedServer.getFile("white-list.txt");
		viewDistance = par1DedicatedServer.getIntProperty("view-distance", 10);
		maxPlayers = par1DedicatedServer.getIntProperty("max-players", 20);
		setWhiteListEnabled(par1DedicatedServer.getBooleanProperty("white-list", false));
		if(!par1DedicatedServer.isSinglePlayer())
		{
			getBannedPlayers().setListActive(true);
			getBannedIPs().setListActive(true);
		}
		getBannedPlayers().loadBanList();
		getBannedPlayers().saveToFileWithHeader();
		getBannedIPs().loadBanList();
		getBannedIPs().saveToFileWithHeader();
		loadOpsList();
		readWhiteList();
		saveOpsList();
		if(!whiteList.exists())
		{
			saveWhiteList();
		}
	}
	
	@Override public void addOp(String par1Str)
	{
		super.addOp(par1Str);
		saveOpsList();
	}
	
	@Override public void addToWhiteList(String par1Str)
	{
		super.addToWhiteList(par1Str);
		saveWhiteList();
	}
	
	public DedicatedServer getDedicatedServerInstance()
	{
		return (DedicatedServer) super.getServerInstance();
	}
	
	@Override public MinecraftServer getServerInstance()
	{
		return getDedicatedServerInstance();
	}
	
	@Override public boolean isAllowedToLogin(String par1Str)
	{
		par1Str = par1Str.trim().toLowerCase();
		return !isWhiteListEnabled() || areCommandsAllowed(par1Str) || getWhiteListedPlayers().contains(par1Str);
	}
	
	private void loadOpsList()
	{
		try
		{
			getOps().clear();
			BufferedReader var1 = new BufferedReader(new FileReader(opsList));
			String var2 = "";
			while((var2 = var1.readLine()) != null)
			{
				getOps().add(var2.trim().toLowerCase());
			}
			var1.close();
		} catch(Exception var3)
		{
			getDedicatedServerInstance().getLogAgent().logWarning("Failed to load operators list: " + var3);
		}
	}
	
	@Override public void loadWhiteList()
	{
		readWhiteList();
	}
	
	private void readWhiteList()
	{
		try
		{
			getWhiteListedPlayers().clear();
			BufferedReader var1 = new BufferedReader(new FileReader(whiteList));
			String var2 = "";
			while((var2 = var1.readLine()) != null)
			{
				getWhiteListedPlayers().add(var2.trim().toLowerCase());
			}
			var1.close();
		} catch(Exception var3)
		{
			getDedicatedServerInstance().getLogAgent().logWarning("Failed to load white-list: " + var3);
		}
	}
	
	@Override public void removeFromWhitelist(String par1Str)
	{
		super.removeFromWhitelist(par1Str);
		saveWhiteList();
	}
	
	@Override public void removeOp(String par1Str)
	{
		super.removeOp(par1Str);
		saveOpsList();
	}
	
	private void saveOpsList()
	{
		try
		{
			PrintWriter var1 = new PrintWriter(new FileWriter(opsList, false));
			Iterator var2 = getOps().iterator();
			while(var2.hasNext())
			{
				String var3 = (String) var2.next();
				var1.println(var3);
			}
			var1.close();
		} catch(Exception var4)
		{
			getDedicatedServerInstance().getLogAgent().logWarning("Failed to save operators list: " + var4);
		}
	}
	
	private void saveWhiteList()
	{
		try
		{
			PrintWriter var1 = new PrintWriter(new FileWriter(whiteList, false));
			Iterator var2 = getWhiteListedPlayers().iterator();
			while(var2.hasNext())
			{
				String var3 = (String) var2.next();
				var1.println(var3);
			}
			var1.close();
		} catch(Exception var4)
		{
			getDedicatedServerInstance().getLogAgent().logWarning("Failed to save white-list: " + var4);
		}
	}
	
	@Override public void setWhiteListEnabled(boolean par1)
	{
		super.setWhiteListEnabled(par1);
		getDedicatedServerInstance().setProperty("white-list", Boolean.valueOf(par1));
		getDedicatedServerInstance().saveProperties();
	}
}
