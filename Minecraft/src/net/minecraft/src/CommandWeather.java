package net.minecraft.src;

import java.util.List;
import java.util.Random;

import net.minecraft.server.MinecraftServer;

public class CommandWeather extends CommandBase
{
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "clear", "rain", "thunder" }) : null;
	}
	
	@Override public String getCommandName()
	{
		return "weather";
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 2;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length < 1) throw new WrongUsageException("commands.weather.usage", new Object[0]);
		else
		{
			int var3 = (300 + new Random().nextInt(600)) * 20;
			if(p_71515_2_.length >= 2)
			{
				var3 = parseIntBounded(p_71515_1_, p_71515_2_[1], 1, 1000000) * 20;
			}
			WorldServer var4 = MinecraftServer.getServer().worldServers[0];
			WorldInfo var5 = var4.getWorldInfo();
			var5.setRainTime(var3);
			var5.setThunderTime(var3);
			if("clear".equalsIgnoreCase(p_71515_2_[0]))
			{
				var5.setRaining(false);
				var5.setThundering(false);
				notifyAdmins(p_71515_1_, "commands.weather.clear", new Object[0]);
			} else if("rain".equalsIgnoreCase(p_71515_2_[0]))
			{
				var5.setRaining(true);
				var5.setThundering(false);
				notifyAdmins(p_71515_1_, "commands.weather.rain", new Object[0]);
			} else if("thunder".equalsIgnoreCase(p_71515_2_[0]))
			{
				var5.setRaining(true);
				var5.setThundering(true);
				notifyAdmins(p_71515_1_, "commands.weather.thunder", new Object[0]);
			}
		}
	}
}
