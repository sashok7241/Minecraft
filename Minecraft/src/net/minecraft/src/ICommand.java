package net.minecraft.src;

import java.util.List;

public interface ICommand extends Comparable
{
	List addTabCompletionOptions(ICommandSender var1, String[] var2);
	
	boolean canCommandSenderUseCommand(ICommandSender var1);
	
	List getCommandAliases();
	
	String getCommandName();
	
	String getCommandUsage(ICommandSender var1);
	
	boolean isUsernameIndex(String[] var1, int var2);
	
	void processCommand(ICommandSender var1, String[] var2);
}
