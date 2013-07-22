package net.minecraft.src;

public class ItemColored extends ItemBlock
{
	private final Block blockRef;
	private String[] blockNames;
	
	public ItemColored(int par1, boolean par2)
	{
		super(par1);
		blockRef = Block.blocksList[getBlockID()];
		if(par2)
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
	
	@Override public int getMetadata(int par1)
	{
		return par1;
	}
	
	@Override public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if(blockNames == null) return super.getUnlocalizedName(par1ItemStack);
		else
		{
			int var2 = par1ItemStack.getItemDamage();
			return var2 >= 0 && var2 < blockNames.length ? super.getUnlocalizedName(par1ItemStack) + "." + blockNames[var2] : super.getUnlocalizedName(par1ItemStack);
		}
	}
	
	public ItemColored setBlockNames(String[] par1ArrayOfStr)
	{
		blockNames = par1ArrayOfStr;
		return this;
	}
}
