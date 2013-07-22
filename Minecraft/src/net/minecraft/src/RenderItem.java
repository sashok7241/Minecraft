package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

public class RenderItem extends Render
{
	private static final ResourceLocation field_110798_h = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	private RenderBlocks itemRenderBlocks = new RenderBlocks();
	private Random random = new Random();
	public boolean renderWithColor = true;
	public float zLevel;
	public static boolean renderInFrame;
	
	public RenderItem()
	{
		shadowSize = 0.15F;
		shadowOpaque = 0.75F;
	}
	
	@Override public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		doRenderItem((EntityItem) par1Entity, par2, par4, par6, par8, par9);
	}
	
	public void doRenderItem(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9)
	{
		func_110777_b(par1EntityItem);
		random.setSeed(187L);
		ItemStack var10 = par1EntityItem.getEntityItem();
		if(var10.getItem() != null)
		{
			GL11.glPushMatrix();
			float var11 = MathHelper.sin((par1EntityItem.age + par9) / 10.0F + par1EntityItem.hoverStart) * 0.1F + 0.1F;
			float var12 = ((par1EntityItem.age + par9) / 20.0F + par1EntityItem.hoverStart) * (180F / (float) Math.PI);
			byte var13 = 1;
			if(par1EntityItem.getEntityItem().stackSize > 1)
			{
				var13 = 2;
			}
			if(par1EntityItem.getEntityItem().stackSize > 5)
			{
				var13 = 3;
			}
			if(par1EntityItem.getEntityItem().stackSize > 20)
			{
				var13 = 4;
			}
			if(par1EntityItem.getEntityItem().stackSize > 40)
			{
				var13 = 5;
			}
			GL11.glTranslatef((float) par2, (float) par4 + var11, (float) par6);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			float var19;
			float var18;
			float var20;
			int var26;
			if(var10.getItemSpriteNumber() == 0 && var10.itemID < Block.blocksList.length && Block.blocksList[var10.itemID] != null && RenderBlocks.renderItemIn3d(Block.blocksList[var10.itemID].getRenderType()))
			{
				Block var21 = Block.blocksList[var10.itemID];
				GL11.glRotatef(var12, 0.0F, 1.0F, 0.0F);
				if(renderInFrame)
				{
					GL11.glScalef(1.25F, 1.25F, 1.25F);
					GL11.glTranslatef(0.0F, 0.05F, 0.0F);
					GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				}
				float var25 = 0.25F;
				int var24 = var21.getRenderType();
				if(var24 == 1 || var24 == 19 || var24 == 12 || var24 == 2)
				{
					var25 = 0.5F;
				}
				GL11.glScalef(var25, var25, var25);
				for(var26 = 0; var26 < var13; ++var26)
				{
					GL11.glPushMatrix();
					if(var26 > 0)
					{
						var18 = (random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
						var19 = (random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
						var20 = (random.nextFloat() * 2.0F - 1.0F) * 0.2F / var25;
						GL11.glTranslatef(var18, var19, var20);
					}
					var18 = 1.0F;
					itemRenderBlocks.renderBlockAsItem(var21, var10.getItemDamage(), var18);
					GL11.glPopMatrix();
				}
			} else
			{
				float var16;
				if(var10.getItemSpriteNumber() == 1 && var10.getItem().requiresMultipleRenderPasses())
				{
					if(renderInFrame)
					{
						GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
						GL11.glTranslatef(0.0F, -0.05F, 0.0F);
					} else
					{
						GL11.glScalef(0.5F, 0.5F, 0.5F);
					}
					for(int var23 = 0; var23 <= 1; ++var23)
					{
						random.setSeed(187L);
						Icon var22 = var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var23);
						var16 = 1.0F;
						if(renderWithColor)
						{
							var26 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, var23);
							var18 = (var26 >> 16 & 255) / 255.0F;
							var19 = (var26 >> 8 & 255) / 255.0F;
							var20 = (var26 & 255) / 255.0F;
							GL11.glColor4f(var18 * var16, var19 * var16, var20 * var16, 1.0F);
							renderDroppedItem(par1EntityItem, var22, var13, par9, var18 * var16, var19 * var16, var20 * var16);
						} else
						{
							renderDroppedItem(par1EntityItem, var22, var13, par9, 1.0F, 1.0F, 1.0F);
						}
					}
				} else
				{
					if(renderInFrame)
					{
						GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
						GL11.glTranslatef(0.0F, -0.05F, 0.0F);
					} else
					{
						GL11.glScalef(0.5F, 0.5F, 0.5F);
					}
					Icon var14 = var10.getIconIndex();
					if(renderWithColor)
					{
						int var15 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, 0);
						var16 = (var15 >> 16 & 255) / 255.0F;
						float var17 = (var15 >> 8 & 255) / 255.0F;
						var18 = (var15 & 255) / 255.0F;
						var19 = 1.0F;
						renderDroppedItem(par1EntityItem, var14, var13, par9, var16 * var19, var17 * var19, var18 * var19);
					} else
					{
						renderDroppedItem(par1EntityItem, var14, var13, par9, 1.0F, 1.0F, 1.0F);
					}
				}
			}
			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			GL11.glPopMatrix();
		}
	}
	
	@Override protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return func_110796_a((EntityItem) par1Entity);
	}
	
	protected ResourceLocation func_110796_a(EntityItem par1EntityItem)
	{
		return renderManager.renderEngine.func_130087_a(par1EntityItem.getEntityItem().getItemSpriteNumber());
	}
	
	private void renderDroppedItem(EntityItem par1EntityItem, Icon par2Icon, int par3, float par4, float par5, float par6, float par7)
	{
		Tessellator var8 = Tessellator.instance;
		if(par2Icon == null)
		{
			TextureManager var9 = Minecraft.getMinecraft().func_110434_K();
			ResourceLocation var10 = var9.func_130087_a(par1EntityItem.getEntityItem().getItemSpriteNumber());
			par2Icon = ((TextureMap) var9.func_110581_b(var10)).func_110572_b("missingno");
		}
		float var25 = par2Icon.getMinU();
		float var26 = par2Icon.getMaxU();
		float var11 = par2Icon.getMinV();
		float var12 = par2Icon.getMaxV();
		float var13 = 1.0F;
		float var14 = 0.5F;
		float var15 = 0.25F;
		float var17;
		if(renderManager.options.fancyGraphics)
		{
			GL11.glPushMatrix();
			if(renderInFrame)
			{
				GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
			} else
			{
				GL11.glRotatef(((par1EntityItem.age + par4) / 20.0F + par1EntityItem.hoverStart) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
			}
			float var16 = 0.0625F;
			var17 = 0.021875F;
			ItemStack var18 = par1EntityItem.getEntityItem();
			int var19 = var18.stackSize;
			byte var24;
			if(var19 < 2)
			{
				var24 = 1;
			} else if(var19 < 16)
			{
				var24 = 2;
			} else if(var19 < 32)
			{
				var24 = 3;
			} else
			{
				var24 = 4;
			}
			GL11.glTranslatef(-var14, -var15, -((var16 + var17) * var24 / 2.0F));
			for(int var20 = 0; var20 < var24; ++var20)
			{
				GL11.glTranslatef(0.0F, 0.0F, var16 + var17);
				if(var18.getItemSpriteNumber() == 0 && Block.blocksList[var18.itemID] != null)
				{
					func_110776_a(TextureMap.field_110575_b);
				} else
				{
					func_110776_a(TextureMap.field_110576_c);
				}
				GL11.glColor4f(par5, par6, par7, 1.0F);
				ItemRenderer.renderItemIn2D(var8, var26, var11, var25, var12, par2Icon.getOriginX(), par2Icon.getOriginY(), var16);
				if(var18.hasEffect())
				{
					GL11.glDepthFunc(GL11.GL_EQUAL);
					GL11.glDisable(GL11.GL_LIGHTING);
					renderManager.renderEngine.func_110577_a(field_110798_h);
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
					float var21 = 0.76F;
					GL11.glColor4f(0.5F * var21, 0.25F * var21, 0.8F * var21, 1.0F);
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glPushMatrix();
					float var22 = 0.125F;
					GL11.glScalef(var22, var22, var22);
					float var23 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
					GL11.glTranslatef(var23, 0.0F, 0.0F);
					GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
					ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
					GL11.glPopMatrix();
					GL11.glPushMatrix();
					GL11.glScalef(var22, var22, var22);
					var23 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
					GL11.glTranslatef(-var23, 0.0F, 0.0F);
					GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
					ItemRenderer.renderItemIn2D(var8, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, var16);
					GL11.glPopMatrix();
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDepthFunc(GL11.GL_LEQUAL);
				}
			}
			GL11.glPopMatrix();
		} else
		{
			for(int var27 = 0; var27 < par3; ++var27)
			{
				GL11.glPushMatrix();
				if(var27 > 0)
				{
					var17 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float var29 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float var28 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					GL11.glTranslatef(var17, var29, var28);
				}
				if(!renderInFrame)
				{
					GL11.glRotatef(180.0F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
				}
				GL11.glColor4f(par5, par6, par7, 1.0F);
				var8.startDrawingQuads();
				var8.setNormal(0.0F, 1.0F, 0.0F);
				var8.addVertexWithUV(0.0F - var14, 0.0F - var15, 0.0D, var25, var12);
				var8.addVertexWithUV(var13 - var14, 0.0F - var15, 0.0D, var26, var12);
				var8.addVertexWithUV(var13 - var14, 1.0F - var15, 0.0D, var26, var11);
				var8.addVertexWithUV(0.0F - var14, 1.0F - var15, 0.0D, var25, var11);
				var8.draw();
				GL11.glPopMatrix();
			}
		}
	}
	
	private void renderGlint(int par1, int par2, int par3, int par4, int par5)
	{
		for(int var6 = 0; var6 < 2; ++var6)
		{
			if(var6 == 0)
			{
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}
			if(var6 == 1)
			{
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}
			float var7 = 0.00390625F;
			float var8 = 0.00390625F;
			float var9 = Minecraft.getSystemTime() % (3000 + var6 * 1873) / (3000.0F + var6 * 1873) * 256.0F;
			float var10 = 0.0F;
			Tessellator var11 = Tessellator.instance;
			float var12 = 4.0F;
			if(var6 == 1)
			{
				var12 = -1.0F;
			}
			var11.startDrawingQuads();
			var11.addVertexWithUV(par2 + 0, par3 + par5, zLevel, (var9 + par5 * var12) * var7, (var10 + par5) * var8);
			var11.addVertexWithUV(par2 + par4, par3 + par5, zLevel, (var9 + par4 + par5 * var12) * var7, (var10 + par5) * var8);
			var11.addVertexWithUV(par2 + par4, par3 + 0, zLevel, (var9 + par4) * var7, (var10 + 0.0F) * var8);
			var11.addVertexWithUV(par2 + 0, par3 + 0, zLevel, (var9 + 0.0F) * var7, (var10 + 0.0F) * var8);
			var11.draw();
		}
	}
	
	public void renderIcon(int par1, int par2, Icon par3Icon, int par4, int par5)
	{
		Tessellator var6 = Tessellator.instance;
		var6.startDrawingQuads();
		var6.addVertexWithUV(par1 + 0, par2 + par5, zLevel, par3Icon.getMinU(), par3Icon.getMaxV());
		var6.addVertexWithUV(par1 + par4, par2 + par5, zLevel, par3Icon.getMaxU(), par3Icon.getMaxV());
		var6.addVertexWithUV(par1 + par4, par2 + 0, zLevel, par3Icon.getMaxU(), par3Icon.getMinV());
		var6.addVertexWithUV(par1 + 0, par2 + 0, zLevel, par3Icon.getMinU(), par3Icon.getMinV());
		var6.draw();
	}
	
	public void renderItemAndEffectIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
	{
		if(par3ItemStack != null)
		{
			renderItemIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5);
			if(par3ItemStack.hasEffect())
			{
				GL11.glDepthFunc(GL11.GL_GREATER);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDepthMask(false);
				par2TextureManager.func_110577_a(field_110798_h);
				zLevel -= 50.0F;
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_DST_COLOR);
				GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
				renderGlint(par4 * 431278612 + par5 * 32178161, par4 - 2, par5 - 2, 20, 20);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthMask(true);
				zLevel += 50.0F;
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}
		}
	}
	
	public void renderItemIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
	{
		int var6 = par3ItemStack.itemID;
		int var7 = par3ItemStack.getItemDamage();
		Object var8 = par3ItemStack.getIconIndex();
		float var17;
		int var18;
		float var12;
		float var13;
		if(par3ItemStack.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.blocksList[var6].getRenderType()))
		{
			par2TextureManager.func_110577_a(TextureMap.field_110575_b);
			Block var15 = Block.blocksList[var6];
			GL11.glPushMatrix();
			GL11.glTranslatef((float) (par4 - 2), (float) (par5 + 3), -3.0F + zLevel);
			GL11.glScalef(10.0F, 10.0F, 10.0F);
			GL11.glTranslatef(1.0F, 0.5F, 1.0F);
			GL11.glScalef(1.0F, 1.0F, -1.0F);
			GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			var18 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
			var17 = (var18 >> 16 & 255) / 255.0F;
			var12 = (var18 >> 8 & 255) / 255.0F;
			var13 = (var18 & 255) / 255.0F;
			if(renderWithColor)
			{
				GL11.glColor4f(var17, var12, var13, 1.0F);
			}
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			itemRenderBlocks.useInventoryTint = renderWithColor;
			itemRenderBlocks.renderBlockAsItem(var15, var7, 1.0F);
			itemRenderBlocks.useInventoryTint = true;
			GL11.glPopMatrix();
		} else if(Item.itemsList[var6].requiresMultipleRenderPasses())
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			par2TextureManager.func_110577_a(TextureMap.field_110576_c);
			for(int var9 = 0; var9 <= 1; ++var9)
			{
				Icon var10 = Item.itemsList[var6].getIconFromDamageForRenderPass(var7, var9);
				int var11 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, var9);
				var12 = (var11 >> 16 & 255) / 255.0F;
				var13 = (var11 >> 8 & 255) / 255.0F;
				float var14 = (var11 & 255) / 255.0F;
				if(renderWithColor)
				{
					GL11.glColor4f(var12, var13, var14, 1.0F);
				}
				renderIcon(par4, par5, var10, 16, 16);
			}
			GL11.glEnable(GL11.GL_LIGHTING);
		} else
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			ResourceLocation var16 = par2TextureManager.func_130087_a(par3ItemStack.getItemSpriteNumber());
			par2TextureManager.func_110577_a(var16);
			if(var8 == null)
			{
				var8 = ((TextureMap) Minecraft.getMinecraft().func_110434_K().func_110581_b(var16)).func_110572_b("missingno");
			}
			var18 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
			var17 = (var18 >> 16 & 255) / 255.0F;
			var12 = (var18 >> 8 & 255) / 255.0F;
			var13 = (var18 & 255) / 255.0F;
			if(renderWithColor)
			{
				GL11.glColor4f(var17, var12, var13, 1.0F);
			}
			renderIcon(par4, par5, (Icon) var8, 16, 16);
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5)
	{
		this.renderItemOverlayIntoGUI(par1FontRenderer, par2TextureManager, par3ItemStack, par4, par5, (String) null);
	}
	
	public void renderItemOverlayIntoGUI(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, String par6Str)
	{
		if(par3ItemStack != null)
		{
			if(par3ItemStack.stackSize > 1 || par6Str != null)
			{
				String var7 = par6Str == null ? String.valueOf(par3ItemStack.stackSize) : par6Str;
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				par1FontRenderer.drawStringWithShadow(var7, par4 + 19 - 2 - par1FontRenderer.getStringWidth(var7), par5 + 6 + 3, 16777215);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
			if(par3ItemStack.isItemDamaged())
			{
				int var12 = (int) Math.round(13.0D - par3ItemStack.getItemDamageForDisplay() * 13.0D / par3ItemStack.getMaxDamage());
				int var8 = (int) Math.round(255.0D - par3ItemStack.getItemDamageForDisplay() * 255.0D / par3ItemStack.getMaxDamage());
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				Tessellator var9 = Tessellator.instance;
				int var10 = 255 - var8 << 16 | var8 << 8;
				int var11 = (255 - var8) / 4 << 16 | 16128;
				renderQuad(var9, par4 + 2, par5 + 13, 13, 2, 0);
				renderQuad(var9, par4 + 2, par5 + 13, 12, 1, var11);
				renderQuad(var9, par4 + 2, par5 + 13, var12, 1, var10);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			}
		}
	}
	
	private void renderQuad(Tessellator par1Tessellator, int par2, int par3, int par4, int par5, int par6)
	{
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setColorOpaque_I(par6);
		par1Tessellator.addVertex(par2 + 0, par3 + 0, 0.0D);
		par1Tessellator.addVertex(par2 + 0, par3 + par5, 0.0D);
		par1Tessellator.addVertex(par2 + par4, par3 + par5, 0.0D);
		par1Tessellator.addVertex(par2 + par4, par3 + 0, 0.0D);
		par1Tessellator.draw();
	}
}
