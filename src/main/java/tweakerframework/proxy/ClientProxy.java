package tweakerframework.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import tweakerframework.RegistryClient;
import tweakerframework.client.gui.GuiDelegator;
import tweakerframework.handler.Handlers;
import tweakerframework.handler.MinecraftClientHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void postInit()
	{
		super.postInit();
		
		//GuiDelegator
		Handlers.clientHandlers.put("GuiDelegator", new GuiDelegator());
	}
	
	@Override
	public void loadComplete()
	{
		super.loadComplete();
		RegistryClient.registerHandlers();
	}
	
	@Override
	public EntityPlayer getClientMinecraftPlayer()
	{
		return Minecraft.getMinecraft().player;
	}

	@Override
	public Boolean isClientConnectedToServer()
	{
		return Minecraft.getMinecraft().getConnection().getNetworkManager().isChannelOpen();
	}
}
