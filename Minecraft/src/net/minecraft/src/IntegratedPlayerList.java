package net.minecraft.src;

import java.net.SocketAddress;

import net.minecraft.server.MinecraftServer;

public class IntegratedPlayerList extends ServerConfigurationManager
{
	private NBTTagCompound hostPlayerData = null;
	
	public IntegratedPlayerList(IntegratedServer p_i3125_1_)
	{
		super(p_i3125_1_);
		viewDistance = 10;
	}
	
	@Override public String allowUserToConnect(SocketAddress p_72399_1_, String p_72399_2_)
	{
		return p_72399_2_.equalsIgnoreCase(getIntegratedServer().getServerOwner()) ? "That name is already taken." : super.allowUserToConnect(p_72399_1_, p_72399_2_);
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
	
	@Override protected void writePlayerData(EntityPlayerMP p_72391_1_)
	{
		if(p_72391_1_.getCommandSenderName().equals(getIntegratedServer().getServerOwner()))
		{
			hostPlayerData = new NBTTagCompound();
			p_72391_1_.writeToNBT(hostPlayerData);
		}
		super.writePlayerData(p_72391_1_);
	}
}
