package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class RegionFileChunkBuffer extends ByteArrayOutputStream
{
	private int chunkX;
	private int chunkZ;
	final RegionFile regionFile;
	
	public RegionFileChunkBuffer(RegionFile par1RegionFile, int par2, int par3)
	{
		super(8096);
		regionFile = par1RegionFile;
		chunkX = par2;
		chunkZ = par3;
	}
	
	@Override public void close() throws IOException
	{
		regionFile.write(chunkX, chunkZ, buf, count);
	}
}
