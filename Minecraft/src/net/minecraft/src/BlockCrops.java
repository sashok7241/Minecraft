package net.minecraft.src;

import java.util.Random;

public class BlockCrops extends BlockFlower
{
	private Icon[] iconArray;
	
	protected BlockCrops(int par1)
	{
		super(par1);
		setTickRandomly(true);
		float var2 = 0.5F;
		setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, 0.25F, 0.5F + var2);
		setCreativeTab((CreativeTabs) null);
		setHardness(0.0F);
		setStepSound(soundGrassFootstep);
		disableStats();
	}
	
	@Override protected boolean canThisPlantGrowOnThisBlockID(int par1)
	{
		return par1 == Block.tilledField.blockID;
	}
	
	@Override public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
		if(!par1World.isRemote)
		{
			if(par5 >= 7)
			{
				int var8 = 3 + par7;
				for(int var9 = 0; var9 < var8; ++var9)
				{
					if(par1World.rand.nextInt(15) <= par5)
					{
						dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(getSeedItem(), 1, 0));
					}
				}
			}
		}
	}
	
	public void fertilize(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockMetadata(par2, par3, par4) + MathHelper.getRandomIntegerInRange(par1World.rand, 2, 5);
		if(var5 > 7)
		{
			var5 = 7;
		}
		par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 2);
	}
	
	protected int getCropItem()
	{
		return Item.wheat.itemID;
	}
	
	private float getGrowthRate(World par1World, int par2, int par3, int par4)
	{
		float var5 = 1.0F;
		int var6 = par1World.getBlockId(par2, par3, par4 - 1);
		int var7 = par1World.getBlockId(par2, par3, par4 + 1);
		int var8 = par1World.getBlockId(par2 - 1, par3, par4);
		int var9 = par1World.getBlockId(par2 + 1, par3, par4);
		int var10 = par1World.getBlockId(par2 - 1, par3, par4 - 1);
		int var11 = par1World.getBlockId(par2 + 1, par3, par4 - 1);
		int var12 = par1World.getBlockId(par2 + 1, par3, par4 + 1);
		int var13 = par1World.getBlockId(par2 - 1, par3, par4 + 1);
		boolean var14 = var8 == blockID || var9 == blockID;
		boolean var15 = var6 == blockID || var7 == blockID;
		boolean var16 = var10 == blockID || var11 == blockID || var12 == blockID || var13 == blockID;
		for(int var17 = par2 - 1; var17 <= par2 + 1; ++var17)
		{
			for(int var18 = par4 - 1; var18 <= par4 + 1; ++var18)
			{
				int var19 = par1World.getBlockId(var17, par3 - 1, var18);
				float var20 = 0.0F;
				if(var19 == Block.tilledField.blockID)
				{
					var20 = 1.0F;
					if(par1World.getBlockMetadata(var17, par3 - 1, var18) > 0)
					{
						var20 = 3.0F;
					}
				}
				if(var17 != par2 || var18 != par4)
				{
					var20 /= 4.0F;
				}
				var5 += var20;
			}
		}
		if(var16 || var14 && var15)
		{
			var5 /= 2.0F;
		}
		return var5;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		if(par2 < 0 || par2 > 7)
		{
			par2 = 7;
		}
		return iconArray[par2];
	}
	
	@Override public int getRenderType()
	{
		return 6;
	}
	
	protected int getSeedItem()
	{
		return Item.seeds.itemID;
	}
	
	@Override public int idDropped(int par1, Random par2Random, int par3)
	{
		return par1 == 7 ? getCropItem() : getSeedItem();
	}
	
	@Override public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return getSeedItem();
	}
	
	@Override public int quantityDropped(Random par1Random)
	{
		return 1;
	}
	
	@Override public void registerIcons(IconRegister par1IconRegister)
	{
		iconArray = new Icon[8];
		for(int var2 = 0; var2 < iconArray.length; ++var2)
		{
			iconArray[var2] = par1IconRegister.registerIcon("crops_" + var2);
		}
	}
	
	@Override public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.updateTick(par1World, par2, par3, par4, par5Random);
		if(par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);
			if(var6 < 7)
			{
				float var7 = getGrowthRate(par1World, par2, par3, par4);
				if(par5Random.nextInt((int) (25.0F / var7) + 1) == 0)
				{
					++var6;
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var6, 2);
				}
			}
		}
	}
}
