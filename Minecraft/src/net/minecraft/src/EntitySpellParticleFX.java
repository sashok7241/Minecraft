package net.minecraft.src;

public class EntitySpellParticleFX extends EntityFX
{
	private int baseSpellTextureIndex = 128;
	
	public EntitySpellParticleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		motionY *= 0.20000000298023224D;
		if(par8 == 0.0D && par12 == 0.0D)
		{
			motionX *= 0.10000000149011612D;
			motionZ *= 0.10000000149011612D;
		}
		particleScale *= 0.75F;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
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
		setParticleTextureIndex(baseSpellTextureIndex + 7 - particleAge * 8 / particleMaxAge);
		motionY += 0.004D;
		moveEntity(motionX, motionY, motionZ);
		if(posY == prevPosY)
		{
			motionX *= 1.1D;
			motionZ *= 1.1D;
		}
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
		float var8 = (particleAge + par2) / particleMaxAge * 32.0F;
		if(var8 < 0.0F)
		{
			var8 = 0.0F;
		}
		if(var8 > 1.0F)
		{
			var8 = 1.0F;
		}
		super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
	
	public void setBaseSpellTextureIndex(int par1)
	{
		baseSpellTextureIndex = par1;
	}
}
