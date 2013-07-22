package net.minecraft.src;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant
{
	private int randomTickDivider;
	private boolean isMating;
	private boolean isPlaying;
	Village villageObj;
	private EntityPlayer buyingPlayer;
	private MerchantRecipeList buyingList;
	private int timeUntilReset;
	private boolean needsInitilization;
	private int wealth;
	private String lastBuyingPlayer;
	private boolean field_82190_bM;
	private float field_82191_bN;
	private static final Map villagerStockList = new HashMap();
	private static final Map blacksmithSellingList = new HashMap();
	
	public EntityVillager(World par1World)
	{
		this(par1World, 0);
	}
	
	public EntityVillager(World par1World, int par2)
	{
		super(par1World);
		randomTickDivider = 0;
		isMating = false;
		isPlaying = false;
		villageObj = null;
		setProfession(par2);
		texture = "/mob/villager/villager.png";
		moveSpeed = 0.5F;
		setSize(0.6F, 1.8F);
		getNavigator().setBreakDoors(true);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.3F, 0.35F));
		tasks.addTask(1, new EntityAITradePlayer(this));
		tasks.addTask(1, new EntityAILookAtTradePlayer(this));
		tasks.addTask(2, new EntityAIMoveIndoors(this));
		tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIMoveTwardsRestriction(this, 0.3F));
		tasks.addTask(6, new EntityAIVillagerMate(this));
		tasks.addTask(7, new EntityAIFollowGolem(this));
		tasks.addTask(8, new EntityAIPlay(this, 0.32F));
		tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
		tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0F, 0.02F));
		tasks.addTask(9, new EntityAIWander(this, 0.3F));
		tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
	}
	
	private void addDefaultEquipmentAndRecipies(int par1)
	{
		if(buyingList != null)
		{
			field_82191_bN = MathHelper.sqrt_float(buyingList.size()) * 0.2F;
		} else
		{
			field_82191_bN = 0.0F;
		}
		MerchantRecipeList var2;
		var2 = new MerchantRecipeList();
		int var6;
		label50: switch(getProfession())
		{
			case 0:
				addMerchantItem(var2, Item.wheat.itemID, rand, func_82188_j(0.9F));
				addMerchantItem(var2, Block.cloth.blockID, rand, func_82188_j(0.5F));
				addMerchantItem(var2, Item.chickenRaw.itemID, rand, func_82188_j(0.5F));
				addMerchantItem(var2, Item.fishCooked.itemID, rand, func_82188_j(0.4F));
				addBlacksmithItem(var2, Item.bread.itemID, rand, func_82188_j(0.9F));
				addBlacksmithItem(var2, Item.melon.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.appleRed.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.cookie.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.shears.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.flintAndSteel.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.chickenCooked.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.arrow.itemID, rand, func_82188_j(0.5F));
				if(rand.nextFloat() < func_82188_j(0.5F))
				{
					var2.add(new MerchantRecipe(new ItemStack(Block.gravel, 10), new ItemStack(Item.emerald), new ItemStack(Item.flint.itemID, 4 + rand.nextInt(2), 0)));
				}
				break;
			case 1:
				addMerchantItem(var2, Item.paper.itemID, rand, func_82188_j(0.8F));
				addMerchantItem(var2, Item.book.itemID, rand, func_82188_j(0.8F));
				addMerchantItem(var2, Item.writtenBook.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Block.bookShelf.blockID, rand, func_82188_j(0.8F));
				addBlacksmithItem(var2, Block.glass.blockID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.compass.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.pocketSundial.itemID, rand, func_82188_j(0.2F));
				if(rand.nextFloat() < func_82188_j(0.07F))
				{
					Enchantment var8 = Enchantment.enchantmentsBookList[rand.nextInt(Enchantment.enchantmentsBookList.length)];
					int var10 = MathHelper.getRandomIntegerInRange(rand, var8.getMinLevel(), var8.getMaxLevel());
					ItemStack var11 = Item.enchantedBook.getEnchantedItemStack(new EnchantmentData(var8, var10));
					var6 = 2 + rand.nextInt(5 + var10 * 10) + 3 * var10;
					var2.add(new MerchantRecipe(new ItemStack(Item.book), new ItemStack(Item.emerald, var6), var11));
				}
				break;
			case 2:
				addBlacksmithItem(var2, Item.eyeOfEnder.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.expBottle.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.redstone.itemID, rand, func_82188_j(0.4F));
				addBlacksmithItem(var2, Block.glowStone.blockID, rand, func_82188_j(0.3F));
				int[] var3 = new int[] { Item.swordIron.itemID, Item.swordDiamond.itemID, Item.plateIron.itemID, Item.plateDiamond.itemID, Item.axeIron.itemID, Item.axeDiamond.itemID, Item.pickaxeIron.itemID, Item.pickaxeDiamond.itemID };
				int[] var4 = var3;
				int var5 = var3.length;
				var6 = 0;
				while(true)
				{
					if(var6 >= var5)
					{
						break label50;
					}
					int var7 = var4[var6];
					if(rand.nextFloat() < func_82188_j(0.05F))
					{
						var2.add(new MerchantRecipe(new ItemStack(var7, 1, 0), new ItemStack(Item.emerald, 2 + rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(var7, 1, 0), 5 + rand.nextInt(15))));
					}
					++var6;
				}
			case 3:
				addMerchantItem(var2, Item.coal.itemID, rand, func_82188_j(0.7F));
				addMerchantItem(var2, Item.ingotIron.itemID, rand, func_82188_j(0.5F));
				addMerchantItem(var2, Item.ingotGold.itemID, rand, func_82188_j(0.5F));
				addMerchantItem(var2, Item.diamond.itemID, rand, func_82188_j(0.5F));
				addBlacksmithItem(var2, Item.swordIron.itemID, rand, func_82188_j(0.5F));
				addBlacksmithItem(var2, Item.swordDiamond.itemID, rand, func_82188_j(0.5F));
				addBlacksmithItem(var2, Item.axeIron.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.axeDiamond.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.pickaxeIron.itemID, rand, func_82188_j(0.5F));
				addBlacksmithItem(var2, Item.pickaxeDiamond.itemID, rand, func_82188_j(0.5F));
				addBlacksmithItem(var2, Item.shovelIron.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.shovelDiamond.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.hoeIron.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.hoeDiamond.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.bootsIron.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.bootsDiamond.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.helmetIron.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.helmetDiamond.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.plateIron.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.plateDiamond.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.legsIron.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.legsDiamond.itemID, rand, func_82188_j(0.2F));
				addBlacksmithItem(var2, Item.bootsChain.itemID, rand, func_82188_j(0.1F));
				addBlacksmithItem(var2, Item.helmetChain.itemID, rand, func_82188_j(0.1F));
				addBlacksmithItem(var2, Item.plateChain.itemID, rand, func_82188_j(0.1F));
				addBlacksmithItem(var2, Item.legsChain.itemID, rand, func_82188_j(0.1F));
				break;
			case 4:
				addMerchantItem(var2, Item.coal.itemID, rand, func_82188_j(0.7F));
				addMerchantItem(var2, Item.porkRaw.itemID, rand, func_82188_j(0.5F));
				addMerchantItem(var2, Item.beefRaw.itemID, rand, func_82188_j(0.5F));
				addBlacksmithItem(var2, Item.saddle.itemID, rand, func_82188_j(0.1F));
				addBlacksmithItem(var2, Item.plateLeather.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.bootsLeather.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.helmetLeather.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.legsLeather.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.porkCooked.itemID, rand, func_82188_j(0.3F));
				addBlacksmithItem(var2, Item.beefCooked.itemID, rand, func_82188_j(0.3F));
		}
		if(var2.isEmpty())
		{
			addMerchantItem(var2, Item.ingotGold.itemID, rand, 1.0F);
		}
		Collections.shuffle(var2);
		if(buyingList == null)
		{
			buyingList = new MerchantRecipeList();
		}
		for(int var9 = 0; var9 < par1 && var9 < var2.size(); ++var9)
		{
			buyingList.addToListWithCheck((MerchantRecipe) var2.get(var9));
		}
	}
	
	@Override protected boolean canDespawn()
	{
		return false;
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		return func_90012_b(par1EntityAgeable);
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Integer.valueOf(0));
	}
	
	public void func_82187_q()
	{
		field_82190_bM = true;
	}
	
	private float func_82188_j(float par1)
	{
		float var2 = par1 + field_82191_bN;
		return var2 > 0.9F ? 0.9F - (var2 - 0.9F) : var2;
	}
	
	public EntityVillager func_90012_b(EntityAgeable par1EntityAgeable)
	{
		EntityVillager var2 = new EntityVillager(worldObj);
		var2.initCreature();
		return var2;
	}
	
	private void generateRandomParticles(String par1Str)
	{
		for(int var2 = 0; var2 < 5; ++var2)
		{
			double var3 = rand.nextGaussian() * 0.02D;
			double var5 = rand.nextGaussian() * 0.02D;
			double var7 = rand.nextGaussian() * 0.02D;
			worldObj.spawnParticle(par1Str, posX + rand.nextFloat() * width * 2.0F - width, posY + 1.0D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var3, var5, var7);
		}
	}
	
	@Override public EntityPlayer getCustomer()
	{
		return buyingPlayer;
	}
	
	@Override protected String getDeathSound()
	{
		return "mob.villager.defaultdeath";
	}
	
	@Override protected String getHurtSound()
	{
		return "mob.villager.defaulthurt";
	}
	
	@Override protected String getLivingSound()
	{
		return "mob.villager.default";
	}
	
	@Override public int getMaxHealth()
	{
		return 20;
	}
	
	public int getProfession()
	{
		return dataWatcher.getWatchableObjectInt(16);
	}
	
	@Override public MerchantRecipeList getRecipes(EntityPlayer par1EntityPlayer)
	{
		if(buyingList == null)
		{
			addDefaultEquipmentAndRecipies(1);
		}
		return buyingList;
	}
	
	@Override public String getTexture()
	{
		switch(getProfession())
		{
			case 0:
				return "/mob/villager/farmer.png";
			case 1:
				return "/mob/villager/librarian.png";
			case 2:
				return "/mob/villager/priest.png";
			case 3:
				return "/mob/villager/smith.png";
			case 4:
				return "/mob/villager/butcher.png";
			default:
				return super.getTexture();
		}
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 12)
		{
			generateRandomParticles("heart");
		} else if(par1 == 13)
		{
			generateRandomParticles("angryVillager");
		} else if(par1 == 14)
		{
			generateRandomParticles("happyVillager");
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public void initCreature()
	{
		setProfession(worldObj.rand.nextInt(5));
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		boolean var3 = var2 != null && var2.itemID == Item.monsterPlacer.itemID;
		if(!var3 && isEntityAlive() && !isTrading() && !isChild())
		{
			if(!worldObj.isRemote)
			{
				setCustomer(par1EntityPlayer);
				par1EntityPlayer.displayGUIMerchant(this, getCustomNameTag());
			}
			return true;
		} else return super.interact(par1EntityPlayer);
	}
	
	@Override public boolean isAIEnabled()
	{
		return true;
	}
	
	public boolean isMating()
	{
		return isMating;
	}
	
	public boolean isPlaying()
	{
		return isPlaying;
	}
	
	public boolean isTrading()
	{
		return buyingPlayer != null;
	}
	
	@Override public void onDeath(DamageSource par1DamageSource)
	{
		if(villageObj != null)
		{
			Entity var2 = par1DamageSource.getEntity();
			if(var2 != null)
			{
				if(var2 instanceof EntityPlayer)
				{
					villageObj.setReputationForPlayer(((EntityPlayer) var2).getCommandSenderName(), -2);
				} else if(var2 instanceof IMob)
				{
					villageObj.endMatingSeason();
				}
			} else if(var2 == null)
			{
				EntityPlayer var3 = worldObj.getClosestPlayerToEntity(this, 16.0D);
				if(var3 != null)
				{
					villageObj.endMatingSeason();
				}
			}
		}
		super.onDeath(par1DamageSource);
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setProfession(par1NBTTagCompound.getInteger("Profession"));
		wealth = par1NBTTagCompound.getInteger("Riches");
		if(par1NBTTagCompound.hasKey("Offers"))
		{
			NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Offers");
			buyingList = new MerchantRecipeList(var2);
		}
	}
	
	@Override public void setCustomer(EntityPlayer par1EntityPlayer)
	{
		buyingPlayer = par1EntityPlayer;
	}
	
	public void setMating(boolean par1)
	{
		isMating = par1;
	}
	
	public void setPlaying(boolean par1)
	{
		isPlaying = par1;
	}
	
	public void setProfession(int par1)
	{
		dataWatcher.updateObject(16, Integer.valueOf(par1));
	}
	
	@Override public void setRecipes(MerchantRecipeList par1MerchantRecipeList)
	{
	}
	
	@Override public void setRevengeTarget(EntityLiving par1EntityLiving)
	{
		super.setRevengeTarget(par1EntityLiving);
		if(villageObj != null && par1EntityLiving != null)
		{
			villageObj.addOrRenewAgressor(par1EntityLiving);
			if(par1EntityLiving instanceof EntityPlayer)
			{
				byte var2 = -1;
				if(isChild())
				{
					var2 = -3;
				}
				villageObj.setReputationForPlayer(((EntityPlayer) par1EntityLiving).getCommandSenderName(), var2);
				if(isEntityAlive())
				{
					worldObj.setEntityState(this, (byte) 13);
				}
			}
		}
	}
	
	@Override protected void updateAITick()
	{
		if(--randomTickDivider <= 0)
		{
			worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
			randomTickDivider = 70 + rand.nextInt(50);
			villageObj = worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ), 32);
			if(villageObj == null)
			{
				detachHome();
			} else
			{
				ChunkCoordinates var1 = villageObj.getCenter();
				setHomeArea(var1.posX, var1.posY, var1.posZ, (int) (villageObj.getVillageRadius() * 0.6F));
				if(field_82190_bM)
				{
					field_82190_bM = false;
					villageObj.func_82683_b(5);
				}
			}
		}
		if(!isTrading() && timeUntilReset > 0)
		{
			--timeUntilReset;
			if(timeUntilReset <= 0)
			{
				if(needsInitilization)
				{
					if(buyingList.size() > 1)
					{
						Iterator var3 = buyingList.iterator();
						while(var3.hasNext())
						{
							MerchantRecipe var2 = (MerchantRecipe) var3.next();
							if(var2.func_82784_g())
							{
								var2.func_82783_a(rand.nextInt(6) + rand.nextInt(6) + 2);
							}
						}
					}
					addDefaultEquipmentAndRecipies(1);
					needsInitilization = false;
					if(villageObj != null && lastBuyingPlayer != null)
					{
						worldObj.setEntityState(this, (byte) 14);
						villageObj.setReputationForPlayer(lastBuyingPlayer, 1);
					}
				}
				addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
			}
		}
		super.updateAITick();
	}
	
	@Override public void useRecipe(MerchantRecipe par1MerchantRecipe)
	{
		par1MerchantRecipe.incrementToolUses();
		if(par1MerchantRecipe.hasSameIDsAs((MerchantRecipe) buyingList.get(buyingList.size() - 1)))
		{
			timeUntilReset = 40;
			needsInitilization = true;
			if(buyingPlayer != null)
			{
				lastBuyingPlayer = buyingPlayer.getCommandSenderName();
			} else
			{
				lastBuyingPlayer = null;
			}
		}
		if(par1MerchantRecipe.getItemToBuy().itemID == Item.emerald.itemID)
		{
			wealth += par1MerchantRecipe.getItemToBuy().stackSize;
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Profession", getProfession());
		par1NBTTagCompound.setInteger("Riches", wealth);
		if(buyingList != null)
		{
			par1NBTTagCompound.setCompoundTag("Offers", buyingList.getRecipiesAsTags());
		}
	}
	
	private static void addBlacksmithItem(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3)
	{
		if(par2Random.nextFloat() < par3)
		{
			int var4 = getRandomCountForBlacksmithItem(par1, par2Random);
			ItemStack var5;
			ItemStack var6;
			if(var4 < 0)
			{
				var5 = new ItemStack(Item.emerald.itemID, 1, 0);
				var6 = new ItemStack(par1, -var4, 0);
			} else
			{
				var5 = new ItemStack(Item.emerald.itemID, var4, 0);
				var6 = new ItemStack(par1, 1, 0);
			}
			par0MerchantRecipeList.add(new MerchantRecipe(var5, var6));
		}
	}
	
	private static void addMerchantItem(MerchantRecipeList par0MerchantRecipeList, int par1, Random par2Random, float par3)
	{
		if(par2Random.nextFloat() < par3)
		{
			par0MerchantRecipeList.add(new MerchantRecipe(getRandomSizedStack(par1, par2Random), Item.emerald));
		}
	}
	
	private static int getRandomCountForBlacksmithItem(int par0, Random par1Random)
	{
		Tuple var2 = (Tuple) blacksmithSellingList.get(Integer.valueOf(par0));
		return var2 == null ? 1 : ((Integer) var2.getFirst()).intValue() >= ((Integer) var2.getSecond()).intValue() ? ((Integer) var2.getFirst()).intValue() : ((Integer) var2.getFirst()).intValue() + par1Random.nextInt(((Integer) var2.getSecond()).intValue() - ((Integer) var2.getFirst()).intValue());
	}
	
	private static int getRandomCountForItem(int par0, Random par1Random)
	{
		Tuple var2 = (Tuple) villagerStockList.get(Integer.valueOf(par0));
		return var2 == null ? 1 : ((Integer) var2.getFirst()).intValue() >= ((Integer) var2.getSecond()).intValue() ? ((Integer) var2.getFirst()).intValue() : ((Integer) var2.getFirst()).intValue() + par1Random.nextInt(((Integer) var2.getSecond()).intValue() - ((Integer) var2.getFirst()).intValue());
	}
	
	private static ItemStack getRandomSizedStack(int par0, Random par1Random)
	{
		return new ItemStack(par0, getRandomCountForItem(par0, par1Random), 0);
	}
	
	static
	{
		villagerStockList.put(Integer.valueOf(Item.coal.itemID), new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
		villagerStockList.put(Integer.valueOf(Item.ingotIron.itemID), new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		villagerStockList.put(Integer.valueOf(Item.ingotGold.itemID), new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		villagerStockList.put(Integer.valueOf(Item.diamond.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		villagerStockList.put(Integer.valueOf(Item.paper.itemID), new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
		villagerStockList.put(Integer.valueOf(Item.book.itemID), new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
		villagerStockList.put(Integer.valueOf(Item.writtenBook.itemID), new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
		villagerStockList.put(Integer.valueOf(Item.enderPearl.itemID), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		villagerStockList.put(Integer.valueOf(Item.eyeOfEnder.itemID), new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
		villagerStockList.put(Integer.valueOf(Item.porkRaw.itemID), new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		villagerStockList.put(Integer.valueOf(Item.beefRaw.itemID), new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		villagerStockList.put(Integer.valueOf(Item.chickenRaw.itemID), new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		villagerStockList.put(Integer.valueOf(Item.fishCooked.itemID), new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
		villagerStockList.put(Integer.valueOf(Item.seeds.itemID), new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
		villagerStockList.put(Integer.valueOf(Item.melonSeeds.itemID), new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
		villagerStockList.put(Integer.valueOf(Item.pumpkinSeeds.itemID), new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
		villagerStockList.put(Integer.valueOf(Item.wheat.itemID), new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
		villagerStockList.put(Integer.valueOf(Block.cloth.blockID), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
		villagerStockList.put(Integer.valueOf(Item.rottenFlesh.itemID), new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
		blacksmithSellingList.put(Integer.valueOf(Item.flintAndSteel.itemID), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		blacksmithSellingList.put(Integer.valueOf(Item.shears.itemID), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		blacksmithSellingList.put(Integer.valueOf(Item.swordIron.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
		blacksmithSellingList.put(Integer.valueOf(Item.swordDiamond.itemID), new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
		blacksmithSellingList.put(Integer.valueOf(Item.axeIron.itemID), new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
		blacksmithSellingList.put(Integer.valueOf(Item.axeDiamond.itemID), new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
		blacksmithSellingList.put(Integer.valueOf(Item.pickaxeIron.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
		blacksmithSellingList.put(Integer.valueOf(Item.pickaxeDiamond.itemID), new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		blacksmithSellingList.put(Integer.valueOf(Item.shovelIron.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		blacksmithSellingList.put(Integer.valueOf(Item.shovelDiamond.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		blacksmithSellingList.put(Integer.valueOf(Item.hoeIron.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		blacksmithSellingList.put(Integer.valueOf(Item.hoeDiamond.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		blacksmithSellingList.put(Integer.valueOf(Item.bootsIron.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		blacksmithSellingList.put(Integer.valueOf(Item.bootsDiamond.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		blacksmithSellingList.put(Integer.valueOf(Item.helmetIron.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		blacksmithSellingList.put(Integer.valueOf(Item.helmetDiamond.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		blacksmithSellingList.put(Integer.valueOf(Item.plateIron.itemID), new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
		blacksmithSellingList.put(Integer.valueOf(Item.plateDiamond.itemID), new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
		blacksmithSellingList.put(Integer.valueOf(Item.legsIron.itemID), new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		blacksmithSellingList.put(Integer.valueOf(Item.legsDiamond.itemID), new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
		blacksmithSellingList.put(Integer.valueOf(Item.bootsChain.itemID), new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
		blacksmithSellingList.put(Integer.valueOf(Item.helmetChain.itemID), new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
		blacksmithSellingList.put(Integer.valueOf(Item.plateChain.itemID), new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
		blacksmithSellingList.put(Integer.valueOf(Item.legsChain.itemID), new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
		blacksmithSellingList.put(Integer.valueOf(Item.bread.itemID), new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
		blacksmithSellingList.put(Integer.valueOf(Item.melon.itemID), new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
		blacksmithSellingList.put(Integer.valueOf(Item.appleRed.itemID), new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
		blacksmithSellingList.put(Integer.valueOf(Item.cookie.itemID), new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
		blacksmithSellingList.put(Integer.valueOf(Block.glass.blockID), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
		blacksmithSellingList.put(Integer.valueOf(Block.bookShelf.blockID), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		blacksmithSellingList.put(Integer.valueOf(Item.plateLeather.itemID), new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
		blacksmithSellingList.put(Integer.valueOf(Item.bootsLeather.itemID), new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		blacksmithSellingList.put(Integer.valueOf(Item.helmetLeather.itemID), new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		blacksmithSellingList.put(Integer.valueOf(Item.legsLeather.itemID), new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		blacksmithSellingList.put(Integer.valueOf(Item.saddle.itemID), new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
		blacksmithSellingList.put(Integer.valueOf(Item.expBottle.itemID), new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
		blacksmithSellingList.put(Integer.valueOf(Item.redstone.itemID), new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
		blacksmithSellingList.put(Integer.valueOf(Item.compass.itemID), new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		blacksmithSellingList.put(Integer.valueOf(Item.pocketSundial.itemID), new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		blacksmithSellingList.put(Integer.valueOf(Block.glowStone.blockID), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
		blacksmithSellingList.put(Integer.valueOf(Item.porkCooked.itemID), new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
		blacksmithSellingList.put(Integer.valueOf(Item.beefCooked.itemID), new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
		blacksmithSellingList.put(Integer.valueOf(Item.chickenCooked.itemID), new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
		blacksmithSellingList.put(Integer.valueOf(Item.eyeOfEnder.itemID), new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
		blacksmithSellingList.put(Integer.valueOf(Item.arrow.itemID), new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
	}
}
