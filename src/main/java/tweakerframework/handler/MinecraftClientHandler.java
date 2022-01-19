package tweakerframework.handler;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinecraftClientHandler extends Handler
{
	public MinecraftClientHandler()
	{
		super("Minecraft Client");
	}

	@Override
	public void init() throws Exception
	{
		//Uncomment if you want to use the event bus here
		//MinecraftForge.EVENT_BUS.register(this);
	}
}
