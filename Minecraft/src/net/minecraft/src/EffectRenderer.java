package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EffectRenderer
{
	protected World worldObj;
	private List[] fxLayers = new List[4];
	private RenderEngine renderer;
	private Random rand = new Random();
	
	public EffectRenderer(World p_i3170_1_, RenderEngine p_i3170_2_)
	{
		if(p_i3170_1_ != null)
		{
			worldObj = p_i3170_1_;
		}
		renderer = p_i3170_2_;
		for(int var3 = 0; var3 < 4; ++var3)
		{
			fxLayers[var3] = new ArrayList();
		}
	}
	
	public void addBlockDestroyEffects(int par1, int par2, int par3, int par4, int par5)
	{
		if(par4 != 0)
		{
			Block var6 = Block.blocksList[par4];
			byte var7 = 4;
			for(int var8 = 0; var8 < var7; ++var8)
			{
				for(int var9 = 0; var9 < var7; ++var9)
				{
					for(int var10 = 0; var10 < var7; ++var10)
					{
						double var11 = par1 + (var8 + 0.5D) / var7;
						double var13 = par2 + (var9 + 0.5D) / var7;
						double var15 = par3 + (var10 + 0.5D) / var7;
						int var17 = rand.nextInt(6);
						addEffect(new EntityDiggingFX(worldObj, var11, var13, var15, var11 - par1 - 0.5D, var13 - par2 - 0.5D, var15 - par3 - 0.5D, var6, var17, par5, renderer).applyColourMultiplier(par1, par2, par3));
					}
				}
			}
		}
	}
	
	public void addBlockHitEffects(int par1, int par2, int par3, int par4)
	{
		int var5 = worldObj.getBlockId(par1, par2, par3);
		if(var5 != 0)
		{
			Block var6 = Block.blocksList[var5];
			float var7 = 0.1F;
			double var8 = par1 + rand.nextDouble() * (var6.getBlockBoundsMaxX() - var6.getBlockBoundsMinX() - var7 * 2.0F) + var7 + var6.getBlockBoundsMinX();
			double var10 = par2 + rand.nextDouble() * (var6.getBlockBoundsMaxY() - var6.getBlockBoundsMinY() - var7 * 2.0F) + var7 + var6.getBlockBoundsMinY();
			double var12 = par3 + rand.nextDouble() * (var6.getBlockBoundsMaxZ() - var6.getBlockBoundsMinZ() - var7 * 2.0F) + var7 + var6.getBlockBoundsMinZ();
			if(par4 == 0)
			{
				var10 = par2 + var6.getBlockBoundsMinY() - var7;
			}
			if(par4 == 1)
			{
				var10 = par2 + var6.getBlockBoundsMaxY() + var7;
			}
			if(par4 == 2)
			{
				var12 = par3 + var6.getBlockBoundsMinZ() - var7;
			}
			if(par4 == 3)
			{
				var12 = par3 + var6.getBlockBoundsMaxZ() + var7;
			}
			if(par4 == 4)
			{
				var8 = par1 + var6.getBlockBoundsMinX() - var7;
			}
			if(par4 == 5)
			{
				var8 = par1 + var6.getBlockBoundsMaxX() + var7;
			}
			addEffect(new EntityDiggingFX(worldObj, var8, var10, var12, 0.0D, 0.0D, 0.0D, var6, par4, worldObj.getBlockMetadata(par1, par2, par3), renderer).applyColourMultiplier(par1, par2, par3).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
		}
	}
	
	public void addEffect(EntityFX par1EntityFX)
	{
		int var2 = par1EntityFX.getFXLayer();
		if(fxLayers[var2].size() >= 4000)
		{
			fxLayers[var2].remove(0);
		}
		fxLayers[var2].add(par1EntityFX);
	}
	
	public void clearEffects(World par1World)
	{
		worldObj = par1World;
		for(int var2 = 0; var2 < 4; ++var2)
		{
			fxLayers[var2].clear();
		}
	}
	
	public String getStatistics()
	{
		return "" + (fxLayers[0].size() + fxLayers[1].size() + fxLayers[2].size());
	}
	
	public void renderLitParticles(Entity par1Entity, float par2)
	{
		float var4 = MathHelper.cos(par1Entity.rotationYaw * 0.017453292F);
		float var5 = MathHelper.sin(par1Entity.rotationYaw * 0.017453292F);
		float var6 = -var5 * MathHelper.sin(par1Entity.rotationPitch * 0.017453292F);
		float var7 = var4 * MathHelper.sin(par1Entity.rotationPitch * 0.017453292F);
		float var8 = MathHelper.cos(par1Entity.rotationPitch * 0.017453292F);
		byte var9 = 3;
		if(!fxLayers[var9].isEmpty())
		{
			Tessellator var10 = Tessellator.instance;
			for(int var11 = 0; var11 < fxLayers[var9].size(); ++var11)
			{
				EntityFX var12 = (EntityFX) fxLayers[var9].get(var11);
				var10.setBrightness(var12.getBrightnessForRender(par2));
				var12.renderParticle(var10, par2, var4, var8, var5, var6, var7);
			}
		}
	}
	
	public void renderParticles(Entity par1Entity, float par2)
	{
		float var3 = ActiveRenderInfo.rotationX;
		float var4 = ActiveRenderInfo.rotationZ;
		float var5 = ActiveRenderInfo.rotationYZ;
		float var6 = ActiveRenderInfo.rotationXY;
		float var7 = ActiveRenderInfo.rotationXZ;
		EntityFX.interpPosX = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * par2;
		EntityFX.interpPosY = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * par2;
		EntityFX.interpPosZ = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * par2;
		for(int var8 = 0; var8 < 3; ++var8)
		{
			if(!fxLayers[var8].isEmpty())
			{
				switch(var8)
				{
					case 0:
					default:
						renderer.bindTexture("/particles.png");
						break;
					case 1:
						renderer.bindTexture("/terrain.png");
						break;
					case 2:
						renderer.bindTexture("/gui/items.png");
				}
				Tessellator var9 = Tessellator.instance;
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDepthMask(false);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
				var9.startDrawingQuads();
				for(int var10 = 0; var10 < fxLayers[var8].size(); ++var10)
				{
					EntityFX var11 = (EntityFX) fxLayers[var8].get(var10);
					var9.setBrightness(var11.getBrightnessForRender(par2));
					var11.renderParticle(var9, par2, var3, var7, var4, var5, var6);
				}
				var9.draw();
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
				GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			}
		}
	}
	
	public void updateEffects()
	{
		for(int var1 = 0; var1 < 4; ++var1)
		{
			for(int var2 = 0; var2 < fxLayers[var1].size(); ++var2)
			{
				EntityFX var3 = (EntityFX) fxLayers[var1].get(var2);
				var3.onUpdate();
				if(var3.isDead)
				{
					fxLayers[var1].remove(var2--);
				}
			}
		}
	}
}
