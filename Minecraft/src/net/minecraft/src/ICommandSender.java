package net.minecraft.src;

public interface ICommandSender
{
	boolean canCommandSenderUseCommand(int var1, String var2);
	
	String getCommandSenderName();
	
	ChunkCoordinates getPlayerCoordinates();
	
	void sendChatToPlayer(String var1);
	
	String translateString(String var1, Object ... var2);
}
