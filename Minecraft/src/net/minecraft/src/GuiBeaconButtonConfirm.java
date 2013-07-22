package net.minecraft.src;

class GuiBeaconButtonConfirm extends GuiBeaconButton
{
	final GuiBeacon beaconGui;
	
	public GuiBeaconButtonConfirm(GuiBeacon p_i5011_1_, int p_i5011_2_, int p_i5011_3_, int p_i5011_4_)
	{
		super(p_i5011_2_, p_i5011_3_, p_i5011_4_, "/gui/beacon.png", 90, 220);
		beaconGui = p_i5011_1_;
	}
	
	@Override public void func_82251_b(int par1, int par2)
	{
		beaconGui.drawCreativeTabHoveringText(StatCollector.translateToLocal("gui.done"), par1, par2);
	}
}
