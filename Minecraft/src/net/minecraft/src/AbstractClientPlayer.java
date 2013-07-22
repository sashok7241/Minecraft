package net.minecraft.src;

import net.minecraft.client.Minecraft;

public abstract class AbstractClientPlayer extends EntityPlayer
{
	public static final ResourceLocation field_110314_b = new ResourceLocation("textures/entity/steve.png");
	private ThreadDownloadImageData field_110316_a;
	private ThreadDownloadImageData field_110315_c;
	private ResourceLocation field_110312_d;
	private ResourceLocation field_110313_e;
	
	public AbstractClientPlayer(World par1World, String par2Str)
	{
		super(par1World, par2Str);
		func_110302_j();
	}
	
	protected void func_110302_j()
	{
		System.out.println("Setting up custom skins");
		if(username != null && !username.isEmpty())
		{
			field_110312_d = func_110311_f(username);
			field_110313_e = func_110299_g(username);
			field_110316_a = func_110304_a(field_110312_d, username);
			field_110315_c = func_110307_b(field_110313_e, username);
		}
	}
	
	public ResourceLocation func_110303_q()
	{
		return field_110313_e;
	}
	
	public ResourceLocation func_110306_p()
	{
		return field_110312_d;
	}
	
	public ThreadDownloadImageData func_110309_l()
	{
		return field_110316_a;
	}
	
	public ThreadDownloadImageData func_110310_o()
	{
		return field_110315_c;
	}
	
	public static ResourceLocation func_110299_g(String par0Str)
	{
		return new ResourceLocation("cloaks/" + StringUtils.stripControlCodes(par0Str));
	}
	
	public static String func_110300_d(String par0Str)
	{
		return String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(par0Str) });
	}
	
	private static ThreadDownloadImageData func_110301_a(ResourceLocation par0ResourceLocation, String par1Str, ResourceLocation par2ResourceLocation, IImageBuffer par3IImageBuffer)
	{
		TextureManager var4 = Minecraft.getMinecraft().func_110434_K();
		Object var5 = var4.func_110581_b(par0ResourceLocation);
		if(var5 == null)
		{
			var5 = new ThreadDownloadImageData(par1Str, par2ResourceLocation, par3IImageBuffer);
			var4.func_110579_a(par0ResourceLocation, (TextureObject) var5);
		}
		return (ThreadDownloadImageData) var5;
	}
	
	public static ThreadDownloadImageData func_110304_a(ResourceLocation par0ResourceLocation, String par1Str)
	{
		return func_110301_a(par0ResourceLocation, func_110300_d(par1Str), field_110314_b, new ImageBufferDownload());
	}
	
	public static ResourceLocation func_110305_h(String par0Str)
	{
		return new ResourceLocation("skull/" + StringUtils.stripControlCodes(par0Str));
	}
	
	public static ThreadDownloadImageData func_110307_b(ResourceLocation par0ResourceLocation, String par1Str)
	{
		return func_110301_a(par0ResourceLocation, func_110308_e(par1Str), (ResourceLocation) null, (IImageBuffer) null);
	}
	
	public static String func_110308_e(String par0Str)
	{
		return String.format("http://skins.minecraft.net/MinecraftCloaks/%s.png", new Object[] { StringUtils.stripControlCodes(par0Str) });
	}
	
	public static ResourceLocation func_110311_f(String par0Str)
	{
		return new ResourceLocation("skins/" + StringUtils.stripControlCodes(par0Str));
	}
}
