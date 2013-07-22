package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class TextureAtlasSprite implements Icon
{
	private final String field_110984_i;
	protected List field_110976_a = Lists.newArrayList();
	private AnimationMetadataSection field_110982_k;
	protected boolean field_130222_e;
	protected int field_110975_c;
	protected int field_110974_d;
	protected int field_130223_c;
	protected int field_130224_d;
	private float field_110979_l;
	private float field_110980_m;
	private float field_110977_n;
	private float field_110978_o;
	protected int field_110973_g;
	protected int field_110983_h;
	
	protected TextureAtlasSprite(String par1Str)
	{
		field_110984_i = par1Str;
	}
	
	public void copyFrom(TextureAtlasSprite par1TextureAtlasSprite)
	{
		field_110975_c = par1TextureAtlasSprite.field_110975_c;
		field_110974_d = par1TextureAtlasSprite.field_110974_d;
		field_130223_c = par1TextureAtlasSprite.field_130223_c;
		field_130224_d = par1TextureAtlasSprite.field_130224_d;
		field_130222_e = par1TextureAtlasSprite.field_130222_e;
		field_110979_l = par1TextureAtlasSprite.field_110979_l;
		field_110980_m = par1TextureAtlasSprite.field_110980_m;
		field_110977_n = par1TextureAtlasSprite.field_110977_n;
		field_110978_o = par1TextureAtlasSprite.field_110978_o;
	}
	
	public int[] func_110965_a(int par1)
	{
		return (int[]) field_110976_a.get(par1);
	}
	
	public void func_110966_b(int par1)
	{
		field_130223_c = par1;
	}
	
	public int func_110967_i()
	{
		return field_110974_d;
	}
	
	public void func_110968_a(List par1List)
	{
		field_110976_a = par1List;
	}
	
	public void func_110969_c(int par1)
	{
		field_130224_d = par1;
	}
	
	public int func_110970_k()
	{
		return field_110976_a.size();
	}
	
	public void func_110971_a(int par1, int par2, int par3, int par4, boolean par5)
	{
		field_110975_c = par3;
		field_110974_d = par4;
		field_130222_e = par5;
		float var6 = (float) (0.009999999776482582D / par1);
		float var7 = (float) (0.009999999776482582D / par2);
		field_110979_l = par3 / (float) par1 + var6;
		field_110980_m = (par3 + field_130223_c) / (float) par1 - var6;
		field_110977_n = (float) par4 / (float) par2 + var7;
		field_110978_o = (float) (par4 + field_130224_d) / (float) par2 - var7;
	}
	
	public int func_130010_a()
	{
		return field_110975_c;
	}
	
	public boolean func_130098_m()
	{
		return field_110982_k != null;
	}
	
	private void func_130099_d(int par1)
	{
		if(field_110976_a.size() <= par1)
		{
			for(int var2 = field_110976_a.size(); var2 <= par1; ++var2)
			{
				field_110976_a.add((Object) null);
			}
		}
	}
	
	public void func_130100_a(Resource par1Resource) throws IOException
	{
		func_130102_n();
		InputStream var2 = par1Resource.func_110527_b();
		AnimationMetadataSection var3 = (AnimationMetadataSection) par1Resource.func_110526_a("animation");
		BufferedImage var4 = ImageIO.read(var2);
		field_130224_d = var4.getHeight();
		field_130223_c = var4.getWidth();
		int[] var5 = new int[field_130224_d * field_130223_c];
		var4.getRGB(0, 0, field_130223_c, field_130224_d, var5, 0, field_130223_c);
		if(var3 == null)
		{
			if(field_130224_d != field_130223_c) throw new RuntimeException("broken aspect ratio and not an animation");
			field_110976_a.add(var5);
		} else
		{
			int var6 = field_130224_d / field_130223_c;
			int var7 = field_130223_c;
			int var8 = field_130223_c;
			field_130224_d = field_130223_c;
			int var10;
			if(var3.func_110473_c() > 0)
			{
				Iterator var9 = var3.func_130073_e().iterator();
				while(var9.hasNext())
				{
					var10 = ((Integer) var9.next()).intValue();
					if(var10 >= var6) throw new RuntimeException("invalid frameindex " + var10);
					func_130099_d(var10);
					field_110976_a.set(var10, func_130101_a(var5, var7, var8, var10));
				}
				field_110982_k = var3;
			} else
			{
				ArrayList var11 = Lists.newArrayList();
				for(var10 = 0; var10 < var6; ++var10)
				{
					field_110976_a.add(func_130101_a(var5, var7, var8, var10));
					var11.add(new AnimationFrame(var10, -1));
				}
				field_110982_k = new AnimationMetadataSection(var11, field_130223_c, field_130224_d, var3.func_110469_d());
			}
		}
	}
	
	private void func_130102_n()
	{
		field_110982_k = null;
		func_110968_a(Lists.newArrayList());
		field_110973_g = 0;
		field_110983_h = 0;
	}
	
	public void func_130103_l()
	{
		field_110976_a.clear();
	}
	
	@Override public String getIconName()
	{
		return field_110984_i;
	}
	
	@Override public float getInterpolatedU(double par1)
	{
		float var3 = field_110980_m - field_110979_l;
		return field_110979_l + var3 * (float) par1 / 16.0F;
	}
	
	@Override public float getInterpolatedV(double par1)
	{
		float var3 = field_110978_o - field_110977_n;
		return field_110977_n + var3 * ((float) par1 / 16.0F);
	}
	
	@Override public float getMaxU()
	{
		return field_110980_m;
	}
	
	@Override public float getMaxV()
	{
		return field_110978_o;
	}
	
	@Override public float getMinU()
	{
		return field_110979_l;
	}
	
	@Override public float getMinV()
	{
		return field_110977_n;
	}
	
	@Override public int getOriginX()
	{
		return field_130223_c;
	}
	
	@Override public int getOriginY()
	{
		return field_130224_d;
	}
	
	@Override public String toString()
	{
		return "TextureAtlasSprite{name=\'" + field_110984_i + '\'' + ", frameCount=" + field_110976_a.size() + ", rotated=" + field_130222_e + ", x=" + field_110975_c + ", y=" + field_110974_d + ", height=" + field_130224_d + ", width=" + field_130223_c + ", u0=" + field_110979_l + ", u1=" + field_110980_m + ", v0=" + field_110977_n + ", v1=" + field_110978_o + '}';
	}
	
	public void updateAnimation()
	{
		++field_110983_h;
		if(field_110983_h >= field_110982_k.func_110472_a(field_110973_g))
		{
			int var1 = field_110982_k.func_110468_c(field_110973_g);
			int var2 = field_110982_k.func_110473_c() == 0 ? field_110976_a.size() : field_110982_k.func_110473_c();
			field_110973_g = (field_110973_g + 1) % var2;
			field_110983_h = 0;
			int var3 = field_110982_k.func_110468_c(field_110973_g);
			if(var1 != var3 && var3 >= 0 && var3 < field_110976_a.size())
			{
				TextureUtil.func_110998_a((int[]) field_110976_a.get(var3), field_130223_c, field_130224_d, field_110975_c, field_110974_d, false, false);
			}
		}
	}
	
	private static int[] func_130101_a(int[] par0ArrayOfInteger, int par1, int par2, int par3)
	{
		int[] var4 = new int[par1 * par2];
		System.arraycopy(par0ArrayOfInteger, par3 * var4.length, var4, 0, var4.length);
		return var4;
	}
}
