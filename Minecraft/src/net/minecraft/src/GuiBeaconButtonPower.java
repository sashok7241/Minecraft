package net.minecraft.src;

class GuiBeaconButtonPower extends GuiBeaconButton
{
	private final int field_82261_l;
	private final int field_82262_m;
	final GuiBeacon beaconGui;
	
	public GuiBeaconButtonPower(GuiBeacon p_i5007_1_, int p_i5007_2_, int p_i5007_3_, int p_i5007_4_, int p_i5007_5_, int p_i5007_6_)
	{
		super(p_i5007_2_, p_i5007_3_, p_i5007_4_, "/gui/inventory.png", 0 + Potion.potionTypes[p_i5007_5_].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[p_i5007_5_].getStatusIconIndex() / 8 * 18);
		beaconGui = p_i5007_1_;
		field_82261_l = p_i5007_5_;
		field_82262_m = p_i5007_6_;
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
