package net.minecraft.src;

public class EntityGiantZombie extends EntityMob
{
	public EntityGiantZombie(World p_i3550_1_)
	{
		super(p_i3550_1_);
		texture = "/mob/zombie.png";
		moveSpeed = 0.5F;
		yOffset *= 6.0F;
		setSize(width * 6.0F, height * 6.0F);
	}
	
	@Override public int getAttackStrength(Entity p_82193_1_)
	{
		return 50;
	}
	
	@Override public float getBlockPathWeight(int p_70783_1_, int p_70783_2_, int p_70783_3_)
	{
		return worldObj.getLightBrightness(p_70783_1_, p_70783_2_, p_70783_3_) - 0.5F;
	}
	
	@Override public int getMaxHealth()
	{
		return 100;
	}
}
