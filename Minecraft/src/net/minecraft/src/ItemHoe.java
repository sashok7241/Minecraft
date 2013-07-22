package net.minecraft.src;

public class ItemHoe extends Item
{
	protected EnumToolMaterial theToolMaterial;
	
	public ItemHoe(int p_i3657_1_, EnumToolMaterial p_i3657_2_)
	{
		super(p_i3657_1_);
		theToolMaterial = p_i3657_2_;
		maxStackSize = 1;
		setMaxDamage(p_i3657_2_.getMaxUses());
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
	
	@Override public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
	{
		if(!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) return false;
		else
		{
			int var11 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_, p_77648_6_);
			int var12 = p_77648_3_.getBlockId(p_77648_4_, p_77648_5_ + 1, p_77648_6_);
			if((p_77648_7_ == 0 || var12 != 0 || var11 != Block.grass.blockID) && var11 != Block.dirt.blockID) return false;
			else
			{
				Block var13 = Block.tilledField;
				p_77648_3_.playSoundEffect(p_77648_4_ + 0.5F, p_77648_5_ + 0.5F, p_77648_6_ + 0.5F, var13.stepSound.getStepSound(), (var13.stepSound.getVolume() + 1.0F) / 2.0F, var13.stepSound.getPitch() * 0.8F);
				if(p_77648_3_.isRemote) return true;
				else
				{
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, var13.blockID);
					p_77648_1_.damageItem(1, p_77648_2_);
					return true;
				}
			}
		}
	}
}
