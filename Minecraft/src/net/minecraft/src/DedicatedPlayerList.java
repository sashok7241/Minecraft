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
	
	public DedicatedPlayerList(DedicatedServer p_i3378_1_)
	{
		super(p_i3378_1_);
		opsList = p_i3378_1_.getFile("ops.txt");
		whiteList = p_i3378_1_.getFile("white-list.txt");
		viewDistance = p_i3378_1_.getIntProperty("view-distance", 10);
		maxPlayers = p_i3378_1_.getIntProperty("max-players", 20);
		setWhiteListEnabled(p_i3378_1_.getBooleanProperty("white-list", false));
		if(!p_i3378_1_.isSinglePlayer())
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
	
	@Override public void addOp(String p_72386_1_)
	{
		super.addOp(p_72386_1_);
		saveOpsList();
	}
	
	@Override public void addToWhiteList(String p_72359_1_)
	{
		super.addToWhiteList(p_72359_1_);
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
	
	@Override public boolean isAllowedToLogin(String p_72370_1_)
	{
		p_72370_1_ = p_72370_1_.trim().toLowerCase();
		return !isWhiteListEnabled() || areCommandsAllowed(p_72370_1_) || getWhiteListedPlayers().contains(p_72370_1_);
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
	
	@Override public void removeFromWhitelist(String p_72379_1_)
	{
		super.removeFromWhitelist(p_72379_1_);
		saveWhiteList();
	}
	
	@Override public void removeOp(String p_72360_1_)
	{
		super.removeOp(p_72360_1_);
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
	
	@Override public void setWhiteListEnabled(boolean p_72371_1_)
	{
		super.setWhiteListEnabled(p_72371_1_);
		getDedicatedServerInstance().setProperty("white-list", Boolean.valueOf(p_72371_1_));
		getDedicatedServerInstance().saveProperties();
	}
}
