package net.minecraft.src;

public class RConConsoleSource implements ICommandSender
{
	public static final RConConsoleSource consoleBuffer = new RConConsoleSource();
	private StringBuffer buffer = new StringBuffer();
	
	@Override public boolean canCommandSenderUseCommand(int par1, String par2Str)
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
	
	@Override public void sendChatToPlayer(String par1Str)
	{
		buffer.append(par1Str);
	}
	
	@Override public String translateString(String par1Str, Object ... par2ArrayOfObj)
	{
		return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
	}
}
