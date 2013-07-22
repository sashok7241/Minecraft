package net.minecraft.src;

class SlotRepair extends Slot
{
	final World theWorld;
	final int blockPosX;
	final int blockPosY;
	final int blockPosZ;
	final ContainerRepair anvil;
	
	SlotRepair(ContainerRepair par1ContainerRepair, IInventory par2IInventory, int par3, int par4, int par5, World par6World, int par7, int par8, int par9)
	{
		super(par2IInventory, par3, par4, par5);
		anvil = par1ContainerRepair;
		theWorld = par6World;
		blockPosX = par7;
		blockPosY = par8;
		blockPosZ = par9;
	}
	
	@Override public boolean canTakeStack(EntityPlayer par1EntityPlayer)
	{
		return (par1EntityPlayer.capabilities.isCreativeMode || par1EntityPlayer.experienceLevel >= anvil.maximumCost) && anvil.maximumCost > 0 && getHasStack();
	}
	
	@Override public boolean isItemValid(ItemStack par1ItemStack)
	{
		return false;
	}
	
	@Override public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
	{
		if(!par1EntityPlayer.capabilities.isCreativeMode)
		{
			par1EntityPlayer.addExperienceLevel(-anvil.maximumCost);
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
		if(!par1EntityPlayer.capabilities.isCreativeMode && !theWorld.isRemote && theWorld.getBlockId(blockPosX, blockPosY, blockPosZ) == Block.anvil.blockID && par1EntityPlayer.getRNG().nextFloat() < 0.12F)
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
