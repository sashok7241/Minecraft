package net.minecraft.src;

public class ItemBlockWithMetadata extends ItemBlock
{
	private Block theBlock;
	
	public ItemBlockWithMetadata(int par1, Block par2Block)
	{
		super(par1);
		theBlock = par2Block;
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return theBlock.getIcon(2, par1);
	}
	
	@Override public int getMetadata(int par1)
	{
		return par1;
	}
}
