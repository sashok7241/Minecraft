package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WorldRenderer
{
	public World worldObj;
	private int glRenderList = -1;
	private static Tessellator tessellator = Tessellator.instance;
	public static int chunksUpdated = 0;
	public int posX;
	public int posY;
	public int posZ;
	public int posXMinus;
	public int posYMinus;
	public int posZMinus;
	public int posXClip;
	public int posYClip;
	public int posZClip;
	public boolean isInFrustum = false;
	public boolean[] skipRenderPass = new boolean[2];
	public int posXPlus;
	public int posYPlus;
	public int posZPlus;
	public boolean needsUpdate;
	public AxisAlignedBB rendererBoundingBox;
	public int chunkIndex;
	public boolean isVisible = true;
	public boolean isWaitingOnOcclusionQuery;
	public int glOcclusionQuery;
	public boolean isChunkLit;
	private boolean isInitialized = false;
	public List tileEntityRenderers = new ArrayList();
	private List tileEntities;
	private int bytesDrawn;
	
	public WorldRenderer(World p_i3196_1_, List p_i3196_2_, int p_i3196_3_, int p_i3196_4_, int p_i3196_5_, int p_i3196_6_)
	{
		worldObj = p_i3196_1_;
		tileEntities = p_i3196_2_;
		glRenderList = p_i3196_6_;
		posX = -999;
		setPosition(p_i3196_3_, p_i3196_4_, p_i3196_5_);
		needsUpdate = false;
	}
	
	public void callOcclusionQueryList()
	{
		GL11.glCallList(glRenderList + 2);
	}
	
	public float distanceToEntitySquared(Entity par1Entity)
	{
		float var2 = (float) (par1Entity.posX - posXPlus);
		float var3 = (float) (par1Entity.posY - posYPlus);
		float var4 = (float) (par1Entity.posZ - posZPlus);
		return var2 * var2 + var3 * var3 + var4 * var4;
	}
	
	public int getGLCallListForPass(int par1)
	{
		return !isInFrustum ? -1 : !skipRenderPass[par1] ? glRenderList + par1 : -1;
	}
	
	public void markDirty()
	{
		needsUpdate = true;
	}
	
	public void setDontDraw()
	{
		for(int var1 = 0; var1 < 2; ++var1)
		{
			skipRenderPass[var1] = true;
		}
		isInFrustum = false;
		isInitialized = false;
	}
	
	public void setPosition(int par1, int par2, int par3)
	{
		if(par1 != posX || par2 != posY || par3 != posZ)
		{
			setDontDraw();
			posX = par1;
			posY = par2;
			posZ = par3;
			posXPlus = par1 + 8;
			posYPlus = par2 + 8;
			posZPlus = par3 + 8;
			posXClip = par1 & 1023;
			posYClip = par2;
			posZClip = par3 & 1023;
			posXMinus = par1 - posXClip;
			posYMinus = par2 - posYClip;
			posZMinus = par3 - posZClip;
			float var4 = 6.0F;
			rendererBoundingBox = AxisAlignedBB.getBoundingBox(par1 - var4, par2 - var4, par3 - var4, par1 + 16 + var4, par2 + 16 + var4, par3 + 16 + var4);
			GL11.glNewList(glRenderList + 2, GL11.GL_COMPILE);
			Render.renderAABB(AxisAlignedBB.getAABBPool().getAABB(posXClip - var4, posYClip - var4, posZClip - var4, posXClip + 16 + var4, posYClip + 16 + var4, posZClip + 16 + var4));
			GL11.glEndList();
			markDirty();
		}
	}
	
	private void setupGLTranslation()
	{
		GL11.glTranslatef((float) posXClip, (float) posYClip, (float) posZClip);
	}
	
	public boolean skipAllRenderPasses()
	{
		return !isInitialized ? false : skipRenderPass[0] && skipRenderPass[1];
	}
	
	public void stopRendering()
	{
		setDontDraw();
		worldObj = null;
	}
	
	public void updateInFrustum(ICamera par1ICamera)
	{
		isInFrustum = par1ICamera.isBoundingBoxInFrustum(rendererBoundingBox);
	}
	
	public void updateRenderer()
	{
		if(needsUpdate)
		{
			needsUpdate = false;
			int var1 = posX;
			int var2 = posY;
			int var3 = posZ;
			int var4 = posX + 16;
			int var5 = posY + 16;
			int var6 = posZ + 16;
			for(int var7 = 0; var7 < 2; ++var7)
			{
				skipRenderPass[var7] = true;
			}
			Chunk.isLit = false;
			HashSet var21 = new HashSet();
			var21.addAll(tileEntityRenderers);
			tileEntityRenderers.clear();
			byte var8 = 1;
			ChunkCache var9 = new ChunkCache(worldObj, var1 - var8, var2 - var8, var3 - var8, var4 + var8, var5 + var8, var6 + var8, var8);
			if(!var9.extendedLevelsInChunkCache())
			{
				++chunksUpdated;
				RenderBlocks var10 = new RenderBlocks(var9);
				bytesDrawn = 0;
				for(int var11 = 0; var11 < 2; ++var11)
				{
					boolean var12 = false;
					boolean var13 = false;
					boolean var14 = false;
					for(int var15 = var2; var15 < var5; ++var15)
					{
						for(int var16 = var3; var16 < var6; ++var16)
						{
							for(int var17 = var1; var17 < var4; ++var17)
							{
								int var18 = var9.getBlockId(var17, var15, var16);
								if(var18 > 0)
								{
									if(!var14)
									{
										var14 = true;
										GL11.glNewList(glRenderList + var11, GL11.GL_COMPILE);
										GL11.glPushMatrix();
										setupGLTranslation();
										float var19 = 1.000001F;
										GL11.glTranslatef(-8.0F, -8.0F, -8.0F);
										GL11.glScalef(var19, var19, var19);
										GL11.glTranslatef(8.0F, 8.0F, 8.0F);
										tessellator.startDrawingQuads();
										tessellator.setTranslation(-posX, -posY, -posZ);
									}
									Block var23 = Block.blocksList[var18];
									if(var23 != null)
									{
										if(var11 == 0 && var23.hasTileEntity())
										{
											TileEntity var20 = var9.getBlockTileEntity(var17, var15, var16);
											if(TileEntityRenderer.instance.hasSpecialRenderer(var20))
											{
												tileEntityRenderers.add(var20);
											}
										}
										int var24 = var23.getRenderBlockPass();
										if(var24 != var11)
										{
											var12 = true;
										} else if(var24 == var11)
										{
											var13 |= var10.renderBlockByRenderType(var23, var17, var15, var16);
										}
									}
								}
							}
						}
					}
					if(var14)
					{
						bytesDrawn += tessellator.draw();
						GL11.glPopMatrix();
						GL11.glEndList();
						tessellator.setTranslation(0.0D, 0.0D, 0.0D);
					} else
					{
						var13 = false;
					}
					if(var13)
					{
						skipRenderPass[var11] = false;
					}
					if(!var12)
					{
						break;
					}
				}
			}
			HashSet var22 = new HashSet();
			var22.addAll(tileEntityRenderers);
			var22.removeAll(var21);
			tileEntities.addAll(var22);
			var21.removeAll(tileEntityRenderers);
			tileEntities.removeAll(var21);
			isChunkLit = Chunk.isLit;
			isInitialized = true;
		}
	}
}
