package net.minecraft.src;

public class EntitySuspendFX extends EntityFX
{
	public EntitySuspendFX(World p_i3167_1_, double p_i3167_2_, double p_i3167_4_, double p_i3167_6_, double p_i3167_8_, double p_i3167_10_, double p_i3167_12_)
	{
		super(p_i3167_1_, p_i3167_2_, p_i3167_4_ - 0.125D, p_i3167_6_, p_i3167_8_, p_i3167_10_, p_i3167_12_);
		particleRed = 0.4F;
		particleGreen = 0.4F;
		particleBlue = 0.7F;
		setParticleTextureIndex(0);
		setSize(0.01F, 0.01F);
		particleScale *= rand.nextFloat() * 0.6F + 0.2F;
		motionX = p_i3167_8_ * 0.0D;
		motionY = p_i3167_10_ * 0.0D;
		motionZ = p_i3167_12_ * 0.0D;
		particleMaxAge = (int) (16.0D / (Math.random() * 0.8D + 0.2D));
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		moveEntity(motionX, motionY, motionZ);
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
