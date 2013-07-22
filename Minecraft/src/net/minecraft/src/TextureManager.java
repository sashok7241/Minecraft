package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class TextureManager
{
	private static TextureManager instance;
	private int nextTextureID = 0;
	private final HashMap texturesMap = new HashMap();
	private final HashMap mapNameToId = new HashMap();
	
	public Texture createEmptyTexture(String p_98145_1_, int p_98145_2_, int p_98145_3_, int p_98145_4_, int p_98145_5_)
	{
		return makeTexture(p_98145_1_, p_98145_2_, p_98145_3_, p_98145_4_, 10496, p_98145_5_, 9728, 9728, false, (BufferedImage) null);
	}
	
	public Stitcher createStitcher(String p_94262_1_)
	{
		int var2 = Minecraft.getGLMaximumTextureSize();
		return new Stitcher(p_94262_1_, var2, var2, true);
	}
	
	public List createTexture(String p_94266_1_)
	{
		ArrayList var2 = new ArrayList();
		ITexturePack var3 = Minecraft.getMinecraft().texturePackList.getSelectedTexturePack();
		try
		{
			BufferedImage var9 = ImageIO.read(var3.getResourceAsStream("/" + p_94266_1_));
			int var10 = var9.getHeight();
			int var11 = var9.getWidth();
			String var12 = getBasename(p_94266_1_);
			if(hasAnimationTxt(p_94266_1_, var3))
			{
				int var13 = var11;
				int var14 = var11;
				int var15 = var10 / var11;
				for(int var16 = 0; var16 < var15; ++var16)
				{
					Texture var17 = makeTexture(var12, 2, var13, var14, 10496, 6408, 9728, 9728, false, var9.getSubimage(0, var14 * var16, var13, var14));
					var2.add(var17);
				}
			} else if(var11 == var10)
			{
				var2.add(makeTexture(var12, 2, var11, var10, 10496, 6408, 9728, 9728, false, var9));
			} else
			{
				Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture: Skipping " + p_94266_1_ + " because of broken aspect ratio and not animation");
			}
			return var2;
		} catch(FileNotFoundException var18)
		{
			Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture called for file " + p_94266_1_ + ", but that file does not exist. Ignoring.");
		} catch(IOException var19)
		{
			Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.createTexture encountered an IOException when trying to read file " + p_94266_1_ + ". Ignoring.");
		}
		return var2;
	}
	
	private String getBasename(String p_98146_1_)
	{
		File var2 = new File(p_98146_1_);
		return var2.getName().substring(0, var2.getName().lastIndexOf(46));
	}
	
	public int getNextTextureId()
	{
		return nextTextureID++;
	}
	
	private boolean hasAnimationTxt(String p_98147_1_, ITexturePack p_98147_2_)
	{
		String var3 = "/" + p_98147_1_.substring(0, p_98147_1_.lastIndexOf(46)) + ".txt";
		boolean var4 = p_98147_2_.func_98138_b("/" + p_98147_1_, false);
		return Minecraft.getMinecraft().texturePackList.getSelectedTexturePack().func_98138_b(var3, !var4);
	}
	
	public Texture makeTexture(String p_94261_1_, int p_94261_2_, int p_94261_3_, int p_94261_4_, int p_94261_5_, int p_94261_6_, int p_94261_7_, int p_94261_8_, boolean p_94261_9_, BufferedImage p_94261_10_)
	{
		Texture var11 = new Texture(p_94261_1_, p_94261_2_, p_94261_3_, p_94261_4_, p_94261_5_, p_94261_6_, p_94261_7_, p_94261_8_, p_94261_10_);
		this.registerTexture(var11);
		return var11;
	}
	
	public void registerTexture(String p_94264_1_, Texture p_94264_2_)
	{
		mapNameToId.put(p_94264_1_, Integer.valueOf(p_94264_2_.getTextureId()));
		if(!texturesMap.containsKey(Integer.valueOf(p_94264_2_.getTextureId())))
		{
			texturesMap.put(Integer.valueOf(p_94264_2_.getTextureId()), p_94264_2_);
		}
	}
	
	public void registerTexture(Texture p_94259_1_)
	{
		if(texturesMap.containsValue(p_94259_1_))
		{
			Minecraft.getMinecraft().getLogAgent().logWarning("TextureManager.registerTexture called, but this texture has already been registered. ignoring.");
		} else
		{
			texturesMap.put(Integer.valueOf(p_94259_1_.getTextureId()), p_94259_1_);
		}
	}
	
	public static void init()
	{
		instance = new TextureManager();
	}
	
	public static TextureManager instance()
	{
		return instance;
	}
}
