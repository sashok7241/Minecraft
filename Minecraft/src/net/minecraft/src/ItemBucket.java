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
		boolean var4 = isFull == 0;
		MovingObjectPosition var5 = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, var4);
		if(var5 == null) return par1ItemStack;
		else
		{
			if(var5.typeOfHit == EnumMovingObjectType.TILE)
			{
				int var6 = var5.blockX;
				int var7 = var5.blockY;
				int var8 = var5.blockZ;
				if(!par2World.canMineBlock(par3EntityPlayer, var6, var7, var8)) return par1ItemStack;
				if(isFull == 0)
				{
					if(!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack)) return par1ItemStack;
					if(par2World.getBlockMaterial(var6, var7, var8) == Material.water && par2World.getBlockMetadata(var6, var7, var8) == 0)
					{
						par2World.setBlockToAir(var6, var7, var8);
						if(par3EntityPlayer.capabilities.isCreativeMode) return par1ItemStack;
						if(--par1ItemStack.stackSize <= 0) return new ItemStack(Item.bucketWater);
						if(!par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bucketWater)))
						{
							par3EntityPlayer.dropPlayerItem(new ItemStack(Item.bucketWater.itemID, 1, 0));
						}
						return par1ItemStack;
					}
					if(par2World.getBlockMaterial(var6, var7, var8) == Material.lava && par2World.getBlockMetadata(var6, var7, var8) == 0)
					{
						par2World.setBlockToAir(var6, var7, var8);
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
					if(var5.sideHit == 0)
					{
						--var7;
					}
					if(var5.sideHit == 1)
					{
						++var7;
					}
					if(var5.sideHit == 2)
					{
						--var8;
					}
					if(var5.sideHit == 3)
					{
						++var8;
					}
					if(var5.sideHit == 4)
					{
						--var6;
					}
					if(var5.sideHit == 5)
					{
						++var6;
					}
					if(!par3EntityPlayer.canPlayerEdit(var6, var7, var8, var5.sideHit, par1ItemStack)) return par1ItemStack;
					if(tryPlaceContainedLiquid(par2World, var6, var7, var8) && !par3EntityPlayer.capabilities.isCreativeMode) return new ItemStack(Item.bucketEmpty);
				}
			}
			return par1ItemStack;
		}
	}
	
	public boolean tryPlaceContainedLiquid(World par1World, int par2, int par3, int par4)
	{
		if(isFull <= 0) return false;
		else
		{
			Material var5 = par1World.getBlockMaterial(par2, par3, par4);
			boolean var6 = !var5.isSolid();
			if(!par1World.isAirBlock(par2, par3, par4) && !var6) return false;
			else
			{
				if(par1World.provider.isHellWorld && isFull == Block.waterMoving.blockID)
				{
					par1World.playSoundEffect(par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, "random.fizz", 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F);
					for(int var7 = 0; var7 < 8; ++var7)
					{
						par1World.spawnParticle("largesmoke", par2 + Math.random(), par3 + Math.random(), par4 + Math.random(), 0.0D, 0.0D, 0.0D);
					}
				} else
				{
					if(!par1World.isRemote && var6 && !var5.isLiquid())
					{
						par1World.destroyBlock(par2, par3, par4, true);
					}
					par1World.setBlock(par2, par3, par4, isFull, 0, 3);
				}
				return true;
			}
		}
	}
}
