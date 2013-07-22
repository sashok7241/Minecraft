package net.minecraft.src;


public class ItemTool extends Item
{
	private Block[] blocksEffectiveAgainst;
	protected float efficiencyOnProperMaterial = 4.0F;
	private float damageVsEntity;
	protected EnumToolMaterial toolMaterial;
	
	protected ItemTool(int par1, float par2, EnumToolMaterial par3EnumToolMaterial, Block[] par4ArrayOfBlock)
	{
		super(par1);
		toolMaterial = par3EnumToolMaterial;
		blocksEffectiveAgainst = par4ArrayOfBlock;
		maxStackSize = 1;
		setMaxDamage(par3EnumToolMaterial.getMaxUses());
		efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	@Override public Multimap func_111205_h()
	{
		Multimap var1 = super.func_111205_h();
		var1.put(SharedMonsterAttributes.field_111264_e.func_111108_a(), new AttributeModifier(field_111210_e, "Tool modifier", damageVsEntity, 0));
		return var1;
	}
	
	@Override public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
	{
		return toolMaterial.getToolCraftingMaterial() == par2ItemStack.itemID ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}
	
	@Override public int getItemEnchantability()
	{
		return toolMaterial.getEnchantability();
	}
	
	@Override public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		for(Block element : blocksEffectiveAgainst)
		{
			if(element == par2Block) return efficiencyOnProperMaterial;
		}
		return 1.0F;
	}
	
	public String getToolMaterialName()
	{
		return toolMaterial.toString();
	}
	
	@Override public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(2, par3EntityLivingBase);
		return true;
	}
	
	@Override public boolean isFull3D()
	{
		return true;
	}
	
	@Override public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
	{
		if(Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
		{
			par1ItemStack.damageItem(1, par7EntityLivingBase);
		}
		return true;
	}
}
