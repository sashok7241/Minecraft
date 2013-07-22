package net.minecraft.src;

public class ItemMultiTextureTile extends ItemBlock
{
	private final Block theBlock;
	private final String[] field_82804_b;
	
	public ItemMultiTextureTile(int p_i5085_1_, Block p_i5085_2_, String[] p_i5085_3_)
	{
		super(p_i5085_1_);
		theBlock = p_i5085_2_;
		field_82804_b = p_i5085_3_;
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return theBlock.getIcon(2, par1);
	}
	
	@Override public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_;
	}
	
	@Override public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		int var2 = p_77667_1_.getItemDamage();
		if(var2 < 0 || var2 >= field_82804_b.length)
		{
			var2 = 0;
		}
		return super.getUnlocalizedName() + "." + field_82804_b[var2];
	}
}
