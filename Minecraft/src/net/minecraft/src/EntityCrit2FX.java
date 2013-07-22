package net.minecraft.src;

public class EntityCrit2FX extends EntityFX
{
	private Entity theEntity;
	private int currentLife;
	private int maximumLife;
	private String particleName;
	
	public EntityCrit2FX(World par1World, Entity par2Entity)
	{
		this(par1World, par2Entity, "crit");
	}
	
	public EntityCrit2FX(World par1World, Entity par2Entity, String par3Str)
	{
		super(par1World, par2Entity.posX, par2Entity.boundingBox.minY + par2Entity.height / 2.0F, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
		currentLife = 0;
		maximumLife = 0;
		theEntity = par2Entity;
		maximumLife = 3;
		particleName = par3Str;
		onUpdate();
	}
	
	@Override public int getFXLayer()
	{
		return 3;
	}
	
	@Override public void onUpdate()
	{
		for(int var1 = 0; var1 < 16; ++var1)
		{
			double var2 = rand.nextFloat() * 2.0F - 1.0F;
			double var4 = rand.nextFloat() * 2.0F - 1.0F;
			double var6 = rand.nextFloat() * 2.0F - 1.0F;
			if(var2 * var2 + var4 * var4 + var6 * var6 <= 1.0D)
			{
				double var8 = theEntity.posX + var2 * theEntity.width / 4.0D;
				double var10 = theEntity.boundingBox.minY + theEntity.height / 2.0F + var4 * theEntity.height / 4.0D;
				double var12 = theEntity.posZ + var6 * theEntity.width / 4.0D;
				worldObj.spawnParticle(particleName, var8, var10, var12, var2, var4 + 0.2D, var6);
			}
		}
		++currentLife;
		if(currentLife >= maximumLife)
		{
			setDead();
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
	}
}
