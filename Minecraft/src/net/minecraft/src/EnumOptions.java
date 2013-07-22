package net.minecraft.src;

public enum EnumOptions
{
	MUSIC("options.music", true, false), SOUND("options.sound", true, false), INVERT_MOUSE("options.invertMouse", false, true), SENSITIVITY("options.sensitivity", true, false), FOV("options.fov", true, false), GAMMA("options.gamma", true, false), RENDER_DISTANCE("options.renderDistance", false, false), VIEW_BOBBING("options.viewBobbing", false, true), ANAGLYPH("options.anaglyph", false, true), ADVANCED_OPENGL("options.advancedOpengl", false, true), FRAMERATE_LIMIT("options.framerateLimit", false, false), DIFFICULTY("options.difficulty", false, false), GRAPHICS("options.graphics", false, false), AMBIENT_OCCLUSION("options.ao", false, false), GUI_SCALE("options.guiScale", false, false), RENDER_CLOUDS("options.renderClouds", false, true), PARTICLES("options.particles", false, false), CHAT_VISIBILITY("options.chat.visibility", false, false), CHAT_COLOR("options.chat.color", false, true), CHAT_LINKS("options.chat.links", false, true), CHAT_OPACITY("options.chat.opacity", true, false), CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true), USE_SERVER_TEXTURES("options.serverTextures", false, true), SNOOPER_ENABLED("options.snooper", false, true), USE_FULLSCREEN("options.fullscreen", false, true), ENABLE_VSYNC("options.vsync", false, true), SHOW_CAPE("options.showCape", false, true), TOUCHSCREEN("options.touchscreen", false, true), CHAT_SCALE("options.chat.scale", true, false), CHAT_WIDTH("options.chat.width", true, false), CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false), CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false);
	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;
	
	private EnumOptions(String p_i3011_3_, boolean p_i3011_4_, boolean p_i3011_5_)
	{
		enumString = p_i3011_3_;
		enumFloat = p_i3011_4_;
		enumBoolean = p_i3011_5_;
	}
	
	public boolean getEnumBoolean()
	{
		return enumBoolean;
	}
	
	public boolean getEnumFloat()
	{
		return enumFloat;
	}
	
	public String getEnumString()
	{
		return enumString;
	}
	
	public int returnEnumOrdinal()
	{
		return ordinal();
	}
	
	public static EnumOptions getEnumOptions(int par0)
	{
		EnumOptions[] var1 = values();
		int var2 = var1.length;
		for(int var3 = 0; var3 < var2; ++var3)
		{
			EnumOptions var4 = var1[var3];
			if(var4.returnEnumOrdinal() == par0) return var4;
		}
		return null;
	}
}
