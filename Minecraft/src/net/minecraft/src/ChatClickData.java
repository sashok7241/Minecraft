package net.minecraft.src;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;

public class ChatClickData
{
	public static final Pattern pattern = Pattern.compile("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
	private final FontRenderer fontR;
	private final ChatLine line;
	private final int field_78312_d;
	private final int field_78313_e;
	private final String field_78310_f;
	private final String clickedUrl;
	
	public ChatClickData(FontRenderer p_i3053_1_, ChatLine p_i3053_2_, int p_i3053_3_, int p_i3053_4_)
	{
		fontR = p_i3053_1_;
		line = p_i3053_2_;
		field_78312_d = p_i3053_3_;
		field_78313_e = p_i3053_4_;
		field_78310_f = p_i3053_1_.trimStringToWidth(p_i3053_2_.getChatLineString(), p_i3053_3_);
		clickedUrl = findClickedUrl();
	}
	
	private String findClickedUrl()
	{
		int var1 = field_78310_f.lastIndexOf(" ", field_78310_f.length()) + 1;
		if(var1 < 0)
		{
			var1 = 0;
		}
		int var2 = line.getChatLineString().indexOf(" ", var1);
		if(var2 < 0)
		{
			var2 = line.getChatLineString().length();
		}
		return StringUtils.stripControlCodes(line.getChatLineString().substring(var1, var2));
	}
	
	public String getClickedUrl()
	{
		return clickedUrl;
	}
	
	public URI getURI()
	{
		String var1 = getClickedUrl();
		if(var1 == null) return null;
		else
		{
			Matcher var2 = pattern.matcher(var1);
			if(var2.matches())
			{
				try
				{
					String var3 = var2.group(0);
					if(var2.group(1) == null)
					{
						var3 = "http://" + var3;
					}
					return new URI(var3);
				} catch(URISyntaxException var4)
				{
					Minecraft.getMinecraft().getLogAgent().logSevereException("Couldn\'t create URI from chat", var4);
				}
			}
			return null;
		}
	}
}
