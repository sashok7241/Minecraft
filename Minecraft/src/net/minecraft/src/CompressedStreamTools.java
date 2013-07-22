package net.minecraft.src;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressedStreamTools
{
	public static byte[] compress(NBTTagCompound p_74798_0_) throws IOException
	{
		ByteArrayOutputStream var1 = new ByteArrayOutputStream();
		DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(var1));
		try
		{
			write(p_74798_0_, var2);
		} finally
		{
			var2.close();
		}
		return var1.toByteArray();
	}
	
	public static NBTTagCompound decompress(byte[] p_74792_0_) throws IOException
	{
		DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(p_74792_0_))));
		NBTTagCompound var2;
		try
		{
			var2 = read(var1);
		} finally
		{
			var1.close();
		}
		return var2;
	}
	
	public static NBTTagCompound read(DataInput p_74794_0_) throws IOException
	{
		NBTBase var1 = NBTBase.readNamedTag(p_74794_0_);
		if(var1 instanceof NBTTagCompound) return (NBTTagCompound) var1;
		else throw new IOException("Root tag must be a named compound tag");
	}
	
	public static NBTTagCompound read(File par0File) throws IOException
	{
		if(!par0File.exists()) return null;
		else
		{
			DataInputStream var1 = new DataInputStream(new FileInputStream(par0File));
			NBTTagCompound var2;
			try
			{
				var2 = read(var1);
			} finally
			{
				var1.close();
			}
			return var2;
		}
	}
	
	public static NBTTagCompound readCompressed(InputStream p_74796_0_) throws IOException
	{
		DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(p_74796_0_)));
		NBTTagCompound var2;
		try
		{
			var2 = read(var1);
		} finally
		{
			var1.close();
		}
		return var2;
	}
	
	public static void safeWrite(NBTTagCompound par0NBTTagCompound, File par1File) throws IOException
	{
		File var2 = new File(par1File.getAbsolutePath() + "_tmp");
		if(var2.exists())
		{
			var2.delete();
		}
		write(par0NBTTagCompound, var2);
		if(par1File.exists())
		{
			par1File.delete();
		}
		if(par1File.exists()) throw new IOException("Failed to delete " + par1File);
		else
		{
			var2.renameTo(par1File);
		}
	}
	
	public static void write(NBTTagCompound p_74800_0_, DataOutput p_74800_1_) throws IOException
	{
		NBTBase.writeNamedTag(p_74800_0_, p_74800_1_);
	}
	
	public static void write(NBTTagCompound par0NBTTagCompound, File par1File) throws IOException
	{
		DataOutputStream var2 = new DataOutputStream(new FileOutputStream(par1File));
		try
		{
			write(par0NBTTagCompound, var2);
		} finally
		{
			var2.close();
		}
	}
	
	public static void writeCompressed(NBTTagCompound p_74799_0_, OutputStream p_74799_1_) throws IOException
	{
		DataOutputStream var2 = new DataOutputStream(new GZIPOutputStream(p_74799_1_));
		try
		{
			write(p_74799_0_, var2);
		} finally
		{
			var2.close();
		}
	}
}
