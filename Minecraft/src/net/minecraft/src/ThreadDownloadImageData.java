package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ThreadDownloadImageData extends AbstractTexture
{
	private final String field_110562_b;
	private final IImageBuffer field_110563_c;
	private BufferedImage field_110560_d;
	private Thread field_110561_e;
	private SimpleTexture field_110558_f;
	private boolean field_110559_g;
	
	public ThreadDownloadImageData(String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
	{
		field_110562_b = par1Str;
		field_110563_c = par3IImageBuffer;
		field_110558_f = par2ResourceLocation != null ? new SimpleTexture(par2ResourceLocation) : null;
	}
	
	@Override public void func_110551_a(ResourceManager par1ResourceManager) throws IOException
	{
		if(field_110560_d == null)
		{
			if(field_110558_f != null)
			{
				field_110558_f.func_110551_a(par1ResourceManager);
				field_110553_a = field_110558_f.func_110552_b();
			}
		} else
		{
			TextureUtil.func_110987_a(func_110552_b(), field_110560_d);
		}
		if(field_110561_e == null)
		{
			field_110561_e = new ThreadDownloadImageDataINNER1(this);
			field_110561_e.setDaemon(true);
			field_110561_e.setName("Skin downloader: " + field_110562_b);
			field_110561_e.start();
		}
	}
	
	@Override public int func_110552_b()
	{
		int var1 = super.func_110552_b();
		if(!field_110559_g && field_110560_d != null)
		{
			TextureUtil.func_110987_a(var1, field_110560_d);
			field_110559_g = true;
		}
		return var1;
	}
	
	public void func_110556_a(BufferedImage par1BufferedImage)
	{
		field_110560_d = par1BufferedImage;
	}
	
	public boolean func_110557_a()
	{
		func_110552_b();
		return field_110559_g;
	}
	
	static String func_110554_a(ThreadDownloadImageData par0ThreadDownloadImageData)
	{
		return par0ThreadDownloadImageData.field_110562_b;
	}
	
	static IImageBuffer func_110555_b(ThreadDownloadImageData par0ThreadDownloadImageData)
	{
		return par0ThreadDownloadImageData.field_110563_c;
	}
}
