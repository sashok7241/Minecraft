package net.minecraft.src;

public class SlotFurnace extends Slot
{
	private EntityPlayer thePlayer;
	private int field_75228_b;
	
	public SlotFurnace(EntityPlayer p_i3608_1_, IInventory p_i3608_2_, int p_i3608_3_, int p_i3608_4_, int p_i3608_5_)
	{
		super(p_i3608_2_, p_i3608_3_, p_i3608_4_, p_i3608_5_);
		thePlayer = p_i3608_1_;
	}
	
	@Override public ItemStack decrStackSize(int p_75209_1_)
	{
		if(getHasStack())
		{
			field_75228_b += Math.min(p_75209_1_, getStack().stackSize);
		}
		return super.decrStackSize(p_75209_1_);
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return false;
	}
	
	@Override protected void onCrafting(ItemStack p_75208_1_)
	{
		p_75208_1_.onCrafting(thePlayer.worldObj, thePlayer, field_75228_b);
		if(!thePlayer.worldObj.isRemote)
		{
			int var2 = field_75228_b;
			float var3 = FurnaceRecipes.smelting().getExperience(p_75208_1_.itemID);
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
		if(p_75208_1_.itemID == Item.ingotIron.itemID)
		{
			thePlayer.addStat(AchievementList.acquireIron, 1);
		}
		if(p_75208_1_.itemID == Item.fishCooked.itemID)
		{
			thePlayer.addStat(AchievementList.cookFish, 1);
		}
	}
	
	@Override protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_)
	{
		field_75228_b += p_75210_2_;
		this.onCrafting(p_75210_1_);
	}
	
	@Override public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		this.onCrafting(p_82870_2_);
		super.onPickupFromSlot(p_82870_1_, p_82870_2_);
	}
}
