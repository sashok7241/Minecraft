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
	
	protected DamageSource(String par1Str)
	{
		damageType = par1Str;
	}
	
	public boolean canHarmInCreative()
	{
		return isDamageAllowedInCreativeMode;
	}
	
	public String getDamageType()
	{
		return damageType;
	}
	
	public String getDeathMessage(EntityLiving par1EntityLiving)
	{
		EntityLiving var2 = par1EntityLiving.func_94060_bK();
		String var3 = "death.attack." + damageType;
		String var4 = var3 + ".player";
		return var2 != null && StatCollector.func_94522_b(var4) ? StatCollector.translateToLocalFormatted(var4, new Object[] { par1EntityLiving.getTranslatedEntityName(), var2.getTranslatedEntityName() }) : StatCollector.translateToLocalFormatted(var3, new Object[] { par1EntityLiving.getTranslatedEntityName() });
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
	
	public static DamageSource causeArrowDamage(EntityArrow par0EntityArrow, Entity par1Entity)
	{
		return new EntityDamageSourceIndirect("arrow", par0EntityArrow, par1Entity).setProjectile();
	}
	
	public static DamageSource causeFireballDamage(EntityFireball par0EntityFireball, Entity par1Entity)
	{
		return par1Entity == null ? new EntityDamageSourceIndirect("onFire", par0EntityFireball, par0EntityFireball).setFireDamage().setProjectile() : new EntityDamageSourceIndirect("fireball", par0EntityFireball, par1Entity).setFireDamage().setProjectile();
	}
	
	public static DamageSource causeIndirectMagicDamage(Entity par0Entity, Entity par1Entity)
	{
		return new EntityDamageSourceIndirect("indirectMagic", par0Entity, par1Entity).setDamageBypassesArmor().setMagicDamage();
	}
	
	public static DamageSource causeMobDamage(EntityLiving par0EntityLiving)
	{
		return new EntityDamageSource("mob", par0EntityLiving);
	}
	
	public static DamageSource causePlayerDamage(EntityPlayer par0EntityPlayer)
	{
		return new EntityDamageSource("player", par0EntityPlayer);
	}
	
	public static DamageSource causeThornsDamage(Entity par0Entity)
	{
		return new EntityDamageSource("thorns", par0Entity).setMagicDamage();
	}
	
	public static DamageSource causeThrownDamage(Entity par0Entity, Entity par1Entity)
	{
		return new EntityDamageSourceIndirect("thrown", par0Entity, par1Entity).setProjectile();
	}
	
	public static DamageSource setExplosionSource(Explosion par0Explosion)
	{
		return par0Explosion != null && par0Explosion.func_94613_c() != null ? new EntityDamageSource("explosion.player", par0Explosion.func_94613_c()).setDifficultyScaled().setExplosion() : new DamageSource("explosion").setDifficultyScaled().setExplosion();
	}
}
