package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveFormatOld implements ISaveFormat
{
	protected final File savesDirectory;
	
	public SaveFormatOld(File p_i3913_1_)
	{
		if(!p_i3913_1_.exists())
		{
			p_i3913_1_.mkdirs();
		}
		savesDirectory = p_i3913_1_;
	}
	
	@Override public boolean canLoadWorld(String par1Str)
	{
		File var2 = new File(savesDirectory, par1Str);
		return var2.isDirectory();
	}
	
	@Override public boolean convertMapFormat(String p_75805_1_, IProgressUpdate p_75805_2_)
	{
		return false;
	}
	
	@Override public boolean deleteWorldDirectory(String p_75802_1_)
	{
		File var2 = new File(savesDirectory, p_75802_1_);
		if(!var2.exists()) return true;
		else
		{
			System.out.println("Deleting level " + p_75802_1_);
			for(int var3 = 1; var3 <= 5; ++var3)
			{
				System.out.println("Attempt " + var3 + "...");
				if(deleteFiles(var2.listFiles()))
				{
					break;
				}
				System.out.println("Unsuccessful in deleting contents.");
				if(var3 < 5)
				{
					try
					{
						Thread.sleep(500L);
					} catch(InterruptedException var5)
					{
						;
					}
				}
			}
			return var2.delete();
		}
	}
	
	@Override public void flushCache()
	{
	}
	
	@Override public List getSaveList() throws AnvilConverterException
	{
		ArrayList var1 = new ArrayList();
		for(int var2 = 0; var2 < 5; ++var2)
		{
			String var3 = "World" + (var2 + 1);
			WorldInfo var4 = getWorldInfo(var3);
			if(var4 != null)
			{
				var1.add(new SaveFormatComparator(var3, "", var4.getLastTimePlayed(), var4.getSizeOnDisk(), var4.getGameType(), false, var4.isHardcoreModeEnabled(), var4.areCommandsAllowed()));
			}
		}
		return var1;
	}
	
	@Override public ISaveHandler getSaveLoader(String p_75804_1_, boolean p_75804_2_)
	{
		return new SaveHandler(savesDirectory, p_75804_1_, p_75804_2_);
	}
	
	@Override public WorldInfo getWorldInfo(String p_75803_1_)
	{
		File var2 = new File(savesDirectory, p_75803_1_);
		if(!var2.exists()) return null;
		else
		{
			File var3 = new File(var2, "level.dat");
			NBTTagCompound var4;
			NBTTagCompound var5;
			if(var3.exists())
			{
				try
				{
					var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
					var5 = var4.getCompoundTag("Data");
					return new WorldInfo(var5);
				} catch(Exception var7)
				{
					var7.printStackTrace();
				}
			}
			var3 = new File(var2, "level.dat_old");
			if(var3.exists())
			{
				try
				{
					var4 = CompressedStreamTools.readCompressed(new FileInputStream(var3));
					var5 = var4.getCompoundTag("Data");
					return new WorldInfo(var5);
				} catch(Exception var6)
				{
					var6.printStackTrace();
				}
			}
			return null;
		}
	}
	
	@Override public boolean isOldMapFormat(String p_75801_1_)
	{
		return false;
	}
	
	@Override public void renameWorld(String par1Str, String par2Str)
	{
		File var3 = new File(savesDirectory, par1Str);
		if(var3.exists())
		{
			File var4 = new File(var3, "level.dat");
			if(var4.exists())
			{
				try
				{
					NBTTagCompound var5 = CompressedStreamTools.readCompressed(new FileInputStream(var4));
					NBTTagCompound var6 = var5.getCompoundTag("Data");
					var6.setString("LevelName", par2Str);
					CompressedStreamTools.writeCompressed(var5, new FileOutputStream(var4));
				} catch(Exception var7)
				{
					var7.printStackTrace();
				}
			}
		}
	}
	
	protected static boolean deleteFiles(File[] p_75807_0_)
	{
		for(File element : p_75807_0_)
		{
			File var2 = element;
			System.out.println("Deleting " + var2);
			if(var2.isDirectory() && !deleteFiles(var2.listFiles()))
			{
				System.out.println("Couldn\'t delete directory " + var2);
				return false;
			}
			if(!var2.delete())
			{
				System.out.println("Couldn\'t delete file " + var2);
				return false;
			}
		}
		return true;
	}
}
