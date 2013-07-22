package net.minecraft.src;

public class EntityLavaFX extends EntityFX
{
	private float lavaParticleScale;
	
	public EntityLavaFX(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		motionX *= 0.800000011920929D;
		motionY *= 0.800000011920929D;
		motionZ *= 0.800000011920929D;
		motionY = rand.nextFloat() * 0.4F + 0.05F;
		particleRed = particleGreen = particleBlue = 1.0F;
		particleScale *= rand.nextFloat() * 2.0F + 0.2F;
		lavaParticleScale = particleScale;
		particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
		noClip = false;
		setParticleTextureIndex(49);
	}
	
	@Override public float getBrightness(float par1)
	{
		return 1.0F;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		float var2 = (particleAge + par1) / particleMaxAge;
		if(var2 < 0.0F)
		{
			var2 = 0.0F;
		}
		if(var2 > 1.0F)
		{
			var2 = 1.0F;
		}
		int var3 = super.getBrightnessForRender(par1);
		short var4 = 240;
		int var5 = var3 >> 16 & 255;
		return var4 | var5 << 16;
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
		float var1 = (float) particleAge / (float) particleMaxAge;
		if(rand.nextFloat() > var1)
		{
			worldObj.spawnParticle("smoke", posX, posY, posZ, motionX, motionY, motionZ);
		}
		motionY -= 0.03D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9990000128746033D;
		motionY *= 0.9990000128746033D;
		motionZ *= 0.9990000128746033D;
		if(onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (particleAge + par2) / particleMaxAge;
		particleScale = lavaParticleScale * (1.0F - var8 * var8);
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
