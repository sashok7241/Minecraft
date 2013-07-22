package net.minecraft.src;

import java.util.Random;

public class EntitySheep extends EntityAnimal
{
	private final InventoryCrafting field_90016_e = new InventoryCrafting(new ContainerSheep(this), 2, 1);
	public static final float[][] fleeceColorTable = new float[][] { { 1.0F, 1.0F, 1.0F }, { 0.85F, 0.5F, 0.2F }, { 0.7F, 0.3F, 0.85F }, { 0.4F, 0.6F, 0.85F }, { 0.9F, 0.9F, 0.2F }, { 0.5F, 0.8F, 0.1F }, { 0.95F, 0.5F, 0.65F }, { 0.3F, 0.3F, 0.3F }, { 0.6F, 0.6F, 0.6F }, { 0.3F, 0.5F, 0.6F }, { 0.5F, 0.25F, 0.7F }, { 0.2F, 0.3F, 0.7F }, { 0.4F, 0.3F, 0.2F }, { 0.4F, 0.5F, 0.2F }, { 0.6F, 0.2F, 0.2F }, { 0.1F, 0.1F, 0.1F } };
	private int sheepTimer;
	private EntityAIEatGrass aiEatGrass = new EntityAIEatGrass(this);
	
	public EntitySheep(World par1World)
	{
		super(par1World);
		texture = "/mob/sheep.png";
		setSize(0.9F, 1.3F);
		float var2 = 0.23F;
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 0.38F));
		tasks.addTask(2, new EntityAIMate(this, var2));
		tasks.addTask(3, new EntityAITempt(this, 0.25F, Item.wheat.itemID, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 0.25F));
		tasks.addTask(5, aiEatGrass);
		tasks.addTask(6, new EntityAIWander(this, var2));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		field_90016_e.setInventorySlotContents(0, new ItemStack(Item.dyePowder, 1, 0));
		field_90016_e.setInventorySlotContents(1, new ItemStack(Item.dyePowder, 1, 0));
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return func_90015_b(par1EntityAgeable);
	}
	
	@Override protected void dropFewItems(boolean par1, int par2)
	{
		if(!getSheared())
		{
			entityDropItem(new ItemStack(Block.cloth.blockID, 1, getFleeceColor()), 0.0F);
		}
	}
	
	@Override public void eatGrassBonus()
	{
		setSheared(false);
		if(isChild())
		{
			int var1 = getGrowingAge() + 1200;
			if(var1 > 0)
			{
				var1 = 0;
			}
			setGrowingAge(var1);
		}
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte) 0));
	}
	
	public float func_70890_k(float par1)
	{
		if(sheepTimer > 4 && sheepTimer <= 36)
		{
			float var2 = (sheepTimer - 4 - par1) / 32.0F;
			return (float) Math.PI / 5F + (float) Math.PI * 7F / 100F * MathHelper.sin(var2 * 28.7F);
		} else return sheepTimer > 0 ? (float) Math.PI / 5F : rotationPitch / (180F / (float) Math.PI);
	}
	
	public float func_70894_j(float par1)
	{
		return sheepTimer <= 0 ? 0.0F : sheepTimer >= 4 && sheepTimer <= 36 ? 1.0F : sheepTimer < 4 ? (sheepTimer - par1) / 4.0F : -(sheepTimer - 40 - par1) / 4.0F;
	}
	
	private int func_90013_b(EntityAnimal par1EntityAnimal)
	{
		return 15 - ((EntitySheep) par1EntityAnimal).getFleeceColor();
	}
	
	private int func_90014_a(EntityAnimal par1EntityAnimal, EntityAnimal par2EntityAnimal)
	{
		int var3 = func_90013_b(par1EntityAnimal);
		int var4 = func_90013_b(par2EntityAnimal);
		field_90016_e.getStackInSlot(0).setItemDamage(var3);
		field_90016_e.getStackInSlot(1).setItemDamage(var4);
		ItemStack var5 = CraftingManager.getInstance().findMatchingRecipe(field_90016_e, ((EntitySheep) par1EntityAnimal).worldObj);
		int var6;
		if(var5 != null && var5.getItem().itemID == Item.dyePowder.itemID)
		{
			var6 = var5.getItemDamage();
		} else
		{
			var6 = worldObj.rand.nextBoolean() ? var3 : var4;
		}
		return var6;
	}
	
	public EntitySheep func_90015_b(EntityAgeable par1EntityAgeable)
	{
		EntitySheep var2 = (EntitySheep) par1EntityAgeable;
		EntitySheep var3 = new EntitySheep(worldObj);
		int var4 = func_90014_a(this, var2);
		var3.setFleeceColor(15 - var4);
		return var3;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.sheep.say";
	}
	
	@Override protected int getDropItemId()
	{
		return Block.cloth.blockID;
	}
	
	public int getFleeceColor()
	{
		return dataWatcher.getWatchableObjectByte(16) & 15;
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.sheep.say";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.sheep.say";
	}
	
	@Override public int getMaxHealth()
	{
		return 8;
	}
	
	public boolean getSheared()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 16) != 0;
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 10)
		{
			sheepTimer = 40;
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public void initCreature()
	{
		setFleeceColor(getRandomFleeceColor(worldObj.rand));
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.shears.itemID && !getSheared() && !isChild())
		{
			if(!worldObj.isRemote)
			{
				setSheared(true);
				int var3 = 1 + rand.nextInt(3);
				for(int var4 = 0; var4 < var3; ++var4)
				{
					EntityItem var5 = entityDropItem(new ItemStack(Block.cloth.blockID, 1, getFleeceColor()), 1.0F);
					var5.motionY += rand.nextFloat() * 0.05F;
					var5.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
					var5.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
				}
			}
			var2.damageItem(1, par1EntityPlayer);
			playSound("mob.sheep.shear", 1.0F, 1.0F);
		}
		return super.interact(par1EntityPlayer);
	}
	
	@Override protected boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public void onLivingUpdate()
	{
		if(worldObj.isRemote)
		{
			sheepTimer = Math.max(0, sheepTimer - 1);
		}
		super.onLivingUpdate();
	}
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.sheep.step", 0.15F, 1.0F);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setSheared(par1NBTTagCompound.getBoolean("Sheared"));
		setFleeceColor(par1NBTTagCompound.getByte("Color"));
	}
	
	public void setFleeceColor(int par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & 240 | par1 & 15)));
	}
	
	public void setSheared(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);
		if(par1)
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 | 16)));
		} else
		{
			dataWatcher.updateObject(16, Byte.valueOf((byte) (var2 & -17)));
		}
	}
	
	@Override protected void updateAITasks()
	{
		sheepTimer = aiEatGrass.getEatGrassTick();
		super.updateAITasks();
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("Sheared", getSheared());
		par1NBTTagCompound.setByte("Color", (byte) getFleeceColor());
	}
	
	public static int getRandomFleeceColor(Random par0Random)
	{
		int var1 = par0Random.nextInt(100);
		return var1 < 5 ? 15 : var1 < 10 ? 7 : var1 < 15 ? 8 : var1 < 18 ? 12 : par0Random.nextInt(500) == 0 ? 6 : 0;
	}
}
