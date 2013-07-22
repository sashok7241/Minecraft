package net.minecraft.src;

public class EntityAuraFX extends EntityFX
{
	public EntityAuraFX(World p_i3166_1_, double p_i3166_2_, double p_i3166_4_, double p_i3166_6_, double p_i3166_8_, double p_i3166_10_, double p_i3166_12_)
	{
		super(p_i3166_1_, p_i3166_2_, p_i3166_4_, p_i3166_6_, p_i3166_8_, p_i3166_10_, p_i3166_12_);
		float var14 = rand.nextFloat() * 0.1F + 0.2F;
		particleRed = var14;
		particleGreen = var14;
		particleBlue = var14;
		setParticleTextureIndex(0);
		setSize(0.02F, 0.02F);
		particleScale *= rand.nextFloat() * 0.6F + 0.5F;
		motionX *= 0.019999999552965164D;
		motionY *= 0.019999999552965164D;
		motionZ *= 0.019999999552965164D;
		particleMaxAge = (int) (20.0D / (Math.random() * 0.8D + 0.2D));
		noClip = true;
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.99D;
		motionY *= 0.99D;
		motionZ *= 0.99D;
		if(particleMaxAge-- <= 0)
		{
			setDead();
		}
	}
}
