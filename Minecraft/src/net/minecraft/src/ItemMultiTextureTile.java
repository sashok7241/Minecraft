package net.minecraft.src;

public class ItemMultiTextureTile extends ItemBlock
{
	private final Block theBlock;
	private final String[] field_82804_b;
	
	public ItemMultiTextureTile(int par1, Block par2Block, String[] par3ArrayOfStr)
	{
		super(par1);
		theBlock = par2Block;
		field_82804_b = par3ArrayOfStr;
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
	
	@Override public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		int var2 = par1ItemStack.getItemDamage();
		if(var2 < 0 || var2 >= field_82804_b.length)
		{
			var2 = 0;
		}
		return super.getUnlocalizedName() + "." + field_82804_b[var2];
	}
}
