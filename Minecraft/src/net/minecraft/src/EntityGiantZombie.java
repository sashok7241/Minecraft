package net.minecraft.src;

public class EntityGiantZombie extends EntityMob
{
	public EntityGiantZombie(World par1World)
	{
		super(par1World);
		texture = "/mob/zombie.png";
		moveSpeed = 0.5F;
		yOffset *= 6.0F;
		setSize(width * 6.0F, height * 6.0F);
	}
	
	@Override public int getAttackStrength(Entity par1Entity)
	{
		return 50;
	}
	
	@Override public float getBlockPathWeight(int par1, int par2, int par3)
	{
		return worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
	}
	
	@Override public int getMaxHealth()
	{
		return 100;
	}
}
