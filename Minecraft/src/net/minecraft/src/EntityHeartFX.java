package net.minecraft.src;

public class EntityHeartFX extends EntityFX
{
	float particleScaleOverTime;
	
	public EntityHeartFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
	}
	
	public EntityHeartFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		motionX *= 0.009999999776482582D;
		motionY *= 0.009999999776482582D;
		motionZ *= 0.009999999776482582D;
		motionY += 0.1D;
		particleScale *= 0.75F;
		particleScale *= par14;
		particleScaleOverTime = particleScale;
		particleMaxAge = 16;
		noClip = false;
		setParticleTextureIndex(80);
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		if(particleAge++ >= particleMaxAge)
		{
			setDead();
		}
		moveEntity(motionX, motionY, motionZ);
		if(posY == prevPosY)
		{
			motionX *= 1.1D;
			motionZ *= 1.1D;
		}
		motionX *= 0.8600000143051147D;
		motionY *= 0.8600000143051147D;
		motionZ *= 0.8600000143051147D;
		if(onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (particleAge + par2) / particleMaxAge * 32.0F;
		if(var8 < 0.0F)
		{
			var8 = 0.0F;
		}
		if(var8 > 1.0F)
		{
			var8 = 1.0F;
		}
		particleScale = particleScaleOverTime * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
