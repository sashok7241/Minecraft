package net.minecraft.src;

public class ModelSquid extends ModelBase
{
	ModelRenderer squidBody;
	ModelRenderer[] squidTentacles = new ModelRenderer[8];
	
	public ModelSquid()
	{
		byte var1 = -16;
		squidBody = new ModelRenderer(this, 0, 0);
		squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
		squidBody.rotationPointY += 24 + var1;
		for(int var2 = 0; var2 < squidTentacles.length; ++var2)
		{
			squidTentacles[var2] = new ModelRenderer(this, 48, 0);
			double var3 = var2 * Math.PI * 2.0D / squidTentacles.length;
			float var5 = (float) Math.cos(var3) * 5.0F;
			float var6 = (float) Math.sin(var3) * 5.0F;
			squidTentacles[var2].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
			squidTentacles[var2].rotationPointX = var5;
			squidTentacles[var2].rotationPointZ = var6;
			squidTentacles[var2].rotationPointY = 31 + var1;
			var3 = var2 * Math.PI * -2.0D / squidTentacles.length + Math.PI / 2D;
			squidTentacles[var2].rotateAngleY = (float) var3;
		}
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		squidBody.render(par7);
		for(ModelRenderer squidTentacle : squidTentacles)
		{
			squidTentacle.render(par7);
		}
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		ModelRenderer[] var8 = squidTentacles;
		int var9 = var8.length;
		for(int var10 = 0; var10 < var9; ++var10)
		{
			ModelRenderer var11 = var8[var10];
			var11.rotateAngleX = par3;
		}
	}
}
