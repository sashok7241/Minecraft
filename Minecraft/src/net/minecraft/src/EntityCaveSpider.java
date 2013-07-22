package net.minecraft.src;

public class EntityCaveSpider extends EntitySpider
{
	public EntityCaveSpider(World p_i3546_1_)
	{
		super(p_i3546_1_);
		texture = "/mob/cavespider.png";
		setSize(0.7F, 0.5F);
	}
	
	@Override public boolean attackEntityAsMob(Entity p_70652_1_)
	{
		if(super.attackEntityAsMob(p_70652_1_))
		{
			if(p_70652_1_ instanceof EntityLiving)
			{
				byte var2 = 0;
				if(worldObj.difficultySetting > 1)
				{
					if(worldObj.difficultySetting == 2)
					{
						var2 = 7;
					} else if(worldObj.difficultySetting == 3)
					{
						var2 = 15;
					}
				}
				if(var2 > 0)
				{
					((EntityLiving) p_70652_1_).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
				}
			}
			return true;
		} else return false;
	}
	
	@Override public int getMaxHealth()
	{
		return 12;
	}
	
	@Override public void initCreature()
	{
	}
	
	@Override public float spiderScaleAmount()
	{
		return 0.7F;
	}
}
