package net.minecraft.src;

public class EntityFlameFX extends EntityFX
{
	private float flameScale;
	
	public EntityFlameFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		motionX = motionX * 0.009999999776482582D + par8;
		motionY = motionY * 0.009999999776482582D + par10;
		motionZ = motionZ * 0.009999999776482582D + par12;
		double var10000 = par2 + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
		var10000 = par4 + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
		var10000 = par6 + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
		flameScale = particleScale;
		particleRed = particleGreen = particleBlue = 1.0F;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
		noClip = true;
		setParticleTextureIndex(48);
	}
	
	@Override public float getBrightness(float par1)
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
		float var3 = super.getBrightness(par1);
		return var3 * var2 + (1.0F - var2);
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
		int var4 = var3 & 255;
		int var5 = var3 >> 16 & 255;
		var4 += (int) (var2 * 15.0F * 16.0F);
		if(var4 > 240)
		{
			var4 = 240;
		}
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
		moveEntity(motionX, motionY, motionZ);
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
		float var8 = (particleAge + par2) / particleMaxAge;
		particleScale = flameScale * (1.0F - var8 * var8 * 0.5F);
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
