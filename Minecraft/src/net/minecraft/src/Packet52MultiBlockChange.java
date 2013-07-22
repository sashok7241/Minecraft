package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
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
	
	public Packet52MultiBlockChange(int par1, int par2, short[] par3ArrayOfShort, int par4, World par5World)
	{
		isChunkDataPacket = true;
		xPosition = par1;
		zPosition = par2;
		size = par4;
		int var6 = 4 * par4;
		Chunk var7 = par5World.getChunkFromChunkCoords(par1, par2);
		try
		{
			if(par4 >= 64)
			{
				field_98193_m.logInfo("ChunkTilesUpdatePacket compress " + par4);
				if(field_73449_e.length < var6)
				{
					field_73449_e = new byte[var6];
				}
			} else
			{
				ByteArrayOutputStream var8 = new ByteArrayOutputStream(var6);
				DataOutputStream var9 = new DataOutputStream(var8);
				for(int var10 = 0; var10 < par4; ++var10)
				{
					int var11 = par3ArrayOfShort[var10] >> 12 & 15;
					int var12 = par3ArrayOfShort[var10] >> 8 & 15;
					int var13 = par3ArrayOfShort[var10] & 255;
					var9.writeShort(par3ArrayOfShort[var10]);
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
	
	@Override public void processPacket(NetHandler par1NetHandler)
	{
		par1NetHandler.handleMultiBlockChange(this);
	}
	
	@Override public void readPacketData(DataInput par1DataInput) throws IOException
	{
		xPosition = par1DataInput.readInt();
		zPosition = par1DataInput.readInt();
		size = par1DataInput.readShort() & 65535;
		int var2 = par1DataInput.readInt();
		if(var2 > 0)
		{
			metadataArray = new byte[var2];
			par1DataInput.readFully(metadataArray);
		}
	}
	
	@Override public void writePacketData(DataOutput par1DataOutput) throws IOException
	{
		par1DataOutput.writeInt(xPosition);
		par1DataOutput.writeInt(zPosition);
		par1DataOutput.writeShort((short) size);
		if(metadataArray != null)
		{
			par1DataOutput.writeInt(metadataArray.length);
			par1DataOutput.write(metadataArray);
		} else
		{
			par1DataOutput.writeInt(0);
		}
	}
}
