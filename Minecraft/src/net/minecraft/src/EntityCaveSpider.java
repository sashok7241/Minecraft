package net.minecraft.src;

public class EntityCaveSpider extends EntitySpider
{
	public EntityCaveSpider(World par1World)
	{
		super(par1World);
		setSize(0.7F, 0.5F);
	}
	
	@Override public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(super.attackEntityAsMob(par1Entity))
		{
			if(par1Entity instanceof EntityLivingBase)
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
					((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
				}
			}
			return true;
		} else return false;
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D);
	}
	
	@Override public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		return par1EntityLivingData;
	}
}
