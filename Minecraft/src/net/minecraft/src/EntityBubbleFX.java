package net.minecraft.src;

public class EntityBubbleFX extends EntityFX
{
	public EntityBubbleFX(World p_i3162_1_, double p_i3162_2_, double p_i3162_4_, double p_i3162_6_, double p_i3162_8_, double p_i3162_10_, double p_i3162_12_)
	{
		super(p_i3162_1_, p_i3162_2_, p_i3162_4_, p_i3162_6_, p_i3162_8_, p_i3162_10_, p_i3162_12_);
		particleRed = 1.0F;
		particleGreen = 1.0F;
		particleBlue = 1.0F;
		setParticleTextureIndex(32);
		setSize(0.02F, 0.02F);
		particleScale *= rand.nextFloat() * 0.6F + 0.2F;
		motionX = p_i3162_8_ * 0.20000000298023224D + (float) (Math.random() * 2.0D - 1.0D) * 0.02F;
		motionY = p_i3162_10_ * 0.20000000298023224D + (float) (Math.random() * 2.0D - 1.0D) * 0.02F;
		motionZ = p_i3162_12_ * 0.20000000298023224D + (float) (Math.random() * 2.0D - 1.0D) * 0.02F;
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
