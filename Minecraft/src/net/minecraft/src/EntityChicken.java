package net.minecraft.src;

public class EntityChicken extends EntityAnimal
{
	public boolean field_70885_d = false;
	public float field_70886_e = 0.0F;
	public float destPos = 0.0F;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;
	public int timeUntilNextEgg;
	
	public EntityChicken(World par1World)
	{
		super(par1World);
		texture = "/mob/chicken.png";
		setSize(0.3F, 0.7F);
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
		float var2 = 0.25F;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		tasks.addTask(2, new EntityAIMate(this, var2));
		tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.seeds.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 0.28F));
		tasks.addTask(5, new EntityAIWander(this, var2));
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
		for(int var4 = 0; var4 < var3; ++var4)
		{
			dropItem(Item.feather.itemID, 1);
		}
		if(isBurning())
		{
			dropItem(Item.chickenCooked.itemID, 1);
		} else
		{
			dropItem(Item.chickenRaw.itemID, 1);
		}
	}
	
	@Override protected void fall(float par1)
	{
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.chicken.hurt";
	}
	
	@Override protected int getDropItemId()
	{
		return Item.feather.itemID;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.chicken.hurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.chicken.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 4;
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack != null && par1ItemStack.getItem() instanceof ItemSeeds;
	}
	
	@Override public void onLivingUpdate()
	{
		super.onLivingUpdate();
		field_70888_h = field_70886_e;
		field_70884_g = destPos;
		destPos = (float) (destPos + (onGround ? -1 : 4) * 0.3D);
		if(destPos < 0.0F)
		{
			destPos = 0.0F;
		}
		if(destPos > 1.0F)
		{
			destPos = 1.0F;
		}
		if(!onGround && field_70889_i < 1.0F)
		{
			field_70889_i = 1.0F;
		}
		field_70889_i = (float) (field_70889_i * 0.9D);
		if(!onGround && motionY < 0.0D)
		{
			motionY *= 0.6D;
		}
		field_70886_e += field_70889_i * 2.0F;
		if(!isChild() && !worldObj.isRemote && --timeUntilNextEgg <= 0)
		{
			playSound("mob.chicken.plop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			dropItem(Item.egg.itemID, 1);
			timeUntilNextEgg = rand.nextInt(6000) + 6000;
		}
	}
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.chicken.step", 0.15F, 1.0F);
	}
	
	public EntityChicken spawnBabyAnimal(EntityAgeable par1EntityAgeable)
	{
		return new EntityChicken(worldObj);
	}
}
