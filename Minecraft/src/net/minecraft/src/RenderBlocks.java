package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class RenderBlocks
{
	public IBlockAccess blockAccess;
	private Icon overrideBlockTexture = null;
	private boolean flipTexture = false;
	private boolean renderAllFaces = false;
	public static boolean fancyGrass = true;
	public boolean useInventoryTint = true;
	private double renderMinX;
	private double renderMaxX;
	private double renderMinY;
	private double renderMaxY;
	private double renderMinZ;
	private double renderMaxZ;
	private boolean lockBlockBounds = false;
	private boolean partialRenderBounds = false;
	private final Minecraft minecraftRB;
	private int uvRotateEast = 0;
	private int uvRotateWest = 0;
	private int uvRotateSouth = 0;
	private int uvRotateNorth = 0;
	private int uvRotateTop = 0;
	private int uvRotateBottom = 0;
	private boolean enableAO;
	private float aoLightValueScratchXYZNNN;
	private float aoLightValueScratchXYNN;
	private float aoLightValueScratchXYZNNP;
	private float aoLightValueScratchYZNN;
	private float aoLightValueScratchYZNP;
	private float aoLightValueScratchXYZPNN;
	private float aoLightValueScratchXYPN;
	private float aoLightValueScratchXYZPNP;
	private float aoLightValueScratchXYZNPN;
	private float aoLightValueScratchXYNP;
	private float aoLightValueScratchXYZNPP;
	private float aoLightValueScratchYZPN;
	private float aoLightValueScratchXYZPPN;
	private float aoLightValueScratchXYPP;
	private float aoLightValueScratchYZPP;
	private float aoLightValueScratchXYZPPP;
	private float aoLightValueScratchXZNN;
	private float aoLightValueScratchXZPN;
	private float aoLightValueScratchXZNP;
	private float aoLightValueScratchXZPP;
	private int aoBrightnessXYZNNN;
	private int aoBrightnessXYNN;
	private int aoBrightnessXYZNNP;
	private int aoBrightnessYZNN;
	private int aoBrightnessYZNP;
	private int aoBrightnessXYZPNN;
	private int aoBrightnessXYPN;
	private int aoBrightnessXYZPNP;
	private int aoBrightnessXYZNPN;
	private int aoBrightnessXYNP;
	private int aoBrightnessXYZNPP;
	private int aoBrightnessYZPN;
	private int aoBrightnessXYZPPN;
	private int aoBrightnessXYPP;
	private int aoBrightnessYZPP;
	private int aoBrightnessXYZPPP;
	private int aoBrightnessXZNN;
	private int aoBrightnessXZPN;
	private int aoBrightnessXZNP;
	private int aoBrightnessXZPP;
	private int brightnessTopLeft;
	private int brightnessBottomLeft;
	private int brightnessBottomRight;
	private int brightnessTopRight;
	private float colorRedTopLeft;
	private float colorRedBottomLeft;
	private float colorRedBottomRight;
	private float colorRedTopRight;
	private float colorGreenTopLeft;
	private float colorGreenBottomLeft;
	private float colorGreenBottomRight;
	private float colorGreenTopRight;
	private float colorBlueTopLeft;
	private float colorBlueBottomLeft;
	private float colorBlueBottomRight;
	private float colorBlueTopRight;
	
	public RenderBlocks()
	{
		minecraftRB = Minecraft.getMinecraft();
	}
	
	public RenderBlocks(IBlockAccess p_i3187_1_)
	{
		blockAccess = p_i3187_1_;
		minecraftRB = Minecraft.getMinecraft();
	}
	
	public void clearOverrideBlockTexture()
	{
		overrideBlockTexture = null;
	}
	
	public void drawCrossedSquares(Block par1Block, int par2, double par3, double par5, double par7, float par9)
	{
		Tessellator var10 = Tessellator.instance;
		Icon var11 = getBlockIconFromSideAndMetadata(par1Block, 0, par2);
		if(hasOverrideBlockTexture())
		{
			var11 = overrideBlockTexture;
		}
		double var12 = var11.getMinU();
		double var14 = var11.getMinV();
		double var16 = var11.getMaxU();
		double var18 = var11.getMaxV();
		double var20 = 0.45D * par9;
		double var22 = par3 + 0.5D - var20;
		double var24 = par3 + 0.5D + var20;
		double var26 = par7 + 0.5D - var20;
		double var28 = par7 + 0.5D + var20;
		var10.addVertexWithUV(var22, par5 + par9, var26, var12, var14);
		var10.addVertexWithUV(var22, par5 + 0.0D, var26, var12, var18);
		var10.addVertexWithUV(var24, par5 + 0.0D, var28, var16, var18);
		var10.addVertexWithUV(var24, par5 + par9, var28, var16, var14);
		var10.addVertexWithUV(var24, par5 + par9, var28, var12, var14);
		var10.addVertexWithUV(var24, par5 + 0.0D, var28, var12, var18);
		var10.addVertexWithUV(var22, par5 + 0.0D, var26, var16, var18);
		var10.addVertexWithUV(var22, par5 + par9, var26, var16, var14);
		var10.addVertexWithUV(var22, par5 + par9, var28, var12, var14);
		var10.addVertexWithUV(var22, par5 + 0.0D, var28, var12, var18);
		var10.addVertexWithUV(var24, par5 + 0.0D, var26, var16, var18);
		var10.addVertexWithUV(var24, par5 + par9, var26, var16, var14);
		var10.addVertexWithUV(var24, par5 + par9, var26, var12, var14);
		var10.addVertexWithUV(var24, par5 + 0.0D, var26, var12, var18);
		var10.addVertexWithUV(var22, par5 + 0.0D, var28, var16, var18);
		var10.addVertexWithUV(var22, par5 + par9, var28, var16, var14);
	}
	
	private int getAoBrightness(int par1, int par2, int par3, int par4)
	{
		if(par1 == 0)
		{
			par1 = par4;
		}
		if(par2 == 0)
		{
			par2 = par4;
		}
		if(par3 == 0)
		{
			par3 = par4;
		}
		return par1 + par2 + par3 + par4 >> 2 & 16711935;
	}
	
	public Icon getBlockIcon(Block par1Block)
	{
		return getIconSafe(par1Block.getBlockTextureFromSide(1));
	}
	
	public Icon getBlockIcon(Block par1Block, IBlockAccess par2IBlockAccess, int par3, int par4, int par5, int par6)
	{
		return getIconSafe(par1Block.getBlockTexture(par2IBlockAccess, par3, par4, par5, par6));
	}
	
	public Icon getBlockIconFromSide(Block par1Block, int par2)
	{
		return getIconSafe(par1Block.getBlockTextureFromSide(par2));
	}
	
	public Icon getBlockIconFromSideAndMetadata(Block par1Block, int par2, int par3)
	{
		return getIconSafe(par1Block.getIcon(par2, par3));
	}
	
	private float getFluidHeight(int par1, int par2, int par3, Material par4Material)
	{
		int var5 = 0;
		float var6 = 0.0F;
		for(int var7 = 0; var7 < 4; ++var7)
		{
			int var8 = par1 - (var7 & 1);
			int var10 = par3 - (var7 >> 1 & 1);
			if(blockAccess.getBlockMaterial(var8, par2 + 1, var10) == par4Material) return 1.0F;
			Material var11 = blockAccess.getBlockMaterial(var8, par2, var10);
			if(var11 == par4Material)
			{
				int var12 = blockAccess.getBlockMetadata(var8, par2, var10);
				if(var12 >= 8 || var12 == 0)
				{
					var6 += BlockFluid.getFluidHeightPercent(var12) * 10.0F;
					var5 += 10;
				}
				var6 += BlockFluid.getFluidHeightPercent(var12);
				++var5;
			} else if(!var11.isSolid())
			{
				++var6;
				++var5;
			}
		}
		return 1.0F - var6 / var5;
	}
	
	public Icon getIconSafe(Icon par1Icon)
	{
		return par1Icon == null ? minecraftRB.renderEngine.getMissingIcon(0) : par1Icon;
	}
	
	public boolean hasOverrideBlockTexture()
	{
		return overrideBlockTexture != null;
	}
	
	private int mixAoBrightness(int par1, int par2, int par3, int par4, double par5, double par7, double par9, double par11)
	{
		int var13 = (int) ((par1 >> 16 & 255) * par5 + (par2 >> 16 & 255) * par7 + (par3 >> 16 & 255) * par9 + (par4 >> 16 & 255) * par11) & 255;
		int var14 = (int) ((par1 & 255) * par5 + (par2 & 255) * par7 + (par3 & 255) * par9 + (par4 & 255) * par11) & 255;
		return var13 << 16 | var14;
	}
	
	public void overrideBlockBounds(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		renderMinX = par1;
		renderMaxX = par7;
		renderMinY = par3;
		renderMaxY = par9;
		renderMinZ = par5;
		renderMaxZ = par11;
		lockBlockBounds = true;
		partialRenderBounds = minecraftRB.gameSettings.ambientOcclusion >= 2 && (renderMinX > 0.0D || renderMaxX < 1.0D || renderMinY > 0.0D || renderMaxY < 1.0D || renderMinZ > 0.0D || renderMaxZ < 1.0D);
	}
	
	public void renderBlockAllFaces(Block par1Block, int par2, int par3, int par4)
	{
		renderAllFaces = true;
		renderBlockByRenderType(par1Block, par2, par3, par4);
		renderAllFaces = false;
	}
	
	private boolean renderBlockAnvil(BlockAnvil par1BlockAnvil, int par2, int par3, int par4)
	{
		return renderBlockAnvilMetadata(par1BlockAnvil, par2, par3, par4, blockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	public boolean renderBlockAnvilMetadata(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5)
	{
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1BlockAnvil.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var7 = 1.0F;
		int var8 = par1BlockAnvil.colorMultiplier(blockAccess, par2, par3, par4);
		float var9 = (var8 >> 16 & 255) / 255.0F;
		float var10 = (var8 >> 8 & 255) / 255.0F;
		float var11 = (var8 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
			float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
			float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
			var9 = var12;
			var10 = var13;
			var11 = var14;
		}
		var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
		return renderBlockAnvilOrient(par1BlockAnvil, par2, par3, par4, par5, false);
	}
	
	private boolean renderBlockAnvilOrient(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5, boolean par6)
	{
		int var7 = par6 ? 0 : par5 & 3;
		boolean var8 = false;
		float var9 = 0.0F;
		switch(var7)
		{
			case 0:
				uvRotateSouth = 2;
				uvRotateNorth = 1;
				uvRotateTop = 3;
				uvRotateBottom = 3;
				break;
			case 1:
				uvRotateEast = 1;
				uvRotateWest = 2;
				uvRotateTop = 2;
				uvRotateBottom = 1;
				var8 = true;
				break;
			case 2:
				uvRotateSouth = 1;
				uvRotateNorth = 2;
				break;
			case 3:
				uvRotateEast = 2;
				uvRotateWest = 1;
				uvRotateTop = 1;
				uvRotateBottom = 2;
				var8 = true;
		}
		var9 = renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 0, var9, 0.75F, 0.25F, 0.75F, var8, par6, par5);
		var9 = renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 1, var9, 0.5F, 0.0625F, 0.625F, var8, par6, par5);
		var9 = renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 2, var9, 0.25F, 0.3125F, 0.5F, var8, par6, par5);
		renderBlockAnvilRotate(par1BlockAnvil, par2, par3, par4, 3, var9, 0.625F, 0.375F, 1.0F, var8, par6, par5);
		setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		uvRotateEast = 0;
		uvRotateWest = 0;
		uvRotateSouth = 0;
		uvRotateNorth = 0;
		uvRotateTop = 0;
		uvRotateBottom = 0;
		return true;
	}
	
	private float renderBlockAnvilRotate(BlockAnvil par1BlockAnvil, int par2, int par3, int par4, int par5, float par6, float par7, float par8, float par9, boolean par10, boolean par11, int par12)
	{
		if(par10)
		{
			float var13 = par7;
			par7 = par9;
			par9 = var13;
		}
		par7 /= 2.0F;
		par9 /= 2.0F;
		par1BlockAnvil.field_82521_b = par5;
		setRenderBounds(0.5F - par7, par6, 0.5F - par9, 0.5F + par7, par6 + par8, 0.5F + par9);
		if(par11)
		{
			Tessellator var14 = Tessellator.instance;
			var14.startDrawingQuads();
			var14.setNormal(0.0F, -1.0F, 0.0F);
			renderFaceYNeg(par1BlockAnvil, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockAnvil, 0, par12));
			var14.draw();
			var14.startDrawingQuads();
			var14.setNormal(0.0F, 1.0F, 0.0F);
			renderFaceYPos(par1BlockAnvil, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockAnvil, 1, par12));
			var14.draw();
			var14.startDrawingQuads();
			var14.setNormal(0.0F, 0.0F, -1.0F);
			renderFaceZNeg(par1BlockAnvil, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockAnvil, 2, par12));
			var14.draw();
			var14.startDrawingQuads();
			var14.setNormal(0.0F, 0.0F, 1.0F);
			renderFaceZPos(par1BlockAnvil, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockAnvil, 3, par12));
			var14.draw();
			var14.startDrawingQuads();
			var14.setNormal(-1.0F, 0.0F, 0.0F);
			renderFaceXNeg(par1BlockAnvil, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockAnvil, 4, par12));
			var14.draw();
			var14.startDrawingQuads();
			var14.setNormal(1.0F, 0.0F, 0.0F);
			renderFaceXPos(par1BlockAnvil, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockAnvil, 5, par12));
			var14.draw();
		} else
		{
			renderStandardBlock(par1BlockAnvil, par2, par3, par4);
		}
		return par6 + par8;
	}
	
	public void renderBlockAsItem(Block par1Block, int par2, float par3)
	{
		Tessellator var4 = Tessellator.instance;
		boolean var5 = par1Block.blockID == Block.grass.blockID;
		if(par1Block == Block.dispenser || par1Block == Block.dropper || par1Block == Block.furnaceIdle)
		{
			par2 = 3;
		}
		int var6;
		float var7;
		float var8;
		float var9;
		if(useInventoryTint)
		{
			var6 = par1Block.getRenderColor(par2);
			if(var5)
			{
				var6 = 16777215;
			}
			var7 = (var6 >> 16 & 255) / 255.0F;
			var8 = (var6 >> 8 & 255) / 255.0F;
			var9 = (var6 & 255) / 255.0F;
			GL11.glColor4f(var7 * par3, var8 * par3, var9 * par3, 1.0F);
		}
		var6 = par1Block.getRenderType();
		setRenderBoundsFromBlock(par1Block);
		int var14;
		if(var6 != 0 && var6 != 31 && var6 != 39 && var6 != 16 && var6 != 26)
		{
			if(var6 == 1)
			{
				var4.startDrawingQuads();
				var4.setNormal(0.0F, -1.0F, 0.0F);
				drawCrossedSquares(par1Block, par2, -0.5D, -0.5D, -0.5D, 1.0F);
				var4.draw();
			} else if(var6 == 19)
			{
				var4.startDrawingQuads();
				var4.setNormal(0.0F, -1.0F, 0.0F);
				par1Block.setBlockBoundsForItemRender();
				renderBlockStemSmall(par1Block, par2, renderMaxY, -0.5D, -0.5D, -0.5D);
				var4.draw();
			} else if(var6 == 23)
			{
				var4.startDrawingQuads();
				var4.setNormal(0.0F, -1.0F, 0.0F);
				par1Block.setBlockBoundsForItemRender();
				var4.draw();
			} else if(var6 == 13)
			{
				par1Block.setBlockBoundsForItemRender();
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				var7 = 0.0625F;
				var4.startDrawingQuads();
				var4.setNormal(0.0F, -1.0F, 0.0F);
				renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 0));
				var4.draw();
				var4.startDrawingQuads();
				var4.setNormal(0.0F, 1.0F, 0.0F);
				renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 1));
				var4.draw();
				var4.startDrawingQuads();
				var4.setNormal(0.0F, 0.0F, -1.0F);
				var4.addTranslation(0.0F, 0.0F, var7);
				renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 2));
				var4.addTranslation(0.0F, 0.0F, -var7);
				var4.draw();
				var4.startDrawingQuads();
				var4.setNormal(0.0F, 0.0F, 1.0F);
				var4.addTranslation(0.0F, 0.0F, -var7);
				renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 3));
				var4.addTranslation(0.0F, 0.0F, var7);
				var4.draw();
				var4.startDrawingQuads();
				var4.setNormal(-1.0F, 0.0F, 0.0F);
				var4.addTranslation(var7, 0.0F, 0.0F);
				renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 4));
				var4.addTranslation(-var7, 0.0F, 0.0F);
				var4.draw();
				var4.startDrawingQuads();
				var4.setNormal(1.0F, 0.0F, 0.0F);
				var4.addTranslation(-var7, 0.0F, 0.0F);
				renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 5));
				var4.addTranslation(var7, 0.0F, 0.0F);
				var4.draw();
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			} else if(var6 == 22)
			{
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				ChestItemRenderHelper.instance.renderChest(par1Block, par2, par3);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			} else if(var6 == 6)
			{
				var4.startDrawingQuads();
				var4.setNormal(0.0F, -1.0F, 0.0F);
				renderBlockCropsImpl(par1Block, par2, -0.5D, -0.5D, -0.5D);
				var4.draw();
			} else if(var6 == 2)
			{
				var4.startDrawingQuads();
				var4.setNormal(0.0F, -1.0F, 0.0F);
				renderTorchAtAngle(par1Block, -0.5D, -0.5D, -0.5D, 0.0D, 0.0D, 0);
				var4.draw();
			} else if(var6 == 10)
			{
				for(var14 = 0; var14 < 2; ++var14)
				{
					if(var14 == 0)
					{
						setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
					}
					if(var14 == 1)
					{
						setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
					}
					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var4.startDrawingQuads();
					var4.setNormal(0.0F, -1.0F, 0.0F);
					renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 0));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 1.0F, 0.0F);
					renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 1));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, -1.0F);
					renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, 1.0F);
					renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 3));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(-1.0F, 0.0F, 0.0F);
					renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 4));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(1.0F, 0.0F, 0.0F);
					renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 5));
					var4.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}
			} else if(var6 == 27)
			{
				var14 = 0;
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				var4.startDrawingQuads();
				for(int var15 = 0; var15 < 8; ++var15)
				{
					byte var16 = 0;
					byte var17 = 1;
					if(var15 == 0)
					{
						var16 = 2;
					}
					if(var15 == 1)
					{
						var16 = 3;
					}
					if(var15 == 2)
					{
						var16 = 4;
					}
					if(var15 == 3)
					{
						var16 = 5;
						var17 = 2;
					}
					if(var15 == 4)
					{
						var16 = 6;
						var17 = 3;
					}
					if(var15 == 5)
					{
						var16 = 7;
						var17 = 5;
					}
					if(var15 == 6)
					{
						var16 = 6;
						var17 = 2;
					}
					if(var15 == 7)
					{
						var16 = 3;
					}
					float var11 = var16 / 16.0F;
					float var12 = 1.0F - var14 / 16.0F;
					float var13 = 1.0F - (var14 + var17) / 16.0F;
					var14 += var17;
					setRenderBounds(0.5F - var11, var13, 0.5F - var11, 0.5F + var11, var12, 0.5F + var11);
					var4.setNormal(0.0F, -1.0F, 0.0F);
					renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 0));
					var4.setNormal(0.0F, 1.0F, 0.0F);
					renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 1));
					var4.setNormal(0.0F, 0.0F, -1.0F);
					renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 2));
					var4.setNormal(0.0F, 0.0F, 1.0F);
					renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 3));
					var4.setNormal(-1.0F, 0.0F, 0.0F);
					renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 4));
					var4.setNormal(1.0F, 0.0F, 0.0F);
					renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 5));
				}
				var4.draw();
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			} else if(var6 == 11)
			{
				for(var14 = 0; var14 < 4; ++var14)
				{
					var8 = 0.125F;
					if(var14 == 0)
					{
						setRenderBounds(0.5F - var8, 0.0D, 0.0D, 0.5F + var8, 1.0D, var8 * 2.0F);
					}
					if(var14 == 1)
					{
						setRenderBounds(0.5F - var8, 0.0D, 1.0F - var8 * 2.0F, 0.5F + var8, 1.0D, 1.0D);
					}
					var8 = 0.0625F;
					if(var14 == 2)
					{
						setRenderBounds(0.5F - var8, 1.0F - var8 * 3.0F, -var8 * 2.0F, 0.5F + var8, 1.0F - var8, 1.0F + var8 * 2.0F);
					}
					if(var14 == 3)
					{
						setRenderBounds(0.5F - var8, 0.5F - var8 * 3.0F, -var8 * 2.0F, 0.5F + var8, 0.5F - var8, 1.0F + var8 * 2.0F);
					}
					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var4.startDrawingQuads();
					var4.setNormal(0.0F, -1.0F, 0.0F);
					renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 0));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 1.0F, 0.0F);
					renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 1));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, -1.0F);
					renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, 1.0F);
					renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 3));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(-1.0F, 0.0F, 0.0F);
					renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 4));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(1.0F, 0.0F, 0.0F);
					renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 5));
					var4.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			} else if(var6 == 21)
			{
				for(var14 = 0; var14 < 3; ++var14)
				{
					var8 = 0.0625F;
					if(var14 == 0)
					{
						setRenderBounds(0.5F - var8, 0.30000001192092896D, 0.0D, 0.5F + var8, 1.0D, var8 * 2.0F);
					}
					if(var14 == 1)
					{
						setRenderBounds(0.5F - var8, 0.30000001192092896D, 1.0F - var8 * 2.0F, 0.5F + var8, 1.0D, 1.0D);
					}
					var8 = 0.0625F;
					if(var14 == 2)
					{
						setRenderBounds(0.5F - var8, 0.5D, 0.0D, 0.5F + var8, 1.0F - var8, 1.0D);
					}
					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var4.startDrawingQuads();
					var4.setNormal(0.0F, -1.0F, 0.0F);
					renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 0));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 1.0F, 0.0F);
					renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 1));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, -1.0F);
					renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, 1.0F);
					renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 3));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(-1.0F, 0.0F, 0.0F);
					renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 4));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(1.0F, 0.0F, 0.0F);
					renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSide(par1Block, 5));
					var4.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}
			} else if(var6 == 32)
			{
				for(var14 = 0; var14 < 2; ++var14)
				{
					if(var14 == 0)
					{
						setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
					}
					if(var14 == 1)
					{
						setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
					}
					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var4.startDrawingQuads();
					var4.setNormal(0.0F, -1.0F, 0.0F);
					renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 0, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 1.0F, 0.0F);
					renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 1, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, -1.0F);
					renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 2, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, 1.0F);
					renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 3, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(-1.0F, 0.0F, 0.0F);
					renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 4, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(1.0F, 0.0F, 0.0F);
					renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 5, par2));
					var4.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			} else if(var6 == 35)
			{
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				renderBlockAnvilOrient((BlockAnvil) par1Block, 0, 0, 0, par2, true);
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			} else if(var6 == 34)
			{
				for(var14 = 0; var14 < 3; ++var14)
				{
					if(var14 == 0)
					{
						setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
						setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
					} else if(var14 == 1)
					{
						setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
						setOverrideBlockTexture(Block.beacon.getBeaconIcon());
					} else if(var14 == 2)
					{
						setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
						setOverrideBlockTexture(this.getBlockIcon(Block.glass));
					}
					GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
					var4.startDrawingQuads();
					var4.setNormal(0.0F, -1.0F, 0.0F);
					renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 0, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 1.0F, 0.0F);
					renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 1, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, -1.0F);
					renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 2, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(0.0F, 0.0F, 1.0F);
					renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 3, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(-1.0F, 0.0F, 0.0F);
					renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 4, par2));
					var4.draw();
					var4.startDrawingQuads();
					var4.setNormal(1.0F, 0.0F, 0.0F);
					renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 5, par2));
					var4.draw();
					GL11.glTranslatef(0.5F, 0.5F, 0.5F);
				}
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
				clearOverrideBlockTexture();
			} else if(var6 == 38)
			{
				GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
				renderBlockHopperMetadata((BlockHopper) par1Block, 0, 0, 0, 0, true);
				GL11.glTranslatef(0.5F, 0.5F, 0.5F);
			}
		} else
		{
			if(var6 == 16)
			{
				par2 = 1;
			}
			par1Block.setBlockBoundsForItemRender();
			setRenderBoundsFromBlock(par1Block);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			var4.startDrawingQuads();
			var4.setNormal(0.0F, -1.0F, 0.0F);
			renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 0, par2));
			var4.draw();
			if(var5 && useInventoryTint)
			{
				var14 = par1Block.getRenderColor(par2);
				var8 = (var14 >> 16 & 255) / 255.0F;
				var9 = (var14 >> 8 & 255) / 255.0F;
				float var10 = (var14 & 255) / 255.0F;
				GL11.glColor4f(var8 * par3, var9 * par3, var10 * par3, 1.0F);
			}
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 1.0F, 0.0F);
			renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 1, par2));
			var4.draw();
			if(var5 && useInventoryTint)
			{
				GL11.glColor4f(par3, par3, par3, 1.0F);
			}
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, -1.0F);
			renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 2, par2));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(0.0F, 0.0F, 1.0F);
			renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 3, par2));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(-1.0F, 0.0F, 0.0F);
			renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 4, par2));
			var4.draw();
			var4.startDrawingQuads();
			var4.setNormal(1.0F, 0.0F, 0.0F);
			renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1Block, 5, par2));
			var4.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}
	
	private boolean renderBlockBeacon(BlockBeacon par1BlockBeacon, int par2, int par3, int par4)
	{
		float var5 = 0.1875F;
		setOverrideBlockTexture(this.getBlockIcon(Block.obsidian));
		setRenderBounds(0.125D, 0.0062500000931322575D, 0.125D, 0.875D, var5, 0.875D);
		renderStandardBlock(par1BlockBeacon, par2, par3, par4);
		setOverrideBlockTexture(this.getBlockIcon(Block.glass));
		setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		renderStandardBlock(par1BlockBeacon, par2, par3, par4);
		setOverrideBlockTexture(par1BlockBeacon.getBeaconIcon());
		setRenderBounds(0.1875D, var5, 0.1875D, 0.8125D, 0.875D, 0.8125D);
		renderStandardBlock(par1BlockBeacon, par2, par3, par4);
		clearOverrideBlockTexture();
		return true;
	}
	
	private boolean renderBlockBed(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var7 = BlockDirectional.getDirection(var6);
		boolean var8 = BlockBed.isBlockHeadOfBed(var6);
		float var9 = 0.5F;
		float var10 = 1.0F;
		float var11 = 0.8F;
		float var12 = 0.6F;
		int var25 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4);
		var5.setBrightness(var25);
		var5.setColorOpaque_F(var9, var9, var9);
		Icon var27 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 0);
		double var28 = var27.getMinU();
		double var30 = var27.getMaxU();
		double var32 = var27.getMinV();
		double var34 = var27.getMaxV();
		double var36 = par2 + renderMinX;
		double var38 = par2 + renderMaxX;
		double var40 = par3 + renderMinY + 0.1875D;
		double var42 = par4 + renderMinZ;
		double var44 = par4 + renderMaxZ;
		var5.addVertexWithUV(var36, var40, var44, var28, var34);
		var5.addVertexWithUV(var36, var40, var42, var28, var32);
		var5.addVertexWithUV(var38, var40, var42, var30, var32);
		var5.addVertexWithUV(var38, var40, var44, var30, var34);
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4));
		var5.setColorOpaque_F(var10, var10, var10);
		var27 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 1);
		var28 = var27.getMinU();
		var30 = var27.getMaxU();
		var32 = var27.getMinV();
		var34 = var27.getMaxV();
		var36 = var28;
		var38 = var30;
		var40 = var32;
		var42 = var32;
		var44 = var28;
		double var46 = var30;
		double var48 = var34;
		double var50 = var34;
		if(var7 == 0)
		{
			var38 = var28;
			var40 = var34;
			var44 = var30;
			var50 = var32;
		} else if(var7 == 2)
		{
			var36 = var30;
			var42 = var34;
			var46 = var28;
			var48 = var32;
		} else if(var7 == 3)
		{
			var36 = var30;
			var42 = var34;
			var46 = var28;
			var48 = var32;
			var38 = var28;
			var40 = var34;
			var44 = var30;
			var50 = var32;
		}
		double var52 = par2 + renderMinX;
		double var54 = par2 + renderMaxX;
		double var56 = par3 + renderMaxY;
		double var58 = par4 + renderMinZ;
		double var60 = par4 + renderMaxZ;
		var5.addVertexWithUV(var54, var56, var60, var44, var48);
		var5.addVertexWithUV(var54, var56, var58, var36, var40);
		var5.addVertexWithUV(var52, var56, var58, var38, var42);
		var5.addVertexWithUV(var52, var56, var60, var46, var50);
		int var62 = Direction.directionToFacing[var7];
		if(var8)
		{
			var62 = Direction.directionToFacing[Direction.rotateOpposite[var7]];
		}
		byte var63 = 4;
		switch(var7)
		{
			case 0:
				var63 = 5;
				break;
			case 1:
				var63 = 3;
			case 2:
			default:
				break;
			case 3:
				var63 = 2;
		}
		if(var62 != 2 && (renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 - 1, 2)))
		{
			var5.setBrightness(renderMinZ > 0.0D ? var25 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1));
			var5.setColorOpaque_F(var11, var11, var11);
			flipTexture = var63 == 2;
			renderFaceZNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 2));
		}
		if(var62 != 3 && (renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 + 1, 3)))
		{
			var5.setBrightness(renderMaxZ < 1.0D ? var25 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1));
			var5.setColorOpaque_F(var11, var11, var11);
			flipTexture = var63 == 3;
			renderFaceZPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3));
		}
		if(var62 != 4 && (renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 - 1, par3, par4, 4)))
		{
			var5.setBrightness(renderMinZ > 0.0D ? var25 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4));
			var5.setColorOpaque_F(var12, var12, var12);
			flipTexture = var63 == 4;
			renderFaceXNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 4));
		}
		if(var62 != 5 && (renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 + 1, par3, par4, 5)))
		{
			var5.setBrightness(renderMaxZ < 1.0D ? var25 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4));
			var5.setColorOpaque_F(var12, var12, var12);
			flipTexture = var63 == 5;
			renderFaceXPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 5));
		}
		flipTexture = false;
		return true;
	}
	
	private boolean renderBlockBrewingStand(BlockBrewingStand par1BlockBrewingStand, int par2, int par3, int par4)
	{
		setRenderBounds(0.4375D, 0.0D, 0.4375D, 0.5625D, 0.875D, 0.5625D);
		renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
		setOverrideBlockTexture(par1BlockBrewingStand.getBrewingStandIcon());
		setRenderBounds(0.5625D, 0.0D, 0.3125D, 0.9375D, 0.125D, 0.6875D);
		renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
		setRenderBounds(0.125D, 0.0D, 0.0625D, 0.5D, 0.125D, 0.4375D);
		renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
		setRenderBounds(0.125D, 0.0D, 0.5625D, 0.5D, 0.125D, 0.9375D);
		renderStandardBlock(par1BlockBrewingStand, par2, par3, par4);
		clearOverrideBlockTexture();
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockBrewingStand.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = par1BlockBrewingStand.colorMultiplier(blockAccess, par2, par3, par4);
		float var8 = (var7 >> 16 & 255) / 255.0F;
		float var9 = (var7 >> 8 & 255) / 255.0F;
		float var10 = (var7 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
			float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
			float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
			var8 = var11;
			var9 = var12;
			var10 = var13;
		}
		var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		Icon var32 = getBlockIconFromSideAndMetadata(par1BlockBrewingStand, 0, 0);
		if(hasOverrideBlockTexture())
		{
			var32 = overrideBlockTexture;
		}
		double var33 = var32.getMinV();
		double var14 = var32.getMaxV();
		int var16 = blockAccess.getBlockMetadata(par2, par3, par4);
		for(int var17 = 0; var17 < 3; ++var17)
		{
			double var18 = var17 * Math.PI * 2.0D / 3.0D + Math.PI / 2D;
			double var20 = var32.getInterpolatedU(8.0D);
			double var22 = var32.getMaxU();
			if((var16 & 1 << var17) != 0)
			{
				var22 = var32.getMinU();
			}
			double var24 = par2 + 0.5D;
			double var26 = par2 + 0.5D + Math.sin(var18) * 8.0D / 16.0D;
			double var28 = par4 + 0.5D;
			double var30 = par4 + 0.5D + Math.cos(var18) * 8.0D / 16.0D;
			var5.addVertexWithUV(var24, par3 + 1, var28, var20, var33);
			var5.addVertexWithUV(var24, par3 + 0, var28, var20, var14);
			var5.addVertexWithUV(var26, par3 + 0, var30, var22, var14);
			var5.addVertexWithUV(var26, par3 + 1, var30, var22, var33);
			var5.addVertexWithUV(var26, par3 + 1, var30, var22, var33);
			var5.addVertexWithUV(var26, par3 + 0, var30, var22, var14);
			var5.addVertexWithUV(var24, par3 + 0, var28, var20, var14);
			var5.addVertexWithUV(var24, par3 + 1, var28, var20, var33);
		}
		par1BlockBrewingStand.setBlockBoundsForItemRender();
		return true;
	}
	
	public boolean renderBlockByRenderType(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = par1Block.getRenderType();
		if(var5 == -1) return false;
		else
		{
			par1Block.setBlockBoundsBasedOnState(blockAccess, par2, par3, par4);
			setRenderBoundsFromBlock(par1Block);
			return var5 == 0 ? renderStandardBlock(par1Block, par2, par3, par4) : var5 == 4 ? renderBlockFluids(par1Block, par2, par3, par4) : var5 == 31 ? renderBlockLog(par1Block, par2, par3, par4) : var5 == 1 ? renderCrossedSquares(par1Block, par2, par3, par4) : var5 == 2 ? renderBlockTorch(par1Block, par2, par3, par4) : var5 == 20 ? renderBlockVine(par1Block, par2, par3, par4) : var5 == 11 ? renderBlockFence((BlockFence) par1Block, par2, par3, par4) : var5 == 39 ? renderBlockQuartz(par1Block, par2, par3, par4) : var5 == 5 ? renderBlockRedstoneWire(par1Block, par2, par3, par4) : var5 == 13 ? renderBlockCactus(par1Block, par2, par3, par4) : var5 == 9 ? renderBlockMinecartTrack((BlockRailBase) par1Block, par2, par3, par4) : var5 == 19 ? renderBlockStem(par1Block, par2, par3, par4) : var5 == 23 ? renderBlockLilyPad(par1Block, par2, par3, par4) : var5 == 6 ? renderBlockCrops(par1Block, par2, par3, par4) : var5 == 3 ? renderBlockFire((BlockFire) par1Block, par2, par3, par4) : var5 == 8 ? renderBlockLadder(par1Block, par2, par3, par4) : var5 == 7 ? renderBlockDoor(par1Block, par2, par3, par4) : var5 == 10 ? renderBlockStairs((BlockStairs) par1Block, par2, par3, par4) : var5 == 27 ? renderBlockDragonEgg((BlockDragonEgg) par1Block, par2, par3, par4) : var5 == 32 ? renderBlockWall((BlockWall) par1Block, par2, par3, par4) : var5 == 12 ? renderBlockLever(par1Block, par2, par3, par4) : var5 == 29 ? renderBlockTripWireSource(par1Block, par2, par3, par4) : var5 == 30 ? renderBlockTripWire(par1Block, par2, par3, par4) : var5 == 14 ? renderBlockBed(par1Block, par2, par3, par4) : var5 == 15 ? renderBlockRepeater((BlockRedstoneRepeater) par1Block, par2, par3, par4) : var5 == 36 ? renderBlockRedstoneLogic((BlockRedstoneLogic) par1Block, par2, par3, par4) : var5 == 37 ? renderBlockComparator((BlockComparator) par1Block, par2, par3, par4) : var5 == 16 ? renderPistonBase(par1Block, par2, par3, par4, false) : var5 == 17 ? renderPistonExtension(par1Block, par2, par3, par4, true) : var5 == 18 ? renderBlockPane((BlockPane) par1Block, par2, par3, par4) : var5 == 21 ? renderBlockFenceGate((BlockFenceGate) par1Block, par2, par3, par4) : var5 == 24 ? renderBlockCauldron((BlockCauldron) par1Block, par2, par3, par4) : var5 == 33 ? renderBlockFlowerpot((BlockFlowerPot) par1Block, par2, par3, par4) : var5 == 35 ? renderBlockAnvil((BlockAnvil) par1Block, par2, par3, par4) : var5 == 25 ? renderBlockBrewingStand((BlockBrewingStand) par1Block, par2, par3, par4) : var5 == 26 ? renderBlockEndPortalFrame((BlockEndPortalFrame) par1Block, par2, par3, par4) : var5 == 28 ? renderBlockCocoa((BlockCocoa) par1Block, par2, par3, par4) : var5 == 34 ? renderBlockBeacon((BlockBeacon) par1Block, par2, par3, par4) : var5 == 38 ? renderBlockHopper((BlockHopper) par1Block, par2, par3, par4) : false;
		}
	}
	
	public boolean renderBlockCactus(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = par1Block.colorMultiplier(blockAccess, par2, par3, par4);
		float var6 = (var5 >> 16 & 255) / 255.0F;
		float var7 = (var5 >> 8 & 255) / 255.0F;
		float var8 = (var5 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
			float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
			float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
			var6 = var9;
			var7 = var10;
			var8 = var11;
		}
		return renderBlockCactusImpl(par1Block, par2, par3, par4, var6, var7, var8);
	}
	
	public boolean renderBlockCactusImpl(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		Tessellator var8 = Tessellator.instance;
		boolean var9 = false;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var10 * par5;
		float var15 = var11 * par5;
		float var16 = var12 * par5;
		float var17 = var13 * par5;
		float var18 = var10 * par6;
		float var19 = var11 * par6;
		float var20 = var12 * par6;
		float var21 = var13 * par6;
		float var22 = var10 * par7;
		float var23 = var11 * par7;
		float var24 = var12 * par7;
		float var25 = var13 * par7;
		float var26 = 0.0625F;
		int var28 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4);
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 - 1, par4, 0))
		{
			var8.setBrightness(renderMinY > 0.0D ? var28 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4));
			var8.setColorOpaque_F(var14, var18, var22);
			renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 0));
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 + 1, par4, 1))
		{
			var8.setBrightness(renderMaxY < 1.0D ? var28 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4));
			var8.setColorOpaque_F(var15, var19, var23);
			renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 1));
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 - 1, 2))
		{
			var8.setBrightness(renderMinZ > 0.0D ? var28 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1));
			var8.setColorOpaque_F(var16, var20, var24);
			var8.addTranslation(0.0F, 0.0F, var26);
			renderFaceZNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 2));
			var8.addTranslation(0.0F, 0.0F, -var26);
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 + 1, 3))
		{
			var8.setBrightness(renderMaxZ < 1.0D ? var28 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1));
			var8.setColorOpaque_F(var16, var20, var24);
			var8.addTranslation(0.0F, 0.0F, -var26);
			renderFaceZPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3));
			var8.addTranslation(0.0F, 0.0F, var26);
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 - 1, par3, par4, 4))
		{
			var8.setBrightness(renderMinX > 0.0D ? var28 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4));
			var8.setColorOpaque_F(var17, var21, var25);
			var8.addTranslation(var26, 0.0F, 0.0F);
			renderFaceXNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 4));
			var8.addTranslation(-var26, 0.0F, 0.0F);
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 + 1, par3, par4, 5))
		{
			var8.setBrightness(renderMaxX < 1.0D ? var28 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4));
			var8.setColorOpaque_F(var17, var21, var25);
			var8.addTranslation(-var26, 0.0F, 0.0F);
			renderFaceXPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 5));
			var8.addTranslation(var26, 0.0F, 0.0F);
			var9 = true;
		}
		return var9;
	}
	
	private boolean renderBlockCauldron(BlockCauldron par1BlockCauldron, int par2, int par3, int par4)
	{
		renderStandardBlock(par1BlockCauldron, par2, par3, par4);
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockCauldron.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = par1BlockCauldron.colorMultiplier(blockAccess, par2, par3, par4);
		float var8 = (var7 >> 16 & 255) / 255.0F;
		float var9 = (var7 >> 8 & 255) / 255.0F;
		float var10 = (var7 & 255) / 255.0F;
		float var12;
		if(EntityRenderer.anaglyphEnable)
		{
			float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
			var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
			float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
			var8 = var11;
			var9 = var12;
			var10 = var13;
		}
		var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		Icon var16 = par1BlockCauldron.getBlockTextureFromSide(2);
		var12 = 0.125F;
		renderFaceXPos(par1BlockCauldron, par2 - 1.0F + var12, par3, par4, var16);
		renderFaceXNeg(par1BlockCauldron, par2 + 1.0F - var12, par3, par4, var16);
		renderFaceZPos(par1BlockCauldron, par2, par3, par4 - 1.0F + var12, var16);
		renderFaceZNeg(par1BlockCauldron, par2, par3, par4 + 1.0F - var12, var16);
		Icon var17 = BlockCauldron.func_94375_b("cauldron_inner");
		renderFaceYPos(par1BlockCauldron, par2, par3 - 1.0F + 0.25F, par4, var17);
		renderFaceYNeg(par1BlockCauldron, par2, par3 + 1.0F - 0.75F, par4, var17);
		int var14 = blockAccess.getBlockMetadata(par2, par3, par4);
		if(var14 > 0)
		{
			Icon var15 = BlockFluid.func_94424_b("water");
			if(var14 > 3)
			{
				var14 = 3;
			}
			renderFaceYPos(par1BlockCauldron, par2, par3 - 1.0F + (6.0F + var14 * 3.0F) / 16.0F, par4, var15);
		}
		return true;
	}
	
	private boolean renderBlockCocoa(BlockCocoa par1BlockCocoa, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockCocoa.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var7 = BlockDirectional.getDirection(var6);
		int var8 = BlockCocoa.func_72219_c(var6);
		Icon var9 = par1BlockCocoa.func_94468_i_(var8);
		int var10 = 4 + var8 * 2;
		int var11 = 5 + var8 * 2;
		double var12 = 15.0D - var10;
		double var14 = 15.0D;
		double var16 = 4.0D;
		double var18 = 4.0D + var11;
		double var20 = var9.getInterpolatedU(var12);
		double var22 = var9.getInterpolatedU(var14);
		double var24 = var9.getInterpolatedV(var16);
		double var26 = var9.getInterpolatedV(var18);
		double var28 = 0.0D;
		double var30 = 0.0D;
		switch(var7)
		{
			case 0:
				var28 = 8.0D - var10 / 2;
				var30 = 15.0D - var10;
				break;
			case 1:
				var28 = 1.0D;
				var30 = 8.0D - var10 / 2;
				break;
			case 2:
				var28 = 8.0D - var10 / 2;
				var30 = 1.0D;
				break;
			case 3:
				var28 = 15.0D - var10;
				var30 = 8.0D - var10 / 2;
		}
		double var32 = par2 + var28 / 16.0D;
		double var34 = par2 + (var28 + var10) / 16.0D;
		double var36 = par3 + (12.0D - var11) / 16.0D;
		double var38 = par3 + 0.75D;
		double var40 = par4 + var30 / 16.0D;
		double var42 = par4 + (var30 + var10) / 16.0D;
		var5.addVertexWithUV(var32, var36, var40, var20, var26);
		var5.addVertexWithUV(var32, var36, var42, var22, var26);
		var5.addVertexWithUV(var32, var38, var42, var22, var24);
		var5.addVertexWithUV(var32, var38, var40, var20, var24);
		var5.addVertexWithUV(var34, var36, var42, var20, var26);
		var5.addVertexWithUV(var34, var36, var40, var22, var26);
		var5.addVertexWithUV(var34, var38, var40, var22, var24);
		var5.addVertexWithUV(var34, var38, var42, var20, var24);
		var5.addVertexWithUV(var34, var36, var40, var20, var26);
		var5.addVertexWithUV(var32, var36, var40, var22, var26);
		var5.addVertexWithUV(var32, var38, var40, var22, var24);
		var5.addVertexWithUV(var34, var38, var40, var20, var24);
		var5.addVertexWithUV(var32, var36, var42, var20, var26);
		var5.addVertexWithUV(var34, var36, var42, var22, var26);
		var5.addVertexWithUV(var34, var38, var42, var22, var24);
		var5.addVertexWithUV(var32, var38, var42, var20, var24);
		int var44 = var10;
		if(var8 >= 2)
		{
			var44 = var10 - 1;
		}
		var20 = var9.getMinU();
		var22 = var9.getInterpolatedU(var44);
		var24 = var9.getMinV();
		var26 = var9.getInterpolatedV(var44);
		var5.addVertexWithUV(var32, var38, var42, var20, var26);
		var5.addVertexWithUV(var34, var38, var42, var22, var26);
		var5.addVertexWithUV(var34, var38, var40, var22, var24);
		var5.addVertexWithUV(var32, var38, var40, var20, var24);
		var5.addVertexWithUV(var32, var36, var40, var20, var24);
		var5.addVertexWithUV(var34, var36, var40, var22, var24);
		var5.addVertexWithUV(var34, var36, var42, var22, var26);
		var5.addVertexWithUV(var32, var36, var42, var20, var26);
		var20 = var9.getInterpolatedU(12.0D);
		var22 = var9.getMaxU();
		var24 = var9.getMinV();
		var26 = var9.getInterpolatedV(4.0D);
		var28 = 8.0D;
		var30 = 0.0D;
		double var45;
		switch(var7)
		{
			case 0:
				var28 = 8.0D;
				var30 = 12.0D;
				var45 = var20;
				var20 = var22;
				var22 = var45;
				break;
			case 1:
				var28 = 0.0D;
				var30 = 8.0D;
				break;
			case 2:
				var28 = 8.0D;
				var30 = 0.0D;
				break;
			case 3:
				var28 = 12.0D;
				var30 = 8.0D;
				var45 = var20;
				var20 = var22;
				var22 = var45;
		}
		var32 = par2 + var28 / 16.0D;
		var34 = par2 + (var28 + 4.0D) / 16.0D;
		var36 = par3 + 0.75D;
		var38 = par3 + 1.0D;
		var40 = par4 + var30 / 16.0D;
		var42 = par4 + (var30 + 4.0D) / 16.0D;
		if(var7 != 2 && var7 != 0)
		{
			if(var7 == 1 || var7 == 3)
			{
				var5.addVertexWithUV(var34, var36, var40, var20, var26);
				var5.addVertexWithUV(var32, var36, var40, var22, var26);
				var5.addVertexWithUV(var32, var38, var40, var22, var24);
				var5.addVertexWithUV(var34, var38, var40, var20, var24);
				var5.addVertexWithUV(var32, var36, var40, var22, var26);
				var5.addVertexWithUV(var34, var36, var40, var20, var26);
				var5.addVertexWithUV(var34, var38, var40, var20, var24);
				var5.addVertexWithUV(var32, var38, var40, var22, var24);
			}
		} else
		{
			var5.addVertexWithUV(var32, var36, var40, var22, var26);
			var5.addVertexWithUV(var32, var36, var42, var20, var26);
			var5.addVertexWithUV(var32, var38, var42, var20, var24);
			var5.addVertexWithUV(var32, var38, var40, var22, var24);
			var5.addVertexWithUV(var32, var36, var42, var20, var26);
			var5.addVertexWithUV(var32, var36, var40, var22, var26);
			var5.addVertexWithUV(var32, var38, var40, var22, var24);
			var5.addVertexWithUV(var32, var38, var42, var20, var24);
		}
		return true;
	}
	
	private boolean renderBlockComparator(BlockComparator par1BlockComparator, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockComparator.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var7 = var6 & 3;
		double var8 = 0.0D;
		double var10 = -0.1875D;
		double var12 = 0.0D;
		double var14 = 0.0D;
		double var16 = 0.0D;
		Icon var18;
		if(par1BlockComparator.func_94490_c(var6))
		{
			var18 = Block.torchRedstoneActive.getBlockTextureFromSide(0);
		} else
		{
			var10 -= 0.1875D;
			var18 = Block.torchRedstoneIdle.getBlockTextureFromSide(0);
		}
		switch(var7)
		{
			case 0:
				var12 = -0.3125D;
				var16 = 1.0D;
				break;
			case 1:
				var8 = 0.3125D;
				var14 = -1.0D;
				break;
			case 2:
				var12 = 0.3125D;
				var16 = -1.0D;
				break;
			case 3:
				var8 = -0.3125D;
				var14 = 1.0D;
		}
		renderTorchAtAngle(par1BlockComparator, par2 + 0.25D * var14 + 0.1875D * var16, par3 - 0.1875F, par4 + 0.25D * var16 + 0.1875D * var14, 0.0D, 0.0D, var6);
		renderTorchAtAngle(par1BlockComparator, par2 + 0.25D * var14 + -0.1875D * var16, par3 - 0.1875F, par4 + 0.25D * var16 + -0.1875D * var14, 0.0D, 0.0D, var6);
		setOverrideBlockTexture(var18);
		renderTorchAtAngle(par1BlockComparator, par2 + var8, par3 + var10, par4 + var12, 0.0D, 0.0D, var6);
		clearOverrideBlockTexture();
		renderBlockRedstoneLogicMetadata(par1BlockComparator, par2, par3, par4, var7);
		return true;
	}
	
	public boolean renderBlockCrops(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderBlockCropsImpl(par1Block, blockAccess.getBlockMetadata(par2, par3, par4), par2, par3 - 0.0625F, par4);
		return true;
	}
	
	public void renderBlockCropsImpl(Block par1Block, int par2, double par3, double par5, double par7)
	{
		Tessellator var9 = Tessellator.instance;
		Icon var10 = getBlockIconFromSideAndMetadata(par1Block, 0, par2);
		if(hasOverrideBlockTexture())
		{
			var10 = overrideBlockTexture;
		}
		double var11 = var10.getMinU();
		double var13 = var10.getMinV();
		double var15 = var10.getMaxU();
		double var17 = var10.getMaxV();
		double var19 = par3 + 0.5D - 0.25D;
		double var21 = par3 + 0.5D + 0.25D;
		double var23 = par7 + 0.5D - 0.5D;
		double var25 = par7 + 0.5D + 0.5D;
		var9.addVertexWithUV(var19, par5 + 1.0D, var23, var11, var13);
		var9.addVertexWithUV(var19, par5 + 0.0D, var23, var11, var17);
		var9.addVertexWithUV(var19, par5 + 0.0D, var25, var15, var17);
		var9.addVertexWithUV(var19, par5 + 1.0D, var25, var15, var13);
		var9.addVertexWithUV(var19, par5 + 1.0D, var25, var11, var13);
		var9.addVertexWithUV(var19, par5 + 0.0D, var25, var11, var17);
		var9.addVertexWithUV(var19, par5 + 0.0D, var23, var15, var17);
		var9.addVertexWithUV(var19, par5 + 1.0D, var23, var15, var13);
		var9.addVertexWithUV(var21, par5 + 1.0D, var25, var11, var13);
		var9.addVertexWithUV(var21, par5 + 0.0D, var25, var11, var17);
		var9.addVertexWithUV(var21, par5 + 0.0D, var23, var15, var17);
		var9.addVertexWithUV(var21, par5 + 1.0D, var23, var15, var13);
		var9.addVertexWithUV(var21, par5 + 1.0D, var23, var11, var13);
		var9.addVertexWithUV(var21, par5 + 0.0D, var23, var11, var17);
		var9.addVertexWithUV(var21, par5 + 0.0D, var25, var15, var17);
		var9.addVertexWithUV(var21, par5 + 1.0D, var25, var15, var13);
		var19 = par3 + 0.5D - 0.5D;
		var21 = par3 + 0.5D + 0.5D;
		var23 = par7 + 0.5D - 0.25D;
		var25 = par7 + 0.5D + 0.25D;
		var9.addVertexWithUV(var19, par5 + 1.0D, var23, var11, var13);
		var9.addVertexWithUV(var19, par5 + 0.0D, var23, var11, var17);
		var9.addVertexWithUV(var21, par5 + 0.0D, var23, var15, var17);
		var9.addVertexWithUV(var21, par5 + 1.0D, var23, var15, var13);
		var9.addVertexWithUV(var21, par5 + 1.0D, var23, var11, var13);
		var9.addVertexWithUV(var21, par5 + 0.0D, var23, var11, var17);
		var9.addVertexWithUV(var19, par5 + 0.0D, var23, var15, var17);
		var9.addVertexWithUV(var19, par5 + 1.0D, var23, var15, var13);
		var9.addVertexWithUV(var21, par5 + 1.0D, var25, var11, var13);
		var9.addVertexWithUV(var21, par5 + 0.0D, var25, var11, var17);
		var9.addVertexWithUV(var19, par5 + 0.0D, var25, var15, var17);
		var9.addVertexWithUV(var19, par5 + 1.0D, var25, var15, var13);
		var9.addVertexWithUV(var19, par5 + 1.0D, var25, var11, var13);
		var9.addVertexWithUV(var19, par5 + 0.0D, var25, var11, var17);
		var9.addVertexWithUV(var21, par5 + 0.0D, var25, var15, var17);
		var9.addVertexWithUV(var21, par5 + 1.0D, var25, var15, var13);
	}
	
	public boolean renderBlockDoor(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		if((var6 & 8) != 0)
		{
			if(blockAccess.getBlockId(par2, par3 - 1, par4) != par1Block.blockID) return false;
		} else if(blockAccess.getBlockId(par2, par3 + 1, par4) != par1Block.blockID) return false;
		boolean var7 = false;
		float var8 = 0.5F;
		float var9 = 1.0F;
		float var10 = 0.8F;
		float var11 = 0.6F;
		int var12 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4);
		var5.setBrightness(renderMinY > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4));
		var5.setColorOpaque_F(var8, var8, var8);
		renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 0));
		var7 = true;
		var5.setBrightness(renderMaxY < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4));
		var5.setColorOpaque_F(var9, var9, var9);
		renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 1));
		var7 = true;
		var5.setBrightness(renderMinZ > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1));
		var5.setColorOpaque_F(var10, var10, var10);
		Icon var14 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 2);
		renderFaceZNeg(par1Block, par2, par3, par4, var14);
		var7 = true;
		flipTexture = false;
		var5.setBrightness(renderMaxZ < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1));
		var5.setColorOpaque_F(var10, var10, var10);
		var14 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3);
		renderFaceZPos(par1Block, par2, par3, par4, var14);
		var7 = true;
		flipTexture = false;
		var5.setBrightness(renderMinX > 0.0D ? var12 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4));
		var5.setColorOpaque_F(var11, var11, var11);
		var14 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 4);
		renderFaceXNeg(par1Block, par2, par3, par4, var14);
		var7 = true;
		flipTexture = false;
		var5.setBrightness(renderMaxX < 1.0D ? var12 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4));
		var5.setColorOpaque_F(var11, var11, var11);
		var14 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 5);
		renderFaceXPos(par1Block, par2, par3, par4, var14);
		var7 = true;
		flipTexture = false;
		return var7;
	}
	
	public boolean renderBlockDragonEgg(BlockDragonEgg par1BlockDragonEgg, int par2, int par3, int par4)
	{
		boolean var5 = false;
		int var6 = 0;
		for(int var7 = 0; var7 < 8; ++var7)
		{
			byte var8 = 0;
			byte var9 = 1;
			if(var7 == 0)
			{
				var8 = 2;
			}
			if(var7 == 1)
			{
				var8 = 3;
			}
			if(var7 == 2)
			{
				var8 = 4;
			}
			if(var7 == 3)
			{
				var8 = 5;
				var9 = 2;
			}
			if(var7 == 4)
			{
				var8 = 6;
				var9 = 3;
			}
			if(var7 == 5)
			{
				var8 = 7;
				var9 = 5;
			}
			if(var7 == 6)
			{
				var8 = 6;
				var9 = 2;
			}
			if(var7 == 7)
			{
				var8 = 3;
			}
			float var10 = var8 / 16.0F;
			float var11 = 1.0F - var6 / 16.0F;
			float var12 = 1.0F - (var6 + var9) / 16.0F;
			var6 += var9;
			setRenderBounds(0.5F - var10, var12, 0.5F - var10, 0.5F + var10, var11, 0.5F + var10);
			renderStandardBlock(par1BlockDragonEgg, par2, par3, par4);
		}
		var5 = true;
		setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return var5;
	}
	
	private boolean renderBlockEndPortalFrame(BlockEndPortalFrame par1BlockEndPortalFrame, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 3;
		if(var6 == 0)
		{
			uvRotateTop = 3;
		} else if(var6 == 3)
		{
			uvRotateTop = 1;
		} else if(var6 == 1)
		{
			uvRotateTop = 2;
		}
		if(!BlockEndPortalFrame.isEnderEyeInserted(var5))
		{
			setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
			renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
			uvRotateTop = 0;
			return true;
		} else
		{
			renderAllFaces = true;
			setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
			renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
			setOverrideBlockTexture(par1BlockEndPortalFrame.func_94398_p());
			setRenderBounds(0.25D, 0.8125D, 0.25D, 0.75D, 1.0D, 0.75D);
			renderStandardBlock(par1BlockEndPortalFrame, par2, par3, par4);
			renderAllFaces = false;
			clearOverrideBlockTexture();
			uvRotateTop = 0;
			return true;
		}
	}
	
	public boolean renderBlockFence(BlockFence par1BlockFence, int par2, int par3, int par4)
	{
		boolean var5 = false;
		float var6 = 0.375F;
		float var7 = 0.625F;
		setRenderBounds(var6, 0.0D, var6, var7, 1.0D, var7);
		renderStandardBlock(par1BlockFence, par2, par3, par4);
		var5 = true;
		boolean var8 = false;
		boolean var9 = false;
		if(par1BlockFence.canConnectFenceTo(blockAccess, par2 - 1, par3, par4) || par1BlockFence.canConnectFenceTo(blockAccess, par2 + 1, par3, par4))
		{
			var8 = true;
		}
		if(par1BlockFence.canConnectFenceTo(blockAccess, par2, par3, par4 - 1) || par1BlockFence.canConnectFenceTo(blockAccess, par2, par3, par4 + 1))
		{
			var9 = true;
		}
		boolean var10 = par1BlockFence.canConnectFenceTo(blockAccess, par2 - 1, par3, par4);
		boolean var11 = par1BlockFence.canConnectFenceTo(blockAccess, par2 + 1, par3, par4);
		boolean var12 = par1BlockFence.canConnectFenceTo(blockAccess, par2, par3, par4 - 1);
		boolean var13 = par1BlockFence.canConnectFenceTo(blockAccess, par2, par3, par4 + 1);
		if(!var8 && !var9)
		{
			var8 = true;
		}
		var6 = 0.4375F;
		var7 = 0.5625F;
		float var14 = 0.75F;
		float var15 = 0.9375F;
		float var16 = var10 ? 0.0F : var6;
		float var17 = var11 ? 1.0F : var7;
		float var18 = var12 ? 0.0F : var6;
		float var19 = var13 ? 1.0F : var7;
		if(var8)
		{
			setRenderBounds(var16, var14, var6, var17, var15, var7);
			renderStandardBlock(par1BlockFence, par2, par3, par4);
			var5 = true;
		}
		if(var9)
		{
			setRenderBounds(var6, var14, var18, var7, var15, var19);
			renderStandardBlock(par1BlockFence, par2, par3, par4);
			var5 = true;
		}
		var14 = 0.375F;
		var15 = 0.5625F;
		if(var8)
		{
			setRenderBounds(var16, var14, var6, var17, var15, var7);
			renderStandardBlock(par1BlockFence, par2, par3, par4);
			var5 = true;
		}
		if(var9)
		{
			setRenderBounds(var6, var14, var18, var7, var15, var19);
			renderStandardBlock(par1BlockFence, par2, par3, par4);
			var5 = true;
		}
		par1BlockFence.setBlockBoundsBasedOnState(blockAccess, par2, par3, par4);
		return var5;
	}
	
	public boolean renderBlockFenceGate(BlockFenceGate par1BlockFenceGate, int par2, int par3, int par4)
	{
		boolean var5 = true;
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		boolean var7 = BlockFenceGate.isFenceGateOpen(var6);
		int var8 = BlockDirectional.getDirection(var6);
		float var9 = 0.375F;
		float var10 = 0.5625F;
		float var11 = 0.75F;
		float var12 = 0.9375F;
		float var13 = 0.3125F;
		float var14 = 1.0F;
		if((var8 == 2 || var8 == 0) && blockAccess.getBlockId(par2 - 1, par3, par4) == Block.cobblestoneWall.blockID && blockAccess.getBlockId(par2 + 1, par3, par4) == Block.cobblestoneWall.blockID || (var8 == 3 || var8 == 1) && blockAccess.getBlockId(par2, par3, par4 - 1) == Block.cobblestoneWall.blockID && blockAccess.getBlockId(par2, par3, par4 + 1) == Block.cobblestoneWall.blockID)
		{
			var9 -= 0.1875F;
			var10 -= 0.1875F;
			var11 -= 0.1875F;
			var12 -= 0.1875F;
			var13 -= 0.1875F;
			var14 -= 0.1875F;
		}
		renderAllFaces = true;
		float var15;
		float var17;
		float var16;
		float var18;
		if(var8 != 3 && var8 != 1)
		{
			var15 = 0.0F;
			var16 = 0.125F;
			var17 = 0.4375F;
			var18 = 0.5625F;
			setRenderBounds(var15, var13, var17, var16, var14, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var15 = 0.875F;
			var16 = 1.0F;
			setRenderBounds(var15, var13, var17, var16, var14, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		} else
		{
			uvRotateTop = 1;
			var15 = 0.4375F;
			var16 = 0.5625F;
			var17 = 0.0F;
			var18 = 0.125F;
			setRenderBounds(var15, var13, var17, var16, var14, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var17 = 0.875F;
			var18 = 1.0F;
			setRenderBounds(var15, var13, var17, var16, var14, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			uvRotateTop = 0;
		}
		if(var7)
		{
			if(var8 == 2 || var8 == 0)
			{
				uvRotateTop = 1;
			}
			if(var8 == 3)
			{
				setRenderBounds(0.8125D, var9, 0.0D, 0.9375D, var12, 0.125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.8125D, var9, 0.875D, 0.9375D, var12, 1.0D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.5625D, var9, 0.0D, 0.8125D, var10, 0.125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.5625D, var9, 0.875D, 0.8125D, var10, 1.0D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.5625D, var11, 0.0D, 0.8125D, var12, 0.125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.5625D, var11, 0.875D, 0.8125D, var12, 1.0D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			} else if(var8 == 1)
			{
				setRenderBounds(0.0625D, var9, 0.0D, 0.1875D, var12, 0.125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.0625D, var9, 0.875D, 0.1875D, var12, 1.0D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.1875D, var9, 0.0D, 0.4375D, var10, 0.125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.1875D, var9, 0.875D, 0.4375D, var10, 1.0D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.1875D, var11, 0.0D, 0.4375D, var12, 0.125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.1875D, var11, 0.875D, 0.4375D, var12, 1.0D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			} else if(var8 == 0)
			{
				setRenderBounds(0.0D, var9, 0.8125D, 0.125D, var12, 0.9375D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.875D, var9, 0.8125D, 1.0D, var12, 0.9375D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.0D, var9, 0.5625D, 0.125D, var10, 0.8125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.875D, var9, 0.5625D, 1.0D, var10, 0.8125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.0D, var11, 0.5625D, 0.125D, var12, 0.8125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.875D, var11, 0.5625D, 1.0D, var12, 0.8125D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			} else if(var8 == 2)
			{
				setRenderBounds(0.0D, var9, 0.0625D, 0.125D, var12, 0.1875D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.875D, var9, 0.0625D, 1.0D, var12, 0.1875D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.0D, var9, 0.1875D, 0.125D, var10, 0.4375D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.875D, var9, 0.1875D, 1.0D, var10, 0.4375D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.0D, var11, 0.1875D, 0.125D, var12, 0.4375D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				setRenderBounds(0.875D, var11, 0.1875D, 1.0D, var12, 0.4375D);
				renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
		} else if(var8 != 3 && var8 != 1)
		{
			var15 = 0.375F;
			var16 = 0.5F;
			var17 = 0.4375F;
			var18 = 0.5625F;
			setRenderBounds(var15, var9, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var15 = 0.5F;
			var16 = 0.625F;
			setRenderBounds(var15, var9, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var15 = 0.625F;
			var16 = 0.875F;
			setRenderBounds(var15, var9, var17, var16, var10, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			setRenderBounds(var15, var11, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var15 = 0.125F;
			var16 = 0.375F;
			setRenderBounds(var15, var9, var17, var16, var10, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			setRenderBounds(var15, var11, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		} else
		{
			uvRotateTop = 1;
			var15 = 0.4375F;
			var16 = 0.5625F;
			var17 = 0.375F;
			var18 = 0.5F;
			setRenderBounds(var15, var9, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var17 = 0.5F;
			var18 = 0.625F;
			setRenderBounds(var15, var9, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var17 = 0.625F;
			var18 = 0.875F;
			setRenderBounds(var15, var9, var17, var16, var10, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			setRenderBounds(var15, var11, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			var17 = 0.125F;
			var18 = 0.375F;
			setRenderBounds(var15, var9, var17, var16, var10, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			setRenderBounds(var15, var11, var17, var16, var12, var18);
			renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}
		renderAllFaces = false;
		uvRotateTop = 0;
		setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return var5;
	}
	
	public boolean renderBlockFire(BlockFire par1BlockFire, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		Icon var6 = par1BlockFire.func_94438_c(0);
		Icon var7 = par1BlockFire.func_94438_c(1);
		Icon var8 = var6;
		if(hasOverrideBlockTexture())
		{
			var8 = overrideBlockTexture;
		}
		var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		var5.setBrightness(par1BlockFire.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		double var9 = var8.getMinU();
		double var11 = var8.getMinV();
		double var13 = var8.getMaxU();
		double var15 = var8.getMaxV();
		float var17 = 1.4F;
		double var32;
		double var20;
		double var22;
		double var24;
		double var26;
		double var28;
		double var30;
		if(!blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && !Block.fire.canBlockCatchFire(blockAccess, par2, par3 - 1, par4))
		{
			float var36 = 0.2F;
			float var19 = 0.0625F;
			if((par2 + par3 + par4 & 1) == 1)
			{
				var9 = var7.getMinU();
				var11 = var7.getMinV();
				var13 = var7.getMaxU();
				var15 = var7.getMaxV();
			}
			if((par2 / 2 + par3 / 2 + par4 / 2 & 1) == 1)
			{
				var20 = var13;
				var13 = var9;
				var9 = var20;
			}
			if(Block.fire.canBlockCatchFire(blockAccess, par2 - 1, par3, par4))
			{
				var5.addVertexWithUV(par2 + var36, par3 + var17 + var19, par4 + 1, var13, var11);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 1, var13, var15);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 0, var9, var15);
				var5.addVertexWithUV(par2 + var36, par3 + var17 + var19, par4 + 0, var9, var11);
				var5.addVertexWithUV(par2 + var36, par3 + var17 + var19, par4 + 0, var9, var11);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 0, var9, var15);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 1, var13, var15);
				var5.addVertexWithUV(par2 + var36, par3 + var17 + var19, par4 + 1, var13, var11);
			}
			if(Block.fire.canBlockCatchFire(blockAccess, par2 + 1, par3, par4))
			{
				var5.addVertexWithUV(par2 + 1 - var36, par3 + var17 + var19, par4 + 0, var9, var11);
				var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var19, par4 + 0, var9, var15);
				var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var19, par4 + 1, var13, var15);
				var5.addVertexWithUV(par2 + 1 - var36, par3 + var17 + var19, par4 + 1, var13, var11);
				var5.addVertexWithUV(par2 + 1 - var36, par3 + var17 + var19, par4 + 1, var13, var11);
				var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var19, par4 + 1, var13, var15);
				var5.addVertexWithUV(par2 + 1 - 0, par3 + 0 + var19, par4 + 0, var9, var15);
				var5.addVertexWithUV(par2 + 1 - var36, par3 + var17 + var19, par4 + 0, var9, var11);
			}
			if(Block.fire.canBlockCatchFire(blockAccess, par2, par3, par4 - 1))
			{
				var5.addVertexWithUV(par2 + 0, par3 + var17 + var19, par4 + var36, var13, var11);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 0, var13, var15);
				var5.addVertexWithUV(par2 + 1, par3 + 0 + var19, par4 + 0, var9, var15);
				var5.addVertexWithUV(par2 + 1, par3 + var17 + var19, par4 + var36, var9, var11);
				var5.addVertexWithUV(par2 + 1, par3 + var17 + var19, par4 + var36, var9, var11);
				var5.addVertexWithUV(par2 + 1, par3 + 0 + var19, par4 + 0, var9, var15);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 0, var13, var15);
				var5.addVertexWithUV(par2 + 0, par3 + var17 + var19, par4 + var36, var13, var11);
			}
			if(Block.fire.canBlockCatchFire(blockAccess, par2, par3, par4 + 1))
			{
				var5.addVertexWithUV(par2 + 1, par3 + var17 + var19, par4 + 1 - var36, var9, var11);
				var5.addVertexWithUV(par2 + 1, par3 + 0 + var19, par4 + 1 - 0, var9, var15);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 1 - 0, var13, var15);
				var5.addVertexWithUV(par2 + 0, par3 + var17 + var19, par4 + 1 - var36, var13, var11);
				var5.addVertexWithUV(par2 + 0, par3 + var17 + var19, par4 + 1 - var36, var13, var11);
				var5.addVertexWithUV(par2 + 0, par3 + 0 + var19, par4 + 1 - 0, var13, var15);
				var5.addVertexWithUV(par2 + 1, par3 + 0 + var19, par4 + 1 - 0, var9, var15);
				var5.addVertexWithUV(par2 + 1, par3 + var17 + var19, par4 + 1 - var36, var9, var11);
			}
			if(Block.fire.canBlockCatchFire(blockAccess, par2, par3 + 1, par4))
			{
				var20 = par2 + 0.5D + 0.5D;
				var22 = par2 + 0.5D - 0.5D;
				var24 = par4 + 0.5D + 0.5D;
				var26 = par4 + 0.5D - 0.5D;
				var28 = par2 + 0.5D - 0.5D;
				var30 = par2 + 0.5D + 0.5D;
				var32 = par4 + 0.5D - 0.5D;
				double var34 = par4 + 0.5D + 0.5D;
				var9 = var6.getMinU();
				var11 = var6.getMinV();
				var13 = var6.getMaxU();
				var15 = var6.getMaxV();
				++par3;
				var17 = -0.2F;
				if((par2 + par3 + par4 & 1) == 0)
				{
					var5.addVertexWithUV(var28, par3 + var17, par4 + 0, var13, var11);
					var5.addVertexWithUV(var20, par3 + 0, par4 + 0, var13, var15);
					var5.addVertexWithUV(var20, par3 + 0, par4 + 1, var9, var15);
					var5.addVertexWithUV(var28, par3 + var17, par4 + 1, var9, var11);
					var9 = var7.getMinU();
					var11 = var7.getMinV();
					var13 = var7.getMaxU();
					var15 = var7.getMaxV();
					var5.addVertexWithUV(var30, par3 + var17, par4 + 1, var13, var11);
					var5.addVertexWithUV(var22, par3 + 0, par4 + 1, var13, var15);
					var5.addVertexWithUV(var22, par3 + 0, par4 + 0, var9, var15);
					var5.addVertexWithUV(var30, par3 + var17, par4 + 0, var9, var11);
				} else
				{
					var5.addVertexWithUV(par2 + 0, par3 + var17, var34, var13, var11);
					var5.addVertexWithUV(par2 + 0, par3 + 0, var26, var13, var15);
					var5.addVertexWithUV(par2 + 1, par3 + 0, var26, var9, var15);
					var5.addVertexWithUV(par2 + 1, par3 + var17, var34, var9, var11);
					var9 = var7.getMinU();
					var11 = var7.getMinV();
					var13 = var7.getMaxU();
					var15 = var7.getMaxV();
					var5.addVertexWithUV(par2 + 1, par3 + var17, var32, var13, var11);
					var5.addVertexWithUV(par2 + 1, par3 + 0, var24, var13, var15);
					var5.addVertexWithUV(par2 + 0, par3 + 0, var24, var9, var15);
					var5.addVertexWithUV(par2 + 0, par3 + var17, var32, var9, var11);
				}
			}
		} else
		{
			double var18 = par2 + 0.5D + 0.2D;
			var20 = par2 + 0.5D - 0.2D;
			var22 = par4 + 0.5D + 0.2D;
			var24 = par4 + 0.5D - 0.2D;
			var26 = par2 + 0.5D - 0.3D;
			var28 = par2 + 0.5D + 0.3D;
			var30 = par4 + 0.5D - 0.3D;
			var32 = par4 + 0.5D + 0.3D;
			var5.addVertexWithUV(var26, par3 + var17, par4 + 1, var13, var11);
			var5.addVertexWithUV(var18, par3 + 0, par4 + 1, var13, var15);
			var5.addVertexWithUV(var18, par3 + 0, par4 + 0, var9, var15);
			var5.addVertexWithUV(var26, par3 + var17, par4 + 0, var9, var11);
			var5.addVertexWithUV(var28, par3 + var17, par4 + 0, var13, var11);
			var5.addVertexWithUV(var20, par3 + 0, par4 + 0, var13, var15);
			var5.addVertexWithUV(var20, par3 + 0, par4 + 1, var9, var15);
			var5.addVertexWithUV(var28, par3 + var17, par4 + 1, var9, var11);
			var9 = var7.getMinU();
			var11 = var7.getMinV();
			var13 = var7.getMaxU();
			var15 = var7.getMaxV();
			var5.addVertexWithUV(par2 + 1, par3 + var17, var32, var13, var11);
			var5.addVertexWithUV(par2 + 1, par3 + 0, var24, var13, var15);
			var5.addVertexWithUV(par2 + 0, par3 + 0, var24, var9, var15);
			var5.addVertexWithUV(par2 + 0, par3 + var17, var32, var9, var11);
			var5.addVertexWithUV(par2 + 0, par3 + var17, var30, var13, var11);
			var5.addVertexWithUV(par2 + 0, par3 + 0, var22, var13, var15);
			var5.addVertexWithUV(par2 + 1, par3 + 0, var22, var9, var15);
			var5.addVertexWithUV(par2 + 1, par3 + var17, var30, var9, var11);
			var18 = par2 + 0.5D - 0.5D;
			var20 = par2 + 0.5D + 0.5D;
			var22 = par4 + 0.5D - 0.5D;
			var24 = par4 + 0.5D + 0.5D;
			var26 = par2 + 0.5D - 0.4D;
			var28 = par2 + 0.5D + 0.4D;
			var30 = par4 + 0.5D - 0.4D;
			var32 = par4 + 0.5D + 0.4D;
			var5.addVertexWithUV(var26, par3 + var17, par4 + 0, var9, var11);
			var5.addVertexWithUV(var18, par3 + 0, par4 + 0, var9, var15);
			var5.addVertexWithUV(var18, par3 + 0, par4 + 1, var13, var15);
			var5.addVertexWithUV(var26, par3 + var17, par4 + 1, var13, var11);
			var5.addVertexWithUV(var28, par3 + var17, par4 + 1, var9, var11);
			var5.addVertexWithUV(var20, par3 + 0, par4 + 1, var9, var15);
			var5.addVertexWithUV(var20, par3 + 0, par4 + 0, var13, var15);
			var5.addVertexWithUV(var28, par3 + var17, par4 + 0, var13, var11);
			var9 = var6.getMinU();
			var11 = var6.getMinV();
			var13 = var6.getMaxU();
			var15 = var6.getMaxV();
			var5.addVertexWithUV(par2 + 0, par3 + var17, var32, var9, var11);
			var5.addVertexWithUV(par2 + 0, par3 + 0, var24, var9, var15);
			var5.addVertexWithUV(par2 + 1, par3 + 0, var24, var13, var15);
			var5.addVertexWithUV(par2 + 1, par3 + var17, var32, var13, var11);
			var5.addVertexWithUV(par2 + 1, par3 + var17, var30, var9, var11);
			var5.addVertexWithUV(par2 + 1, par3 + 0, var22, var9, var15);
			var5.addVertexWithUV(par2 + 0, par3 + 0, var22, var13, var15);
			var5.addVertexWithUV(par2 + 0, par3 + var17, var30, var13, var11);
		}
		return true;
	}
	
	private boolean renderBlockFlowerpot(BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4)
	{
		renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = par1BlockFlowerPot.colorMultiplier(blockAccess, par2, par3, par4);
		Icon var8 = getBlockIconFromSide(par1BlockFlowerPot, 0);
		float var9 = (var7 >> 16 & 255) / 255.0F;
		float var10 = (var7 >> 8 & 255) / 255.0F;
		float var11 = (var7 & 255) / 255.0F;
		float var12;
		float var14;
		if(EntityRenderer.anaglyphEnable)
		{
			var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
			float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
			var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
			var9 = var12;
			var10 = var13;
			var11 = var14;
		}
		var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
		var12 = 0.1865F;
		renderFaceXPos(par1BlockFlowerPot, par2 - 0.5F + var12, par3, par4, var8);
		renderFaceXNeg(par1BlockFlowerPot, par2 + 0.5F - var12, par3, par4, var8);
		renderFaceZPos(par1BlockFlowerPot, par2, par3, par4 - 0.5F + var12, var8);
		renderFaceZNeg(par1BlockFlowerPot, par2, par3, par4 + 0.5F - var12, var8);
		renderFaceYPos(par1BlockFlowerPot, par2, par3 - 0.5F + var12 + 0.1875F, par4, this.getBlockIcon(Block.dirt));
		int var19 = blockAccess.getBlockMetadata(par2, par3, par4);
		if(var19 != 0)
		{
			var14 = 0.0F;
			float var15 = 4.0F;
			float var16 = 0.0F;
			BlockFlower var17 = null;
			switch(var19)
			{
				case 1:
					var17 = Block.plantRed;
					break;
				case 2:
					var17 = Block.plantYellow;
				case 3:
				case 4:
				case 5:
				case 6:
				default:
					break;
				case 7:
					var17 = Block.mushroomRed;
					break;
				case 8:
					var17 = Block.mushroomBrown;
			}
			var5.addTranslation(var14 / 16.0F, var15 / 16.0F, var16 / 16.0F);
			if(var17 != null)
			{
				renderBlockByRenderType(var17, par2, par3, par4);
			} else if(var19 == 9)
			{
				renderAllFaces = true;
				float var18 = 0.125F;
				setRenderBounds(0.5F - var18, 0.0D, 0.5F - var18, 0.5F + var18, 0.25D, 0.5F + var18);
				renderStandardBlock(Block.cactus, par2, par3, par4);
				setRenderBounds(0.5F - var18, 0.25D, 0.5F - var18, 0.5F + var18, 0.5D, 0.5F + var18);
				renderStandardBlock(Block.cactus, par2, par3, par4);
				setRenderBounds(0.5F - var18, 0.5D, 0.5F - var18, 0.5F + var18, 0.75D, 0.5F + var18);
				renderStandardBlock(Block.cactus, par2, par3, par4);
				renderAllFaces = false;
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			} else if(var19 == 3)
			{
				drawCrossedSquares(Block.sapling, 0, par2, par3, par4, 0.75F);
			} else if(var19 == 5)
			{
				drawCrossedSquares(Block.sapling, 2, par2, par3, par4, 0.75F);
			} else if(var19 == 4)
			{
				drawCrossedSquares(Block.sapling, 1, par2, par3, par4, 0.75F);
			} else if(var19 == 6)
			{
				drawCrossedSquares(Block.sapling, 3, par2, par3, par4, 0.75F);
			} else if(var19 == 11)
			{
				var7 = Block.tallGrass.colorMultiplier(blockAccess, par2, par3, par4);
				var9 = (var7 >> 16 & 255) / 255.0F;
				var10 = (var7 >> 8 & 255) / 255.0F;
				var11 = (var7 & 255) / 255.0F;
				var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
				drawCrossedSquares(Block.tallGrass, 2, par2, par3, par4, 0.75F);
			} else if(var19 == 10)
			{
				drawCrossedSquares(Block.deadBush, 2, par2, par3, par4, 0.75F);
			}
			var5.addTranslation(-var14 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
		}
		return true;
	}
	
	public boolean renderBlockFluids(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = par1Block.colorMultiplier(blockAccess, par2, par3, par4);
		float var7 = (var6 >> 16 & 255) / 255.0F;
		float var8 = (var6 >> 8 & 255) / 255.0F;
		float var9 = (var6 & 255) / 255.0F;
		boolean var10 = par1Block.shouldSideBeRendered(blockAccess, par2, par3 + 1, par4, 1);
		boolean var11 = par1Block.shouldSideBeRendered(blockAccess, par2, par3 - 1, par4, 0);
		boolean[] var12 = new boolean[] { par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 - 1, 2), par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 + 1, 3), par1Block.shouldSideBeRendered(blockAccess, par2 - 1, par3, par4, 4), par1Block.shouldSideBeRendered(blockAccess, par2 + 1, par3, par4, 5) };
		if(!var10 && !var11 && !var12[0] && !var12[1] && !var12[2] && !var12[3]) return false;
		else
		{
			boolean var13 = false;
			float var14 = 0.5F;
			float var15 = 1.0F;
			float var16 = 0.8F;
			float var17 = 0.6F;
			double var18 = 0.0D;
			double var20 = 1.0D;
			Material var22 = par1Block.blockMaterial;
			int var23 = blockAccess.getBlockMetadata(par2, par3, par4);
			double var24 = getFluidHeight(par2, par3, par4, var22);
			double var26 = getFluidHeight(par2, par3, par4 + 1, var22);
			double var28 = getFluidHeight(par2 + 1, par3, par4 + 1, var22);
			double var30 = getFluidHeight(par2 + 1, par3, par4, var22);
			double var32 = 0.0010000000474974513D;
			float var53;
			float var52;
			if(renderAllFaces || var10)
			{
				var13 = true;
				Icon var34 = getBlockIconFromSideAndMetadata(par1Block, 1, var23);
				float var35 = (float) BlockFluid.getFlowDirection(blockAccess, par2, par3, par4, var22);
				if(var35 > -999.0F)
				{
					var34 = getBlockIconFromSideAndMetadata(par1Block, 2, var23);
				}
				var24 -= var32;
				var26 -= var32;
				var28 -= var32;
				var30 -= var32;
				double var38;
				double var36;
				double var42;
				double var40;
				double var46;
				double var44;
				double var50;
				double var48;
				if(var35 < -999.0F)
				{
					var36 = var34.getInterpolatedU(0.0D);
					var44 = var34.getInterpolatedV(0.0D);
					var38 = var36;
					var46 = var34.getInterpolatedV(16.0D);
					var40 = var34.getInterpolatedU(16.0D);
					var48 = var46;
					var42 = var40;
					var50 = var44;
				} else
				{
					var52 = MathHelper.sin(var35) * 0.25F;
					var53 = MathHelper.cos(var35) * 0.25F;
					var36 = var34.getInterpolatedU(8.0F + (-var53 - var52) * 16.0F);
					var44 = var34.getInterpolatedV(8.0F + (-var53 + var52) * 16.0F);
					var38 = var34.getInterpolatedU(8.0F + (-var53 + var52) * 16.0F);
					var46 = var34.getInterpolatedV(8.0F + (var53 + var52) * 16.0F);
					var40 = var34.getInterpolatedU(8.0F + (var53 + var52) * 16.0F);
					var48 = var34.getInterpolatedV(8.0F + (var53 - var52) * 16.0F);
					var42 = var34.getInterpolatedU(8.0F + (var53 - var52) * 16.0F);
					var50 = var34.getInterpolatedV(8.0F + (-var53 - var52) * 16.0F);
				}
				var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
				var52 = 1.0F;
				var5.setColorOpaque_F(var15 * var52 * var7, var15 * var52 * var8, var15 * var52 * var9);
				var5.addVertexWithUV(par2 + 0, par3 + var24, par4 + 0, var36, var44);
				var5.addVertexWithUV(par2 + 0, par3 + var26, par4 + 1, var38, var46);
				var5.addVertexWithUV(par2 + 1, par3 + var28, par4 + 1, var40, var48);
				var5.addVertexWithUV(par2 + 1, par3 + var30, par4 + 0, var42, var50);
			}
			if(renderAllFaces || var11)
			{
				var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4));
				float var58 = 1.0F;
				var5.setColorOpaque_F(var14 * var58, var14 * var58, var14 * var58);
				renderFaceYNeg(par1Block, par2, par3 + var32, par4, getBlockIconFromSide(par1Block, 0));
				var13 = true;
			}
			for(int var57 = 0; var57 < 4; ++var57)
			{
				int var59 = par2;
				int var37 = par4;
				if(var57 == 0)
				{
					var37 = par4 - 1;
				}
				if(var57 == 1)
				{
					++var37;
				}
				if(var57 == 2)
				{
					var59 = par2 - 1;
				}
				if(var57 == 3)
				{
					++var59;
				}
				Icon var60 = getBlockIconFromSideAndMetadata(par1Block, var57 + 2, var23);
				if(renderAllFaces || var12[var57])
				{
					double var39;
					double var43;
					double var41;
					double var47;
					double var45;
					double var49;
					if(var57 == 0)
					{
						var39 = var24;
						var41 = var30;
						var43 = par2;
						var47 = par2 + 1;
						var45 = par4 + var32;
						var49 = par4 + var32;
					} else if(var57 == 1)
					{
						var39 = var28;
						var41 = var26;
						var43 = par2 + 1;
						var47 = par2;
						var45 = par4 + 1 - var32;
						var49 = par4 + 1 - var32;
					} else if(var57 == 2)
					{
						var39 = var26;
						var41 = var24;
						var43 = par2 + var32;
						var47 = par2 + var32;
						var45 = par4 + 1;
						var49 = par4;
					} else
					{
						var39 = var30;
						var41 = var28;
						var43 = par2 + 1 - var32;
						var47 = par2 + 1 - var32;
						var45 = par4;
						var49 = par4 + 1;
					}
					var13 = true;
					float var51 = var60.getInterpolatedU(0.0D);
					var52 = var60.getInterpolatedU(8.0D);
					var53 = var60.getInterpolatedV((1.0D - var39) * 16.0D * 0.5D);
					float var54 = var60.getInterpolatedV((1.0D - var41) * 16.0D * 0.5D);
					float var55 = var60.getInterpolatedV(8.0D);
					var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, var59, par3, var37));
					float var56 = 1.0F;
					if(var57 < 2)
					{
						var56 *= var16;
					} else
					{
						var56 *= var17;
					}
					var5.setColorOpaque_F(var15 * var56 * var7, var15 * var56 * var8, var15 * var56 * var9);
					var5.addVertexWithUV(var43, par3 + var39, var45, var51, var53);
					var5.addVertexWithUV(var47, par3 + var41, var49, var52, var54);
					var5.addVertexWithUV(var47, par3 + 0, var49, var52, var55);
					var5.addVertexWithUV(var43, par3 + 0, var45, var51, var55);
				}
			}
			renderMinY = var18;
			renderMaxY = var20;
			return var13;
		}
	}
	
	private boolean renderBlockHopper(BlockHopper par1BlockHopper, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = par1BlockHopper.colorMultiplier(blockAccess, par2, par3, par4);
		float var8 = (var7 >> 16 & 255) / 255.0F;
		float var9 = (var7 >> 8 & 255) / 255.0F;
		float var10 = (var7 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
			float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
			float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
			var8 = var11;
			var9 = var12;
			var10 = var13;
		}
		var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		return renderBlockHopperMetadata(par1BlockHopper, par2, par3, par4, blockAccess.getBlockMetadata(par2, par3, par4), false);
	}
	
	private boolean renderBlockHopperMetadata(BlockHopper par1BlockHopper, int par2, int par3, int par4, int par5, boolean par6)
	{
		Tessellator var7 = Tessellator.instance;
		int var8 = BlockHopper.getDirectionFromMetadata(par5);
		double var9 = 0.625D;
		setRenderBounds(0.0D, var9, 0.0D, 1.0D, 1.0D, 1.0D);
		if(par6)
		{
			var7.startDrawingQuads();
			var7.setNormal(0.0F, -1.0F, 0.0F);
			renderFaceYNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockHopper, 0, par5));
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 1.0F, 0.0F);
			renderFaceYPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockHopper, 1, par5));
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 0.0F, -1.0F);
			renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockHopper, 2, par5));
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 0.0F, 1.0F);
			renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockHopper, 3, par5));
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(-1.0F, 0.0F, 0.0F);
			renderFaceXNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockHopper, 4, par5));
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(1.0F, 0.0F, 0.0F);
			renderFaceXPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, getBlockIconFromSideAndMetadata(par1BlockHopper, 5, par5));
			var7.draw();
		} else
		{
			renderStandardBlock(par1BlockHopper, par2, par3, par4);
		}
		float var13;
		if(!par6)
		{
			var7.setBrightness(par1BlockHopper.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
			float var11 = 1.0F;
			int var12 = par1BlockHopper.colorMultiplier(blockAccess, par2, par3, par4);
			var13 = (var12 >> 16 & 255) / 255.0F;
			float var14 = (var12 >> 8 & 255) / 255.0F;
			float var15 = (var12 & 255) / 255.0F;
			if(EntityRenderer.anaglyphEnable)
			{
				float var16 = (var13 * 30.0F + var14 * 59.0F + var15 * 11.0F) / 100.0F;
				float var17 = (var13 * 30.0F + var14 * 70.0F) / 100.0F;
				float var18 = (var13 * 30.0F + var15 * 70.0F) / 100.0F;
				var13 = var16;
				var14 = var17;
				var15 = var18;
			}
			var7.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
		}
		Icon var24 = BlockHopper.getHopperIcon("hopper");
		Icon var25 = BlockHopper.getHopperIcon("hopper_inside");
		var13 = 0.125F;
		if(par6)
		{
			var7.startDrawingQuads();
			var7.setNormal(1.0F, 0.0F, 0.0F);
			renderFaceXPos(par1BlockHopper, -1.0F + var13, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(-1.0F, 0.0F, 0.0F);
			renderFaceXNeg(par1BlockHopper, 1.0F - var13, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 0.0F, 1.0F);
			renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, -1.0F + var13, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 0.0F, -1.0F);
			renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 1.0F - var13, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 1.0F, 0.0F);
			renderFaceYPos(par1BlockHopper, 0.0D, -1.0D + var9, 0.0D, var25);
			var7.draw();
		} else
		{
			renderFaceXPos(par1BlockHopper, par2 - 1.0F + var13, par3, par4, var24);
			renderFaceXNeg(par1BlockHopper, par2 + 1.0F - var13, par3, par4, var24);
			renderFaceZPos(par1BlockHopper, par2, par3, par4 - 1.0F + var13, var24);
			renderFaceZNeg(par1BlockHopper, par2, par3, par4 + 1.0F - var13, var24);
			renderFaceYPos(par1BlockHopper, par2, par3 - 1.0F + var9, par4, var25);
		}
		setOverrideBlockTexture(var24);
		double var26 = 0.25D;
		double var27 = 0.25D;
		setRenderBounds(var26, var27, var26, 1.0D - var26, var9 - 0.002D, 1.0D - var26);
		if(par6)
		{
			var7.startDrawingQuads();
			var7.setNormal(1.0F, 0.0F, 0.0F);
			renderFaceXPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(-1.0F, 0.0F, 0.0F);
			renderFaceXNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 0.0F, 1.0F);
			renderFaceZPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 0.0F, -1.0F);
			renderFaceZNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, 1.0F, 0.0F);
			renderFaceYPos(par1BlockHopper, 0.0D, 0.0D, 0.0D, var24);
			var7.draw();
			var7.startDrawingQuads();
			var7.setNormal(0.0F, -1.0F, 0.0F);
			renderFaceYNeg(par1BlockHopper, 0.0D, 0.0D, 0.0D, var24);
			var7.draw();
		} else
		{
			renderStandardBlock(par1BlockHopper, par2, par3, par4);
		}
		if(!par6)
		{
			double var20 = 0.375D;
			double var22 = 0.25D;
			setOverrideBlockTexture(var24);
			if(var8 == 0)
			{
				setRenderBounds(var20, 0.0D, var20, 1.0D - var20, 0.25D, 1.0D - var20);
				renderStandardBlock(par1BlockHopper, par2, par3, par4);
			}
			if(var8 == 2)
			{
				setRenderBounds(var20, var27, 0.0D, 1.0D - var20, var27 + var22, var26);
				renderStandardBlock(par1BlockHopper, par2, par3, par4);
			}
			if(var8 == 3)
			{
				setRenderBounds(var20, var27, 1.0D - var26, 1.0D - var20, var27 + var22, 1.0D);
				renderStandardBlock(par1BlockHopper, par2, par3, par4);
			}
			if(var8 == 4)
			{
				setRenderBounds(0.0D, var27, var20, var26, var27 + var22, 1.0D - var20);
				renderStandardBlock(par1BlockHopper, par2, par3, par4);
			}
			if(var8 == 5)
			{
				setRenderBounds(1.0D - var26, var27, var20, 1.0D, var27 + var22, 1.0D - var20);
				renderStandardBlock(par1BlockHopper, par2, par3, par4);
			}
		}
		clearOverrideBlockTexture();
		return true;
	}
	
	public boolean renderBlockLadder(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		Icon var6 = getBlockIconFromSide(par1Block, 0);
		if(hasOverrideBlockTexture())
		{
			var6 = overrideBlockTexture;
		}
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var7 = 1.0F;
		var5.setColorOpaque_F(var7, var7, var7);
		double var20 = var6.getMinU();
		double var9 = var6.getMinV();
		double var11 = var6.getMaxU();
		double var13 = var6.getMaxV();
		int var15 = blockAccess.getBlockMetadata(par2, par3, par4);
		double var16 = 0.0D;
		double var18 = 0.05000000074505806D;
		if(var15 == 5)
		{
			var5.addVertexWithUV(par2 + var18, par3 + 1 + var16, par4 + 1 + var16, var20, var9);
			var5.addVertexWithUV(par2 + var18, par3 + 0 - var16, par4 + 1 + var16, var20, var13);
			var5.addVertexWithUV(par2 + var18, par3 + 0 - var16, par4 + 0 - var16, var11, var13);
			var5.addVertexWithUV(par2 + var18, par3 + 1 + var16, par4 + 0 - var16, var11, var9);
		}
		if(var15 == 4)
		{
			var5.addVertexWithUV(par2 + 1 - var18, par3 + 0 - var16, par4 + 1 + var16, var11, var13);
			var5.addVertexWithUV(par2 + 1 - var18, par3 + 1 + var16, par4 + 1 + var16, var11, var9);
			var5.addVertexWithUV(par2 + 1 - var18, par3 + 1 + var16, par4 + 0 - var16, var20, var9);
			var5.addVertexWithUV(par2 + 1 - var18, par3 + 0 - var16, par4 + 0 - var16, var20, var13);
		}
		if(var15 == 3)
		{
			var5.addVertexWithUV(par2 + 1 + var16, par3 + 0 - var16, par4 + var18, var11, var13);
			var5.addVertexWithUV(par2 + 1 + var16, par3 + 1 + var16, par4 + var18, var11, var9);
			var5.addVertexWithUV(par2 + 0 - var16, par3 + 1 + var16, par4 + var18, var20, var9);
			var5.addVertexWithUV(par2 + 0 - var16, par3 + 0 - var16, par4 + var18, var20, var13);
		}
		if(var15 == 2)
		{
			var5.addVertexWithUV(par2 + 1 + var16, par3 + 1 + var16, par4 + 1 - var18, var20, var9);
			var5.addVertexWithUV(par2 + 1 + var16, par3 + 0 - var16, par4 + 1 - var18, var20, var13);
			var5.addVertexWithUV(par2 + 0 - var16, par3 + 0 - var16, par4 + 1 - var18, var11, var13);
			var5.addVertexWithUV(par2 + 0 - var16, par3 + 1 + var16, par4 + 1 - var18, var11, var9);
		}
		return true;
	}
	
	public boolean renderBlockLever(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 7;
		boolean var7 = (var5 & 8) > 0;
		Tessellator var8 = Tessellator.instance;
		boolean var9 = hasOverrideBlockTexture();
		if(!var9)
		{
			setOverrideBlockTexture(this.getBlockIcon(Block.cobblestone));
		}
		float var10 = 0.25F;
		float var11 = 0.1875F;
		float var12 = 0.1875F;
		if(var6 == 5)
		{
			setRenderBounds(0.5F - var11, 0.0D, 0.5F - var10, 0.5F + var11, var12, 0.5F + var10);
		} else if(var6 == 6)
		{
			setRenderBounds(0.5F - var10, 0.0D, 0.5F - var11, 0.5F + var10, var12, 0.5F + var11);
		} else if(var6 == 4)
		{
			setRenderBounds(0.5F - var11, 0.5F - var10, 1.0F - var12, 0.5F + var11, 0.5F + var10, 1.0D);
		} else if(var6 == 3)
		{
			setRenderBounds(0.5F - var11, 0.5F - var10, 0.0D, 0.5F + var11, 0.5F + var10, var12);
		} else if(var6 == 2)
		{
			setRenderBounds(1.0F - var12, 0.5F - var10, 0.5F - var11, 1.0D, 0.5F + var10, 0.5F + var11);
		} else if(var6 == 1)
		{
			setRenderBounds(0.0D, 0.5F - var10, 0.5F - var11, var12, 0.5F + var10, 0.5F + var11);
		} else if(var6 == 0)
		{
			setRenderBounds(0.5F - var10, 1.0F - var12, 0.5F - var11, 0.5F + var10, 1.0D, 0.5F + var11);
		} else if(var6 == 7)
		{
			setRenderBounds(0.5F - var11, 1.0F - var12, 0.5F - var10, 0.5F + var11, 1.0D, 0.5F + var10);
		}
		renderStandardBlock(par1Block, par2, par3, par4);
		if(!var9)
		{
			clearOverrideBlockTexture();
		}
		var8.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var13 = 1.0F;
		if(Block.lightValue[par1Block.blockID] > 0)
		{
			var13 = 1.0F;
		}
		var8.setColorOpaque_F(var13, var13, var13);
		Icon var14 = getBlockIconFromSide(par1Block, 0);
		if(hasOverrideBlockTexture())
		{
			var14 = overrideBlockTexture;
		}
		double var15 = var14.getMinU();
		double var17 = var14.getMinV();
		double var19 = var14.getMaxU();
		double var21 = var14.getMaxV();
		Vec3[] var23 = new Vec3[8];
		float var24 = 0.0625F;
		float var25 = 0.0625F;
		float var26 = 0.625F;
		var23[0] = blockAccess.getWorldVec3Pool().getVecFromPool(-var24, 0.0D, -var25);
		var23[1] = blockAccess.getWorldVec3Pool().getVecFromPool(var24, 0.0D, -var25);
		var23[2] = blockAccess.getWorldVec3Pool().getVecFromPool(var24, 0.0D, var25);
		var23[3] = blockAccess.getWorldVec3Pool().getVecFromPool(-var24, 0.0D, var25);
		var23[4] = blockAccess.getWorldVec3Pool().getVecFromPool(-var24, var26, -var25);
		var23[5] = blockAccess.getWorldVec3Pool().getVecFromPool(var24, var26, -var25);
		var23[6] = blockAccess.getWorldVec3Pool().getVecFromPool(var24, var26, var25);
		var23[7] = blockAccess.getWorldVec3Pool().getVecFromPool(-var24, var26, var25);
		for(int var27 = 0; var27 < 8; ++var27)
		{
			if(var7)
			{
				var23[var27].zCoord -= 0.0625D;
				var23[var27].rotateAroundX((float) Math.PI * 2F / 9F);
			} else
			{
				var23[var27].zCoord += 0.0625D;
				var23[var27].rotateAroundX(-((float) Math.PI * 2F / 9F));
			}
			if(var6 == 0 || var6 == 7)
			{
				var23[var27].rotateAroundZ((float) Math.PI);
			}
			if(var6 == 6 || var6 == 0)
			{
				var23[var27].rotateAroundY((float) Math.PI / 2F);
			}
			if(var6 > 0 && var6 < 5)
			{
				var23[var27].yCoord -= 0.375D;
				var23[var27].rotateAroundX((float) Math.PI / 2F);
				if(var6 == 4)
				{
					var23[var27].rotateAroundY(0.0F);
				}
				if(var6 == 3)
				{
					var23[var27].rotateAroundY((float) Math.PI);
				}
				if(var6 == 2)
				{
					var23[var27].rotateAroundY((float) Math.PI / 2F);
				}
				if(var6 == 1)
				{
					var23[var27].rotateAroundY(-((float) Math.PI / 2F));
				}
				var23[var27].xCoord += par2 + 0.5D;
				var23[var27].yCoord += par3 + 0.5F;
				var23[var27].zCoord += par4 + 0.5D;
			} else if(var6 != 0 && var6 != 7)
			{
				var23[var27].xCoord += par2 + 0.5D;
				var23[var27].yCoord += par3 + 0.125F;
				var23[var27].zCoord += par4 + 0.5D;
			} else
			{
				var23[var27].xCoord += par2 + 0.5D;
				var23[var27].yCoord += par3 + 0.875F;
				var23[var27].zCoord += par4 + 0.5D;
			}
		}
		Vec3 var32 = null;
		Vec3 var28 = null;
		Vec3 var29 = null;
		Vec3 var30 = null;
		for(int var31 = 0; var31 < 6; ++var31)
		{
			if(var31 == 0)
			{
				var15 = var14.getInterpolatedU(7.0D);
				var17 = var14.getInterpolatedV(6.0D);
				var19 = var14.getInterpolatedU(9.0D);
				var21 = var14.getInterpolatedV(8.0D);
			} else if(var31 == 2)
			{
				var15 = var14.getInterpolatedU(7.0D);
				var17 = var14.getInterpolatedV(6.0D);
				var19 = var14.getInterpolatedU(9.0D);
				var21 = var14.getMaxV();
			}
			if(var31 == 0)
			{
				var32 = var23[0];
				var28 = var23[1];
				var29 = var23[2];
				var30 = var23[3];
			} else if(var31 == 1)
			{
				var32 = var23[7];
				var28 = var23[6];
				var29 = var23[5];
				var30 = var23[4];
			} else if(var31 == 2)
			{
				var32 = var23[1];
				var28 = var23[0];
				var29 = var23[4];
				var30 = var23[5];
			} else if(var31 == 3)
			{
				var32 = var23[2];
				var28 = var23[1];
				var29 = var23[5];
				var30 = var23[6];
			} else if(var31 == 4)
			{
				var32 = var23[3];
				var28 = var23[2];
				var29 = var23[6];
				var30 = var23[7];
			} else if(var31 == 5)
			{
				var32 = var23[0];
				var28 = var23[3];
				var29 = var23[7];
				var30 = var23[4];
			}
			var8.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var15, var21);
			var8.addVertexWithUV(var28.xCoord, var28.yCoord, var28.zCoord, var19, var21);
			var8.addVertexWithUV(var29.xCoord, var29.yCoord, var29.zCoord, var19, var17);
			var8.addVertexWithUV(var30.xCoord, var30.yCoord, var30.zCoord, var15, var17);
		}
		return true;
	}
	
	public boolean renderBlockLilyPad(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		Icon var6 = getBlockIconFromSide(par1Block, 1);
		if(hasOverrideBlockTexture())
		{
			var6 = overrideBlockTexture;
		}
		float var7 = 0.015625F;
		double var8 = var6.getMinU();
		double var10 = var6.getMinV();
		double var12 = var6.getMaxU();
		double var14 = var6.getMaxV();
		long var16 = par2 * 3129871 ^ par4 * 116129781L ^ par3;
		var16 = var16 * var16 * 42317861L + var16 * 11L;
		int var18 = (int) (var16 >> 16 & 3L);
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var19 = par2 + 0.5F;
		float var20 = par4 + 0.5F;
		float var21 = (var18 & 1) * 0.5F * (1 - var18 / 2 % 2 * 2);
		float var22 = (var18 + 1 & 1) * 0.5F * (1 - (var18 + 1) / 2 % 2 * 2);
		var5.setColorOpaque_I(par1Block.getBlockColor());
		var5.addVertexWithUV(var19 + var21 - var22, par3 + var7, var20 + var21 + var22, var8, var10);
		var5.addVertexWithUV(var19 + var21 + var22, par3 + var7, var20 - var21 + var22, var12, var10);
		var5.addVertexWithUV(var19 - var21 + var22, par3 + var7, var20 - var21 - var22, var12, var14);
		var5.addVertexWithUV(var19 - var21 - var22, par3 + var7, var20 + var21 - var22, var8, var14);
		var5.setColorOpaque_I((par1Block.getBlockColor() & 16711422) >> 1);
		var5.addVertexWithUV(var19 - var21 - var22, par3 + var7, var20 + var21 - var22, var8, var14);
		var5.addVertexWithUV(var19 - var21 + var22, par3 + var7, var20 - var21 - var22, var12, var14);
		var5.addVertexWithUV(var19 + var21 + var22, par3 + var7, var20 - var21 + var22, var12, var10);
		var5.addVertexWithUV(var19 + var21 - var22, par3 + var7, var20 + var21 + var22, var8, var10);
		return true;
	}
	
	public boolean renderBlockLog(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 12;
		if(var6 == 4)
		{
			uvRotateEast = 1;
			uvRotateWest = 1;
			uvRotateTop = 1;
			uvRotateBottom = 1;
		} else if(var6 == 8)
		{
			uvRotateSouth = 1;
			uvRotateNorth = 1;
		}
		boolean var7 = renderStandardBlock(par1Block, par2, par3, par4);
		uvRotateSouth = 0;
		uvRotateEast = 0;
		uvRotateWest = 0;
		uvRotateNorth = 0;
		uvRotateTop = 0;
		uvRotateBottom = 0;
		return var7;
	}
	
	public boolean renderBlockMinecartTrack(BlockRailBase par1BlockRailBase, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		Icon var7 = getBlockIconFromSideAndMetadata(par1BlockRailBase, 0, var6);
		if(hasOverrideBlockTexture())
		{
			var7 = overrideBlockTexture;
		}
		if(par1BlockRailBase.isPowered())
		{
			var6 &= 7;
		}
		var5.setBrightness(par1BlockRailBase.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double var8 = var7.getMinU();
		double var10 = var7.getMinV();
		double var12 = var7.getMaxU();
		double var14 = var7.getMaxV();
		double var16 = 0.0625D;
		double var18 = par2 + 1;
		double var20 = par2 + 1;
		double var22 = par2 + 0;
		double var24 = par2 + 0;
		double var26 = par4 + 0;
		double var28 = par4 + 1;
		double var30 = par4 + 1;
		double var32 = par4 + 0;
		double var34 = par3 + var16;
		double var36 = par3 + var16;
		double var38 = par3 + var16;
		double var40 = par3 + var16;
		if(var6 != 1 && var6 != 2 && var6 != 3 && var6 != 7)
		{
			if(var6 == 8)
			{
				var18 = var20 = par2 + 0;
				var22 = var24 = par2 + 1;
				var26 = var32 = par4 + 1;
				var28 = var30 = par4 + 0;
			} else if(var6 == 9)
			{
				var18 = var24 = par2 + 0;
				var20 = var22 = par2 + 1;
				var26 = var28 = par4 + 0;
				var30 = var32 = par4 + 1;
			}
		} else
		{
			var18 = var24 = par2 + 1;
			var20 = var22 = par2 + 0;
			var26 = var28 = par4 + 1;
			var30 = var32 = par4 + 0;
		}
		if(var6 != 2 && var6 != 4)
		{
			if(var6 == 3 || var6 == 5)
			{
				++var36;
				++var38;
			}
		} else
		{
			++var34;
			++var40;
		}
		var5.addVertexWithUV(var18, var34, var26, var12, var10);
		var5.addVertexWithUV(var20, var36, var28, var12, var14);
		var5.addVertexWithUV(var22, var38, var30, var8, var14);
		var5.addVertexWithUV(var24, var40, var32, var8, var10);
		var5.addVertexWithUV(var24, var40, var32, var8, var10);
		var5.addVertexWithUV(var22, var38, var30, var8, var14);
		var5.addVertexWithUV(var20, var36, var28, var12, var14);
		var5.addVertexWithUV(var18, var34, var26, var12, var10);
		return true;
	}
	
	public boolean renderBlockPane(BlockPane par1BlockPane, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getHeight();
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1BlockPane.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var7 = 1.0F;
		int var8 = par1BlockPane.colorMultiplier(blockAccess, par2, par3, par4);
		float var9 = (var8 >> 16 & 255) / 255.0F;
		float var10 = (var8 >> 8 & 255) / 255.0F;
		float var11 = (var8 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
			float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
			float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
			var9 = var12;
			var10 = var13;
			var11 = var14;
		}
		var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
		Icon var64;
		Icon var65;
		int var66;
		if(hasOverrideBlockTexture())
		{
			var64 = overrideBlockTexture;
			var65 = overrideBlockTexture;
		} else
		{
			var66 = blockAccess.getBlockMetadata(par2, par3, par4);
			var64 = getBlockIconFromSideAndMetadata(par1BlockPane, 0, var66);
			var65 = par1BlockPane.getSideTextureIndex();
		}
		var66 = var64.getOriginX();
		int var15 = var64.getOriginY();
		double var16 = var64.getMinU();
		double var18 = var64.getInterpolatedU(8.0D);
		double var20 = var64.getMaxU();
		double var22 = var64.getMinV();
		double var24 = var64.getMaxV();
		int var26 = var65.getOriginX();
		int var27 = var65.getOriginY();
		double var28 = var65.getInterpolatedU(7.0D);
		double var30 = var65.getInterpolatedU(9.0D);
		double var32 = var65.getMinV();
		double var34 = var65.getInterpolatedV(8.0D);
		double var36 = var65.getMaxV();
		double var38 = par2;
		double var40 = par2 + 0.5D;
		double var42 = par2 + 1;
		double var44 = par4;
		double var46 = par4 + 0.5D;
		double var48 = par4 + 1;
		double var50 = par2 + 0.5D - 0.0625D;
		double var52 = par2 + 0.5D + 0.0625D;
		double var54 = par4 + 0.5D - 0.0625D;
		double var56 = par4 + 0.5D + 0.0625D;
		boolean var58 = par1BlockPane.canThisPaneConnectToThisBlockID(blockAccess.getBlockId(par2, par3, par4 - 1));
		boolean var59 = par1BlockPane.canThisPaneConnectToThisBlockID(blockAccess.getBlockId(par2, par3, par4 + 1));
		boolean var60 = par1BlockPane.canThisPaneConnectToThisBlockID(blockAccess.getBlockId(par2 - 1, par3, par4));
		boolean var61 = par1BlockPane.canThisPaneConnectToThisBlockID(blockAccess.getBlockId(par2 + 1, par3, par4));
		boolean var62 = par1BlockPane.shouldSideBeRendered(blockAccess, par2, par3 + 1, par4, 1);
		boolean var63 = par1BlockPane.shouldSideBeRendered(blockAccess, par2, par3 - 1, par4, 0);
		if((!var60 || !var61) && (var60 || var61 || var58 || var59))
		{
			if(var60 && !var61)
			{
				var6.addVertexWithUV(var38, par3 + 1, var46, var16, var22);
				var6.addVertexWithUV(var38, par3 + 0, var46, var16, var24);
				var6.addVertexWithUV(var40, par3 + 0, var46, var18, var24);
				var6.addVertexWithUV(var40, par3 + 1, var46, var18, var22);
				var6.addVertexWithUV(var40, par3 + 1, var46, var16, var22);
				var6.addVertexWithUV(var40, par3 + 0, var46, var16, var24);
				var6.addVertexWithUV(var38, par3 + 0, var46, var18, var24);
				var6.addVertexWithUV(var38, par3 + 1, var46, var18, var22);
				if(!var59 && !var58)
				{
					var6.addVertexWithUV(var40, par3 + 1, var56, var28, var32);
					var6.addVertexWithUV(var40, par3 + 0, var56, var28, var36);
					var6.addVertexWithUV(var40, par3 + 0, var54, var30, var36);
					var6.addVertexWithUV(var40, par3 + 1, var54, var30, var32);
					var6.addVertexWithUV(var40, par3 + 1, var54, var28, var32);
					var6.addVertexWithUV(var40, par3 + 0, var54, var28, var36);
					var6.addVertexWithUV(var40, par3 + 0, var56, var30, var36);
					var6.addVertexWithUV(var40, par3 + 1, var56, var30, var32);
				}
				if(var62 || par3 < var5 - 1 && blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var34);
				}
				if(var63 || par3 > 1 && blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var38, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var38, par3 - 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var38, par3 - 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var38, par3 - 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var34);
				}
			} else if(!var60 && var61)
			{
				var6.addVertexWithUV(var40, par3 + 1, var46, var18, var22);
				var6.addVertexWithUV(var40, par3 + 0, var46, var18, var24);
				var6.addVertexWithUV(var42, par3 + 0, var46, var20, var24);
				var6.addVertexWithUV(var42, par3 + 1, var46, var20, var22);
				var6.addVertexWithUV(var42, par3 + 1, var46, var18, var22);
				var6.addVertexWithUV(var42, par3 + 0, var46, var18, var24);
				var6.addVertexWithUV(var40, par3 + 0, var46, var20, var24);
				var6.addVertexWithUV(var40, par3 + 1, var46, var20, var22);
				if(!var59 && !var58)
				{
					var6.addVertexWithUV(var40, par3 + 1, var54, var28, var32);
					var6.addVertexWithUV(var40, par3 + 0, var54, var28, var36);
					var6.addVertexWithUV(var40, par3 + 0, var56, var30, var36);
					var6.addVertexWithUV(var40, par3 + 1, var56, var30, var32);
					var6.addVertexWithUV(var40, par3 + 1, var56, var28, var32);
					var6.addVertexWithUV(var40, par3 + 0, var56, var28, var36);
					var6.addVertexWithUV(var40, par3 + 0, var54, var30, var36);
					var6.addVertexWithUV(var40, par3 + 1, var54, var30, var32);
				}
				if(var62 || par3 < var5 - 1 && blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var32);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var54, var28, var32);
				}
				if(var63 || par3 > 1 && blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var42, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var42, par3 - 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var32);
					var6.addVertexWithUV(var42, par3 - 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var42, par3 - 0.01D, var54, var28, var32);
				}
			}
		} else
		{
			var6.addVertexWithUV(var38, par3 + 1, var46, var16, var22);
			var6.addVertexWithUV(var38, par3 + 0, var46, var16, var24);
			var6.addVertexWithUV(var42, par3 + 0, var46, var20, var24);
			var6.addVertexWithUV(var42, par3 + 1, var46, var20, var22);
			var6.addVertexWithUV(var42, par3 + 1, var46, var16, var22);
			var6.addVertexWithUV(var42, par3 + 0, var46, var16, var24);
			var6.addVertexWithUV(var38, par3 + 0, var46, var20, var24);
			var6.addVertexWithUV(var38, par3 + 1, var46, var20, var22);
			if(var62)
			{
				var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var56, var30, var36);
				var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var56, var30, var32);
				var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var54, var28, var32);
				var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var54, var28, var36);
				var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var56, var30, var36);
				var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var56, var30, var32);
				var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var54, var28, var32);
				var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var54, var28, var36);
			} else
			{
				if(par3 < var5 - 1 && blockAccess.isAirBlock(par2 - 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var38, par3 + 1 + 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var34);
				}
				if(par3 < var5 - 1 && blockAccess.isAirBlock(par2 + 1, par3 + 1, par4))
				{
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var32);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 + 1 + 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var42, par3 + 1 + 0.01D, var54, var28, var32);
				}
			}
			if(var63)
			{
				var6.addVertexWithUV(var38, par3 - 0.01D, var56, var30, var36);
				var6.addVertexWithUV(var42, par3 - 0.01D, var56, var30, var32);
				var6.addVertexWithUV(var42, par3 - 0.01D, var54, var28, var32);
				var6.addVertexWithUV(var38, par3 - 0.01D, var54, var28, var36);
				var6.addVertexWithUV(var42, par3 - 0.01D, var56, var30, var36);
				var6.addVertexWithUV(var38, par3 - 0.01D, var56, var30, var32);
				var6.addVertexWithUV(var38, par3 - 0.01D, var54, var28, var32);
				var6.addVertexWithUV(var42, par3 - 0.01D, var54, var28, var36);
			} else
			{
				if(par3 > 1 && blockAccess.isAirBlock(par2 - 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var38, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var38, par3 - 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var38, par3 - 0.01D, var56, var30, var36);
					var6.addVertexWithUV(var38, par3 - 0.01D, var54, var28, var36);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var34);
				}
				if(par3 > 1 && blockAccess.isAirBlock(par2 + 1, par3 - 1, par4))
				{
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var42, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var42, par3 - 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var32);
					var6.addVertexWithUV(var42, par3 - 0.01D, var56, var30, var32);
					var6.addVertexWithUV(var40, par3 - 0.01D, var56, var30, var34);
					var6.addVertexWithUV(var40, par3 - 0.01D, var54, var28, var34);
					var6.addVertexWithUV(var42, par3 - 0.01D, var54, var28, var32);
				}
			}
		}
		if((!var58 || !var59) && (var60 || var61 || var58 || var59))
		{
			if(var58 && !var59)
			{
				var6.addVertexWithUV(var40, par3 + 1, var44, var16, var22);
				var6.addVertexWithUV(var40, par3 + 0, var44, var16, var24);
				var6.addVertexWithUV(var40, par3 + 0, var46, var18, var24);
				var6.addVertexWithUV(var40, par3 + 1, var46, var18, var22);
				var6.addVertexWithUV(var40, par3 + 1, var46, var16, var22);
				var6.addVertexWithUV(var40, par3 + 0, var46, var16, var24);
				var6.addVertexWithUV(var40, par3 + 0, var44, var18, var24);
				var6.addVertexWithUV(var40, par3 + 1, var44, var18, var22);
				if(!var61 && !var60)
				{
					var6.addVertexWithUV(var50, par3 + 1, var46, var28, var32);
					var6.addVertexWithUV(var50, par3 + 0, var46, var28, var36);
					var6.addVertexWithUV(var52, par3 + 0, var46, var30, var36);
					var6.addVertexWithUV(var52, par3 + 1, var46, var30, var32);
					var6.addVertexWithUV(var52, par3 + 1, var46, var28, var32);
					var6.addVertexWithUV(var52, par3 + 0, var46, var28, var36);
					var6.addVertexWithUV(var50, par3 + 0, var46, var30, var36);
					var6.addVertexWithUV(var50, par3 + 1, var46, var30, var32);
				}
				if(var62 || par3 < var5 - 1 && blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))
				{
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var44, var30, var32);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var44, var28, var32);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var30, var32);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var44, var30, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var44, var28, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var28, var32);
				}
				if(var63 || par3 > 1 && blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))
				{
					var6.addVertexWithUV(var50, par3 - 0.005D, var44, var30, var32);
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var44, var28, var32);
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var30, var32);
					var6.addVertexWithUV(var50, par3 - 0.005D, var44, var30, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var44, var28, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var28, var32);
				}
			} else if(!var58 && var59)
			{
				var6.addVertexWithUV(var40, par3 + 1, var46, var18, var22);
				var6.addVertexWithUV(var40, par3 + 0, var46, var18, var24);
				var6.addVertexWithUV(var40, par3 + 0, var48, var20, var24);
				var6.addVertexWithUV(var40, par3 + 1, var48, var20, var22);
				var6.addVertexWithUV(var40, par3 + 1, var48, var18, var22);
				var6.addVertexWithUV(var40, par3 + 0, var48, var18, var24);
				var6.addVertexWithUV(var40, par3 + 0, var46, var20, var24);
				var6.addVertexWithUV(var40, par3 + 1, var46, var20, var22);
				if(!var61 && !var60)
				{
					var6.addVertexWithUV(var52, par3 + 1, var46, var28, var32);
					var6.addVertexWithUV(var52, par3 + 0, var46, var28, var36);
					var6.addVertexWithUV(var50, par3 + 0, var46, var30, var36);
					var6.addVertexWithUV(var50, par3 + 1, var46, var30, var32);
					var6.addVertexWithUV(var50, par3 + 1, var46, var28, var32);
					var6.addVertexWithUV(var50, par3 + 0, var46, var28, var36);
					var6.addVertexWithUV(var52, par3 + 0, var46, var30, var36);
					var6.addVertexWithUV(var52, par3 + 1, var46, var30, var32);
				}
				if(var62 || par3 < var5 - 1 && blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))
				{
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var48, var28, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var48, var30, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var48, var28, var34);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var28, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var30, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var48, var30, var34);
				}
				if(var63 || par3 > 1 && blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))
				{
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var50, par3 - 0.005D, var48, var28, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var48, var30, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var50, par3 - 0.005D, var48, var28, var34);
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var28, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var30, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var48, var30, var34);
				}
			}
		} else
		{
			var6.addVertexWithUV(var40, par3 + 1, var48, var16, var22);
			var6.addVertexWithUV(var40, par3 + 0, var48, var16, var24);
			var6.addVertexWithUV(var40, par3 + 0, var44, var20, var24);
			var6.addVertexWithUV(var40, par3 + 1, var44, var20, var22);
			var6.addVertexWithUV(var40, par3 + 1, var44, var16, var22);
			var6.addVertexWithUV(var40, par3 + 0, var44, var16, var24);
			var6.addVertexWithUV(var40, par3 + 0, var48, var20, var24);
			var6.addVertexWithUV(var40, par3 + 1, var48, var20, var22);
			if(var62)
			{
				var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var48, var30, var36);
				var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var44, var30, var32);
				var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var44, var28, var32);
				var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var48, var28, var36);
				var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var44, var30, var36);
				var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var48, var30, var32);
				var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var48, var28, var32);
				var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var44, var28, var36);
			} else
			{
				if(par3 < var5 - 1 && blockAccess.isAirBlock(par2, par3 + 1, par4 - 1))
				{
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var44, var30, var32);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var44, var28, var32);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var30, var32);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var44, var30, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var44, var28, var34);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var28, var32);
				}
				if(par3 < var5 - 1 && blockAccess.isAirBlock(par2, par3 + 1, par4 + 1))
				{
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var48, var28, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var48, var30, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var48, var28, var34);
					var6.addVertexWithUV(var50, par3 + 1 + 0.005D, var46, var28, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var46, var30, var36);
					var6.addVertexWithUV(var52, par3 + 1 + 0.005D, var48, var30, var34);
				}
			}
			if(var63)
			{
				var6.addVertexWithUV(var52, par3 - 0.005D, var48, var30, var36);
				var6.addVertexWithUV(var52, par3 - 0.005D, var44, var30, var32);
				var6.addVertexWithUV(var50, par3 - 0.005D, var44, var28, var32);
				var6.addVertexWithUV(var50, par3 - 0.005D, var48, var28, var36);
				var6.addVertexWithUV(var52, par3 - 0.005D, var44, var30, var36);
				var6.addVertexWithUV(var52, par3 - 0.005D, var48, var30, var32);
				var6.addVertexWithUV(var50, par3 - 0.005D, var48, var28, var32);
				var6.addVertexWithUV(var50, par3 - 0.005D, var44, var28, var36);
			} else
			{
				if(par3 > 1 && blockAccess.isAirBlock(par2, par3 - 1, par4 - 1))
				{
					var6.addVertexWithUV(var50, par3 - 0.005D, var44, var30, var32);
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var44, var28, var32);
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var30, var32);
					var6.addVertexWithUV(var50, par3 - 0.005D, var44, var30, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var44, var28, var34);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var28, var32);
				}
				if(par3 > 1 && blockAccess.isAirBlock(par2, par3 - 1, par4 + 1))
				{
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var28, var34);
					var6.addVertexWithUV(var50, par3 - 0.005D, var48, var28, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var48, var30, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var30, var34);
					var6.addVertexWithUV(var50, par3 - 0.005D, var48, var28, var34);
					var6.addVertexWithUV(var50, par3 - 0.005D, var46, var28, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var46, var30, var36);
					var6.addVertexWithUV(var52, par3 - 0.005D, var48, var30, var34);
				}
			}
		}
		return true;
	}
	
	public boolean renderBlockQuartz(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getBlockMetadata(par2, par3, par4);
		if(var5 == 3)
		{
			uvRotateEast = 1;
			uvRotateWest = 1;
			uvRotateTop = 1;
			uvRotateBottom = 1;
		} else if(var5 == 4)
		{
			uvRotateSouth = 1;
			uvRotateNorth = 1;
		}
		boolean var6 = renderStandardBlock(par1Block, par2, par3, par4);
		uvRotateSouth = 0;
		uvRotateEast = 0;
		uvRotateWest = 0;
		uvRotateNorth = 0;
		uvRotateTop = 0;
		uvRotateBottom = 0;
		return var6;
	}
	
	private boolean renderBlockRedstoneLogic(BlockRedstoneLogic par1BlockRedstoneLogic, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		renderBlockRedstoneLogicMetadata(par1BlockRedstoneLogic, par2, par3, par4, blockAccess.getBlockMetadata(par2, par3, par4) & 3);
		return true;
	}
	
	private void renderBlockRedstoneLogicMetadata(BlockRedstoneLogic par1BlockRedstoneLogic, int par2, int par3, int par4, int par5)
	{
		renderStandardBlock(par1BlockRedstoneLogic, par2, par3, par4);
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1BlockRedstoneLogic.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		int var7 = blockAccess.getBlockMetadata(par2, par3, par4);
		Icon var8 = getBlockIconFromSideAndMetadata(par1BlockRedstoneLogic, 1, var7);
		double var9 = var8.getMinU();
		double var11 = var8.getMaxU();
		double var13 = var8.getMinV();
		double var15 = var8.getMaxV();
		double var17 = 0.125D;
		double var19 = par2 + 1;
		double var21 = par2 + 1;
		double var23 = par2 + 0;
		double var25 = par2 + 0;
		double var27 = par4 + 0;
		double var29 = par4 + 1;
		double var31 = par4 + 1;
		double var33 = par4 + 0;
		double var35 = par3 + var17;
		if(par5 == 2)
		{
			var19 = var21 = par2 + 0;
			var23 = var25 = par2 + 1;
			var27 = var33 = par4 + 1;
			var29 = var31 = par4 + 0;
		} else if(par5 == 3)
		{
			var19 = var25 = par2 + 0;
			var21 = var23 = par2 + 1;
			var27 = var29 = par4 + 0;
			var31 = var33 = par4 + 1;
		} else if(par5 == 1)
		{
			var19 = var25 = par2 + 1;
			var21 = var23 = par2 + 0;
			var27 = var29 = par4 + 1;
			var31 = var33 = par4 + 0;
		}
		var6.addVertexWithUV(var25, var35, var33, var9, var13);
		var6.addVertexWithUV(var23, var35, var31, var9, var15);
		var6.addVertexWithUV(var21, var35, var29, var11, var15);
		var6.addVertexWithUV(var19, var35, var27, var11, var13);
	}
	
	public boolean renderBlockRedstoneWire(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		Icon var7 = BlockRedstoneWire.func_94409_b("redstoneDust_cross");
		Icon var8 = BlockRedstoneWire.func_94409_b("redstoneDust_line");
		Icon var9 = BlockRedstoneWire.func_94409_b("redstoneDust_cross_overlay");
		Icon var10 = BlockRedstoneWire.func_94409_b("redstoneDust_line_overlay");
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var11 = 1.0F;
		float var12 = var6 / 15.0F;
		float var13 = var12 * 0.6F + 0.4F;
		if(var6 == 0)
		{
			var13 = 0.3F;
		}
		float var14 = var12 * var12 * 0.7F - 0.5F;
		float var15 = var12 * var12 * 0.6F - 0.7F;
		if(var14 < 0.0F)
		{
			var14 = 0.0F;
		}
		if(var15 < 0.0F)
		{
			var15 = 0.0F;
		}
		var5.setColorOpaque_F(var13, var14, var15);
		boolean var20 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2 - 1, par3, par4, 1) || !blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2 - 1, par3 - 1, par4, -1);
		boolean var21 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2 + 1, par3, par4, 3) || !blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2 + 1, par3 - 1, par4, -1);
		boolean var22 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2, par3, par4 - 1, 2) || !blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2, par3 - 1, par4 - 1, -1);
		boolean var23 = BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2, par3, par4 + 1, 0) || !blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2, par3 - 1, par4 + 1, -1);
		if(!blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
		{
			if(blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2 - 1, par3 + 1, par4, -1))
			{
				var20 = true;
			}
			if(blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2 + 1, par3 + 1, par4, -1))
			{
				var21 = true;
			}
			if(blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2, par3 + 1, par4 - 1, -1))
			{
				var22 = true;
			}
			if(blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && BlockRedstoneWire.isPowerProviderOrWire(blockAccess, par2, par3 + 1, par4 + 1, -1))
			{
				var23 = true;
			}
		}
		float var24 = par2 + 0;
		float var25 = par2 + 1;
		float var26 = par4 + 0;
		float var27 = par4 + 1;
		int var28 = 0;
		if((var20 || var21) && !var22 && !var23)
		{
			var28 = 1;
		}
		if((var22 || var23) && !var21 && !var20)
		{
			var28 = 2;
		}
		if(var28 == 0)
		{
			int var29 = 0;
			int var30 = 0;
			int var31 = 16;
			int var32 = 16;
			if(!var20)
			{
				var24 += 0.3125F;
			}
			if(!var20)
			{
				var29 += 5;
			}
			if(!var21)
			{
				var25 -= 0.3125F;
			}
			if(!var21)
			{
				var31 -= 5;
			}
			if(!var22)
			{
				var26 += 0.3125F;
			}
			if(!var22)
			{
				var30 += 5;
			}
			if(!var23)
			{
				var27 -= 0.3125F;
			}
			if(!var23)
			{
				var32 -= 5;
			}
			var5.addVertexWithUV(var25, par3 + 0.015625D, var27, var7.getInterpolatedU(var31), var7.getInterpolatedV(var32));
			var5.addVertexWithUV(var25, par3 + 0.015625D, var26, var7.getInterpolatedU(var31), var7.getInterpolatedV(var30));
			var5.addVertexWithUV(var24, par3 + 0.015625D, var26, var7.getInterpolatedU(var29), var7.getInterpolatedV(var30));
			var5.addVertexWithUV(var24, par3 + 0.015625D, var27, var7.getInterpolatedU(var29), var7.getInterpolatedV(var32));
			var5.setColorOpaque_F(var11, var11, var11);
			var5.addVertexWithUV(var25, par3 + 0.015625D, var27, var9.getInterpolatedU(var31), var9.getInterpolatedV(var32));
			var5.addVertexWithUV(var25, par3 + 0.015625D, var26, var9.getInterpolatedU(var31), var9.getInterpolatedV(var30));
			var5.addVertexWithUV(var24, par3 + 0.015625D, var26, var9.getInterpolatedU(var29), var9.getInterpolatedV(var30));
			var5.addVertexWithUV(var24, par3 + 0.015625D, var27, var9.getInterpolatedU(var29), var9.getInterpolatedV(var32));
		} else if(var28 == 1)
		{
			var5.addVertexWithUV(var25, par3 + 0.015625D, var27, var8.getMaxU(), var8.getMaxV());
			var5.addVertexWithUV(var25, par3 + 0.015625D, var26, var8.getMaxU(), var8.getMinV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var26, var8.getMinU(), var8.getMinV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var27, var8.getMinU(), var8.getMaxV());
			var5.setColorOpaque_F(var11, var11, var11);
			var5.addVertexWithUV(var25, par3 + 0.015625D, var27, var10.getMaxU(), var10.getMaxV());
			var5.addVertexWithUV(var25, par3 + 0.015625D, var26, var10.getMaxU(), var10.getMinV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var26, var10.getMinU(), var10.getMinV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var27, var10.getMinU(), var10.getMaxV());
		} else
		{
			var5.addVertexWithUV(var25, par3 + 0.015625D, var27, var8.getMaxU(), var8.getMaxV());
			var5.addVertexWithUV(var25, par3 + 0.015625D, var26, var8.getMinU(), var8.getMaxV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var26, var8.getMinU(), var8.getMinV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var27, var8.getMaxU(), var8.getMinV());
			var5.setColorOpaque_F(var11, var11, var11);
			var5.addVertexWithUV(var25, par3 + 0.015625D, var27, var10.getMaxU(), var10.getMaxV());
			var5.addVertexWithUV(var25, par3 + 0.015625D, var26, var10.getMinU(), var10.getMaxV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var26, var10.getMinU(), var10.getMinV());
			var5.addVertexWithUV(var24, par3 + 0.015625D, var27, var10.getMaxU(), var10.getMinV());
		}
		if(!blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
		{
			if(blockAccess.isBlockNormalCube(par2 - 1, par3, par4) && blockAccess.getBlockId(par2 - 1, par3 + 1, par4) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 1, var8.getMinU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 0, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var8.getMaxU(), var8.getMaxV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 1, var10.getMinU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 0, par4 + 0, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var10.getMaxU(), var10.getMaxV());
			}
			if(blockAccess.isBlockNormalCube(par2 + 1, par3, par4) && blockAccess.getBlockId(par2 + 1, par3 + 1, par4) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 1, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var8.getMaxU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 0, var8.getMinU(), var8.getMinV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 1, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 1, var10.getMaxU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 1 + 0.021875F, par4 + 0, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 1 - 0.015625D, par3 + 0, par4 + 0, var10.getMinU(), var10.getMinV());
			}
			if(blockAccess.isBlockNormalCube(par2, par3, par4 - 1) && blockAccess.getBlockId(par2, par3 + 1, par4 - 1) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 0.015625D, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 0.015625D, var8.getMaxU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 0.015625D, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 0.015625D, var8.getMinU(), var8.getMinV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 0.015625D, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 0.015625D, var10.getMaxU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 0.015625D, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 0.015625D, var10.getMinU(), var10.getMinV());
			}
			if(blockAccess.isBlockNormalCube(par2, par3, par4 + 1) && blockAccess.getBlockId(par2, par3 + 1, par4 + 1) == Block.redstoneWire.blockID)
			{
				var5.setColorOpaque_F(var11 * var13, var11 * var14, var11 * var15);
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var8.getMaxU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - 0.015625D, var8.getMinU(), var8.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - 0.015625D, var8.getMinU(), var8.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var8.getMaxU(), var8.getMaxV());
				var5.setColorOpaque_F(var11, var11, var11);
				var5.addVertexWithUV(par2 + 1, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var10.getMaxU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - 0.015625D, var10.getMinU(), var10.getMinV());
				var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - 0.015625D, var10.getMinU(), var10.getMaxV());
				var5.addVertexWithUV(par2 + 0, par3 + 1 + 0.021875F, par4 + 1 - 0.015625D, var10.getMaxU(), var10.getMaxV());
			}
		}
		return true;
	}
	
	private boolean renderBlockRepeater(BlockRedstoneRepeater par1BlockRedstoneRepeater, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var6 = var5 & 3;
		int var7 = (var5 & 12) >> 2;
		Tessellator var8 = Tessellator.instance;
		var8.setBrightness(par1BlockRedstoneRepeater.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double var9 = -0.1875D;
		boolean var11 = par1BlockRedstoneRepeater.func_94476_e(blockAccess, par2, par3, par4, var5);
		double var12 = 0.0D;
		double var14 = 0.0D;
		double var16 = 0.0D;
		double var18 = 0.0D;
		switch(var6)
		{
			case 0:
				var18 = -0.3125D;
				var14 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
				break;
			case 1:
				var16 = 0.3125D;
				var12 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
				break;
			case 2:
				var18 = 0.3125D;
				var14 = -BlockRedstoneRepeater.repeaterTorchOffset[var7];
				break;
			case 3:
				var16 = -0.3125D;
				var12 = BlockRedstoneRepeater.repeaterTorchOffset[var7];
		}
		if(!var11)
		{
			renderTorchAtAngle(par1BlockRedstoneRepeater, par2 + var12, par3 + var9, par4 + var14, 0.0D, 0.0D, 0);
		} else
		{
			Icon var20 = this.getBlockIcon(Block.bedrock);
			setOverrideBlockTexture(var20);
			float var21 = 2.0F;
			float var22 = 14.0F;
			float var23 = 7.0F;
			float var24 = 9.0F;
			switch(var6)
			{
				case 1:
				case 3:
					var21 = 7.0F;
					var22 = 9.0F;
					var23 = 2.0F;
					var24 = 14.0F;
				case 0:
				case 2:
				default:
					setRenderBounds(var21 / 16.0F + (float) var12, 0.125D, var23 / 16.0F + (float) var14, var22 / 16.0F + (float) var12, 0.25D, var24 / 16.0F + (float) var14);
					double var25 = var20.getInterpolatedU(var21);
					double var27 = var20.getInterpolatedV(var23);
					double var29 = var20.getInterpolatedU(var22);
					double var31 = var20.getInterpolatedV(var24);
					var8.addVertexWithUV(par2 + var21 / 16.0F + var12, par3 + 0.25F, par4 + var23 / 16.0F + var14, var25, var27);
					var8.addVertexWithUV(par2 + var21 / 16.0F + var12, par3 + 0.25F, par4 + var24 / 16.0F + var14, var25, var31);
					var8.addVertexWithUV(par2 + var22 / 16.0F + var12, par3 + 0.25F, par4 + var24 / 16.0F + var14, var29, var31);
					var8.addVertexWithUV(par2 + var22 / 16.0F + var12, par3 + 0.25F, par4 + var23 / 16.0F + var14, var29, var27);
					renderStandardBlock(par1BlockRedstoneRepeater, par2, par3, par4);
					setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
					clearOverrideBlockTexture();
			}
		}
		var8.setBrightness(par1BlockRedstoneRepeater.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var8.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		renderTorchAtAngle(par1BlockRedstoneRepeater, par2 + var16, par3 + var9, par4 + var18, 0.0D, 0.0D, 0);
		renderBlockRedstoneLogic(par1BlockRedstoneRepeater, par2, par3, par4);
		return true;
	}
	
	public void renderBlockSandFalling(Block par1Block, World par2World, int par3, int par4, int par5, int par6)
	{
		float var7 = 0.5F;
		float var8 = 1.0F;
		float var9 = 0.8F;
		float var10 = 0.6F;
		Tessellator var11 = Tessellator.instance;
		var11.startDrawingQuads();
		var11.setBrightness(par1Block.getMixedBrightnessForBlock(par2World, par3, par4, par5));
		float var12 = 1.0F;
		float var13 = 1.0F;
		if(var13 < var12)
		{
			var13 = var12;
		}
		var11.setColorOpaque_F(var7 * var13, var7 * var13, var7 * var13);
		renderFaceYNeg(par1Block, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(par1Block, 0, par6));
		var13 = 1.0F;
		if(var13 < var12)
		{
			var13 = var12;
		}
		var11.setColorOpaque_F(var8 * var13, var8 * var13, var8 * var13);
		renderFaceYPos(par1Block, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(par1Block, 1, par6));
		var13 = 1.0F;
		if(var13 < var12)
		{
			var13 = var12;
		}
		var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
		renderFaceZNeg(par1Block, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(par1Block, 2, par6));
		var13 = 1.0F;
		if(var13 < var12)
		{
			var13 = var12;
		}
		var11.setColorOpaque_F(var9 * var13, var9 * var13, var9 * var13);
		renderFaceZPos(par1Block, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(par1Block, 3, par6));
		var13 = 1.0F;
		if(var13 < var12)
		{
			var13 = var12;
		}
		var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
		renderFaceXNeg(par1Block, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(par1Block, 4, par6));
		var13 = 1.0F;
		if(var13 < var12)
		{
			var13 = var12;
		}
		var11.setColorOpaque_F(var10 * var13, var10 * var13, var10 * var13);
		renderFaceXPos(par1Block, -0.5D, -0.5D, -0.5D, getBlockIconFromSideAndMetadata(par1Block, 5, par6));
		var11.draw();
	}
	
	public boolean renderBlockStairs(BlockStairs par1BlockStairs, int par2, int par3, int par4)
	{
		par1BlockStairs.func_82541_d(blockAccess, par2, par3, par4);
		setRenderBoundsFromBlock(par1BlockStairs);
		renderStandardBlock(par1BlockStairs, par2, par3, par4);
		boolean var5 = par1BlockStairs.func_82542_g(blockAccess, par2, par3, par4);
		setRenderBoundsFromBlock(par1BlockStairs);
		renderStandardBlock(par1BlockStairs, par2, par3, par4);
		if(var5 && par1BlockStairs.func_82544_h(blockAccess, par2, par3, par4))
		{
			setRenderBoundsFromBlock(par1BlockStairs);
			renderStandardBlock(par1BlockStairs, par2, par3, par4);
		}
		return true;
	}
	
	public boolean renderBlockStem(Block par1Block, int par2, int par3, int par4)
	{
		BlockStem var5 = (BlockStem) par1Block;
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(var5.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var7 = 1.0F;
		int var8 = var5.colorMultiplier(blockAccess, par2, par3, par4);
		float var9 = (var8 >> 16 & 255) / 255.0F;
		float var10 = (var8 >> 8 & 255) / 255.0F;
		float var11 = (var8 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
			float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
			float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
			var9 = var12;
			var10 = var13;
			var11 = var14;
		}
		var6.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
		var5.setBlockBoundsBasedOnState(blockAccess, par2, par3, par4);
		int var15 = var5.getState(blockAccess, par2, par3, par4);
		if(var15 < 0)
		{
			renderBlockStemSmall(var5, blockAccess.getBlockMetadata(par2, par3, par4), renderMaxY, par2, par3 - 0.0625F, par4);
		} else
		{
			renderBlockStemSmall(var5, blockAccess.getBlockMetadata(par2, par3, par4), 0.5D, par2, par3 - 0.0625F, par4);
			renderBlockStemBig(var5, blockAccess.getBlockMetadata(par2, par3, par4), var15, renderMaxY, par2, par3 - 0.0625F, par4);
		}
		return true;
	}
	
	public void renderBlockStemBig(BlockStem par1BlockStem, int par2, int par3, double par4, double par6, double par8, double par10)
	{
		Tessellator var12 = Tessellator.instance;
		Icon var13 = par1BlockStem.func_94368_p();
		if(hasOverrideBlockTexture())
		{
			var13 = overrideBlockTexture;
		}
		double var14 = var13.getMinU();
		double var16 = var13.getMinV();
		double var18 = var13.getMaxU();
		double var20 = var13.getMaxV();
		double var22 = par6 + 0.5D - 0.5D;
		double var24 = par6 + 0.5D + 0.5D;
		double var26 = par10 + 0.5D - 0.5D;
		double var28 = par10 + 0.5D + 0.5D;
		double var30 = par6 + 0.5D;
		double var32 = par10 + 0.5D;
		if((par3 + 1) / 2 % 2 == 1)
		{
			double var34 = var18;
			var18 = var14;
			var14 = var34;
		}
		if(par3 < 2)
		{
			var12.addVertexWithUV(var22, par8 + par4, var32, var14, var16);
			var12.addVertexWithUV(var22, par8 + 0.0D, var32, var14, var20);
			var12.addVertexWithUV(var24, par8 + 0.0D, var32, var18, var20);
			var12.addVertexWithUV(var24, par8 + par4, var32, var18, var16);
			var12.addVertexWithUV(var24, par8 + par4, var32, var18, var16);
			var12.addVertexWithUV(var24, par8 + 0.0D, var32, var18, var20);
			var12.addVertexWithUV(var22, par8 + 0.0D, var32, var14, var20);
			var12.addVertexWithUV(var22, par8 + par4, var32, var14, var16);
		} else
		{
			var12.addVertexWithUV(var30, par8 + par4, var28, var14, var16);
			var12.addVertexWithUV(var30, par8 + 0.0D, var28, var14, var20);
			var12.addVertexWithUV(var30, par8 + 0.0D, var26, var18, var20);
			var12.addVertexWithUV(var30, par8 + par4, var26, var18, var16);
			var12.addVertexWithUV(var30, par8 + par4, var26, var18, var16);
			var12.addVertexWithUV(var30, par8 + 0.0D, var26, var18, var20);
			var12.addVertexWithUV(var30, par8 + 0.0D, var28, var14, var20);
			var12.addVertexWithUV(var30, par8 + par4, var28, var14, var16);
		}
	}
	
	public void renderBlockStemSmall(Block par1Block, int par2, double par3, double par5, double par7, double par9)
	{
		Tessellator var11 = Tessellator.instance;
		Icon var12 = getBlockIconFromSideAndMetadata(par1Block, 0, par2);
		if(hasOverrideBlockTexture())
		{
			var12 = overrideBlockTexture;
		}
		double var13 = var12.getMinU();
		double var15 = var12.getMinV();
		double var17 = var12.getMaxU();
		double var19 = var12.getInterpolatedV(par3 * 16.0D);
		double var21 = par5 + 0.5D - 0.44999998807907104D;
		double var23 = par5 + 0.5D + 0.44999998807907104D;
		double var25 = par9 + 0.5D - 0.44999998807907104D;
		double var27 = par9 + 0.5D + 0.44999998807907104D;
		var11.addVertexWithUV(var21, par7 + par3, var25, var13, var15);
		var11.addVertexWithUV(var21, par7 + 0.0D, var25, var13, var19);
		var11.addVertexWithUV(var23, par7 + 0.0D, var27, var17, var19);
		var11.addVertexWithUV(var23, par7 + par3, var27, var17, var15);
		var11.addVertexWithUV(var23, par7 + par3, var27, var13, var15);
		var11.addVertexWithUV(var23, par7 + 0.0D, var27, var13, var19);
		var11.addVertexWithUV(var21, par7 + 0.0D, var25, var17, var19);
		var11.addVertexWithUV(var21, par7 + par3, var25, var17, var15);
		var11.addVertexWithUV(var21, par7 + par3, var27, var13, var15);
		var11.addVertexWithUV(var21, par7 + 0.0D, var27, var13, var19);
		var11.addVertexWithUV(var23, par7 + 0.0D, var25, var17, var19);
		var11.addVertexWithUV(var23, par7 + par3, var25, var17, var15);
		var11.addVertexWithUV(var23, par7 + par3, var25, var13, var15);
		var11.addVertexWithUV(var23, par7 + 0.0D, var25, var13, var19);
		var11.addVertexWithUV(var21, par7 + 0.0D, var27, var17, var19);
		var11.addVertexWithUV(var21, par7 + par3, var27, var17, var15);
	}
	
	public boolean renderBlockTorch(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = blockAccess.getBlockMetadata(par2, par3, par4);
		Tessellator var6 = Tessellator.instance;
		var6.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		var6.setColorOpaque_F(1.0F, 1.0F, 1.0F);
		double var7 = 0.4000000059604645D;
		double var9 = 0.5D - var7;
		double var11 = 0.20000000298023224D;
		if(var5 == 1)
		{
			renderTorchAtAngle(par1Block, par2 - var9, par3 + var11, par4, -var7, 0.0D, 0);
		} else if(var5 == 2)
		{
			renderTorchAtAngle(par1Block, par2 + var9, par3 + var11, par4, var7, 0.0D, 0);
		} else if(var5 == 3)
		{
			renderTorchAtAngle(par1Block, par2, par3 + var11, par4 - var9, 0.0D, -var7, 0);
		} else if(var5 == 4)
		{
			renderTorchAtAngle(par1Block, par2, par3 + var11, par4 + var9, 0.0D, var7, 0);
		} else
		{
			renderTorchAtAngle(par1Block, par2, par3, par4, 0.0D, 0.0D, 0);
		}
		return true;
	}
	
	public boolean renderBlockTripWire(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		Icon var6 = getBlockIconFromSide(par1Block, 0);
		int var7 = blockAccess.getBlockMetadata(par2, par3, par4);
		boolean var8 = (var7 & 4) == 4;
		boolean var9 = (var7 & 2) == 2;
		if(hasOverrideBlockTexture())
		{
			var6 = overrideBlockTexture;
		}
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var10 = par1Block.getBlockBrightness(blockAccess, par2, par3, par4) * 0.75F;
		var5.setColorOpaque_F(var10, var10, var10);
		double var11 = var6.getMinU();
		double var13 = var6.getInterpolatedV(var8 ? 2.0D : 0.0D);
		double var15 = var6.getMaxU();
		double var17 = var6.getInterpolatedV(var8 ? 4.0D : 2.0D);
		double var19 = (var9 ? 3.5F : 1.5F) / 16.0D;
		boolean var21 = BlockTripWire.func_72148_a(blockAccess, par2, par3, par4, var7, 1);
		boolean var22 = BlockTripWire.func_72148_a(blockAccess, par2, par3, par4, var7, 3);
		boolean var23 = BlockTripWire.func_72148_a(blockAccess, par2, par3, par4, var7, 2);
		boolean var24 = BlockTripWire.func_72148_a(blockAccess, par2, par3, par4, var7, 0);
		float var25 = 0.03125F;
		float var26 = 0.5F - var25 / 2.0F;
		float var27 = var26 + var25;
		if(!var23 && !var22 && !var24 && !var21)
		{
			var23 = true;
			var24 = true;
		}
		if(var23)
		{
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.25D, var11, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.25D, var11, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4, var15, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4, var15, var13);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4, var15, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4, var15, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.25D, var11, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.25D, var11, var13);
		}
		if(var23 || var24 && !var22 && !var21)
		{
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.5D, var11, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.5D, var11, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.25D, var15, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.25D, var15, var13);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.25D, var15, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.25D, var15, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.5D, var11, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.5D, var11, var13);
		}
		if(var24 || var23 && !var22 && !var21)
		{
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.75D, var11, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.75D, var11, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.5D, var15, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.5D, var15, var13);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.5D, var15, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.5D, var15, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.75D, var11, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.75D, var11, var13);
		}
		if(var24)
		{
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 1, var11, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 1, var11, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.75D, var15, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.75D, var15, var13);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 0.75D, var15, var13);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 0.75D, var15, var17);
			var5.addVertexWithUV(par2 + var27, par3 + var19, par4 + 1, var11, var17);
			var5.addVertexWithUV(par2 + var26, par3 + var19, par4 + 1, var11, var13);
		}
		if(var21)
		{
			var5.addVertexWithUV(par2, par3 + var19, par4 + var27, var11, var17);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2, par3 + var19, par4 + var27, var11, var17);
		}
		if(var21 || var22 && !var23 && !var24)
		{
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var27, var11, var17);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 0.25D, par3 + var19, par4 + var27, var11, var17);
		}
		if(var22 || var21 && !var23 && !var24)
		{
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var27, var11, var17);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 0.5D, par3 + var19, par4 + var27, var11, var17);
		}
		if(var22)
		{
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var27, var11, var17);
			var5.addVertexWithUV(par2 + 1, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 1, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var26, var11, var13);
			var5.addVertexWithUV(par2 + 1, par3 + var19, par4 + var26, var15, var13);
			var5.addVertexWithUV(par2 + 1, par3 + var19, par4 + var27, var15, var17);
			var5.addVertexWithUV(par2 + 0.75D, par3 + var19, par4 + var27, var11, var17);
		}
		return true;
	}
	
	public boolean renderBlockTripWireSource(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var7 = var6 & 3;
		boolean var8 = (var6 & 4) == 4;
		boolean var9 = (var6 & 8) == 8;
		boolean var10 = !blockAccess.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);
		boolean var11 = hasOverrideBlockTexture();
		if(!var11)
		{
			setOverrideBlockTexture(this.getBlockIcon(Block.planks));
		}
		float var12 = 0.25F;
		float var13 = 0.125F;
		float var14 = 0.125F;
		float var15 = 0.3F - var12;
		float var16 = 0.3F + var12;
		if(var7 == 2)
		{
			setRenderBounds(0.5F - var13, var15, 1.0F - var14, 0.5F + var13, var16, 1.0D);
		} else if(var7 == 0)
		{
			setRenderBounds(0.5F - var13, var15, 0.0D, 0.5F + var13, var16, var14);
		} else if(var7 == 1)
		{
			setRenderBounds(1.0F - var14, var15, 0.5F - var13, 1.0D, var16, 0.5F + var13);
		} else if(var7 == 3)
		{
			setRenderBounds(0.0D, var15, 0.5F - var13, var14, var16, 0.5F + var13);
		}
		renderStandardBlock(par1Block, par2, par3, par4);
		if(!var11)
		{
			clearOverrideBlockTexture();
		}
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var17 = 1.0F;
		if(Block.lightValue[par1Block.blockID] > 0)
		{
			var17 = 1.0F;
		}
		var5.setColorOpaque_F(var17, var17, var17);
		Icon var18 = getBlockIconFromSide(par1Block, 0);
		if(hasOverrideBlockTexture())
		{
			var18 = overrideBlockTexture;
		}
		double var19 = var18.getMinU();
		double var21 = var18.getMinV();
		double var23 = var18.getMaxU();
		double var25 = var18.getMaxV();
		Vec3[] var27 = new Vec3[8];
		float var28 = 0.046875F;
		float var29 = 0.046875F;
		float var30 = 0.3125F;
		var27[0] = blockAccess.getWorldVec3Pool().getVecFromPool(-var28, 0.0D, -var29);
		var27[1] = blockAccess.getWorldVec3Pool().getVecFromPool(var28, 0.0D, -var29);
		var27[2] = blockAccess.getWorldVec3Pool().getVecFromPool(var28, 0.0D, var29);
		var27[3] = blockAccess.getWorldVec3Pool().getVecFromPool(-var28, 0.0D, var29);
		var27[4] = blockAccess.getWorldVec3Pool().getVecFromPool(-var28, var30, -var29);
		var27[5] = blockAccess.getWorldVec3Pool().getVecFromPool(var28, var30, -var29);
		var27[6] = blockAccess.getWorldVec3Pool().getVecFromPool(var28, var30, var29);
		var27[7] = blockAccess.getWorldVec3Pool().getVecFromPool(-var28, var30, var29);
		for(int var31 = 0; var31 < 8; ++var31)
		{
			var27[var31].zCoord += 0.0625D;
			if(var9)
			{
				var27[var31].rotateAroundX(0.5235988F);
				var27[var31].yCoord -= 0.4375D;
			} else if(var8)
			{
				var27[var31].rotateAroundX(0.08726647F);
				var27[var31].yCoord -= 0.4375D;
			} else
			{
				var27[var31].rotateAroundX(-((float) Math.PI * 2F / 9F));
				var27[var31].yCoord -= 0.375D;
			}
			var27[var31].rotateAroundX((float) Math.PI / 2F);
			if(var7 == 2)
			{
				var27[var31].rotateAroundY(0.0F);
			}
			if(var7 == 0)
			{
				var27[var31].rotateAroundY((float) Math.PI);
			}
			if(var7 == 1)
			{
				var27[var31].rotateAroundY((float) Math.PI / 2F);
			}
			if(var7 == 3)
			{
				var27[var31].rotateAroundY(-((float) Math.PI / 2F));
			}
			var27[var31].xCoord += par2 + 0.5D;
			var27[var31].yCoord += par3 + 0.3125F;
			var27[var31].zCoord += par4 + 0.5D;
		}
		Vec3 var62 = null;
		Vec3 var32 = null;
		Vec3 var33 = null;
		Vec3 var34 = null;
		byte var35 = 7;
		byte var36 = 9;
		byte var37 = 9;
		byte var38 = 16;
		for(int var39 = 0; var39 < 6; ++var39)
		{
			if(var39 == 0)
			{
				var62 = var27[0];
				var32 = var27[1];
				var33 = var27[2];
				var34 = var27[3];
				var19 = var18.getInterpolatedU(var35);
				var21 = var18.getInterpolatedV(var37);
				var23 = var18.getInterpolatedU(var36);
				var25 = var18.getInterpolatedV(var37 + 2);
			} else if(var39 == 1)
			{
				var62 = var27[7];
				var32 = var27[6];
				var33 = var27[5];
				var34 = var27[4];
			} else if(var39 == 2)
			{
				var62 = var27[1];
				var32 = var27[0];
				var33 = var27[4];
				var34 = var27[5];
				var19 = var18.getInterpolatedU(var35);
				var21 = var18.getInterpolatedV(var37);
				var23 = var18.getInterpolatedU(var36);
				var25 = var18.getInterpolatedV(var38);
			} else if(var39 == 3)
			{
				var62 = var27[2];
				var32 = var27[1];
				var33 = var27[5];
				var34 = var27[6];
			} else if(var39 == 4)
			{
				var62 = var27[3];
				var32 = var27[2];
				var33 = var27[6];
				var34 = var27[7];
			} else if(var39 == 5)
			{
				var62 = var27[0];
				var32 = var27[3];
				var33 = var27[7];
				var34 = var27[4];
			}
			var5.addVertexWithUV(var62.xCoord, var62.yCoord, var62.zCoord, var19, var25);
			var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var23, var25);
			var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var23, var21);
			var5.addVertexWithUV(var34.xCoord, var34.yCoord, var34.zCoord, var19, var21);
		}
		float var63 = 0.09375F;
		float var40 = 0.09375F;
		float var41 = 0.03125F;
		var27[0] = blockAccess.getWorldVec3Pool().getVecFromPool(-var63, 0.0D, -var40);
		var27[1] = blockAccess.getWorldVec3Pool().getVecFromPool(var63, 0.0D, -var40);
		var27[2] = blockAccess.getWorldVec3Pool().getVecFromPool(var63, 0.0D, var40);
		var27[3] = blockAccess.getWorldVec3Pool().getVecFromPool(-var63, 0.0D, var40);
		var27[4] = blockAccess.getWorldVec3Pool().getVecFromPool(-var63, var41, -var40);
		var27[5] = blockAccess.getWorldVec3Pool().getVecFromPool(var63, var41, -var40);
		var27[6] = blockAccess.getWorldVec3Pool().getVecFromPool(var63, var41, var40);
		var27[7] = blockAccess.getWorldVec3Pool().getVecFromPool(-var63, var41, var40);
		for(int var42 = 0; var42 < 8; ++var42)
		{
			var27[var42].zCoord += 0.21875D;
			if(var9)
			{
				var27[var42].yCoord -= 0.09375D;
				var27[var42].zCoord -= 0.1625D;
				var27[var42].rotateAroundX(0.0F);
			} else if(var8)
			{
				var27[var42].yCoord += 0.015625D;
				var27[var42].zCoord -= 0.171875D;
				var27[var42].rotateAroundX(0.17453294F);
			} else
			{
				var27[var42].rotateAroundX(0.87266463F);
			}
			if(var7 == 2)
			{
				var27[var42].rotateAroundY(0.0F);
			}
			if(var7 == 0)
			{
				var27[var42].rotateAroundY((float) Math.PI);
			}
			if(var7 == 1)
			{
				var27[var42].rotateAroundY((float) Math.PI / 2F);
			}
			if(var7 == 3)
			{
				var27[var42].rotateAroundY(-((float) Math.PI / 2F));
			}
			var27[var42].xCoord += par2 + 0.5D;
			var27[var42].yCoord += par3 + 0.3125F;
			var27[var42].zCoord += par4 + 0.5D;
		}
		byte var65 = 5;
		byte var43 = 11;
		byte var44 = 3;
		byte var45 = 9;
		for(int var46 = 0; var46 < 6; ++var46)
		{
			if(var46 == 0)
			{
				var62 = var27[0];
				var32 = var27[1];
				var33 = var27[2];
				var34 = var27[3];
				var19 = var18.getInterpolatedU(var65);
				var21 = var18.getInterpolatedV(var44);
				var23 = var18.getInterpolatedU(var43);
				var25 = var18.getInterpolatedV(var45);
			} else if(var46 == 1)
			{
				var62 = var27[7];
				var32 = var27[6];
				var33 = var27[5];
				var34 = var27[4];
			} else if(var46 == 2)
			{
				var62 = var27[1];
				var32 = var27[0];
				var33 = var27[4];
				var34 = var27[5];
				var19 = var18.getInterpolatedU(var65);
				var21 = var18.getInterpolatedV(var44);
				var23 = var18.getInterpolatedU(var43);
				var25 = var18.getInterpolatedV(var44 + 2);
			} else if(var46 == 3)
			{
				var62 = var27[2];
				var32 = var27[1];
				var33 = var27[5];
				var34 = var27[6];
			} else if(var46 == 4)
			{
				var62 = var27[3];
				var32 = var27[2];
				var33 = var27[6];
				var34 = var27[7];
			} else if(var46 == 5)
			{
				var62 = var27[0];
				var32 = var27[3];
				var33 = var27[7];
				var34 = var27[4];
			}
			var5.addVertexWithUV(var62.xCoord, var62.yCoord, var62.zCoord, var19, var25);
			var5.addVertexWithUV(var32.xCoord, var32.yCoord, var32.zCoord, var23, var25);
			var5.addVertexWithUV(var33.xCoord, var33.yCoord, var33.zCoord, var23, var21);
			var5.addVertexWithUV(var34.xCoord, var34.yCoord, var34.zCoord, var19, var21);
		}
		if(var8)
		{
			double var64 = var27[0].yCoord;
			float var48 = 0.03125F;
			float var49 = 0.5F - var48 / 2.0F;
			float var50 = var49 + var48;
			Icon var51 = this.getBlockIcon(Block.tripWire);
			double var52 = var18.getMinU();
			double var54 = var18.getInterpolatedV(var8 ? 2.0D : 0.0D);
			double var56 = var18.getMaxU();
			double var58 = var18.getInterpolatedV(var8 ? 4.0D : 2.0D);
			double var60 = (var10 ? 3.5F : 1.5F) / 16.0D;
			var17 = par1Block.getBlockBrightness(blockAccess, par2, par3, par4) * 0.75F;
			var5.setColorOpaque_F(var17, var17, var17);
			if(var7 == 2)
			{
				var5.addVertexWithUV(par2 + var49, par3 + var60, par4 + 0.25D, var52, var54);
				var5.addVertexWithUV(par2 + var50, par3 + var60, par4 + 0.25D, var52, var58);
				var5.addVertexWithUV(par2 + var50, par3 + var60, par4, var56, var58);
				var5.addVertexWithUV(par2 + var49, par3 + var60, par4, var56, var54);
				var5.addVertexWithUV(par2 + var49, var64, par4 + 0.5D, var52, var54);
				var5.addVertexWithUV(par2 + var50, var64, par4 + 0.5D, var52, var58);
				var5.addVertexWithUV(par2 + var50, par3 + var60, par4 + 0.25D, var56, var58);
				var5.addVertexWithUV(par2 + var49, par3 + var60, par4 + 0.25D, var56, var54);
			} else if(var7 == 0)
			{
				var5.addVertexWithUV(par2 + var49, par3 + var60, par4 + 0.75D, var52, var54);
				var5.addVertexWithUV(par2 + var50, par3 + var60, par4 + 0.75D, var52, var58);
				var5.addVertexWithUV(par2 + var50, var64, par4 + 0.5D, var56, var58);
				var5.addVertexWithUV(par2 + var49, var64, par4 + 0.5D, var56, var54);
				var5.addVertexWithUV(par2 + var49, par3 + var60, par4 + 1, var52, var54);
				var5.addVertexWithUV(par2 + var50, par3 + var60, par4 + 1, var52, var58);
				var5.addVertexWithUV(par2 + var50, par3 + var60, par4 + 0.75D, var56, var58);
				var5.addVertexWithUV(par2 + var49, par3 + var60, par4 + 0.75D, var56, var54);
			} else if(var7 == 1)
			{
				var5.addVertexWithUV(par2, par3 + var60, par4 + var50, var52, var58);
				var5.addVertexWithUV(par2 + 0.25D, par3 + var60, par4 + var50, var56, var58);
				var5.addVertexWithUV(par2 + 0.25D, par3 + var60, par4 + var49, var56, var54);
				var5.addVertexWithUV(par2, par3 + var60, par4 + var49, var52, var54);
				var5.addVertexWithUV(par2 + 0.25D, par3 + var60, par4 + var50, var52, var58);
				var5.addVertexWithUV(par2 + 0.5D, var64, par4 + var50, var56, var58);
				var5.addVertexWithUV(par2 + 0.5D, var64, par4 + var49, var56, var54);
				var5.addVertexWithUV(par2 + 0.25D, par3 + var60, par4 + var49, var52, var54);
			} else
			{
				var5.addVertexWithUV(par2 + 0.5D, var64, par4 + var50, var52, var58);
				var5.addVertexWithUV(par2 + 0.75D, par3 + var60, par4 + var50, var56, var58);
				var5.addVertexWithUV(par2 + 0.75D, par3 + var60, par4 + var49, var56, var54);
				var5.addVertexWithUV(par2 + 0.5D, var64, par4 + var49, var52, var54);
				var5.addVertexWithUV(par2 + 0.75D, par3 + var60, par4 + var50, var52, var58);
				var5.addVertexWithUV(par2 + 1, par3 + var60, par4 + var50, var56, var58);
				var5.addVertexWithUV(par2 + 1, par3 + var60, par4 + var49, var56, var54);
				var5.addVertexWithUV(par2 + 0.75D, par3 + var60, par4 + var49, var52, var54);
			}
		}
		return true;
	}
	
	public void renderBlockUsingTexture(Block par1Block, int par2, int par3, int par4, Icon par5Icon)
	{
		setOverrideBlockTexture(par5Icon);
		renderBlockByRenderType(par1Block, par2, par3, par4);
		clearOverrideBlockTexture();
	}
	
	public boolean renderBlockVine(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		Icon var6 = getBlockIconFromSide(par1Block, 0);
		if(hasOverrideBlockTexture())
		{
			var6 = overrideBlockTexture;
		}
		float var7 = 1.0F;
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		int var8 = par1Block.colorMultiplier(blockAccess, par2, par3, par4);
		float var9 = (var8 >> 16 & 255) / 255.0F;
		float var10 = (var8 >> 8 & 255) / 255.0F;
		float var11 = (var8 & 255) / 255.0F;
		var5.setColorOpaque_F(var7 * var9, var7 * var10, var7 * var11);
		double var19 = var6.getMinU();
		double var20 = var6.getMinV();
		double var12 = var6.getMaxU();
		double var14 = var6.getMaxV();
		double var16 = 0.05000000074505806D;
		int var18 = blockAccess.getBlockMetadata(par2, par3, par4);
		if((var18 & 2) != 0)
		{
			var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 1, var19, var20);
			var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 1, var19, var14);
			var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 0, var12, var14);
			var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 0, var12, var20);
			var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 0, var12, var20);
			var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 0, var12, var14);
			var5.addVertexWithUV(par2 + var16, par3 + 0, par4 + 1, var19, var14);
			var5.addVertexWithUV(par2 + var16, par3 + 1, par4 + 1, var19, var20);
		}
		if((var18 & 8) != 0)
		{
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 1, var12, var14);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 1, var12, var20);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 0, var19, var20);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 0, var19, var14);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 0, var19, var14);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 0, var19, var20);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 1, par4 + 1, var12, var20);
			var5.addVertexWithUV(par2 + 1 - var16, par3 + 0, par4 + 1, var12, var14);
		}
		if((var18 & 4) != 0)
		{
			var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + var16, var12, var14);
			var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + var16, var12, var20);
			var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + var16, var19, var20);
			var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + var16, var19, var14);
			var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + var16, var19, var14);
			var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + var16, var19, var20);
			var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + var16, var12, var20);
			var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + var16, var12, var14);
		}
		if((var18 & 1) != 0)
		{
			var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + 1 - var16, var19, var20);
			var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - var16, var19, var14);
			var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - var16, var12, var14);
			var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + 1 - var16, var12, var20);
			var5.addVertexWithUV(par2 + 0, par3 + 1, par4 + 1 - var16, var12, var20);
			var5.addVertexWithUV(par2 + 0, par3 + 0, par4 + 1 - var16, var12, var14);
			var5.addVertexWithUV(par2 + 1, par3 + 0, par4 + 1 - var16, var19, var14);
			var5.addVertexWithUV(par2 + 1, par3 + 1, par4 + 1 - var16, var19, var20);
		}
		if(blockAccess.isBlockNormalCube(par2, par3 + 1, par4))
		{
			var5.addVertexWithUV(par2 + 1, par3 + 1 - var16, par4 + 0, var19, var20);
			var5.addVertexWithUV(par2 + 1, par3 + 1 - var16, par4 + 1, var19, var14);
			var5.addVertexWithUV(par2 + 0, par3 + 1 - var16, par4 + 1, var12, var14);
			var5.addVertexWithUV(par2 + 0, par3 + 1 - var16, par4 + 0, var12, var20);
		}
		return true;
	}
	
	public boolean renderBlockWall(BlockWall par1BlockWall, int par2, int par3, int par4)
	{
		boolean var5 = par1BlockWall.canConnectWallTo(blockAccess, par2 - 1, par3, par4);
		boolean var6 = par1BlockWall.canConnectWallTo(blockAccess, par2 + 1, par3, par4);
		boolean var7 = par1BlockWall.canConnectWallTo(blockAccess, par2, par3, par4 - 1);
		boolean var8 = par1BlockWall.canConnectWallTo(blockAccess, par2, par3, par4 + 1);
		boolean var9 = var7 && var8 && !var5 && !var6;
		boolean var10 = !var7 && !var8 && var5 && var6;
		boolean var11 = blockAccess.isAirBlock(par2, par3 + 1, par4);
		if((var9 || var10) && var11)
		{
			if(var9)
			{
				setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 1.0D);
				renderStandardBlock(par1BlockWall, par2, par3, par4);
			} else
			{
				setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
				renderStandardBlock(par1BlockWall, par2, par3, par4);
			}
		} else
		{
			setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
			renderStandardBlock(par1BlockWall, par2, par3, par4);
			if(var5)
			{
				setRenderBounds(0.0D, 0.0D, 0.3125D, 0.25D, 0.8125D, 0.6875D);
				renderStandardBlock(par1BlockWall, par2, par3, par4);
			}
			if(var6)
			{
				setRenderBounds(0.75D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
				renderStandardBlock(par1BlockWall, par2, par3, par4);
			}
			if(var7)
			{
				setRenderBounds(0.3125D, 0.0D, 0.0D, 0.6875D, 0.8125D, 0.25D);
				renderStandardBlock(par1BlockWall, par2, par3, par4);
			}
			if(var8)
			{
				setRenderBounds(0.3125D, 0.0D, 0.75D, 0.6875D, 0.8125D, 1.0D);
				renderStandardBlock(par1BlockWall, par2, par3, par4);
			}
		}
		par1BlockWall.setBlockBoundsBasedOnState(blockAccess, par2, par3, par4);
		return true;
	}
	
	public boolean renderBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		enableAO = true;
		boolean var8 = false;
		float var9 = 0.0F;
		float var10 = 0.0F;
		float var11 = 0.0F;
		float var12 = 0.0F;
		boolean var13 = true;
		int var14 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4);
		Tessellator var15 = Tessellator.instance;
		var15.setBrightness(983055);
		if(this.getBlockIcon(par1Block).getIconName().equals("grass_top"))
		{
			var13 = false;
		} else if(hasOverrideBlockTexture())
		{
			var13 = false;
		}
		boolean var17;
		boolean var16;
		boolean var19;
		boolean var18;
		float var21;
		int var20;
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 - 1, par4, 0))
		{
			if(renderMinY <= 0.0D)
			{
				--par3;
			}
			aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			if(!var19 && !var17)
			{
				aoLightValueScratchXYZNNN = aoLightValueScratchXYNN;
				aoBrightnessXYZNNN = aoBrightnessXYNN;
			} else
			{
				aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 - 1);
				aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 - 1);
			}
			if(!var18 && !var17)
			{
				aoLightValueScratchXYZNNP = aoLightValueScratchXYNN;
				aoBrightnessXYZNNP = aoBrightnessXYNN;
			} else
			{
				aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 + 1);
				aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 + 1);
			}
			if(!var19 && !var16)
			{
				aoLightValueScratchXYZPNN = aoLightValueScratchXYPN;
				aoBrightnessXYZPNN = aoBrightnessXYPN;
			} else
			{
				aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 - 1);
				aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 - 1);
			}
			if(!var18 && !var16)
			{
				aoLightValueScratchXYZPNP = aoLightValueScratchXYPN;
				aoBrightnessXYZPNP = aoBrightnessXYPN;
			} else
			{
				aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 + 1);
				aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 + 1);
			}
			if(renderMinY <= 0.0D)
			{
				++par3;
			}
			var20 = var14;
			if(renderMinY <= 0.0D || !blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			var9 = (aoLightValueScratchXYZNNP + aoLightValueScratchXYNN + aoLightValueScratchYZNP + var21) / 4.0F;
			var12 = (aoLightValueScratchYZNP + var21 + aoLightValueScratchXYZPNP + aoLightValueScratchXYPN) / 4.0F;
			var11 = (var21 + aoLightValueScratchYZNN + aoLightValueScratchXYPN + aoLightValueScratchXYZPNN) / 4.0F;
			var10 = (aoLightValueScratchXYNN + aoLightValueScratchXYZNNN + var21 + aoLightValueScratchYZNN) / 4.0F;
			brightnessTopLeft = getAoBrightness(aoBrightnessXYZNNP, aoBrightnessXYNN, aoBrightnessYZNP, var20);
			brightnessTopRight = getAoBrightness(aoBrightnessYZNP, aoBrightnessXYZPNP, aoBrightnessXYPN, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessYZNN, aoBrightnessXYPN, aoBrightnessXYZPNN, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessXYNN, aoBrightnessXYZNNN, aoBrightnessYZNN, var20);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.5F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.5F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.5F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.5F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.5F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.5F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 0));
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 + 1, par4, 1))
		{
			if(renderMaxY >= 1.0D)
			{
				++par3;
			}
			aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			if(!var19 && !var17)
			{
				aoLightValueScratchXYZNPN = aoLightValueScratchXYNP;
				aoBrightnessXYZNPN = aoBrightnessXYNP;
			} else
			{
				aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 - 1);
				aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 - 1);
			}
			if(!var19 && !var16)
			{
				aoLightValueScratchXYZPPN = aoLightValueScratchXYPP;
				aoBrightnessXYZPPN = aoBrightnessXYPP;
			} else
			{
				aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 - 1);
				aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 - 1);
			}
			if(!var18 && !var17)
			{
				aoLightValueScratchXYZNPP = aoLightValueScratchXYNP;
				aoBrightnessXYZNPP = aoBrightnessXYNP;
			} else
			{
				aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 + 1);
				aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 + 1);
			}
			if(!var18 && !var16)
			{
				aoLightValueScratchXYZPPP = aoLightValueScratchXYPP;
				aoBrightnessXYZPPP = aoBrightnessXYPP;
			} else
			{
				aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 + 1);
				aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 + 1);
			}
			if(renderMaxY >= 1.0D)
			{
				--par3;
			}
			var20 = var14;
			if(renderMaxY >= 1.0D || !blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			var12 = (aoLightValueScratchXYZNPP + aoLightValueScratchXYNP + aoLightValueScratchYZPP + var21) / 4.0F;
			var9 = (aoLightValueScratchYZPP + var21 + aoLightValueScratchXYZPPP + aoLightValueScratchXYPP) / 4.0F;
			var10 = (var21 + aoLightValueScratchYZPN + aoLightValueScratchXYPP + aoLightValueScratchXYZPPN) / 4.0F;
			var11 = (aoLightValueScratchXYNP + aoLightValueScratchXYZNPN + var21 + aoLightValueScratchYZPN) / 4.0F;
			brightnessTopRight = getAoBrightness(aoBrightnessXYZNPP, aoBrightnessXYNP, aoBrightnessYZPP, var20);
			brightnessTopLeft = getAoBrightness(aoBrightnessYZPP, aoBrightnessXYZPPP, aoBrightnessXYPP, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessYZPN, aoBrightnessXYPP, aoBrightnessXYZPPN, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessXYNP, aoBrightnessXYZNPN, aoBrightnessYZPN, var20);
			colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5;
			colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6;
			colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7;
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 1));
			var8 = true;
		}
		float var23;
		float var22;
		float var25;
		float var24;
		int var27;
		int var26;
		int var29;
		int var28;
		Icon var30;
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 - 1, 2))
		{
			if(renderMinZ <= 0.0D)
			{
				--par4;
			}
			aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			if(!var17 && !var19)
			{
				aoLightValueScratchXYZNNN = aoLightValueScratchXZNN;
				aoBrightnessXYZNNN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 - 1, par4);
				aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 - 1, par4);
			}
			if(!var17 && !var18)
			{
				aoLightValueScratchXYZNPN = aoLightValueScratchXZNN;
				aoBrightnessXYZNPN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 + 1, par4);
				aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 + 1, par4);
			}
			if(!var16 && !var19)
			{
				aoLightValueScratchXYZPNN = aoLightValueScratchXZPN;
				aoBrightnessXYZPNN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 - 1, par4);
				aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 - 1, par4);
			}
			if(!var16 && !var18)
			{
				aoLightValueScratchXYZPPN = aoLightValueScratchXZPN;
				aoBrightnessXYZPPN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 + 1, par4);
				aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 + 1, par4);
			}
			if(renderMinZ <= 0.0D)
			{
				++par4;
			}
			var20 = var14;
			if(renderMinZ <= 0.0D || !blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			var22 = (aoLightValueScratchXZNN + aoLightValueScratchXYZNPN + var21 + aoLightValueScratchYZPN) / 4.0F;
			var23 = (var21 + aoLightValueScratchYZPN + aoLightValueScratchXZPN + aoLightValueScratchXYZPPN) / 4.0F;
			var24 = (aoLightValueScratchYZNN + var21 + aoLightValueScratchXYZPNN + aoLightValueScratchXZPN) / 4.0F;
			var25 = (aoLightValueScratchXYZNNN + aoLightValueScratchXZNN + aoLightValueScratchYZNN + var21) / 4.0F;
			var9 = (float) (var22 * renderMaxY * (1.0D - renderMinX) + var23 * renderMinY * renderMinX + var24 * (1.0D - renderMaxY) * renderMinX + var25 * (1.0D - renderMaxY) * (1.0D - renderMinX));
			var10 = (float) (var22 * renderMaxY * (1.0D - renderMaxX) + var23 * renderMaxY * renderMaxX + var24 * (1.0D - renderMaxY) * renderMaxX + var25 * (1.0D - renderMaxY) * (1.0D - renderMaxX));
			var11 = (float) (var22 * renderMinY * (1.0D - renderMaxX) + var23 * renderMinY * renderMaxX + var24 * (1.0D - renderMinY) * renderMaxX + var25 * (1.0D - renderMinY) * (1.0D - renderMaxX));
			var12 = (float) (var22 * renderMinY * (1.0D - renderMinX) + var23 * renderMinY * renderMinX + var24 * (1.0D - renderMinY) * renderMinX + var25 * (1.0D - renderMinY) * (1.0D - renderMinX));
			var26 = getAoBrightness(aoBrightnessXZNN, aoBrightnessXYZNPN, aoBrightnessYZPN, var20);
			var27 = getAoBrightness(aoBrightnessYZPN, aoBrightnessXZPN, aoBrightnessXYZPPN, var20);
			var28 = getAoBrightness(aoBrightnessYZNN, aoBrightnessXYZPNN, aoBrightnessXZPN, var20);
			var29 = getAoBrightness(aoBrightnessXYZNNN, aoBrightnessXZNN, aoBrightnessYZNN, var20);
			brightnessTopLeft = mixAoBrightness(var26, var27, var28, var29, renderMaxY * (1.0D - renderMinX), renderMaxY * renderMinX, (1.0D - renderMaxY) * renderMinX, (1.0D - renderMaxY) * (1.0D - renderMinX));
			brightnessBottomLeft = mixAoBrightness(var26, var27, var28, var29, renderMaxY * (1.0D - renderMaxX), renderMaxY * renderMaxX, (1.0D - renderMaxY) * renderMaxX, (1.0D - renderMaxY) * (1.0D - renderMaxX));
			brightnessBottomRight = mixAoBrightness(var26, var27, var28, var29, renderMinY * (1.0D - renderMaxX), renderMinY * renderMaxX, (1.0D - renderMinY) * renderMaxX, (1.0D - renderMinY) * (1.0D - renderMaxX));
			brightnessTopRight = mixAoBrightness(var26, var27, var28, var29, renderMinY * (1.0D - renderMinX), renderMinY * renderMinX, (1.0D - renderMinY) * renderMinX, (1.0D - renderMinY) * (1.0D - renderMinX));
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.8F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.8F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 2);
			renderFaceZNeg(par1Block, par2, par3, par4, var30);
			if(fancyGrass && var30.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceZNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 + 1, 3))
		{
			if(renderMaxZ >= 1.0D)
			{
				++par4;
			}
			aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			if(!var17 && !var19)
			{
				aoLightValueScratchXYZNNP = aoLightValueScratchXZNP;
				aoBrightnessXYZNNP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 - 1, par4);
				aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 - 1, par4);
			}
			if(!var17 && !var18)
			{
				aoLightValueScratchXYZNPP = aoLightValueScratchXZNP;
				aoBrightnessXYZNPP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 + 1, par4);
				aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 + 1, par4);
			}
			if(!var16 && !var19)
			{
				aoLightValueScratchXYZPNP = aoLightValueScratchXZPP;
				aoBrightnessXYZPNP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 - 1, par4);
				aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 - 1, par4);
			}
			if(!var16 && !var18)
			{
				aoLightValueScratchXYZPPP = aoLightValueScratchXZPP;
				aoBrightnessXYZPPP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 + 1, par4);
				aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 + 1, par4);
			}
			if(renderMaxZ >= 1.0D)
			{
				--par4;
			}
			var20 = var14;
			if(renderMaxZ >= 1.0D || !blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			var22 = (aoLightValueScratchXZNP + aoLightValueScratchXYZNPP + var21 + aoLightValueScratchYZPP) / 4.0F;
			var23 = (var21 + aoLightValueScratchYZPP + aoLightValueScratchXZPP + aoLightValueScratchXYZPPP) / 4.0F;
			var24 = (aoLightValueScratchYZNP + var21 + aoLightValueScratchXYZPNP + aoLightValueScratchXZPP) / 4.0F;
			var25 = (aoLightValueScratchXYZNNP + aoLightValueScratchXZNP + aoLightValueScratchYZNP + var21) / 4.0F;
			var9 = (float) (var22 * renderMaxY * (1.0D - renderMinX) + var23 * renderMaxY * renderMinX + var24 * (1.0D - renderMaxY) * renderMinX + var25 * (1.0D - renderMaxY) * (1.0D - renderMinX));
			var10 = (float) (var22 * renderMinY * (1.0D - renderMinX) + var23 * renderMinY * renderMinX + var24 * (1.0D - renderMinY) * renderMinX + var25 * (1.0D - renderMinY) * (1.0D - renderMinX));
			var11 = (float) (var22 * renderMinY * (1.0D - renderMaxX) + var23 * renderMinY * renderMaxX + var24 * (1.0D - renderMinY) * renderMaxX + var25 * (1.0D - renderMinY) * (1.0D - renderMaxX));
			var12 = (float) (var22 * renderMaxY * (1.0D - renderMaxX) + var23 * renderMaxY * renderMaxX + var24 * (1.0D - renderMaxY) * renderMaxX + var25 * (1.0D - renderMaxY) * (1.0D - renderMaxX));
			var26 = getAoBrightness(aoBrightnessXZNP, aoBrightnessXYZNPP, aoBrightnessYZPP, var20);
			var27 = getAoBrightness(aoBrightnessYZPP, aoBrightnessXZPP, aoBrightnessXYZPPP, var20);
			var28 = getAoBrightness(aoBrightnessYZNP, aoBrightnessXYZPNP, aoBrightnessXZPP, var20);
			var29 = getAoBrightness(aoBrightnessXYZNNP, aoBrightnessXZNP, aoBrightnessYZNP, var20);
			brightnessTopLeft = mixAoBrightness(var26, var29, var28, var27, renderMaxY * (1.0D - renderMinX), (1.0D - renderMaxY) * (1.0D - renderMinX), (1.0D - renderMaxY) * renderMinX, renderMaxY * renderMinX);
			brightnessBottomLeft = mixAoBrightness(var26, var29, var28, var27, renderMinY * (1.0D - renderMinX), (1.0D - renderMinY) * (1.0D - renderMinX), (1.0D - renderMinY) * renderMinX, renderMinY * renderMinX);
			brightnessBottomRight = mixAoBrightness(var26, var29, var28, var27, renderMinY * (1.0D - renderMaxX), (1.0D - renderMinY) * (1.0D - renderMaxX), (1.0D - renderMinY) * renderMaxX, renderMinY * renderMaxX);
			brightnessTopRight = mixAoBrightness(var26, var29, var28, var27, renderMaxY * (1.0D - renderMaxX), (1.0D - renderMaxY) * (1.0D - renderMaxX), (1.0D - renderMaxY) * renderMaxX, renderMaxY * renderMaxX);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.8F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.8F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3);
			renderFaceZPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3));
			if(fancyGrass && var30.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceZPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 - 1, par3, par4, 4))
		{
			if(renderMinX <= 0.0D)
			{
				--par2;
			}
			aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			if(!var18 && !var17)
			{
				aoLightValueScratchXYZNNN = aoLightValueScratchXZNN;
				aoBrightnessXYZNNN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 - 1);
				aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 - 1);
			}
			if(!var19 && !var17)
			{
				aoLightValueScratchXYZNNP = aoLightValueScratchXZNP;
				aoBrightnessXYZNNP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 + 1);
				aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 + 1);
			}
			if(!var18 && !var16)
			{
				aoLightValueScratchXYZNPN = aoLightValueScratchXZNN;
				aoBrightnessXYZNPN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 - 1);
				aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 - 1);
			}
			if(!var19 && !var16)
			{
				aoLightValueScratchXYZNPP = aoLightValueScratchXZNP;
				aoBrightnessXYZNPP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 + 1);
				aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 + 1);
			}
			if(renderMinX <= 0.0D)
			{
				++par2;
			}
			var20 = var14;
			if(renderMinX <= 0.0D || !blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			var22 = (aoLightValueScratchXYNN + aoLightValueScratchXYZNNP + var21 + aoLightValueScratchXZNP) / 4.0F;
			var23 = (var21 + aoLightValueScratchXZNP + aoLightValueScratchXYNP + aoLightValueScratchXYZNPP) / 4.0F;
			var24 = (aoLightValueScratchXZNN + var21 + aoLightValueScratchXYZNPN + aoLightValueScratchXYNP) / 4.0F;
			var25 = (aoLightValueScratchXYZNNN + aoLightValueScratchXYNN + aoLightValueScratchXZNN + var21) / 4.0F;
			var9 = (float) (var23 * renderMaxY * renderMaxZ + var24 * renderMaxY * (1.0D - renderMaxZ) + var25 * (1.0D - renderMaxY) * (1.0D - renderMaxZ) + var22 * (1.0D - renderMaxY) * renderMaxZ);
			var10 = (float) (var23 * renderMaxY * renderMinZ + var24 * renderMaxY * (1.0D - renderMinZ) + var25 * (1.0D - renderMaxY) * (1.0D - renderMinZ) + var22 * (1.0D - renderMaxY) * renderMinZ);
			var11 = (float) (var23 * renderMinY * renderMinZ + var24 * renderMinY * (1.0D - renderMinZ) + var25 * (1.0D - renderMinY) * (1.0D - renderMinZ) + var22 * (1.0D - renderMinY) * renderMinZ);
			var12 = (float) (var23 * renderMinY * renderMaxZ + var24 * renderMinY * (1.0D - renderMaxZ) + var25 * (1.0D - renderMinY) * (1.0D - renderMaxZ) + var22 * (1.0D - renderMinY) * renderMaxZ);
			var26 = getAoBrightness(aoBrightnessXYNN, aoBrightnessXYZNNP, aoBrightnessXZNP, var20);
			var27 = getAoBrightness(aoBrightnessXZNP, aoBrightnessXYNP, aoBrightnessXYZNPP, var20);
			var28 = getAoBrightness(aoBrightnessXZNN, aoBrightnessXYZNPN, aoBrightnessXYNP, var20);
			var29 = getAoBrightness(aoBrightnessXYZNNN, aoBrightnessXYNN, aoBrightnessXZNN, var20);
			brightnessTopLeft = mixAoBrightness(var27, var28, var29, var26, renderMaxY * renderMaxZ, renderMaxY * (1.0D - renderMaxZ), (1.0D - renderMaxY) * (1.0D - renderMaxZ), (1.0D - renderMaxY) * renderMaxZ);
			brightnessBottomLeft = mixAoBrightness(var27, var28, var29, var26, renderMaxY * renderMinZ, renderMaxY * (1.0D - renderMinZ), (1.0D - renderMaxY) * (1.0D - renderMinZ), (1.0D - renderMaxY) * renderMinZ);
			brightnessBottomRight = mixAoBrightness(var27, var28, var29, var26, renderMinY * renderMinZ, renderMinY * (1.0D - renderMinZ), (1.0D - renderMinY) * (1.0D - renderMinZ), (1.0D - renderMinY) * renderMinZ);
			brightnessTopRight = mixAoBrightness(var27, var28, var29, var26, renderMinY * renderMaxZ, renderMinY * (1.0D - renderMaxZ), (1.0D - renderMinY) * (1.0D - renderMaxZ), (1.0D - renderMinY) * renderMaxZ);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.6F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.6F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 4);
			renderFaceXNeg(par1Block, par2, par3, par4, var30);
			if(fancyGrass && var30.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceXNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 + 1, par3, par4, 5))
		{
			if(renderMaxX >= 1.0D)
			{
				++par2;
			}
			aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			if(!var17 && !var19)
			{
				aoLightValueScratchXYZPNN = aoLightValueScratchXZPN;
				aoBrightnessXYZPNN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 - 1);
				aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 - 1);
			}
			if(!var17 && !var18)
			{
				aoLightValueScratchXYZPNP = aoLightValueScratchXZPP;
				aoBrightnessXYZPNP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 + 1);
				aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 + 1);
			}
			if(!var16 && !var19)
			{
				aoLightValueScratchXYZPPN = aoLightValueScratchXZPN;
				aoBrightnessXYZPPN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 - 1);
				aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 - 1);
			}
			if(!var16 && !var18)
			{
				aoLightValueScratchXYZPPP = aoLightValueScratchXZPP;
				aoBrightnessXYZPPP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 + 1);
				aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 + 1);
			}
			if(renderMaxX >= 1.0D)
			{
				--par2;
			}
			var20 = var14;
			if(renderMaxX >= 1.0D || !blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			var22 = (aoLightValueScratchXYPN + aoLightValueScratchXYZPNP + var21 + aoLightValueScratchXZPP) / 4.0F;
			var23 = (aoLightValueScratchXYZPNN + aoLightValueScratchXYPN + aoLightValueScratchXZPN + var21) / 4.0F;
			var24 = (aoLightValueScratchXZPN + var21 + aoLightValueScratchXYZPPN + aoLightValueScratchXYPP) / 4.0F;
			var25 = (var21 + aoLightValueScratchXZPP + aoLightValueScratchXYPP + aoLightValueScratchXYZPPP) / 4.0F;
			var9 = (float) (var22 * (1.0D - renderMinY) * renderMaxZ + var23 * (1.0D - renderMinY) * (1.0D - renderMaxZ) + var24 * renderMinY * (1.0D - renderMaxZ) + var25 * renderMinY * renderMaxZ);
			var10 = (float) (var22 * (1.0D - renderMinY) * renderMinZ + var23 * (1.0D - renderMinY) * (1.0D - renderMinZ) + var24 * renderMinY * (1.0D - renderMinZ) + var25 * renderMinY * renderMinZ);
			var11 = (float) (var22 * (1.0D - renderMaxY) * renderMinZ + var23 * (1.0D - renderMaxY) * (1.0D - renderMinZ) + var24 * renderMaxY * (1.0D - renderMinZ) + var25 * renderMaxY * renderMinZ);
			var12 = (float) (var22 * (1.0D - renderMaxY) * renderMaxZ + var23 * (1.0D - renderMaxY) * (1.0D - renderMaxZ) + var24 * renderMaxY * (1.0D - renderMaxZ) + var25 * renderMaxY * renderMaxZ);
			var26 = getAoBrightness(aoBrightnessXYPN, aoBrightnessXYZPNP, aoBrightnessXZPP, var20);
			var27 = getAoBrightness(aoBrightnessXZPP, aoBrightnessXYPP, aoBrightnessXYZPPP, var20);
			var28 = getAoBrightness(aoBrightnessXZPN, aoBrightnessXYZPPN, aoBrightnessXYPP, var20);
			var29 = getAoBrightness(aoBrightnessXYZPNN, aoBrightnessXYPN, aoBrightnessXZPN, var20);
			brightnessTopLeft = mixAoBrightness(var26, var29, var28, var27, (1.0D - renderMinY) * renderMaxZ, (1.0D - renderMinY) * (1.0D - renderMaxZ), renderMinY * (1.0D - renderMaxZ), renderMinY * renderMaxZ);
			brightnessBottomLeft = mixAoBrightness(var26, var29, var28, var27, (1.0D - renderMinY) * renderMinZ, (1.0D - renderMinY) * (1.0D - renderMinZ), renderMinY * (1.0D - renderMinZ), renderMinY * renderMinZ);
			brightnessBottomRight = mixAoBrightness(var26, var29, var28, var27, (1.0D - renderMaxY) * renderMinZ, (1.0D - renderMaxY) * (1.0D - renderMinZ), renderMaxY * (1.0D - renderMinZ), renderMaxY * renderMinZ);
			brightnessTopRight = mixAoBrightness(var26, var29, var28, var27, (1.0D - renderMaxY) * renderMaxZ, (1.0D - renderMaxY) * (1.0D - renderMaxZ), renderMaxY * (1.0D - renderMaxZ), renderMaxY * renderMaxZ);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.6F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.6F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var30 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 5);
			renderFaceXPos(par1Block, par2, par3, par4, var30);
			if(fancyGrass && var30.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceXPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		enableAO = false;
		return var8;
	}
	
	public boolean renderCrossedSquares(Block par1Block, int par2, int par3, int par4)
	{
		Tessellator var5 = Tessellator.instance;
		var5.setBrightness(par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
		float var6 = 1.0F;
		int var7 = par1Block.colorMultiplier(blockAccess, par2, par3, par4);
		float var8 = (var7 >> 16 & 255) / 255.0F;
		float var9 = (var7 >> 8 & 255) / 255.0F;
		float var10 = (var7 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
			float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
			float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
			var8 = var11;
			var9 = var12;
			var10 = var13;
		}
		var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
		double var19 = par2;
		double var20 = par3;
		double var15 = par4;
		if(par1Block == Block.tallGrass)
		{
			long var17 = par2 * 3129871 ^ par4 * 116129781L ^ par3;
			var17 = var17 * var17 * 42317861L + var17 * 11L;
			var19 += ((var17 >> 16 & 15L) / 15.0F - 0.5D) * 0.5D;
			var20 += ((var17 >> 20 & 15L) / 15.0F - 1.0D) * 0.2D;
			var15 += ((var17 >> 24 & 15L) / 15.0F - 0.5D) * 0.5D;
		}
		drawCrossedSquares(par1Block, blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);
		return true;
	}
	
	public void renderFaceXNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;
		if(hasOverrideBlockTexture())
		{
			par8Icon = overrideBlockTexture;
		}
		double var10 = par8Icon.getInterpolatedU(renderMinZ * 16.0D);
		double var12 = par8Icon.getInterpolatedU(renderMaxZ * 16.0D);
		double var14 = par8Icon.getInterpolatedV(16.0D - renderMaxY * 16.0D);
		double var16 = par8Icon.getInterpolatedV(16.0D - renderMinY * 16.0D);
		double var18;
		if(flipTexture)
		{
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if(renderMinZ < 0.0D || renderMaxZ > 1.0D)
		{
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if(renderMinY < 0.0D || renderMaxY > 1.0D)
		{
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if(uvRotateNorth == 1)
		{
			var10 = par8Icon.getInterpolatedU(renderMinY * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMaxZ * 16.0D);
			var12 = par8Icon.getInterpolatedU(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMinZ * 16.0D);
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if(uvRotateNorth == 2)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMaxY * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMinZ * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMinY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMaxZ * 16.0D);
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if(uvRotateNorth == 3)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMinZ * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMaxZ * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMinY * 16.0D);
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + renderMinX;
		double var28 = par4 + renderMinY;
		double var30 = par4 + renderMaxY;
		double var32 = par6 + renderMinZ;
		double var34 = par6 + renderMaxZ;
		if(enableAO)
		{
			var9.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			var9.setBrightness(brightnessTopLeft);
			var9.addVertexWithUV(var26, var30, var34, var18, var22);
			var9.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			var9.setBrightness(brightnessBottomLeft);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			var9.setBrightness(brightnessBottomRight);
			var9.addVertexWithUV(var26, var28, var32, var20, var24);
			var9.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			var9.setBrightness(brightnessTopRight);
			var9.addVertexWithUV(var26, var28, var34, var12, var16);
		} else
		{
			var9.addVertexWithUV(var26, var30, var34, var18, var22);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.addVertexWithUV(var26, var28, var32, var20, var24);
			var9.addVertexWithUV(var26, var28, var34, var12, var16);
		}
	}
	
	public void renderFaceXPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;
		if(hasOverrideBlockTexture())
		{
			par8Icon = overrideBlockTexture;
		}
		double var10 = par8Icon.getInterpolatedU(renderMinZ * 16.0D);
		double var12 = par8Icon.getInterpolatedU(renderMaxZ * 16.0D);
		double var14 = par8Icon.getInterpolatedV(16.0D - renderMaxY * 16.0D);
		double var16 = par8Icon.getInterpolatedV(16.0D - renderMinY * 16.0D);
		double var18;
		if(flipTexture)
		{
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if(renderMinZ < 0.0D || renderMaxZ > 1.0D)
		{
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if(renderMinY < 0.0D || renderMaxY > 1.0D)
		{
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if(uvRotateSouth == 2)
		{
			var10 = par8Icon.getInterpolatedU(renderMinY * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMinZ * 16.0D);
			var12 = par8Icon.getInterpolatedU(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMaxZ * 16.0D);
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if(uvRotateSouth == 1)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMaxY * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMaxZ * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMinY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMinZ * 16.0D);
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if(uvRotateSouth == 3)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMinZ * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMaxZ * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMinY * 16.0D);
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + renderMaxX;
		double var28 = par4 + renderMinY;
		double var30 = par4 + renderMaxY;
		double var32 = par6 + renderMinZ;
		double var34 = par6 + renderMaxZ;
		if(enableAO)
		{
			var9.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			var9.setBrightness(brightnessTopLeft);
			var9.addVertexWithUV(var26, var28, var34, var20, var24);
			var9.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			var9.setBrightness(brightnessBottomLeft);
			var9.addVertexWithUV(var26, var28, var32, var12, var16);
			var9.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			var9.setBrightness(brightnessBottomRight);
			var9.addVertexWithUV(var26, var30, var32, var18, var22);
			var9.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			var9.setBrightness(brightnessTopRight);
			var9.addVertexWithUV(var26, var30, var34, var10, var14);
		} else
		{
			var9.addVertexWithUV(var26, var28, var34, var20, var24);
			var9.addVertexWithUV(var26, var28, var32, var12, var16);
			var9.addVertexWithUV(var26, var30, var32, var18, var22);
			var9.addVertexWithUV(var26, var30, var34, var10, var14);
		}
	}
	
	public void renderFaceYNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;
		if(hasOverrideBlockTexture())
		{
			par8Icon = overrideBlockTexture;
		}
		double var10 = par8Icon.getInterpolatedU(renderMinX * 16.0D);
		double var12 = par8Icon.getInterpolatedU(renderMaxX * 16.0D);
		double var14 = par8Icon.getInterpolatedV(renderMinZ * 16.0D);
		double var16 = par8Icon.getInterpolatedV(renderMaxZ * 16.0D);
		if(renderMinX < 0.0D || renderMaxX > 1.0D)
		{
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if(renderMinZ < 0.0D || renderMaxZ > 1.0D)
		{
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		double var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if(uvRotateBottom == 2)
		{
			var10 = par8Icon.getInterpolatedU(renderMinZ * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMaxX * 16.0D);
			var12 = par8Icon.getInterpolatedU(renderMaxZ * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMinX * 16.0D);
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if(uvRotateBottom == 1)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMaxZ * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMinZ * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMaxX * 16.0D);
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if(uvRotateBottom == 3)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMaxX * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMinZ * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMaxZ * 16.0D);
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + renderMinX;
		double var28 = par2 + renderMaxX;
		double var30 = par4 + renderMinY;
		double var32 = par6 + renderMinZ;
		double var34 = par6 + renderMaxZ;
		if(enableAO)
		{
			var9.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			var9.setBrightness(brightnessTopLeft);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			var9.setBrightness(brightnessBottomLeft);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			var9.setBrightness(brightnessBottomRight);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			var9.setBrightness(brightnessTopRight);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
		} else
		{
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
		}
	}
	
	public void renderFaceYPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;
		if(hasOverrideBlockTexture())
		{
			par8Icon = overrideBlockTexture;
		}
		double var10 = par8Icon.getInterpolatedU(renderMinX * 16.0D);
		double var12 = par8Icon.getInterpolatedU(renderMaxX * 16.0D);
		double var14 = par8Icon.getInterpolatedV(renderMinZ * 16.0D);
		double var16 = par8Icon.getInterpolatedV(renderMaxZ * 16.0D);
		if(renderMinX < 0.0D || renderMaxX > 1.0D)
		{
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if(renderMinZ < 0.0D || renderMaxZ > 1.0D)
		{
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		double var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if(uvRotateTop == 1)
		{
			var10 = par8Icon.getInterpolatedU(renderMinZ * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMaxX * 16.0D);
			var12 = par8Icon.getInterpolatedU(renderMaxZ * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMinX * 16.0D);
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if(uvRotateTop == 2)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMaxZ * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMinZ * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMaxX * 16.0D);
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if(uvRotateTop == 3)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMaxX * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMinZ * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMaxZ * 16.0D);
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + renderMinX;
		double var28 = par2 + renderMaxX;
		double var30 = par4 + renderMaxY;
		double var32 = par6 + renderMinZ;
		double var34 = par6 + renderMaxZ;
		if(enableAO)
		{
			var9.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			var9.setBrightness(brightnessTopLeft);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			var9.setBrightness(brightnessBottomLeft);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			var9.setBrightness(brightnessBottomRight);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			var9.setBrightness(brightnessTopRight);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
		} else
		{
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.addVertexWithUV(var28, var30, var32, var18, var22);
			var9.addVertexWithUV(var26, var30, var32, var10, var14);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
		}
	}
	
	public void renderFaceZNeg(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;
		if(hasOverrideBlockTexture())
		{
			par8Icon = overrideBlockTexture;
		}
		double var10 = par8Icon.getInterpolatedU(renderMinX * 16.0D);
		double var12 = par8Icon.getInterpolatedU(renderMaxX * 16.0D);
		double var14 = par8Icon.getInterpolatedV(16.0D - renderMaxY * 16.0D);
		double var16 = par8Icon.getInterpolatedV(16.0D - renderMinY * 16.0D);
		double var18;
		if(flipTexture)
		{
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if(renderMinX < 0.0D || renderMaxX > 1.0D)
		{
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if(renderMinY < 0.0D || renderMaxY > 1.0D)
		{
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if(uvRotateEast == 2)
		{
			var10 = par8Icon.getInterpolatedU(renderMinY * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMaxX * 16.0D);
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if(uvRotateEast == 1)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMaxY * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMaxX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMinY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMinX * 16.0D);
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if(uvRotateEast == 3)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMaxX * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMinY * 16.0D);
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + renderMinX;
		double var28 = par2 + renderMaxX;
		double var30 = par4 + renderMinY;
		double var32 = par4 + renderMaxY;
		double var34 = par6 + renderMinZ;
		if(enableAO)
		{
			var9.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			var9.setBrightness(brightnessTopLeft);
			var9.addVertexWithUV(var26, var32, var34, var18, var22);
			var9.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			var9.setBrightness(brightnessBottomLeft);
			var9.addVertexWithUV(var28, var32, var34, var10, var14);
			var9.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			var9.setBrightness(brightnessBottomRight);
			var9.addVertexWithUV(var28, var30, var34, var20, var24);
			var9.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			var9.setBrightness(brightnessTopRight);
			var9.addVertexWithUV(var26, var30, var34, var12, var16);
		} else
		{
			var9.addVertexWithUV(var26, var32, var34, var18, var22);
			var9.addVertexWithUV(var28, var32, var34, var10, var14);
			var9.addVertexWithUV(var28, var30, var34, var20, var24);
			var9.addVertexWithUV(var26, var30, var34, var12, var16);
		}
	}
	
	public void renderFaceZPos(Block par1Block, double par2, double par4, double par6, Icon par8Icon)
	{
		Tessellator var9 = Tessellator.instance;
		if(hasOverrideBlockTexture())
		{
			par8Icon = overrideBlockTexture;
		}
		double var10 = par8Icon.getInterpolatedU(renderMinX * 16.0D);
		double var12 = par8Icon.getInterpolatedU(renderMaxX * 16.0D);
		double var14 = par8Icon.getInterpolatedV(16.0D - renderMaxY * 16.0D);
		double var16 = par8Icon.getInterpolatedV(16.0D - renderMinY * 16.0D);
		double var18;
		if(flipTexture)
		{
			var18 = var10;
			var10 = var12;
			var12 = var18;
		}
		if(renderMinX < 0.0D || renderMaxX > 1.0D)
		{
			var10 = par8Icon.getMinU();
			var12 = par8Icon.getMaxU();
		}
		if(renderMinY < 0.0D || renderMaxY > 1.0D)
		{
			var14 = par8Icon.getMinV();
			var16 = par8Icon.getMaxV();
		}
		var18 = var12;
		double var20 = var10;
		double var22 = var14;
		double var24 = var16;
		if(uvRotateWest == 1)
		{
			var10 = par8Icon.getInterpolatedU(renderMinY * 16.0D);
			var16 = par8Icon.getInterpolatedV(16.0D - renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(renderMaxY * 16.0D);
			var14 = par8Icon.getInterpolatedV(16.0D - renderMaxX * 16.0D);
			var22 = var14;
			var24 = var16;
			var18 = var10;
			var20 = var12;
			var14 = var16;
			var16 = var22;
		} else if(uvRotateWest == 2)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMaxY * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMinY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMaxX * 16.0D);
			var18 = var12;
			var20 = var10;
			var10 = var12;
			var12 = var20;
			var22 = var16;
			var24 = var14;
		} else if(uvRotateWest == 3)
		{
			var10 = par8Icon.getInterpolatedU(16.0D - renderMinX * 16.0D);
			var12 = par8Icon.getInterpolatedU(16.0D - renderMaxX * 16.0D);
			var14 = par8Icon.getInterpolatedV(renderMaxY * 16.0D);
			var16 = par8Icon.getInterpolatedV(renderMinY * 16.0D);
			var18 = var12;
			var20 = var10;
			var22 = var14;
			var24 = var16;
		}
		double var26 = par2 + renderMinX;
		double var28 = par2 + renderMaxX;
		double var30 = par4 + renderMinY;
		double var32 = par4 + renderMaxY;
		double var34 = par6 + renderMaxZ;
		if(enableAO)
		{
			var9.setColorOpaque_F(colorRedTopLeft, colorGreenTopLeft, colorBlueTopLeft);
			var9.setBrightness(brightnessTopLeft);
			var9.addVertexWithUV(var26, var32, var34, var10, var14);
			var9.setColorOpaque_F(colorRedBottomLeft, colorGreenBottomLeft, colorBlueBottomLeft);
			var9.setBrightness(brightnessBottomLeft);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.setColorOpaque_F(colorRedBottomRight, colorGreenBottomRight, colorBlueBottomRight);
			var9.setBrightness(brightnessBottomRight);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.setColorOpaque_F(colorRedTopRight, colorGreenTopRight, colorBlueTopRight);
			var9.setBrightness(brightnessTopRight);
			var9.addVertexWithUV(var28, var32, var34, var18, var22);
		} else
		{
			var9.addVertexWithUV(var26, var32, var34, var10, var14);
			var9.addVertexWithUV(var26, var30, var34, var20, var24);
			var9.addVertexWithUV(var28, var30, var34, var12, var16);
			var9.addVertexWithUV(var28, var32, var34, var18, var22);
		}
	}
	
	private boolean renderPistonBase(Block par1Block, int par2, int par3, int par4, boolean par5)
	{
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		boolean var7 = par5 || (var6 & 8) != 0;
		int var8 = BlockPistonBase.getOrientation(var6);
		if(var7)
		{
			switch(var8)
			{
				case 0:
					uvRotateEast = 3;
					uvRotateWest = 3;
					uvRotateSouth = 3;
					uvRotateNorth = 3;
					setRenderBounds(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
					break;
				case 1:
					setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
					break;
				case 2:
					uvRotateSouth = 1;
					uvRotateNorth = 2;
					setRenderBounds(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
					break;
				case 3:
					uvRotateSouth = 2;
					uvRotateNorth = 1;
					uvRotateTop = 3;
					uvRotateBottom = 3;
					setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
					break;
				case 4:
					uvRotateEast = 1;
					uvRotateWest = 2;
					uvRotateTop = 2;
					uvRotateBottom = 1;
					setRenderBounds(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
					break;
				case 5:
					uvRotateEast = 2;
					uvRotateWest = 1;
					uvRotateTop = 1;
					uvRotateBottom = 2;
					setRenderBounds(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
			}
			((BlockPistonBase) par1Block).func_96479_b((float) renderMinX, (float) renderMinY, (float) renderMinZ, (float) renderMaxX, (float) renderMaxY, (float) renderMaxZ);
			renderStandardBlock(par1Block, par2, par3, par4);
			uvRotateEast = 0;
			uvRotateWest = 0;
			uvRotateSouth = 0;
			uvRotateNorth = 0;
			uvRotateTop = 0;
			uvRotateBottom = 0;
			setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			((BlockPistonBase) par1Block).func_96479_b((float) renderMinX, (float) renderMinY, (float) renderMinZ, (float) renderMaxX, (float) renderMaxY, (float) renderMaxZ);
		} else
		{
			switch(var8)
			{
				case 0:
					uvRotateEast = 3;
					uvRotateWest = 3;
					uvRotateSouth = 3;
					uvRotateNorth = 3;
				case 1:
				default:
					break;
				case 2:
					uvRotateSouth = 1;
					uvRotateNorth = 2;
					break;
				case 3:
					uvRotateSouth = 2;
					uvRotateNorth = 1;
					uvRotateTop = 3;
					uvRotateBottom = 3;
					break;
				case 4:
					uvRotateEast = 1;
					uvRotateWest = 2;
					uvRotateTop = 2;
					uvRotateBottom = 1;
					break;
				case 5:
					uvRotateEast = 2;
					uvRotateWest = 1;
					uvRotateTop = 1;
					uvRotateBottom = 2;
			}
			renderStandardBlock(par1Block, par2, par3, par4);
			uvRotateEast = 0;
			uvRotateWest = 0;
			uvRotateSouth = 0;
			uvRotateNorth = 0;
			uvRotateTop = 0;
			uvRotateBottom = 0;
		}
		return true;
	}
	
	public void renderPistonBaseAllFaces(Block par1Block, int par2, int par3, int par4)
	{
		renderAllFaces = true;
		renderPistonBase(par1Block, par2, par3, par4, true);
		renderAllFaces = false;
	}
	
	private boolean renderPistonExtension(Block par1Block, int par2, int par3, int par4, boolean par5)
	{
		int var6 = blockAccess.getBlockMetadata(par2, par3, par4);
		int var7 = BlockPistonExtension.getDirectionMeta(var6);
		float var11 = par1Block.getBlockBrightness(blockAccess, par2, par3, par4);
		float var12 = par5 ? 1.0F : 0.5F;
		double var13 = par5 ? 16.0D : 8.0D;
		switch(var7)
		{
			case 0:
				uvRotateEast = 3;
				uvRotateWest = 3;
				uvRotateSouth = 3;
				uvRotateNorth = 3;
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
				renderStandardBlock(par1Block, par2, par3, par4);
				renderPistonRodUD(par2 + 0.375F, par2 + 0.625F, par3 + 0.25F, par3 + 0.25F + var12, par4 + 0.625F, par4 + 0.625F, var11 * 0.8F, var13);
				renderPistonRodUD(par2 + 0.625F, par2 + 0.375F, par3 + 0.25F, par3 + 0.25F + var12, par4 + 0.375F, par4 + 0.375F, var11 * 0.8F, var13);
				renderPistonRodUD(par2 + 0.375F, par2 + 0.375F, par3 + 0.25F, par3 + 0.25F + var12, par4 + 0.375F, par4 + 0.625F, var11 * 0.6F, var13);
				renderPistonRodUD(par2 + 0.625F, par2 + 0.625F, par3 + 0.25F, par3 + 0.25F + var12, par4 + 0.625F, par4 + 0.375F, var11 * 0.6F, var13);
				break;
			case 1:
				setRenderBounds(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
				renderStandardBlock(par1Block, par2, par3, par4);
				renderPistonRodUD(par2 + 0.375F, par2 + 0.625F, par3 - 0.25F + 1.0F - var12, par3 - 0.25F + 1.0F, par4 + 0.625F, par4 + 0.625F, var11 * 0.8F, var13);
				renderPistonRodUD(par2 + 0.625F, par2 + 0.375F, par3 - 0.25F + 1.0F - var12, par3 - 0.25F + 1.0F, par4 + 0.375F, par4 + 0.375F, var11 * 0.8F, var13);
				renderPistonRodUD(par2 + 0.375F, par2 + 0.375F, par3 - 0.25F + 1.0F - var12, par3 - 0.25F + 1.0F, par4 + 0.375F, par4 + 0.625F, var11 * 0.6F, var13);
				renderPistonRodUD(par2 + 0.625F, par2 + 0.625F, par3 - 0.25F + 1.0F - var12, par3 - 0.25F + 1.0F, par4 + 0.625F, par4 + 0.375F, var11 * 0.6F, var13);
				break;
			case 2:
				uvRotateSouth = 1;
				uvRotateNorth = 2;
				setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
				renderStandardBlock(par1Block, par2, par3, par4);
				renderPistonRodSN(par2 + 0.375F, par2 + 0.375F, par3 + 0.625F, par3 + 0.375F, par4 + 0.25F, par4 + 0.25F + var12, var11 * 0.6F, var13);
				renderPistonRodSN(par2 + 0.625F, par2 + 0.625F, par3 + 0.375F, par3 + 0.625F, par4 + 0.25F, par4 + 0.25F + var12, var11 * 0.6F, var13);
				renderPistonRodSN(par2 + 0.375F, par2 + 0.625F, par3 + 0.375F, par3 + 0.375F, par4 + 0.25F, par4 + 0.25F + var12, var11 * 0.5F, var13);
				renderPistonRodSN(par2 + 0.625F, par2 + 0.375F, par3 + 0.625F, par3 + 0.625F, par4 + 0.25F, par4 + 0.25F + var12, var11, var13);
				break;
			case 3:
				uvRotateSouth = 2;
				uvRotateNorth = 1;
				uvRotateTop = 3;
				uvRotateBottom = 3;
				setRenderBounds(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
				renderStandardBlock(par1Block, par2, par3, par4);
				renderPistonRodSN(par2 + 0.375F, par2 + 0.375F, par3 + 0.625F, par3 + 0.375F, par4 - 0.25F + 1.0F - var12, par4 - 0.25F + 1.0F, var11 * 0.6F, var13);
				renderPistonRodSN(par2 + 0.625F, par2 + 0.625F, par3 + 0.375F, par3 + 0.625F, par4 - 0.25F + 1.0F - var12, par4 - 0.25F + 1.0F, var11 * 0.6F, var13);
				renderPistonRodSN(par2 + 0.375F, par2 + 0.625F, par3 + 0.375F, par3 + 0.375F, par4 - 0.25F + 1.0F - var12, par4 - 0.25F + 1.0F, var11 * 0.5F, var13);
				renderPistonRodSN(par2 + 0.625F, par2 + 0.375F, par3 + 0.625F, par3 + 0.625F, par4 - 0.25F + 1.0F - var12, par4 - 0.25F + 1.0F, var11, var13);
				break;
			case 4:
				uvRotateEast = 1;
				uvRotateWest = 2;
				uvRotateTop = 2;
				uvRotateBottom = 1;
				setRenderBounds(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
				renderStandardBlock(par1Block, par2, par3, par4);
				renderPistonRodEW(par2 + 0.25F, par2 + 0.25F + var12, par3 + 0.375F, par3 + 0.375F, par4 + 0.625F, par4 + 0.375F, var11 * 0.5F, var13);
				renderPistonRodEW(par2 + 0.25F, par2 + 0.25F + var12, par3 + 0.625F, par3 + 0.625F, par4 + 0.375F, par4 + 0.625F, var11, var13);
				renderPistonRodEW(par2 + 0.25F, par2 + 0.25F + var12, par3 + 0.375F, par3 + 0.625F, par4 + 0.375F, par4 + 0.375F, var11 * 0.6F, var13);
				renderPistonRodEW(par2 + 0.25F, par2 + 0.25F + var12, par3 + 0.625F, par3 + 0.375F, par4 + 0.625F, par4 + 0.625F, var11 * 0.6F, var13);
				break;
			case 5:
				uvRotateEast = 2;
				uvRotateWest = 1;
				uvRotateTop = 1;
				uvRotateBottom = 2;
				setRenderBounds(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
				renderStandardBlock(par1Block, par2, par3, par4);
				renderPistonRodEW(par2 - 0.25F + 1.0F - var12, par2 - 0.25F + 1.0F, par3 + 0.375F, par3 + 0.375F, par4 + 0.625F, par4 + 0.375F, var11 * 0.5F, var13);
				renderPistonRodEW(par2 - 0.25F + 1.0F - var12, par2 - 0.25F + 1.0F, par3 + 0.625F, par3 + 0.625F, par4 + 0.375F, par4 + 0.625F, var11, var13);
				renderPistonRodEW(par2 - 0.25F + 1.0F - var12, par2 - 0.25F + 1.0F, par3 + 0.375F, par3 + 0.625F, par4 + 0.375F, par4 + 0.375F, var11 * 0.6F, var13);
				renderPistonRodEW(par2 - 0.25F + 1.0F - var12, par2 - 0.25F + 1.0F, par3 + 0.625F, par3 + 0.375F, par4 + 0.625F, par4 + 0.625F, var11 * 0.6F, var13);
		}
		uvRotateEast = 0;
		uvRotateWest = 0;
		uvRotateSouth = 0;
		uvRotateNorth = 0;
		uvRotateTop = 0;
		uvRotateBottom = 0;
		setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return true;
	}
	
	public void renderPistonExtensionAllFaces(Block par1Block, int par2, int par3, int par4, boolean par5)
	{
		renderAllFaces = true;
		renderPistonExtension(par1Block, par2, par3, par4, par5);
		renderAllFaces = false;
	}
	
	private void renderPistonRodEW(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14)
	{
		Icon var16 = BlockPistonBase.func_94496_b("piston_side");
		if(hasOverrideBlockTexture())
		{
			var16 = overrideBlockTexture;
		}
		Tessellator var17 = Tessellator.instance;
		double var18 = var16.getMinU();
		double var20 = var16.getMinV();
		double var22 = var16.getInterpolatedU(par14);
		double var24 = var16.getInterpolatedV(4.0D);
		var17.setColorOpaque_F(par13, par13, par13);
		var17.addVertexWithUV(par3, par5, par9, var22, var20);
		var17.addVertexWithUV(par1, par5, par9, var18, var20);
		var17.addVertexWithUV(par1, par7, par11, var18, var24);
		var17.addVertexWithUV(par3, par7, par11, var22, var24);
	}
	
	private void renderPistonRodSN(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14)
	{
		Icon var16 = BlockPistonBase.func_94496_b("piston_side");
		if(hasOverrideBlockTexture())
		{
			var16 = overrideBlockTexture;
		}
		Tessellator var17 = Tessellator.instance;
		double var18 = var16.getMinU();
		double var20 = var16.getMinV();
		double var22 = var16.getInterpolatedU(par14);
		double var24 = var16.getInterpolatedV(4.0D);
		var17.setColorOpaque_F(par13, par13, par13);
		var17.addVertexWithUV(par1, par5, par11, var22, var20);
		var17.addVertexWithUV(par1, par5, par9, var18, var20);
		var17.addVertexWithUV(par3, par7, par9, var18, var24);
		var17.addVertexWithUV(par3, par7, par11, var22, var24);
	}
	
	private void renderPistonRodUD(double par1, double par3, double par5, double par7, double par9, double par11, float par13, double par14)
	{
		Icon var16 = BlockPistonBase.func_94496_b("piston_side");
		if(hasOverrideBlockTexture())
		{
			var16 = overrideBlockTexture;
		}
		Tessellator var17 = Tessellator.instance;
		double var18 = var16.getMinU();
		double var20 = var16.getMinV();
		double var22 = var16.getInterpolatedU(par14);
		double var24 = var16.getInterpolatedV(4.0D);
		var17.setColorOpaque_F(par13, par13, par13);
		var17.addVertexWithUV(par1, par7, par9, var22, var20);
		var17.addVertexWithUV(par1, par5, par9, var18, var20);
		var17.addVertexWithUV(par3, par5, par11, var18, var24);
		var17.addVertexWithUV(par3, par7, par11, var22, var24);
	}
	
	public boolean renderStandardBlock(Block par1Block, int par2, int par3, int par4)
	{
		int var5 = par1Block.colorMultiplier(blockAccess, par2, par3, par4);
		float var6 = (var5 >> 16 & 255) / 255.0F;
		float var7 = (var5 >> 8 & 255) / 255.0F;
		float var8 = (var5 & 255) / 255.0F;
		if(EntityRenderer.anaglyphEnable)
		{
			float var9 = (var6 * 30.0F + var7 * 59.0F + var8 * 11.0F) / 100.0F;
			float var10 = (var6 * 30.0F + var7 * 70.0F) / 100.0F;
			float var11 = (var6 * 30.0F + var8 * 70.0F) / 100.0F;
			var6 = var9;
			var7 = var10;
			var8 = var11;
		}
		return Minecraft.isAmbientOcclusionEnabled() && Block.lightValue[par1Block.blockID] == 0 ? partialRenderBounds ? renderBlockWithAmbientOcclusion(par1Block, par2, par3, par4, var6, var7, var8) : renderStandardBlockWithAmbientOcclusion(par1Block, par2, par3, par4, var6, var7, var8) : renderStandardBlockWithColorMultiplier(par1Block, par2, par3, par4, var6, var7, var8);
	}
	
	public boolean renderStandardBlockWithAmbientOcclusion(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		enableAO = true;
		boolean var8 = false;
		float var9 = 0.0F;
		float var10 = 0.0F;
		float var11 = 0.0F;
		float var12 = 0.0F;
		boolean var13 = true;
		int var14 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4);
		Tessellator var15 = Tessellator.instance;
		var15.setBrightness(983055);
		if(this.getBlockIcon(par1Block).getIconName().equals("grass_top"))
		{
			var13 = false;
		} else if(hasOverrideBlockTexture())
		{
			var13 = false;
		}
		boolean var17;
		boolean var16;
		boolean var19;
		boolean var18;
		float var21;
		int var20;
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 - 1, par4, 0))
		{
			if(renderMinY <= 0.0D)
			{
				--par3;
			}
			aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			if(!var19 && !var17)
			{
				aoLightValueScratchXYZNNN = aoLightValueScratchXYNN;
				aoBrightnessXYZNNN = aoBrightnessXYNN;
			} else
			{
				aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 - 1);
				aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 - 1);
			}
			if(!var18 && !var17)
			{
				aoLightValueScratchXYZNNP = aoLightValueScratchXYNN;
				aoBrightnessXYZNNP = aoBrightnessXYNN;
			} else
			{
				aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 + 1);
				aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 + 1);
			}
			if(!var19 && !var16)
			{
				aoLightValueScratchXYZPNN = aoLightValueScratchXYPN;
				aoBrightnessXYZPNN = aoBrightnessXYPN;
			} else
			{
				aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 - 1);
				aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 - 1);
			}
			if(!var18 && !var16)
			{
				aoLightValueScratchXYZPNP = aoLightValueScratchXYPN;
				aoBrightnessXYZPNP = aoBrightnessXYPN;
			} else
			{
				aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 + 1);
				aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 + 1);
			}
			if(renderMinY <= 0.0D)
			{
				++par3;
			}
			var20 = var14;
			if(renderMinY <= 0.0D || !blockAccess.isBlockOpaqueCube(par2, par3 - 1, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			var9 = (aoLightValueScratchXYZNNP + aoLightValueScratchXYNN + aoLightValueScratchYZNP + var21) / 4.0F;
			var12 = (aoLightValueScratchYZNP + var21 + aoLightValueScratchXYZPNP + aoLightValueScratchXYPN) / 4.0F;
			var11 = (var21 + aoLightValueScratchYZNN + aoLightValueScratchXYPN + aoLightValueScratchXYZPNN) / 4.0F;
			var10 = (aoLightValueScratchXYNN + aoLightValueScratchXYZNNN + var21 + aoLightValueScratchYZNN) / 4.0F;
			brightnessTopLeft = getAoBrightness(aoBrightnessXYZNNP, aoBrightnessXYNN, aoBrightnessYZNP, var20);
			brightnessTopRight = getAoBrightness(aoBrightnessYZNP, aoBrightnessXYZPNP, aoBrightnessXYPN, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessYZNN, aoBrightnessXYPN, aoBrightnessXYZPNN, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessXYNN, aoBrightnessXYZNNN, aoBrightnessYZNN, var20);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.5F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.5F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.5F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.5F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.5F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.5F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 0));
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 + 1, par4, 1))
		{
			if(renderMaxY >= 1.0D)
			{
				++par3;
			}
			aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			if(!var19 && !var17)
			{
				aoLightValueScratchXYZNPN = aoLightValueScratchXYNP;
				aoBrightnessXYZNPN = aoBrightnessXYNP;
			} else
			{
				aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 - 1);
				aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 - 1);
			}
			if(!var19 && !var16)
			{
				aoLightValueScratchXYZPPN = aoLightValueScratchXYPP;
				aoBrightnessXYZPPN = aoBrightnessXYPP;
			} else
			{
				aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 - 1);
				aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 - 1);
			}
			if(!var18 && !var17)
			{
				aoLightValueScratchXYZNPP = aoLightValueScratchXYNP;
				aoBrightnessXYZNPP = aoBrightnessXYNP;
			} else
			{
				aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4 + 1);
				aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4 + 1);
			}
			if(!var18 && !var16)
			{
				aoLightValueScratchXYZPPP = aoLightValueScratchXYPP;
				aoBrightnessXYZPPP = aoBrightnessXYPP;
			} else
			{
				aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4 + 1);
				aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4 + 1);
			}
			if(renderMaxY >= 1.0D)
			{
				--par3;
			}
			var20 = var14;
			if(renderMaxY >= 1.0D || !blockAccess.isBlockOpaqueCube(par2, par3 + 1, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			var12 = (aoLightValueScratchXYZNPP + aoLightValueScratchXYNP + aoLightValueScratchYZPP + var21) / 4.0F;
			var9 = (aoLightValueScratchYZPP + var21 + aoLightValueScratchXYZPPP + aoLightValueScratchXYPP) / 4.0F;
			var10 = (var21 + aoLightValueScratchYZPN + aoLightValueScratchXYPP + aoLightValueScratchXYZPPN) / 4.0F;
			var11 = (aoLightValueScratchXYNP + aoLightValueScratchXYZNPN + var21 + aoLightValueScratchYZPN) / 4.0F;
			brightnessTopRight = getAoBrightness(aoBrightnessXYZNPP, aoBrightnessXYNP, aoBrightnessYZPP, var20);
			brightnessTopLeft = getAoBrightness(aoBrightnessYZPP, aoBrightnessXYZPPP, aoBrightnessXYPP, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessYZPN, aoBrightnessXYPP, aoBrightnessXYZPPN, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessXYNP, aoBrightnessXYZNPN, aoBrightnessYZPN, var20);
			colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5;
			colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6;
			colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7;
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 1));
			var8 = true;
		}
		Icon var22;
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 - 1, 2))
		{
			if(renderMinZ <= 0.0D)
			{
				--par4;
			}
			aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchYZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchYZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessYZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessYZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 - 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 - 1)];
			if(!var17 && !var19)
			{
				aoLightValueScratchXYZNNN = aoLightValueScratchXZNN;
				aoBrightnessXYZNNN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 - 1, par4);
				aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 - 1, par4);
			}
			if(!var17 && !var18)
			{
				aoLightValueScratchXYZNPN = aoLightValueScratchXZNN;
				aoBrightnessXYZNPN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 + 1, par4);
				aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 + 1, par4);
			}
			if(!var16 && !var19)
			{
				aoLightValueScratchXYZPNN = aoLightValueScratchXZPN;
				aoBrightnessXYZPNN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 - 1, par4);
				aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 - 1, par4);
			}
			if(!var16 && !var18)
			{
				aoLightValueScratchXYZPPN = aoLightValueScratchXZPN;
				aoBrightnessXYZPPN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 + 1, par4);
				aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 + 1, par4);
			}
			if(renderMinZ <= 0.0D)
			{
				++par4;
			}
			var20 = var14;
			if(renderMinZ <= 0.0D || !blockAccess.isBlockOpaqueCube(par2, par3, par4 - 1))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			var9 = (aoLightValueScratchXZNN + aoLightValueScratchXYZNPN + var21 + aoLightValueScratchYZPN) / 4.0F;
			var10 = (var21 + aoLightValueScratchYZPN + aoLightValueScratchXZPN + aoLightValueScratchXYZPPN) / 4.0F;
			var11 = (aoLightValueScratchYZNN + var21 + aoLightValueScratchXYZPNN + aoLightValueScratchXZPN) / 4.0F;
			var12 = (aoLightValueScratchXYZNNN + aoLightValueScratchXZNN + aoLightValueScratchYZNN + var21) / 4.0F;
			brightnessTopLeft = getAoBrightness(aoBrightnessXZNN, aoBrightnessXYZNPN, aoBrightnessYZPN, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessYZPN, aoBrightnessXZPN, aoBrightnessXYZPPN, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessYZNN, aoBrightnessXYZPNN, aoBrightnessXZPN, var20);
			brightnessTopRight = getAoBrightness(aoBrightnessXYZNNN, aoBrightnessXZNN, aoBrightnessYZNN, var20);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.8F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.8F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var22 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 2);
			renderFaceZNeg(par1Block, par2, par3, par4, var22);
			if(fancyGrass && var22.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceZNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 + 1, 3))
		{
			if(renderMaxZ >= 1.0D)
			{
				++par4;
			}
			aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			aoLightValueScratchYZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchYZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			aoBrightnessYZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessYZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 + 1, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2, par3 - 1, par4 + 1)];
			if(!var17 && !var19)
			{
				aoLightValueScratchXYZNNP = aoLightValueScratchXZNP;
				aoBrightnessXYZNNP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 - 1, par4);
				aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 - 1, par4);
			}
			if(!var17 && !var18)
			{
				aoLightValueScratchXYZNPP = aoLightValueScratchXZNP;
				aoBrightnessXYZNPP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3 + 1, par4);
				aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3 + 1, par4);
			}
			if(!var16 && !var19)
			{
				aoLightValueScratchXYZPNP = aoLightValueScratchXZPP;
				aoBrightnessXYZPNP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 - 1, par4);
				aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 - 1, par4);
			}
			if(!var16 && !var18)
			{
				aoLightValueScratchXYZPPP = aoLightValueScratchXZPP;
				aoBrightnessXYZPPP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3 + 1, par4);
				aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3 + 1, par4);
			}
			if(renderMaxZ >= 1.0D)
			{
				--par4;
			}
			var20 = var14;
			if(renderMaxZ >= 1.0D || !blockAccess.isBlockOpaqueCube(par2, par3, par4 + 1))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			var9 = (aoLightValueScratchXZNP + aoLightValueScratchXYZNPP + var21 + aoLightValueScratchYZPP) / 4.0F;
			var12 = (var21 + aoLightValueScratchYZPP + aoLightValueScratchXZPP + aoLightValueScratchXYZPPP) / 4.0F;
			var11 = (aoLightValueScratchYZNP + var21 + aoLightValueScratchXYZPNP + aoLightValueScratchXZPP) / 4.0F;
			var10 = (aoLightValueScratchXYZNNP + aoLightValueScratchXZNP + aoLightValueScratchYZNP + var21) / 4.0F;
			brightnessTopLeft = getAoBrightness(aoBrightnessXZNP, aoBrightnessXYZNPP, aoBrightnessYZPP, var20);
			brightnessTopRight = getAoBrightness(aoBrightnessYZPP, aoBrightnessXZPP, aoBrightnessXYZPPP, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessYZNP, aoBrightnessXYZPNP, aoBrightnessXZPP, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessXYZNNP, aoBrightnessXZNP, aoBrightnessYZNP, var20);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.8F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.8F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.8F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.8F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var22 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3);
			renderFaceZPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3));
			if(fancyGrass && var22.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceZPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 - 1, par3, par4, 4))
		{
			if(renderMinX <= 0.0D)
			{
				--par2;
			}
			aoLightValueScratchXYNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchXZNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchXZNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXYNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessXZNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessXZNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoBrightnessXYNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 + 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3 - 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 - 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2 - 1, par3, par4 + 1)];
			if(!var18 && !var17)
			{
				aoLightValueScratchXYZNNN = aoLightValueScratchXZNN;
				aoBrightnessXYZNNN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 - 1);
				aoBrightnessXYZNNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 - 1);
			}
			if(!var19 && !var17)
			{
				aoLightValueScratchXYZNNP = aoLightValueScratchXZNP;
				aoBrightnessXYZNNP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 + 1);
				aoBrightnessXYZNNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 + 1);
			}
			if(!var18 && !var16)
			{
				aoLightValueScratchXYZNPN = aoLightValueScratchXZNN;
				aoBrightnessXYZNPN = aoBrightnessXZNN;
			} else
			{
				aoLightValueScratchXYZNPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 - 1);
				aoBrightnessXYZNPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 - 1);
			}
			if(!var19 && !var16)
			{
				aoLightValueScratchXYZNPP = aoLightValueScratchXZNP;
				aoBrightnessXYZNPP = aoBrightnessXZNP;
			} else
			{
				aoLightValueScratchXYZNPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 + 1);
				aoBrightnessXYZNPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 + 1);
			}
			if(renderMinX <= 0.0D)
			{
				++par2;
			}
			var20 = var14;
			if(renderMinX <= 0.0D || !blockAccess.isBlockOpaqueCube(par2 - 1, par3, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 - 1, par3, par4);
			var12 = (aoLightValueScratchXYNN + aoLightValueScratchXYZNNP + var21 + aoLightValueScratchXZNP) / 4.0F;
			var9 = (var21 + aoLightValueScratchXZNP + aoLightValueScratchXYNP + aoLightValueScratchXYZNPP) / 4.0F;
			var10 = (aoLightValueScratchXZNN + var21 + aoLightValueScratchXYZNPN + aoLightValueScratchXYNP) / 4.0F;
			var11 = (aoLightValueScratchXYZNNN + aoLightValueScratchXYNN + aoLightValueScratchXZNN + var21) / 4.0F;
			brightnessTopRight = getAoBrightness(aoBrightnessXYNN, aoBrightnessXYZNNP, aoBrightnessXZNP, var20);
			brightnessTopLeft = getAoBrightness(aoBrightnessXZNP, aoBrightnessXYNP, aoBrightnessXYZNPP, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessXZNN, aoBrightnessXYZNPN, aoBrightnessXYNP, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessXYZNNN, aoBrightnessXYNN, aoBrightnessXZNN, var20);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.6F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.6F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var22 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 4);
			renderFaceXNeg(par1Block, par2, par3, par4, var22);
			if(fancyGrass && var22.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceXNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 + 1, par3, par4, 5))
		{
			if(renderMaxX >= 1.0D)
			{
				++par2;
			}
			aoLightValueScratchXYPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4);
			aoLightValueScratchXZPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 - 1);
			aoLightValueScratchXZPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3, par4 + 1);
			aoLightValueScratchXYPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4);
			aoBrightnessXYPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4);
			aoBrightnessXZPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1);
			aoBrightnessXZPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1);
			aoBrightnessXYPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4);
			var16 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 + 1, par4)];
			var17 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3 - 1, par4)];
			var18 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 + 1)];
			var19 = Block.canBlockGrass[blockAccess.getBlockId(par2 + 1, par3, par4 - 1)];
			if(!var17 && !var19)
			{
				aoLightValueScratchXYZPNN = aoLightValueScratchXZPN;
				aoBrightnessXYZPNN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPNN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 - 1);
				aoBrightnessXYZPNN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 - 1);
			}
			if(!var17 && !var18)
			{
				aoLightValueScratchXYZPNP = aoLightValueScratchXZPP;
				aoBrightnessXYZPNP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPNP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 - 1, par4 + 1);
				aoBrightnessXYZPNP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4 + 1);
			}
			if(!var16 && !var19)
			{
				aoLightValueScratchXYZPPN = aoLightValueScratchXZPN;
				aoBrightnessXYZPPN = aoBrightnessXZPN;
			} else
			{
				aoLightValueScratchXYZPPN = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 - 1);
				aoBrightnessXYZPPN = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 - 1);
			}
			if(!var16 && !var18)
			{
				aoLightValueScratchXYZPPP = aoLightValueScratchXZPP;
				aoBrightnessXYZPPP = aoBrightnessXZPP;
			} else
			{
				aoLightValueScratchXYZPPP = par1Block.getAmbientOcclusionLightValue(blockAccess, par2, par3 + 1, par4 + 1);
				aoBrightnessXYZPPP = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4 + 1);
			}
			if(renderMaxX >= 1.0D)
			{
				--par2;
			}
			var20 = var14;
			if(renderMaxX >= 1.0D || !blockAccess.isBlockOpaqueCube(par2 + 1, par3, par4))
			{
				var20 = par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4);
			}
			var21 = par1Block.getAmbientOcclusionLightValue(blockAccess, par2 + 1, par3, par4);
			var9 = (aoLightValueScratchXYPN + aoLightValueScratchXYZPNP + var21 + aoLightValueScratchXZPP) / 4.0F;
			var10 = (aoLightValueScratchXYZPNN + aoLightValueScratchXYPN + aoLightValueScratchXZPN + var21) / 4.0F;
			var11 = (aoLightValueScratchXZPN + var21 + aoLightValueScratchXYZPPN + aoLightValueScratchXYPP) / 4.0F;
			var12 = (var21 + aoLightValueScratchXZPP + aoLightValueScratchXYPP + aoLightValueScratchXYZPPP) / 4.0F;
			brightnessTopLeft = getAoBrightness(aoBrightnessXYPN, aoBrightnessXYZPNP, aoBrightnessXZPP, var20);
			brightnessTopRight = getAoBrightness(aoBrightnessXZPP, aoBrightnessXYPP, aoBrightnessXYZPPP, var20);
			brightnessBottomRight = getAoBrightness(aoBrightnessXZPN, aoBrightnessXYZPPN, aoBrightnessXYPP, var20);
			brightnessBottomLeft = getAoBrightness(aoBrightnessXYZPNN, aoBrightnessXYPN, aoBrightnessXZPN, var20);
			if(var13)
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = par5 * 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = par6 * 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = par7 * 0.6F;
			} else
			{
				colorRedTopLeft = colorRedBottomLeft = colorRedBottomRight = colorRedTopRight = 0.6F;
				colorGreenTopLeft = colorGreenBottomLeft = colorGreenBottomRight = colorGreenTopRight = 0.6F;
				colorBlueTopLeft = colorBlueBottomLeft = colorBlueBottomRight = colorBlueTopRight = 0.6F;
			}
			colorRedTopLeft *= var9;
			colorGreenTopLeft *= var9;
			colorBlueTopLeft *= var9;
			colorRedBottomLeft *= var10;
			colorGreenBottomLeft *= var10;
			colorBlueBottomLeft *= var10;
			colorRedBottomRight *= var11;
			colorGreenBottomRight *= var11;
			colorBlueBottomRight *= var11;
			colorRedTopRight *= var12;
			colorGreenTopRight *= var12;
			colorBlueTopRight *= var12;
			var22 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 5);
			renderFaceXPos(par1Block, par2, par3, par4, var22);
			if(fancyGrass && var22.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				colorRedTopLeft *= par5;
				colorRedBottomLeft *= par5;
				colorRedBottomRight *= par5;
				colorRedTopRight *= par5;
				colorGreenTopLeft *= par6;
				colorGreenBottomLeft *= par6;
				colorGreenBottomRight *= par6;
				colorGreenTopRight *= par6;
				colorBlueTopLeft *= par7;
				colorBlueBottomLeft *= par7;
				colorBlueBottomRight *= par7;
				colorBlueTopRight *= par7;
				renderFaceXPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var8 = true;
		}
		enableAO = false;
		return var8;
	}
	
	public boolean renderStandardBlockWithColorMultiplier(Block par1Block, int par2, int par3, int par4, float par5, float par6, float par7)
	{
		enableAO = false;
		Tessellator var8 = Tessellator.instance;
		boolean var9 = false;
		float var10 = 0.5F;
		float var11 = 1.0F;
		float var12 = 0.8F;
		float var13 = 0.6F;
		float var14 = var11 * par5;
		float var15 = var11 * par6;
		float var16 = var11 * par7;
		float var17 = var10;
		float var18 = var12;
		float var19 = var13;
		float var20 = var10;
		float var21 = var12;
		float var22 = var13;
		float var23 = var10;
		float var24 = var12;
		float var25 = var13;
		if(par1Block != Block.grass)
		{
			var17 = var10 * par5;
			var18 = var12 * par5;
			var19 = var13 * par5;
			var20 = var10 * par6;
			var21 = var12 * par6;
			var22 = var13 * par6;
			var23 = var10 * par7;
			var24 = var12 * par7;
			var25 = var13 * par7;
		}
		int var26 = par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4);
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 - 1, par4, 0))
		{
			var8.setBrightness(renderMinY > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 - 1, par4));
			var8.setColorOpaque_F(var17, var20, var23);
			renderFaceYNeg(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 0));
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3 + 1, par4, 1))
		{
			var8.setBrightness(renderMaxY < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3 + 1, par4));
			var8.setColorOpaque_F(var14, var15, var16);
			renderFaceYPos(par1Block, par2, par3, par4, this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 1));
			var9 = true;
		}
		Icon var28;
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 - 1, 2))
		{
			var8.setBrightness(renderMinZ > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 - 1));
			var8.setColorOpaque_F(var18, var21, var24);
			var28 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 2);
			renderFaceZNeg(par1Block, par2, par3, par4, var28);
			if(fancyGrass && var28.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var18 * par5, var21 * par6, var24 * par7);
				renderFaceZNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2, par3, par4 + 1, 3))
		{
			var8.setBrightness(renderMaxZ < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(blockAccess, par2, par3, par4 + 1));
			var8.setColorOpaque_F(var18, var21, var24);
			var28 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 3);
			renderFaceZPos(par1Block, par2, par3, par4, var28);
			if(fancyGrass && var28.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var18 * par5, var21 * par6, var24 * par7);
				renderFaceZPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 - 1, par3, par4, 4))
		{
			var8.setBrightness(renderMinX > 0.0D ? var26 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 - 1, par3, par4));
			var8.setColorOpaque_F(var19, var22, var25);
			var28 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 4);
			renderFaceXNeg(par1Block, par2, par3, par4, var28);
			if(fancyGrass && var28.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var19 * par5, var22 * par6, var25 * par7);
				renderFaceXNeg(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var9 = true;
		}
		if(renderAllFaces || par1Block.shouldSideBeRendered(blockAccess, par2 + 1, par3, par4, 5))
		{
			var8.setBrightness(renderMaxX < 1.0D ? var26 : par1Block.getMixedBrightnessForBlock(blockAccess, par2 + 1, par3, par4));
			var8.setColorOpaque_F(var19, var22, var25);
			var28 = this.getBlockIcon(par1Block, blockAccess, par2, par3, par4, 5);
			renderFaceXPos(par1Block, par2, par3, par4, var28);
			if(fancyGrass && var28.getIconName().equals("grass_side") && !hasOverrideBlockTexture())
			{
				var8.setColorOpaque_F(var19 * par5, var22 * par6, var25 * par7);
				renderFaceXPos(par1Block, par2, par3, par4, BlockGrass.getIconSideOverlay());
			}
			var9 = true;
		}
		return var9;
	}
	
	public void renderTorchAtAngle(Block par1Block, double par2, double par4, double par6, double par8, double par10, int par12)
	{
		Tessellator var13 = Tessellator.instance;
		Icon var14 = getBlockIconFromSideAndMetadata(par1Block, 0, par12);
		if(hasOverrideBlockTexture())
		{
			var14 = overrideBlockTexture;
		}
		double var15 = var14.getMinU();
		double var17 = var14.getMinV();
		double var19 = var14.getMaxU();
		double var21 = var14.getMaxV();
		double var23 = var14.getInterpolatedU(7.0D);
		double var25 = var14.getInterpolatedV(6.0D);
		double var27 = var14.getInterpolatedU(9.0D);
		double var29 = var14.getInterpolatedV(8.0D);
		double var31 = var14.getInterpolatedU(7.0D);
		double var33 = var14.getInterpolatedV(13.0D);
		double var35 = var14.getInterpolatedU(9.0D);
		double var37 = var14.getInterpolatedV(15.0D);
		par2 += 0.5D;
		par6 += 0.5D;
		double var39 = par2 - 0.5D;
		double var41 = par2 + 0.5D;
		double var43 = par6 - 0.5D;
		double var45 = par6 + 0.5D;
		double var47 = 0.0625D;
		double var49 = 0.625D;
		var13.addVertexWithUV(par2 + par8 * (1.0D - var49) - var47, par4 + var49, par6 + par10 * (1.0D - var49) - var47, var23, var25);
		var13.addVertexWithUV(par2 + par8 * (1.0D - var49) - var47, par4 + var49, par6 + par10 * (1.0D - var49) + var47, var23, var29);
		var13.addVertexWithUV(par2 + par8 * (1.0D - var49) + var47, par4 + var49, par6 + par10 * (1.0D - var49) + var47, var27, var29);
		var13.addVertexWithUV(par2 + par8 * (1.0D - var49) + var47, par4 + var49, par6 + par10 * (1.0D - var49) - var47, var27, var25);
		var13.addVertexWithUV(par2 + var47 + par8, par4, par6 - var47 + par10, var35, var33);
		var13.addVertexWithUV(par2 + var47 + par8, par4, par6 + var47 + par10, var35, var37);
		var13.addVertexWithUV(par2 - var47 + par8, par4, par6 + var47 + par10, var31, var37);
		var13.addVertexWithUV(par2 - var47 + par8, par4, par6 - var47 + par10, var31, var33);
		var13.addVertexWithUV(par2 - var47, par4 + 1.0D, var43, var15, var17);
		var13.addVertexWithUV(par2 - var47 + par8, par4 + 0.0D, var43 + par10, var15, var21);
		var13.addVertexWithUV(par2 - var47 + par8, par4 + 0.0D, var45 + par10, var19, var21);
		var13.addVertexWithUV(par2 - var47, par4 + 1.0D, var45, var19, var17);
		var13.addVertexWithUV(par2 + var47, par4 + 1.0D, var45, var15, var17);
		var13.addVertexWithUV(par2 + par8 + var47, par4 + 0.0D, var45 + par10, var15, var21);
		var13.addVertexWithUV(par2 + par8 + var47, par4 + 0.0D, var43 + par10, var19, var21);
		var13.addVertexWithUV(par2 + var47, par4 + 1.0D, var43, var19, var17);
		var13.addVertexWithUV(var39, par4 + 1.0D, par6 + var47, var15, var17);
		var13.addVertexWithUV(var39 + par8, par4 + 0.0D, par6 + var47 + par10, var15, var21);
		var13.addVertexWithUV(var41 + par8, par4 + 0.0D, par6 + var47 + par10, var19, var21);
		var13.addVertexWithUV(var41, par4 + 1.0D, par6 + var47, var19, var17);
		var13.addVertexWithUV(var41, par4 + 1.0D, par6 - var47, var15, var17);
		var13.addVertexWithUV(var41 + par8, par4 + 0.0D, par6 - var47 + par10, var15, var21);
		var13.addVertexWithUV(var39 + par8, par4 + 0.0D, par6 - var47 + par10, var19, var21);
		var13.addVertexWithUV(var39, par4 + 1.0D, par6 - var47, var19, var17);
	}
	
	public void setOverrideBlockTexture(Icon par1Icon)
	{
		overrideBlockTexture = par1Icon;
	}
	
	public void setRenderBounds(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		if(!lockBlockBounds)
		{
			renderMinX = par1;
			renderMaxX = par7;
			renderMinY = par3;
			renderMaxY = par9;
			renderMinZ = par5;
			renderMaxZ = par11;
			partialRenderBounds = minecraftRB.gameSettings.ambientOcclusion >= 2 && (renderMinX > 0.0D || renderMaxX < 1.0D || renderMinY > 0.0D || renderMaxY < 1.0D || renderMinZ > 0.0D || renderMaxZ < 1.0D);
		}
	}
	
	public void setRenderBoundsFromBlock(Block par1Block)
	{
		if(!lockBlockBounds)
		{
			renderMinX = par1Block.getBlockBoundsMinX();
			renderMaxX = par1Block.getBlockBoundsMaxX();
			renderMinY = par1Block.getBlockBoundsMinY();
			renderMaxY = par1Block.getBlockBoundsMaxY();
			renderMinZ = par1Block.getBlockBoundsMinZ();
			renderMaxZ = par1Block.getBlockBoundsMaxZ();
			partialRenderBounds = minecraftRB.gameSettings.ambientOcclusion >= 2 && (renderMinX > 0.0D || renderMaxX < 1.0D || renderMinY > 0.0D || renderMaxY < 1.0D || renderMinZ > 0.0D || renderMaxZ < 1.0D);
		}
	}
	
	public void unlockBlockBounds()
	{
		lockBlockBounds = false;
	}
	
	public static boolean renderItemIn3d(int par0)
	{
		return par0 == 0 ? true : par0 == 31 ? true : par0 == 39 ? true : par0 == 13 ? true : par0 == 10 ? true : par0 == 11 ? true : par0 == 27 ? true : par0 == 22 ? true : par0 == 21 ? true : par0 == 16 ? true : par0 == 26 ? true : par0 == 32 ? true : par0 == 34 ? true : par0 == 35;
	}
}
