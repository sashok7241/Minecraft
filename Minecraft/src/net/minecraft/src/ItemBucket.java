package net.minecraft.src;

public class ItemBucket extends Item
{
	private int isFull;
	
	public ItemBucket(int par1, int par2)
	{
		super(par1);
		maxStackSize = 1;
		isFull = par2;
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		float var4 = 1.0F;
		double var5 = par3EntityPlayer.prevPosX + (par3EntityPlayer.posX - par3EntityPlayer.prevPosX) * var4;
		double var7 = par3EntityPlayer.prevPosY + (par3EntityPlayer.posY - par3EntityPlayer.prevPosY) * var4 + 1.62D - par3EntityPlayer.yOffset;
		double var9 = par3EntityPlayer.prevPosZ + (par3EntityPlayer.posZ - par3EntityPlayer.prevPosZ) * var4;
		boolean var11 = isFull == 0;
		MovingObjectPosition var12 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var11);
		if(var12 == null) return par1ItemStack;
		else
		{
			if(var12.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var13 = var12.blockX;
				int var14 = var12.blockY;
				int var15 = var12.blockZ;
				if(!par2World.canMineBlock(par3EntityPlayer, var13, var14, var15)) return par1ItemStack;
				if(isFull == 0)
				{
					if(!par3EntityPlayer.canPlayerEdit(var13, var14, var15, var12.sideHit, par1ItemStack)) return par1ItemStack;
					if(par2World.getBlockMaterial(var13, var14, var15) == Material.water && par2World.getBlockMetadata(var13, var14, var15) == 0)
					{
						par2World.setBlockToAir(var13, var14, var15);
						if(par3EntityPlayer.capabilities.isCreativeMode) return par1ItemStack;
						if(--par1ItemStack.stackSize <= 0) return new ItemStack(Item.bucketWater);
						if(!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater)))
						{
							par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketWater.itemID, 1, 0));
						}
						return par1ItemStack;
					}
					if(par2World.getBlockMaterial(var13, var14, var15) == Material.lava && par2World.getBlockMetadata(var13, var14, var15) == 0)
					{
						par2World.setBlockToAir(var13, var14, var15);
						if(par3EntityPlayer.capabilities.isCreativeMode) return par1ItemStack;
						if(--par1ItemStack.stackSize <= 0) return new ItemStack(Item.bucketLava);
						if(!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketLava)))
						{
							par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketLava.itemID, 1, 0));
						}
						return par1ItemStack;
					}
				} else
				{
					if(isFull < 0) return new ItemStack(Item.bucketEmpty);
					if(var12.sideHit == 0)
					{
						--var14;
					}
					if(var12.sideHit == 1)
					{
						++var14;
					}
					if(var12.sideHit == 2)
					{
						--var15;
					}
					if(var12.sideHit == 3)
					{
						++var15;
					}
					if(var12.sideHit == 4)
					{
						--var13;
					}
					if(var12.sideHit == 5)
					{
						++var13;
					}
					if(!par3EntityPlayer.canPlayerEdit(var13, var14, var15, var12.sideHit, par1ItemStack)) return par1ItemStack;
					if(tryPlaceContainedLiquid(par2World, var5, var7, var9, var13, var14, var15) && !par3EntityPlayer.capabilities.isCreativeMode) return new ItemStack(Item.bucketEmpty);
				}
			} else if(isFull == 0 && var12.entityHit instanceof EntityCow) return new ItemStack(Item.bucketMilk);
			return par1ItemStack;
		}
	}
	
	public boolean tryPlaceContainedLiquid(World par1World, double par2, double par4, double par6, int par8, int par9, int par10)
	{
		if(isFull <= 0) return false;
		else if(!par1World.isAirBlock(par8, par9, par10) && par1World.getBlockMaterial(par8, par9, par10).isSolid()) return false;
		else
		{
			if(par1World.provider.isHellWorld && isFull == Block.waterMoving.blockID)
			{
				par1World.playSoundEffect(par2 + 0.5D, par4 + 0.5D, par6 + 0.5D, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);
				for(int var11 = 0; var11 < 8; ++var11)
				{
					par1World.spawnParticle("largesmoke", par8 + Math.random(), par9 + Math.random(), par10 + Math.random(), 0.0D, 0.0D, 0.0D);
				}
			} else
			{
				par1World.setBlock(par8, par9, par10, isFull, 0, 3);
			}
			return true;
		}
	}
}
