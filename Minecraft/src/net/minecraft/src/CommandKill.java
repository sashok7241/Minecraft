package net.minecraft.src;

public class CommandKill extends CommandBase
{
	@Override public String getCommandName()
	{
		return "kill";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.kill.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		EntityPlayerMP var3 = getCommandSenderAsPlayer(par1ICommandSender);
		var3.attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
		par1ICommandSender.sendChatToPlayer(ChatMessageComponent.func_111077_e("commands.kill.success"));
	}
}
