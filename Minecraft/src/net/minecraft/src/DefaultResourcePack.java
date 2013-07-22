package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class DefaultResourcePack implements ResourcePack
{
	public static final Set field_110608_a = ImmutableSet.of("minecraft");
	private final Map field_110606_b = Maps.newHashMap();
	private final File field_110607_c;
	
	public DefaultResourcePack(File par1File)
	{
		field_110607_c = par1File;
		func_110603_a(field_110607_c);
	}
	
	@Override public BufferedImage func_110586_a() throws IOException
	{
		return ImageIO.read(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").func_110623_a()));
	}
	
	@Override public Set func_110587_b()
	{
		return field_110608_a;
	}
	
	@Override public boolean func_110589_b(ResourceLocation par1ResourceLocation)
	{
		return func_110605_c(par1ResourceLocation) != null || field_110606_b.containsKey(par1ResourceLocation.toString());
	}
	
	@Override public InputStream func_110590_a(ResourceLocation par1ResourceLocation) throws IOException
	{
		InputStream var2 = func_110605_c(par1ResourceLocation);
		if(var2 != null) return var2;
		else
		{
			File var3 = (File) field_110606_b.get(par1ResourceLocation.toString());
			if(var3 != null) return new FileInputStream(var3);
			else throw new FileNotFoundException(par1ResourceLocation.func_110623_a());
		}
	}
	
	public void func_110603_a(File par1File)
	{
		if(par1File.isDirectory())
		{
			File[] var2 = par1File.listFiles();
			int var3 = var2.length;
			for(int var4 = 0; var4 < var3; ++var4)
			{
				File var5 = var2[var4];
				func_110603_a(var5);
			}
		} else
		{
			func_110604_a(AbstractResourcePack.func_110595_a(field_110607_c, par1File), par1File);
		}
	}
	
	public void func_110604_a(String par1Str, File par2File)
	{
		field_110606_b.put(new ResourceLocation(par1Str).toString(), par2File);
	}
	
	private InputStream func_110605_c(ResourceLocation par1ResourceLocation)
	{
		return DefaultResourcePack.class.getResourceAsStream("/assets/minecraft/" + par1ResourceLocation.func_110623_a());
	}
	
	@Override public String func_130077_b()
	{
		return "Default";
	}
	
	@Override public MetadataSection func_135058_a(MetadataSerializer par1MetadataSerializer, String par2Str) throws IOException
	{
		return AbstractResourcePack.func_110596_a(par1MetadataSerializer, DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.mcmeta").func_110623_a()), par2Str);
	}
}
