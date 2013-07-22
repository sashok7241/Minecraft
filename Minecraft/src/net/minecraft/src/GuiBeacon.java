package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

public class GuiBeacon extends GuiContainer
{
	private TileEntityBeacon beacon;
	private GuiBeaconButtonConfirm beaconConfirmButton;
	private boolean buttonsNotDrawn;
	
	public GuiBeacon(InventoryPlayer par1, TileEntityBeacon par2)
	{
		super(new ContainerBeacon(par1, par2));
		beacon = par2;
		xSize = 230;
		ySize = 219;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.id == -2)
		{
			mc.displayGuiScreen((GuiScreen) null);
		} else if(par1GuiButton.id == -1)
		{
			String var2 = "MC|Beacon";
			ByteArrayOutputStream var3 = new ByteArrayOutputStream();
			DataOutputStream var4 = new DataOutputStream(var3);
			try
			{
				var4.writeInt(beacon.getPrimaryEffect());
				var4.writeInt(beacon.getSecondaryEffect());
				mc.getNetHandler().addToSendQueue(new Packet250CustomPayload(var2, var3.toByteArray()));
			} catch(Exception var6)
			{
				var6.printStackTrace();
			}
			mc.displayGuiScreen((GuiScreen) null);
		} else if(par1GuiButton instanceof GuiBeaconButtonPower)
		{
			if(((GuiBeaconButtonPower) par1GuiButton).func_82255_b()) return;
			int var7 = par1GuiButton.id;
			int var8 = var7 & 255;
			int var9 = var7 >> 8;
			if(var9 < 3)
			{
				beacon.setPrimaryEffect(var8);
			} else
			{
				beacon.setSecondaryEffect(var8);
			}
			buttonList.clear();
			initGui();
			updateScreen();
		}
	}
	
	@Override protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/beacon.png");
		int var4 = (width - xSize) / 2;
		int var5 = (height - ySize) / 2;
		drawTexturedModalRect(var4, var5, 0, 0, xSize, ySize);
		itemRenderer.zLevel = 100.0F;
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, new ItemStack(Item.emerald), var4 + 42, var5 + 109);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, new ItemStack(Item.diamond), var4 + 42 + 22, var5 + 109);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, new ItemStack(Item.ingotGold), var4 + 42 + 44, var5 + 109);
		itemRenderer.renderItemAndEffectIntoGUI(fontRenderer, mc.renderEngine, new ItemStack(Item.ingotIron), var4 + 42 + 66, var5 + 109);
		itemRenderer.zLevel = 0.0F;
	}
	
	@Override protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		RenderHelper.disableStandardItemLighting();
		drawCenteredString(fontRenderer, StatCollector.translateToLocal("tile.beacon.primary"), 62, 10, 14737632);
		drawCenteredString(fontRenderer, StatCollector.translateToLocal("tile.beacon.secondary"), 169, 10, 14737632);
		Iterator var3 = buttonList.iterator();
		while(var3.hasNext())
		{
			GuiButton var4 = (GuiButton) var3.next();
			if(var4.func_82252_a())
			{
				var4.func_82251_b(par1 - guiLeft, par2 - guiTop);
				break;
			}
		}
		RenderHelper.enableGUIStandardItemLighting();
	}
	
	@Override public void initGui()
	{
		super.initGui();
		buttonList.add(beaconConfirmButton = new GuiBeaconButtonConfirm(this, -1, guiLeft + 164, guiTop + 107));
		buttonList.add(new GuiBeaconButtonCancel(this, -2, guiLeft + 190, guiTop + 107));
		buttonsNotDrawn = true;
		beaconConfirmButton.enabled = false;
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		if(buttonsNotDrawn && beacon.getLevels() >= 0)
		{
			buttonsNotDrawn = false;
			int var2;
			int var3;
			int var4;
			int var5;
			GuiBeaconButtonPower var6;
			for(int var1 = 0; var1 <= 2; ++var1)
			{
				var2 = TileEntityBeacon.effectsList[var1].length;
				var3 = var2 * 22 + (var2 - 1) * 2;
				for(var4 = 0; var4 < var2; ++var4)
				{
					var5 = TileEntityBeacon.effectsList[var1][var4].id;
					var6 = new GuiBeaconButtonPower(this, var1 << 8 | var5, guiLeft + 76 + var4 * 24 - var3 / 2, guiTop + 22 + var1 * 25, var5, var1);
					buttonList.add(var6);
					if(var1 >= beacon.getLevels())
					{
						var6.enabled = false;
					} else if(var5 == beacon.getPrimaryEffect())
					{
						var6.func_82254_b(true);
					}
				}
			}
			byte var7 = 3;
			var2 = TileEntityBeacon.effectsList[var7].length + 1;
			var3 = var2 * 22 + (var2 - 1) * 2;
			for(var4 = 0; var4 < var2 - 1; ++var4)
			{
				var5 = TileEntityBeacon.effectsList[var7][var4].id;
				var6 = new GuiBeaconButtonPower(this, var7 << 8 | var5, guiLeft + 167 + var4 * 24 - var3 / 2, guiTop + 47, var5, var7);
				buttonList.add(var6);
				if(var7 >= beacon.getLevels())
				{
					var6.enabled = false;
				} else if(var5 == beacon.getSecondaryEffect())
				{
					var6.func_82254_b(true);
				}
			}
			if(beacon.getPrimaryEffect() > 0)
			{
				GuiBeaconButtonPower var8 = new GuiBeaconButtonPower(this, var7 << 8 | beacon.getPrimaryEffect(), guiLeft + 167 + (var2 - 1) * 24 - var3 / 2, guiTop + 47, beacon.getPrimaryEffect(), var7);
				buttonList.add(var8);
				if(var7 >= beacon.getLevels())
				{
					var8.enabled = false;
				} else if(beacon.getPrimaryEffect() == beacon.getSecondaryEffect())
				{
					var8.func_82254_b(true);
				}
			}
		}
		beaconConfirmButton.enabled = beacon.getStackInSlot(0) != null && beacon.getPrimaryEffect() > 0;
	}
}
