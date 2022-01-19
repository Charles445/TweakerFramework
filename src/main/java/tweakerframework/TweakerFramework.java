package tweakerframework;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import tweakerframework.command.CommandErrorReport;
import tweakerframework.network.NetworkHandler;
import tweakerframework.network.PacketHandler;
import tweakerframework.proxy.CommonProxy;
import tweakerframework.util.ErrorUtil;
import tweakerframework.util.ServerRunnable;
import tweakerframework.util.VersionDelimiter;







/*
 * SETUP INSTRUCTIONS
 * 
 * 
 * 
 * Refactor the class packages to whatever custom name you prefer (ex. tweakerframework.handler -> myframework.handler)
 * Open the Reference.java file and fix all of the information, make sure the proxy and asm stuff points to the right place
 * Open mcmod.info and fix all of the information
 * Open build.gradle and fix the version, group, archivesBaseName
 * 
 * That should do it!
 * 
 *
 */






@Mod
(
	modid = TweakerFramework.MODID, 
	name = TweakerFramework.NAME, 
	version = TweakerFramework.VERSION,
	acceptedMinecraftVersions = "[1.12, 1.13)",
	acceptableRemoteVersions = "*",
	dependencies = "required-after:forge@[14.23.5.2859,);"
)
public class TweakerFramework
{
	public static final String MODID = Reference.MODID;
	public static final String NAME = Reference.NAME;
	public static final String VERSION = Reference.VERSION;
	public static final VersionDelimiter VERSION_DELIMITER = new VersionDelimiter(VERSION);
	
	@Mod.Instance(TweakerFramework.MODID)
	public static TweakerFramework instance;
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT,
			serverSide = Reference.PROXY_COMMON)
	public static CommonProxy proxy;
	public static Logger logger = LogManager.getLogger(Reference.NAME);
	public static Map<String, ServerRunnable> serverRunnables = new HashMap<>();
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().registerCrashCallable(new ErrorUtil.CrashCallable());
		PacketHandler.init();
		proxy.preInit();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit();
	}
	
	@Mod.EventHandler
	public void loadComplete(FMLLoadCompleteEvent event)
	{
		proxy.loadComplete();
	}
	
	@Mod.EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandErrorReport());
		Registry.registerCommands(event);
		this.serverRunnables.values().forEach(runnable -> runnable.onServerStarting());
	}
	
	@Mod.EventHandler
	public void serverStopping(FMLServerStoppingEvent event)
	{
		this.serverRunnables.values().forEach(runnable -> runnable.onServerStopping());
	}
	
	@NetworkCheckHandler
	public boolean checkVersion(Map<String, String> values, Side side)
	{
		String version = values.get(MODID);
		
		if(side == Side.SERVER)
		{
			NetworkHandler.serverHasVersioning = false;
			
			//System.out.println("checkVersion SERVER");
			if(StringUtils.isEmpty(version))
			{
				NetworkHandler.serverVersion = new VersionDelimiter("0.0.0");
			}
			else
			{
				VersionDelimiter servervd = new VersionDelimiter(version);
				NetworkHandler.serverVersion = servervd;
				if(servervd.isSameOrNewerVersion(0, 1))
				{
					NetworkHandler.serverHasVersioning = true;
				}
			}
			
			TweakerFramework.logger.trace("Server Version: "+NetworkHandler.serverVersion);
			TweakerFramework.logger.trace("Local Version: "+VERSION_DELIMITER);
			TweakerFramework.logger.trace("Server Has Versioning: "+NetworkHandler.serverHasVersioning);
			
			return true;
		}
		else
		{
			//System.out.println("checkVersion CLIENT");
			if(StringUtils.isEmpty(version))
			{
				TweakerFramework.logger.trace("Client Version: "+new VersionDelimiter("0.0.0"));
			}
			else
			{
				TweakerFramework.logger.trace("Client Version: "+new VersionDelimiter(version));
			}
			
			TweakerFramework.logger.trace("Local Version: "+VERSION_DELIMITER);
			
			return true;
		}
	}
	
}