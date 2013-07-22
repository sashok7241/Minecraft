package net.minecraft.src;

public class EntityHeartFX extends EntityFX
{
	float particleScaleOverTime;
	
	public EntityHeartFX(World p_i3174_1_, double p_i3174_2_, double p_i3174_4_, double p_i3174_6_, double p_i3174_8_, double p_i3174_10_, double p_i3174_12_)
	{
		this(p_i3174_1_, p_i3174_2_, p_i3174_4_, p_i3174_6_, p_i3174_8_, p_i3174_10_, p_i3174_12_, 2.0F);
	}
	
	public EntityHeartFX(World p_i3175_1_, double p_i3175_2_, double p_i3175_4_, double p_i3175_6_, double p_i3175_8_, double p_i3175_10_, double p_i3175_12_, float p_i3175_14_)
	{
		super(p_i3175_1_, p_i3175_2_, p_i3175_4_, p_i3175_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.009999999776482582D;
		motionY *= 0.009999999776482582D;
		motionZ *= 0.009999999776482582D;
		motionY += 0.1D;
		particleScale *= 0.75F;
		particleScale *= p_i3175_14_;
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
