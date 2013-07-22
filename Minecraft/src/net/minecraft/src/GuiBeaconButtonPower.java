package net.minecraft.src;

class GuiBeaconButtonPower extends GuiBeaconButton
{
	private final int field_82261_l;
	private final int field_82262_m;
	final GuiBeacon beaconGui;
	
	public GuiBeaconButtonPower(GuiBeacon par1GuiBeacon, int par2, int par3, int par4, int par5, int par6)
	{
		super(par2, par3, par4, "/gui/inventory.png", 0 + Potion.potionTypes[par5].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[par5].getStatusIconIndex() / 8 * 18);
		beaconGui = par1GuiBeacon;
		field_82261_l = par5;
		field_82262_m = par6;
	}
	
	@Override public void func_82251_b(int par1, int par2)
	{
		String var3 = StatCollector.translateToLocal(Potion.potionTypes[field_82261_l].getName());
		if(field_82262_m >= 3 && field_82261_l != Potion.regeneration.id)
		{
			var3 = var3 + " II";
		}
		beaconGui.drawCreativeTabHoveringText(var3, par1, par2);
	}
}
