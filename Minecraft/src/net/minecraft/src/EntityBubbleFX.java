package net.minecraft.src;

public class EntityBubbleFX extends EntityFX
{
	public EntityBubbleFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, par8, par10, par12);
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		setParticleTextureIndex(32);
		setSize(0.02F, 0.02F);
		particleScale *= rand.nextFloat() * 0.6F + 0.2F;
		motionX = par8 * 0.20000000298023224D + (float) (Math.random() * 2.0D - 1.0D) * 0.02F;
		motionY = par10 * 0.20000000298023224D + (float) (Math.random() * 2.0D - 1.0D) * 0.02F;
		motionZ = par12 * 0.20000000298023224D + (float) (Math.random() * 2.0D - 1.0D) * 0.02F;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY += 0.002D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.8500000238418579D;
		motionY *= 0.8500000238418579D;
		motionZ *= 0.8500000238418579D;
		if(worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) != Material.water)
		{
			setDead();
		}
		if(particleMaxAge-- <= 0)
		{
			setDead();
		}
	}
}
