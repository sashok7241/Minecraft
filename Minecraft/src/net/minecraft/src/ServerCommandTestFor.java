package net.minecraft.src;

public class ServerCommandTestFor extends CommandBase
{
	@Override public String getCommandName()
	{
		return "testfor";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.testfor.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
	{
		return par2 == 0;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		if(par2ArrayOfStr.length != 1) throw new WrongUsageException("commands.testfor.usage", new Object[0]);
		else if(!(par1ICommandSender instanceof TileEntityCommandBlock)) throw new CommandException("commands.testfor.failed", new Object[0]);
		else
		{
			func_82359_c(par1ICommandSender, par2ArrayOfStr[0]);
		}
	}
}
