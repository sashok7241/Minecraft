package net.minecraft.src;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class CommandDebug extends CommandBase
{
	private long startTime = 0L;
	private int startTicks = 0;
	
	@Override public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_)
	{
		return p_71516_2_.length == 1 ? getListOfStringsMatchingLastWord(p_71516_2_, new String[] { "start", "stop" }) : null;
	}
	
	@Override public String getCommandName()
	{
		return "debug";
	}
	
	private void getProfileDump(int p_71546_1_, String p_71546_2_, StringBuilder p_71546_3_)
	{
		List var4 = MinecraftServer.getServer().theProfiler.getProfilingData(p_71546_2_);
		if(var4 != null && var4.size() >= 3)
		{
			for(int var5 = 1; var5 < var4.size(); ++var5)
			{
				ProfilerResult var6 = (ProfilerResult) var4.get(var5);
				p_71546_3_.append(String.format("[%02d] ", new Object[] { Integer.valueOf(p_71546_1_) }));
				for(int var7 = 0; var7 < p_71546_1_; ++var7)
				{
					p_71546_3_.append(" ");
				}
				p_71546_3_.append(var6.field_76331_c);
				p_71546_3_.append(" - ");
				p_71546_3_.append(String.format("%.2f", new Object[] { Double.valueOf(var6.field_76332_a) }));
				p_71546_3_.append("%/");
				p_71546_3_.append(String.format("%.2f", new Object[] { Double.valueOf(var6.field_76330_b) }));
				p_71546_3_.append("%\n");
				if(!var6.field_76331_c.equals("unspecified"))
				{
					try
					{
						getProfileDump(p_71546_1_ + 1, p_71546_2_ + "." + var6.field_76331_c, p_71546_3_);
					} catch(Exception var8)
					{
						p_71546_3_.append("[[ EXCEPTION " + var8 + " ]]");
					}
				}
			}
		}
	}
	
	private String getProfilerResults(long p_71547_1_, int p_71547_3_)
	{
		StringBuilder var4 = new StringBuilder();
		var4.append("---- Minecraft Profiler Results ----\n");
		var4.append("// ");
		var4.append(getWittyComment());
		var4.append("\n\n");
		var4.append("Time span: ").append(p_71547_1_).append(" ms\n");
		var4.append("Tick span: ").append(p_71547_3_).append(" ticks\n");
		var4.append("// This is approximately ").append(String.format("%.2f", new Object[] { Float.valueOf(p_71547_3_ / (p_71547_1_ / 1000.0F)) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
		var4.append("--- BEGIN PROFILE DUMP ---\n\n");
		getProfileDump(0, "root", var4);
		var4.append("--- END PROFILE DUMP ---\n\n");
		return var4.toString();
	}
	
	@Override public int getRequiredPermissionLevel()
	{
		return 3;
	}
	
	@Override public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_)
	{
		if(p_71515_2_.length == 1)
		{
			if(p_71515_2_[0].equals("start"))
			{
				notifyAdmins(p_71515_1_, "commands.debug.start", new Object[0]);
				MinecraftServer.getServer().enableProfiling();
				startTime = System.currentTimeMillis();
				startTicks = MinecraftServer.getServer().getTickCounter();
				return;
			}
			if(p_71515_2_[0].equals("stop"))
			{
				if(!MinecraftServer.getServer().theProfiler.profilingEnabled) throw new CommandException("commands.debug.notStarted", new Object[0]);
				long var3 = System.currentTimeMillis();
				int var5 = MinecraftServer.getServer().getTickCounter();
				long var6 = var3 - startTime;
				int var8 = var5 - startTicks;
				saveProfilerResults(var6, var8);
				MinecraftServer.getServer().theProfiler.profilingEnabled = false;
				notifyAdmins(p_71515_1_, "commands.debug.stop", new Object[] { Float.valueOf(var6 / 1000.0F), Integer.valueOf(var8) });
				return;
			}
		}
		throw new WrongUsageException("commands.debug.usage", new Object[0]);
	}
	
	private void saveProfilerResults(long p_71548_1_, int p_71548_3_)
	{
		File var4 = new File(MinecraftServer.getServer().getFile("debug"), "profile-results-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
		var4.getParentFile().mkdirs();
		try
		{
			FileWriter var5 = new FileWriter(var4);
			var5.write(getProfilerResults(p_71548_1_, p_71548_3_));
			var5.close();
		} catch(Throwable var6)
		{
			MinecraftServer.getServer().getLogAgent().logSevereException("Could not save profiler results to " + var4, var6);
		}
	}
	
	private static String getWittyComment()
	{
		String[] var0 = new String[] { "Shiny numbers!", "Am I not running fast enough? :(", "I\'m working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it\'ll have more motivation to work faster! Poor server." };
		try
		{
			return var0[(int) (System.nanoTime() % var0.length)];
		} catch(Throwable var2)
		{
			return "Witty comment unavailable :(";
		}
	}
}
