package net.minecraft.src;

public class StepSound
{
	public final String stepSoundName;
	public final float stepSoundVolume;
	public final float stepSoundPitch;
	
	public StepSound(String p_i4008_1_, float p_i4008_2_, float p_i4008_3_)
	{
		stepSoundName = p_i4008_1_;
		stepSoundVolume = p_i4008_2_;
		stepSoundPitch = p_i4008_3_;
	}
	
	public String getBreakSound()
	{
		return "dig." + stepSoundName;
	}
	
	public float getPitch()
	{
		return stepSoundPitch;
	}
	
	public String getPlaceSound()
	{
		return getBreakSound();
	}
	
	public String getStepSound()
	{
		return "step." + stepSoundName;
	}
	
	public float getVolume()
	{
		return stepSoundVolume;
	}
}
