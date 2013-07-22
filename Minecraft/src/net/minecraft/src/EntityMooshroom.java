package net.minecraft.src;

public class EntityMooshroom extends EntityCow
{
	public EntityMooshroom(World p_i3518_1_)
	{
		super(p_i3518_1_);
		texture = "/mob/redcow.png";
		setSize(0.9F, 1.3F);
	}
	
	@Override public EntityAgeable createChild(EntityAgeable p_90011_1_)
	{
		return func_94900_c(p_90011_1_);
	}
	
	public EntityMooshroom func_94900_c(EntityAgeable p_94900_1_)
	{
		return new EntityMooshroom(worldObj);
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.bowlEmpty.itemID && getGrowingAge() >= 0)
		{
			if(var2.stackSize == 1)
			{
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, new ItemStack(Item.bowlSoup));
				return true;
			}
			if(p_70085_1_.inventory.addItemStackToInventory(new ItemStack(Item.bowlSoup)) && !p_70085_1_.capabilities.isCreativeMode)
			{
				p_70085_1_.inventory.decrStackSize(p_70085_1_.inventory.currentItem, 1);
				return true;
			}
		}
		if(var2 != null && var2.itemID == Item.shears.itemID && getGrowingAge() >= 0)
		{
			setDead();
			worldObj.spawnParticle("largeexplode", posX, posY + height / 2.0F, posZ, 0.0D, 0.0D, 0.0D);
			if(!worldObj.isRemote)
			{
				EntityCow var3 = new EntityCow(worldObj);
				var3.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
				var3.setEntityHealth(getHealth());
				var3.renderYawOffset = renderYawOffset;
				worldObj.spawnEntityInWorld(var3);
				for(int var4 = 0; var4 < 5; ++var4)
				{
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + height, posZ, new ItemStack(Block.mushroomRed)));
				}
			}
			return true;
		} else return super.interact(p_70085_1_);
	}
	
	@Override public EntityCow spawnBabyAnimal(EntityAgeable p_70879_1_)
	{
		return func_94900_c(p_70879_1_);
	}
}
