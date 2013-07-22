package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandServerSaveAll extends CommandBase
{
	@Override public String getCommandName()
	{
		return "save-all";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 4;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		MinecraftServer var3 = MinecraftServer.getServer();
		p_71515_1_.sendChatToPlayer(p_71515_1_.translateString("commands.save.start", new Object[0]));
		if(var3.getConfigurationManager() != null)
		{
			var3.getConfigurationManager().saveAllPlayerData();
		}
		try
		{
			int var4;
			WorldServer var5;
			boolean var6;
			for(var4 = 0; var4 < var3.worldServers.length; ++var4)
			{
				if(var3.worldServers[var4] != null)
				{
					var5 = var3.worldServers[var4];
					var6 = var5.canNotSave;
					var5.canNotSave = false;
					var5.saveAllChunks(true, (IProgressUpdate) null);
					var5.canNotSave = var6;
				}
			}
			if(p_71515_2_.length > 0 && "flush".equals(p_71515_2_[0]))
			{
				p_71515_1_.sendChatToPlayer(p_71515_1_.translateString("commands.save.flushStart", new Object[0]));
				for(var4 = 0; var4 < var3.worldServers.length; ++var4)
				{
					if(var3.worldServers[var4] != null)
					{
						var5 = var3.worldServers[var4];
						var6 = var5.canNotSave;
						var5.canNotSave = false;
						var5.func_104140_m();
						var5.canNotSave = var6;
					}
				}
				p_71515_1_.sendChatToPlayer(p_71515_1_.translateString("commands.save.flushEnd", new Object[0]));
			}
		} catch(MinecraftException var7)
		{
			notifyAdmins(p_71515_1_, "commands.save.failed", new Object[] { var7.getMessage() });
			return;
		}
		notifyAdmins(p_71515_1_, "commands.save.success", new Object[0]);
	}
}
