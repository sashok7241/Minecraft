package net.minecraft.src;

class SlotRepair extends Slot
{
	final World theWorld;
	final int blockPosX;
	final int blockPosY;
	final int blockPosZ;
	final ContainerRepair anvil;
	
	SlotRepair(ContainerRepair p_i5079_1_, IInventory p_i5079_2_, int p_i5079_3_, int p_i5079_4_, int p_i5079_5_, World p_i5079_6_, int p_i5079_7_, int p_i5079_8_, int p_i5079_9_)
	{
		super(p_i5079_2_, p_i5079_3_, p_i5079_4_, p_i5079_5_);
		anvil = p_i5079_1_;
		theWorld = p_i5079_6_;
		blockPosX = p_i5079_7_;
		blockPosY = p_i5079_8_;
		blockPosZ = p_i5079_9_;
	}
	
	@Override public boolean canTakeStack(EntityPlayer p_82869_1_)
	{
		return (p_82869_1_.capabilities.isCreativeMode || p_82869_1_.experienceLevel >= anvil.maximumCost) && anvil.maximumCost > 0 && getHasStack();
	}
	
	@Override public boolean isItemValid(ItemStack p_75214_1_)
	{
		return false;
	}
	
	@Override public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
	{
		if(!p_82870_1_.capabilities.isCreativeMode)
		{
			p_82870_1_.addExperienceLevel(-anvil.maximumCost);
		}
		ContainerRepair.getRepairInputInventory(anvil).setInventorySlotContents(0, (ItemStack) null);
		if(ContainerRepair.getStackSizeUsedInRepair(anvil) > 0)
		{
			ItemStack var3 = ContainerRepair.getRepairInputInventory(anvil).getStackInSlot(1);
			if(var3 != null && var3.stackSize > ContainerRepair.getStackSizeUsedInRepair(anvil))
			{
				var3.stackSize -= ContainerRepair.getStackSizeUsedInRepair(anvil);
				ContainerRepair.getRepairInputInventory(anvil).setInventorySlotContents(1, var3);
			} else
			{
				ContainerRepair.getRepairInputInventory(anvil).setInventorySlotContents(1, (ItemStack) null);
			}
		} else
		{
			ContainerRepair.getRepairInputInventory(anvil).setInventorySlotContents(1, (ItemStack) null);
		}
		anvil.maximumCost = 0;
		if(!p_82870_1_.capabilities.isCreativeMode && !theWorld.isRemote && theWorld.getBlockId(blockPosX, blockPosY, blockPosZ) == Block.anvil.blockID && p_82870_1_.getRNG().nextFloat() < 0.12F)
		{
			int var6 = theWorld.getBlockMetadata(blockPosX, blockPosY, blockPosZ);
			int var4 = var6 & 3;
			int var5 = var6 >> 2;
			++var5;
			if(var5 > 2)
			{
				theWorld.setBlockToAir(blockPosX, blockPosY, blockPosZ);
				theWorld.playAuxSFX(1020, blockPosX, blockPosY, blockPosZ, 0);
			} else
			{
				theWorld.setBlockMetadataWithNotify(blockPosX, blockPosY, blockPosZ, var4 | var5 << 2, 2);
				theWorld.playAuxSFX(1021, blockPosX, blockPosY, blockPosZ, 0);
			}
		} else if(!theWorld.isRemote)
		{
			theWorld.playAuxSFX(1021, blockPosX, blockPosY, blockPosZ, 0);
		}
	}
}
