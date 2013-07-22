package net.minecraft.src;

import java.awt.BorderLayout;
import java.awt.Canvas;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftApplet;

public class MinecraftAppletImpl extends Minecraft
{
	final MinecraftApplet mainFrame;
	
	public MinecraftAppletImpl(MinecraftApplet p_i3013_1_, Canvas p_i3013_2_, MinecraftApplet p_i3013_3_, int p_i3013_4_, int p_i3013_5_, boolean p_i3013_6_)
	{
		super(p_i3013_2_, p_i3013_3_, p_i3013_4_, p_i3013_5_, p_i3013_6_);
		mainFrame = p_i3013_1_;
	}
	
	@Override public void displayCrashReportInternal(CrashReport p_71406_1_)
	{
		mainFrame.removeAll();
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(new PanelCrashReport(p_71406_1_), "Center");
		mainFrame.validate();
	}
	
	@Override public void startGame() throws LWJGLException
	{
		mcDataDir = getMinecraftDir();
		gameSettings = new GameSettings(this, mcDataDir);
		if(gameSettings.overrideHeight > 0 && gameSettings.overrideWidth > 0 && mainFrame.getParent() != null && mainFrame.getParent().getParent() != null)
		{
			mainFrame.getParent().getParent().setSize(gameSettings.overrideWidth, gameSettings.overrideHeight);
		}
		super.startGame();
	}
}
