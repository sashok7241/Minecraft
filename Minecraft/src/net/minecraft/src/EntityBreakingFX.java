package net.minecraft.src;

public class EntityBreakingFX extends EntityFX
{
	public EntityBreakingFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, Item par14Item, RenderEngine par15RenderEngine)
	{
		this(par1World, par2, par4, par6, par14Item, par15RenderEngine);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += par8;
		motionY += par10;
		motionZ += par12;
	}
	
	public EntityBreakingFX(World par1World, double par2, double par4, double par6, Item par8Item, RenderEngine par9RenderEngine)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		setParticleIcon(par9RenderEngine, par8Item.getIconFromDamage(0));
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
