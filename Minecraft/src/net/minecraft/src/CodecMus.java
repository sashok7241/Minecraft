package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;

public class CodecMus extends CodecJOrbis
{
	protected InputStream openInputStream()
	{
		try
		{
			return new MusInputStream(this, url, urlConnection.getInputStream());
		} catch(IOException e)
		{
			return null;
		}
	}
}
