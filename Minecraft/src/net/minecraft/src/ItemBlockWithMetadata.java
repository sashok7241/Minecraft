package net.minecraft.src;

public class ItemBlockWithMetadata extends ItemBlock
{
	private Block theBlock;
	
	public ItemBlockWithMetadata(int p_i10057_1_, Block p_i10057_2_)
	{
		super(p_i10057_1_);
		theBlock = p_i10057_2_;
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
}
