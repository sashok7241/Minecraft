package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class GuiMerchant extends GuiContainer
{
	private static final ResourceLocation field_110418_t = new ResourceLocation("textures/gui/container/villager.png");
	private IMerchant theIMerchant;
	private GuiButtonMerchant nextRecipeButtonIndex;
	private GuiButtonMerchant previousRecipeButtonIndex;
	private int currentRecipeIndex;
	private String field_94082_v;
	
	public GuiMerchant(InventoryPlayer par1InventoryPlayer, IMerchant par2IMerchant, World par3World, String par4Str)
	{
		super(new ContainerMerchant(par1InventoryPlayer, par2IMerchant, par3World));
		theIMerchant = par2IMerchant;
		field_94082_v = par4Str != null && par4Str.length() >= 1 ? par4Str : I18n.func_135053_a("entity.Villager.name");
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		boolean var2 = false;
		if(par1GuiButton == nextRecipeButtonIndex)
		{
			++currentRecipeIndex;
			var2 = true;
		} else if(par1GuiButton == previousRecipeButtonIndex)
		{
			--currentRecipeIndex;
			var2 = true;
		}
		if(var2)
		{
			((ContainerMerchant) inventorySlots).setCurrentRecipeIndex(currentRecipeIndex);
			ByteArrayOutputStream var3 = new ByteArrayOutputStream();
			DataOutputStream var4 = new DataOutputStream(var3);
			try
			{
				var4.writeInt(currentRecipeIndex);
				mc.getNetHandler().addToSendQueue(new Packet250CustomPayload("MC|TrSel", var3.toByteArray()));
			} catch(Exception var6)
			{
				var6.printStackTrace();
			}
		}
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.func_110434_K().func_110577_a(field_110418_t);
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		MerchantRecipeList var6 = theIMerchant.getRecipes(mc.thePlayer);
		if(var6 != null && !var6.isEmpty())
		{
			int var7 = currentRecipeIndex;
			MerchantRecipe var8 = (MerchantRecipe) var6.get(var7);
			if(var8.func_82784_g())
			{
				mc.func_110434_K().func_110577_a(field_110418_t);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				drawTexturedModalRect(guiLeft + 83, guiTop + 21, 212, 0, 28, 21);
				drawTexturedModalRect(guiLeft + 83, guiTop + 51, 212, 0, 28, 21);
			}
		}
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		fontRenderer.drawString(field_94082_v, xSize / 2 - fontRenderer.getStringWidth(field_94082_v) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, ySize - 96 + 2, 4210752);
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		MerchantRecipeList var4 = theIMerchant.getRecipes(mc.thePlayer);
		if(var4 != null && !var4.isEmpty())
		{
			int var5 = (width - xSize) / 2;
			int var6 = (height - ySize) / 2;
			int var7 = currentRecipeIndex;
			MerchantRecipe var8 = (MerchantRecipe) var4.get(var7);
			GL11.glPushMatrix();
			ItemStack var9 = var8.getItemToBuy();
			ItemStack var10 = var8.getSecondItemToBuy();
			ItemStack var11 = var8.getItemToSell();
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
			GL11.glEnable(GL11.GL_LIGHTING);
			itemRenderer.zLevel = 100.0F;
			itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.func_110434_K(), var9, var5 + 36, var6 + 24);
			itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.func_110434_K(), var9, var5 + 36, var6 + 24);
			if(var10 != null)
			{
				itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.func_110434_K(), var10, var5 + 62, var6 + 24);
				itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.func_110434_K(), var10, var5 + 62, var6 + 24);
			}
			itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.func_110434_K(), var11, var5 + 120, var6 + 24);
			itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.func_110434_K(), var11, var5 + 120, var6 + 24);
			itemRenderer.zLevel = 0.0F;
			GL11.glDisable(GL11.GL_LIGHTING);
			if(isPointInRegion(36, 24, 16, 16, par1, par2))
			{
				drawItemStackTooltip(var9, par1, par2);
			} else if(var10 != null && isPointInRegion(62, 24, 16, 16, par1, par2))
			{
				drawItemStackTooltip(var10, par1, par2);
			} else if(isPointInRegion(120, 24, 16, 16, par1, par2))
			{
				drawItemStackTooltip(var11, par1, par2);
			}
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			RenderHelper.enableStandardItemLighting();
		}
	}
	
	public IMerchant getIMerchant()
	{
		return theIMerchant;
	}
	
	@Override public void initGui()
	{
		super.initGui();
		int var1 = (width - xSize) / 2;
		int var2 = (height - ySize) / 2;
		buttonList.add(nextRecipeButtonIndex = new GuiButtonMerchant(1, var1 + 120 + 27, var2 + 24 - 1, true));
		buttonList.add(previousRecipeButtonIndex = new GuiButtonMerchant(2, var1 + 36 - 19, var2 + 24 - 1, false));
		nextRecipeButtonIndex.enabled = false;
		previousRecipeButtonIndex.enabled = false;
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		MerchantRecipeList var1 = theIMerchant.getRecipes(mc.thePlayer);
		if(var1 != null)
		{
			nextRecipeButtonIndex.enabled = currentRecipeIndex < var1.size() - 1;
			previousRecipeButtonIndex.enabled = currentRecipeIndex > 0;
		}
	}
	
	static ResourceLocation func_110417_h()
	{
		return field_110418_t;
	}
}
