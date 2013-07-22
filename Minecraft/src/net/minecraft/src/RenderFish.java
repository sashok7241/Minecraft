package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class RenderFish extends Render
{
	private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRenderFishHook((EntityFishHook) par1Entity, par2, par4, par6, par8, par9);
	}
	
	public void doRenderFishHook(EntityFishHook par1EntityFishHook, double par2, double par4, double par6, float par8, float par9)
	{
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		func_110777_b(par1EntityFishHook);
		Tessellator var10 = Tessellator.instance;
		byte var11 = 1;
		byte var12 = 2;
		float var13 = (var11 * 8 + 0) / 128.0F;
		float var14 = (var11 * 8 + 8) / 128.0F;
		float var15 = (var12 * 8 + 0) / 128.0F;
		float var16 = (var12 * 8 + 8) / 128.0F;
		float var17 = 1.0F;
		float var18 = 0.5F;
		float var19 = 0.5F;
		GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		var10.startDrawingQuads();
		var10.setNormal(0.0F, 1.0F, 0.0F);
		var10.addVertexWithUV(0.0F - var18, 0.0F - var19, 0.0D, var13, var16);
		var10.addVertexWithUV(var17 - var18, 0.0F - var19, 0.0D, var14, var16);
		var10.addVertexWithUV(var17 - var18, 1.0F - var19, 0.0D, var14, var15);
		var10.addVertexWithUV(0.0F - var18, 1.0F - var19, 0.0D, var13, var15);
		var10.draw();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		if(par1EntityFishHook.angler != null)
		{
			float var20 = par1EntityFishHook.angler.getSwingProgress(par9);
			float var21 = MathHelper.sin(MathHelper.sqrt_float(var20) * (float) Math.PI);
			Vec3 var22 = par1EntityFishHook.worldObj.getWorldVec3Pool().getVecFromPool(-0.5D, 0.03D, 0.8D);
			var22.rotateAroundX(-(par1EntityFishHook.angler.prevRotationPitch + (par1EntityFishHook.angler.rotationPitch - par1EntityFishHook.angler.prevRotationPitch) * par9) * (float) Math.PI / 180.0F);
			var22.rotateAroundY(-(par1EntityFishHook.angler.prevRotationYaw + (par1EntityFishHook.angler.rotationYaw - par1EntityFishHook.angler.prevRotationYaw) * par9) * (float) Math.PI / 180.0F);
			var22.rotateAroundY(var21 * 0.5F);
			var22.rotateAroundX(-var21 * 0.7F);
			double var23 = par1EntityFishHook.angler.prevPosX + (par1EntityFishHook.angler.posX - par1EntityFishHook.angler.prevPosX) * par9 + var22.xCoord;
			double var25 = par1EntityFishHook.angler.prevPosY + (par1EntityFishHook.angler.posY - par1EntityFishHook.angler.prevPosY) * par9 + var22.yCoord;
			double var27 = par1EntityFishHook.angler.prevPosZ + (par1EntityFishHook.angler.posZ - par1EntityFishHook.angler.prevPosZ) * par9 + var22.zCoord;
			double var29 = par1EntityFishHook.angler == Minecraft.getMinecraft().thePlayer ? 0.0D : (double) par1EntityFishHook.angler.getEyeHeight();
			if(renderManager.options.thirdPersonView > 0 || par1EntityFishHook.angler != Minecraft.getMinecraft().thePlayer)
			{
				float var31 = (par1EntityFishHook.angler.prevRenderYawOffset + (par1EntityFishHook.angler.renderYawOffset - par1EntityFishHook.angler.prevRenderYawOffset) * par9) * (float) Math.PI / 180.0F;
				double var32 = MathHelper.sin(var31);
				double var34 = MathHelper.cos(var31);
				var23 = par1EntityFishHook.angler.prevPosX + (par1EntityFishHook.angler.posX - par1EntityFishHook.angler.prevPosX) * par9 - var34 * 0.35D - var32 * 0.85D;
				var25 = par1EntityFishHook.angler.prevPosY + var29 + (par1EntityFishHook.angler.posY - par1EntityFishHook.angler.prevPosY) * par9 - 0.45D;
				var27 = par1EntityFishHook.angler.prevPosZ + (par1EntityFishHook.angler.posZ - par1EntityFishHook.angler.prevPosZ) * par9 - var32 * 0.35D + var34 * 0.85D;
			}
			double var46 = par1EntityFishHook.prevPosX + (par1EntityFishHook.posX - par1EntityFishHook.prevPosX) * par9;
			double var33 = par1EntityFishHook.prevPosY + (par1EntityFishHook.posY - par1EntityFishHook.prevPosY) * par9 + 0.25D;
			double var35 = par1EntityFishHook.prevPosZ + (par1EntityFishHook.posZ - par1EntityFishHook.prevPosZ) * par9;
			double var37 = (float) (var23 - var46);
			double var39 = (float) (var25 - var33);
			double var41 = (float) (var27 - var35);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_LIGHTING);
			var10.startDrawing(3);
			var10.setColorOpaque_I(0);
			byte var43 = 16;
			for(int var44 = 0; var44 <= var43; ++var44)
			{
				float var45 = (float) var44 / (float) var43;
				var10.addVertex(par2 + var37 * var45, par4 + var39 * (var45 * var45 + var45) * 0.5D + 0.25D, par6 + var41 * var45);
			}
			var10.draw();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110791_a((EntityFishHook) par1Entity);
	}
	
	protected ResourceLocation func_110791_a(EntityFishHook par1EntityFishHook)
	{
		return field_110792_a;
	}
}
