package net.minecraft.src;

public class ModelBox
{
	private PositionTextureVertex[] vertexPositions;
	private TexturedQuad[] quadList;
	public final float posX1;
	public final float posY1;
	public final float posZ1;
	public final float posX2;
	public final float posY2;
	public final float posZ2;
	public String field_78247_g;
	
	public ModelBox(ModelRenderer p_i3145_1_, int p_i3145_2_, int p_i3145_3_, float p_i3145_4_, float p_i3145_5_, float p_i3145_6_, int p_i3145_7_, int p_i3145_8_, int p_i3145_9_, float p_i3145_10_)
	{
		posX1 = p_i3145_4_;
		posY1 = p_i3145_5_;
		posZ1 = p_i3145_6_;
		posX2 = p_i3145_4_ + p_i3145_7_;
		posY2 = p_i3145_5_ + p_i3145_8_;
		posZ2 = p_i3145_6_ + p_i3145_9_;
		vertexPositions = new PositionTextureVertex[8];
		quadList = new TexturedQuad[6];
		float var11 = p_i3145_4_ + p_i3145_7_;
		float var12 = p_i3145_5_ + p_i3145_8_;
		float var13 = p_i3145_6_ + p_i3145_9_;
		p_i3145_4_ -= p_i3145_10_;
		p_i3145_5_ -= p_i3145_10_;
		p_i3145_6_ -= p_i3145_10_;
		var11 += p_i3145_10_;
		var12 += p_i3145_10_;
		var13 += p_i3145_10_;
		if(p_i3145_1_.mirror)
		{
			float var14 = var11;
			var11 = p_i3145_4_;
			p_i3145_4_ = var14;
		}
		PositionTextureVertex var23 = new PositionTextureVertex(p_i3145_4_, p_i3145_5_, p_i3145_6_, 0.0F, 0.0F);
		PositionTextureVertex var15 = new PositionTextureVertex(var11, p_i3145_5_, p_i3145_6_, 0.0F, 8.0F);
		PositionTextureVertex var16 = new PositionTextureVertex(var11, var12, p_i3145_6_, 8.0F, 8.0F);
		PositionTextureVertex var17 = new PositionTextureVertex(p_i3145_4_, var12, p_i3145_6_, 8.0F, 0.0F);
		PositionTextureVertex var18 = new PositionTextureVertex(p_i3145_4_, p_i3145_5_, var13, 0.0F, 0.0F);
		PositionTextureVertex var19 = new PositionTextureVertex(var11, p_i3145_5_, var13, 0.0F, 8.0F);
		PositionTextureVertex var20 = new PositionTextureVertex(var11, var12, var13, 8.0F, 8.0F);
		PositionTextureVertex var21 = new PositionTextureVertex(p_i3145_4_, var12, var13, 8.0F, 0.0F);
		vertexPositions[0] = var23;
		vertexPositions[1] = var15;
		vertexPositions[2] = var16;
		vertexPositions[3] = var17;
		vertexPositions[4] = var18;
		vertexPositions[5] = var19;
		vertexPositions[6] = var20;
		vertexPositions[7] = var21;
		quadList[0] = new TexturedQuad(new PositionTextureVertex[] { var19, var15, var16, var20 }, p_i3145_2_ + p_i3145_9_ + p_i3145_7_, p_i3145_3_ + p_i3145_9_, p_i3145_2_ + p_i3145_9_ + p_i3145_7_ + p_i3145_9_, p_i3145_3_ + p_i3145_9_ + p_i3145_8_, p_i3145_1_.textureWidth, p_i3145_1_.textureHeight);
		quadList[1] = new TexturedQuad(new PositionTextureVertex[] { var23, var18, var21, var17 }, p_i3145_2_, p_i3145_3_ + p_i3145_9_, p_i3145_2_ + p_i3145_9_, p_i3145_3_ + p_i3145_9_ + p_i3145_8_, p_i3145_1_.textureWidth, p_i3145_1_.textureHeight);
		quadList[2] = new TexturedQuad(new PositionTextureVertex[] { var19, var18, var23, var15 }, p_i3145_2_ + p_i3145_9_, p_i3145_3_, p_i3145_2_ + p_i3145_9_ + p_i3145_7_, p_i3145_3_ + p_i3145_9_, p_i3145_1_.textureWidth, p_i3145_1_.textureHeight);
		quadList[3] = new TexturedQuad(new PositionTextureVertex[] { var16, var17, var21, var20 }, p_i3145_2_ + p_i3145_9_ + p_i3145_7_, p_i3145_3_ + p_i3145_9_, p_i3145_2_ + p_i3145_9_ + p_i3145_7_ + p_i3145_7_, p_i3145_3_, p_i3145_1_.textureWidth, p_i3145_1_.textureHeight);
		quadList[4] = new TexturedQuad(new PositionTextureVertex[] { var15, var23, var17, var16 }, p_i3145_2_ + p_i3145_9_, p_i3145_3_ + p_i3145_9_, p_i3145_2_ + p_i3145_9_ + p_i3145_7_, p_i3145_3_ + p_i3145_9_ + p_i3145_8_, p_i3145_1_.textureWidth, p_i3145_1_.textureHeight);
		quadList[5] = new TexturedQuad(new PositionTextureVertex[] { var18, var19, var20, var21 }, p_i3145_2_ + p_i3145_9_ + p_i3145_7_ + p_i3145_9_, p_i3145_3_ + p_i3145_9_, p_i3145_2_ + p_i3145_9_ + p_i3145_7_ + p_i3145_9_ + p_i3145_7_, p_i3145_3_ + p_i3145_9_ + p_i3145_8_, p_i3145_1_.textureWidth, p_i3145_1_.textureHeight);
		if(p_i3145_1_.mirror)
		{
			for(TexturedQuad element : quadList)
			{
				element.flipFace();
			}
		}
	}
	
	public ModelBox func_78244_a(String par1Str)
	{
		field_78247_g = par1Str;
		return this;
	}
	
	public void render(Tessellator par1Tessellator, float par2)
	{
		for(TexturedQuad element : quadList)
		{
			element.draw(par1Tessellator, par2);
		}
	}
}
