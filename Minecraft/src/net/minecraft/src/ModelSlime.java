package net.minecraft.src;

public class ModelSlime extends ModelBase
{
	ModelRenderer slimeBodies;
	ModelRenderer slimeRightEye;
	ModelRenderer slimeLeftEye;
	ModelRenderer slimeMouth;
	
	public ModelSlime(int par1)
	{
		slimeBodies = new ModelRenderer(this, 0, par1);
		slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8, 8, 8);
		if(par1 > 0)
		{
			slimeBodies = new ModelRenderer(this, 0, par1);
			slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6, 6, 6);
			slimeRightEye = new ModelRenderer(this, 32, 0);
			slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2, 2, 2);
			slimeLeftEye = new ModelRenderer(this, 32, 4);
			slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2, 2, 2);
			slimeMouth = new ModelRenderer(this, 32, 8);
			slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1, 1, 1);
		}
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		slimeBodies.render(par7);
		if(slimeRightEye != null)
		{
			slimeRightEye.render(par7);
			slimeLeftEye.render(par7);
			slimeMouth.render(par7);
		}
	}
}
