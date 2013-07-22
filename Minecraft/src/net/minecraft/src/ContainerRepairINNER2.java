package net.minecraft.src;

class ContainerRepairINNER2 extends Slot
{
	final World field_135071_a;
	final int field_135069_b;
	final int field_135070_c;
	final int field_135067_d;
	final ContainerRepair field_135068_e;
	
	ContainerRepairINNER2(ContainerRepair par1ContainerRepair, IInventory par2IInventory, int par3, int par4, int par5, World par6World, int par7, int par8, int par9)
	{
		super(par2IInventory, par3, par4, par5);
		field_135068_e = par1ContainerRepair;
		field_135071_a = par6World;
		field_135069_b = par7;
		field_135070_c = par8;
		field_135067_d = par9;
	}
	
	@Override public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return (par1EntityPlayer.capabilities.isCreativeMode || par1EntityPlayer.experienceLevel >= field_135068_e.maximumCost) && field_135068_e.maximumCost > 0 && getHasStack();
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}
	
	@Override public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		if(!par1EntityPlayer.capabilities.isCreativeMode)
		{
			par1EntityPlayer.addExperienceLevel(-field_135068_e.maximumCost);
		}
		ContainerRepair.getRepairInputInventory(field_135068_e).setInventorySlotContents(0, (ItemStack) null);
		if(ContainerRepair.getStackSizeUsedInRepair(field_135068_e) > 0)
		{
			ItemStack var3 = ContainerRepair.getRepairInputInventory(field_135068_e).getStackInSlot(1);
			if(var3 != null && var3.stackSize > ContainerRepair.getStackSizeUsedInRepair(field_135068_e))
			{
				var3.stackSize -= ContainerRepair.getStackSizeUsedInRepair(field_135068_e);
				ContainerRepair.getRepairInputInventory(field_135068_e).setInventorySlotContents(1, var3);
			} else
			{
				ContainerRepair.getRepairInputInventory(field_135068_e).setInventorySlotContents(1, (ItemStack) null);
			}
		} else
		{
			ContainerRepair.getRepairInputInventory(field_135068_e).setInventorySlotContents(1, (ItemStack) null);
		}
		field_135068_e.maximumCost = 0;
		if(!par1EntityPlayer.capabilities.isCreativeMode && !field_135071_a.isRemote && field_135071_a.getBlockId(field_135069_b, field_135070_c, field_135067_d) == Block.anvil.blockID && par1EntityPlayer.getRNG().nextFloat() < 0.12F)
		{
			int var6 = field_135071_a.getBlockMetadata(field_135069_b, field_135070_c, field_135067_d);
			int var4 = var6 & 3;
			int var5 = var6 >> 2;
			++var5;
			if(var5 > 2)
			{
				field_135071_a.setBlockToAir(field_135069_b, field_135070_c, field_135067_d);
				field_135071_a.playAuxSFX(1020, field_135069_b, field_135070_c, field_135067_d, 0);
			} else
			{
				field_135071_a.setBlockMetadataWithNotify(field_135069_b, field_135070_c, field_135067_d, var4 | var5 << 2, 2);
				field_135071_a.playAuxSFX(1021, field_135069_b, field_135070_c, field_135067_d, 0);
			}
		} else if(!field_135071_a.isRemote)
		{
			field_135071_a.playAuxSFX(1021, field_135069_b, field_135070_c, field_135067_d, 0);
		}
	}
}
