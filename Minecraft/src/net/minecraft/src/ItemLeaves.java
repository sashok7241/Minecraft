package net.minecraft.src;

public class ItemLeaves extends ItemBlock
{
	public ItemLeaves(int p_i3667_1_)
	{
		super(p_i3667_1_);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		int var3 = par1ItemStack.getItemDamage();
		return (var3 & 1) == 1 ? ColorizerFoliage.getFoliageColorPine() : (var3 & 2) == 2 ? ColorizerFoliage.getFoliageColorBirch() : ColorizerFoliage.getFoliageColorBasic();
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return Block.leaves.getIcon(0, par1);
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_ | 4;
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		int var2 = p_77667_1_.getItemDamage();
		if(var2 < 0 || var2 >= BlockLeaves.LEAF_TYPES.length)
		{
			var2 = 0;
		}
		return super.getUnlocalizedName() + "." + BlockLeaves.LEAF_TYPES[var2];
	}
}
