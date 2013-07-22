package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
	private static final UUID field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
	private static final AttributeModifier field_110185_bq = new AttributeModifier(field_110184_bp, "Drinking speed penalty", -0.25D, 0).func_111168_a(false);
	private static final int[] witchDrops = new int[] { Item.glowstone.itemID, Item.sugar.itemID, Item.redstone.itemID, Item.spiderEye.itemID, Item.glassBottle.itemID, Item.gunpowder.itemID, Item.stick.itemID, Item.stick.itemID };
	private int witchAttackTimer;
	
	public EntityWitch(World par1World)
	{
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 60, 10.0F));
		tasks.addTask(2, new EntityAIWander(this, 1.0D));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(3, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
	@Override protected float applyPotionDamageCalculations(DamageSource par1DamageSource, float par2)
	{
		par2 = super.applyPotionDamageCalculations(par1DamageSource, par2);
		if(par1DamageSource.getEntity() == this)
		{
			par2 = 0.0F;
		}
		if(par1DamageSource.isMagicDamage())
		{
			par2 = (float) (par2 * 0.15D);
		}
		return par2;
	}
	
	@Override public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
	{
		if(!getAggressive())
		{
			EntityPotion var3 = new EntityPotion(worldObj, this, 32732);
			var3.rotationPitch -= -20.0F;
			double var4 = par1EntityLivingBase.posX + par1EntityLivingBase.motionX - posX;
			double var6 = par1EntityLivingBase.posY + par1EntityLivingBase.getEyeHeight() - 1.100000023841858D - posY;
			double var8 = par1EntityLivingBase.posZ + par1EntityLivingBase.motionZ - posZ;
			float var10 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
			if(var10 >= 8.0F && !par1EntityLivingBase.isPotionActive(Potion.moveSlowdown))
			{
				var3.setPotionDamage(32698);
			} else if(par1EntityLivingBase.func_110143_aJ() >= 8.0F && !par1EntityLivingBase.isPotionActive(Potion.poison))
			{
				var3.setPotionDamage(32660);
			} else if(var10 <= 3.0F && !par1EntityLivingBase.isPotionActive(Potion.weakness) && rand.nextFloat() < 0.25F)
			{
				var3.setPotionDamage(32696);
			}
			var3.setThrowableHeading(var4, var6 + var10 * 0.2F, var8, 0.75F, 8.0F);
			worldObj.spawnEntityInWorld(var3);
		}
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(3) + 1;
		for(int var4 = 0; var4 < var3; ++var4)
		{
			int var5 = rand.nextInt(3);
			int var6 = witchDrops[rand.nextInt(witchDrops.length)];
			if(par2 > 0)
			{
				var5 += rand.nextInt(par2 + 1);
			}
			for(int var7 = 0; var7 < var5; ++var7)
			{
				dropItem(var6, 1);
			}
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(21, Byte.valueOf((byte) 0));
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(26.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
	}
	
	public boolean getAggressive()
	{
		return getDataWatcher().getWatchableObjectByte(21) == 1;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.witch.death";
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.witch.hurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.witch.idle";
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 15)
		{
			for(int var2 = 0; var2 < rand.nextInt(35) + 10; ++var2)
			{
				worldObj.spawnParticle("witchMagic", posX + rand.nextGaussian() * 0.12999999523162842D, boundingBox.maxY + 0.5D + rand.nextGaussian() * 0.12999999523162842D, posZ + rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D);
			}
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public void onLivingUpdate()
	{
		if(!worldObj.isRemote)
		{
			if(getAggressive())
			{
				if(witchAttackTimer-- <= 0)
				{
					setAggressive(false);
					ItemStack var1 = getHeldItem();
					setCurrentItemOrArmor(0, (ItemStack) null);
					if(var1 != null && var1.itemID == Item.potion.itemID)
					{
						List var2 = Item.potion.getEffects(var1);
						if(var2 != null)
						{
							Iterator var3 = var2.iterator();
							while(var3.hasNext())
							{
								PotionEffect var4 = (PotionEffect) var3.next();
								addPotionEffect(new PotionEffect(var4));
							}
						}
					}
					func_110148_a(SharedMonsterAttributes.field_111263_d).func_111124_b(field_110185_bq);
				}
			} else
			{
				short var5 = -1;
				if(rand.nextFloat() < 0.15F && isBurning() && !this.isPotionActive(Potion.fireResistance))
				{
					var5 = 16307;
				} else if(rand.nextFloat() < 0.05F && func_110143_aJ() < func_110138_aP())
				{
					var5 = 16341;
				} else if(rand.nextFloat() < 0.25F && getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && getAttackTarget().getDistanceSqToEntity(this) > 121.0D)
				{
					var5 = 16274;
				} else if(rand.nextFloat() < 0.25F && getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && getAttackTarget().getDistanceSqToEntity(this) > 121.0D)
				{
					var5 = 16274;
				}
				if(var5 > -1)
				{
					setCurrentItemOrArmor(0, new ItemStack(Item.potion, 1, var5));
					witchAttackTimer = getHeldItem().getMaxItemUseDuration();
					setAggressive(true);
					AttributeInstance var6 = func_110148_a(SharedMonsterAttributes.field_111263_d);
					var6.func_111124_b(field_110185_bq);
					var6.func_111121_a(field_110185_bq);
				}
			}
			if(rand.nextFloat() < 7.5E-4F)
			{
				worldObj.setEntityState(this, (byte) 15);
			}
		}
		super.onLivingUpdate();
	}
	
	public void setAggressive(boolean par1)
	{
		getDataWatcher().updateObject(21, Byte.valueOf((byte) (par1 ? 1 : 0)));
	}
}
