package net.minecraft.src;

public class EntityNoteFX extends EntityFX
{
	float noteParticleScale;
	
	public EntityNoteFX(World p_i3158_1_, double p_i3158_2_, double p_i3158_4_, double p_i3158_6_, double p_i3158_8_, double p_i3158_10_, double p_i3158_12_)
	{
		this(p_i3158_1_, p_i3158_2_, p_i3158_4_, p_i3158_6_, p_i3158_8_, p_i3158_10_, p_i3158_12_, 2.0F);
	}
	
	public EntityNoteFX(World p_i3159_1_, double p_i3159_2_, double p_i3159_4_, double p_i3159_6_, double p_i3159_8_, double p_i3159_10_, double p_i3159_12_, float p_i3159_14_)
	{
		super(p_i3159_1_, p_i3159_2_, p_i3159_4_, p_i3159_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.009999999776482582D;
		motionY *= 0.009999999776482582D;
		motionZ *= 0.009999999776482582D;
		motionY += 0.2D;
		particleRed = MathHelper.sin(((float) p_i3159_8_ + 0.0F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
		particleGreen = MathHelper.sin(((float) p_i3159_8_ + 0.33333334F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
		particleBlue = MathHelper.sin(((float) p_i3159_8_ + 0.6666667F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
		particleScale *= 0.75F;
		particleScale *= p_i3159_14_;
		noteParticleScale = particleScale;
		particleMaxAge = 6;
		noClip = false;
		setParticleTextureIndex(64);
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
		motionX *= 0.6600000262260437D;
		motionY *= 0.6600000262260437D;
		motionZ *= 0.6600000262260437D;
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
		particleScale = noteParticleScale * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
