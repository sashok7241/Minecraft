package net.minecraft.src;

class GuiBeaconButtonCancel extends GuiBeaconButton
{
	final GuiBeacon beaconGui;
	
	public GuiBeaconButtonCancel(GuiBeacon p_i5010_1_, int p_i5010_2_, int p_i5010_3_, int p_i5010_4_)
	{
		super(p_i5010_2_, p_i5010_3_, p_i5010_4_, "/gui/beacon.png", 112, 220);
		beaconGui = p_i5010_1_;
	}
	
	@Override public void func_82251_b(int par1, int par2)
	{
		beaconGui.drawCreativeTabHoveringText(StatCollector.translateToLocal("gui.cancel"), par1, par2);
	}
}
