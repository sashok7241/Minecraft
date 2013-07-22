package net.minecraft.src;

public class ModelBoat extends ModelBase
{
	public ModelRenderer[] boatSides = new ModelRenderer[5];
	
	public ModelBoat()
	{
		boatSides[0] = new ModelRenderer(this, 0, 8);
		boatSides[1] = new ModelRenderer(this, 0, 0);
		boatSides[2] = new ModelRenderer(this, 0, 0);
		boatSides[3] = new ModelRenderer(this, 0, 0);
		boatSides[4] = new ModelRenderer(this, 0, 0);
		byte var1 = 24;
		byte var2 = 6;
		byte var3 = 20;
		byte var4 = 4;
		boatSides[0].addBox(-var1 / 2, -var3 / 2 + 2, -3.0F, var1, var3 - 4, 4, 0.0F);
		boatSides[0].setRotationPoint(0.0F, var4, 0.0F);
		boatSides[1].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		boatSides[1].setRotationPoint(-var1 / 2 + 1, var4, 0.0F);
		boatSides[2].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		boatSides[2].setRotationPoint(var1 / 2 - 1, var4, 0.0F);
		boatSides[3].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		boatSides[3].setRotationPoint(0.0F, var4, -var3 / 2 + 1);
		boatSides[4].addBox(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
		boatSides[4].setRotationPoint(0.0F, var4, var3 / 2 - 1);
		boatSides[0].rotateAngleX = (float) Math.PI / 2F;
		boatSides[1].rotateAngleY = (float) Math.PI * 3F / 2F;
		boatSides[2].rotateAngleY = (float) Math.PI / 2F;
		boatSides[3].rotateAngleY = (float) Math.PI;
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		for(int var8 = 0; var8 < 5; ++var8)
		{
			boatSides[var8].render(par7);
		}
	}
}
