package net.minecraft.src;

public class ItemSaddle extends Item
{
	public ItemSaddle(int par1)
	{
		super(par1);
		maxStackSize = 1;
		setCreativeTab(CreativeTabs.tabTransport);
	}
	
	@Override public boolean func_111207_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
	{
		if(par3EntityLivingBase instanceof EntityPig)
		{
			EntityPig var4 = (EntityPig) par3EntityLivingBase;
			if(!var4.getSaddled() && !var4.isChild())
			{
				var4.setSaddled(true);
				--par1ItemStack.stackSize;
			}
			return true;
		} else return false;
	}
	
	@Override public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		func_111207_a(par1ItemStack, (EntityPlayer) null, par2EntityLivingBase);
		return true;
	}
}
