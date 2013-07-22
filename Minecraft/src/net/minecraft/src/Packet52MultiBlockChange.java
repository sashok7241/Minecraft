package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet52MultiBlockChange extends Packet
{
	public int xPosition;
	public int zPosition;
	public byte[] metadataArray;
	public int size;
	private static byte[] field_73449_e = new byte[0];
	
	public Packet52MultiBlockChange()
	{
		isChunkDataPacket = true;
	}
	
	public Packet52MultiBlockChange(int p_i3302_1_, int p_i3302_2_, short[] p_i3302_3_, int p_i3302_4_, World p_i3302_5_)
	{
		isChunkDataPacket = true;
		xPosition = p_i3302_1_;
		zPosition = p_i3302_2_;
		size = p_i3302_4_;
		int var6 = 4 * p_i3302_4_;
		Chunk var7 = p_i3302_5_.getChunkFromChunkCoords(p_i3302_1_, p_i3302_2_);
		try
		{
			if(p_i3302_4_ >= 64)
			{
				field_98193_m.logInfo("ChunkTilesUpdatePacket compress " + p_i3302_4_);
				if(field_73449_e.length < var6)
				{
					field_73449_e = new byte[var6];
				}
			} else
			{
				ByteArrayOutputStream var8 = new ByteArrayOutputStream(var6);
				DataOutputStream var9 = new DataOutputStream(var8);
				for(int var10 = 0; var10 < p_i3302_4_; ++var10)
				{
					int var11 = p_i3302_3_[var10] >> 12 & 15;
					int var12 = p_i3302_3_[var10] >> 8 & 15;
					int var13 = p_i3302_3_[var10] & 255;
					var9.writeShort(p_i3302_3_[var10]);
					var9.writeShort((short) ((var7.getBlockID(var11, var13, var12) & 4095) << 4 | var7.getBlockMetadata(var11, var13, var12) & 15));
				}
				metadataArray = var8.toByteArray();
				if(metadataArray.length != var6) throw new RuntimeException("Expected length " + var6 + " doesn\'t match received length " + metadataArray.length);
			}
		} catch(IOException var14)
		{
			field_98193_m.logSevereException("Couldn\'t create chunk packet", var14);
			metadataArray = null;
		}
	}
	
	@Override public int getPacketSize()
	{
		return 10 + size * 4;
	}
	
	@Override public void processPacket(NetHandler p_73279_1_)
	{
		p_73279_1_.handleMultiBlockChange(this);
	}
	
	@Override public void readPacketData(DataInputStream p_73267_1_) throws IOException
	{
		xPosition = p_73267_1_.readInt();
		zPosition = p_73267_1_.readInt();
		size = p_73267_1_.readShort() & 65535;
		int var2 = p_73267_1_.readInt();
		if(var2 > 0)
		{
			metadataArray = new byte[var2];
			p_73267_1_.readFully(metadataArray);
		}
	}
	
	@Override public void writePacketData(DataOutputStream p_73273_1_) throws IOException
	{
		p_73273_1_.writeInt(xPosition);
		p_73273_1_.writeInt(zPosition);
		p_73273_1_.writeShort((short) size);
		if(metadataArray != null)
		{
			p_73273_1_.writeInt(metadataArray.length);
			p_73273_1_.write(metadataArray);
		} else
		{
			p_73273_1_.writeInt(0);
		}
	}
}
