package net.minecraft.src;

import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;

public class GuiScreenBackup extends GuiScreen
{
	private final GuiScreenConfigureWorld field_110380_a;
	private final long field_110377_b;
	private List field_110378_c = Collections.emptyList();
	private GuiScreenBackupSelectionList field_110375_d;
	private int field_110376_e = -1;
	private GuiButton field_110379_p;
	
	public GuiScreenBackup(GuiScreenConfigureWorld par1GuiScreenConfigureWorld, long par2)
	{
		field_110380_a = par1GuiScreenConfigureWorld;
		field_110377_b = par2;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				String var2 = I18n.func_135053_a("mco.configure.world.restore.question.line1");
				String var3 = I18n.func_135053_a("mco.configure.world.restore.question.line2");
				mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmationType.Warning, var2, var3, 1));
			} else if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen(field_110380_a);
			} else
			{
				field_110375_d.actionPerformed(par1GuiButton);
			}
		}
	}
	
	@Override public void confirmClicked(boolean par1, int par2)
	{
		if(par1 && par2 == 1)
		{
			func_110374_h();
		} else
		{
			mc.displayGuiScreen(this);
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		field_110375_d.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, I18n.func_135053_a("mco.backup.title"), width / 2, 20, 16777215);
		super.drawScreen(par1, par2, par3);
	}
	
	private void func_110369_g()
	{
		buttonList.add(new GuiButton(0, width / 2 + 6, height - 52, 153, 20, I18n.func_135053_a("gui.back")));
		buttonList.add(field_110379_p = new GuiButton(1, width / 2 - 154, height - 52, 153, 20, I18n.func_135053_a("mco.backup.button.restore")));
	}
	
	private void func_110374_h()
	{
		if(field_110376_e >= 0 && field_110376_e < field_110378_c.size())
		{
			Backup var1 = (Backup) field_110378_c.get(field_110376_e);
			GuiScreenBackupRestoreTask var2 = new GuiScreenBackupRestoreTask(this, var1, (GuiScreenBackupDownloadThread) null);
			GuiScreenLongRunningTask var3 = new GuiScreenLongRunningTask(mc, field_110380_a, var2);
			var3.func_98117_g();
			mc.displayGuiScreen(var3);
		}
	}
	
	@Override public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		field_110375_d = new GuiScreenBackupSelectionList(this);
		new GuiScreenBackupDownloadThread(this).start();
		func_110369_g();
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
	}
	
	static Minecraft func_110366_a(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.mc;
	}
	
	static long func_110367_b(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.field_110377_b;
	}
	
	static List func_110370_e(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.field_110378_c;
	}
	
	static List func_110373_a(GuiScreenBackup par0GuiScreenBackup, List par1List)
	{
		return par0GuiScreenBackup.field_110378_c = par1List;
	}
	
	static int func_130029_a(GuiScreenBackup par0GuiScreenBackup, int par1)
	{
		return par0GuiScreenBackup.field_110376_e = par1;
	}
	
	static Minecraft func_130030_c(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.mc;
	}
	
	static GuiScreenConfigureWorld func_130031_d(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.field_110380_a;
	}
	
	static FontRenderer func_130032_i(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.fontRenderer;
	}
	
	static FontRenderer func_130033_j(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.fontRenderer;
	}
	
	static int func_130034_h(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.field_110376_e;
	}
	
	static Minecraft func_130035_e(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.mc;
	}
	
	static Minecraft func_130036_f(GuiScreenBackup par0GuiScreenBackup)
	{
		return par0GuiScreenBackup.mc;
	}
}
