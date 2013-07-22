package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
	private static final IEntitySelector field_110276_bu = new EntityHorseBredSelector();
	private static final Attribute field_110271_bv = new RangedAttribute("horse.jumpStrength", 0.7D, 0.0D, 2.0D).func_111117_a("Jump Strength").func_111112_a(true);
	private static final String[] field_110270_bw = new String[] { null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png" };
	private static final String[] field_110273_bx = new String[] { "", "meo", "goo", "dio" };
	private static final int[] field_110272_by = new int[] { 0, 5, 7, 11 };
	private static final String[] field_110268_bz = new String[] { "textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png" };
	private static final String[] field_110269_bA = new String[] { "hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb" };
	private static final String[] field_110291_bB = new String[] { null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png" };
	private static final String[] field_110292_bC = new String[] { "", "wo_", "wmo", "wdo", "bdo" };
	private int field_110289_bD;
	private int field_110290_bE;
	private int field_110295_bF;
	public int field_110278_bp;
	public int field_110279_bq;
	protected boolean field_110275_br;
	private AnimalChest field_110296_bG;
	private boolean field_110293_bH;
	protected int field_110274_bs;
	protected float field_110277_bt;
	private boolean field_110294_bI;
	private float field_110283_bJ;
	private float field_110284_bK;
	private float field_110281_bL;
	private float field_110282_bM;
	private float field_110287_bN;
	private float field_110288_bO;
	private int field_110285_bP;
	private String field_110286_bQ;
	private String[] field_110280_bR = new String[3];
	
	public EntityHorse(World par1World)
	{
		super(par1World);
		setSize(1.4F, 1.6F);
		isImmuneToFire = false;
		func_110207_m(false);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.2D));
		tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
		tasks.addTask(6, new EntityAIWander(this, 0.7D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		func_110226_cD();
	}
	
	@Override public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		Entity var3 = par1DamageSource.getEntity();
		return riddenByEntity != null && riddenByEntity.equals(var3) ? false : super.attackEntityFrom(par1DamageSource, par2);
	}
	
	@Override public boolean canBePushed()
	{
		return riddenByEntity == null;
	}
	
	@Override public boolean canMateWith(EntityAnimal par1EntityAnimal)
	{
		if(par1EntityAnimal == this) return false;
		else if(par1EntityAnimal.getClass() != this.getClass()) return false;
		else
		{
			EntityHorse var2 = (EntityHorse) par1EntityAnimal;
			if(func_110200_cJ() && var2.func_110200_cJ())
			{
				int var3 = func_110265_bP();
				int var4 = var2.func_110265_bP();
				return var3 == var4 || var3 == 0 && var4 == 1 || var3 == 1 && var4 == 0;
			} else return false;
		}
	}
	
	@Override public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
	{
		EntityHorse var2 = (EntityHorse) par1EntityAgeable;
		EntityHorse var3 = new EntityHorse(worldObj);
		int var4 = func_110265_bP();
		int var5 = var2.func_110265_bP();
		int var6 = 0;
		if(var4 == var5)
		{
			var6 = var4;
		} else if(var4 == 0 && var5 == 1 || var4 == 1 && var5 == 0)
		{
			var6 = 2;
		}
		if(var6 == 0)
		{
			int var8 = rand.nextInt(9);
			int var7;
			if(var8 < 4)
			{
				var7 = func_110202_bQ() & 255;
			} else if(var8 < 8)
			{
				var7 = var2.func_110202_bQ() & 255;
			} else
			{
				var7 = rand.nextInt(7);
			}
			int var9 = rand.nextInt(5);
			if(var9 < 4)
			{
				var7 |= func_110202_bQ() & 65280;
			} else if(var9 < 8)
			{
				var7 |= var2.func_110202_bQ() & 65280;
			} else
			{
				var7 |= rand.nextInt(5) << 8 & 65280;
			}
			var3.func_110235_q(var7);
		}
		var3.func_110214_p(var6);
		double var14 = func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() + par1EntityAgeable.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() + func_110267_cL();
		var3.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(var14 / 3.0D);
		double var13 = func_110148_a(field_110271_bv).func_111125_b() + par1EntityAgeable.func_110148_a(field_110271_bv).func_111125_b() + func_110245_cM();
		var3.func_110148_a(field_110271_bv).func_111128_a(var13 / 3.0D);
		double var11 = func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() + par1EntityAgeable.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() + func_110203_cN();
		var3.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(var11 / 3.0D);
		return var3;
	}
	
	@Override protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Integer.valueOf(0));
		dataWatcher.addObject(19, Byte.valueOf((byte) 0));
		dataWatcher.addObject(20, Integer.valueOf(0));
		dataWatcher.addObject(21, String.valueOf(""));
		dataWatcher.addObject(22, Integer.valueOf(0));
	}
	
	@Override protected void fall(float par1)
	{
		if(par1 > 1.0F)
		{
			playSound("mob.horse.land", 0.4F, 1.0F);
		}
		int var2 = MathHelper.ceiling_float_int(par1 * 0.5F - 3.0F);
		if(var2 > 0)
		{
			attackEntityFrom(DamageSource.fall, var2);
			if(riddenByEntity != null)
			{
				riddenByEntity.attackEntityFrom(DamageSource.fall, var2);
			}
			int var3 = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.2D - prevRotationYaw), MathHelper.floor_double(posZ));
			if(var3 > 0)
			{
				StepSound var4 = Block.blocksList[var3].stepSound;
				worldObj.playSoundAtEntity(this, var4.getStepSound(), var4.getVolume() * 0.5F, var4.getPitch() * 0.75F);
			}
		}
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110140_aT().func_111150_b(field_110271_bv);
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(53.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.22499999403953552D);
	}
	
	@Override public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
	{
		Object par1EntityLivingData1 = super.func_110161_a(par1EntityLivingData);
		boolean var2 = false;
		int var3 = 0;
		int var7;
		if(par1EntityLivingData1 instanceof EntityHorseGroupData)
		{
			var7 = ((EntityHorseGroupData) par1EntityLivingData1).field_111107_a;
			var3 = ((EntityHorseGroupData) par1EntityLivingData1).field_111106_b & 255 | rand.nextInt(5) << 8;
		} else
		{
			if(rand.nextInt(10) == 0)
			{
				var7 = 1;
			} else
			{
				int var4 = rand.nextInt(7);
				int var5 = rand.nextInt(5);
				var7 = 0;
				var3 = var4 | var5 << 8;
			}
			par1EntityLivingData1 = new EntityHorseGroupData(var7, var3);
		}
		func_110214_p(var7);
		func_110235_q(var3);
		if(rand.nextInt(5) == 0)
		{
			setGrowingAge(-24000);
		}
		if(var7 != 4 && var7 != 3)
		{
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(func_110267_cL());
			if(var7 == 0)
			{
				func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(func_110203_cN());
			} else
			{
				func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.17499999701976776D);
			}
		} else
		{
			func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(15.0D);
			func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
		}
		if(var7 != 2 && var7 != 1)
		{
			func_110148_a(field_110271_bv).func_111128_a(func_110245_cM());
		} else
		{
			func_110148_a(field_110271_bv).func_111128_a(0.5D);
		}
		setEntityHealth(func_110138_aP());
		return (EntityLivingData) par1EntityLivingData1;
	}
	
	@Override public boolean func_110164_bC()
	{
		return !func_110256_cu() && super.func_110164_bC();
	}
	
	public int func_110198_t(int par1)
	{
		int var2 = MathHelper.clamp_int(func_110252_cg() + par1, 0, func_110218_cm());
		func_110238_s(var2);
		return var2;
	}
	
	public void func_110199_f(EntityPlayer par1EntityPlayer)
	{
		if(!worldObj.isRemote && (riddenByEntity == null || riddenByEntity == par1EntityPlayer) && func_110248_bS())
		{
			field_110296_bG.func_110133_a(getEntityName());
			par1EntityPlayer.func_110298_a(this, field_110296_bG);
		}
	}
	
	private boolean func_110200_cJ()
	{
		return riddenByEntity == null && ridingEntity == null && func_110248_bS() && func_110228_bR() && !func_110222_cv() && func_110143_aJ() >= func_110138_aP();
	}
	
	public float func_110201_q(float par1)
	{
		return field_110288_bO + (field_110287_bN - field_110288_bO) * par1;
	}
	
	public int func_110202_bQ()
	{
		return dataWatcher.getWatchableObjectInt(20);
	}
	
	private double func_110203_cN()
	{
		return (0.44999998807907104D + rand.nextDouble() * 0.3D + rand.nextDouble() * 0.3D + rand.nextDouble() * 0.3D) * 0.25D;
	}
	
	public boolean func_110204_cc()
	{
		return func_110233_w(32);
	}
	
	public boolean func_110205_ce()
	{
		return func_110233_w(16);
	}
	
	public void func_110206_u(int par1)
	{
		if(func_110257_ck())
		{
			if(par1 < 0)
			{
				par1 = 0;
			} else
			{
				field_110294_bI = true;
				func_110220_cK();
			}
			if(par1 >= 90)
			{
				field_110277_bt = 1.0F;
			} else
			{
				field_110277_bt = 0.4F + 0.4F * par1 / 90.0F;
			}
		}
	}
	
	public void func_110207_m(boolean par1)
	{
		func_110208_b(8, par1);
	}
	
	private void func_110208_b(int par1, boolean par2)
	{
		int var3 = dataWatcher.getWatchableObjectInt(16);
		if(par2)
		{
			dataWatcher.updateObject(16, Integer.valueOf(var3 | par1));
		} else
		{
			dataWatcher.updateObject(16, Integer.valueOf(var3 & ~par1));
		}
	}
	
	public boolean func_110209_cd()
	{
		return func_110233_w(64);
	}
	
	private void func_110210_cH()
	{
		field_110278_bp = 1;
	}
	
	public String[] func_110212_cp()
	{
		if(field_110286_bQ == null)
		{
			func_110247_cG();
		}
		return field_110280_bR;
	}
	
	public void func_110213_b(String par1Str)
	{
		dataWatcher.updateObject(21, par1Str);
	}
	
	public void func_110214_p(int par1)
	{
		dataWatcher.updateObject(19, Byte.valueOf((byte) par1));
		func_110230_cF();
	}
	
	public double func_110215_cj()
	{
		return func_110148_a(field_110271_bv).func_111126_e();
	}
	
	protected void func_110216_r(boolean par1)
	{
		String var2 = par1 ? "heart" : "smoke";
		for(int var3 = 0; var3 < 7; ++var3)
		{
			double var4 = rand.nextGaussian() * 0.02D;
			double var6 = rand.nextGaussian() * 0.02D;
			double var8 = rand.nextGaussian() * 0.02D;
			worldObj.spawnParticle(var2, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var4, var6, var8);
		}
	}
	
	protected String func_110217_cl()
	{
		func_110249_cI();
		func_110220_cK();
		int var1 = func_110265_bP();
		return var1 != 3 && var1 != 4 ? var1 != 1 && var1 != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry" : null;
	}
	
	public int func_110218_cm()
	{
		return 100;
	}
	
	public void func_110219_q(boolean par1)
	{
		if(par1)
		{
			func_110227_p(false);
		}
		func_110208_b(64, par1);
	}
	
	private void func_110220_cK()
	{
		if(!worldObj.isRemote)
		{
			field_110295_bF = 1;
			func_110219_q(true);
		}
	}
	
	public void func_110221_n(boolean par1)
	{
		field_110293_bH = par1;
	}
	
	public boolean func_110222_cv()
	{
		return func_110256_cu() || func_110265_bP() == 2;
	}
	
	public float func_110223_p(float par1)
	{
		return field_110282_bM + (field_110281_bL - field_110282_bM) * par1;
	}
	
	public void func_110224_ci()
	{
		if(!worldObj.isRemote && func_110261_ca())
		{
			dropItem(Block.chest.blockID, 1);
			func_110207_m(false);
		}
	}
	
	private int func_110225_cC()
	{
		int var1 = func_110265_bP();
		return func_110261_ca() && (var1 == 1 || var1 == 2) ? 17 : 2;
	}
	
	private void func_110226_cD()
	{
		AnimalChest var1 = field_110296_bG;
		field_110296_bG = new AnimalChest("HorseChest", func_110225_cC());
		field_110296_bG.func_110133_a(getEntityName());
		if(var1 != null)
		{
			var1.func_110132_b(this);
			int var2 = Math.min(var1.getSizeInventory(), field_110296_bG.getSizeInventory());
			for(int var3 = 0; var3 < var2; ++var3)
			{
				ItemStack var4 = var1.getStackInSlot(var3);
				if(var4 != null)
				{
					field_110296_bG.setInventorySlotContents(var3, var4.copy());
				}
			}
			var1 = null;
		}
		field_110296_bG.func_110134_a(this);
		func_110232_cE();
	}
	
	public void func_110227_p(boolean par1)
	{
		setEating(par1);
	}
	
	public boolean func_110228_bR()
	{
		return !isChild();
	}
	
	public boolean func_110229_cs()
	{
		int var1 = func_110265_bP();
		return var1 == 2 || var1 == 1;
	}
	
	private void func_110230_cF()
	{
		field_110286_bQ = null;
	}
	
	public void func_110231_cz()
	{
		func_110220_cK();
		String var1 = func_110217_cl();
		if(var1 != null)
		{
			playSound(var1, getSoundVolume(), getSoundPitch());
		}
	}
	
	private void func_110232_cE()
	{
		if(!worldObj.isRemote)
		{
			func_110251_o(field_110296_bG.getStackInSlot(0) != null);
			if(func_110259_cr())
			{
				func_110236_r(func_110260_d(field_110296_bG.getStackInSlot(1)));
			}
		}
	}
	
	private boolean func_110233_w(int par1)
	{
		return (dataWatcher.getWatchableObjectInt(16) & par1) != 0;
	}
	
	public void func_110234_j(boolean par1)
	{
		func_110208_b(2, par1);
	}
	
	public void func_110235_q(int par1)
	{
		dataWatcher.updateObject(20, Integer.valueOf(par1));
		func_110230_cF();
	}
	
	public void func_110236_r(int par1)
	{
		dataWatcher.updateObject(22, Integer.valueOf(par1));
		func_110230_cF();
	}
	
	private void func_110237_h(EntityPlayer par1EntityPlayer)
	{
		par1EntityPlayer.rotationYaw = rotationYaw;
		par1EntityPlayer.rotationPitch = rotationPitch;
		func_110227_p(false);
		func_110219_q(false);
		if(!worldObj.isRemote)
		{
			par1EntityPlayer.mountEntity(this);
		}
	}
	
	public void func_110238_s(int par1)
	{
		field_110274_bs = par1;
	}
	
	public boolean func_110239_cn()
	{
		return func_110265_bP() == 0 || func_110241_cb() > 0;
	}
	
	private void func_110240_a(Entity par1Entity, AnimalChest par2AnimalChest)
	{
		if(par2AnimalChest != null && !worldObj.isRemote)
		{
			for(int var3 = 0; var3 < par2AnimalChest.getSizeInventory(); ++var3)
			{
				ItemStack var4 = par2AnimalChest.getStackInSlot(var3);
				if(var4 != null)
				{
					entityDropItem(var4, 0.0F);
				}
			}
		}
	}
	
	public int func_110241_cb()
	{
		return dataWatcher.getWatchableObjectInt(22);
	}
	
	public void func_110242_l(boolean par1)
	{
		func_110208_b(16, par1);
	}
	
	public boolean func_110243_cf()
	{
		return field_110293_bH;
	}
	
	public void func_110244_cA()
	{
		func_110240_a(this, field_110296_bG);
		func_110224_ci();
	}
	
	private double func_110245_cM()
	{
		return 0.4000000059604645D + rand.nextDouble() * 0.2D + rand.nextDouble() * 0.2D + rand.nextDouble() * 0.2D;
	}
	
	public boolean func_110246_bZ()
	{
		return field_110275_br;
	}
	
	private void func_110247_cG()
	{
		field_110286_bQ = "horse/";
		field_110280_bR[0] = null;
		field_110280_bR[1] = null;
		field_110280_bR[2] = null;
		int var1 = func_110265_bP();
		int var2 = func_110202_bQ();
		int var3;
		if(var1 == 0)
		{
			var3 = var2 & 255;
			int var4 = (var2 & 65280) >> 8;
			field_110280_bR[0] = field_110268_bz[var3];
			field_110286_bQ = field_110286_bQ + field_110269_bA[var3];
			field_110280_bR[1] = field_110291_bB[var4];
			field_110286_bQ = field_110286_bQ + field_110292_bC[var4];
		} else
		{
			field_110280_bR[0] = "";
			field_110286_bQ = field_110286_bQ + "_" + var1 + "_";
		}
		var3 = func_110241_cb();
		field_110280_bR[2] = field_110270_bw[var3];
		field_110286_bQ = field_110286_bQ + field_110273_bx[var3];
	}
	
	public boolean func_110248_bS()
	{
		return func_110233_w(2);
	}
	
	private void func_110249_cI()
	{
		if(!worldObj.isRemote)
		{
			field_110290_bE = 1;
			func_110208_b(128, true);
		}
	}
	
	protected EntityHorse func_110250_a(Entity par1Entity, double par2)
	{
		double var4 = Double.MAX_VALUE;
		Entity var6 = null;
		List var7 = worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par1Entity.boundingBox.addCoord(par2, par2, par2), field_110276_bu);
		Iterator var8 = var7.iterator();
		while(var8.hasNext())
		{
			Entity var9 = (Entity) var8.next();
			double var10 = var9.getDistanceSq(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
			if(var10 < var4)
			{
				var6 = var9;
				var4 = var10;
			}
		}
		return (EntityHorse) var6;
	}
	
	public void func_110251_o(boolean par1)
	{
		func_110208_b(4, par1);
	}
	
	public int func_110252_cg()
	{
		return field_110274_bs;
	}
	
	public boolean func_110253_bW()
	{
		return func_110228_bR();
	}
	
	public float func_110254_bY()
	{
		int var1 = getGrowingAge();
		return var1 >= 0 ? 1.0F : 0.5F + (-24000 - var1) / -24000.0F * 0.5F;
	}
	
	public void func_110255_k(boolean par1)
	{
		field_110275_br = par1;
	}
	
	public boolean func_110256_cu()
	{
		int var1 = func_110265_bP();
		return var1 == 3 || var1 == 4;
	}
	
	public boolean func_110257_ck()
	{
		return func_110233_w(4);
	}
	
	public float func_110258_o(float par1)
	{
		return field_110284_bK + (field_110283_bJ - field_110284_bK) * par1;
	}
	
	public boolean func_110259_cr()
	{
		return func_110265_bP() == 0;
	}
	
	public int func_110260_d(ItemStack par1ItemStack)
	{
		return par1ItemStack == null ? 0 : par1ItemStack.itemID == Item.field_111215_ce.itemID ? 1 : par1ItemStack.itemID == Item.field_111216_cf.itemID ? 2 : par1ItemStack.itemID == Item.field_111213_cg.itemID ? 3 : 0;
	}
	
	public boolean func_110261_ca()
	{
		return func_110233_w(8);
	}
	
	public boolean func_110262_ch()
	{
		int var1 = MathHelper.floor_double(posX);
		int var2 = MathHelper.floor_double(posZ);
		worldObj.getBiomeGenForCoords(var1, var2);
		return true;
	}
	
	public boolean func_110263_g(EntityPlayer par1EntityPlayer)
	{
		func_110213_b(par1EntityPlayer.getCommandSenderName());
		func_110234_j(true);
		return true;
	}
	
	public String func_110264_co()
	{
		if(field_110286_bQ == null)
		{
			func_110247_cG();
		}
		return field_110286_bQ;
	}
	
	public int func_110265_bP()
	{
		return dataWatcher.getWatchableObjectByte(19);
	}
	
	private void func_110266_cB()
	{
		func_110249_cI();
		worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.2F);
	}
	
	private float func_110267_cL()
	{
		return 15.0F + rand.nextInt(8) + rand.nextInt(9);
	}
	
	@Override protected void func_142017_o(float par1)
	{
		if(par1 > 6.0F && func_110204_cc())
		{
			func_110227_p(false);
		}
	}
	
	public String func_142019_cb()
	{
		return dataWatcher.getWatchableObjectString(21);
	}
	
	@Override public void func_98054_a(boolean par1)
	{
		if(par1)
		{
			func_98055_j(func_110254_bY());
		} else
		{
			func_98055_j(1.0F);
		}
	}
	
	@Override public boolean getCanSpawnHere()
	{
		func_110262_ch();
		return super.getCanSpawnHere();
	}
	
	@Override protected String getDeathSound()
	{
		func_110249_cI();
		int var1 = func_110265_bP();
		return var1 == 3 ? "mob.horse.zombie.death" : var1 == 4 ? "mob.horse.skeleton.death" : var1 != 1 && var1 != 2 ? "mob.horse.death" : "mob.horse.donkey.death";
	}
	
	@Override protected int getDropItemId()
	{
		boolean var1 = rand.nextInt(4) == 0;
		int var2 = func_110265_bP();
		return var2 == 4 ? Item.bone.itemID : var2 == 3 ? var1 ? 0 : Item.rottenFlesh.itemID : Item.leather.itemID;
	}
	
	@Override public String getEntityName()
	{
		if(hasCustomNameTag()) return getCustomNameTag();
		else
		{
			int var1 = func_110265_bP();
			switch(var1)
			{
				case 0:
				default:
					return StatCollector.translateToLocal("entity.horse.name");
				case 1:
					return StatCollector.translateToLocal("entity.donkey.name");
				case 2:
					return StatCollector.translateToLocal("entity.mule.name");
				case 3:
					return StatCollector.translateToLocal("entity.zombiehorse.name");
				case 4:
					return StatCollector.translateToLocal("entity.skeletonhorse.name");
			}
		}
	}
	
	@Override protected String getHurtSound()
	{
		func_110249_cI();
		if(rand.nextInt(3) == 0)
		{
			func_110220_cK();
		}
		int var1 = func_110265_bP();
		return var1 == 3 ? "mob.horse.zombie.hit" : var1 == 4 ? "mob.horse.skeleton.hit" : var1 != 1 && var1 != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit";
	}
	
	@Override protected String getLivingSound()
	{
		func_110249_cI();
		if(rand.nextInt(10) == 0 && !isMovementBlocked())
		{
			func_110220_cK();
		}
		int var1 = func_110265_bP();
		return var1 == 3 ? "mob.horse.zombie.idle" : var1 == 4 ? "mob.horse.skeleton.idle" : var1 != 1 && var1 != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle";
	}
	
	@Override public int getMaxSpawnedInChunk()
	{
		return 6;
	}
	
	@Override protected float getSoundVolume()
	{
		return 0.8F;
	}
	
	@Override public int getTalkInterval()
	{
		return 400;
	}
	
	@Override public int getTotalArmorValue()
	{
		return field_110272_by[func_110241_cb()];
	}
	
	@Override public void handleHealthUpdate(byte par1)
	{
		if(par1 == 7)
		{
			func_110216_r(true);
		} else if(par1 == 6)
		{
			func_110216_r(false);
		} else
		{
			super.handleHealthUpdate(par1);
		}
	}
	
	@Override public boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
		if(var2 != null && var2.itemID == Item.monsterPlacer.itemID) return super.interact(par1EntityPlayer);
		else if(!func_110248_bS() && func_110256_cu()) return false;
		else if(func_110248_bS() && func_110228_bR() && par1EntityPlayer.isSneaking())
		{
			func_110199_f(par1EntityPlayer);
			return true;
		} else if(func_110253_bW() && riddenByEntity != null) return super.interact(par1EntityPlayer);
		else
		{
			if(var2 != null)
			{
				boolean var3 = false;
				if(func_110259_cr())
				{
					byte var4 = -1;
					if(var2.itemID == Item.field_111215_ce.itemID)
					{
						var4 = 1;
					} else if(var2.itemID == Item.field_111216_cf.itemID)
					{
						var4 = 2;
					} else if(var2.itemID == Item.field_111213_cg.itemID)
					{
						var4 = 3;
					}
					if(var4 >= 0)
					{
						if(!func_110248_bS())
						{
							func_110231_cz();
							return true;
						}
						func_110199_f(par1EntityPlayer);
						return true;
					}
				}
				if(!var3 && !func_110256_cu())
				{
					float var7 = 0.0F;
					short var5 = 0;
					byte var6 = 0;
					if(var2.itemID == Item.wheat.itemID)
					{
						var7 = 2.0F;
						var5 = 60;
						var6 = 3;
					} else if(var2.itemID == Item.sugar.itemID)
					{
						var7 = 1.0F;
						var5 = 30;
						var6 = 3;
					} else if(var2.itemID == Item.bread.itemID)
					{
						var7 = 7.0F;
						var5 = 180;
						var6 = 3;
					} else if(var2.itemID == Block.field_111038_cB.blockID)
					{
						var7 = 20.0F;
						var5 = 180;
					} else if(var2.itemID == Item.appleRed.itemID)
					{
						var7 = 3.0F;
						var5 = 60;
						var6 = 3;
					} else if(var2.itemID == Item.goldenCarrot.itemID)
					{
						var7 = 4.0F;
						var5 = 60;
						var6 = 5;
						if(func_110248_bS() && getGrowingAge() == 0)
						{
							var3 = true;
							func_110196_bT();
						}
					} else if(var2.itemID == Item.appleGold.itemID)
					{
						var7 = 10.0F;
						var5 = 240;
						var6 = 10;
						if(func_110248_bS() && getGrowingAge() == 0)
						{
							var3 = true;
							func_110196_bT();
						}
					}
					if(func_110143_aJ() < func_110138_aP() && var7 > 0.0F)
					{
						heal(var7);
						var3 = true;
					}
					if(!func_110228_bR() && var5 > 0)
					{
						func_110195_a(var5);
						var3 = true;
					}
					if(var6 > 0 && (var3 || !func_110248_bS()) && var6 < func_110218_cm())
					{
						var3 = true;
						func_110198_t(var6);
					}
					if(var3)
					{
						func_110266_cB();
					}
				}
				if(!func_110248_bS() && !var3)
				{
					if(var2 != null && var2.func_111282_a(par1EntityPlayer, this)) return true;
					func_110231_cz();
					return true;
				}
				if(!var3 && func_110229_cs() && !func_110261_ca() && var2.itemID == Block.chest.blockID)
				{
					func_110207_m(true);
					playSound("mob.chickenplop", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
					var3 = true;
					func_110226_cD();
				}
				if(!var3 && func_110253_bW() && !func_110257_ck() && var2.itemID == Item.saddle.itemID)
				{
					func_110199_f(par1EntityPlayer);
					return true;
				}
				if(var3)
				{
					if(!par1EntityPlayer.capabilities.isCreativeMode && --var2.stackSize == 0)
					{
						par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack) null);
					}
					return true;
				}
			}
			if(func_110253_bW() && riddenByEntity == null)
			{
				if(var2 != null && var2.func_111282_a(par1EntityPlayer, this)) return true;
				else
				{
					func_110237_h(par1EntityPlayer);
					return true;
				}
			} else return super.interact(par1EntityPlayer);
		}
	}
	
	@Override protected boolean isAIEnabled()
	{
		return true;
	}
	
	@Override public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return false;
	}
	
	@Override protected boolean isMovementBlocked()
	{
		return riddenByEntity != null && func_110257_ck() ? true : func_110204_cc() || func_110209_cd();
	}
	
	@Override public boolean isOnLadder()
	{
		return false;
	}
	
	@Override public void moveEntityWithHeading(float par1, float par2)
	{
		if(riddenByEntity != null && func_110257_ck())
		{
			prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
			rotationPitch = riddenByEntity.rotationPitch * 0.5F;
			setRotation(rotationYaw, rotationPitch);
			rotationYawHead = renderYawOffset = rotationYaw;
			par1 = ((EntityLivingBase) riddenByEntity).moveStrafing * 0.5F;
			par2 = ((EntityLivingBase) riddenByEntity).moveForward;
			if(par2 <= 0.0F)
			{
				par2 *= 0.25F;
				field_110285_bP = 0;
			}
			if(onGround && field_110277_bt == 0.0F && func_110209_cd() && !field_110294_bI)
			{
				par1 = 0.0F;
				par2 = 0.0F;
			}
			if(field_110277_bt > 0.0F && !func_110246_bZ() && onGround)
			{
				motionY = func_110215_cj() * field_110277_bt;
				if(this.isPotionActive(Potion.jump))
				{
					motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
				}
				func_110255_k(true);
				isAirBorne = true;
				if(par2 > 0.0F)
				{
					float var3 = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F);
					float var4 = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F);
					motionX += -0.4F * var3 * field_110277_bt;
					motionZ += 0.4F * var4 * field_110277_bt;
					playSound("mob.horse.jump", 0.4F, 1.0F);
				}
				field_110277_bt = 0.0F;
			}
			stepHeight = 1.0F;
			jumpMovementFactor = getAIMoveSpeed() * 0.1F;
			if(!worldObj.isRemote)
			{
				setAIMoveSpeed((float) func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
				super.moveEntityWithHeading(par1, par2);
			}
			if(onGround)
			{
				field_110277_bt = 0.0F;
				func_110255_k(false);
			}
			prevLimbYaw = limbYaw;
			double var8 = posX - prevPosX;
			double var5 = posZ - prevPosZ;
			float var7 = MathHelper.sqrt_double(var8 * var8 + var5 * var5) * 4.0F;
			if(var7 > 1.0F)
			{
				var7 = 1.0F;
			}
			limbYaw += (var7 - limbYaw) * 0.4F;
			limbSwing += limbYaw;
		} else
		{
			stepHeight = 0.5F;
			jumpMovementFactor = 0.02F;
			super.moveEntityWithHeading(par1, par2);
		}
	}
	
	@Override public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);
		if(!worldObj.isRemote)
		{
			func_110244_cA();
		}
	}
	
	@Override public void onInventoryChanged(InventoryBasic par1InventoryBasic)
	{
		int var2 = func_110241_cb();
		boolean var3 = func_110257_ck();
		func_110232_cE();
		if(ticksExisted > 20)
		{
			if(var2 == 0 && var2 != func_110241_cb())
			{
				playSound("mob.horse.armor", 0.5F, 1.0F);
			}
			if(!var3 && func_110257_ck())
			{
				playSound("mob.horse.leather", 0.5F, 1.0F);
			}
		}
	}
	
	@Override public void onLivingUpdate()
	{
		if(rand.nextInt(200) == 0)
		{
			func_110210_cH();
		}
		super.onLivingUpdate();
		if(!worldObj.isRemote)
		{
			if(rand.nextInt(900) == 0 && deathTime == 0)
			{
				heal(1.0F);
			}
			if(!func_110204_cc() && riddenByEntity == null && rand.nextInt(300) == 0 && worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY) - 1, MathHelper.floor_double(posZ)) == Block.grass.blockID)
			{
				func_110227_p(true);
			}
			if(func_110204_cc() && ++field_110289_bD > 50)
			{
				field_110289_bD = 0;
				func_110227_p(false);
			}
			if(func_110205_ce() && !func_110228_bR() && !func_110204_cc())
			{
				EntityHorse var1 = func_110250_a(this, 16.0D);
				if(var1 != null && getDistanceSqToEntity(var1) > 4.0D)
				{
					PathEntity var2 = worldObj.getPathEntityToEntity(this, var1, 16.0F, true, false, false, true);
					setPathToEntity(var2);
				}
			}
		}
	}
	
	@Override public void onUpdate()
	{
		super.onUpdate();
		if(worldObj.isRemote && dataWatcher.hasChanges())
		{
			dataWatcher.func_111144_e();
			func_110230_cF();
		}
		if(field_110290_bE > 0 && ++field_110290_bE > 30)
		{
			field_110290_bE = 0;
			func_110208_b(128, false);
		}
		if(!worldObj.isRemote && field_110295_bF > 0 && ++field_110295_bF > 20)
		{
			field_110295_bF = 0;
			func_110219_q(false);
		}
		if(field_110278_bp > 0 && ++field_110278_bp > 8)
		{
			field_110278_bp = 0;
		}
		if(field_110279_bq > 0)
		{
			++field_110279_bq;
			if(field_110279_bq > 300)
			{
				field_110279_bq = 0;
			}
		}
		field_110284_bK = field_110283_bJ;
		if(func_110204_cc())
		{
			field_110283_bJ += (1.0F - field_110283_bJ) * 0.4F + 0.05F;
			if(field_110283_bJ > 1.0F)
			{
				field_110283_bJ = 1.0F;
			}
		} else
		{
			field_110283_bJ += (0.0F - field_110283_bJ) * 0.4F - 0.05F;
			if(field_110283_bJ < 0.0F)
			{
				field_110283_bJ = 0.0F;
			}
		}
		field_110282_bM = field_110281_bL;
		if(func_110209_cd())
		{
			field_110284_bK = field_110283_bJ = 0.0F;
			field_110281_bL += (1.0F - field_110281_bL) * 0.4F + 0.05F;
			if(field_110281_bL > 1.0F)
			{
				field_110281_bL = 1.0F;
			}
		} else
		{
			field_110294_bI = false;
			field_110281_bL += (0.8F * field_110281_bL * field_110281_bL * field_110281_bL - field_110281_bL) * 0.6F - 0.05F;
			if(field_110281_bL < 0.0F)
			{
				field_110281_bL = 0.0F;
			}
		}
		field_110288_bO = field_110287_bN;
		if(func_110233_w(128))
		{
			field_110287_bN += (1.0F - field_110287_bN) * 0.7F + 0.05F;
			if(field_110287_bN > 1.0F)
			{
				field_110287_bN = 1.0F;
			}
		} else
		{
			field_110287_bN += (0.0F - field_110287_bN) * 0.7F - 0.05F;
			if(field_110287_bN < 0.0F)
			{
				field_110287_bN = 0.0F;
			}
		}
	}
	
	@Override protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		StepSound var5 = Block.blocksList[par4].stepSound;
		if(worldObj.getBlockId(par1, par2 + 1, par3) == Block.snow.blockID)
		{
			var5 = Block.snow.stepSound;
		}
		if(!Block.blocksList[par4].blockMaterial.isLiquid())
		{
			int var6 = func_110265_bP();
			if(riddenByEntity != null && var6 != 1 && var6 != 2)
			{
				++field_110285_bP;
				if(field_110285_bP > 5 && field_110285_bP % 3 == 0)
				{
					playSound("mob.horse.gallop", var5.getVolume() * 0.15F, var5.getPitch());
					if(var6 == 0 && rand.nextInt(10) == 0)
					{
						playSound("mob.horse.breathe", var5.getVolume() * 0.6F, var5.getPitch());
					}
				} else if(field_110285_bP <= 5)
				{
					playSound("mob.horse.wood", var5.getVolume() * 0.15F, var5.getPitch());
				}
			} else if(var5 == Block.soundWoodFootstep)
			{
				playSound("mob.horse.soft", var5.getVolume() * 0.15F, var5.getPitch());
			} else
			{
				playSound("mob.horse.wood", var5.getVolume() * 0.15F, var5.getPitch());
			}
		}
	}
	
	@Override public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		func_110227_p(par1NBTTagCompound.getBoolean("EatingHaystack"));
		func_110242_l(par1NBTTagCompound.getBoolean("Bred"));
		func_110207_m(par1NBTTagCompound.getBoolean("ChestedHorse"));
		func_110221_n(par1NBTTagCompound.getBoolean("HasReproduced"));
		func_110214_p(par1NBTTagCompound.getInteger("Type"));
		func_110235_q(par1NBTTagCompound.getInteger("Variant"));
		func_110238_s(par1NBTTagCompound.getInteger("Temper"));
		func_110234_j(par1NBTTagCompound.getBoolean("Tame"));
		if(par1NBTTagCompound.hasKey("OwnerName"))
		{
			func_110213_b(par1NBTTagCompound.getString("OwnerName"));
		}
		AttributeInstance var2 = func_110140_aT().func_111152_a("Speed");
		if(var2 != null)
		{
			func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(var2.func_111125_b() * 0.25D);
		}
		if(func_110261_ca())
		{
			NBTTagList var3 = par1NBTTagCompound.getTagList("Items");
			func_110226_cD();
			for(int var4 = 0; var4 < var3.tagCount(); ++var4)
			{
				NBTTagCompound var5 = (NBTTagCompound) var3.tagAt(var4);
				int var6 = var5.getByte("Slot") & 255;
				if(var6 >= 2 && var6 < field_110296_bG.getSizeInventory())
				{
					field_110296_bG.setInventorySlotContents(var6, ItemStack.loadItemStackFromNBT(var5));
				}
			}
		}
		ItemStack var7;
		if(par1NBTTagCompound.hasKey("ArmorItem"))
		{
			var7 = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("ArmorItem"));
			if(var7 != null && func_110211_v(var7.itemID))
			{
				field_110296_bG.setInventorySlotContents(1, var7);
			}
		}
		if(par1NBTTagCompound.hasKey("SaddleItem"))
		{
			var7 = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("SaddleItem"));
			if(var7 != null && var7.itemID == Item.saddle.itemID)
			{
				field_110296_bG.setInventorySlotContents(0, var7);
			}
		} else if(par1NBTTagCompound.getBoolean("Saddle"))
		{
			field_110296_bG.setInventorySlotContents(0, new ItemStack(Item.saddle));
		}
		func_110232_cE();
	}
	
	@Override public void setEating(boolean par1)
	{
		func_110208_b(32, par1);
	}
	
	@Override public void updateRiderPosition()
	{
		super.updateRiderPosition();
		if(field_110282_bM > 0.0F)
		{
			float var1 = MathHelper.sin(renderYawOffset * (float) Math.PI / 180.0F);
			float var2 = MathHelper.cos(renderYawOffset * (float) Math.PI / 180.0F);
			float var3 = 0.7F * field_110282_bM;
			float var4 = 0.15F * field_110282_bM;
			riddenByEntity.setPosition(posX + var3 * var1, posY + getMountedYOffset() + riddenByEntity.getYOffset() + var4, posZ - var3 * var2);
			if(riddenByEntity instanceof EntityLivingBase)
			{
				((EntityLivingBase) riddenByEntity).renderYawOffset = renderYawOffset;
			}
		}
	}
	
	@Override public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setBoolean("EatingHaystack", func_110204_cc());
		par1NBTTagCompound.setBoolean("ChestedHorse", func_110261_ca());
		par1NBTTagCompound.setBoolean("HasReproduced", func_110243_cf());
		par1NBTTagCompound.setBoolean("Bred", func_110205_ce());
		par1NBTTagCompound.setInteger("Type", func_110265_bP());
		par1NBTTagCompound.setInteger("Variant", func_110202_bQ());
		par1NBTTagCompound.setInteger("Temper", func_110252_cg());
		par1NBTTagCompound.setBoolean("Tame", func_110248_bS());
		par1NBTTagCompound.setString("OwnerName", func_142019_cb());
		if(func_110261_ca())
		{
			NBTTagList var2 = new NBTTagList();
			for(int var3 = 2; var3 < field_110296_bG.getSizeInventory(); ++var3)
			{
				ItemStack var4 = field_110296_bG.getStackInSlot(var3);
				if(var4 != null)
				{
					NBTTagCompound var5 = new NBTTagCompound();
					var5.setByte("Slot", (byte) var3);
					var4.writeToNBT(var5);
					var2.appendTag(var5);
				}
			}
			par1NBTTagCompound.setTag("Items", var2);
		}
		if(field_110296_bG.getStackInSlot(1) != null)
		{
			par1NBTTagCompound.setTag("ArmorItem", field_110296_bG.getStackInSlot(1).writeToNBT(new NBTTagCompound("ArmorItem")));
		}
		if(field_110296_bG.getStackInSlot(0) != null)
		{
			par1NBTTagCompound.setTag("SaddleItem", field_110296_bG.getStackInSlot(0).writeToNBT(new NBTTagCompound("SaddleItem")));
		}
	}
	
	public static boolean func_110211_v(int par0)
	{
		return par0 == Item.field_111215_ce.itemID || par0 == Item.field_111216_cf.itemID || par0 == Item.field_111213_cg.itemID;
	}
}
