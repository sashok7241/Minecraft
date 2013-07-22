package net.minecraft.src;


public abstract class Render
{
	private static final ResourceLocation field_110778_a = new ResourceLocation("textures/misc/shadow.png");
	protected RenderManager renderManager;
	protected RenderBlocks renderBlocks = new RenderBlocks();
	protected float shadowSize;
	protected float shadowOpaque = 1.0F;
	
	public abstract void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9);
	
	public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		if(renderManager.options.fancyGraphics && shadowSize > 0.0F && !par1Entity.isInvisible())
		{
			double var10 = renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
			float var12 = (float) ((1.0D - var10 / 256.0D) * shadowOpaque);
			if(var12 > 0.0F)
			{
				renderShadow(par1Entity, par2, par4, par6, var12, par9);
			}
		}
		if(par1Entity.canRenderOnFire())
		{
			renderEntityOnFire(par1Entity, par2, par4, par6, par9);
		}
	}
	
	protected abstract ResourceLocation func_110775_a(Entity var1);
	
	protected void func_110776_a(ResourceLocation par1ResourceLocation)
	{
		renderManager.renderEngine.func_110577_a(par1ResourceLocation);
	}
	
	protected void func_110777_b(Entity par1Entity)
	{
		func_110776_a(func_110775_a(par1Entity));
	}
	
	public FontRenderer getFontRendererFromRenderManager()
	{
		return renderManager.getFontRenderer();
	}
	
	private World getWorldFromRenderManager()
	{
		return renderManager.worldObj;
	}
	
	private void renderEntityOnFire(Entity par1Entity, double par2, double par4, double par6, float par8)
	{
		GL11.glDisable(GL11.GL_LIGHTING);
		Icon var9 = Block.fire.func_94438_c(0);
		Icon var10 = Block.fire.func_94438_c(1);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		float var11 = par1Entity.width * 1.4F;
		GL11.glScalef(var11, var11, var11);
		Tessellator var12 = Tessellator.instance;
		float var13 = 0.5F;
		float var14 = 0.0F;
		float var15 = par1Entity.height / var11;
		float var16 = (float) (par1Entity.posY - par1Entity.boundingBox.minY);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.0F, -0.3F + (int) var15 * 0.02F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float var17 = 0.0F;
		int var18 = 0;
		var12.startDrawingQuads();
		while(var15 > 0.0F)
		{
			Icon var19 = var18 % 2 == 0 ? var9 : var10;
			func_110776_a(TextureMap.field_110575_b);
			float var20 = var19.getMinU();
			float var21 = var19.getMinV();
			float var22 = var19.getMaxU();
			float var23 = var19.getMaxV();
			if(var18 / 2 % 2 == 0)
			{
				float var24 = var22;
				var22 = var20;
				var20 = var24;
			}
			var12.addVertexWithUV(var13 - var14, 0.0F - var16, var17, var22, var23);
			var12.addVertexWithUV(-var13 - var14, 0.0F - var16, var17, var20, var23);
			var12.addVertexWithUV(-var13 - var14, 1.4F - var16, var17, var20, var21);
			var12.addVertexWithUV(var13 - var14, 1.4F - var16, var17, var22, var21);
			var15 -= 0.45F;
			var16 -= 0.45F;
			var13 *= 0.9F;
			var17 += 0.03F;
			++var18;
		}
		var12.draw();
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private void renderShadow(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		renderManager.renderEngine.func_110577_a(field_110778_a);
		World var10 = getWorldFromRenderManager();
		GL11.glDepthMask(false);
		float var11 = shadowSize;
		if(par1Entity instanceof EntityLiving)
		{
			EntityLiving var12 = (EntityLiving) par1Entity;
			var11 *= var12.getRenderSizeModifier();
			if(var12.isChild())
			{
				var11 *= 0.5F;
			}
		}
		double var35 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par9;
		double var14 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par9 + par1Entity.getShadowSize();
		double var16 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par9;
		int var18 = MathHelper.floor_double(var35 - var11);
		int var19 = MathHelper.floor_double(var35 + var11);
		int var20 = MathHelper.floor_double(var14 - var11);
		int var21 = MathHelper.floor_double(var14);
		int var22 = MathHelper.floor_double(var16 - var11);
		int var23 = MathHelper.floor_double(var16 + var11);
		double var24 = par2 - var35;
		double var26 = par4 - var14;
		double var28 = par6 - var16;
		Tessellator var30 = Tessellator.instance;
		var30.startDrawingQuads();
		for(int var31 = var18; var31 <= var19; ++var31)
		{
			for(int var32 = var20; var32 <= var21; ++var32)
			{
				for(int var33 = var22; var33 <= var23; ++var33)
				{
					int var34 = var10.getBlockId(var31, var32 - 1, var33);
					if(var34 > 0 && var10.getBlockLightValue(var31, var32, var33) > 3)
					{
						renderShadowOnBlock(Block.blocksList[var34], par2, par4 + par1Entity.getShadowSize(), par6, var31, var32, var33, par8, var11, var24, var26 + par1Entity.getShadowSize(), var28);
					}
				}
			}
		}
		var30.draw();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
	}
	
	private void renderShadowOnBlock(Block par1Block, double par2, double par4, double par6, int par8, int par9, int par10, float par11, float par12, double par13, double par15, double par17)
	{
		Tessellator var19 = Tessellator.instance;
		if(par1Block.renderAsNormalBlock())
		{
			double var20 = (par11 - (par4 - (par9 + par15)) / 2.0D) * 0.5D * getWorldFromRenderManager().getLightBrightness(par8, par9, par10);
			if(var20 >= 0.0D)
			{
				if(var20 > 1.0D)
				{
					var20 = 1.0D;
				}
				var19.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float) var20);
				double var22 = par8 + par1Block.getBlockBoundsMinX() + par13;
				double var24 = par8 + par1Block.getBlockBoundsMaxX() + par13;
				double var26 = par9 + par1Block.getBlockBoundsMinY() + par15 + 0.015625D;
				double var28 = par10 + par1Block.getBlockBoundsMinZ() + par17;
				double var30 = par10 + par1Block.getBlockBoundsMaxZ() + par17;
				float var32 = (float) ((par2 - var22) / 2.0D / par12 + 0.5D);
				float var33 = (float) ((par2 - var24) / 2.0D / par12 + 0.5D);
				float var34 = (float) ((par6 - var28) / 2.0D / par12 + 0.5D);
				float var35 = (float) ((par6 - var30) / 2.0D / par12 + 0.5D);
				var19.addVertexWithUV(var22, var26, var28, var32, var34);
				var19.addVertexWithUV(var22, var26, var30, var32, var35);
				var19.addVertexWithUV(var24, var26, var30, var33, var35);
				var19.addVertexWithUV(var24, var26, var28, var33, var34);
			}
		}
	}
	
	public void setRenderManager(RenderManager par1RenderManager)
	{
		renderManager = par1RenderManager;
	}
	
	public void updateIcons(IconRegister par1IconRegister)
	{
	}
	
	public static void renderAABB(AxisAlignedBB par0AxisAlignedBB)
	{
		Tessellator var1 = Tessellator.instance;
		var1.startDrawingQuads();
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var1.draw();
	}
	
	public static void renderOffsetAABB(AxisAlignedBB par0AxisAlignedBB, double par1, double par3, double par5)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator var7 = Tessellator.instance;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		var7.startDrawingQuads();
		var7.setTranslation(par1, par3, par5);
		var7.setNormal(0.0F, 0.0F, -1.0F);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var7.setNormal(0.0F, 0.0F, 1.0F);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var7.setNormal(0.0F, -1.0F, 0.0F);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var7.setNormal(0.0F, 1.0F, 0.0F);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var7.setNormal(-1.0F, 0.0F, 0.0F);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var7.setNormal(1.0F, 0.0F, 0.0F);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
		var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
		var7.setTranslation(0.0D, 0.0D, 0.0D);
		var7.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
