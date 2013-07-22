package net.minecraft.src;

public class EntityExplodeFX extends EntityFX
{
	public EntityExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		motionX = par8 + (float) (Math.random() * 2.0D - 1.0D) * 0.05F;
		motionY = par10 + (float) (Math.random() * 2.0D - 1.0D) * 0.05F;
		motionZ = par12 + (float) (Math.random() * 2.0D - 1.0D) * 0.05F;
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
