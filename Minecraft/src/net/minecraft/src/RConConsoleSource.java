package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class RConConsoleSource implements ICommandSender
{
	public static final RConConsoleSource consoleBuffer = new RConConsoleSource();
	private StringBuffer buffer = new StringBuffer();
	
	@Override public boolean canCommandSenderUseCommand(int par1, String par2Str)
	{
		return true;
	}
	
	@Override public World func_130014_f_()
	{
		return MinecraftServer.getServer().func_130014_f_();
	}
	
	public String getChatBuffer()
	{
		return buffer.toString();
	}
	
	@Override public String getCommandSenderName()
	{
		return "Rcon";
	}
	
	@Override public ChunkCoordinates getPlayerCoordinates()
	{
		return new ChunkCoordinates(0, 0, 0);
	}
	
	public void resetLog()
	{
		buffer.setLength(0);
	}
	
	@Override public void sendChatToPlayer(ChatMessageComponent par1ChatMessageComponent)
	{
		buffer.append(par1ChatMessageComponent.toString());
	}
}
