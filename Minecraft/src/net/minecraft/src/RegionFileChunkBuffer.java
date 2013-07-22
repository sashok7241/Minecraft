package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

class RegionFileChunkBuffer extends ByteArrayOutputStream
{
	private int chunkX;
	private int chunkZ;
	final RegionFile regionFile;
	
	public RegionFileChunkBuffer(RegionFile p_i3776_1_, int p_i3776_2_, int p_i3776_3_)
	{
		super(8096);
		regionFile = p_i3776_1_;
		chunkX = p_i3776_2_;
		chunkZ = p_i3776_3_;
	}
	
	@Override public void close() throws IOException
	{
		regionFile.write(chunkX, chunkZ, buf, count);
	}
}
