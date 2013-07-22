package net.minecraft.src;

public class FoodStats
{
	private int foodLevel = 20;
	private float foodSaturationLevel = 5.0F;
	private float foodExhaustionLevel;
	private int foodTimer = 0;
	private int prevFoodLevel = 20;
	
	public void addExhaustion(float par1)
	{
		foodExhaustionLevel = Math.min(foodExhaustionLevel + par1, 40.0F);
	}
	
	public void addStats(int par1, float par2)
	{
		foodLevel = Math.min(par1 + foodLevel, 20);
		foodSaturationLevel = Math.min(foodSaturationLevel + par1 * par2 * 2.0F, foodLevel);
	}
	
	public void addStats(ItemFood par1ItemFood)
	{
		this.addStats(par1ItemFood.getHealAmount(), par1ItemFood.getSaturationModifier());
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
	
	public void onUpdate(EntityPlayer par1EntityPlayer)
	{
		int var2 = par1EntityPlayer.worldObj.difficultySetting;
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
		if(foodLevel >= 18 && par1EntityPlayer.shouldHeal())
		{
			++foodTimer;
			if(foodTimer >= 80)
			{
				par1EntityPlayer.heal(1);
				foodTimer = 0;
			}
		} else if(foodLevel <= 0)
		{
			++foodTimer;
			if(foodTimer >= 80)
			{
				if(par1EntityPlayer.getHealth() > 10 || var2 >= 3 || par1EntityPlayer.getHealth() > 1 && var2 >= 2)
				{
					par1EntityPlayer.attackEntityFrom(DamageSource.starve, 1);
				}
				foodTimer = 0;
			}
		} else
		{
			foodTimer = 0;
		}
	}
	
	public void readNBT(NBTTagCompound par1NBTTagCompound)
	{
		if(par1NBTTagCompound.hasKey("foodLevel"))
		{
			foodLevel = par1NBTTagCompound.getInteger("foodLevel");
			foodTimer = par1NBTTagCompound.getInteger("foodTickTimer");
			foodSaturationLevel = par1NBTTagCompound.getFloat("foodSaturationLevel");
			foodExhaustionLevel = par1NBTTagCompound.getFloat("foodExhaustionLevel");
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
	
	public void writeNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setInteger("foodLevel", foodLevel);
		par1NBTTagCompound.setInteger("foodTickTimer", foodTimer);
		par1NBTTagCompound.setFloat("foodSaturationLevel", foodSaturationLevel);
		par1NBTTagCompound.setFloat("foodExhaustionLevel", foodExhaustionLevel);
	}
}
