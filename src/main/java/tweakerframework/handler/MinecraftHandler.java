package tweakerframework.handler;

public class MinecraftHandler extends Handler
{
	public MinecraftHandler() 
	{
		super("Minecraft");
	}

	@Override
	public void init() throws Exception
	{
		//Uncomment if you want to use the event bus here
		//MinecraftForge.EVENT_BUS.register(this);
	}
}
