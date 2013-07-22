package net.minecraft.src;

public class RConConsoleSource implements ICommandSender
{
	public static final RConConsoleSource consoleBuffer = new RConConsoleSource();
	private StringBuffer buffer = new StringBuffer();
	
	@Override public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_)
	{
		return true;
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
	
	@Override public void sendChatToPlayer(String p_70006_1_)
	{
		buffer.append(p_70006_1_);
	}
	
	@Override public String translateString(String p_70004_1_, Object ... p_70004_2_)
	{
		return StringTranslate.getInstance().translateKeyFormat(p_70004_1_, p_70004_2_);
	}
}
