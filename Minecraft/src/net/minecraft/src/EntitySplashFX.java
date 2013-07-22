package net.minecraft.src;

public class EntitySplashFX extends EntityRainFX
{
	public EntitySplashFX(World p_i3163_1_, double p_i3163_2_, double p_i3163_4_, double p_i3163_6_, double p_i3163_8_, double p_i3163_10_, double p_i3163_12_)
	{
		super(p_i3163_1_, p_i3163_2_, p_i3163_4_, p_i3163_6_);
		particleGravity = 0.04F;
		nextTextureIndexX();
		if(p_i3163_10_ == 0.0D && (p_i3163_8_ != 0.0D || p_i3163_12_ != 0.0D))
		{
			motionX = p_i3163_8_;
			motionY = p_i3163_10_ + 0.1D;
			motionZ = p_i3163_12_;
		}
	}
}
