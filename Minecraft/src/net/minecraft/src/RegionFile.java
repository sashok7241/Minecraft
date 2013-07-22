package net.minecraft.src;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import net.minecraft.server.MinecraftServer;

public class RegionFile
{
	private static final byte[] emptySector = new byte[4096];
	private final File fileName;
	private RandomAccessFile dataFile;
	private final int[] offsets = new int[1024];
	private final int[] chunkTimestamps = new int[1024];
	private ArrayList sectorFree;
	private int sizeDelta;
	private long lastModified;
	
	public RegionFile(File par1File)
	{
		fileName = par1File;
		sizeDelta = 0;
		try
		{
			if(par1File.exists())
			{
				lastModified = par1File.lastModified();
			}
			dataFile = new RandomAccessFile(par1File, "rw");
			int var2;
			if(dataFile.length() < 4096L)
			{
				for(var2 = 0; var2 < 1024; ++var2)
				{
					dataFile.writeInt(0);
				}
				for(var2 = 0; var2 < 1024; ++var2)
				{
					dataFile.writeInt(0);
				}
				sizeDelta += 8192;
			}
			if((dataFile.length() & 4095L) != 0L)
			{
				for(var2 = 0; var2 < (dataFile.length() & 4095L); ++var2)
				{
					dataFile.write(0);
				}
			}
			var2 = (int) dataFile.length() / 4096;
			sectorFree = new ArrayList(var2);
			int var3;
			for(var3 = 0; var3 < var2; ++var3)
			{
				sectorFree.add(Boolean.valueOf(true));
			}
			sectorFree.set(0, Boolean.valueOf(false));
			sectorFree.set(1, Boolean.valueOf(false));
			dataFile.seek(0L);
			int var4;
			for(var3 = 0; var3 < 1024; ++var3)
			{
				var4 = dataFile.readInt();
				offsets[var3] = var4;
				if(var4 != 0 && (var4 >> 8) + (var4 & 255) <= sectorFree.size())
				{
					for(int var5 = 0; var5 < (var4 & 255); ++var5)
					{
						sectorFree.set((var4 >> 8) + var5, Boolean.valueOf(false));
					}
				}
			}
			for(var3 = 0; var3 < 1024; ++var3)
			{
				var4 = dataFile.readInt();
				chunkTimestamps[var3] = var4;
			}
		} catch(IOException var6)
		{
			var6.printStackTrace();
		}
	}
	
	public void close() throws IOException
	{
		if(dataFile != null)
		{
			dataFile.close();
		}
	}
	
	public synchronized DataInputStream getChunkDataInputStream(int par1, int par2)
	{
		if(outOfBounds(par1, par2)) return null;
		else
		{
			try
			{
				int var3 = getOffset(par1, par2);
				if(var3 == 0) return null;
				else
				{
					int var4 = var3 >> 8;
					int var5 = var3 & 255;
					if(var4 + var5 > sectorFree.size()) return null;
					else
					{
						dataFile.seek(var4 * 4096);
						int var6 = dataFile.readInt();
						if(var6 > 4096 * var5) return null;
						else if(var6 <= 0) return null;
						else
						{
							byte var7 = dataFile.readByte();
							byte[] var8;
							if(var7 == 1)
							{
								var8 = new byte[var6 - 1];
								dataFile.read(var8);
								return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var8))));
							} else if(var7 == 2)
							{
								var8 = new byte[var6 - 1];
								dataFile.read(var8);
								return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var8))));
							} else return null;
						}
					}
				}
			} catch(IOException var9)
			{
				return null;
			}
		}
	}
	
	public DataOutputStream getChunkDataOutputStream(int par1, int par2)
	{
		return outOfBounds(par1, par2) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, par1, par2)));
	}
	
	private int getOffset(int par1, int par2)
	{
		return offsets[par1 + par2 * 32];
	}
	
	public boolean isChunkSaved(int par1, int par2)
	{
		return getOffset(par1, par2) != 0;
	}
	
	private boolean outOfBounds(int par1, int par2)
	{
		return par1 < 0 || par1 >= 32 || par2 < 0 || par2 >= 32;
	}
	
	private void setChunkTimestamp(int par1, int par2, int par3) throws IOException
	{
		chunkTimestamps[par1 + par2 * 32] = par3;
		dataFile.seek(4096 + (par1 + par2 * 32) * 4);
		dataFile.writeInt(par3);
	}
	
	private void setOffset(int par1, int par2, int par3) throws IOException
	{
		offsets[par1 + par2 * 32] = par3;
		dataFile.seek((par1 + par2 * 32) * 4);
		dataFile.writeInt(par3);
	}
	
	private void write(int par1, byte[] par2ArrayOfByte, int par3) throws IOException
	{
		dataFile.seek(par1 * 4096);
		dataFile.writeInt(par3 + 1);
		dataFile.writeByte(2);
		dataFile.write(par2ArrayOfByte, 0, par3);
	}
	
	protected synchronized void write(int par1, int par2, byte[] par3ArrayOfByte, int par4)
	{
		try
		{
			int var5 = getOffset(par1, par2);
			int var6 = var5 >> 8;
			int var7 = var5 & 255;
			int var8 = (par4 + 5) / 4096 + 1;
			if(var8 >= 256) return;
			if(var6 != 0 && var7 == var8)
			{
				this.write(var6, par3ArrayOfByte, par4);
			} else
			{
				int var9;
				for(var9 = 0; var9 < var7; ++var9)
				{
					sectorFree.set(var6 + var9, Boolean.valueOf(true));
				}
				var9 = sectorFree.indexOf(Boolean.valueOf(true));
				int var10 = 0;
				int var11;
				if(var9 != -1)
				{
					for(var11 = var9; var11 < sectorFree.size(); ++var11)
					{
						if(var10 != 0)
						{
							if(((Boolean) sectorFree.get(var11)).booleanValue())
							{
								++var10;
							} else
							{
								var10 = 0;
							}
						} else if(((Boolean) sectorFree.get(var11)).booleanValue())
						{
							var9 = var11;
							var10 = 1;
						}
						if(var10 >= var8)
						{
							break;
						}
					}
				}
				if(var10 >= var8)
				{
					var6 = var9;
					setOffset(par1, par2, var9 << 8 | var8);
					for(var11 = 0; var11 < var8; ++var11)
					{
						sectorFree.set(var6 + var11, Boolean.valueOf(false));
					}
					this.write(var6, par3ArrayOfByte, par4);
				} else
				{
					dataFile.seek(dataFile.length());
					var6 = sectorFree.size();
					for(var11 = 0; var11 < var8; ++var11)
					{
						dataFile.write(emptySector);
						sectorFree.add(Boolean.valueOf(false));
					}
					sizeDelta += 4096 * var8;
					this.write(var6, par3ArrayOfByte, par4);
					setOffset(par1, par2, var6 << 8 | var8);
				}
			}
			setChunkTimestamp(par1, par2, (int) (MinecraftServer.func_130071_aq() / 1000L));
		} catch(IOException var12)
		{
			var12.printStackTrace();
		}
	}
}
