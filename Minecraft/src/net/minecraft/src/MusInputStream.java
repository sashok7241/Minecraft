package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

class MusInputStream extends InputStream
{
	private int hash;
	private InputStream inputStream;
	byte[] buffer;
	final CodecMus codec;
	
	public MusInputStream(CodecMus par1CodecMus, URL par2URL, InputStream par3InputStream)
	{
		codec = par1CodecMus;
		buffer = new byte[1];
		inputStream = par3InputStream;
		String var4 = par2URL.getPath();
		var4 = var4.substring(var4.lastIndexOf("/") + 1);
		hash = var4.hashCode();
	}
	
	@Override public int read() throws IOException
	{
		int var1 = this.read(buffer, 0, 1);
		return var1 < 0 ? var1 : buffer[0];
	}
	
	@Override public int read(byte[] par1ArrayOfByte, int par2, int par3) throws IOException
	{
		par3 = inputStream.read(par1ArrayOfByte, par2, par3);
		for(int var4 = 0; var4 < par3; ++var4)
		{
			byte var5 = par1ArrayOfByte[par2 + var4] = (byte) (par1ArrayOfByte[par2 + var4] ^ hash >> 8);
			hash = hash * 498729871 + 85731 * var5;
		}
		return par3;
	}
}
