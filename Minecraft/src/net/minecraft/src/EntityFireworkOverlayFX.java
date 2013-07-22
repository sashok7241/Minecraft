package net.minecraft.src;

public class EntityFireworkOverlayFX extends EntityFX
{
	protected EntityFireworkOverlayFX(World p_i8004_1_, double p_i8004_2_, double p_i8004_4_, double p_i8004_6_)
	{
		super(p_i8004_1_, p_i8004_2_, p_i8004_4_, p_i8004_6_);
		particleMaxAge = 4;
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = 0.25F;
		float var9 = var8 + 0.25F;
		float var10 = 0.125F;
		float var11 = var10 + 0.25F;
		float var12 = 7.1F * MathHelper.sin((particleAge + par2 - 1.0F) * 0.25F * (float) Math.PI);
		particleAlpha = 0.6F - (particleAge + par2 - 1.0F) * 0.25F * 0.5F;
		float var13 = (float) (prevPosX + (posX - prevPosX) * par2 - interpPosX);
		float var14 = (float) (prevPosY + (posY - prevPosY) * par2 - interpPosY);
		float var15 = (float) (prevPosZ + (posZ - prevPosZ) * par2 - interpPosZ);
		par1Tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
		par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
		par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
		par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
	}
}
