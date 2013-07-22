package net.minecraft.src;

public class EntityCow extends EntityAnimal
{
	public EntityCow(World p_i3516_1_)
	{
		super(p_i3516_1_);
		texture = "/mob/cow.png";
		setSize(0.9F, 1.3F);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		tasks.addTask(2, new EntityAIMate(this, 0.2F));
		tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		tasks.addTask(5, new EntityAIWander(this, 0.2F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}
	
	@Override public EntityAgeable createChild(EntityAgeable p_90011_1_)
	{
		return spawnBabyAnimal(p_90011_1_);
	}
	
	@Override protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
	{
		int var3 = rand.nextInt(3) + rand.nextInt(1 + p_70628_2_);
		int var4;
		for(var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.leather.itemID, 1);
		}
		var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + p_70628_2_);
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
	
	@Override public int getMaxHealth()
	{
		return 10;
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.4F;
	}
	
	@Override public boolean interact(EntityPlayer p_70085_1_)
	{
		ItemStack var2 = p_70085_1_.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.bucketEmpty.itemID)
		{
			if(--var2.stackSize <= 0)
			{
				p_70085_1_.inventory.setInventorySlotContents(p_70085_1_.inventory.currentItem, new ItemStack(Item.bucketMilk));
			} else if(!p_70085_1_.inventory.addItemStackToInventory(new ItemStack(Item.bucketMilk)))
			{
				p_70085_1_.dropPlayerItem(new ItemStack(Item.bucketMilk.itemID, 1, 0));
			}
			return true;
		} else return super.interact(p_70085_1_);
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override protected void playStepSound(int p_70036_1_, int p_70036_2_, int p_70036_3_, int p_70036_4_)
	{
		playSound("mob.cow.step", 0.15F, 1.0F);
	}
	
	public EntityCow spawnBabyAnimal(EntityAgeable p_70879_1_)
	{
		return new EntityCow(worldObj);
	}
}
