package net.minecraft.src;

class GuiBeaconButtonConfirm extends GuiBeaconButton
{
	final GuiBeacon beaconGui;
	
	public GuiBeaconButtonConfirm(GuiBeacon par1, int par2, int par3, int par4)
	{
		super(par2, par3, par4, "/gui/beacon.png", 90, 220);
		beaconGui = par1;
	}
	
	@Override public void func_82251_b(int par1, int par2)
	{
		beaconGui.drawCreativeTabHoveringText(StatCollector.translateToLocal("gui.done"), par1, par2);
	}
}
