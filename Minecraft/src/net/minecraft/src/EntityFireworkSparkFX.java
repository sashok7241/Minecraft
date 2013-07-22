package net.minecraft.src;

public class EntityFireworkSparkFX extends EntityFX
{
	private int field_92049_a = 160;
	private boolean field_92054_ax;
	private boolean field_92048_ay;
	private final EffectRenderer field_92047_az;
	private float field_92050_aA;
	private float field_92051_aB;
	private float field_92052_aC;
	private boolean field_92053_aD;
	
	public EntityFireworkSparkFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, EffectRenderer par14EffectRenderer)
	{
		super(par1World, par2, par4, par6);
		motionX = par8;
		motionY = par10;
		motionZ = par12;
		field_92047_az = par14EffectRenderer;
		particleScale *= 0.75F;
		particleMaxAge = 48 + rand.nextInt(12);
		noClip = false;
	}
	
	@Override public boolean canBePushed()
	{
		return false;
	}
	
	public void func_92043_f(boolean par1)
	{
		field_92048_ay = par1;
	}
	
	public void func_92044_a(int par1)
	{
		float var2 = ((par1 & 16711680) >> 16) / 255.0F;
		float var3 = ((par1 & 65280) >> 8) / 255.0F;
		float var4 = ((par1 & 255) >> 0) / 255.0F;
		float var5 = 1.0F;
		setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
	}
	
	public void func_92045_e(boolean par1)
	{
		field_92054_ax = par1;
	}
	
	public void func_92046_g(int par1)
	{
		field_92050_aA = ((par1 & 16711680) >> 16) / 255.0F;
		field_92051_aB = ((par1 & 65280) >> 8) / 255.0F;
		field_92052_aC = ((par1 & 255) >> 0) / 255.0F;
		field_92053_aD = true;
	}
	
	@Override public AxisAlignedBB getBoundingBox()
	{
		return null;
	}
	
	@Override public float getBrightness(float par1)
	{
		return 1.0F;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return 15728880;
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
		if(particleAge > particleMaxAge / 2)
		{
			setAlphaF(1.0F - ((float) particleAge - (float) (particleMaxAge / 2)) / particleMaxAge);
			if(field_92053_aD)
			{
				particleRed += (field_92050_aA - particleRed) * 0.2F;
				particleGreen += (field_92051_aB - particleGreen) * 0.2F;
				particleBlue += (field_92052_aC - particleBlue) * 0.2F;
			}
		}
		setParticleTextureIndex(field_92049_a + 7 - particleAge * 8 / particleMaxAge);
		motionY -= 0.004D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9100000262260437D;
		motionY *= 0.9100000262260437D;
		motionZ *= 0.9100000262260437D;
		if(onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
		if(field_92054_ax && particleAge < particleMaxAge / 2 && (particleAge + particleMaxAge) % 2 == 0)
		{
			EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(worldObj, posX, posY, posZ, 0.0D, 0.0D, 0.0D, field_92047_az);
			var1.setRBGColorF(particleRed, particleGreen, particleBlue);
			var1.particleAge = var1.particleMaxAge / 2;
			if(field_92053_aD)
			{
				var1.field_92053_aD = true;
				var1.field_92050_aA = field_92050_aA;
				var1.field_92051_aB = field_92051_aB;
				var1.field_92052_aC = field_92052_aC;
			}
			var1.field_92048_ay = field_92048_ay;
			field_92047_az.addEffect(var1);
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		if(!field_92048_ay || particleAge < particleMaxAge / 3 || (particleAge + particleMaxAge) / 3 % 2 == 0)
		{
			super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
		}
	}
}
