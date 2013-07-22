package net.minecraft.src;

public class EntitySmokeFX extends EntityFX
{
	float smokeParticleScale;
	
	public EntitySmokeFX(World p_i3152_1_, double p_i3152_2_, double p_i3152_4_, double p_i3152_6_, double p_i3152_8_, double p_i3152_10_, double p_i3152_12_)
	{
		this(p_i3152_1_, p_i3152_2_, p_i3152_4_, p_i3152_6_, p_i3152_8_, p_i3152_10_, p_i3152_12_, 1.0F);
	}
	
	public EntitySmokeFX(World p_i3153_1_, double p_i3153_2_, double p_i3153_4_, double p_i3153_6_, double p_i3153_8_, double p_i3153_10_, double p_i3153_12_, float p_i3153_14_)
	{
		super(p_i3153_1_, p_i3153_2_, p_i3153_4_, p_i3153_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += p_i3153_8_;
		motionY += p_i3153_10_;
		motionZ += p_i3153_12_;
		particleRed = particleGreen = particleBlue = (float) (Math.random() * 0.30000001192092896D);
		particleScale *= 0.75F;
		particleScale *= p_i3153_14_;
		smokeParticleScale = particleScale;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		particleMaxAge = (int) (particleMaxAge * p_i3153_14_);
		noClip = false;
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
		setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
		motionY += 0.004D;
		moveEntity(motionX, motionY, motionZ);
		if(posY == prevPosY)
		{
			motionX *= 1.1D;
			motionZ *= 1.1D;
		}
		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;
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
		particleScale = smokeParticleScale * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
