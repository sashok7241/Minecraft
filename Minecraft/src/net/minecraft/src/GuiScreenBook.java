package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class GuiScreenBook extends GuiScreen
{
	private final EntityPlayer editingPlayer;
	private final ItemStack itemstackBook;
	private final boolean bookIsUnsigned;
	private boolean bookModified;
	private boolean editingTitle;
	private int updateCount;
	private int bookImageWidth = 192;
	private int bookImageHeight = 192;
	private int bookTotalPages = 1;
	private int currPage;
	private NBTTagList bookPages;
	private String bookTitle = "";
	private GuiButtonNextPage buttonNextPage;
	private GuiButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private GuiButton buttonSign;
	private GuiButton buttonFinalize;
	private GuiButton buttonCancel;
	
	public GuiScreenBook(EntityPlayer p_i3085_1_, ItemStack p_i3085_2_, boolean p_i3085_3_)
	{
		editingPlayer = p_i3085_1_;
		itemstackBook = p_i3085_2_;
		bookIsUnsigned = p_i3085_3_;
		if(p_i3085_2_.hasTagCompound())
		{
			NBTTagCompound var4 = p_i3085_2_.getTagCompound();
			bookPages = var4.getTagList("pages");
			if(bookPages != null)
			{
				bookPages = (NBTTagList) bookPages.copy();
				bookTotalPages = bookPages.tagCount();
				if(bookTotalPages < 1)
				{
					bookTotalPages = 1;
				}
			}
		}
		if(bookPages == null && p_i3085_3_)
		{
			bookPages = new NBTTagList("pages");
			bookPages.appendTag(new NBTTagString("1", ""));
			bookTotalPages = 1;
		}
	}
	
	@Override protected void actionPerformed(GuiButton par1GuiButton)
	{
		if(par1GuiButton.enabled)
		{
			if(par1GuiButton.id == 0)
			{
				mc.displayGuiScreen((GuiScreen) null);
				sendBookToServer(false);
			} else if(par1GuiButton.id == 3 && bookIsUnsigned)
			{
				editingTitle = true;
			} else if(par1GuiButton.id == 1)
			{
				if(currPage < bookTotalPages - 1)
				{
					++currPage;
				} else if(bookIsUnsigned)
				{
					addNewPage();
					if(currPage < bookTotalPages - 1)
					{
						++currPage;
					}
				}
			} else if(par1GuiButton.id == 2)
			{
				if(currPage > 0)
				{
					--currPage;
				}
			} else if(par1GuiButton.id == 5 && editingTitle)
			{
				sendBookToServer(true);
				mc.displayGuiScreen((GuiScreen) null);
			} else if(par1GuiButton.id == 4 && editingTitle)
			{
				editingTitle = false;
			}
			updateButtons();
		}
	}
	
	private void addNewPage()
	{
		if(bookPages != null && bookPages.tagCount() < 50)
		{
			bookPages.appendTag(new NBTTagString("" + (bookTotalPages + 1), ""));
			++bookTotalPages;
			bookModified = true;
		}
	}
	
	@Override public void drawScreen(int par1, int par2, float par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture("/gui/book.png");
		int var4 = (width - bookImageWidth) / 2;
		byte var5 = 2;
		drawTexturedModalRect(var4, var5, 0, 0, bookImageWidth, bookImageHeight);
		String var6;
		String var7;
		int var8;
		if(editingTitle)
		{
			var6 = bookTitle;
			if(bookIsUnsigned)
			{
				if(updateCount / 6 % 2 == 0)
				{
					var6 = var6 + "" + EnumChatFormatting.BLACK + "_";
				} else
				{
					var6 = var6 + "" + EnumChatFormatting.GRAY + "_";
				}
			}
			var7 = StatCollector.translateToLocal("book.editTitle");
			var8 = fontRenderer.getStringWidth(var7);
			fontRenderer.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
			int var9 = fontRenderer.getStringWidth(var6);
			fontRenderer.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
			String var10 = String.format(StatCollector.translateToLocal("book.byAuthor"), new Object[] { editingPlayer.username });
			int var11 = fontRenderer.getStringWidth(var10);
			fontRenderer.drawString(EnumChatFormatting.DARK_GRAY + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
			String var12 = StatCollector.translateToLocal("book.finalizeWarning");
			fontRenderer.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
		} else
		{
			var6 = String.format(StatCollector.translateToLocal("book.pageIndicator"), new Object[] { Integer.valueOf(currPage + 1), Integer.valueOf(bookTotalPages) });
			var7 = "";
			if(bookPages != null && currPage >= 0 && currPage < bookPages.tagCount())
			{
				NBTTagString var13 = (NBTTagString) bookPages.tagAt(currPage);
				var7 = var13.toString();
			}
			if(bookIsUnsigned)
			{
				if(fontRenderer.getBidiFlag())
				{
					var7 = var7 + "_";
				} else if(updateCount / 6 % 2 == 0)
				{
					var7 = var7 + "" + EnumChatFormatting.BLACK + "_";
				} else
				{
					var7 = var7 + "" + EnumChatFormatting.GRAY + "_";
				}
			}
			var8 = fontRenderer.getStringWidth(var6);
			fontRenderer.drawString(var6, var4 - var8 + bookImageWidth - 44, var5 + 16, 0);
			fontRenderer.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
		}
		super.drawScreen(par1, par2, par3);
	}
	
	private String func_74158_i()
	{
		if(bookPages != null && currPage >= 0 && currPage < bookPages.tagCount())
		{
			NBTTagString var1 = (NBTTagString) bookPages.tagAt(currPage);
			return var1.toString();
		} else return "";
	}
	
	private void func_74159_a(String par1Str)
	{
		if(bookPages != null && currPage >= 0 && currPage < bookPages.tagCount())
		{
			NBTTagString var2 = (NBTTagString) bookPages.tagAt(currPage);
			var2.data = par1Str;
			bookModified = true;
		}
	}
	
	private void func_74160_b(String par1Str)
	{
		String var2 = func_74158_i();
		String var3 = var2 + par1Str;
		int var4 = fontRenderer.splitStringWidth(var3 + "" + EnumChatFormatting.BLACK + "_", 118);
		if(var4 <= 118 && var3.length() < 256)
		{
			func_74159_a(var3);
		}
	}
	
	private void func_74162_c(char par1, int par2)
	{
		switch(par2)
		{
			case 14:
				if(bookTitle.length() > 0)
				{
					bookTitle = bookTitle.substring(0, bookTitle.length() - 1);
					updateButtons();
				}
				return;
			case 28:
				if(bookTitle.length() > 0)
				{
					sendBookToServer(true);
					mc.displayGuiScreen((GuiScreen) null);
				}
				return;
			default:
				if(bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(par1))
				{
					bookTitle = bookTitle + Character.toString(par1);
					updateButtons();
					bookModified = true;
				}
		}
	}
	
	@Override public void initGui()
	{
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);
		if(bookIsUnsigned)
		{
			buttonList.add(buttonSign = new GuiButton(3, width / 2 - 100, 4 + bookImageHeight, 98, 20, StatCollector.translateToLocal("book.signButton")));
			buttonList.add(buttonDone = new GuiButton(0, width / 2 + 2, 4 + bookImageHeight, 98, 20, StatCollector.translateToLocal("gui.done")));
			buttonList.add(buttonFinalize = new GuiButton(5, width / 2 - 100, 4 + bookImageHeight, 98, 20, StatCollector.translateToLocal("book.finalizeButton")));
			buttonList.add(buttonCancel = new GuiButton(4, width / 2 + 2, 4 + bookImageHeight, 98, 20, StatCollector.translateToLocal("gui.cancel")));
		} else
		{
			buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + bookImageHeight, 200, 20, StatCollector.translateToLocal("gui.done")));
		}
		int var1 = (width - bookImageWidth) / 2;
		byte var2 = 2;
		buttonList.add(buttonNextPage = new GuiButtonNextPage(1, var1 + 120, var2 + 154, true));
		buttonList.add(buttonPreviousPage = new GuiButtonNextPage(2, var1 + 38, var2 + 154, false));
		updateButtons();
	}
	
	@Override protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);
		if(bookIsUnsigned)
		{
			if(editingTitle)
			{
				func_74162_c(par1, par2);
			} else
			{
				keyTypedInBook(par1, par2);
			}
		}
	}
	
	private void keyTypedInBook(char par1, int par2)
	{
		switch(par1)
		{
			case 22:
				func_74160_b(GuiScreen.getClipboardString());
				return;
			default:
				switch(par2)
				{
					case 14:
						String var3 = func_74158_i();
						if(var3.length() > 0)
						{
							func_74159_a(var3.substring(0, var3.length() - 1));
						}
						return;
					case 28:
						func_74160_b("\n");
						return;
					default:
						if(ChatAllowedCharacters.isAllowedCharacter(par1))
						{
							func_74160_b(Character.toString(par1));
						}
				}
		}
	}
	
	@Override public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	private void sendBookToServer(boolean par1)
	{
		if(bookIsUnsigned && bookModified)
		{
			if(bookPages != null)
			{
				while(bookPages.tagCount() > 1)
				{
					NBTTagString var2 = (NBTTagString) bookPages.tagAt(bookPages.tagCount() - 1);
					if(var2.data != null && var2.data.length() != 0)
					{
						break;
					}
					bookPages.removeTag(bookPages.tagCount() - 1);
				}
				if(itemstackBook.hasTagCompound())
				{
					NBTTagCompound var7 = itemstackBook.getTagCompound();
					var7.setTag("pages", bookPages);
				} else
				{
					itemstackBook.setTagInfo("pages", bookPages);
				}
				String var8 = "MC|BEdit";
				if(par1)
				{
					var8 = "MC|BSign";
					itemstackBook.setTagInfo("author", new NBTTagString("author", editingPlayer.username));
					itemstackBook.setTagInfo("title", new NBTTagString("title", bookTitle.trim()));
					itemstackBook.itemID = Item.writtenBook.itemID;
				}
				ByteArrayOutputStream var3 = new ByteArrayOutputStream();
				DataOutputStream var4 = new DataOutputStream(var3);
				try
				{
					Packet.writeItemStack(itemstackBook, var4);
					mc.getNetHandler().addToSendQueue(new Packet250CustomPayload(var8, var3.toByteArray()));
				} catch(Exception var6)
				{
					var6.printStackTrace();
				}
			}
		}
	}
	
	private void updateButtons()
	{
		buttonNextPage.drawButton = !editingTitle && (currPage < bookTotalPages - 1 || bookIsUnsigned);
		buttonPreviousPage.drawButton = !editingTitle && currPage > 0;
		buttonDone.drawButton = !bookIsUnsigned || !editingTitle;
		if(bookIsUnsigned)
		{
			buttonSign.drawButton = !editingTitle;
			buttonCancel.drawButton = editingTitle;
			buttonFinalize.drawButton = editingTitle;
			buttonFinalize.enabled = bookTitle.trim().length() > 0;
		}
	}
	
	@Override public void updateScreen()
	{
		super.updateScreen();
		++updateCount;
	}
}
