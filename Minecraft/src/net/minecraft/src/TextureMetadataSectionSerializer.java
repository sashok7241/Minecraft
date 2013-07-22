package net.minecraft.src;

import java.lang.reflect.Type;

public class TextureMetadataSectionSerializer extends BaseMetadataSectionSerializer
{
	public Object deserialize(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
	{
		return func_110494_a(par1JsonElement, par2Type, par3JsonDeserializationContext);
	}
	
	@Override public String func_110483_a()
	{
		return "texture";
	}
	
	public TextureMetadataSection func_110494_a(JsonElement par1JsonElement, Type par2Type, JsonDeserializationContext par3JsonDeserializationContext)
	{
		JsonObject var4 = par1JsonElement.getAsJsonObject();
		boolean var5 = func_110484_a(var4.get("blur"), "blur", Boolean.valueOf(false));
		boolean var6 = func_110484_a(var4.get("clamp"), "clamp", Boolean.valueOf(false));
		return new TextureMetadataSection(var5, var6);
	}
}
