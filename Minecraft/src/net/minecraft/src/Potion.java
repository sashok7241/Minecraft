package net.minecraft.src;

public class Potion
{
	public static final Potion[] potionTypes = new Potion[32];
	public static final Potion field_76423_b = null;
	public static final Potion moveSpeed = new Potion(1, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0);
	public static final Potion moveSlowdown = new Potion(2, true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0);
	public static final Potion digSpeed = new Potion(3, false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D);
	public static final Potion digSlowdown = new Potion(4, true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
	public static final Potion damageBoost = new Potion(5, false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0);
	public static final Potion heal = new PotionHealth(6, false, 16262179).setPotionName("potion.heal");
	public static final Potion harm = new PotionHealth(7, true, 4393481).setPotionName("potion.harm");
	public static final Potion jump = new Potion(8, false, 7889559).setPotionName("potion.jump").setIconIndex(2, 1);
	public static final Potion confusion = new Potion(9, true, 5578058).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25D);
	public static final Potion regeneration = new Potion(10, false, 13458603).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25D);
	public static final Potion resistance = new Potion(11, false, 10044730).setPotionName("potion.resistance").setIconIndex(6, 1);
	public static final Potion fireResistance = new Potion(12, false, 14981690).setPotionName("potion.fireResistance").setIconIndex(7, 1);
	public static final Potion waterBreathing = new Potion(13, false, 3035801).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
	public static final Potion invisibility = new Potion(14, false, 8356754).setPotionName("potion.invisibility").setIconIndex(0, 1);
	public static final Potion blindness = new Potion(15, true, 2039587).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25D);
	public static final Potion nightVision = new Potion(16, false, 2039713).setPotionName("potion.nightVision").setIconIndex(4, 1);
	public static final Potion hunger = new Potion(17, true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
	public static final Potion weakness = new Potion(18, true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0);
	public static final Potion poison = new Potion(19, true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25D);
	public static final Potion wither = new Potion(20, true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25D);
	public static final Potion field_76434_w = null;
	public static final Potion field_76444_x = null;
	public static final Potion field_76443_y = null;
	public static final Potion field_76442_z = null;
	public static final Potion field_76409_A = null;
	public static final Potion field_76410_B = null;
	public static final Potion field_76411_C = null;
	public static final Potion field_76405_D = null;
	public static final Potion field_76406_E = null;
	public static final Potion field_76407_F = null;
	public static final Potion field_76408_G = null;
	public final int id;
	private String name = "";
	private int statusIconIndex = -1;
	private final boolean isBadEffect;
	private double effectiveness;
	private boolean usable;
	private final int liquidColor;
	
	protected Potion(int p_i3433_1_, boolean p_i3433_2_, int p_i3433_3_)
	{
		id = p_i3433_1_;
		potionTypes[p_i3433_1_] = this;
		isBadEffect = p_i3433_2_;
		if(p_i3433_2_)
		{
			effectiveness = 0.5D;
		} else
		{
			effectiveness = 1.0D;
		}
		liquidColor = p_i3433_3_;
	}
	
	public void affectEntity(EntityLiving p_76402_1_, EntityLiving p_76402_2_, int p_76402_3_, double p_76402_4_)
	{
		int var6;
		if((id != heal.id || p_76402_2_.isEntityUndead()) && (id != harm.id || !p_76402_2_.isEntityUndead()))
		{
			if(id == harm.id && !p_76402_2_.isEntityUndead() || id == heal.id && p_76402_2_.isEntityUndead())
			{
				var6 = (int) (p_76402_4_ * (6 << p_76402_3_) + 0.5D);
				if(p_76402_1_ == null)
				{
					p_76402_2_.attackEntityFrom(DamageSource.magic, var6);
				} else
				{
					p_76402_2_.attackEntityFrom(DamageSource.causeIndirectMagicDamage(p_76402_2_, p_76402_1_), var6);
				}
			}
		} else
		{
			var6 = (int) (p_76402_4_ * (6 << p_76402_3_) + 0.5D);
			p_76402_2_.heal(var6);
		}
	}
	
	public double getEffectiveness()
	{
		return effectiveness;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getLiquidColor()
	{
		return liquidColor;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getStatusIconIndex()
	{
		return statusIconIndex;
	}
	
	public boolean hasStatusIcon()
	{
		return statusIconIndex >= 0;
	}
	
	public boolean isBadEffect()
	{
		return isBadEffect;
	}
	
	public boolean isInstant()
	{
		return false;
	}
	
	public boolean isReady(int p_76397_1_, int p_76397_2_)
	{
		int var3;
		if(id != regeneration.id && id != poison.id)
		{
			if(id == wither.id)
			{
				var3 = 40 >> p_76397_2_;
				return var3 > 0 ? p_76397_1_ % var3 == 0 : true;
			} else return id == hunger.id;
		} else
		{
			var3 = 25 >> p_76397_2_;
			return var3 > 0 ? p_76397_1_ % var3 == 0 : true;
		}
	}
	
	public boolean isUsable()
	{
		return usable;
	}
	
	public void performEffect(EntityLiving p_76394_1_, int p_76394_2_)
	{
		if(id == regeneration.id)
		{
			if(p_76394_1_.getHealth() < p_76394_1_.getMaxHealth())
			{
				p_76394_1_.heal(1);
			}
		} else if(id == poison.id)
		{
			if(p_76394_1_.getHealth() > 1)
			{
				p_76394_1_.attackEntityFrom(DamageSource.magic, 1);
			}
		} else if(id == wither.id)
		{
			p_76394_1_.attackEntityFrom(DamageSource.wither, 1);
		} else if(id == hunger.id && p_76394_1_ instanceof EntityPlayer)
		{
			((EntityPlayer) p_76394_1_).addExhaustion(0.025F * (p_76394_2_ + 1));
		} else if((id != heal.id || p_76394_1_.isEntityUndead()) && (id != harm.id || !p_76394_1_.isEntityUndead()))
		{
			if(id == harm.id && !p_76394_1_.isEntityUndead() || id == heal.id && p_76394_1_.isEntityUndead())
			{
				p_76394_1_.attackEntityFrom(DamageSource.magic, 6 << p_76394_2_);
			}
		} else
		{
			p_76394_1_.heal(6 << p_76394_2_);
		}
	}
	
	protected Potion setEffectiveness(double p_76404_1_)
	{
		effectiveness = p_76404_1_;
		return this;
	}
	
	protected Potion setIconIndex(int p_76399_1_, int p_76399_2_)
	{
		statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
		return this;
	}
	
	public Potion setPotionName(String p_76390_1_)
	{
		name = p_76390_1_;
		return this;
	}
	
	public static String getDurationString(PotionEffect par0PotionEffect)
	{
		if(par0PotionEffect.getIsPotionDurationMax()) return "**:**";
		else
		{
			int var1 = par0PotionEffect.getDuration();
			return StringUtils.ticksToElapsedTime(var1);
		}
	}
}
