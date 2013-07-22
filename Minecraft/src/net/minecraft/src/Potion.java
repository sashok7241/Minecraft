package net.minecraft.src;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class Potion
{
	public static final Potion[] potionTypes = new Potion[32];
	public static final Potion field_76423_b = null;
	public static final Potion moveSpeed = new Potion(1, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0).func_111184_a(SharedMonsterAttributes.field_111263_d, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
	public static final Potion moveSlowdown = new Potion(2, true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).func_111184_a(SharedMonsterAttributes.field_111263_d, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
	public static final Potion digSpeed = new Potion(3, false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5D);
	public static final Potion digSlowdown = new Potion(4, true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
	public static final Potion damageBoost = new PotionAttackDamage(5, false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0).func_111184_a(SharedMonsterAttributes.field_111264_e, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0D, 2);
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
	public static final Potion weakness = new PotionAttackDamage(18, true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0).func_111184_a(SharedMonsterAttributes.field_111264_e, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
	public static final Potion poison = new Potion(19, true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25D);
	public static final Potion wither = new Potion(20, true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25D);
	public static final Potion field_76434_w = new PotionHealthBoost(21, false, 16284963).setPotionName("potion.healthBoost").setIconIndex(2, 2).func_111184_a(SharedMonsterAttributes.field_111267_a, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
	public static final Potion field_76444_x = new PotionAbsoption(22, false, 2445989).setPotionName("potion.absorption").setIconIndex(2, 2);
	public static final Potion field_76443_y = new PotionHealth(23, false, 16262179).setPotionName("potion.saturation");
	public static final Potion field_76442_z = null;
	public static final Potion field_76409_A = null;
	public static final Potion field_76410_B = null;
	public static final Potion field_76411_C = null;
	public static final Potion field_76405_D = null;
	public static final Potion field_76406_E = null;
	public static final Potion field_76407_F = null;
	public static final Potion field_76408_G = null;
	public final int id;
	private final Map field_111188_I = Maps.newHashMap();
	private final boolean isBadEffect;
	private final int liquidColor;
	private String name = "";
	private int statusIconIndex = -1;
	private double effectiveness;
	private boolean usable;
	
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
	
	public void affectEntity(EntityLivingBase par1EntityLivingBase, EntityLivingBase par2EntityLivingBase, int par3, double par4)
	{
		int var6;
		if((id != heal.id || par2EntityLivingBase.isEntityUndead()) && (id != harm.id || !par2EntityLivingBase.isEntityUndead()))
		{
			if(id == harm.id && !par2EntityLivingBase.isEntityUndead() || id == heal.id && par2EntityLivingBase.isEntityUndead())
			{
				var6 = (int) (par4 * (6 << par3) + 0.5D);
				if(par1EntityLivingBase == null)
				{
					par2EntityLivingBase.attackEntityFrom(DamageSource.magic, var6);
				} else
				{
					par2EntityLivingBase.attackEntityFrom(DamageSource.causeIndirectMagicDamage(par2EntityLivingBase, par1EntityLivingBase), var6);
				}
			}
		} else
		{
			var6 = (int) (par4 * (4 << par3) + 0.5D);
			par2EntityLivingBase.heal(var6);
		}
	}
	
	public double func_111183_a(int par1, AttributeModifier par2AttributeModifier)
	{
		return par2AttributeModifier.func_111164_d() * (par1 + 1);
	}
	
	public Potion func_111184_a(Attribute par1Attribute, String par2Str, double par3, int par5)
	{
		AttributeModifier var6 = new AttributeModifier(UUID.fromString(par2Str), getName(), par3, par5);
		field_111188_I.put(par1Attribute, var6);
		return this;
	}
	
	public void func_111185_a(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
	{
		Iterator var4 = field_111188_I.entrySet().iterator();
		while(var4.hasNext())
		{
			Entry var5 = (Entry) var4.next();
			AttributeInstance var6 = par2BaseAttributeMap.func_111151_a((Attribute) var5.getKey());
			if(var6 != null)
			{
				AttributeModifier var7 = (AttributeModifier) var5.getValue();
				var6.func_111124_b(var7);
				var6.func_111121_a(new AttributeModifier(var7.func_111167_a(), getName() + " " + par3, func_111183_a(par3, var7), var7.func_111169_c()));
			}
		}
	}
	
	public Map func_111186_k()
	{
		return field_111188_I;
	}
	
	public void func_111187_a(EntityLivingBase par1EntityLivingBase, BaseAttributeMap par2BaseAttributeMap, int par3)
	{
		Iterator var4 = field_111188_I.entrySet().iterator();
		while(var4.hasNext())
		{
			Entry var5 = (Entry) var4.next();
			AttributeInstance var6 = par2BaseAttributeMap.func_111151_a((Attribute) var5.getKey());
			if(var6 != null)
			{
				var6.func_111124_b((AttributeModifier) var5.getValue());
			}
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
		if(id == regeneration.id)
		{
			var3 = 50 >> par2;
			return var3 > 0 ? par1 % var3 == 0 : true;
		} else if(id == poison.id)
		{
			var3 = 25 >> par2;
			return var3 > 0 ? par1 % var3 == 0 : true;
		} else if(id == wither.id)
		{
			var3 = 40 >> par2;
			return var3 > 0 ? par1 % var3 == 0 : true;
		} else return id == hunger.id;
	}
	
	public boolean isUsable()
	{
		return usable;
	}
	
	public void performEffect(EntityLivingBase par1EntityLivingBase, int par2)
	{
		if(id == regeneration.id)
		{
			if(par1EntityLivingBase.func_110143_aJ() < par1EntityLivingBase.func_110138_aP())
			{
				par1EntityLivingBase.heal(1.0F);
			}
		} else if(id == poison.id)
		{
			if(par1EntityLivingBase.func_110143_aJ() > 1.0F)
			{
				par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 1.0F);
			}
		} else if(id == wither.id)
		{
			par1EntityLivingBase.attackEntityFrom(DamageSource.wither, 1.0F);
		} else if(id == hunger.id && par1EntityLivingBase instanceof EntityPlayer)
		{
			((EntityPlayer) par1EntityLivingBase).addExhaustion(0.025F * (par2 + 1));
		} else if(id == field_76443_y.id && par1EntityLivingBase instanceof EntityPlayer)
		{
			if(!par1EntityLivingBase.worldObj.isRemote)
			{
				((EntityPlayer) par1EntityLivingBase).getFoodStats().addStats(par2 + 1, 1.0F);
			}
		} else if((id != heal.id || par1EntityLivingBase.isEntityUndead()) && (id != harm.id || !par1EntityLivingBase.isEntityUndead()))
		{
			if(id == harm.id && !par1EntityLivingBase.isEntityUndead() || id == heal.id && par1EntityLivingBase.isEntityUndead())
			{
				par1EntityLivingBase.attackEntityFrom(DamageSource.magic, 6 << par2);
			}
		} else
		{
			par1EntityLivingBase.heal(Math.max(4 << par2, 0));
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
