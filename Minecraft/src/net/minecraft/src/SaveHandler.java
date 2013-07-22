package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.server.MinecraftServer;

public class SaveHandler implements ISaveHandler, IPlayerFileData
{
	private final File worldDirectory;
	private final File playersDirectory;
	private final File mapDataDir;
	private final long initializationTime = System.currentTimeMillis();
	private final String saveDirectoryName;
	
	public SaveHandler(File par1File, String par2Str, boolean par3)
	{
		worldDirectory = new File(par1File, par2Str);
		worldDirectory.mkdirs();
		playersDirectory = new File(worldDirectory, "players");
		mapDataDir = new File(worldDirectory, "data");
		mapDataDir.mkdirs();
		saveDirectoryName = par2Str;
		if(par3)
		{
			playersDirectory.mkdirs();
		}
		setSessionLock();
	}
	
	@Override public void checkSessionLock() throws MinecraftException
	{
		try
		{
			File var1 = new File(worldDirectory, "session.lock");
			DataInputStream var2 = new DataInputStream(new FileInputStream(var1));
			try
			{
				if(var2.readLong() != initializationTime) throw new MinecraftException("The save is being accessed from another location, aborting");
			} finally
			{
				var2.close();
			}
		} catch(IOException var7)
		{
			throw new MinecraftException("Failed to check session lock, aborting");
		}
	}
	
	@Override public void flush()
	{
	}
	
	@Override public String[] getAvailablePlayerDat()
	{
		String[] var1 = playersDirectory.list();
		for(int var2 = 0; var2 < var1.length; ++var2)
		{
			if(var1[var2].endsWith(".dat"))
			{
				var1[var2] = var1[var2].substring(0, var1[var2].length() - 4);
			}
		}
		return var1;
	}
	
	@Override public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
	{
		throw new RuntimeException("Old Chunk Storage is no longer supported.");
	}
	
	@Override public File getMapFileFromName(String par1Str)
	{
		return new File(mapDataDir, par1Str + ".dat");
	}
	
	public NBTTagCompound getPlayerData(String par1Str)
	{
		try
		{
			File var2 = new File(playersDirectory, par1Str + ".dat");
			if(var2.exists()) return CompressedStreamTools.readCompressed(new FileInputStream(var2));
		} catch(Exception var3)
		{
			MinecraftServer.getServer().getLogAgent().logWarning("Failed to load player data for " + par1Str);
		}
		return null;
	}
	
	@Override public IPlayerFileData getSaveHandler()
	{
		return this;
	}
	
	protected File getWorldDirectory()
	{
		return worldDirectory;
	}
	
	@Override public String getWorldDirectoryName()
	{
		return saveDirectoryName;
	}
	
	@Override public WorldInfo loadWorldInfo()
	{
		File var1 = new File(worldDirectory, "level.dat");
		NBTTagCompound var2;
		NBTTagCompound var3;
		if(var1.exists())
		{
			try
			{
				var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
				var3 = var2.getCompoundTag("Data");
				return new WorldInfo(var3);
			} catch(Exception var5)
			{
				var5.printStackTrace();
			}
		}
		var1 = new File(worldDirectory, "level.dat_old");
		if(var1.exists())
		{
			try
			{
				var2 = CompressedStreamTools.readCompressed(new FileInputStream(var1));
				var3 = var2.getCompoundTag("Data");
				return new WorldInfo(var3);
			} catch(Exception var4)
			{
				var4.printStackTrace();
			}
		}
		return null;
	}
	
	@Override public NBTTagCompound readPlayerData(EntityPlayer par1EntityPlayer)
	{
		NBTTagCompound var2 = getPlayerData(par1EntityPlayer.username);
		if(var2 != null)
		{
			par1EntityPlayer.readFromNBT(var2);
		}
		return var2;
	}
	
	@Override public void saveWorldInfo(WorldInfo par1WorldInfo)
	{
		NBTTagCompound var2 = par1WorldInfo.getNBTTagCompound();
		NBTTagCompound var3 = new NBTTagCompound();
		var3.setTag("Data", var2);
		try
		{
			File var4 = new File(worldDirectory, "level.dat_new");
			File var5 = new File(worldDirectory, "level.dat_old");
			File var6 = new File(worldDirectory, "level.dat");
			CompressedStreamTools.writeCompressed(var3, new FileOutputStream(var4));
			if(var5.exists())
			{
				var5.delete();
			}
			var6.renameTo(var5);
			if(var6.exists())
			{
				var6.delete();
			}
			var4.renameTo(var6);
			if(var4.exists())
			{
				var4.delete();
			}
		} catch(Exception var7)
		{
			var7.printStackTrace();
		}
	}
	
	@Override public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound)
	{
		NBTTagCompound var3 = par1WorldInfo.cloneNBTCompound(par2NBTTagCompound);
		NBTTagCompound var4 = new NBTTagCompound();
		var4.setTag("Data", var3);
		try
		{
			File var5 = new File(worldDirectory, "level.dat_new");
			File var6 = new File(worldDirectory, "level.dat_old");
			File var7 = new File(worldDirectory, "level.dat");
			CompressedStreamTools.writeCompressed(var4, new FileOutputStream(var5));
			if(var6.exists())
			{
				var6.delete();
			}
			var7.renameTo(var6);
			if(var7.exists())
			{
				var7.delete();
			}
			var5.renameTo(var7);
			if(var5.exists())
			{
				var5.delete();
			}
		} catch(Exception var8)
		{
			var8.printStackTrace();
		}
	}
	
	private void setSessionLock()
	{
		try
		{
			File var1 = new File(worldDirectory, "session.lock");
			DataOutputStream var2 = new DataOutputStream(new FileOutputStream(var1));
			try
			{
				var2.writeLong(initializationTime);
			} finally
			{
				var2.close();
			}
		} catch(IOException var7)
		{
			var7.printStackTrace();
			throw new RuntimeException("Failed to check session lock, aborting");
		}
	}
	
	@Override public void writePlayerData(EntityPlayer par1EntityPlayer)
	{
		try
		{
			NBTTagCompound var2 = new NBTTagCompound();
			par1EntityPlayer.writeToNBT(var2);
			File var3 = new File(playersDirectory, par1EntityPlayer.username + ".dat.tmp");
			File var4 = new File(playersDirectory, par1EntityPlayer.username + ".dat");
			CompressedStreamTools.writeCompressed(var2, new FileOutputStream(var3));
			if(var4.exists())
			{
				var4.delete();
			}
			var3.renameTo(var4);
		} catch(Exception var5)
		{
			MinecraftServer.getServer().getLogAgent().logWarning("Failed to save player data for " + par1EntityPlayer.username);
		}
	}
}
