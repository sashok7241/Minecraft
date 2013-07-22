package net.minecraft.src;

public class EntityHugeExplodeFX extends EntityFX
{
	private int timeSinceStart = 0;
	private int maximumTime = 0;
	
	public EntityHugeExplodeFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
	{
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
		maximumTime = 8;
	}
	
	@Override public int getFXLayer()
	{
		return 1;
	}
	
	@Override public void onUpdate()
	{
		for(int var1 = 0; var1 < 6; ++var1)
		{
			double var2 = posX + (rand.nextDouble() - rand.nextDouble()) * 4.0D;
			double var4 = posY + (rand.nextDouble() - rand.nextDouble()) * 4.0D;
			double var6 = posZ + (rand.nextDouble() - rand.nextDouble()) * 4.0D;
			worldObj.spawnParticle("largeexplode", var2, var4, var6, (float) timeSinceStart / (float) maximumTime, 0.0D, 0.0D);
		}
		++timeSinceStart;
		if(timeSinceStart == maximumTime)
		{
			setDead();
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
	}
}
