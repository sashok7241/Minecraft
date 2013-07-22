package net.minecraft.src;

public class EntityCloudFX extends EntityFX
{
	float field_70569_a;
	
	public EntityCloudFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		float var14 = 2.5F;
		motionX *= 0.10000000149011612D;
		motionY *= 0.10000000149011612D;
		motionZ *= 0.10000000149011612D;
		motionX += par8;
		motionY += par10;
		motionZ += par12;
		particleRed = particleGreen = particleBlue = 1.0F - (float) (Math.random() * 0.30000001192092896D);
		particleScale *= 0.75F;
		particleScale *= var14;
		field_70569_a = particleScale;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.3D));
		particleMaxAge = (int) (particleMaxAge * var14);
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
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9599999785423279D;
		motionY *= 0.9599999785423279D;
		motionZ *= 0.9599999785423279D;
		EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, 2.0D);
		if(var1 != null && posY > var1.boundingBox.minY)
		{
			posY += (var1.boundingBox.minY - posY) * 0.2D;
			motionY += (var1.motionY - motionY) * 0.2D;
			setPosition(posX, posY, posZ);
		}
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
		particleScale = field_70569_a * var8;
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
}
