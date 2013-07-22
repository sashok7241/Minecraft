package net.minecraft.src;

public class EntityRainFX extends EntityFX
{
	public EntityRainFX(World par1World, double par2, double par4, double par6)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		motionX *= 0.30000001192092896D;
		motionY = (float) Math.random() * 0.2F + 0.1F;
		motionZ *= 0.30000001192092896D;
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		setParticleTextureIndex(19 + rand.nextInt(4));
		setSize(0.01F, 0.01F);
		particleGravity = 0.06F;
		particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= particleGravity;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;
		if(particleMaxAge-- <= 0)
		{
			setDead();
		}
		if(onGround)
		{
			if(Math.random() < 0.5D)
			{
				setDead();
			}
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
		Material var1 = worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
		if(var1.isLiquid() || var1.isSolid())
		{
			double var2 = MathHelper.floor_double(posY) + 1 - BlockFluid.getFluidHeightPercent(worldObj.getBlockMetadata(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)));
			if(posY < var2)
			{
				setDead();
			}
		}
	}
}
