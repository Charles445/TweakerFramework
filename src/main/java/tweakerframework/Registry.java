package tweakerframework;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import tweakerframework.handler.Handlers;
import tweakerframework.handler.MinecraftHandler;

public class Registry
{
	public static void registerHandlers()
	{
		//ADD COMMON HANDLERS HERE!
		Handlers.addHandler(new MinecraftHandler());
		
		
	}
	
	public static void registerCommands(FMLServerStartingEvent event)
	{
		//ADD COMMANDS HERE!
		//event.registerServerCommand(new CommandDebug());
		
		
	}
}
