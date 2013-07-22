package net.minecraft.src;

public class DamageSource
{
	public static DamageSource inFire = new DamageSource("inFire").setFireDamage();
	public static DamageSource onFire = new DamageSource("onFire").setDamageBypassesArmor().setFireDamage();
	public static DamageSource lava = new DamageSource("lava").setFireDamage();
	public static DamageSource inWall = new DamageSource("inWall").setDamageBypassesArmor();
	public static DamageSource drown = new DamageSource("drown").setDamageBypassesArmor();
	public static DamageSource starve = new DamageSource("starve").setDamageBypassesArmor();
	public static DamageSource cactus = new DamageSource("cactus");
	public static DamageSource fall = new DamageSource("fall").setDamageBypassesArmor();
	public static DamageSource outOfWorld = new DamageSource("outOfWorld").setDamageBypassesArmor().setDamageAllowedInCreativeMode();
	public static DamageSource generic = new DamageSource("generic").setDamageBypassesArmor();
	public static DamageSource magic = new DamageSource("magic").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource wither = new DamageSource("wither").setDamageBypassesArmor();
	public static DamageSource anvil = new DamageSource("anvil");
	public static DamageSource fallingBlock = new DamageSource("fallingBlock");
	private boolean isUnblockable = false;
	private boolean isDamageAllowedInCreativeMode = false;
	private float hungerDamage = 0.3F;
	private boolean fireDamage;
	private boolean projectile;
	private boolean difficultyScaled;
	private boolean magicDamage = false;
	private boolean explosion = false;
	public String damageType;
	
	protected DamageSource(String p_i3429_1_)
	{
		damageType = p_i3429_1_;
	}
	
	public boolean canHarmInCreative()
	{
		return isDamageAllowedInCreativeMode;
	}
	
	public String getDamageType()
	{
		return damageType;
	}
	
	public String getDeathMessage(EntityLiving p_76360_1_)
	{
		EntityLiving var2 = p_76360_1_.func_94060_bK();
		String var3 = "death.attack." + damageType;
		String var4 = var3 + ".player";
		return var2 != null && StatCollector.func_94522_b(var4) ? StatCollector.translateToLocalFormatted(var4, new Object[] { p_76360_1_.getTranslatedEntityName(), var2.getTranslatedEntityName() }) : StatCollector.translateToLocalFormatted(var3, new Object[] { p_76360_1_.getTranslatedEntityName() });
	}
	
	public Entity getEntity()
	{
		return null;
	}
	
	public float getHungerDamage()
	{
		return hungerDamage;
	}
	
	public Entity getSourceOfDamage()
	{
		return getEntity();
	}
	
	public boolean isDifficultyScaled()
	{
		return difficultyScaled;
	}
	
	public boolean isExplosion()
	{
		return explosion;
	}
	
	public boolean isFireDamage()
	{
		return fireDamage;
	}
	
	public boolean isMagicDamage()
	{
		return magicDamage;
	}
	
	public boolean isProjectile()
	{
		return projectile;
	}
	
	public boolean isUnblockable()
	{
		return isUnblockable;
	}
	
	protected DamageSource setDamageAllowedInCreativeMode()
	{
		isDamageAllowedInCreativeMode = true;
		return this;
	}
	
	protected DamageSource setDamageBypassesArmor()
	{
		isUnblockable = true;
		hungerDamage = 0.0F;
		return this;
	}
	
	public DamageSource setDifficultyScaled()
	{
		difficultyScaled = true;
		return this;
	}
	
	public DamageSource setExplosion()
	{
		explosion = true;
		return this;
	}
	
	protected DamageSource setFireDamage()
	{
		fireDamage = true;
		return this;
	}
	
	public DamageSource setMagicDamage()
	{
		magicDamage = true;
		return this;
	}
	
	public DamageSource setProjectile()
	{
		projectile = true;
		return this;
	}
	
	public static DamageSource causeArrowDamage(EntityArrow p_76353_0_, Entity p_76353_1_)
	{
		return new EntityDamageSourceIndirect("arrow", p_76353_0_, p_76353_1_).setProjectile();
	}
	
	public static DamageSource causeFireballDamage(EntityFireball p_76362_0_, Entity p_76362_1_)
	{
		return p_76362_1_ == null ? new EntityDamageSourceIndirect("onFire", p_76362_0_, p_76362_0_).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", p_76362_0_, p_76362_1_).setFireDamage().setProjectile();
	}
	
	public static DamageSource causeIndirectMagicDamage(Entity p_76354_0_, Entity p_76354_1_)
	{
		return new EntityDamageSourceIndirect("indirectMagic", p_76354_0_, p_76354_1_).setDamageBypassesArmor().setMagicDamage();
	}
	
	public static DamageSource causeMobDamage(EntityLiving p_76358_0_)
	{
		return new EntityDamageSource("mob", p_76358_0_);
	}
	
	public static DamageSource causePlayerDamage(EntityPlayer p_76365_0_)
	{
		return new EntityDamageSource("player", p_76365_0_);
	}
	
	public static DamageSource causeThornsDamage(Entity p_92087_0_)
	{
		return new EntityDamageSource("thorns", p_92087_0_).setMagicDamage();
	}
	
	public static DamageSource causeThrownDamage(Entity p_76356_0_, Entity p_76356_1_)
	{
		return new EntityDamageSourceIndirect("thrown", p_76356_0_, p_76356_1_).setProjectile();
	}
	
	public static DamageSource setExplosionSource(Explosion p_94539_0_)
	{
		return p_94539_0_ != null && p_94539_0_.func_94613_c() != null ? new EntityDamageSource("explosion.player", p_94539_0_.func_94613_c()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
	}
}
