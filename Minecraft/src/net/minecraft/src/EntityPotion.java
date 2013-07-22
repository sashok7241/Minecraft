package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityPotion extends EntityThrowable
{
	private ItemStack potionDamage;
	
	public EntityPotion(World p_i3595_1_)
	{
		super(p_i3595_1_);
	}
	
	public EntityPotion(World p_i3597_1_, double p_i3597_2_, double p_i3597_4_, double p_i3597_6_, int p_i3597_8_)
	{
		this(p_i3597_1_, p_i3597_2_, p_i3597_4_, p_i3597_6_, new ItemStack(Item.potion, 1, p_i3597_8_));
	}
	
	public EntityPotion(World p_i5071_1_, double p_i5071_2_, double p_i5071_4_, double p_i5071_6_, ItemStack p_i5071_8_)
	{
		super(p_i5071_1_, p_i5071_2_, p_i5071_4_, p_i5071_6_);
		potionDamage = p_i5071_8_;
	}
	
	public EntityPotion(World p_i3596_1_, EntityLiving p_i3596_2_, int p_i3596_3_)
	{
		this(p_i3596_1_, p_i3596_2_, new ItemStack(Item.potion, 1, p_i3596_3_));
	}
	
	public EntityPotion(World p_i5070_1_, EntityLiving p_i5070_2_, ItemStack p_i5070_3_)
	{
		super(p_i5070_1_, p_i5070_2_);
		potionDamage = p_i5070_3_;
	}
	
	@Override protected float func_70182_d()
	{
		return 0.5F;
	}
	
	@Override protected float func_70183_g()
	{
		return -20.0F;
	}
	
	@Override protected float getGravityVelocity()
	{
		return 0.05F;
	}
	
	public int getPotionDamage()
	{
		if(potionDamage == null)
		{
			potionDamage = new ItemStack(Item.potion, 1, 0);
		}
		return potionDamage.getItemDamage();
	}
	
	@Override protected void onImpact(MovingObjectPosition p_70184_1_)
	{
		if(!worldObj.isRemote)
		{
			List var2 = Item.potion.getEffects(potionDamage);
			if(var2 != null && !var2.isEmpty())
			{
				AxisAlignedBB var3 = boundingBox.expand(4.0D, 2.0D, 4.0D);
				List var4 = worldObj.getEntitiesWithinAABB(EntityLiving.class, var3);
				if(var4 != null && !var4.isEmpty())
				{
					Iterator var5 = var4.iterator();
					while(var5.hasNext())
					{
						EntityLiving var6 = (EntityLiving) var5.next();
						double var7 = getDistanceSqToEntity(var6);
						if(var7 < 16.0D)
						{
							double var9 = 1.0D - Math.sqrt(var7) / 4.0D;
							if(var6 == p_70184_1_.entityHit)
							{
								var9 = 1.0D;
							}
							Iterator var11 = var2.iterator();
							while(var11.hasNext())
							{
								PotionEffect var12 = (PotionEffect) var11.next();
								int var13 = var12.getPotionID();
								if(Potion.potionTypes[var13].isInstant())
								{
									Potion.potionTypes[var13].affectEntity(getThrower(), var6, var12.getAmplifier(), var9);
								} else
								{
									int var14 = (int) (var9 * var12.getDuration() + 0.5D);
									if(var14 > 20)
									{
										var6.addPotionEffect(new PotionEffect(var13, var14, var12.getAmplifier()));
									}
								}
							}
						}
					}
				}
			}
			worldObj.playAuxSFX(2002, (int) Math.round(posX), (int) Math.round(posY), (int) Math.round(posZ), getPotionDamage());
			setDead();
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound p_70037_1_)
	{
		super.readEntityFromNBT(p_70037_1_);
		if(p_70037_1_.hasKey("Potion"))
		{
			potionDamage = ItemStack.loadItemStackFromNBT(p_70037_1_.getCompoundTag("Potion"));
		} else
		{
			setPotionDamage(p_70037_1_.getInteger("potionValue"));
		}
		if(potionDamage == null)
		{
			setDead();
		}
	}
	
	public void setPotionDamage(int p_82340_1_)
	{
		if(potionDamage == null)
		{
			potionDamage = new ItemStack(Item.potion, 1, 0);
		}
		potionDamage.setItemDamage(p_82340_1_);
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound p_70014_1_)
	{
		super.writeEntityToNBT(p_70014_1_);
		if(potionDamage != null)
		{
			p_70014_1_.setCompoundTag("Potion", potionDamage.writeToNBT(new NBTTagCompound()));
		}
	}
}
