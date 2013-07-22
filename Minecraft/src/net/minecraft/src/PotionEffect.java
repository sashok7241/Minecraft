package net.minecraft.src;

public class PotionEffect
{
	private int potionID;
	private int duration;
	private int amplifier;
	private boolean isSplashPotion;
	private boolean isAmbient;
	private boolean isPotionDurationMax;
	
	public PotionEffect(int p_i5051_1_, int p_i5051_2_)
	{
		this(p_i5051_1_, p_i5051_2_, 0);
	}
	
	public PotionEffect(int p_i3434_1_, int p_i3434_2_, int p_i3434_3_)
	{
		this(p_i3434_1_, p_i3434_2_, p_i3434_3_, false);
	}
	
	public PotionEffect(int p_i5052_1_, int p_i5052_2_, int p_i5052_3_, boolean p_i5052_4_)
	{
		potionID = p_i5052_1_;
		duration = p_i5052_2_;
		amplifier = p_i5052_3_;
		isAmbient = p_i5052_4_;
	}
	
	public PotionEffect(PotionEffect p_i3435_1_)
	{
		potionID = p_i3435_1_.potionID;
		duration = p_i3435_1_.duration;
		amplifier = p_i3435_1_.amplifier;
	}
	
	public void combine(PotionEffect p_76452_1_)
	{
		if(potionID != p_76452_1_.potionID)
		{
			System.err.println("This method should only be called for matching effects!");
		}
		if(p_76452_1_.amplifier > amplifier)
		{
			amplifier = p_76452_1_.amplifier;
			duration = p_76452_1_.duration;
		} else if(p_76452_1_.amplifier == amplifier && duration < p_76452_1_.duration)
		{
			duration = p_76452_1_.duration;
		} else if(!p_76452_1_.isAmbient && isAmbient)
		{
			isAmbient = p_76452_1_.isAmbient;
		}
	}
	
	private int deincrementDuration()
	{
		return --duration;
	}
	
	@Override public boolean equals(Object p_equals_1_)
	{
		if(!(p_equals_1_ instanceof PotionEffect)) return false;
		else
		{
			PotionEffect var2 = (PotionEffect) p_equals_1_;
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
	
	public boolean isSplashPotionEffect()
	{
		return isSplashPotion;
	}
	
	public boolean onUpdate(EntityLiving p_76455_1_)
	{
		if(duration > 0)
		{
			if(Potion.potionTypes[potionID].isReady(duration, amplifier))
			{
				performEffect(p_76455_1_);
			}
			deincrementDuration();
		}
		return duration > 0;
	}
	
	public void performEffect(EntityLiving p_76457_1_)
	{
		if(duration > 0)
		{
			Potion.potionTypes[potionID].performEffect(p_76457_1_, amplifier);
		}
	}
	
	public void setPotionDurationMax(boolean par1)
	{
		isPotionDurationMax = par1;
	}
	
	public void setSplashPotion(boolean p_82721_1_)
	{
		isSplashPotion = p_82721_1_;
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
	
	public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound p_82719_1_)
	{
		p_82719_1_.setByte("Id", (byte) getPotionID());
		p_82719_1_.setByte("Amplifier", (byte) getAmplifier());
		p_82719_1_.setInteger("Duration", getDuration());
		p_82719_1_.setBoolean("Ambient", getIsAmbient());
		return p_82719_1_;
	}
	
	public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound p_82722_0_)
	{
		byte var1 = p_82722_0_.getByte("Id");
		byte var2 = p_82722_0_.getByte("Amplifier");
		int var3 = p_82722_0_.getInteger("Duration");
		boolean var4 = p_82722_0_.getBoolean("Ambient");
		return new PotionEffect(var1, var3, var2, var4);
	}
}
