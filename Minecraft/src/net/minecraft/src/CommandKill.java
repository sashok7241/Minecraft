package net.minecraft.src;

public class CommandKill extends CommandBase
{
	@Override public String getCommandName()
	{
		return "kill";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 0;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		EntityPlayerMP var3 = getCommandSenderAsPlayer(p_71515_1_);
		var3.attackEntityFrom(DamageSource.outOfWorld, 1000);
		p_71515_1_.sendChatToPlayer("Ouch. That looks like it hurt.");
	}
}
