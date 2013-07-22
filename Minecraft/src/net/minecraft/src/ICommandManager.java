package net.minecraft.src;

import java.util.List;
import java.util.Map;

public interface ICommandManager
{
	int executeCommand(ICommandSender var1, String var2);
	
	Map getCommands();
	
	List getPossibleCommands(ICommandSender var1);
	
	List getPossibleCommands(ICommandSender var1, String var2);
}
