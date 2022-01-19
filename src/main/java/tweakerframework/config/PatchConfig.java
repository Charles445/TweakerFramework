package tweakerframework.config;

import net.minecraftforge.common.config.Config;

public class PatchConfig
{
	//Do NOT provide a Config.Name!
	//DO NOT!
	
	//Field name will be appended to
	//general.patches.
	
	//general.patches.ENABLED
	@Config.RequiresMcRestart
	@Config.Comment("Master switch for the coremod")
	public boolean ENABLED = true;
}
