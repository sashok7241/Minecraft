package net.minecraft.src;

public class ModelMinecart extends ModelBase
{
	public ModelRenderer[] sideModels = new ModelRenderer[7];
	
	public ModelMinecart()
	{
		sideModels[0] = new ModelRenderer(this, 0, 10);
		sideModels[1] = new ModelRenderer(this, 0, 0);
		sideModels[2] = new ModelRenderer(this, 0, 0);
		sideModels[3] = new ModelRenderer(this, 0, 0);
		sideModels[4] = new ModelRenderer(this, 0, 0);
		sideModels[5] = new ModelRenderer(this, 44, 10);
		byte var1 = 20;
		byte var2 = 8;
		byte var3 = 16;
		byte var4 = 4;
		sideModels[0].addBox(-var1 / 2, -var3 / 2, -1.0F, var1, var3, 2, 0.0F);
		sideModels[0].setRotationPoint(0.0F, var4, 0.0F);
		sideModels[5].addBox(-var1 / 2 + 1, -var3 / 2 + 1, -1.0F, var1 - 2, var3 - 2, 1, 0.0F);
		sideModels[5].setRotationPoint(0.0F, var4, 0.0F);
		sideModels[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		sideModels[1].setRotationPoint(-var1 / 2 + 1, var4, 0.0F);
		sideModels[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		sideModels[2].setRotationPoint(var1 / 2 - 1, var4, 0.0F);
		sideModels[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		sideModels[3].setRotationPoint(0.0F, var4, -var3 / 2 + 1);
		sideModels[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		sideModels[4].setRotationPoint(0.0F, var4, var3 / 2 - 1);
		sideModels[0].rotateAngleX = (float) Math.PI / 2F;
		sideModels[1].rotateAngleY = (float) Math.PI * 3F / 2F;
		sideModels[2].rotateAngleY = (float) Math.PI / 2F;
		sideModels[3].rotateAngleY = (float) Math.PI;
		sideModels[5].rotateAngleX = -((float) Math.PI / 2F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		sideModels[5].rotationPointY = 4.0F - par4;
		for(int var8 = 0; var8 < 6; ++var8)
		{
			sideModels[var8].render(par7);
		}
	}
}
