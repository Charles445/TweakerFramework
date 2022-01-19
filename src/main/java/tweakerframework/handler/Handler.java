package tweakerframework.handler;

import tweakerframework.TweakerFramework;
import tweakerframework.util.CriticalException;
import tweakerframework.util.ErrorUtil;

public abstract class Handler
{
	/*
		Handlers start with a Handler() constructor that uses super to define the mod name
		
		Do any setup in the init function
		
		If your handler uses SubscribeEvent, make sure to register it to the event bus at the end of init
		
		
		USEFUL TOOLS
		
		ReflectUtil - makes reflection easier
		CompatUtil - lets you wrap or remove handlers from the event bus and also lets you wrap world generators
		ErrorUtil - lets you log errors that will show up in crash reports or if server owners use the error report command
		
		
		
		More complex but still useful tools
		
		AIUtil - Lets you swap out ai tasks in place
		GuiDelegator.addDelegate - For clients, lets you run tasks when players interact with the GUI
		NetworkHandler.isVersionAtLeast - Lets you check the version of the tweaker the player has, great for networking (clients use NetworkHandler.serverVersion)
		StackTraceUtil - Looks for classes in the stack trace, not recommended
	*/
	
	
	
	public String modName;
	
	public Handler(String modname)
	{
		this.modName = modname;
		
		try
		{
			this.init();
		}
		catch(Exception e)
		{
			TweakerFramework.logger.error("Failed to setup "+modname+"!", e);
			ErrorUtil.logSilent(""+modname+" Critical Setup Failure");
			
			//Crash on Critical
			if(e instanceof CriticalException)
				throw new RuntimeException(e);
		}
	}
	
	/**
	 * Setup your handler here
	 * Cache whatever reflection you need to cache
	 * Run any methods you need to run
	 * Register the instance to the event bus if you are going to use it for events
	 * @throws Exception
	 */
	public abstract void init() throws Exception;
}
