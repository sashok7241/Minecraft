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
	
	public ItemFood(int par1, int par2, boolean par3)
	{
		this(par1, par2, 0.6F, par3);
	}
	
	public ItemFood(int par1, int par2, float par3, boolean par4)
	{
		super(par1);
		itemUseDuration = 32;
		healAmount = par2;
		isWolfsFavoriteMeat = par4;
		saturationModifier = par3;
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public int getHealAmount()
	{
		return healAmount;
	}
	
	@Override public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.eat;
	}
	
	@Override public int getMaxItemUseDuration(ItemStack par1ItemStack)
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
	
	@Override public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		--par1ItemStack.stackSize;
		par3EntityPlayer.getFoodStats().addStats(this);
		par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
		onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
	}
	
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(!par2World.isRemote && potionId > 0 && par2World.rand.nextFloat() < potionEffectProbability)
		{
			par3EntityPlayer.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
		}
	}
	
	@Override public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if(par3EntityPlayer.canEat(alwaysEdible))
		{
			par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		}
		return par1ItemStack;
	}
	
	public ItemFood setAlwaysEdible()
	{
		alwaysEdible = true;
		return this;
	}
	
	public ItemFood setPotionEffect(int par1, int par2, int par3, float par4)
	{
		potionId = par1;
		potionDuration = par2;
		potionAmplifier = par3;
		potionEffectProbability = par4;
		return this;
	}
}
