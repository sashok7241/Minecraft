package net.minecraft.src;

public class PotionEffect
{
	private int potionID;
	private int duration;
	private int amplifier;
	private boolean isSplashPotion;
	private boolean isAmbient;
	private boolean isPotionDurationMax;
	
	public PotionEffect(int par1, int par2)
	{
		this(par1, par2, 0);
	}
	
	public PotionEffect(int par1, int par2, int par3)
	{
		this(par1, par2, par3, false);
	}
	
	public PotionEffect(int par1, int par2, int par3, boolean par4)
	{
		potionID = par1;
		duration = par2;
		amplifier = par3;
		isAmbient = par4;
	}
	
	public PotionEffect(PotionEffect par1PotionEffect)
	{
		potionID = par1PotionEffect.potionID;
		duration = par1PotionEffect.duration;
		amplifier = par1PotionEffect.amplifier;
	}
	
	public void combine(PotionEffect par1PotionEffect)
	{
		if(potionID != par1PotionEffect.potionID)
		{
			System.err.println("This method should only be called for matching effects!");
		}
		if(par1PotionEffect.amplifier > amplifier)
		{
			amplifier = par1PotionEffect.amplifier;
			duration = par1PotionEffect.duration;
		} else if(par1PotionEffect.amplifier == amplifier && duration < par1PotionEffect.duration)
		{
			duration = par1PotionEffect.duration;
		} else if(!par1PotionEffect.isAmbient && isAmbient)
		{
			isAmbient = par1PotionEffect.isAmbient;
		}
	}
	
	private int deincrementDuration()
	{
		return --duration;
	}
	
	@Override public boolean equals(Object par1Obj)
	{
		if(!(par1Obj instanceof PotionEffect)) return false;
		else
		{
			PotionEffect var2 = (PotionEffect) par1Obj;
			return potionID == var2.potionID && amplifier == var2.amplifier && duration == var2.duration && isSplashPotion == var2.isSplashPotion && isAmbient == var2.isAmbient;
		}
	}
	
	public int getAmplifier()
	{
		return amplifier;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public String getEffectName()
	{
		return Potion.potionTypes[potionID].getName();
	}
	
	public boolean getIsAmbient()
	{
		return isAmbient;
	}
	
	public boolean getIsPotionDurationMax()
	{
		return isPotionDurationMax;
	}
	
	public int getPotionID()
	{
		return potionID;
	}
	
	@Override public int hashCode()
	{
		return potionID;
	}
	
	public boolean onUpdate(EntityLivingBase par1EntityLivingBase)
	{
		if(duration > 0)
		{
			if(Potion.potionTypes[potionID].isReady(duration, amplifier))
			{
				performEffect(par1EntityLivingBase);
			}
			deincrementDuration();
		}
		return duration > 0;
	}
	
	public void performEffect(EntityLivingBase par1EntityLivingBase)
	{
		if(duration > 0)
		{
			Potion.potionTypes[potionID].performEffect(par1EntityLivingBase, amplifier);
		}
	}
	
	public void setPotionDurationMax(boolean par1)
	{
		isPotionDurationMax = par1;
	}
	
	public void setSplashPotion(boolean par1)
	{
		isSplashPotion = par1;
	}
	
	@Override public String toString()
	{
		String var1 = "";
		if(getAmplifier() > 0)
		{
			var1 = getEffectName() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
		} else
		{
			var1 = getEffectName() + ", Duration: " + getDuration();
		}
		if(isSplashPotion)
		{
			var1 = var1 + ", Splash: true";
		}
		return Potion.potionTypes[potionID].isUsable() ? "(" + var1 + ")" : var1;
	}
	
	public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("Id", (byte) getPotionID());
		par1NBTTagCompound.setByte("Amplifier", (byte) getAmplifier());
		par1NBTTagCompound.setInteger("Duration", getDuration());
		par1NBTTagCompound.setBoolean("Ambient", getIsAmbient());
		return par1NBTTagCompound;
	}
	
	public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound par0NBTTagCompound)
	{
		byte var1 = par0NBTTagCompound.getByte("Id");
		byte var2 = par0NBTTagCompound.getByte("Amplifier");
		int var3 = par0NBTTagCompound.getInteger("Duration");
		boolean var4 = par0NBTTagCompound.getBoolean("Ambient");
		return new PotionEffect(var1, var3, var2, var4);
	}
}
