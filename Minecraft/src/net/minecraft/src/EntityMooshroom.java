package net.minecraft.src;

public class EntityMooshroom extends EntityCow
{
	public EntityMooshroom(World par1World)
	{
		super(par1World);
		setSize(0.9F, 1.3F);
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return func_94900_c(par1EntityAgeable);
	}
	
	public EntityMooshroom func_94900_c(EntityAgeable par1EntityAgeable)
	{
		return new EntityMooshroom(worldObj);
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.bowlEmpty.itemID && getGrowingAge() >= 0)
		{
			if(var2.stackSize == 1)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bowlSoup));
				return true;
			}
			if(par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bowlSoup)) && !par1EntityPlayer.capabilities.isCreativeMode)
			{
				par1EntityPlayer.inventory.decrStackSize(par1EntityPlayer.inventory.currentItem, 1);
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
				var3.setEntityHealth(func_110143_aJ());
				var3.renderYawOffset = renderYawOffset;
				worldObj.spawnEntityInWorld(var3);
				for(int var4 = 0; var4 < 5; ++var4)
				{
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY + height, posZ, new ItemStack(Block.mushroomRed)));
				}
			}
			return true;
		} else return super.interact(par1EntityPlayer);
	}
	
	@Override public EntityCow spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		return func_94900_c(par1EntityAgeable);
	}
}
