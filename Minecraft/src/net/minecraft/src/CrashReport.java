package net.minecraft.src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CrashReport
{
	private final String description;
	private final Throwable cause;
	private final CrashReportCategory field_85061_c = new CrashReportCategory(this, "System Details");
	private final List crashReportSections = new ArrayList();
	private File crashReportFile = null;
	private boolean field_85059_f = true;
	private StackTraceElement[] field_85060_g = new StackTraceElement[0];
	
	public CrashReport(String p_i3250_1_, Throwable p_i3250_2_)
	{
		description = p_i3250_1_;
		cause = p_i3250_2_;
		populateEnvironment();
	}
	
	public CrashReportCategory func_85056_g()
	{
		return field_85061_c;
	}
	
	public String func_90021_c()
	{
		StringBuilder var1 = new StringBuilder();
		getSectionsInStringBuilder(var1);
		return var1.toString();
	}
	
	public String getCauseStackTraceOrString()
	{
		StringWriter var1 = null;
		PrintWriter var2 = null;
		String var3 = cause.toString();
		try
		{
			var1 = new StringWriter();
			var2 = new PrintWriter(var1);
			cause.printStackTrace(var2);
			var3 = var1.toString();
		} finally
		{
			try
			{
				if(var1 != null)
				{
					var1.close();
				}
				if(var2 != null)
				{
					var2.close();
				}
			} catch(IOException var10)
			{
				;
			}
		}
		return var3;
	}
	
	public String getCompleteReport()
	{
		StringBuilder var1 = new StringBuilder();
		var1.append("---- Minecraft Crash Report ----\n");
		var1.append("// ");
		var1.append(getWittyComment());
		var1.append("\n\n");
		var1.append("Time: ");
		var1.append(new SimpleDateFormat().format(new Date()));
		var1.append("\n");
		var1.append("Description: ");
		var1.append(description);
		var1.append("\n\n");
		var1.append(getCauseStackTraceOrString());
		var1.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
		for(int var2 = 0; var2 < 87; ++var2)
		{
			var1.append("-");
		}
		var1.append("\n\n");
		getSectionsInStringBuilder(var1);
		return var1.toString();
	}
	
	public Throwable getCrashCause()
	{
		return cause;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public File getFile()
	{
		return crashReportFile;
	}
	
	public void getSectionsInStringBuilder(StringBuilder p_71506_1_)
	{
		if(field_85060_g != null && field_85060_g.length > 0)
		{
			p_71506_1_.append("-- Head --\n");
			p_71506_1_.append("Stacktrace:\n");
			StackTraceElement[] var2 = field_85060_g;
			int var3 = var2.length;
			for(int var4 = 0; var4 < var3; ++var4)
			{
				StackTraceElement var5 = var2[var4];
				p_71506_1_.append("\t").append("at ").append(var5.toString());
				p_71506_1_.append("\n");
			}
			p_71506_1_.append("\n");
		}
		Iterator var6 = crashReportSections.iterator();
		while(var6.hasNext())
		{
			CrashReportCategory var7 = (CrashReportCategory) var6.next();
			var7.func_85072_a(p_71506_1_);
			p_71506_1_.append("\n\n");
		}
		field_85061_c.func_85072_a(p_71506_1_);
	}
	
	public CrashReportCategory makeCategory(String p_85058_1_)
	{
		return makeCategoryDepth(p_85058_1_, 1);
	}
	
	public CrashReportCategory makeCategoryDepth(String p_85057_1_, int p_85057_2_)
	{
		CrashReportCategory var3 = new CrashReportCategory(this, p_85057_1_);
		if(field_85059_f)
		{
			int var4 = var3.func_85073_a(p_85057_2_);
			StackTraceElement[] var5 = cause.getStackTrace();
			StackTraceElement var6 = null;
			StackTraceElement var7 = null;
			if(var5 != null && var5.length - var4 < var5.length)
			{
				var6 = var5[var5.length - var4];
				if(var5.length + 1 - var4 < var5.length)
				{
					var7 = var5[var5.length + 1 - var4];
				}
			}
			field_85059_f = var3.func_85069_a(var6, var7);
			if(var4 > 0 && !crashReportSections.isEmpty())
			{
				CrashReportCategory var8 = (CrashReportCategory) crashReportSections.get(crashReportSections.size() - 1);
				var8.func_85070_b(var4);
			} else if(var5 != null && var5.length >= var4)
			{
				field_85060_g = new StackTraceElement[var5.length - var4];
				System.arraycopy(var5, 0, field_85060_g, 0, field_85060_g.length);
			} else
			{
				field_85059_f = false;
			}
		}
		crashReportSections.add(var3);
		return var3;
	}
	
	private void populateEnvironment()
	{
		field_85061_c.addCrashSectionCallable("Minecraft Version", new CallableMinecraftVersion(this));
		field_85061_c.addCrashSectionCallable("Operating System", new CallableOSInfo(this));
		field_85061_c.addCrashSectionCallable("Java Version", new CallableJavaInfo(this));
		field_85061_c.addCrashSectionCallable("Java VM Version", new CallableJavaInfo2(this));
		field_85061_c.addCrashSectionCallable("Memory", new CallableMemoryInfo(this));
		field_85061_c.addCrashSectionCallable("JVM Flags", new CallableJVMFlags(this));
		field_85061_c.addCrashSectionCallable("AABB Pool Size", new CallableCrashMemoryReport(this));
		field_85061_c.addCrashSectionCallable("Suspicious classes", new CallableSuspiciousClasses(this));
		field_85061_c.addCrashSectionCallable("IntCache", new CallableIntCache(this));
	}
	
	public boolean saveToFile(File p_71508_1_, ILogAgent p_71508_2_)
	{
		if(crashReportFile != null) return false;
		else
		{
			if(p_71508_1_.getParentFile() != null)
			{
				p_71508_1_.getParentFile().mkdirs();
			}
			try
			{
				FileWriter var3 = new FileWriter(p_71508_1_);
				var3.write(getCompleteReport());
				var3.close();
				crashReportFile = p_71508_1_;
				return true;
			} catch(Throwable var4)
			{
				p_71508_2_.logSevereException("Could not save crash report to " + p_71508_1_, var4);
				return false;
			}
		}
	}
	
	private static String getWittyComment()
	{
		String[] var0 = new String[] { "Who set us up the TNT?", "Everything\'s going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I\'m sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don\'t be sad. I\'ll do better next time, I promise!", "Don\'t be sad, have a hug! <3", "I just don\'t know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn\'t worry myself about that.", "I bet Cylons wouldn\'t have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I\'m Minecraft, and I\'m a crashaholic.", "Ooh. Shiny.", "This doesn\'t make any sense!", "Why is it breaking :(", "Don\'t do that.", "Ouch. That hurt :(", "You\'re mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!" };
		try
		{
			return var0[(int) (System.nanoTime() % var0.length)];
		} catch(Throwable var2)
		{
			return "Witty comment unavailable :(";
		}
	}
	
	public static CrashReport makeCrashReport(Throwable p_85055_0_, String p_85055_1_)
	{
		CrashReport var2;
		if(p_85055_0_ instanceof ReportedException)
		{
			var2 = ((ReportedException) p_85055_0_).getCrashReport();
		} else
		{
			var2 = new CrashReport(p_85055_1_, p_85055_0_);
		}
		return var2;
	}
}
