package net.minecraft.src;


public class ModelEnderCrystal extends ModelBase
{
	private ModelRenderer cube;
	private ModelRenderer glass = new ModelRenderer(this, "glass");
	private ModelRenderer base;
	
	public ModelEnderCrystal(float p_i5023_1_, boolean p_i5023_2_)
	{
		glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		cube = new ModelRenderer(this, "cube");
		cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
		if(p_i5023_2_)
		{
			base = new ModelRenderer(this, "base");
			base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
		}
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		GL11.glTranslatef(0.0F, -0.5F, 0.0F);
		if(base != null)
		{
			base.render(par7);
		}
		GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, 0.8F + par4, 0.0F);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		glass.render(par7);
		float var8 = 0.875F;
		GL11.glScalef(var8, var8, var8);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
		glass.render(par7);
		GL11.glScalef(var8, var8, var8);
		GL11.glRotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
		GL11.glRotatef(par3, 0.0F, 1.0F, 0.0F);
		cube.render(par7);
		GL11.glPopMatrix();
	}
}
