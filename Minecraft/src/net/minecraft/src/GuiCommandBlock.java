package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class GuiCommandBlock extends GuiScreen
{
	private GuiTextField commandTextField;
	private final TileEntityCommandBlock commandBlock;
	private GuiButton doneBtn;
	private GuiButton cancelBtn;
	
	public GuiCommandBlock(TileEntityCommandBlock p_i5009_1_)
	{
		commandBlock = p_i5009_1_;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 1)
			{
				mc.displayGuiScreen((GuiScreen) null);
			} else if(par1GuiButton.id == 0)
			{
				String var2 = "MC|AdvCdm";
				ByteArrayOutputStream var3 = new ByteArrayOutputStream();
				DataOutputStream var4 = new DataOutputStream(var3);
				try
				{
					var4.writeInt(commandBlock.xCoord);
					var4.writeInt(commandBlock.yCoord);
					var4.writeInt(commandBlock.zCoord);
					Packet.writeString(commandTextField.getText(), var4);
					mc.getNetHandler().addToSendQueue(new Packet250CustomPayload(var2, var3.toByteArray()));
				} catch(Exception var6)
				{
					var6.printStackTrace();
				}
				mc.displayGuiScreen((GuiScreen) null);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		StringTranslate var4 = StringTranslate.getInstance();
		drawDefaultBackground();
		drawCenteredString(fontRenderer, var4.translateKey("advMode.setCommand"), width / 2, height / 4 - 60 + 20, 16777215);
		drawString(fontRenderer, var4.translateKey("advMode.command"), width / 2 - 150, 47, 10526880);
		drawString(fontRenderer, var4.translateKey("advMode.nearestPlayer"), width / 2 - 150, 97, 10526880);
		drawString(fontRenderer, var4.translateKey("advMode.randomPlayer"), width / 2 - 150, 108, 10526880);
		drawString(fontRenderer, var4.translateKey("advMode.allPlayers"), width / 2 - 150, 119, 10526880);
		commandTextField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(doneBtn = new GuiButton(0, width / 2 - 100, height / 4 + 96 + 12, var1.translateKey("gui.done")));
		buttonList.add(cancelBtn = new GuiButton(1, width / 2 - 100, height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
		commandTextField = new GuiTextField(fontRenderer, width / 2 - 150, 60, 300, 20);
		commandTextField.setMaxStringLength(32767);
		commandTextField.setFocused(true);
		commandTextField.setText(commandBlock.getCommand());
		doneBtn.enabled = commandTextField.getText().trim().length() > 0;
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		commandTextField.textboxKeyTyped(par1, par2);
		doneBtn.enabled = commandTextField.getText().trim().length() > 0;
		if(par2 != 28 && par1 != 13)
		{
			if(par2 == 1)
			{
				actionPerformed(cancelBtn);
			}
		} else
		{
			actionPerformed(doneBtn);
		}
	}
	
	@Override protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		commandTextField.mouseClicked(par1, par2, par3);
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override public void updateScreen()
	{
		commandTextField.updateCursorCounter();
	}
}
