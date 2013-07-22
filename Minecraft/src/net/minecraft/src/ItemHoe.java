package net.minecraft.src;

public class ItemHoe extends Item
{
	protected EnumToolMaterial theToolMaterial;
	
	public ItemHoe(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1);
		theToolMaterial = par2EnumToolMaterial;
		maxStackSize = 1;
		setMaxDamage(par2EnumToolMaterial.getMaxUses());
		setCreativeTab(CreativeTabs.tabTools);
	}
	
	public String getMaterialName()
	{
		return theToolMaterial.toString();
	}
	
	@Override public boolean isFull3D()
	{
		return true;
	}
	
	@Override public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	{
		if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) return false;
		else
		{
			int var11 = par3World.getBlockId(par4, par5, par6);
			int var12 = par3World.getBlockId(par4, par5 + 1, par6);
			if((par7 == 0 || var12 != 0 || var11 != Block.grass.blockID) && var11 != Block.dirt.blockID) return false;
			else
			{
				Block var13 = Block.tilledField;
				par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);
				if(par3World.isRemote) return true;
				else
				{
					par3World.setBlock(par4, par5, par6, var13.blockID);
					par1ItemStack.damageItem(1, par2EntityPlayer);
					return true;
				}
			}
		}
	}
}
