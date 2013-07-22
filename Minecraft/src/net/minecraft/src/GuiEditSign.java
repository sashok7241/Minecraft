package net.minecraft.src;


public class GuiEditSign extends GuiScreen
{
	private static final String allowedCharacters = ChatAllowedCharacters.allowedCharacters;
	protected String screenTitle = "Edit sign message:";
	private TileEntitySign entitySign;
	private int updateCounter;
	private int editLine;
	private GuiButton doneBtn;
	
	public GuiEditSign(TileEntitySign par1TileEntitySign)
	{
		entitySign = par1TileEntitySign;
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 0)
			{
				entitySign.onInventoryChanged();
				mc.displayGuiScreen((GuiScreen) null);
			}
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		drawDefaultBackground();
		drawCenteredString(fontRenderer, screenTitle, width / 2, 40, 16777215);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) (width / 2), 0.0F, 50.0F);
		float var4 = 93.75F;
		GL11.glScalef(-var4, -var4, -var4);
		GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
		Block var5 = entitySign.getBlockType();
		if(var5 == Block.signPost)
		{
			float var6 = entitySign.getBlockMetadata() * 360 / 16.0F;
			GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
		} else
		{
			int var8 = entitySign.getBlockMetadata();
			float var7 = 0.0F;
			if(var8 == 2)
			{
				var7 = 180.0F;
			}
			if(var8 == 4)
			{
				var7 = 90.0F;
			}
			if(var8 == 5)
			{
				var7 = -90.0F;
			}
			GL11.glRotatef(var7, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, -1.0625F, 0.0F);
		}
		if(updateCounter / 6 % 2 == 0)
		{
			entitySign.lineBeingEdited = editLine;
		}
		TileEntityRenderer.instance.renderTileEntityAt(entitySign, -0.5D, -0.75D, -0.5D, 0.0F);
		entitySign.lineBeingEdited = -1;
		GL11.glPopMatrix();
		super.drawScreen(par1, par2, par3);
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		buttonList.add(doneBtn = new GuiButton(0, width / 2 - 100, height / 4 + 120, "Done"));
		entitySign.setEditable(false);
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		if(par2 == 200)
		{
			editLine = editLine - 1 & 3;
		}
		if(par2 == 208 || par2 == 28 || par2 == 156)
		{
			editLine = editLine + 1 & 3;
		}
		if(par2 == 14 && entitySign.signText[editLine].length() > 0)
		{
			entitySign.signText[editLine] = entitySign.signText[editLine].substring(0, entitySign.signText[editLine].length() - 1);
		}
		if(allowedCharacters.indexOf(par1) >= 0 && entitySign.signText[editLine].length() < 15)
		{
			entitySign.signText[editLine] = entitySign.signText[editLine] + par1;
		}
		if(par2 == 1)
		{
			actionPerformed(doneBtn);
		}
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
		NetClientHandler var1 = mc.getNetHandler();
		if(var1 != null)
		{
			var1.addToSendQueue(new Packet130UpdateSign(entitySign.xCoord, entitySign.yCoord, entitySign.zCoord, entitySign.signText));
		}
		entitySign.setEditable(true);
	}
	
	@Override public void updateScreen()
	{
		++updateCounter;
	}
}
