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
	
	protected Potion(int par1, boolean par2, int par3)
	{
		id = par1;
		potionTypes[par1] = this;
		isBadEffect = par2;
		if(par2)
		{
			effectiveness = 0.5D;
		} else
		{
			effectiveness = 1.0D;
		}
		liquidColor = par3;
	}
	
	public void affectEntity(EntityLiving par1EntityLiving, EntityLiving par2EntityLiving, int par3, double par4)
	{
		int var6;
		if((id != heal.id || par2EntityLiving.isEntityUndead()) && (id != harm.id || !par2EntityLiving.isEntityUndead()))
		{
			if(id == harm.id && !par2EntityLiving.isEntityUndead() || id == heal.id && par2EntityLiving.isEntityUndead())
			{
				var6 = (int) (par4 * (6 << par3) + 0.5D);
				if(par1EntityLiving == null)
				{
					par2EntityLiving.attackEntityFrom(DamageSource.magic, var6);
				} else
				{
					par2EntityLiving.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par2EntityLiving, par1EntityLiving), var6);
				}
			}
		} else
		{
			var6 = (int) (par4 * (6 << par3) + 0.5D);
			par2EntityLiving.heal(var6);
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
	
	public boolean isReady(int par1, int par2)
	{
		int var3;
		if(id != regeneration.id && id != poison.id)
		{
			if(id == wither.id)
			{
				var3 = 40 >> par2;
				return var3 > 0 ? par1 % var3 == 0 : true;
			} else return id == hunger.id;
		} else
		{
			var3 = 25 >> par2;
			return var3 > 0 ? par1 % var3 == 0 : true;
		}
	}
	
	public boolean isUsable()
	{
		return usable;
	}
	
	public void performEffect(EntityLiving par1EntityLiving, int par2)
	{
		if(id == regeneration.id)
		{
			if(par1EntityLiving.getHealth() < par1EntityLiving.getMaxHealth())
			{
				par1EntityLiving.heal(1);
			}
		} else if(id == poison.id)
		{
			if(par1EntityLiving.getHealth() > 1)
			{
				par1EntityLiving.attackEntityFrom(DamageSource.magic, 1);
			}
		} else if(id == wither.id)
		{
			par1EntityLiving.attackEntityFrom(DamageSource.wither, 1);
		} else if(id == hunger.id && par1EntityLiving instanceof EntityPlayer)
		{
			((EntityPlayer) par1EntityLiving).addExhaustion(0.025F * (par2 + 1));
		} else if((id != heal.id || par1EntityLiving.isEntityUndead()) && (id != harm.id || !par1EntityLiving.isEntityUndead()))
		{
			if(id == harm.id && !par1EntityLiving.isEntityUndead() || id == heal.id && par1EntityLiving.isEntityUndead())
			{
				par1EntityLiving.attackEntityFrom(DamageSource.magic, 6 << par2);
			}
		} else
		{
			par1EntityLiving.heal(6 << par2);
		}
	}
	
	protected Potion setEffectiveness(double par1)
	{
		effectiveness = par1;
		return this;
	}
	
	protected Potion setIconIndex(int par1, int par2)
	{
		statusIconIndex = par1 + par2 * 8;
		return this;
	}
	
	public Potion setPotionName(String par1Str)
	{
		name = par1Str;
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
