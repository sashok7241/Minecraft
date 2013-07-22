package net.minecraft.src;

public class EntityExplodeFX extends EntityFX
{
	public EntityExplodeFX(World p_i3180_1_, double p_i3180_2_, double p_i3180_4_, double p_i3180_6_, double p_i3180_8_, double p_i3180_10_, double p_i3180_12_)
	{
		super(p_i3180_1_, p_i3180_2_, p_i3180_4_, p_i3180_6_, p_i3180_8_, p_i3180_10_, p_i3180_12_);
		motionX = p_i3180_8_ + (float) (Math.random() * 2.0D - 1.0D) * 0.05F;
		motionY = p_i3180_10_ + (float) (Math.random() * 2.0D - 1.0D) * 0.05F;
		motionZ = p_i3180_12_ + (float) (Math.random() * 2.0D - 1.0D) * 0.05F;
		particleRed = particleGreen = particleBlue = rand.nextFloat() * 0.3F + 0.7F;
		particleScale = rand.nextFloat() * rand.nextFloat() * 6.0F + 1.0F;
		particleMaxAge = (int) (16.0D / (rand.nextFloat() * 0.8D + 0.2D)) + 2;
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
		motionX *= 0.8999999761581421D;
		motionY *= 0.8999999761581421D;
		motionZ *= 0.8999999761581421D;
		if(onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
}
