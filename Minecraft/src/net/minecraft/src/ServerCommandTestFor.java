package net.minecraft.src;

public class ServerCommandTestFor extends CommandBase
{
	@Override public String getCommandName()
	{
		return "testfor";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_)
	{
		return p_82358_2_ == 0;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length != 1) throw new WrongUsageException("commands.testfor.usage", new Object[0]);
		else if(!(p_71515_1_ instanceof TileEntityCommandBlock)) throw new CommandException("commands.testfor.failed", new Object[0]);
		else
		{
			func_82359_c(p_71515_1_, p_71515_2_[0]);
		}
	}
}
