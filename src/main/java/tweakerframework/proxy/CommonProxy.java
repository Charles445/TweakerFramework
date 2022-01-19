package tweakerframework.proxy;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import tweakerframework.Registry;
import tweakerframework.handler.Handlers;
import tweakerframework.handler.TweakerCoreHandler;

public class CommonProxy
{
	public void preInit()
	{
		
	}
	
	public void init()
	{
		
	}
	
	public void postInit()
	{
		
	}
	
	public void loadComplete()
	{
		Handlers.addHandler(new TweakerCoreHandler());
		Registry.registerHandlers();
	}
	
	@Nullable
	public EntityPlayer getClientMinecraftPlayer()
	{
		return null;
	}
	
	@Nullable
	public Boolean isClientConnectedToServer()
	{
		return null;
	}
}
