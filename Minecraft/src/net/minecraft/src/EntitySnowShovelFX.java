package net.minecraft.src;

public class EntitySnowShovelFX extends EntityFX
{
	float snowDigParticleScale;
	
	public EntitySnowShovelFX(World p_i3160_1_, double p_i3160_2_, double p_i3160_4_, double p_i3160_6_, double p_i3160_8_, double p_i3160_10_, double p_i3160_12_)
	{
		this(p_i3160_1_, p_i3160_2_, p_i3160_4_, p_i3160_6_, p_i3160_8_, p_i3160_10_, p_i3160_12_, 1.0F);
	}
	
	public EntitySnowShovelFX(World p_i3161_1_, double p_i3161_2_, double p_i3161_4_, double p_i3161_6_, double p_i3161_8_, double p_i3161_10_, double p_i3161_12_, float p_i3161_14_)
	{
		super(p_i3161_1_, p_i3161_2_, p_i3161_4_, p_i3161_6_, p_i3161_8_, p_i3161_10_, p_i3161_12_);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += p_i3161_8_;
		motionY += p_i3161_10_;
		motionZ += p_i3161_12_;
		particleRed = particleGreen = particleBlue = 1.0F - (float) (Math.random() * 0.30000001192092896D);
		particleScale *= 0.75F;
		particleScale *= p_i3161_14_;
		snowDigParticleScale = particleScale;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
		particleMaxAge = (int) (particleMaxAge * p_i3161_14_);
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
		motionY -= 0.03D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9900000095367432D;
		motionY *= 0.9900000095367432D;
		motionZ *= 0.9900000095367432D;
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
		particleScale = snowDigParticleScale * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
