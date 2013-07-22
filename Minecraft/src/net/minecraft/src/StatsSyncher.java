package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class StatsSyncher
{
	private volatile boolean isBusy;
	private volatile Map field_77430_b;
	private volatile Map field_77431_c;
	private StatFileWriter statFileWriter;
	private File unsentDataFile;
	private File dataFile;
	private File unsentTempFile;
	private File tempFile;
	private File unsentOldFile;
	private File oldFile;
	private Session theSession;
	private int field_77433_l;
	private int field_77434_m;
	
	public StatsSyncher(Session par1Session, StatFileWriter par2StatFileWriter, File par3File)
	{
		String var4 = par1Session.func_111285_a();
		String var5 = var4.toLowerCase();
		unsentDataFile = new File(par3File, "stats_" + var5 + "_unsent.dat");
		dataFile = new File(par3File, "stats_" + var5 + ".dat");
		unsentOldFile = new File(par3File, "stats_" + var5 + "_unsent.old");
		oldFile = new File(par3File, "stats_" + var5 + ".old");
		unsentTempFile = new File(par3File, "stats_" + var5 + "_unsent.tmp");
		tempFile = new File(par3File, "stats_" + var5 + ".tmp");
		if(!var5.equals(var4))
		{
			func_77412_a(par3File, "stats_" + var4 + "_unsent.dat", unsentDataFile);
			func_77412_a(par3File, "stats_" + var4 + ".dat", dataFile);
			func_77412_a(par3File, "stats_" + var4 + "_unsent.old", unsentOldFile);
			func_77412_a(par3File, "stats_" + var4 + ".old", oldFile);
			func_77412_a(par3File, "stats_" + var4 + "_unsent.tmp", unsentTempFile);
			func_77412_a(par3File, "stats_" + var4 + ".tmp", tempFile);
		}
		statFileWriter = par2StatFileWriter;
		theSession = par1Session;
		if(unsentDataFile.exists())
		{
			par2StatFileWriter.writeStats(func_77417_a(unsentDataFile, unsentTempFile, unsentOldFile));
		}
		beginReceiveStats();
	}
	
	public void beginReceiveStats()
	{
		if(isBusy) throw new IllegalStateException("Can\'t get stats from server while StatsSyncher is busy!");
		else
		{
			field_77433_l = 100;
			isBusy = true;
			new ThreadStatSyncherReceive(this).start();
		}
	}
	
	public void beginSendStats(Map par1Map)
	{
		if(isBusy) throw new IllegalStateException("Can\'t save stats while StatsSyncher is busy!");
		else
		{
			field_77433_l = 100;
			isBusy = true;
			new ThreadStatSyncherSend(this, par1Map).start();
		}
	}
	
	private void func_77412_a(File par1File, String par2Str, File par3File)
	{
		File var4 = new File(par1File, par2Str);
		if(var4.exists() && !var4.isDirectory() && !par3File.exists())
		{
			var4.renameTo(par3File);
		}
	}
	
	private Map func_77413_a(File par1File)
	{
		BufferedReader var2 = null;
		try
		{
			var2 = new BufferedReader(new FileReader(par1File));
			String var3 = "";
			StringBuilder var4 = new StringBuilder();
			while((var3 = var2.readLine()) != null)
			{
				var4.append(var3);
			}
			Map var5 = StatFileWriter.func_77453_b(var4.toString());
			return var5;
		} catch(Exception var15)
		{
			var15.printStackTrace();
		} finally
		{
			if(var2 != null)
			{
				try
				{
					var2.close();
				} catch(Exception var14)
				{
					var14.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private Map func_77417_a(File par1File, File par2File, File par3File)
	{
		return par1File.exists() ? func_77413_a(par1File) : par3File.exists() ? func_77413_a(par3File) : par2File.exists() ? func_77413_a(par2File) : null;
	}
	
	private void func_77421_a(Map par1Map, File par2File, File par3File, File par4File) throws IOException
	{
		PrintWriter var5 = new PrintWriter(new FileWriter(par3File, false));
		try
		{
			var5.print(StatFileWriter.func_77441_a(theSession.func_111285_a(), "local", par1Map));
		} finally
		{
			var5.close();
		}
		if(par4File.exists())
		{
			par4File.delete();
		}
		if(par2File.exists())
		{
			par2File.renameTo(par4File);
		}
		par3File.renameTo(par2File);
	}
	
	public void func_77422_e()
	{
		if(field_77433_l > 0)
		{
			--field_77433_l;
		}
		if(field_77434_m > 0)
		{
			--field_77434_m;
		}
		if(field_77431_c != null)
		{
			statFileWriter.func_77448_c(field_77431_c);
			field_77431_c = null;
		}
		if(field_77430_b != null)
		{
			statFileWriter.func_77452_b(field_77430_b);
			field_77430_b = null;
		}
	}
	
	public boolean func_77425_c()
	{
		return field_77433_l <= 0 && !isBusy && field_77431_c == null;
	}
	
	public void syncStatsFileWithMap(Map par1Map)
	{
		int var2 = 30;
		while(isBusy)
		{
			--var2;
			if(var2 <= 0)
			{
				break;
			}
			try
			{
				Thread.sleep(100L);
			} catch(InterruptedException var10)
			{
				var10.printStackTrace();
			}
		}
		isBusy = true;
		try
		{
			func_77421_a(par1Map, unsentDataFile, unsentTempFile, unsentOldFile);
		} catch(Exception var8)
		{
			var8.printStackTrace();
		} finally
		{
			isBusy = false;
		}
	}
	
	static File func_77407_c(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.tempFile;
	}
	
	static File func_77408_b(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.dataFile;
	}
	
	static Map func_77410_a(StatsSyncher par0StatsSyncher, File par1File, File par2File, File par3File)
	{
		return par0StatsSyncher.func_77417_a(par1File, par2File, par3File);
	}
	
	static File func_77411_d(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.oldFile;
	}
	
	static void func_77414_a(StatsSyncher par0StatsSyncher, Map par1Map, File par2File, File par3File, File par4File) throws IOException
	{
		par0StatsSyncher.func_77421_a(par1Map, par2File, par3File, par4File);
	}
	
	static Map func_77416_a(StatsSyncher par0StatsSyncher, Map par1Map)
	{
		return par0StatsSyncher.field_77430_b = par1Map;
	}
	
	static Map func_77419_a(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.field_77430_b;
	}
	
	static File getUnsentDataFile(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.unsentDataFile;
	}
	
	static File getUnsentOldFile(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.unsentOldFile;
	}
	
	static File getUnsentTempFile(StatsSyncher par0StatsSyncher)
	{
		return par0StatsSyncher.unsentTempFile;
	}
	
	static boolean setBusy(StatsSyncher par0StatsSyncher, boolean par1)
	{
		return par0StatsSyncher.isBusy = par1;
	}
}
