package net.minecraft.src;

public class EntityGiantZombie extends EntityMob
{
	public EntityGiantZombie(World par1World)
	{
		super(par1World);
		yOffset *= 6.0F;
		setSize(width * 6.0F, height * 6.0F);
	}
	
	@Override protected void func_110147_ax()
	{
		super.func_110147_ax();
		func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
		func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
		func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0D);
	}
	
	@Override public float getBlockPathWeight(int par1, int par2, int par3)
	{
		return worldObj.getLightBrightness(par1, par2, par3) - 0.5F;
	}
}
