package net.minecraft.src;

import java.util.Random;

public class ModelGhast extends ModelBase
{
	ModelRenderer body;
	ModelRenderer[] tentacles = new ModelRenderer[9];
	
	public ModelGhast()
	{
		byte var1 = -16;
		body = new ModelRenderer(this, 0, 0);
		body.addBox(-8.0F, -8.0F, -8.0F, 16, 16, 16);
		body.rotationPointY += 24 + var1;
		Random var2 = new Random(1660L);
		for(int var3 = 0; var3 < tentacles.length; ++var3)
		{
			tentacles[var3] = new ModelRenderer(this, 0, 0);
			float var4 = ((var3 % 3 - var3 / 3 % 2 * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
			float var5 = (var3 / 3 / 2.0F * 2.0F - 1.0F) * 5.0F;
			int var6 = var2.nextInt(7) + 8;
			tentacles[var3].addBox(-1.0F, 0.0F, -1.0F, 2, var6, 2);
			tentacles[var3].rotationPointX = var4;
			tentacles[var3].rotationPointZ = var5;
			tentacles[var3].rotationPointY = 31 + var1;
		}
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.6F, 0.0F);
		body.render(par7);
		ModelRenderer[] var8 = tentacles;
		int var9 = var8.length;
		for(int var10 = 0; var10 < var9; ++var10)
		{
			ModelRenderer var11 = var8[var10];
			var11.render(par7);
		}
		GL11.glPopMatrix();
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		for(int var8 = 0; var8 < tentacles.length; ++var8)
		{
			tentacles[var8].rotateAngleX = 0.2F * MathHelper.sin(par3 * 0.3F + var8) + 0.4F;
		}
	}
}
