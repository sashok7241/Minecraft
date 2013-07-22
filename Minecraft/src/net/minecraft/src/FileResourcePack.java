package net.minecraft.src;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileResourcePack extends AbstractResourcePack implements Closeable
{
	public static final Splitter field_110601_c = Splitter.on('/').omitEmptyStrings().limit(3);
	private ZipFile field_110600_d;
	
	public FileResourcePack(File par1File)
	{
		super(par1File);
	}
	
	@Override public void close()
	{
		if(field_110600_d != null)
		{
			try
			{
				field_110600_d.close();
			} catch(Exception ex)
			{
			}
			field_110600_d = null;
		}
	}
	
	@Override protected void finalize()
	{
		close();
		try
		{
			super.finalize();
		} catch(Throwable t)
		{
		}
	}
	
	@Override public Set func_110587_b()
	{
		ZipFile var1;
		try
		{
			var1 = func_110599_c();
		} catch(IOException var8)
		{
			return Collections.emptySet();
		}
		Enumeration var2 = var1.entries();
		HashSet var3 = Sets.newHashSet();
		while(var2.hasMoreElements())
		{
			ZipEntry var4 = (ZipEntry) var2.nextElement();
			String var5 = var4.getName();
			if(var5.startsWith("assets/"))
			{
				ArrayList var6 = Lists.newArrayList(field_110601_c.split(var5));
				if(var6.size() > 1)
				{
					String var7 = (String) var6.get(1);
					if(!var7.equals(var7.toLowerCase()))
					{
						func_110594_c(var7);
					} else
					{
						var3.add(var7);
					}
				}
			}
		}
		return var3;
	}
	
	@Override protected InputStream func_110591_a(String par1Str) throws IOException
	{
		ZipFile var2 = func_110599_c();
		ZipEntry var3 = var2.getEntry(par1Str);
		if(var3 == null) throw new ResourcePackFileNotFoundException(field_110597_b, par1Str);
		else return var2.getInputStream(var3);
	}
	
	@Override public boolean func_110593_b(String par1Str)
	{
		try
		{
			return func_110599_c().getEntry(par1Str) != null;
		} catch(IOException var3)
		{
			return false;
		}
	}
	
	private ZipFile func_110599_c() throws IOException
	{
		if(field_110600_d == null)
		{
			field_110600_d = new ZipFile(field_110597_b);
		}
		return field_110600_d;
	}
}
