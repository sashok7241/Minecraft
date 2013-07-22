package net.minecraft.src;

public class EntityCow extends EntityAnimal
{
	public EntityCow(World par1World)
	{
		super(par1World);
		setSize(0.9F, 1.3F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.25D, Item.wheat.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return spawnBabyAnimal(par1EntityAgeable);
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(3) + rand.nextInt(1 + par2);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.leather.itemID, 1);
		}
		var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + par2);
		for(var4 = 0; var4 < var3; ++var4)
		{
			if(isBurning())
			{
				dropItem(Item.beefCooked.itemID, 1);
			} else
			{
				dropItem(Item.beefRaw.itemID, 1);
			}
		}
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.cow.hurt";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.leather.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.cow.hurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.cow.say";
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.bucketEmpty.itemID && !par1EntityPlayer.capabilities.isCreativeMode)
		{
			if(var2.stackSize-- == 1)
			{
				par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
			} else if(!par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketMilk)))
			{
				par1EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketMilk.itemID, 1, 0));
			}
			return true;
		} else return super.interact(par1EntityPlayer);
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.cow.step", 0.15F, 1.0F);
	}
	
	public EntityCow spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		return new EntityCow(worldObj);
	}
}
