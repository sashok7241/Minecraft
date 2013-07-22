package net.minecraft.src;

public class ModelLeashKnot extends ModelBase
{
	public ModelRenderer field_110723_a;
	
	public ModelLeashKnot()
	{
		this(0, 0, 32, 32);
	}
	
	public ModelLeashKnot(int par1, int par2, int par3, int par4)
	{
		textureWidth = par3;
		textureHeight = par4;
		field_110723_a = new ModelRenderer(this, par1, par2);
		field_110723_a.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
		field_110723_a.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		field_110723_a.render(par7);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		field_110723_a.rotateAngleY = par4 / (180F / (float) Math.PI);
		field_110723_a.rotateAngleX = par5 / (180F / (float) Math.PI);
	}
}
