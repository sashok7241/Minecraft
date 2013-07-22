package net.minecraft.src;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class Tessellator
{
	private static boolean convertQuadsToTriangles = false;
	private static boolean tryVBO = false;
	private ByteBuffer byteBuffer;
	private IntBuffer intBuffer;
	private FloatBuffer floatBuffer;
	private ShortBuffer shortBuffer;
	private int[] rawBuffer;
	private int vertexCount = 0;
	private double textureU;
	private double textureV;
	private int brightness;
	private int color;
	private boolean hasColor = false;
	private boolean hasTexture = false;
	private boolean hasBrightness = false;
	private boolean hasNormals = false;
	private int rawBufferIndex = 0;
	private int addedVertices = 0;
	private boolean isColorDisabled = false;
	private int drawMode;
	private double xOffset;
	private double yOffset;
	private double zOffset;
	private int normal;
	public static final Tessellator instance = new Tessellator(2097152);
	private boolean isDrawing = false;
	private boolean useVBO = false;
	private IntBuffer vertexBuffers;
	private int vboIndex = 0;
	private int vboCount = 10;
	private int bufferSize;
	
	private Tessellator(int p_i3191_1_)
	{
		bufferSize = p_i3191_1_;
		byteBuffer = GLAllocation.createDirectByteBuffer(p_i3191_1_ * 4);
		intBuffer = byteBuffer.asIntBuffer();
		floatBuffer = byteBuffer.asFloatBuffer();
		shortBuffer = byteBuffer.asShortBuffer();
		rawBuffer = new int[p_i3191_1_];
		useVBO = tryVBO && GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
		if(useVBO)
		{
			vertexBuffers = GLAllocation.createDirectIntBuffer(vboCount);
			ARBVertexBufferObject.glGenBuffersARB(vertexBuffers);
		}
	}
	
	public void addTranslation(float par1, float par2, float par3)
	{
		xOffset += par1;
		yOffset += par2;
		zOffset += par3;
	}
	
	public void addVertex(double par1, double par3, double par5)
	{
		++addedVertices;
		if(drawMode == 7 && convertQuadsToTriangles && addedVertices % 4 == 0)
		{
			for(int var7 = 0; var7 < 2; ++var7)
			{
				int var8 = 8 * (3 - var7);
				if(hasTexture)
				{
					rawBuffer[rawBufferIndex + 3] = rawBuffer[rawBufferIndex - var8 + 3];
					rawBuffer[rawBufferIndex + 4] = rawBuffer[rawBufferIndex - var8 + 4];
				}
				if(hasBrightness)
				{
					rawBuffer[rawBufferIndex + 7] = rawBuffer[rawBufferIndex - var8 + 7];
				}
				if(hasColor)
				{
					rawBuffer[rawBufferIndex + 5] = rawBuffer[rawBufferIndex - var8 + 5];
				}
				rawBuffer[rawBufferIndex + 0] = rawBuffer[rawBufferIndex - var8 + 0];
				rawBuffer[rawBufferIndex + 1] = rawBuffer[rawBufferIndex - var8 + 1];
				rawBuffer[rawBufferIndex + 2] = rawBuffer[rawBufferIndex - var8 + 2];
				++vertexCount;
				rawBufferIndex += 8;
			}
		}
		if(hasTexture)
		{
			rawBuffer[rawBufferIndex + 3] = Float.floatToRawIntBits((float) textureU);
			rawBuffer[rawBufferIndex + 4] = Float.floatToRawIntBits((float) textureV);
		}
		if(hasBrightness)
		{
			rawBuffer[rawBufferIndex + 7] = brightness;
		}
		if(hasColor)
		{
			rawBuffer[rawBufferIndex + 5] = color;
		}
		if(hasNormals)
		{
			rawBuffer[rawBufferIndex + 6] = normal;
		}
		rawBuffer[rawBufferIndex + 0] = Float.floatToRawIntBits((float) (par1 + xOffset));
		rawBuffer[rawBufferIndex + 1] = Float.floatToRawIntBits((float) (par3 + yOffset));
		rawBuffer[rawBufferIndex + 2] = Float.floatToRawIntBits((float) (par5 + zOffset));
		rawBufferIndex += 8;
		++vertexCount;
		if(vertexCount % 4 == 0 && rawBufferIndex >= bufferSize - 32)
		{
			draw();
			isDrawing = true;
		}
	}
	
	public void addVertexWithUV(double par1, double par3, double par5, double par7, double par9)
	{
		setTextureUV(par7, par9);
		addVertex(par1, par3, par5);
	}
	
	public void disableColor()
	{
		isColorDisabled = true;
	}
	
	public int draw()
	{
		if(!isDrawing) throw new IllegalStateException("Not tesselating!");
		else
		{
			isDrawing = false;
			if(vertexCount > 0)
			{
				intBuffer.clear();
				intBuffer.put(rawBuffer, 0, rawBufferIndex);
				byteBuffer.position(0);
				byteBuffer.limit(rawBufferIndex * 4);
				if(useVBO)
				{
					vboIndex = (vboIndex + 1) % vboCount;
					ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBuffers.get(vboIndex));
					ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, byteBuffer, ARBVertexBufferObject.GL_STREAM_DRAW_ARB);
				}
				if(hasTexture)
				{
					if(useVBO)
					{
						GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 32, 12L);
					} else
					{
						floatBuffer.position(3);
						GL11.glTexCoordPointer(2, 32, floatBuffer);
					}
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}
				if(hasBrightness)
				{
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
					if(useVBO)
					{
						GL11.glTexCoordPointer(2, GL11.GL_SHORT, 32, 28L);
					} else
					{
						shortBuffer.position(14);
						GL11.glTexCoordPointer(2, 32, shortBuffer);
					}
					GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
				}
				if(hasColor)
				{
					if(useVBO)
					{
						GL11.glColorPointer(4, GL11.GL_UNSIGNED_BYTE, 32, 20L);
					} else
					{
						byteBuffer.position(20);
						GL11.glColorPointer(4, true, 32, byteBuffer);
					}
					GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
				}
				if(hasNormals)
				{
					if(useVBO)
					{
						GL11.glNormalPointer(GL11.GL_UNSIGNED_BYTE, 32, 24L);
					} else
					{
						byteBuffer.position(24);
						GL11.glNormalPointer(32, byteBuffer);
					}
					GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
				}
				if(useVBO)
				{
					GL11.glVertexPointer(3, GL11.GL_FLOAT, 32, 0L);
				} else
				{
					floatBuffer.position(0);
					GL11.glVertexPointer(3, 32, floatBuffer);
				}
				GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				if(drawMode == 7 && convertQuadsToTriangles)
				{
					GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
				} else
				{
					GL11.glDrawArrays(drawMode, 0, vertexCount);
				}
				GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
				if(hasTexture)
				{
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
				}
				if(hasBrightness)
				{
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
					GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
					OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
				}
				if(hasColor)
				{
					GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
				}
				if(hasNormals)
				{
					GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
				}
			}
			int var1 = rawBufferIndex * 4;
			reset();
			return var1;
		}
	}
	
	private void reset()
	{
		vertexCount = 0;
		byteBuffer.clear();
		rawBufferIndex = 0;
		addedVertices = 0;
	}
	
	public void setBrightness(int par1)
	{
		hasBrightness = true;
		brightness = par1;
	}
	
	public void setColorOpaque(int par1, int par2, int par3)
	{
		setColorRGBA(par1, par2, par3, 255);
	}
	
	public void setColorOpaque_F(float par1, float par2, float par3)
	{
		setColorOpaque((int) (par1 * 255.0F), (int) (par2 * 255.0F), (int) (par3 * 255.0F));
	}
	
	public void setColorOpaque_I(int par1)
	{
		int var2 = par1 >> 16 & 255;
		int var3 = par1 >> 8 & 255;
		int var4 = par1 & 255;
		setColorOpaque(var2, var3, var4);
	}
	
	public void setColorRGBA(int par1, int par2, int par3, int par4)
	{
		if(!isColorDisabled)
		{
			if(par1 > 255)
			{
				par1 = 255;
			}
			if(par2 > 255)
			{
				par2 = 255;
			}
			if(par3 > 255)
			{
				par3 = 255;
			}
			if(par4 > 255)
			{
				par4 = 255;
			}
			if(par1 < 0)
			{
				par1 = 0;
			}
			if(par2 < 0)
			{
				par2 = 0;
			}
			if(par3 < 0)
			{
				par3 = 0;
			}
			if(par4 < 0)
			{
				par4 = 0;
			}
			hasColor = true;
			if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN)
			{
				color = par4 << 24 | par3 << 16 | par2 << 8 | par1;
			} else
			{
				color = par1 << 24 | par2 << 16 | par3 << 8 | par4;
			}
		}
	}
	
	public void setColorRGBA_F(float par1, float par2, float par3, float par4)
	{
		setColorRGBA((int) (par1 * 255.0F), (int) (par2 * 255.0F), (int) (par3 * 255.0F), (int) (par4 * 255.0F));
	}
	
	public void setColorRGBA_I(int par1, int par2)
	{
		int var3 = par1 >> 16 & 255;
		int var4 = par1 >> 8 & 255;
		int var5 = par1 & 255;
		setColorRGBA(var3, var4, var5, par2);
	}
	
	public void setNormal(float par1, float par2, float par3)
	{
		hasNormals = true;
		byte var4 = (byte) (int) (par1 * 127.0F);
		byte var5 = (byte) (int) (par2 * 127.0F);
		byte var6 = (byte) (int) (par3 * 127.0F);
		normal = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
	}
	
	public void setTextureUV(double par1, double par3)
	{
		hasTexture = true;
		textureU = par1;
		textureV = par3;
	}
	
	public void setTranslation(double par1, double par3, double par5)
	{
		xOffset = par1;
		yOffset = par3;
		zOffset = par5;
	}
	
	public void startDrawing(int par1)
	{
		if(isDrawing) throw new IllegalStateException("Already tesselating!");
		else
		{
			isDrawing = true;
			reset();
			drawMode = par1;
			hasNormals = false;
			hasColor = false;
			hasTexture = false;
			hasBrightness = false;
			isColorDisabled = false;
		}
	}
	
	public void startDrawingQuads()
	{
		startDrawing(7);
	}
}
