package net.minecraft.src;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ActiveRenderInfo
{
	public static float objectX = 0.0F;
	public static float objectY = 0.0F;
	public static float objectZ = 0.0F;
	private static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
	private static FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
	private static FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
	private static FloatBuffer objectCoords = GLAllocation.createDirectFloatBuffer(3);
	public static float rotationX;
	public static float rotationXZ;
	public static float rotationZ;
	public static float rotationYZ;
	public static float rotationXY;
	
	public static int getBlockIdAtEntityViewpoint(World par0World, EntityLiving par1EntityLiving, float par2)
	{
		Vec3 var3 = projectViewFromEntity(par1EntityLiving, par2);
		ChunkPosition var4 = new ChunkPosition(var3);
		int var5 = par0World.getBlockId(var4.x, var4.y, var4.z);
		if(var5 != 0 && Block.blocksList[var5].blockMaterial.isLiquid())
		{
			float var6 = BlockFluid.getFluidHeightPercent(par0World.getBlockMetadata(var4.x, var4.y, var4.z)) - 0.11111111F;
			float var7 = var4.y + 1 - var6;
			if(var3.yCoord >= var7)
			{
				var5 = par0World.getBlockId(var4.x, var4.y + 1, var4.z);
			}
		}
		return var5;
	}
	
	public static Vec3 projectViewFromEntity(EntityLiving par0EntityLiving, double par1)
	{
		double var3 = par0EntityLiving.prevPosX + (par0EntityLiving.posX - par0EntityLiving.prevPosX) * par1;
		double var5 = par0EntityLiving.prevPosY + (par0EntityLiving.posY - par0EntityLiving.prevPosY) * par1 + par0EntityLiving.getEyeHeight();
		double var7 = par0EntityLiving.prevPosZ + (par0EntityLiving.posZ - par0EntityLiving.prevPosZ) * par1;
		double var9 = var3 + objectX * 1.0F;
		double var11 = var5 + objectY * 1.0F;
		double var13 = var7 + objectZ * 1.0F;
		return par0EntityLiving.worldObj.getWorldVec3Pool().getVecFromPool(var9, var11, var13);
	}
	
	public static void updateRenderInfo(EntityPlayer par0EntityPlayer, boolean par1)
	{
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		float var2 = (viewport.get(0) + viewport.get(2)) / 2;
		float var3 = (viewport.get(1) + viewport.get(3)) / 2;
		GLU.gluUnProject(var2, var3, 0.0F, modelview, projection, viewport, objectCoords);
		objectX = objectCoords.get(0);
		objectY = objectCoords.get(1);
		objectZ = objectCoords.get(2);
		int var4 = par1 ? 1 : 0;
		float var5 = par0EntityPlayer.rotationPitch;
		float var6 = par0EntityPlayer.rotationYaw;
		rotationX = MathHelper.cos(var6 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationZ = MathHelper.sin(var6 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationYZ = -rotationZ * MathHelper.sin(var5 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationXY = rotationX * MathHelper.sin(var5 * (float) Math.PI / 180.0F) * (1 - var4 * 2);
		rotationXZ = MathHelper.cos(var5 * (float) Math.PI / 180.0F);
	}
}
