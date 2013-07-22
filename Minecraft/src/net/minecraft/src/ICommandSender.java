package net.minecraft.src;

public interface ICommandSender
{
	boolean canCommandSenderUseCommand(int var1, String var2);
	
	World func_130014_f_();
	
	String getCommandSenderName();
	
	ChunkCoordinates getPlayerCoordinates();
	
	void sendChatToPlayer(ChatMessageComponent var1);
}
