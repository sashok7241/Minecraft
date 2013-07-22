package net.minecraft.src;

public class EntityMagmaCube extends EntitySlime
{
	public EntityMagmaCube(World p_i3551_1_)
	{
		super(p_i3551_1_);
		texture = "/mob/lava.png";
		isImmuneToFire = true;
		landMovementFactor = 0.2F;
	}
	
	@Override protected boolean canDamagePlayer()
	{
		return true;
	}
	
	@Override protected EntitySlime createInstance()
	{
		return new EntityMagmaCube(worldObj);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = getDropItemId();
		if(var3 > 0 && getSlimeSize() > 1)
		{
			int var4 = rand.nextInt(4) - 2;
			if(p_70628_2_ > 0)
			{
				var4 += rand.nextInt(p_70628_2_ + 1);
			}
			for(int var5 = 0; var5 < var4; ++var5)
			{
				dropItem(var3, 1);
			}
		}
	}
	
	@Override protected void fall(float p_70069_1_)
	{
	}
	
	@Override protected void func_70808_l()
	{
		field_70813_a *= 0.9F;
	}
	
	@Override protected int getAttackStrength()
	{
		return super.getAttackStrength() + 2;
	}
	
	@Override public float getBrightness(float p_70013_1_)
	{
		return 1.0F;
	}
	
	@Override public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}
	
	@Override public boolean getCanSpawnHere()
	{
		return worldObj.difficultySetting > 0 && worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() && !worldObj.isAnyLiquid(boundingBox);
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}
	
	@Override protected int getDropItemId()
	{
		return Item.magmaCream.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
	}
	
	@Override protected int getJumpDelay()
	{
		return super.getJumpDelay() * 4;
	}
	
	@Override protected String getJumpSound()
	{
		return getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
	}
	
	@Override protected String getSlimeParticle()
	{
		return "flame";
	}
	
	@Override public int getTotalArmorValue()
	{
		return getSlimeSize() * 3;
	}
	
	@Override public boolean handleLavaMovement()
	{
		return false;
	}
	
	@Override public boolean isBurning()
	{
		return false;
	}
	
	@Override protected void jump()
	{
		motionY = 0.42F + getSlimeSize() * 0.1F;
		isAirBorne = true;
	}
	
	@Override protected boolean makesSoundOnLand()
	{
		return true;
	}
}
