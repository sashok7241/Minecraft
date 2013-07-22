package net.minecraft.src;

public class EntityFX extends Entity
{
	protected int particleTextureIndexX;
	protected int particleTextureIndexY;
	protected float particleTextureJitterX;
	protected float particleTextureJitterY;
	protected int particleAge;
	protected int particleMaxAge;
	protected float particleScale;
	protected float particleGravity;
	protected float particleRed;
	protected float particleGreen;
	protected float particleBlue;
	protected float particleAlpha;
	protected Icon particleIcon;
	public static double interpPosX;
	public static double interpPosY;
	public static double interpPosZ;
	
	protected EntityFX(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		particleAge = 0;
		particleMaxAge = 0;
		particleAlpha = 1.0F;
		particleIcon = null;
		setSize(0.2F, 0.2F);
		yOffset = height / 2.0F;
		setPosition(par2, par4, par6);
		lastTickPosX = par2;
		lastTickPosY = par4;
		lastTickPosZ = par6;
		particleRed = particleGreen = particleBlue = 1.0F;
		particleTextureJitterX = rand.nextFloat() * 3.0F;
		particleTextureJitterY = rand.nextFloat() * 3.0F;
		particleScale = (rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
		particleMaxAge = (int) (4.0F / (rand.nextFloat() * 0.9F + 0.1F));
		particleAge = 0;
	}
	
	public EntityFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		this(par1World, par2, par4, par6);
		motionX = par8 + (float) (Math.random() * 2.0D - 1.0D) * 0.4F;
		motionY = par10 + (float) (Math.random() * 2.0D - 1.0D) * 0.4F;
		motionZ = par12 + (float) (Math.random() * 2.0D - 1.0D) * 0.4F;
		float var14 = (float) (Math.random() + Math.random() + 1.0D) * 0.15F;
		float var15 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
		motionX = motionX / var15 * var14 * 0.4000000059604645D;
		motionY = motionY / var15 * var14 * 0.4000000059604645D + 0.10000000149011612D;
		motionZ = motionZ / var15 * var14 * 0.4000000059604645D;
	}
	
	@Override public boolean canAttackWithItem()
	{
		return false;
	}
	
	@Override protected boolean canTriggerWalking()
	{
		return false;
	}
	
	@Override protected void entityInit()
	{
	}
	
	public float getBlueColorF()
	{
		return particleBlue;
	}
	
	public int getFXLayer()
	{
		return 0;
	}
	
	public float getGreenColorF()
	{
		return particleGreen;
	}
	
	public float getRedColorF()
	{
		return particleRed;
	}
	
	public EntityFX multipleParticleScaleBy(float par1)
	{
		setSize(0.2F * par1, 0.2F * par1);
		particleScale *= par1;
		return this;
	}
	
	public EntityFX multiplyVelocity(float par1)
	{
		motionX *= par1;
		motionY = (motionY - 0.10000000149011612D) * par1 + 0.10000000149011612D;
		motionZ *= par1;
		return this;
	}
	
	public void nextTextureIndexX()
	{
		++particleTextureIndexX;
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
		motionY -= 0.04D * particleGravity;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;
		if(onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
	
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = particleTextureIndexX / 16.0F;
		float var9 = var8 + 0.0624375F;
		float var10 = particleTextureIndexY / 16.0F;
		float var11 = var10 + 0.0624375F;
		float var12 = 0.1F * particleScale;
		if(particleIcon != null)
		{
			var8 = particleIcon.getMinU();
			var9 = particleIcon.getMaxU();
			var10 = particleIcon.getMinV();
			var11 = particleIcon.getMaxV();
		}
		float var13 = (float) (prevPosX + (posX - prevPosX) * par2 - interpPosX);
		float var14 = (float) (prevPosY + (posY - prevPosY) * par2 - interpPosY);
		float var15 = (float) (prevPosZ + (posZ - prevPosZ) * par2 - interpPosZ);
		float var16 = 1.0F;
		par1Tessellator.setColorRGBA_F(particleRed * var16, particleGreen * var16, particleBlue * var16, particleAlpha);
		par1Tessellator.addVertexWithUV(var13 - par3 * var12 - par6 * var12, var14 - par4 * var12, var15 - par5 * var12 - par7 * var12, var9, var11);
		par1Tessellator.addVertexWithUV(var13 - par3 * var12 + par6 * var12, var14 + par4 * var12, var15 - par5 * var12 + par7 * var12, var9, var10);
		par1Tessellator.addVertexWithUV(var13 + par3 * var12 + par6 * var12, var14 + par4 * var12, var15 + par5 * var12 + par7 * var12, var8, var10);
		par1Tessellator.addVertexWithUV(var13 + par3 * var12 - par6 * var12, var14 - par4 * var12, var15 + par5 * var12 - par7 * var12, var8, var11);
	}
	
	public void setAlphaF(float par1)
	{
		particleAlpha = par1;
	}
	
	public void setParticleIcon(RenderEngine par1RenderEngine, Icon par2Icon)
	{
		if(getFXLayer() == 1)
		{
			particleIcon = par2Icon;
		} else
		{
			if(getFXLayer() != 2) throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
			particleIcon = par2Icon;
		}
	}
	
	public void setParticleTextureIndex(int par1)
	{
		if(getFXLayer() != 0) throw new RuntimeException("Invalid call to Particle.setMiscTex");
		else
		{
			particleTextureIndexX = par1 % 16;
			particleTextureIndexY = par1 / 16;
		}
	}
	
	public void setRBGColorF(float par1, float par2, float par3)
	{
		particleRed = par1;
		particleGreen = par2;
		particleBlue = par3;
	}
	
	@Override public String toString()
	{
		return this.getClass().getSimpleName() + ", Pos (" + posX + "," + posY + "," + posZ + "), RGBA (" + particleRed + "," + particleGreen + "," + particleBlue + "," + particleAlpha + "), Age " + particleAge;
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
	}
}
