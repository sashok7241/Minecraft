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
	
	public MusInputStream(CodecMus p_i3126_1_, URL p_i3126_2_, InputStream p_i3126_3_)
	{
		codec = p_i3126_1_;
		buffer = new byte[1];
		inputStream = p_i3126_3_;
		String var4 = p_i3126_2_.getPath();
		var4 = var4.substring(var4.lastIndexOf("/") + 1);
		hash = var4.hashCode();
	}
	
	@Override public int read() throws IOException
	{
		int var1 = this.read(buffer, 0, 1);
		return var1 < 0 ? var1 : buffer[0];
	}
	
	@Override public int read(byte[] p_read_1_, int p_read_2_, int p_read_3_) throws IOException
	{
		p_read_3_ = inputStream.read(p_read_1_, p_read_2_, p_read_3_);
		for(int var4 = 0; var4 < p_read_3_; ++var4)
		{
			byte var5 = p_read_1_[p_read_2_ + var4] = (byte) (p_read_1_[p_read_2_ + var4] ^ hash >> 8);
			hash = hash * 498729871 + 85731 * var5;
		}
		return p_read_3_;
	}
}
