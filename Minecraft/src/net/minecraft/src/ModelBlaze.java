package net.minecraft.src;

public class ModelBlaze extends ModelBase
{
	private ModelRenderer[] blazeSticks = new ModelRenderer[12];
	private ModelRenderer blazeHead;
	
	public ModelBlaze()
	{
		for(int var1 = 0; var1 < blazeSticks.length; ++var1)
		{
			blazeSticks[var1] = new ModelRenderer(this, 0, 16);
			blazeSticks[var1].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
		}
		blazeHead = new ModelRenderer(this, 0, 0);
		blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
	}
	
	public int func_78104_a()
	{
		return 8;
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		blazeHead.render(par7);
		for(ModelRenderer blazeStick : blazeSticks)
		{
			blazeStick.render(par7);
		}
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		float var8 = par3 * (float) Math.PI * -0.1F;
		int var9;
		for(var9 = 0; var9 < 4; ++var9)
		{
			blazeSticks[var9].rotationPointY = -2.0F + MathHelper.cos((var9 * 2 + par3) * 0.25F);
			blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 9.0F;
			blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 9.0F;
			++var8;
		}
		var8 = (float) Math.PI / 4F + par3 * (float) Math.PI * 0.03F;
		for(var9 = 4; var9 < 8; ++var9)
		{
			blazeSticks[var9].rotationPointY = 2.0F + MathHelper.cos((var9 * 2 + par3) * 0.25F);
			blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 7.0F;
			blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 7.0F;
			++var8;
		}
		var8 = 0.47123894F + par3 * (float) Math.PI * -0.05F;
		for(var9 = 8; var9 < 12; ++var9)
		{
			blazeSticks[var9].rotationPointY = 11.0F + MathHelper.cos((var9 * 1.5F + par3) * 0.5F);
			blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 5.0F;
			blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 5.0F;
			++var8;
		}
		blazeHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		blazeHead.rotateAngleX = par5 / (180F / (float) Math.PI);
	}
}
