package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandShowSeed extends CommandBase
{
	@Override public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_)
	{
		return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(p_71519_1_);
	}
	
	@Override public String getCommandName()
	{
		return "seed";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		Object var3 = p_71515_1_ instanceof EntityPlayer ? ((EntityPlayer) p_71515_1_).worldObj : MinecraftServer.getServer().worldServerForDimension(0);
		p_71515_1_.sendChatToPlayer("Seed: " + ((World) var3).getSeed());
	}
}
