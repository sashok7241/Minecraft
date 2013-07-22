package net.minecraft.src;

import net.minecraft.client.Minecraft;

public abstract class TaskLongRunning implements Runnable
{
	protected GuiScreenLongRunningTask field_96579_b;
	
	public void func_96571_d()
	{
	}
	
	public void func_96572_a(GuiButton par1GuiButton)
	{
	}
	
	public void func_96573_a()
	{
	}
	
	public void func_96574_a(GuiScreenLongRunningTask par1GuiScreenLongRunningTask)
	{
		field_96579_b = par1GuiScreenLongRunningTask;
	}
	
	public void func_96575_a(String par1Str)
	{
		field_96579_b.func_96209_a(par1Str);
	}
	
	public void func_96576_b(String par1Str)
	{
		field_96579_b.func_96210_b(par1Str);
	}
	
	public boolean func_96577_c()
	{
		return field_96579_b.func_96207_h();
	}
	
	public Minecraft func_96578_b()
	{
		return field_96579_b.func_96208_g();
	}
}
