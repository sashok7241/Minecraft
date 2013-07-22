package net.minecraft.src;

public class ItemFood extends Item
{
	public final int itemUseDuration;
	private final int healAmount;
	private final float saturationModifier;
	private final boolean isWolfsFavoriteMeat;
	private boolean alwaysEdible;
	private int potionId;
	private int potionDuration;
	private int potionAmplifier;
	private float potionEffectProbability;
	
	public ItemFood(int p_i3654_1_, int p_i3654_2_, boolean p_i3654_3_)
	{
		this(p_i3654_1_, p_i3654_2_, 0.6F, p_i3654_3_);
	}
	
	public ItemFood(int p_i3653_1_, int p_i3653_2_, float p_i3653_3_, boolean p_i3653_4_)
	{
		super(p_i3653_1_);
		itemUseDuration = 32;
		healAmount = p_i3653_2_;
		isWolfsFavoriteMeat = p_i3653_4_;
		saturationModifier = p_i3653_3_;
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public int getHealAmount()
	{
		return healAmount;
	}
	
	@Override public EnumAction getItemUseAction(ItemStack p_77661_1_)
	{
		return EnumAction.eat;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack p_77626_1_)
	{
		return 32;
	}
	
	public float getSaturationModifier()
	{
		return saturationModifier;
	}
	
	public boolean isWolfsFavoriteMeat()
	{
		return isWolfsFavoriteMeat;
	}
	
	@Override public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
	{
		--p_77654_1_.stackSize;
		p_77654_3_.getFoodStats().addStats(this);
		p_77654_2_.playSoundAtEntity(p_77654_3_, "random.burp", 0.5F, p_77654_2_.rand.nextFloat() * 0.1F + 0.9F);
		onFoodEaten(p_77654_1_, p_77654_2_, p_77654_3_);
		return p_77654_1_;
	}
	
	protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
	{
		if(!p_77849_2_.isRemote && potionId > 0 && p_77849_2_.rand.nextFloat() < potionEffectProbability)
		{
			p_77849_3_.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
		}
	}
	
	@Override public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
	{
		if(p_77659_3_.canEat(alwaysEdible))
		{
			p_77659_3_.setItemInUse(p_77659_1_, getMaxItemUseDuration(p_77659_1_));
		}
		return p_77659_1_;
	}
	
	public ItemFood setAlwaysEdible()
	{
		alwaysEdible = true;
		return this;
	}
	
	public ItemFood setPotionEffect(int p_77844_1_, int p_77844_2_, int p_77844_3_, float p_77844_4_)
	{
		potionId = p_77844_1_;
		potionDuration = p_77844_2_;
		potionAmplifier = p_77844_3_;
		potionEffectProbability = p_77844_4_;
		return this;
	}
}
