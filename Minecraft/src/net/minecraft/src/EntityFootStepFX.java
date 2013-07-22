package net.minecraft.src;


public class EntityFootStepFX extends EntityFX
{
	private int field_70576_a = 0;
	private int field_70578_aq = 0;
	private RenderEngine currentFootSteps;
	
	public EntityFootStepFX(RenderEngine p_i3168_1_, World p_i3168_2_, double p_i3168_3_, double p_i3168_5_, double p_i3168_7_)
	{
		super(p_i3168_2_, p_i3168_3_, p_i3168_5_, p_i3168_7_, 0.0D, 0.0D, 0.0D);
		currentFootSteps = p_i3168_1_;
		motionX = motionY = motionZ = 0.0D;
		field_70578_aq = 200;
	}
	
	@Override public int getFXLayer()
	{
		return 3;
	}
	
	@Override public void onUpdate()
	{
		++field_70576_a;
		if(field_70576_a == field_70578_aq)
		{
			setDead();
		}
	}
	
	@Override public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		float var8 = (field_70576_a + par2) / field_70578_aq;
		var8 *= var8;
		float var9 = 2.0F - var8 * 2.0F;
		if(var9 > 1.0F)
		{
			var9 = 1.0F;
		}
		var9 *= 0.2F;
		GL11.glDisable(GL11.GL_LIGHTING);
		float var10 = 0.125F;
		float var11 = (float) (posX - interpPosX);
		float var12 = (float) (posY - interpPosY);
		float var13 = (float) (posZ - interpPosZ);
		float var14 = worldObj.getLightBrightness(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
		currentFootSteps.bindTexture("/misc/footprint.png");
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setColorRGBA_F(var14, var14, var14, var9);
		par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 + var10, 0.0D, 1.0D);
		par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 + var10, 1.0D, 1.0D);
		par1Tessellator.addVertexWithUV(var11 + var10, var12, var13 - var10, 1.0D, 0.0D);
		par1Tessellator.addVertexWithUV(var11 - var10, var12, var13 - var10, 0.0D, 0.0D);
		par1Tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
