package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandShowSeed extends CommandBase
{
	@Override public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
	{
		return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(par1ICommandSender);
	}
	
	@Override public String getCommandName()
	{
		return "seed";
	}
	
	@Override public String getCommandUsage(ICommandSender par1ICommandSender)
	{
		return "commands.seed.usage";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
	{
		Object var3 = par1ICommandSender instanceof EntityPlayer ? ((EntityPlayer) par1ICommandSender).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
		par1ICommandSender.sendChatToPlayer(ChatMessageComponent.func_111082_b("commands.seed.success", new Object[] { Long.valueOf(((World) var3).getSeed()) }));
	}
}
