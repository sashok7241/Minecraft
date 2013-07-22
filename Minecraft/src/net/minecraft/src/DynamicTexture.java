package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DynamicTexture extends AbstractTexture
{
	private final int[] field_110566_b;
	private final int width;
	private final int height;
	
	public DynamicTexture(BufferedImage par1BufferedImage)
	{
		this(par1BufferedImage.getWidth(), par1BufferedImage.getHeight());
		par1BufferedImage.getRGB(0, 0, par1BufferedImage.getWidth(), par1BufferedImage.getHeight(), field_110566_b, 0, par1BufferedImage.getWidth());
		func_110564_a();
	}
	
	public DynamicTexture(int par1, int par2)
	{
		width = par1;
		height = par2;
		field_110566_b = new int[par1 * par2];
		TextureUtil.func_110991_a(func_110552_b(), par1, par2);
	}
	
	@Override public void func_110551_a(ResourceManager par1ResourceManager) throws IOException
	{
	}
	
	public void func_110564_a()
	{
		TextureUtil.func_110988_a(func_110552_b(), field_110566_b, width, height);
	}
	
	public int[] func_110565_c()
	{
		return field_110566_b;
	}
}
