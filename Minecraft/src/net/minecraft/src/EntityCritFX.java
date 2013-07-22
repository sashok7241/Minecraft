package net.minecraft.src;

public class EntityCritFX extends EntityFX
{
	float field_70561_a;
	
	public EntityCritFX(World p_i3182_1_, double p_i3182_2_, double p_i3182_4_, double p_i3182_6_, double p_i3182_8_, double p_i3182_10_, double p_i3182_12_)
	{
		this(p_i3182_1_, p_i3182_2_, p_i3182_4_, p_i3182_6_, p_i3182_8_, p_i3182_10_, p_i3182_12_, 1.0F);
	}
	
	public EntityCritFX(World p_i3183_1_, double p_i3183_2_, double p_i3183_4_, double p_i3183_6_, double p_i3183_8_, double p_i3183_10_, double p_i3183_12_, float p_i3183_14_)
	{
		super(p_i3183_1_, p_i3183_2_, p_i3183_4_, p_i3183_6_, 0.0D, 0.0D, 0.0D);
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += p_i3183_8_ * 0.4D;
		motionY += p_i3183_10_ * 0.4D;
		motionZ += p_i3183_12_ * 0.4D;
		particleRed = particleGreen = particleBlue = (float) (Math.random() * 0.30000001192092896D + 0.6000000238418579D);
		particleScale *= 0.75F;
		particleScale *= p_i3183_14_;
		field_70561_a = particleScale;
		particleMaxAge = (int) (6.0D / (Math.random() * 0.8D + 0.6D));
		particleMaxAge = (int) (particleMaxAge * p_i3183_14_);
		noClip = false;
		setParticleTextureIndex(65);
		onUpdate();
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
		particleGreen = (float) (particleGreen * 0.96D);
		particleBlue = (float) (particleBlue * 0.9D);
		motionX *= 0.699999988079071D;
		motionY *= 0.699999988079071D;
		motionZ *= 0.699999988079071D;
		motionY -= 0.019999999552965164D;
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
		particleScale = field_70561_a * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
