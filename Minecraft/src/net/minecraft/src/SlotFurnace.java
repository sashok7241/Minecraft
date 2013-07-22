package net.minecraft.src;

public class SlotFurnace extends Slot
{
	private EntityPlayer thePlayer;
	private int field_75228_b;
	
	public SlotFurnace(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5)
	{
		super(par2IInventory, par3, par4, par5);
		thePlayer = par1EntityPlayer;
	}
	
	@Override public ItemStack decrStackSize(int par1)
	{
		if(getHasStack())
		{
			field_75228_b += Math.min(par1, getStack().stackSize);
		}
		return super.decrStackSize(par1);
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}
	
	@Override protected void onCrafting(ItemStack par1ItemStack)
	{
		par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, field_75228_b);
		if(!thePlayer.worldObj.isRemote)
		{
			int var2 = field_75228_b;
			float var3 = FurnaceRecipes.smelting().getExperience(par1ItemStack.itemID);
			int var4;
			if(var3 == 0.0F)
			{
				var2 = 0;
			} else if(var3 < 1.0F)
			{
				var4 = MathHelper.floor_float(var2 * var3);
				if(var4 < MathHelper.ceiling_float_int(var2 * var3) && (float) Math.random() < var2 * var3 - var4)
				{
					++var4;
				}
				var2 = var4;
			}
			while(var2 > 0)
			{
				var4 = EntityXPOrb.getXPSplit(var2);
				var2 -= var4;
				thePlayer.worldObj.spawnEntityInWorld(new EntityXPOrb(thePlayer.worldObj, thePlayer.posX, thePlayer.posY + 0.5D, thePlayer.posZ + 0.5D, var4));
			}
		}
		field_75228_b = 0;
		if(par1ItemStack.itemID == Item.ingotIron.itemID)
		{
			thePlayer.addStat(AchievementList.acquireIron, 1);
		}
		if(par1ItemStack.itemID == Item.fishCooked.itemID)
		{
			thePlayer.addStat(AchievementList.cookFish, 1);
		}
	}
	
	@Override protected void onCrafting(ItemStack par1ItemStack, int par2)
	{
		field_75228_b += par2;
		this.onCrafting(par1ItemStack);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		this.onCrafting(par2ItemStack);
		super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
	}
}
