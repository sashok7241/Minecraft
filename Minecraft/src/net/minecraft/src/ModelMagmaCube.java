package net.minecraft.src;

public class ModelMagmaCube extends ModelBase
{
	ModelRenderer[] field_78109_a = new ModelRenderer[8];
	ModelRenderer field_78108_b;
	
	public ModelMagmaCube()
	{
		for(int var1 = 0; var1 < field_78109_a.length; ++var1)
		{
			byte var2 = 0;
			int var3 = var1;
			if(var1 == 2)
			{
				var2 = 24;
				var3 = 10;
			} else if(var1 == 3)
			{
				var2 = 24;
				var3 = 19;
			}
			field_78109_a[var1] = new ModelRenderer(this, var2, var3);
			field_78109_a[var1].addBox(-4.0F, 16 + var1, -4.0F, 8, 1, 8);
		}
		field_78108_b = new ModelRenderer(this, 0, 16);
		field_78108_b.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
	}
	
	public int func_78107_a()
	{
		return 5;
	}
	
	@Override public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		field_78108_b.render(par7);
		for(ModelRenderer element : field_78109_a)
		{
			element.render(par7);
		}
	}
	
	@Override public void setLivingAnimations(EntityLiving par1EntityLivingBase, float par2, float par3, float par4)
	{
		EntityMagmaCube var5 = (EntityMagmaCube) par1EntityLivingBase;
		float var6 = var5.field_70812_c + (var5.field_70811_b - var5.field_70812_c) * par4;
		if(var6 < 0.0F)
		{
			var6 = 0.0F;
		}
		for(int var7 = 0; var7 < field_78109_a.length; ++var7)
		{
			field_78109_a[var7].rotationPointY = -(4 - var7) * var6 * 1.7F;
		}
	}
}
