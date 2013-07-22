package net.minecraft.src;

public class EntityBreakingFX extends EntityFX
{
	public EntityBreakingFX(World p_i9004_1_, double p_i9004_2_, double p_i9004_4_, double p_i9004_6_, double p_i9004_8_, double p_i9004_10_, double p_i9004_12_, Item p_i9004_14_, RenderEngine p_i9004_15_)
	{
		this(p_i9004_1_, p_i9004_2_, p_i9004_4_, p_i9004_6_, p_i9004_14_, p_i9004_15_);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += p_i9004_8_;
		motionY += p_i9004_10_;
		motionZ += p_i9004_12_;
	}
	
	public EntityBreakingFX(World p_i9003_1_, double p_i9003_2_, double p_i9003_4_, double p_i9003_6_, Item p_i9003_8_, RenderEngine p_i9003_9_)
	{
		super(p_i9003_1_, p_i9003_2_, p_i9003_4_, p_i9003_6_, 0.0D, 0.0D, 0.0D);
		setParticleIcon(p_i9003_9_, p_i9003_8_.getIconFromDamage(0));
		particleRed = particleGreen = particleBlue = 1.0F;
		particleGravity = Block.blockSnow.blockParticleGravity;
		particleScale /= 2.0F;
	}
	
	@Override public int getFXLayer()
	{
		return 2;
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
