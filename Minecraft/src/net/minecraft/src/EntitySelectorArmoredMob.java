package net.minecraft.src;

public class EntitySelectorArmoredMob implements IEntitySelector
{
	private final ItemStack field_96567_c;
	
	public EntitySelectorArmoredMob(ItemStack par1ItemStack)
	{
		field_96567_c = par1ItemStack;
	}
	
	@Override public boolean isEntityApplicable(Entity par1Entity)
	{
		if(!par1Entity.isEntityAlive()) return false;
		else if(!(par1Entity instanceof EntityLiving)) return false;
		else
		{
			EntityLiving var2 = (EntityLiving) par1Entity;
			return var2.getCurrentItemOrArmor(EntityLiving.getArmorPosition(field_96567_c)) != null ? false : var2.canPickUpLoot() || var2 instanceof EntityPlayer;
		}
	}
}
