package net.minecraft.src;

public class EntityDiggingFX extends EntityFX
{
	private Block blockInstance;
	
	public EntityDiggingFX(World p_i9005_1_, double p_i9005_2_, double p_i9005_4_, double p_i9005_6_, double p_i9005_8_, double p_i9005_10_, double p_i9005_12_, Block p_i9005_14_, int p_i9005_15_, int p_i9005_16_, RenderEngine p_i9005_17_)
	{
		super(p_i9005_1_, p_i9005_2_, p_i9005_4_, p_i9005_6_, p_i9005_8_, p_i9005_10_, p_i9005_12_);
		blockInstance = p_i9005_14_;
		setParticleIcon(p_i9005_17_, p_i9005_14_.getIcon(0, p_i9005_16_));
		particleGravity = p_i9005_14_.blockParticleGravity;
		particleRed = particleGreen = particleBlue = 0.6F;
		particleScale /= 2.0F;
	}
	
	public EntityDiggingFX applyColourMultiplier(int par1, int par2, int par3)
	{
		if(blockInstance == Block.grass) return this;
		else
		{
			int var4 = blockInstance.colorMultiplier(worldObj, par1, par2, par3);
			particleRed *= (var4 >> 16 & 255) / 255.0F;
			particleGreen *= (var4 >> 8 & 255) / 255.0F;
			particleBlue *= (var4 & 255) / 255.0F;
			return this;
		}
	}
	
	public EntityDiggingFX applyRenderColor(int par1)
	{
		if(blockInstance == Block.grass) return this;
		else
		{
			int var2 = blockInstance.getRenderColor(par1);
			particleRed *= (var2 >> 16 & 255) / 255.0F;
			particleGreen *= (var2 >> 8 & 255) / 255.0F;
			particleBlue *= (var2 & 255) / 255.0F;
			return this;
		}
	}
	
	@Override public int getFXLayer()
	{
		return 1;
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (particleTextureIndexX + particleTextureJitterX / 4.0F) / 16.0F;
		float var9 = var8 + 0.015609375F;
		float var10 = (particleTextureIndexY + particleTextureJitterY / 4.0F) / 16.0F;
		float var11 = var10 + 0.015609375F;
		float var12 = 0.1F * particleScale;
		if(particleIcon != null)
		{
			var8 = particleIcon.getInterpolatedU(particleTextureJitterX / 4.0F * 16.0F);
			var9 = particleIcon.getInterpolatedU((particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
			var10 = particleIcon.getInterpolatedV(particleTextureJitterY / 4.0F * 16.0F);
			var11 = particleIcon.getInterpolatedV((particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
		}
		float var13 = (float) (prevPosX + (posX - prevPosX) * par2 - interpPosX);
		float var14 = (float) (prevPosY + (posY - prevPosY) * par2 - interpPosY);
		float var15 = (float) (prevPosZ + (posZ - prevPosZ) * par2 - interpPosZ);
		float var16 = 1.0F;
		par1Tessellator.setColorOpaque_F(var16 * particleRed, var16 * particleGreen, var16 * particleBlue);
		par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var8, var11);
		par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var8, var10);
		par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var9, var10);
		par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var9, var11);
	}
}
