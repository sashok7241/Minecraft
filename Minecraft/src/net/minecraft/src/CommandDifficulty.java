package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandDifficulty extends CommandBase
{
	private static final String[] difficulties = new String[] { "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard" };
	
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "peaceful", "easy", "normal", "hard" }) : null;
	}
	
	@Override public String getCommandName()
	{
		return "difficulty";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.difficulty.usage", new Object[0]);
	}
	
	protected int getDifficultyForName(ICommandSender p_82364_1_, String p_82364_2_)
	{
		return !p_82364_2_.equalsIgnoreCase("peaceful") && !p_82364_2_.equalsIgnoreCase("p") ? !p_82364_2_.equalsIgnoreCase("easy") && !p_82364_2_.equalsIgnoreCase("e") ? !p_82364_2_.equalsIgnoreCase("normal") && !p_82364_2_.equalsIgnoreCase("n") ? !p_82364_2_.equalsIgnoreCase("hard") && !p_82364_2_.equalsIgnoreCase("h") ? parseIntBounded(p_82364_1_, p_82364_2_, 0, 3) : 3 : 2 : 1 : 0;
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length > 0)
		{
			int var3 = getDifficultyForName(p_71515_1_, p_71515_2_[0]);
			MinecraftServer.getServer().setDifficultyForAllWorlds(var3);
			String var4 = StatCollector.translateToLocal(difficulties[var3]);
			notifyAdmins(p_71515_1_, "commands.difficulty.success", new Object[] { var4 });
		} else throw new WrongUsageException("commands.difficulty.usage", new Object[0]);
	}
}
