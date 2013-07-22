package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TileEntityRenderer
{
	private Map specialRendererMap = new HashMap();
	public static TileEntityRenderer instance = new TileEntityRenderer();
	private FontRenderer fontRenderer;
	public static double staticPlayerX;
	public static double staticPlayerY;
	public static double staticPlayerZ;
	public RenderEngine renderEngine;
	public World worldObj;
	public EntityLiving entityLivingPlayer;
	public float playerYaw;
	public float playerPitch;
	public double playerX;
	public double playerY;
	public double playerZ;
	
	private TileEntityRenderer()
	{
		specialRendererMap.put(TileEntitySign.class, new TileEntitySignRenderer());
		specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
		specialRendererMap.put(TileEntityPiston.class, new TileEntityRendererPiston());
		specialRendererMap.put(TileEntityChest.class, new TileEntityChestRenderer());
		specialRendererMap.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
		specialRendererMap.put(TileEntityEnchantmentTable.class, new RenderEnchantmentTable());
		specialRendererMap.put(TileEntityEndPortal.class, new RenderEndPortal());
		specialRendererMap.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
		specialRendererMap.put(TileEntitySkull.class, new TileEntitySkullRenderer());
		Iterator var1 = specialRendererMap.values().iterator();
		while(var1.hasNext())
		{
			TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer) var1.next();
			var2.setTileEntityRenderer(this);
		}
	}
	
	public void cacheActiveRenderInfo(World par1World, RenderEngine par2RenderEngine, FontRenderer par3FontRenderer, EntityLiving par4EntityLiving, float par5)
	{
		if(worldObj != par1World)
		{
			setWorld(par1World);
		}
		renderEngine = par2RenderEngine;
		entityLivingPlayer = par4EntityLiving;
		fontRenderer = par3FontRenderer;
		playerYaw = par4EntityLiving.prevRotationYaw + (par4EntityLiving.rotationYaw - par4EntityLiving.prevRotationYaw) * par5;
		playerPitch = par4EntityLiving.prevRotationPitch + (par4EntityLiving.rotationPitch - par4EntityLiving.prevRotationPitch) * par5;
		playerX = par4EntityLiving.lastTickPosX + (par4EntityLiving.posX - par4EntityLiving.lastTickPosX) * par5;
		playerY = par4EntityLiving.lastTickPosY + (par4EntityLiving.posY - par4EntityLiving.lastTickPosY) * par5;
		playerZ = par4EntityLiving.lastTickPosZ + (par4EntityLiving.posZ - par4EntityLiving.lastTickPosZ) * par5;
	}
	
	public FontRenderer getFontRenderer()
	{
		return fontRenderer;
	}
	
	public TileEntitySpecialRenderer getSpecialRendererForClass(Class par1Class)
	{
		TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer) specialRendererMap.get(par1Class);
		if(var2 == null && par1Class != TileEntity.class)
		{
			var2 = getSpecialRendererForClass(par1Class.getSuperclass());
			specialRendererMap.put(par1Class, var2);
		}
		return var2;
	}
	
	public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity par1TileEntity)
	{
		return par1TileEntity == null ? null : getSpecialRendererForClass(par1TileEntity.getClass());
	}
	
	public boolean hasSpecialRenderer(TileEntity par1TileEntity)
	{
		return getSpecialRendererForEntity(par1TileEntity) != null;
	}
	
	public void renderTileEntity(TileEntity par1TileEntity, float par2)
	{
		if(par1TileEntity.getDistanceFrom(playerX, playerY, playerZ) < par1TileEntity.getMaxRenderDistanceSquared())
		{
			int var3 = worldObj.getLightBrightnessForSkyBlocks(par1TileEntity.xCoord, par1TileEntity.yCoord, par1TileEntity.zCoord, 0);
			int var4 = var3 % 65536;
			int var5 = var3 / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var4 / 1.0F, var5 / 1.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			renderTileEntityAt(par1TileEntity, par1TileEntity.xCoord - staticPlayerX, par1TileEntity.yCoord - staticPlayerY, par1TileEntity.zCoord - staticPlayerZ, par2);
		}
	}
	
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		TileEntitySpecialRenderer var9 = getSpecialRendererForEntity(par1TileEntity);
		if(var9 != null)
		{
			try
			{
				var9.renderTileEntityAt(par1TileEntity, par2, par4, par6, par8);
			} catch(Throwable var13)
			{
				CrashReport var11 = CrashReport.makeCrashReport(var13, "Rendering Tile Entity");
				CrashReportCategory var12 = var11.makeCategory("Tile Entity Details");
				par1TileEntity.func_85027_a(var12);
				throw new ReportedException(var11);
			}
		}
	}
	
	public void setWorld(World par1World)
	{
		worldObj = par1World;
		Iterator var2 = specialRendererMap.values().iterator();
		while(var2.hasNext())
		{
			TileEntitySpecialRenderer var3 = (TileEntitySpecialRenderer) var2.next();
			if(var3 != null)
			{
				var3.onWorldChange(par1World);
			}
		}
	}
}
