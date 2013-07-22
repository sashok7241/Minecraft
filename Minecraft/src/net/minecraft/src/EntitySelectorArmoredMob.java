package net.minecraft.src;

public class EntitySelectorArmoredMob implements IEntitySelector
{
	private final ItemStack field_96567_c;
	
	public EntitySelectorArmoredMob(ItemStack p_i10049_1_)
	{
		field_96567_c = p_i10049_1_;
	}
	
	@Override public boolean isEntityApplicable(Entity p_82704_1_)
	{
		if(!p_82704_1_.isEntityAlive()) return false;
		else if(!(p_82704_1_ instanceof EntityLiving)) return false;
		else
		{
			EntityLiving var2 = (EntityLiving) p_82704_1_;
			return var2.getCurrentItemOrArmor(EntityLiving.getArmorPosition(field_96567_c)) != null ? false : var2.canPickUpLoot() || var2 instanceof EntityPlayer;
		}
	}
}
