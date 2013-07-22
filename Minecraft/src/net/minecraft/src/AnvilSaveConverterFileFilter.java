package net.minecraft.src;

import java.io.File;
import java.io.FilenameFilter;

class AnvilSaveConverterFileFilter implements FilenameFilter
{
	final AnvilSaveConverter parent;
	
	AnvilSaveConverterFileFilter(AnvilSaveConverter p_i3909_1_)
	{
		parent = p_i3909_1_;
	}
	
	@Override public boolean accept(File p_accept_1_, String p_accept_2_)
	{
		return p_accept_2_.endsWith(".mcr");
	}
}
