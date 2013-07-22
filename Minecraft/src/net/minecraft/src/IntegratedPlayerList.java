package net.minecraft.src;

import java.net.SocketAddress;

import net.minecraft.server.MinecraftServer;

public class IntegratedPlayerList extends ServerConfigurationManager
{
	private NBTTagCompound hostPlayerData = null;
	
	public IntegratedPlayerList(IntegratedServer par1IntegratedServer)
	{
		super(par1IntegratedServer);
		viewDistance = 10;
	}
	
	@Override public String allowUserToConnect(SocketAddress par1SocketAddress, String par2Str)
	{
		return par2Str.equalsIgnoreCase(getIntegratedServer().getServerOwner()) ? "That name is already taken." : super.allowUserToConnect(par1SocketAddress, par2Str);
	}
	
	@Override public NBTTagCompound getHostPlayerData()
	{
		return hostPlayerData;
	}
	
	public IntegratedServer getIntegratedServer()
	{
		return (IntegratedServer) super.getServerInstance();
	}
	
	@Override public MinecraftServer getServerInstance()
	{
		return getIntegratedServer();
	}
	
	@Override protected void writePlayerData(EntityPlayerMP par1EntityPlayerMP)
	{
		if(par1EntityPlayerMP.getCommandSenderName().equals(getIntegratedServer().getServerOwner()))
		{
			hostPlayerData = new NBTTagCompound();
			par1EntityPlayerMP.writeToNBT(hostPlayerData);
		}
		super.writePlayerData(par1EntityPlayerMP);
	}
}
