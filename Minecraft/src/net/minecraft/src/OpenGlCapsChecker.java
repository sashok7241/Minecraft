package net.minecraft.src;


public class OpenGlCapsChecker
{
	public static boolean checkARBOcclusion()
	{
		return GLContext.getCapabilities().GL_ARB_occlusion_query;
	}
}
