package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.Bidi;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;

public class FontRenderer implements ResourceManagerReloadListener
{
	private static final ResourceLocation[] field_111274_c = new ResourceLocation[256];
	private int[] charWidth = new int[256];
	public int FONT_HEIGHT = 9;
	public Random fontRandom = new Random();
	private byte[] glyphWidth = new byte[65536];
	private int[] colorCode = new int[32];
	private final ResourceLocation field_111273_g;
	private final TextureManager renderEngine;
	private float posX;
	private float posY;
	private boolean unicodeFlag;
	private boolean bidiFlag;
	private float red;
	private float blue;
	private float green;
	private float alpha;
	private int textColor;
	private boolean randomStyle;
	private boolean boldStyle;
	private boolean italicStyle;
	private boolean underlineStyle;
	private boolean strikethroughStyle;
	
	public FontRenderer(GameSettings par1GameSettings, ResourceLocation par2ResourceLocation, TextureManager par3TextureManager, boolean par4)
	{
		field_111273_g = par2ResourceLocation;
		renderEngine = par3TextureManager;
		unicodeFlag = par4;
		par3TextureManager.func_110577_a(field_111273_g);
		for(int var5 = 0; var5 < 32; ++var5)
		{
			int var6 = (var5 >> 3 & 1) * 85;
			int var7 = (var5 >> 2 & 1) * 170 + var6;
			int var8 = (var5 >> 1 & 1) * 170 + var6;
			int var9 = (var5 >> 0 & 1) * 170 + var6;
			if(var5 == 6)
			{
				var7 += 85;
			}
			if(par1GameSettings.anaglyph)
			{
				int var10 = (var7 * 30 + var8 * 59 + var9 * 11) / 100;
				int var11 = (var7 * 30 + var8 * 70) / 100;
				int var12 = (var7 * 30 + var9 * 70) / 100;
				var7 = var10;
				var8 = var11;
				var9 = var12;
			}
			if(var5 >= 16)
			{
				var7 /= 4;
				var8 /= 4;
				var9 /= 4;
			}
			colorCode[var5] = (var7 & 255) << 16 | (var8 & 255) << 8 | var9 & 255;
		}
		readGlyphSizes();
	}
	
	private String bidiReorder(String par1Str)
	{
		if(par1Str != null && Bidi.requiresBidi(par1Str.toCharArray(), 0, par1Str.length()))
		{
			Bidi var2 = new Bidi(par1Str, -2);
			byte[] var3 = new byte[var2.getRunCount()];
			String[] var4 = new String[var3.length];
			int var7;
			for(int var5 = 0; var5 < var3.length; ++var5)
			{
				int var6 = var2.getRunStart(var5);
				var7 = var2.getRunLimit(var5);
				int var8 = var2.getRunLevel(var5);
				String var9 = par1Str.substring(var6, var7);
				var3[var5] = (byte) var8;
				var4[var5] = var9;
			}
			String[] var11 = var4.clone();
			Bidi.reorderVisually(var3, 0, var4, 0, var3.length);
			StringBuilder var12 = new StringBuilder();
			var7 = 0;
			while(var7 < var4.length)
			{
				byte var13 = var3[var7];
				int var14 = 0;
				while(true)
				{
					if(var14 < var11.length)
					{
						if(!var11[var14].equals(var4[var7]))
						{
							++var14;
							continue;
						}
						var13 = var3[var14];
					}
					if((var13 & 1) == 0)
					{
						var12.append(var4[var7]);
					} else
					{
						for(var14 = var4[var7].length() - 1; var14 >= 0; --var14)
						{
							char var10 = var4[var7].charAt(var14);
							if(var10 == 40)
							{
								var10 = 41;
							} else if(var10 == 41)
							{
								var10 = 40;
							}
							var12.append(var10);
						}
					}
					++var7;
					break;
				}
			}
			return var12.toString();
		} else return par1Str;
	}
	
	public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5)
	{
		resetStyles();
		textColor = par5;
		par1Str = trimStringNewline(par1Str);
		renderSplitString(par1Str, par2, par3, par4, false);
	}
	
	public int drawString(String par1Str, int par2, int par3, int par4)
	{
		return this.drawString(par1Str, par2, par3, par4, false);
	}
	
	public int drawString(String par1Str, int par2, int par3, int par4, boolean par5)
	{
		resetStyles();
		if(bidiFlag)
		{
			par1Str = bidiReorder(par1Str);
		}
		int var6;
		if(par5)
		{
			var6 = renderString(par1Str, par2 + 1, par3 + 1, par4, true);
			var6 = Math.max(var6, renderString(par1Str, par2, par3, par4, false));
		} else
		{
			var6 = renderString(par1Str, par2, par3, par4, false);
		}
		return var6;
	}
	
	public int drawStringWithShadow(String par1Str, int par2, int par3, int par4)
	{
		return this.drawString(par1Str, par2, par3, par4, true);
	}
	
	@Override public void func_110549_a(ResourceManager par1ResourceManager)
	{
		func_111272_d();
	}
	
	private ResourceLocation func_111271_a(int par1)
	{
		if(field_111274_c[par1] == null)
		{
			field_111274_c[par1] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] { Integer.valueOf(par1) }));
		}
		return field_111274_c[par1];
	}
	
	private void func_111272_d()
	{
		BufferedImage var1;
		try
		{
			var1 = ImageIO.read(Minecraft.getMinecraft().func_110442_L().func_110536_a(field_111273_g).func_110527_b());
		} catch(IOException var17)
		{
			throw new RuntimeException(var17);
		}
		int var2 = var1.getWidth();
		int var3 = var1.getHeight();
		int[] var4 = new int[var2 * var3];
		var1.getRGB(0, 0, var2, var3, var4, 0, var2);
		int var5 = var3 / 16;
		int var6 = var2 / 16;
		byte var7 = 1;
		float var8 = 8.0F / var6;
		int var9 = 0;
		while(var9 < 256)
		{
			int var10 = var9 % 16;
			int var11 = var9 / 16;
			if(var9 == 32)
			{
				charWidth[var9] = 3 + var7;
			}
			int var12 = var6 - 1;
			while(true)
			{
				if(var12 >= 0)
				{
					int var13 = var10 * var6 + var12;
					boolean var14 = true;
					for(int var15 = 0; var15 < var5 && var14; ++var15)
					{
						int var16 = (var11 * var6 + var15) * var2;
						if((var4[var13 + var16] >> 24 & 255) != 0)
						{
							var14 = false;
						}
					}
					if(var14)
					{
						--var12;
						continue;
					}
				}
				++var12;
				charWidth[var9] = (int) (0.5D + var12 * var8) + var7;
				++var9;
				break;
			}
		}
	}
	
	public boolean getBidiFlag()
	{
		return bidiFlag;
	}
	
	public int getCharWidth(char par1)
	{
		if(par1 == 167) return -1;
		else if(par1 == 32) return 4;
		else
		{
			int var2 = ChatAllowedCharacters.allowedCharacters.indexOf(par1);
			if(var2 >= 0 && !unicodeFlag) return charWidth[var2 + 32];
			else if(glyphWidth[par1] != 0)
			{
				int var3 = glyphWidth[par1] >>> 4;
				int var4 = glyphWidth[par1] & 15;
				if(var4 > 7)
				{
					var4 = 15;
					var3 = 0;
				}
				++var4;
				return (var4 - var3) / 2 + 1;
			} else return 0;
		}
	}
	
	public int getStringWidth(String par1Str)
	{
		if(par1Str == null) return 0;
		else
		{
			int var2 = 0;
			boolean var3 = false;
			for(int var4 = 0; var4 < par1Str.length(); ++var4)
			{
				char var5 = par1Str.charAt(var4);
				int var6 = getCharWidth(var5);
				if(var6 < 0 && var4 < par1Str.length() - 1)
				{
					++var4;
					var5 = par1Str.charAt(var4);
					if(var5 != 108 && var5 != 76)
					{
						if(var5 == 114 || var5 == 82)
						{
							var3 = false;
						}
					} else
					{
						var3 = true;
					}
					var6 = 0;
				}
				var2 += var6;
				if(var3)
				{
					++var2;
				}
			}
			return var2;
		}
	}
	
	public boolean getUnicodeFlag()
	{
		return unicodeFlag;
	}
	
	public List listFormattedStringToWidth(String par1Str, int par2)
	{
		return Arrays.asList(wrapFormattedStringToWidth(par1Str, par2).split("\n"));
	}
	
	private void loadGlyphTexture(int par1)
	{
		renderEngine.func_110577_a(func_111271_a(par1));
	}
	
	private void readGlyphSizes()
	{
		try
		{
			InputStream var1 = Minecraft.getMinecraft().func_110442_L().func_110536_a(new ResourceLocation("font/glyph_sizes.bin")).func_110527_b();
			var1.read(glyphWidth);
		} catch(IOException var2)
		{
			throw new RuntimeException(var2);
		}
	}
	
	private float renderCharAtPos(int par1, char par2, boolean par3)
	{
		return par2 == 32 ? 4.0F : par1 > 0 && !unicodeFlag ? renderDefaultChar(par1 + 32, par3) : renderUnicodeChar(par2, par3);
	}
	
	private float renderDefaultChar(int par1, boolean par2)
	{
		float var3 = par1 % 16 * 8;
		float var4 = par1 / 16 * 8;
		float var5 = par2 ? 1.0F : 0.0F;
		renderEngine.func_110577_a(field_111273_g);
		float var6 = charWidth[par1] - 0.01F;
		GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
		GL11.glTexCoord2f(var3 / 128.0F, var4 / 128.0F);
		GL11.glVertex3f(posX + var5, posY, 0.0F);
		GL11.glTexCoord2f(var3 / 128.0F, (var4 + 7.99F) / 128.0F);
		GL11.glVertex3f(posX - var5, posY + 7.99F, 0.0F);
		GL11.glTexCoord2f((var3 + var6 - 1.0F) / 128.0F, var4 / 128.0F);
		GL11.glVertex3f(posX + var6 - 1.0F + var5, posY, 0.0F);
		GL11.glTexCoord2f((var3 + var6 - 1.0F) / 128.0F, (var4 + 7.99F) / 128.0F);
		GL11.glVertex3f(posX + var6 - 1.0F - var5, posY + 7.99F, 0.0F);
		GL11.glEnd();
		return charWidth[par1];
	}
	
	private void renderSplitString(String par1Str, int par2, int par3, int par4, boolean par5)
	{
		List var6 = listFormattedStringToWidth(par1Str, par4);
		for(Iterator var7 = var6.iterator(); var7.hasNext(); par3 += FONT_HEIGHT)
		{
			String var8 = (String) var7.next();
			renderStringAligned(var8, par2, par3, par4, textColor, par5);
		}
	}
	
	private int renderString(String par1Str, int par2, int par3, int par4, boolean par5)
	{
		if(par1Str == null) return 0;
		else
		{
			if((par4 & -67108864) == 0)
			{
				par4 |= -16777216;
			}
			if(par5)
			{
				par4 = (par4 & 16579836) >> 2 | par4 & -16777216;
			}
			red = (par4 >> 16 & 255) / 255.0F;
			blue = (par4 >> 8 & 255) / 255.0F;
			green = (par4 & 255) / 255.0F;
			alpha = (par4 >> 24 & 255) / 255.0F;
			GL11.glColor4f(red, blue, green, alpha);
			posX = par2;
			posY = par3;
			renderStringAtPos(par1Str, par5);
			return (int) posX;
		}
	}
	
	private int renderStringAligned(String par1Str, int par2, int par3, int par4, int par5, boolean par6)
	{
		if(bidiFlag)
		{
			par1Str = bidiReorder(par1Str);
			int var7 = getStringWidth(par1Str);
			par2 = par2 + par4 - var7;
		}
		return renderString(par1Str, par2, par3, par5, par6);
	}
	
	private void renderStringAtPos(String par1Str, boolean par2)
	{
		for(int var3 = 0; var3 < par1Str.length(); ++var3)
		{
			char var4 = par1Str.charAt(var3);
			int var5;
			int var6;
			if(var4 == 167 && var3 + 1 < par1Str.length())
			{
				var5 = "0123456789abcdefklmnor".indexOf(par1Str.toLowerCase().charAt(var3 + 1));
				if(var5 < 16)
				{
					randomStyle = false;
					boldStyle = false;
					strikethroughStyle = false;
					underlineStyle = false;
					italicStyle = false;
					if(var5 < 0 || var5 > 15)
					{
						var5 = 15;
					}
					if(par2)
					{
						var5 += 16;
					}
					var6 = colorCode[var5];
					textColor = var6;
					GL11.glColor4f((var6 >> 16) / 255.0F, (var6 >> 8 & 255) / 255.0F, (var6 & 255) / 255.0F, alpha);
				} else if(var5 == 16)
				{
					randomStyle = true;
				} else if(var5 == 17)
				{
					boldStyle = true;
				} else if(var5 == 18)
				{
					strikethroughStyle = true;
				} else if(var5 == 19)
				{
					underlineStyle = true;
				} else if(var5 == 20)
				{
					italicStyle = true;
				} else if(var5 == 21)
				{
					randomStyle = false;
					boldStyle = false;
					strikethroughStyle = false;
					underlineStyle = false;
					italicStyle = false;
					GL11.glColor4f(red, blue, green, alpha);
				}
				++var3;
			} else
			{
				var5 = ChatAllowedCharacters.allowedCharacters.indexOf(var4);
				if(randomStyle && var5 > 0)
				{
					do
					{
						var6 = fontRandom.nextInt(ChatAllowedCharacters.allowedCharacters.length());
					} while(charWidth[var5 + 32] != charWidth[var6 + 32]);
					var5 = var6;
				}
				float var11 = unicodeFlag ? 0.5F : 1.0F;
				boolean var7 = (var5 <= 0 || unicodeFlag) && par2;
				if(var7)
				{
					posX -= var11;
					posY -= var11;
				}
				float var8 = renderCharAtPos(var5, var4, italicStyle);
				if(var7)
				{
					posX += var11;
					posY += var11;
				}
				if(boldStyle)
				{
					posX += var11;
					if(var7)
					{
						posX -= var11;
						posY -= var11;
					}
					renderCharAtPos(var5, var4, italicStyle);
					posX -= var11;
					if(var7)
					{
						posX += var11;
						posY += var11;
					}
					++var8;
				}
				Tessellator var9;
				if(strikethroughStyle)
				{
					var9 = Tessellator.instance;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					var9.startDrawingQuads();
					var9.addVertex(posX, posY + FONT_HEIGHT / 2, 0.0D);
					var9.addVertex(posX + var8, posY + FONT_HEIGHT / 2, 0.0D);
					var9.addVertex(posX + var8, posY + FONT_HEIGHT / 2 - 1.0F, 0.0D);
					var9.addVertex(posX, posY + FONT_HEIGHT / 2 - 1.0F, 0.0D);
					var9.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				if(underlineStyle)
				{
					var9 = Tessellator.instance;
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					var9.startDrawingQuads();
					int var10 = underlineStyle ? -1 : 0;
					var9.addVertex(posX + var10, posY + FONT_HEIGHT, 0.0D);
					var9.addVertex(posX + var8, posY + FONT_HEIGHT, 0.0D);
					var9.addVertex(posX + var8, posY + FONT_HEIGHT - 1.0F, 0.0D);
					var9.addVertex(posX + var10, posY + FONT_HEIGHT - 1.0F, 0.0D);
					var9.draw();
					GL11.glEnable(GL11.GL_TEXTURE_2D);
				}
				posX += (int) var8;
			}
		}
	}
	
	private float renderUnicodeChar(char par1, boolean par2)
	{
		if(glyphWidth[par1] == 0) return 0.0F;
		else
		{
			int var3 = par1 / 256;
			loadGlyphTexture(var3);
			int var4 = glyphWidth[par1] >>> 4;
			int var5 = glyphWidth[par1] & 15;
			float var6 = var4;
			float var7 = var5 + 1;
			float var8 = par1 % 16 * 16 + var6;
			float var9 = (par1 & 255) / 16 * 16;
			float var10 = var7 - var6 - 0.02F;
			float var11 = par2 ? 1.0F : 0.0F;
			GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
			GL11.glTexCoord2f(var8 / 256.0F, var9 / 256.0F);
			GL11.glVertex3f(posX + var11, posY, 0.0F);
			GL11.glTexCoord2f(var8 / 256.0F, (var9 + 15.98F) / 256.0F);
			GL11.glVertex3f(posX - var11, posY + 7.99F, 0.0F);
			GL11.glTexCoord2f((var8 + var10) / 256.0F, var9 / 256.0F);
			GL11.glVertex3f(posX + var10 / 2.0F + var11, posY, 0.0F);
			GL11.glTexCoord2f((var8 + var10) / 256.0F, (var9 + 15.98F) / 256.0F);
			GL11.glVertex3f(posX + var10 / 2.0F - var11, posY + 7.99F, 0.0F);
			GL11.glEnd();
			return (var7 - var6) / 2.0F + 1.0F;
		}
	}
	
	private void resetStyles()
	{
		randomStyle = false;
		boldStyle = false;
		italicStyle = false;
		underlineStyle = false;
		strikethroughStyle = false;
	}
	
	public void setBidiFlag(boolean par1)
	{
		bidiFlag = par1;
	}
	
	public void setUnicodeFlag(boolean par1)
	{
		unicodeFlag = par1;
	}
	
	private int sizeStringToWidth(String par1Str, int par2)
	{
		int var3 = par1Str.length();
		int var4 = 0;
		int var5 = 0;
		int var6 = -1;
		for(boolean var7 = false; var5 < var3; ++var5)
		{
			char var8 = par1Str.charAt(var5);
			switch(var8)
			{
				case 10:
					--var5;
					break;
				case 167:
					if(var5 < var3 - 1)
					{
						++var5;
						char var9 = par1Str.charAt(var5);
						if(var9 != 108 && var9 != 76)
						{
							if(var9 == 114 || var9 == 82 || isFormatColor(var9))
							{
								var7 = false;
							}
						} else
						{
							var7 = true;
						}
					}
					break;
				case 32:
					var6 = var5;
				default:
					var4 += getCharWidth(var8);
					if(var7)
					{
						++var4;
					}
			}
			if(var8 == 10)
			{
				++var5;
				var6 = var5;
				break;
			}
			if(var4 > par2)
			{
				break;
			}
		}
		return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
	}
	
	public int splitStringWidth(String par1Str, int par2)
	{
		return FONT_HEIGHT * listFormattedStringToWidth(par1Str, par2).size();
	}
	
	private String trimStringNewline(String par1Str)
	{
		while(par1Str != null && par1Str.endsWith("\n"))
		{
			par1Str = par1Str.substring(0, par1Str.length() - 1);
		}
		return par1Str;
	}
	
	public String trimStringToWidth(String par1Str, int par2)
	{
		return this.trimStringToWidth(par1Str, par2, false);
	}
	
	public String trimStringToWidth(String par1Str, int par2, boolean par3)
	{
		StringBuilder var4 = new StringBuilder();
		int var5 = 0;
		int var6 = par3 ? par1Str.length() - 1 : 0;
		int var7 = par3 ? -1 : 1;
		boolean var8 = false;
		boolean var9 = false;
		for(int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7)
		{
			char var11 = par1Str.charAt(var10);
			int var12 = getCharWidth(var11);
			if(var8)
			{
				var8 = false;
				if(var11 != 108 && var11 != 76)
				{
					if(var11 == 114 || var11 == 82)
					{
						var9 = false;
					}
				} else
				{
					var9 = true;
				}
			} else if(var12 < 0)
			{
				var8 = true;
			} else
			{
				var5 += var12;
				if(var9)
				{
					++var5;
				}
			}
			if(var5 > par2)
			{
				break;
			}
			if(par3)
			{
				var4.insert(0, var11);
			} else
			{
				var4.append(var11);
			}
		}
		return var4.toString();
	}
	
	String wrapFormattedStringToWidth(String par1Str, int par2)
	{
		int var3 = sizeStringToWidth(par1Str, par2);
		if(par1Str.length() <= var3) return par1Str;
		else
		{
			String var4 = par1Str.substring(0, var3);
			char var5 = par1Str.charAt(var3);
			boolean var6 = var5 == 32 || var5 == 10;
			String var7 = getFormatFromString(var4) + par1Str.substring(var3 + (var6 ? 1 : 0));
			return var4 + "\n" + wrapFormattedStringToWidth(var7, par2);
		}
	}
	
	private static String getFormatFromString(String par0Str)
	{
		String var1 = "";
		int var2 = -1;
		int var3 = par0Str.length();
		while((var2 = par0Str.indexOf(167, var2 + 1)) != -1)
		{
			if(var2 < var3 - 1)
			{
				char var4 = par0Str.charAt(var2 + 1);
				if(isFormatColor(var4))
				{
					var1 = "\u00a7" + var4;
				} else if(isFormatSpecial(var4))
				{
					var1 = var1 + "\u00a7" + var4;
				}
			}
		}
		return var1;
	}
	
	private static boolean isFormatColor(char par0)
	{
		return par0 >= 48 && par0 <= 57 || par0 >= 97 && par0 <= 102 || par0 >= 65 && par0 <= 70;
	}
	
	private static boolean isFormatSpecial(char par0)
	{
		return par0 >= 107 && par0 <= 111 || par0 >= 75 && par0 <= 79 || par0 == 114 || par0 == 82;
	}
}
