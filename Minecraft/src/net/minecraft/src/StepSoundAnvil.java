package net.minecraft.src;

final class StepSoundAnvil extends StepSound
{
	StepSoundAnvil(String par1Str, float par2, float par3)
	{
		super(par1Str, par2, par3);
	}
	
	@Override public String getBreakSound()
	{
		return "dig.stone";
	}
	
	@Override public String getPlaceSound()
	{
		return "random.anvil_land";
	}
}
