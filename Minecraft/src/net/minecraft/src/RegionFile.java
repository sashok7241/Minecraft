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

public class RegionFile
{
	private static final byte[] emptySector = new byte[4096];
	private final File fileName;
	private RandomAccessFile dataFile;
	private final int[] offsets = new int[1024];
	private final int[] chunkTimestamps = new int[1024];
	private ArrayList sectorFree;
	private int sizeDelta;
	private long lastModified = 0L;
	
	public RegionFile(File p_i3777_1_)
	{
		fileName = p_i3777_1_;
		sizeDelta = 0;
		try
		{
			if(p_i3777_1_.exists())
			{
				lastModified = p_i3777_1_.lastModified();
			}
			dataFile = new RandomAccessFile(p_i3777_1_, "rw");
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
	
	public synchronized DataInputStream getChunkDataInputStream(int p_76704_1_, int p_76704_2_)
	{
		if(outOfBounds(p_76704_1_, p_76704_2_)) return null;
		else
		{
			try
			{
				int var3 = getOffset(p_76704_1_, p_76704_2_);
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
	
	public DataOutputStream getChunkDataOutputStream(int p_76710_1_, int p_76710_2_)
	{
		return outOfBounds(p_76710_1_, p_76710_2_) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFileChunkBuffer(this, p_76710_1_, p_76710_2_)));
	}
	
	private int getOffset(int p_76707_1_, int p_76707_2_)
	{
		return offsets[p_76707_1_ + p_76707_2_ * 32];
	}
	
	public boolean isChunkSaved(int p_76709_1_, int p_76709_2_)
	{
		return getOffset(p_76709_1_, p_76709_2_) != 0;
	}
	
	private boolean outOfBounds(int p_76705_1_, int p_76705_2_)
	{
		return p_76705_1_ < 0 || p_76705_1_ >= 32 || p_76705_2_ < 0 || p_76705_2_ >= 32;
	}
	
	private void setChunkTimestamp(int p_76713_1_, int p_76713_2_, int p_76713_3_) throws IOException
	{
		chunkTimestamps[p_76713_1_ + p_76713_2_ * 32] = p_76713_3_;
		dataFile.seek(4096 + (p_76713_1_ + p_76713_2_ * 32) * 4);
		dataFile.writeInt(p_76713_3_);
	}
	
	private void setOffset(int p_76711_1_, int p_76711_2_, int p_76711_3_) throws IOException
	{
		offsets[p_76711_1_ + p_76711_2_ * 32] = p_76711_3_;
		dataFile.seek((p_76711_1_ + p_76711_2_ * 32) * 4);
		dataFile.writeInt(p_76711_3_);
	}
	
	private void write(int p_76712_1_, byte[] p_76712_2_, int p_76712_3_) throws IOException
	{
		dataFile.seek(p_76712_1_ * 4096);
		dataFile.writeInt(p_76712_3_ + 1);
		dataFile.writeByte(2);
		dataFile.write(p_76712_2_, 0, p_76712_3_);
	}
	
	protected synchronized void write(int p_76706_1_, int p_76706_2_, byte[] p_76706_3_, int p_76706_4_)
	{
		try
		{
			int var5 = getOffset(p_76706_1_, p_76706_2_);
			int var6 = var5 >> 8;
			int var7 = var5 & 255;
			int var8 = (p_76706_4_ + 5) / 4096 + 1;
			if(var8 >= 256) return;
			if(var6 != 0 && var7 == var8)
			{
				this.write(var6, p_76706_3_, p_76706_4_);
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
					setOffset(p_76706_1_, p_76706_2_, var9 << 8 | var8);
					for(var11 = 0; var11 < var8; ++var11)
					{
						sectorFree.set(var6 + var11, Boolean.valueOf(false));
					}
					this.write(var6, p_76706_3_, p_76706_4_);
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
					this.write(var6, p_76706_3_, p_76706_4_);
					setOffset(p_76706_1_, p_76706_2_, var6 << 8 | var8);
				}
			}
			setChunkTimestamp(p_76706_1_, p_76706_2_, (int) (System.currentTimeMillis() / 1000L));
		} catch(IOException var12)
		{
			var12.printStackTrace();
		}
	}
}
