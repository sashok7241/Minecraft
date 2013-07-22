package net.minecraft.src;

public class ItemCloth extends ItemBlock
{
	public ItemCloth(int par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override public Icon getIconFromDamage(int par1)
	{
		return Block.cloth.getIcon(2, BlockColored.getBlockFromDye(par1));
	}
	
	@Override public int getMetadata(int par1)
	{
		return par1;
	}
	
	@Override public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + "." + ItemDye.dyeColorNames[BlockColored.getBlockFromDye(par1ItemStack.getItemDamage())];
	}
}
