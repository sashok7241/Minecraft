package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class ResourcePackRepositoryEntry
{
	private final File field_110523_b;
	private ResourcePack field_110524_c;
	private PackMetadataSection field_110521_d;
	private BufferedImage field_110522_e;
	private ResourceLocation field_110520_f;
	final ResourcePackRepository field_110525_a;
	
	private ResourcePackRepositoryEntry(ResourcePackRepository par1ResourcePackRepository, File par2File)
	{
		field_110525_a = par1ResourcePackRepository;
		field_110523_b = par2File;
	}
	
	ResourcePackRepositoryEntry(ResourcePackRepository par1ResourcePackRepository, File par2File, ResourcePackRepositoryFilter par3ResourcePackRepositoryFilter)
	{
		this(par1ResourcePackRepository, par2File);
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		return this == par1Obj ? true : par1Obj instanceof ResourcePackRepositoryEntry ? toString().equals(par1Obj.toString()) : false;
	}
	
	public ResourcePack func_110514_c()
	{
		return field_110524_c;
	}
	
	public String func_110515_d()
	{
		return field_110524_c.func_130077_b();
	}
	
	public void func_110516_a() throws IOException
	{
		field_110524_c = field_110523_b.isDirectory() ? new FolderResourcePack(field_110523_b) : new FileResourcePack(field_110523_b);
		field_110521_d = (PackMetadataSection) field_110524_c.func_135058_a(field_110525_a.field_110621_c, "pack");
		try
		{
			field_110522_e = field_110524_c.func_110586_a();
		} catch(IOException var2)
		{
			;
		}
		if(field_110522_e == null)
		{
			field_110522_e = field_110525_a.field_110620_b.func_110586_a();
		}
		func_110517_b();
	}
	
	public void func_110517_b()
	{
		if(field_110524_c instanceof Closeable)
		{
			IOUtils.closeQuietly((Closeable) field_110524_c);
		}
	}
	
	public void func_110518_a(TextureManager par1TextureManager)
	{
		if(field_110520_f == null)
		{
			field_110520_f = par1TextureManager.func_110578_a("texturepackicon", new DynamicTexture(field_110522_e));
		}
		par1TextureManager.func_110577_a(field_110520_f);
	}
	
	public String func_110519_e()
	{
		return field_110521_d == null ? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing \'pack\' section)" : field_110521_d.func_110461_a();
	}
	
	@Override public int hashCode()
	{
		return toString().hashCode();
	}
	
	@Override public String toString()
	{
		return String.format("%s:%s:%d", new Object[] { field_110523_b.getName(), field_110523_b.isDirectory() ? "folder" : "zip", Long.valueOf(field_110523_b.lastModified()) });
	}
}
