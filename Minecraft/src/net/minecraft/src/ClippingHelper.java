package net.minecraft.src;

public class ClippingHelper
{
	public float[][] frustum = new float[16][16];
	public float[] projectionMatrix = new float[16];
	public float[] modelviewMatrix = new float[16];
	public float[] clippingMatrix = new float[16];
	
	public boolean isBoxInFrustum(double par1, double par3, double par5, double par7, double par9, double par11)
	{
		for(int var13 = 0; var13 < 6; ++var13)
		{
			if(frustum[var13][0] * par1 + frustum[var13][1] * par3 + frustum[var13][2] * par5 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par7 + frustum[var13][1] * par3 + frustum[var13][2] * par5 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par1 + frustum[var13][1] * par9 + frustum[var13][2] * par5 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par7 + frustum[var13][1] * par9 + frustum[var13][2] * par5 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par1 + frustum[var13][1] * par3 + frustum[var13][2] * par11 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par7 + frustum[var13][1] * par3 + frustum[var13][2] * par11 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par1 + frustum[var13][1] * par9 + frustum[var13][2] * par11 + frustum[var13][3] <= 0.0D && frustum[var13][0] * par7 + frustum[var13][1] * par9 + frustum[var13][2] * par11 + frustum[var13][3] <= 0.0D) return false;
		}
		return true;
	}
}
