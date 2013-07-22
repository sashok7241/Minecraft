package net.minecraft.src;

import java.io.IOException;

class ThreadTitleScreen extends Thread
{
	final StringTranslate field_98135_a;
	final int field_98133_b;
	final int field_98134_c;
	final GuiMainMenu field_98132_d;
	
	ThreadTitleScreen(GuiMainMenu p_i11019_1_, StringTranslate p_i11019_2_, int p_i11019_3_, int p_i11019_4_)
	{
		field_98132_d = p_i11019_1_;
		field_98135_a = p_i11019_2_;
		field_98133_b = p_i11019_3_;
		field_98134_c = p_i11019_4_;
	}
	
	@Override public void run()
	{
		McoClient var1 = new McoClient(GuiMainMenu.func_98058_a(field_98132_d).session);
		boolean var2 = false;
		for(int var3 = 0; var3 < 3; ++var3)
		{
			try
			{
				Boolean var4 = var1.func_96375_b();
				if(var4.booleanValue())
				{
					GuiMainMenu.func_98061_a(field_98132_d, field_98135_a, field_98133_b, field_98134_c);
				}
				GuiMainMenu.func_98059_a(var4.booleanValue());
			} catch(ExceptionRetryCall var6)
			{
				var2 = true;
			} catch(ExceptionMcoService var7)
			{
				;
			} catch(IOException var8)
			{
				;
			}
			if(!var2)
			{
				break;
			}
			try
			{
				Thread.sleep(10000L);
			} catch(InterruptedException var5)
			{
				Thread.currentThread().interrupt();
			}
		}
	}
}
