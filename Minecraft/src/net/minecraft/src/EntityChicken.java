package net.minecraft.src;

public class EntityChicken extends EntityAnimal
{
	public float field_70886_e;
	public float destPos;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;
	public int timeUntilNextEgg;
	
	public EntityChicken(World par1World)
	{
		super(par1World);
		setSize(0.3F, 0.7F);
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.0D, Item.seeds.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
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
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
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
