package net.minecraft.src;

public class ModelVillager extends ModelBase
{
	public ModelRenderer villagerHead;
	public ModelRenderer villagerBody;
	public ModelRenderer villagerArms;
	public ModelRenderer rightVillagerLeg;
	public ModelRenderer leftVillagerLeg;
	public ModelRenderer villagerNose;
	
	public ModelVillager(float p_i3138_1_)
	{
		this(p_i3138_1_, 0.0F, 64, 64);
	}
	
	public ModelVillager(float p_i5022_1_, float p_i5022_2_, int p_i5022_3_, int p_i5022_4_)
	{
		villagerHead = new ModelRenderer(this).setTextureSize(p_i5022_3_, p_i5022_4_);
		villagerHead.setRotationPoint(0.0F, 0.0F + p_i5022_2_, 0.0F);
		villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i5022_1_);
		villagerNose = new ModelRenderer(this).setTextureSize(p_i5022_3_, p_i5022_4_);
		villagerNose.setRotationPoint(0.0F, p_i5022_2_ - 2.0F, 0.0F);
		villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i5022_1_);
		villagerHead.addChild(villagerNose);
		villagerBody = new ModelRenderer(this).setTextureSize(p_i5022_3_, p_i5022_4_);
		villagerBody.setRotationPoint(0.0F, 0.0F + p_i5022_2_, 0.0F);
		villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i5022_1_);
		villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i5022_1_ + 0.5F);
		villagerArms = new ModelRenderer(this).setTextureSize(p_i5022_3_, p_i5022_4_);
		villagerArms.setRotationPoint(0.0F, 0.0F + p_i5022_2_ + 2.0F, 0.0F);
		villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i5022_1_);
		villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i5022_1_);
		villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i5022_1_);
		rightVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(p_i5022_3_, p_i5022_4_);
		rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + p_i5022_2_, 0.0F);
		rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i5022_1_);
		leftVillagerLeg = new ModelRenderer(this, 0, 22).setTextureSize(p_i5022_3_, p_i5022_4_);
		leftVillagerLeg.mirror = true;
		leftVillagerLeg.setRotationPoint(2.0F, 12.0F + p_i5022_2_, 0.0F);
		leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i5022_1_);
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		villagerHead.render(par7);
		villagerBody.render(par7);
		rightVillagerLeg.render(par7);
		leftVillagerLeg.render(par7);
		villagerArms.render(par7);
	}
	
	@Override public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		villagerHead.rotateAngleY = par4 / (180F / (float) Math.PI);
		villagerHead.rotateAngleX = par5 / (180F / (float) Math.PI);
		villagerArms.rotationPointY = 3.0F;
		villagerArms.rotationPointZ = -1.0F;
		villagerArms.rotateAngleX = -0.75F;
		rightVillagerLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2 * 0.5F;
		leftVillagerLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 1.4F * par2 * 0.5F;
		rightVillagerLeg.rotateAngleY = 0.0F;
		leftVillagerLeg.rotateAngleY = 0.0F;
	}
}
