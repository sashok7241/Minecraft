package net.minecraft.src;

public abstract class BlockRotatedPillar extends Block
{
	protected Icon field_111051_a;
	
	protected BlockRotatedPillar(int par1, Material par2Material)
	{
		super(par1, par2Material);
	}
	
	@Override protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(blockID, 1, func_111050_e(par1));
	}
	
	@Override public int damageDropped(int par1)
	{
		return par1 & 3;
	}
	
	protected abstract Icon func_111048_c(int var1);
	
	protected Icon func_111049_d(int par1)
	{
		return field_111051_a;
	}
	
	public int func_111050_e(int par1)
	{
		return par1 & 3;
	}
	
	@Override public Icon getIcon(int par1, int par2)
	{
		int var3 = par2 & 12;
		int var4 = par2 & 3;
		return var3 == 0 && (par1 == 1 || par1 == 0) ? func_111049_d(var4) : var3 == 4 && (par1 == 5 || par1 == 4) ? func_111049_d(var4) : var3 == 8 && (par1 == 2 || par1 == 3) ? func_111049_d(var4) : func_111048_c(var4);
	}
	
	@Override public int getRenderType()
	{
		return 31;
	}
	
	@Override public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
	{
		int var10 = par9 & 3;
		byte var11 = 0;
		switch(par5)
		{
			case 0:
			case 1:
				var11 = 0;
				break;
			case 2:
			case 3:
				var11 = 8;
				break;
			case 4:
			case 5:
				var11 = 4;
		}
		return var10 | var11;
	}
}
