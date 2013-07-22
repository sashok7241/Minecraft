package net.minecraft.src;

public class EntityEnchantmentTableParticleFX extends EntityFX
{
	private float field_70565_a;
	private double field_70568_aq;
	private double field_70567_ar;
	private double field_70566_as;
	
	public EntityEnchantmentTableParticleFX(World p_i3155_1_, double p_i3155_2_, double p_i3155_4_, double p_i3155_6_, double p_i3155_8_, double p_i3155_10_, double p_i3155_12_)
	{
		super(p_i3155_1_, p_i3155_2_, p_i3155_4_, p_i3155_6_, p_i3155_8_, p_i3155_10_, p_i3155_12_);
		motionX = p_i3155_8_;
		motionY = p_i3155_10_;
		motionZ = p_i3155_12_;
		field_70568_aq = posX = p_i3155_2_;
		field_70567_ar = posY = p_i3155_4_;
		field_70566_as = posZ = p_i3155_6_;
		float var14 = rand.nextFloat() * 0.6F + 0.4F;
		field_70565_a = particleScale = rand.nextFloat() * 0.5F + 0.2F;
		particleRed = particleGreen = particleBlue = 1.0F * var14;
		particleGreen *= 0.9F;
		particleRed *= 0.9F;
		particleMaxAge = (int) (Math.random() * 10.0D) + 30;
		noClip = true;
		setParticleTextureIndex((int) (Math.random() * 26.0D + 1.0D + 224.0D));
	}
	
	@Override public float getBrightness(float p_70013_1_)
	{
		float var2 = super.getBrightness(p_70013_1_);
		float var3 = (float) particleAge / (float) particleMaxAge;
		var3 *= var3;
		var3 *= var3;
		return var2 * (1.0F - var3) + var3;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		int var2 = super.getBrightnessForRender(par1);
		float var3 = (float) particleAge / (float) particleMaxAge;
		var3 *= var3;
		var3 *= var3;
		int var4 = var2 & 255;
		int var5 = var2 >> 16 & 255;
		var5 += (int) (var3 * 15.0F * 16.0F);
		if(var5 > 240)
		{
			var5 = 240;
		}
		return var4 | var5 << 16;
	}
	
	@Override public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		float var1 = (float) particleAge / (float) particleMaxAge;
		var1 = 1.0F - var1;
		float var2 = 1.0F - var1;
		var2 *= var2;
		var2 *= var2;
		posX = field_70568_aq + motionX * var1;
		posY = field_70567_ar + motionY * var1 - var2 * 1.2F;
		posZ = field_70566_as + motionZ * var1;
		if(particleAge++ >= particleMaxAge)
		{
			setDead();
		}
	}
}
