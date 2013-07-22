package net.minecraft.src;

public class EntityAuraFX extends EntityFX
{
	public EntityAuraFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
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
