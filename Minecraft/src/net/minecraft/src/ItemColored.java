package net.minecraft.src;

public class ItemColored extends ItemBlock
{
	private final Block blockRef;
	private String[] blockNames;
	
	public ItemColored(int p_i3628_1_, boolean p_i3628_2_)
	{
		super(p_i3628_1_);
		blockRef = Block.blocksList[getBlockID()];
		if(p_i3628_2_)
		{
			setMaxDamage(0);
			setHasSubtypes(true);
		}
	}
	
	@Override public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
	{
		return blockRef.getRenderColor(par1ItemStack.getItemDamage());
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return blockRef.getIcon(0, par1);
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_;
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		if(blockNames == null) return super.getUnlocalizedName(p_77667_1_);
		else
		{
			int var2 = p_77667_1_.getItemDamage();
			return var2 >= 0 && var2 < blockNames.length ? super.getUnlocalizedName(p_77667_1_) + "." + blockNames[var2] : super.getUnlocalizedName(p_77667_1_);
		}
	}
	
	public ItemColored setBlockNames(String[] p_77894_1_)
	{
		blockNames = p_77894_1_;
		return this;
	}
}
