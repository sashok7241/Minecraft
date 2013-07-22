package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class Packet51MapChunk extends Packet
{
	public int xCh;
	public int zCh;
	public int yChMin;
	public int yChMax;
	private byte[] chunkData;
	private byte[] compressedChunkData;
	public boolean includeInitialize;
	private int tempLength;
	private static byte[] temp = new byte[196864];
	
	public Packet51MapChunk()
	{
		isChunkDataPacket = true;
	}
	
	public Packet51MapChunk(Chunk p_i3323_1_, boolean p_i3323_2_, int p_i3323_3_)
	{
		isChunkDataPacket = true;
		xCh = p_i3323_1_.xPosition;
		zCh = p_i3323_1_.zPosition;
		includeInitialize = p_i3323_2_;
		Packet51MapChunkData var4 = getMapChunkData(p_i3323_1_, p_i3323_2_, p_i3323_3_);
		Deflater var5 = new Deflater(-1);
		yChMax = var4.chunkHasAddSectionFlag;
		yChMin = var4.chunkExistFlag;
		try
		{
			compressedChunkData = var4.compressedData;
			var5.setInput(var4.compressedData, 0, var4.compressedData.length);
			var5.finish();
			chunkData = new byte[var4.compressedData.length];
			tempLength = var5.deflate(chunkData);
		} finally
		{
			var5.end();
		}
	}
	
	public byte[] getCompressedChunkData()
	{
		return compressedChunkData;
	}
	
	@Override public int getPacketSize()
	{
		return 17 + tempLength;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleMapChunk(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xCh = p_73267_1_.readInt();
		zCh = p_73267_1_.readInt();
		includeInitialize = p_73267_1_.readBoolean();
		yChMin = p_73267_1_.readShort();
		yChMax = p_73267_1_.readShort();
		tempLength = p_73267_1_.readInt();
		if(temp.length < tempLength)
		{
			temp = new byte[tempLength];
		}
		p_73267_1_.readFully(temp, 0, tempLength);
		int var2 = 0;
		int var3;
		for(var3 = 0; var3 < 16; ++var3)
		{
			var2 += yChMin >> var3 & 1;
		}
		var3 = 12288 * var2;
		if(includeInitialize)
		{
			var3 += 256;
		}
		compressedChunkData = new byte[var3];
		Inflater var4 = new Inflater();
		var4.setInput(temp, 0, tempLength);
		try
		{
			var4.inflate(compressedChunkData);
		} catch(DataFormatException var9)
		{
			throw new IOException("Bad compressed data format");
		} finally
		{
			var4.end();
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xCh);
		p_73273_1_.writeInt(zCh);
		p_73273_1_.writeBoolean(includeInitialize);
		p_73273_1_.writeShort((short) (yChMin & 65535));
		p_73273_1_.writeShort((short) (yChMax & 65535));
		p_73273_1_.writeInt(tempLength);
		p_73273_1_.write(chunkData, 0, tempLength);
	}
	
	public static Packet51MapChunkData getMapChunkData(Chunk p_73594_0_, boolean p_73594_1_, int p_73594_2_)
	{
		int var3 = 0;
		ExtendedBlockStorage[] var4 = p_73594_0_.getBlockStorageArray();
		int var5 = 0;
		Packet51MapChunkData var6 = new Packet51MapChunkData();
		byte[] var7 = temp;
		if(p_73594_1_)
		{
			p_73594_0_.sendUpdates = true;
		}
		int var8;
		for(var8 = 0; var8 < var4.length; ++var8)
		{
			if(var4[var8] != null && (!p_73594_1_ || !var4[var8].isEmpty()) && (p_73594_2_ & 1 << var8) != 0)
			{
				var6.chunkExistFlag |= 1 << var8;
				if(var4[var8].getBlockMSBArray() != null)
				{
					var6.chunkHasAddSectionFlag |= 1 << var8;
					++var5;
				}
			}
		}
		for(var8 = 0; var8 < var4.length; ++var8)
		{
			if(var4[var8] != null && (!p_73594_1_ || !var4[var8].isEmpty()) && (p_73594_2_ & 1 << var8) != 0)
			{
				byte[] var9 = var4[var8].getBlockLSBArray();
				System.arraycopy(var9, 0, var7, var3, var9.length);
				var3 += var9.length;
			}
		}
		NibbleArray var10;
		for(var8 = 0; var8 < var4.length; ++var8)
		{
			if(var4[var8] != null && (!p_73594_1_ || !var4[var8].isEmpty()) && (p_73594_2_ & 1 << var8) != 0)
			{
				var10 = var4[var8].getMetadataArray();
				System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
				var3 += var10.data.length;
			}
		}
		for(var8 = 0; var8 < var4.length; ++var8)
		{
			if(var4[var8] != null && (!p_73594_1_ || !var4[var8].isEmpty()) && (p_73594_2_ & 1 << var8) != 0)
			{
				var10 = var4[var8].getBlocklightArray();
				System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
				var3 += var10.data.length;
			}
		}
		if(!p_73594_0_.worldObj.provider.hasNoSky)
		{
			for(var8 = 0; var8 < var4.length; ++var8)
			{
				if(var4[var8] != null && (!p_73594_1_ || !var4[var8].isEmpty()) && (p_73594_2_ & 1 << var8) != 0)
				{
					var10 = var4[var8].getSkylightArray();
					System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
					var3 += var10.data.length;
				}
			}
		}
		if(var5 > 0)
		{
			for(var8 = 0; var8 < var4.length; ++var8)
			{
				if(var4[var8] != null && (!p_73594_1_ || !var4[var8].isEmpty()) && var4[var8].getBlockMSBArray() != null && (p_73594_2_ & 1 << var8) != 0)
				{
					var10 = var4[var8].getBlockMSBArray();
					System.arraycopy(var10.data, 0, var7, var3, var10.data.length);
					var3 += var10.data.length;
				}
			}
		}
		if(p_73594_1_)
		{
			byte[] var11 = p_73594_0_.getBiomeArray();
			System.arraycopy(var11, 0, var7, var3, var11.length);
			var3 += var11.length;
		}
		var6.compressedData = new byte[var3];
		System.arraycopy(var7, 0, var6.compressedData, 0, var3);
		return var6;
	}
}
