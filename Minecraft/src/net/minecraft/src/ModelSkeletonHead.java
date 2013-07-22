package net.minecraft.src;

public class ModelSkeletonHead extends ModelBase
{
	public ModelRenderer skeletonHead;
	
	public ModelSkeletonHead()
	{
		this(0, 35, 64, 64);
	}
	
	public ModelSkeletonHead(int p_i5015_1_, int p_i5015_2_, int p_i5015_3_, int p_i5015_4_)
	{
		textureWidth = p_i5015_3_;
		textureHeight = p_i5015_4_;
		skeletonHead = new ModelRenderer(this, p_i5015_1_, p_i5015_2_);
		skeletonHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
		skeletonHead.setRotationPoint(0.0F, 0.0F, 0.0F);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		skeletonHead.render(par7);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
		skeletonHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		skeletonHead.rotateAngleX = par5 / (180F / (float) Math.PI);
	}
}
