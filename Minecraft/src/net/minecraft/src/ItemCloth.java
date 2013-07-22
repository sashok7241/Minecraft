package net.minecraft.src;

public class ItemCloth extends ItemBlock
{
	public ItemCloth(int p_i3626_1_)
	{
		super(p_i3626_1_);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return Block.cloth.getIcon(2, BlockCloth.getBlockFromDye(par1));
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_;
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		return super.getUnlocalizedName() + "." + ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(p_77667_1_.getItemDamage())];
	}
}
