package net.minecraft.src;

import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandSetSpawnpoint extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length != 1 && p_71516_2_.length != 2 ? null : getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
	}
	
	@Override public String getCommandName()
	{
		return "spawnpoint";
	}
	
	@Override public String getCommandUsage(ICommandSender p_71518_1_)
	{
		return p_71518_1_.translateString("commands.spawnpoint.usage", new Object[0]);
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
		EntityPlayerMP var3 = p_71515_2_.length == 0 ? getCommandSenderAsPlayer(p_71515_1_) : func_82359_c(p_71515_1_, p_71515_2_[0]);
		if(p_71515_2_.length == 4)
		{
			if(var3.worldObj != null)
			{
				byte var4 = 1;
				int var5 = 30000000;
				int var10 = var4 + 1;
				int var6 = parseIntBounded(p_71515_1_, p_71515_2_[var4], -var5, var5);
				int var7 = parseIntBounded(p_71515_1_, p_71515_2_[var10++], 0, 256);
				int var8 = parseIntBounded(p_71515_1_, p_71515_2_[var10++], -var5, var5);
				var3.setSpawnChunk(new ChunkCoordinates(var6, var7, var8), true);
				notifyAdmins(p_71515_1_, "commands.spawnpoint.success", new Object[] { var3.getEntityName(), Integer.valueOf(var6), Integer.valueOf(var7), Integer.valueOf(var8) });
			}
		} else
		{
			if(p_71515_2_.length > 1) throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
			ChunkCoordinates var11 = var3.getPlayerCoordinates();
			var3.setSpawnChunk(var11, true);
			notifyAdmins(p_71515_1_, "commands.spawnpoint.success", new Object[] { var3.getEntityName(), Integer.valueOf(var11.posX), Integer.valueOf(var11.posY), Integer.valueOf(var11.posZ) });
		}
	}
}
