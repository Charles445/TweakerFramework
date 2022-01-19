package tweakerframework;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tweakerframework.handler.Handlers;
import tweakerframework.handler.MinecraftClientHandler;

@SideOnly(Side.CLIENT)
public class RegistryClient
{
	public static void registerHandlers()
	{
		//ADD CLIENT HANDLERS HERE!
		Handlers.addClientHandler(new MinecraftClientHandler());
		
		
	}
}
