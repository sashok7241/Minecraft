package net.minecraft.src;

public class FoodStats
{
	private int foodLevel = 20;
	private float foodSaturationLevel = 5.0F;
	private float foodExhaustionLevel;
	private int foodTimer = 0;
	private int prevFoodLevel = 20;
	
	public void addExhaustion(float p_75113_1_)
	{
		foodExhaustionLevel = Math.min(foodExhaustionLevel + p_75113_1_, 40.0F);
	}
	
	public void addStats(int p_75122_1_, float p_75122_2_)
	{
		foodLevel = Math.min(p_75122_1_ + foodLevel, 20);
		foodSaturationLevel = Math.min(foodSaturationLevel + p_75122_1_ * p_75122_2_ * 2.0F, foodLevel);
	}
	
	public void addStats(ItemFood p_75111_1_)
	{
		this.addStats(p_75111_1_.getHealAmount(), p_75111_1_.getSaturationModifier());
	}
	
	public int getFoodLevel()
	{
		return foodLevel;
	}
	
	public int getPrevFoodLevel()
	{
		return prevFoodLevel;
	}
	
	public float getSaturationLevel()
	{
		return foodSaturationLevel;
	}
	
	public boolean needFood()
	{
		return foodLevel < 20;
	}
	
	public void onUpdate(EntityPlayer p_75118_1_)
	{
		int var2 = p_75118_1_.worldObj.difficultySetting;
		prevFoodLevel = foodLevel;
		if(foodExhaustionLevel > 4.0F)
		{
			foodExhaustionLevel -= 4.0F;
			if(foodSaturationLevel > 0.0F)
			{
				foodSaturationLevel = Math.max(foodSaturationLevel - 1.0F, 0.0F);
			} else if(var2 > 0)
			{
				foodLevel = Math.max(foodLevel - 1, 0);
			}
		}
		if(foodLevel >= 18 && p_75118_1_.shouldHeal())
		{
			++foodTimer;
			if(foodTimer >= 80)
			{
				p_75118_1_.heal(1);
				foodTimer = 0;
			}
		} else if(foodLevel <= 0)
		{
			++foodTimer;
			if(foodTimer >= 80)
			{
				if(p_75118_1_.getHealth() > 10 || var2 >= 3 || p_75118_1_.getHealth() > 1 && var2 >= 2)
				{
					p_75118_1_.attackEntityFrom(DamageSource.starve, 1);
				}
				foodTimer = 0;
			}
		} else
		{
			foodTimer = 0;
		}
	}
	
	public void readNBT(NBTTagCompound p_75112_1_)
	{
		if(p_75112_1_.hasKey("foodLevel"))
		{
			foodLevel = p_75112_1_.getInteger("foodLevel");
			foodTimer = p_75112_1_.getInteger("foodTickTimer");
			foodSaturationLevel = p_75112_1_.getFloat("foodSaturationLevel");
			foodExhaustionLevel = p_75112_1_.getFloat("foodExhaustionLevel");
		}
	}
	
	public void setFoodLevel(int par1)
	{
		foodLevel = par1;
	}
	
	public void setFoodSaturationLevel(float par1)
	{
		foodSaturationLevel = par1;
	}
	
	public void writeNBT(NBTTagCompound p_75117_1_)
	{
		p_75117_1_.setInteger("foodLevel", foodLevel);
		p_75117_1_.setInteger("foodTickTimer", foodTimer);
		p_75117_1_.setFloat("foodSaturationLevel", foodSaturationLevel);
		p_75117_1_.setFloat("foodExhaustionLevel", foodExhaustionLevel);
	}
}
